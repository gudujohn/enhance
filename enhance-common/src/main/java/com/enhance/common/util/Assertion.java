package com.enhance.common.util;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

public class Assertion extends Assert {

	private Assertion() {
		throw new IllegalStateException("Constant class");
	}

	public static void isNegative(short value, String message) {
		if (!Detect.isNegative(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isPositive(short value, String message) {
		if (!Detect.isPositive(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isNegative(int value, String message) {
		if (!Detect.isNegative(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isPositive(int value, String message) {
		if (!Detect.isPositive(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isNegative(long value, String message) {
		if (!Detect.isNegative(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isPositive(long value, String message) {
		if (!Detect.isPositive(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isNegative(float value, String message) {
		if (!Detect.isNegative(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isPositive(float value, String message) {
		if (!Detect.isPositive(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isNegative(double value, String message) {
		if (!Detect.isNegative(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void isPositive(double value, String message) {
		if (!Detect.isPositive(value)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(String[] string, String message) {
		if (!Detect.notEmpty(string)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(long[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(byte[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(short[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(int[] values, String message) {
		if (!Detect.notEmpty(values)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(List<?> list, String message) {
		if (!Detect.notEmpty(list)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(String string, String message) {
		if (!Detect.notEmpty(string)) {
			throw new RuntimeException(message);
		}
	}

	public static void isEmpty(String[] string, String message) {
		if (Detect.notEmpty(string)) {
			throw new RuntimeException(message);
		}
	}

	public static void isEmpty(long[] values, String message) {
		if (Detect.notEmpty(values)) {
			throw new RuntimeException(message);
		}
	}

	public static void isEmpty(List<?> list, String message) {
		if (Detect.notEmpty(list)) {
			throw new RuntimeException(message);
		}
	}

	public static void isEmpty(String string, String message) {
		if (Detect.notEmpty(string)) {
			throw new RuntimeException(message);
		}
	}

	public static void onlyOne(List<?> list, String message) {
		if (!Detect.onlyOne(list)) {
			throw new RuntimeException(message);
		}
	}

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new RuntimeException(message);
		}
	}

	public static void isFalse(boolean expression, String message) {
		if (expression) {
			throw new RuntimeException(message);
		}
	}

	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new RuntimeException(message);
		}
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new RuntimeException(message);
		}
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new RuntimeException(message);
		}
	}

	public static void equals(Object obj1, Object obj2, String message) {
		if (!Detect.equals(obj1, obj2)) {
			throw new RuntimeException(message);
		}
	}

	public static void notEquals(Object obj1, Object obj2, String message) {
		if (Detect.equals(obj1, obj2)) {
			throw new RuntimeException(message);
		}
	}

	public static void notNullAndNotEquals(Object obj1, Object obj2, String message) {
		if (null == obj1 || null == obj2 || Detect.equals(obj1, obj2)) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value是否是纯数字 NumberUtils.isDigits： "0512" -> true; "5.96" -> fasle; "s5" -> false;
	 *
	 * @param value
	 * @param message
	 */
	public static void isDigits(String value, String message) {
		if (!NumberUtils.isDigits(value)) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value是否是数字（小数） NumberUtils.isNumber: "5.96" -> true; "5s" -> false; "00644" -> true;
	 *
	 * @param value
	 * @param message
	 */
	public static void isNumber(String value, String message) {
		if (!NumberUtils.isCreatable(value)) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value的长度是否大于length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void greaterThanLength(String value, int length, String message) {
		if (value.length() <= length) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value的长度是否大于等于length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void greaterThanOrEqualLength(String value, int length, String message) {
		if (value.length() < length) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value的长度是否小于length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void lessThanLength(String value, int length, String message) {
		if (value.length() >= length) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value的长度是否小于等于length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void lessThanOrEqualLength(String value, int length, String message) {
		if (value.length() > length) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value长度是否在开区间内
	 *
	 * @param value
	 * @param min
	 * @param max
	 * @param message
	 */
	public static void betweenLength(String value, int min, int max, String message) {
		if (min >= max) {
			throw new RuntimeException("最小值: " + min + "不能大于等于最大值: " + max);
		}
		if (value.length() <= min || value.length() >= max) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value长度是否在闭区间内
	 *
	 * @param value
	 * @param min
	 * @param max
	 * @param message
	 */
	public static void betweenOrEqualLength(String value, int min, int max, String message) {
		if (min > max) {
			throw new RuntimeException("最小值: " + min + "不能大于最大值: " + max);
		}
		if (value.length() < min || value.length() > max) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 校验value的字节长度是否小于等于length
	 *
	 * @param value
	 * @param length
	 * @param message
	 */
	public static void lessThanOrEqualLengthInByte(String value, int length, String message) {
		if (value.getBytes().length > length) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 判断str不能包含数组keys中的字符串，否则抛错
	 *
	 * @param str
	 * @param keys
	 * @param message
	 */
	public static void notContains(String str, String[] keys, String message) {
		if (null == str) {
			throw new RuntimeException(message);
		}
		for (int i = 0; i < keys.length; i++) {
			if (str.contains(keys[i])) {
				throw new RuntimeException(message);
			}
		}
	}

	/**
	 * 判断str取值是否在字符串数组列表中 如果str不匹配字符串数组中任意一个字符串则抛错
	 *
	 * @param value
	 * @param enums
	 * @param message
	 */
	public static void in(String str, String[] enums, String message) {
		if (!Detect.notEmpty(enums)) {
			throw new RuntimeException(message + "，枚举集合为空");
		}
		boolean match = false;
		for (int i = 0; i < enums.length; i++) {
			if (null != enums[i] && enums[i].equals(str)) {
				match = true;
				break;
			}
		}
		if (!match) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 如果str是以key结束的则ok，否则报错
	 *
	 * @param str
	 * @param key
	 * @param message
	 */
	public static void endsWith(String str, String key, String message) {
		if (null != str && str.endsWith(key)) {
			return;
		} else {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 匹配正则表达式
	 *
	 * @param value
	 * @param regexp
	 * @param message
	 */
	public static void matchRegexp(String value, String regexp, String message) {
		if (!value.matches(regexp)) {
			throw new RuntimeException(message);
		}
	}

	/**
	 * 判断是否为小数
	 *
	 * @param value
	 * @param message
	 */
	public static void isDecimal(String value, String message) {
		matchRegexp(value, "\\d+\\.\\d+$|-\\d+\\.\\d+$", message);
	}

}
