package org.enhance.common.util;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import org.enhance.common.exception.InternalAssertionException;

public class InternalAssertion {

	private InternalAssertion() {
		throw new IllegalStateException("Constant class");
	}

	public static void isNegative(short value, String message) {
		if (!Detect.isNegative(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isPositive(short value, String message) {
		if (!Detect.isPositive(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isNegative(int value, String message) {
		if (!Detect.isNegative(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isPositive(int value, String message) {
		if (!Detect.isPositive(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isNegative(long value, String message) {
		if (!Detect.isNegative(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isPositive(long value, String message) {
		if (!Detect.isPositive(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isNegative(float value, String message) {
		if (!Detect.isNegative(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isPositive(float value, String message) {
		if (!Detect.isPositive(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isNegative(double value, String message) {
		if (!Detect.isNegative(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isPositive(double value, String message) {
		if (!Detect.isPositive(value)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEmpty(String[] string, String message) {
		if (!Detect.notEmpty(string)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEmpty(long[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEmpty(byte[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEmpty(short[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEmpty(int[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEmpty(List<?> list, String message) {
		if (!Detect.notEmpty(list)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEmpty(String string, String message) {
		if (!Detect.notEmpty(string)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isEmpty(String[] string, String message) {
		if (Detect.notEmpty(string)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isEmpty(long[] values, String message) {
		if (Detect.notEmpty(values)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isEmpty(List<?> list, String message) {
		if (Detect.notEmpty(list)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isEmpty(String string, String message) {
		if (Detect.notEmpty(string)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void onlyOne(List<?> list, String message) {
		if (!Detect.onlyOne(list)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isFalse(boolean expression, String message) {
		if (expression) {
			throw new InternalAssertionException(message);
		}
	}

	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new InternalAssertionException(message);
		}
	}

	public static void isNull(Object object, String message) {
		if (Detect.notNull(object)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notNull(Object object, String message) {
		if (Detect.isNull(object)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void equals(Object obj1, Object obj2, String message) {
		if (!Detect.equals(obj1, obj2)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notEquals(Object obj1, Object obj2, String message) {
		if (Detect.equals(obj1, obj2)) {
			throw new InternalAssertionException(message);
		}
	}

	public static void notNullAndNotEquals(Object obj1, Object obj2, String message) {
		if (Detect.isNull(obj1) || Detect.isNull(obj2) || Detect.equals(obj1, obj2)) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value?????????????????? NumberUtils.isDigits??? "0512" -> true; "5.96" -> fasle; "s5" -> false;
	 *
	 * @param value
	 * @param message
	 */
	public static void isDigits(String value, String message) {
		if (!NumberUtils.isDigits(value)) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value??????????????????????????? NumberUtils.isNumber: "5.96" -> true; "5s" -> false; "00644" -> true;
	 *
	 * @param value
	 * @param message
	 */
	public static void isNumber(String value, String message) {
		if (!NumberUtils.isCreatable(value)) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value?????????????????????length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void greaterThanLength(String value, int length, String message) {
		if (value.length() <= length) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value???????????????????????????length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void greaterThanOrEqualLength(String value, int length, String message) {
		if (value.length() < length) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value?????????????????????length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void lessThanLength(String value, int length, String message) {
		if (value.length() >= length) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value???????????????????????????length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void lessThanOrEqualLength(String value, int length, String message) {
		if (value.length() > length) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value???????????????????????????
	 *
	 * @param value
	 * @param min
	 * @param max
	 * @param message
	 */
	public static void betweenLength(String value, int min, int max, String message) {
		if (min >= max) {
			throw new InternalAssertionException("?????????: " + min + "???????????????????????????: " + max);
		}
		if (value.length() <= min || value.length() >= max) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value???????????????????????????
	 *
	 * @param value
	 * @param min
	 * @param max
	 * @param message
	 */
	public static void betweenOrEqualLength(String value, int min, int max, String message) {
		if (min > max) {
			throw new InternalAssertionException("?????????: " + min + "?????????????????????: " + max);
		}
		if (value.length() < min || value.length() > max) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????value?????????????????????????????????length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void lessThanOrEqualLengthInByte(String value, int length, String message) {
		if (value.getBytes().length > length) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????str??????????????????keys??????????????????????????????
	 *
	 * @param str
	 * @param keys
	 * @param message
	 */
	public static void notContains(String str, String[] keys, String message) {
		if (Detect.isNull(str)) {
			throw new InternalAssertionException(message);
		}
		for (int i = 0; i < keys.length; i++) {
			if (str.contains(keys[i])) {
				throw new InternalAssertionException(message);
			}
		}
	}

	/**
	 * ??????str??????????????????????????????????????? ??????str?????????????????????????????????????????????????????????
	 *
	 * @param value
	 * @param enums
	 * @param message
	 */
	public static void in(String str, String[] enums, String message) {
		if (!Detect.notEmpty(enums)) {
			throw new InternalAssertionException(message + "?????????????????????");
		}
		boolean match = false;
		for (int i = 0; i < enums.length; i++) {
			if (Detect.notNull(enums[i]) && enums[i].equals(str)) {
				match = true;
				break;
			}
		}
		if (!match) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ??????str??????key????????????ok???????????????
	 *
	 * @param str
	 * @param key
	 * @param message
	 */
	public static void endsWith(String str, String key, String message) {
		if (Detect.notNull(str) && str.endsWith(key)) {
			return;
		} else {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ?????????????????????
	 *
	 * @param value
	 * @param regexp
	 * @param message
	 */
	public static void matchRegexp(String value, String regexp, String message) {
		if (!value.matches(regexp)) {
			throw new InternalAssertionException(message);
		}
	}

	/**
	 * ?????????????????????
	 *
	 * @param value
	 * @param message
	 */
	public static void isDecimal(String value, String message) {
		matchRegexp(value, "\\d+\\.\\d+$|-\\d+\\.\\d+$", message);
	}

}
