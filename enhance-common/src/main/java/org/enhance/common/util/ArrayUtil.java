package org.enhance.common.util;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.enhance.common.exception.InternalAssertionException;

public class ArrayUtil {

	private ArrayUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static final String COMMA = ",";
	public static final String DELIMITER = COMMA;
	public static final String DEFAULT_DELIMITER = COMMA;

	public static List<long[]> grouping(long[] values, int groupSize) {
		if (ArrayUtils.isEmpty(values)) {
			return new ArrayList<>();
		}
		InternalAssertion.isPositive(groupSize, "divideSize must be bigger than 0");
		int len = values.length;
		int groupLength = len / groupSize + ((len % groupSize) > 0 ? 1 : 0);
		List<long[]> longArryGroup = new LinkedList<>();
		long[] valueArray = null;
		for (int i = 0; i < groupLength; i++) {
			int arrayLength = (i < groupLength - 1 || len % groupSize == 0) ? groupSize : (len % groupSize);

			valueArray = new long[arrayLength];
			for (int j = 0; j < arrayLength; j++) {
				valueArray[j] = values[i * groupSize + j];
			}
			longArryGroup.add(valueArray);
		}
		return longArryGroup;
	}

	public static String join(long[] values) {
		return join(values, DEFAULT_DELIMITER);
	}

	public static String join(long[] values, String delimiter) {
		return StringUtils.join(ArrayUtils.toObject(values), delimiter);
	}

	public static String join(short[] values) {
		return join(values, DEFAULT_DELIMITER);
	}

	public static String join(short[] values, String delimiter) {
		return StringUtils.join(ArrayUtils.toObject(values), delimiter);
	}

	public static String join(String[] values) {
		return StringUtils.join(values, DELIMITER);
	}

	public static long[] escapeDuplication(long[] values) {
		long[] result = ArrayUtils.EMPTY_LONG_ARRAY;
		if (ArrayUtils.isNotEmpty(values)) {
			for (long val : values) {
				if (!ArrayUtils.contains(result, val)) {
					result = ArrayUtils.add(result, val);
				}
			}
		}
		return result;
	}

	public static short[] safeArray(short[] values) {
		if (null == values) {
			values = new short[0];
		}
		return values;
	}

	public static int[] safeArray(int[] values) {
		if (null == values) {
			values = new int[0];
		}
		return values;
	}

	public static long[] safeArray(long[] values) {
		if (null == values) {
			values = new long[0];
		}
		return values;
	}

	public static double[] safeArray(double[] values) {
		if (null == values) {
			values = new double[0];
		}
		return values;
	}

