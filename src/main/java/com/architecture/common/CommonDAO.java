package com.architecture.common;

import java.math.BigInteger;
import java.util.List;

import com.architecture.exception.CommonException;

/**
 * Define o contrato das operações básicas disponíveis
 * @author daniel.menezes
 */
public interface CommonDAO<E extends CommonBean> {

	/**
	 * Encontra um entidade pelo identificador
	 * @param id Identificador da entidade
	 * @return entidade
	 */
	public E findById(BigInteger id) throws CommonException;
	/**
	 * Remove a entidade
	 * @param E Entity a ser removida
	 * @return <tt>TRUE</tt> caso a entidade tenha sido removida com sucesso,<br><tt>FALSE</tt> caso contrário.
	 */
	public void delete(E entity) throws CommonException;
	/**
	 * Cria ou atualiza a entidade passada como parâmetro
	 * @param bean Entidade a ser salva/ atualizada
	 * @return Entidade persistida na base de dados
	 */
	public E saveOrUpdate(E bean) throws CommonException;
	/**
	 * Cria ou atualiza a lista de entidade passadas como parâmetro
	 * @param beanList Lista de entidades
	 * @return Entidade persistida na base de dados
	 */
	public void saveOrUpdateAll(List<E> beanList) throws CommonException;

}
