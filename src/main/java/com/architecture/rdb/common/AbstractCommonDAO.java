package com.architecture.rdb.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.architecture.rdb.exception.CommonException;
/**
 * Responsável por provir os métodos comuns a todos os Services(DAOs) <tt>(Data access objects)</tt>.
 * @author Daniel Menezes <tt>daniel.afmenezes@gmail.com</tt>
 * @param <E> Entidade relacionada
 */
public abstract class AbstractCommonDAO<E extends CommonBean> implements CommonDAO<E>, Serializable  {

	/** Logger da instância. */
	protected final Logger logger = LogManager.getLogManager().getLogger(AbstractCommonDAO.class.getName());

	/** Default UID serial version. */
	private static final long serialVersionUID = -725477241919501850L;

	/**
	 * Recupera a classe da entidade.
	 * @return Class
	 */
	protected abstract Class<E> getEntityClass();

	/**
	 * Recupera a entityManager.
	 * @return {@link EntityManager}
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * Remove a entidade do banco de dados.
	 * @param entity Entidade a ser removida
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(E entity) throws CommonException {
		try {
			getEntityManager().remove(this.getEntityManager().getReference(entity.getClass(), entity.getId()));
			getEntityManager().flush();
		} catch (PersistenceException ex) {
			this.logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
			throw new CommonException("MESSAGE_ERROR_ENTITY_HAS_RELATIONSHIPS", ex);
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
		}
	}

	/**
	 * {@inheritDoc}.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteAll() throws CommonException{
		try {
			StringBuilder jpaQl = new StringBuilder();
			jpaQl.append(" DELETE ");
			jpaQl.append(getEntityClass().getName());
			jpaQl.append(" entity ");

			this.getEntityManager().createQuery(jpaQl.toString()).executeUpdate();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
		}

	}

	/**
	 * Delete the entity by id
	 * @param id Identification
	 * @throws CommonException When exceptions occur
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteById(Integer id) throws CommonException {
		E obj = getEntityManager().find(getEntityClass(), id);
		if (obj!= null){
			this.delete(obj);
		}
	}

	/**
	 * {@inheritDoc}.
	 */
	/**
	 * Delete the entities by Id list
	 * @param listId List of entities identification 
	 * @throws CommonException When exceptions occur
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteByIds(List<Integer> listId) throws CommonException {
		if (listId!= null && listId.size()>0){
			for (Integer id : listId) {
				this.deleteById(id);
			}
		}
	}

	/**
	 * Find the entities that the id is present in Id`s list
	 * @param listId List of identifications
	 * @return List of entities
	 * @throws CommonException When exceptions occur
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<E> findByIds(List<Integer> listId) throws CommonException {
		if (listId!= null && listId.size()>0){
			try {
				StringBuilder jpaQl = new StringBuilder();
				jpaQl.append(" SELECT entity FROM ");
				jpaQl.append(getEntityClass().getName());
				jpaQl.append(" entity WHERE entity.id in (:listId) ");
				Query query = this.getEntityManager().createQuery(jpaQl.toString());
				query.setParameter("listId", listId);
				List<E> list = query.getResultList();
				return list;
			} catch (Exception e) {
				this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
				throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
			}
		}
		return new ArrayList<E>();
	}

	/**
	 * Return all entities
	 * @return List of all entities
	 * @throws CommonException When exceptions occur
	 */
	public List<E> findAll() throws CommonException {
		return this.findAll(null);
	}

	/**
	 * Return all entities ordered by param orderBy
	 * @param orderBy Column used to order the result list
	 * @return LIst of Entities ordered by column orderBy
	 * @throws CommonException When exceptions occur 
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<E> findAll(String orderBy) throws CommonException {
		try {
			StringBuilder jpaQl = new StringBuilder();
			jpaQl.append("SELECT entity FROM ");
			jpaQl.append(getEntityClass().getName());
			jpaQl.append(" entity ");
			jpaQl.append(" WHERE 1 = 1 ");
			if(orderBy != null){
				jpaQl.append(" ORDER BY entity.");
				jpaQl.append(orderBy);
			}
			Query query = this.getEntityManager().createQuery(jpaQl.toString());
			List<E> list = query.getResultList();
			return list;
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
		}
	}

	/**
	 * Find the entity by name attribute
	 * @param name Field Name
	 * @return E entity
	 * @throws CommonException When exceptions occur
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public E findByName( String name) throws CommonException {
		try {
			StringBuilder jpaQl = new StringBuilder();
			jpaQl.append(" SELECT entity FROM ");
			jpaQl.append(getEntityClass().getName());
			jpaQl.append(" entity WHERE entity.name = :name ");
			Query query = this.getEntityManager().createQuery(jpaQl.toString());
			query.setParameter("name", name);
			List<E> list = query.getResultList();
			if(list.size() > 0){
				return (E) list.get(0);
			}
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
		}
		return null;
	}

	/**
	 * {@inheritDoc}.
	 */
	public void saveOrUpdateAll(List<E> objList) throws CommonException {
		try {
			EntityManager em = getEntityManager();
			for (E entity : objList) {
				em.merge(entity);
			}
			this.getEntityManager().flush();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			this.getEntityManager().getTransaction().rollback();
			throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
		}
	}

	/**
	 * {@inheritDoc}.
	 */
	public E saveOrUpdate(E entity) throws CommonException {
		E newObj = null;
		try {
			newObj = this.getEntityManager().merge(entity);
			this.getEntityManager().flush();
		} catch (Exception e) {
			this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
		}
		return newObj;
	}

	/**
	 * {@inheritDoc}.
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public E findById(Integer id) throws CommonException {
		try{
			StringBuilder jpaQl = new StringBuilder();
			jpaQl.append(" SELECT entity FROM ");
			jpaQl.append(getEntityClass().getName());
			jpaQl.append(" entity WHERE entity.id= :ID ");
			Query query = this.getEntityManager().createQuery(jpaQl.toString());
			query.setParameter("ID", id);
			List<E> list = query.getResultList();
			if(list.size() > 0){
				return (E) list.get(0);
				}
			} catch (Exception e) {
				this.logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
				throw new CommonException("MESSAGE_INTERNAL_ERROR", e, "DAO-INTERNAL_SERVER_ERROR", "Ocorreu um erro na requisição - CDAO0001");
			}
		return null;
	}

}