	/**
	 * 判断数组是否完全包含在另一数组中
	 *
	 * @param array
	 * @param arrayToFind 被包含的数组[可能存在重复元素，所以可能比array要长]
	 * @return
	 */
	public static boolean contains(String[] array, String[] arrayToFind) {
		if (array == null || arrayToFind == null) {
			return false;
		}
		for (int i = 0; i < arrayToFind.length; i++) {
			if (!ArrayUtils.contains(array, arrayToFind[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean containsAny(long[] array, long[] arrayToFind) {
		if (Detect.isEmpty(array) || Detect.isEmpty(arrayToFind)) {
			return false;
		}
		for (long l : arrayToFind) {
			if (ArrayUtils.contains(array, l)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsIgnoreCase(String[] array, String str) {
		if (Detect.isEmpty(array) || Detect.isEmpty(str)) {
			return false;
		}
		for (String tmp : array) {
			if (tmp.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] concat(T[] first, T[]... others) {
		if (Detect.isEmpty(first) && Detect.isEmpty(others)) {
			return null;
		}

		if (Detect.notEmpty(first) && Detect.isEmpty(others)) {
			return first;
		}

		if (Detect.isEmpty(first) && Detect.notEmpty(others) && others.length == 1) {
			return others[0];
		}

		T[] results = Detect.notEmpty(first) ? first : null;

		for (T[] other : others) {
			if (results == null) {
				results = other;
			} else {
				results = ArrayUtils.addAll(results, other);
			}
		}

		return results;
	}

	public static long[] concat(long[] first, long[]... others) {
		if (Detect.isEmpty(first) && Detect.isEmpty(others)) {
			return null;
		}

		if (Detect.notEmpty(first) && Detect.isEmpty(others)) {
			return first;
		}

		if (Detect.isEmpty(first) && Detect.notEmpty(others) && others.length == 1) {
			return others[0];
		}

		long[] results = Detect.notEmpty(first) ? first : null;

		for (long[] other : others) {
			if (results == null) {
				results = other;
			} else {
				results = ArrayUtils.addAll(results, other);
			}
		}

		return results;
	}

	public static short[] toShortArray(String value) {
		return toShortArray(value, DELIMITER);
	}

	public static short[] toShortArray(String value, char delimiter) {
		if (!Detect.notEmpty(value)) {
			return new short[0];
		}
		String[] values = StringUtils.split(value, delimiter);

		short[] shortValues = new short[values.length];
		for (int i = 0; i < values.length; i++) {
			shortValues[i] = Short.valueOf(values[i]);
		}
		return shortValues;
	}

	public static short[] toShortArray(String value, String delimiter) {
		if (!Detect.notEmpty(value)) {
			return new short[0];
		}
		String[] values = StringUtils.split(value, delimiter);

		short[] shortValues = new short[values.length];
		for (int i = 0; i < values.length; i++) {
			if (Detect.notEmpty(values[i])) {
				shortValues[i] = Short.valueOf(values[i].trim());
			}
		}
		return shortValues;
	}

	public static Object[] toObjectArray(String[] arr) {
		if (Detect.notEmpty(arr)) {
			return Arrays.asList(arr).toArray(new Object[arr.length]);
		}
		return new Object[0];
	}

	public static long[] toLongArray(String value) {
		return toLongArray(value, DELIMITER);
	}

	public static long[] toLongArray(String value, char delimiter) {
		if (!Detect.notEmpty(value)) {
			return new long[0];
		}
		String[] values = StringUtils.split(value, delimiter);

		long[] longValues = new long[values.length];
		for (int i = 0; i < values.length; i++) {
			longValues[i] = Long.valueOf(values[i]);
		}
		return longValues;
	}

	public static long[] toLongArray(String value, String delimiter) {
		if (!Detect.notEmpty(value)) {
			return new long[0];
		}
		String[] values = StringUtils.split(value, delimiter);

		long[] longValues = new long[values.length];
		for (int i = 0; i < values.length; i++) {
			if (Detect.notEmpty(values[i])) {
				longValues[i] = Long.valueOf(values[i].trim());
			}
		}
		return longValues;
	}

	public static long[] toLongArray(Collection<Long> collection) {
		if (Detect.notEmpty(collection)) {
			return ArrayUtils.toPrimitive(collection.toArray(new Long[0]), 0);
		}
		return new long[0];
	}

	public static long[] remove(long[] originalArr, long[] needDeleteArr) {
		if (Detect.isEmpty(originalArr) || Detect.isEmpty(needDeleteArr)) {
			return originalArr;
		}
		long[] result = ArrayUtils.EMPTY_LONG_ARRAY;
		for (long l : originalArr) {
			if (!ArrayUtils.contains(needDeleteArr, l)) {
				result = ArrayUtils.add(result, l);
			}
		}
		return result;
	}

	public static String[] remove(String[] originalArr, String[] needDeleteArr) {
		if (Detect.isEmpty(originalArr) || Detect.isEmpty(needDeleteArr)) {
			return originalArr;
		}
		List<String> list = new ArrayList<>();
		for (String l : originalArr) {
			if (!ArrayUtils.contains(needDeleteArr, l)) {
				list.add(l);
			}
		}
		return list.toArray(new String[list.size()]);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Object> T convertToObject(byte[] bytes) {
		T t = null;
		if (bytes != null) {
			try (ByteArrayInputStream byteArrayInputputStream = new ByteArrayInputStream(bytes); ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputputStream)) {
				t = (T) objectInputStream.readObject();
			} catch (Exception ex) {
				throw new InternalAssertionException(ex);
			}
		}
		return t;
	}

	/**
	 * 把一个数组拆分为多个数组
	 *
	 * @param array     要拆分的数组
	 * @param arraySize 每个数组的大小
	 * @return
	 */
	public static String[][] splitArray(String[] array, int arraySize) {
		if (Detect.notEmpty(array)) {
			if (1 > arraySize) {
				throw new IllegalArgumentException("数组大小不能小于1");
			}
			int blockCount = array.length / arraySize;
			if (0 < array.length % arraySize) {
				blockCount++;
			}
			String[][] result = new String[blockCount][];

			for (int i = 0; i < blockCount; i++) {
				int bottom = i * arraySize;
				int top = bottom + arraySize - 1;
				top = top < array.length - 1 ? top : array.length - 1;

				String[] tmpArray = new String[top - bottom + 1];

				for (int j = bottom; j <= top; j++) {
					tmpArray[j - bottom] = array[j];
				}

				result[i] = tmpArray;
			}

			return result;
		} else {
			throw new IllegalArgumentException("数组不能为空");
		}
	}

	public static String[] toStringArray(long[] array) {
		if (Detect.notEmpty(array)) {
			int len = array.length;
			String[] result = new String[len];
			for (int i = 0; i < len; i++) {
				result[i] = String.valueOf(array[i]);
			}
			return result;
		}
		return new String[0];
	}

	public static int sum(int[] array) {
		int sum = 0;
		if (Detect.notEmpty(array)) {
			for (int i : array) {
				sum += i;
			}
		}
		return sum;
	}

	public static boolean ignoreCaseContains(String[] array, String str) {
		if (Detect.isEmpty(array) || Detect.isEmpty(str)) {
			return false;
		}
		for (String tmp : array) {
			if (tmp.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	public static String[] merger(String[] arr1, String[] arr2) {
		if (Detect.isEmpty(arr1) && Detect.isEmpty(arr2)) {
			return new String[0];
		}

		Set<String> set = new HashSet<>();
		if (Detect.notEmpty(arr1)) {
			for (String s : arr1) {
				set.add(s);
			}
		}

		if (Detect.notEmpty(arr2)) {
			for (String s : arr2) {
				set.add(s);
			}
		}

		return set.toArray(new String[set.size()]);
	}

	public static String join(Long[] values, String delimiter) {
		return StringUtils.join(values, delimiter);
	}
}
