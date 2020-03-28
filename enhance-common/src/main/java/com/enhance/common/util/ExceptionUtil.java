package com.enhance.common.util;

import com.enhance.common.exception.BusinessAssertionException;
import com.enhance.common.exception.InternalAssertionException;

public class ExceptionUtil {
	private ExceptionUtil() {
		throw new IllegalStateException("Constant class");
	}

	public static boolean isInternalException(Exception e) {
		return e instanceof InternalAssertionException;
	}

	public static boolean isBusinessException(Exception e) {
		return e instanceof BusinessAssertionException;
	}

	public static <T> void isException(T e) throws Exception {
		if (e instanceof Exception) {
			throw (Exception) e;
		}
	}

}
