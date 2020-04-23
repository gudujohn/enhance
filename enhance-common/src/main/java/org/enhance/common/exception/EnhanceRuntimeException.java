package org.enhance.common.exception;

public class EnhanceRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -5985428146066366070L;

	public EnhanceRuntimeException() {
		super();
	}

	public EnhanceRuntimeException(String s) {
		super(s);
	}

	public EnhanceRuntimeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public EnhanceRuntimeException(Throwable throwable) {
		super(throwable);
	}

}
