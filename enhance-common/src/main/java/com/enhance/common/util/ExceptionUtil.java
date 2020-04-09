package com.enhance.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.enhance.common.exception.BusinessAssertionException;
import com.enhance.common.exception.InternalAssertionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public static String toFormatDetailErrorMessage(Throwable throwable) {
		String detailMessage = toDetailErrorMessage(throwable);
		if (Detect.notEmpty(detailMessage)) {
			detailMessage = detailMessage.replaceAll("\r\n", "<br />");
		}
		return detailMessage;
	}

	private static String toDetailErrorMessage(Throwable throwable) {
		if (null != throwable) {
			try (StringWriter writer = new StringWriter();) {
				write(throwable, writer);
				return writer.toString();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	private static void write(Throwable throwable, StringWriter writer) {
		if (null != throwable) {
			PrintWriter printWriter = new PrintWriter(writer);
			throwable.printStackTrace(printWriter);
			printWriter.close();
			printWriter = null;
		}
	}

}
