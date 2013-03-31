/**
 * 
 */
package com.nebulent.cep.repository;

/**
 * @author Max Fedorov
 *
 */
public class RepositoryException extends RuntimeException {

	/**/
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public RepositoryException() {
		super();
	}
	
	/**
	 * @param msg
	 */
	public RepositoryException(String msg) {
		super(msg);
	}
	
	/**
	 * @param throwable
	 */
	public RepositoryException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * @param msg
	 * @param throwable
	 */
	public RepositoryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
	
}
