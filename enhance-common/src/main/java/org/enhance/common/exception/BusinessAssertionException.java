package org.enhance.common.exception;

public class BusinessAssertionException extends EnhanceRuntimeException {

	private static final long serialVersionUID = 3327851222968688650L;

	public BusinessAssertionException() {
		super();
	}

	public BusinessAssertionException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessAssertionException(String message) {
		super(message);
	}

	public BusinessAssertionException(Throwable cause) {
		super(cause);
	}

}
