package com.architecture.common;

import java.io.Serializable;
import java.math.BigInteger;


/**
 * Interface com o contrato de um bean.
 * @author Daniel Menezes <tt>daniel.afmenezes@gmail.com</tt>
 */
public interface CommonBean extends Serializable {
	
	/**
	 * Recupera o identificador da entidade.
	 * @return Identificador
	 */
	public BigInteger getId();
	
	/**
	 * Atribui o identificador da entidade.
	 * @param id Identificador
	 */
	public void setId(BigInteger id);
	
}