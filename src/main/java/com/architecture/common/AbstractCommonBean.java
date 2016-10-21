package com.architecture.common;

import java.io.Serializable;

/**
 * Implementa as funções básicas de um bean.
 * @author daniel.menezes
 */
public abstract class AbstractCommonBean implements CommonBean, Serializable {

	/** Generated Serial UID Version. */
	private static final long serialVersionUID = -2050248527788932359L;
	
	/** Atributo de identificação do bean. */
	protected Integer id;

	/**
	 * {@inheritDoc}
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Realiza a comparação básica sobre a identificação do bean.
	 * @param bean Bean a ser comparado
	 */
	public int compareTo(AbstractCommonBean bean) {
		if(this.id == null || bean.getId() == null) {
			return 1;
		}
		return id.compareTo(bean.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractCommonBean other = (AbstractCommonBean) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}