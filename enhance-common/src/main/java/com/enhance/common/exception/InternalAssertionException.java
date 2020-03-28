package com.enhance.common.exception;

public class InternalAssertionException extends EnhanceRuntimeException {

	private static final long serialVersionUID = -8198380253483658320L;

	public InternalAssertionException() {
		super();
	}

	public InternalAssertionException(String s) {
		super(s);
	}

	public InternalAssertionException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public InternalAssertionException(Throwable throwable) {
		super(throwable);
	}

}
