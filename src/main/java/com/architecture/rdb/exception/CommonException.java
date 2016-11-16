package com.architecture.rdb.exception;

/**
 * Exceção generica.
 * @author Daniel Menezes <tt>daniel.afmenezes@gmail.com</tt>
 */
public class CommonException extends Exception {

	/** Serial UID version. */
	private static final long serialVersionUID = 2126695576475764800L;
	/** Chave de tradução. */
	private String translateKey;
	/** see {@link Throwable}. */
	private Throwable throwable;
	/** Code identifier of the error */
	private String errorCode;
	/** Message identifier of the error */
	private String errorMessage;
	
	/**
	 * Construtor default. 
	 */
	public CommonException(){}
	
	/**
	 * Construtor com parâmetros.
	 * @param translateKey
	 */
	public CommonException (String translateKey) {
		this.translateKey = translateKey;
	}
	
	/**
	 * Construtor com parâmetros.
	 * @param errorCode
	 * @param errorMessage
	 */
	public CommonException (String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * Construtor com parâmetros.
	 * @param translateKey
	 * @param throwable
	 */
	public CommonException (String translateKey, Throwable throwable, String errorCode, String errorMessage) {
		this.translateKey = translateKey;
		this.throwable = throwable;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * Construtor com parâmetros.
	 * @param throwable
	 */
	public CommonException (Throwable throwable) {
		this.throwable = throwable;
	}
	
	/**
	 * Construtor com parâmetros.
	 * @param translateKey
	 * @param throwable
	 */
	public CommonException (String translateKey, Throwable throwable) {
		this.translateKey = translateKey;
		this.throwable = throwable;
	}

	/**
	 * Getter da chave de tradução.
	 * @return the translateKey
	 */
	public String getTranslateKey() {
		return translateKey;
	}

	/**
	 * Setter da chave de tradução.
	 * @param translateKey the translateKey to set
	 */
	public void setTranslateKey(String translateKey) {
		this.translateKey = translateKey;
	}

	/**
	 * Getter do lançador da exceção.
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * Setter do lançador da exceção.
	 * @param throwable the throwable to set
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
