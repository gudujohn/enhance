package org.enhance.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.enhance.common.exception.InternalAssertionException;

public class ListUtil {

	private ListUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String COMMA = ",";

	public static long[] toLongArray(Collection<Long> collection) {
		if (Detect.notEmpty(collection)) {
			// 为什么从原来的Long[] array = collection.toArray(new long[0]);更改为for循环，且用Object作为对象类型?
			// Flex那边ActionScript使用Number()传参过来，java后台接收到类型虽然是List<long>，但实际集合里的类型却可能是Integer，原来的写法就会报错。
			long[] array = new long[0];
			for (Object l : collection) {
				array = ArrayUtils.add(array, ObjectUtil.asPrimitiveLong(l));
			}
			return array;
		}
		return ArrayUtils.EMPTY_LONG_ARRAY;
	}

	public static String[] toStringArray(Collection<Object> collection) {
		if (Detect.notEmpty(collection)) {
			String[] array = new String[0];
			for (Object obj : collection) {
				array = ArrayUtils.add(array, ObjectUtil.asString(obj));
			}
			return array;
		}
		return ArrayUtils.EMPTY_STRING_ARRAY;
	}

	public static <T> List<List<T>> grouping(List<T> values, int groupSize) {
		if (Detect.notEmpty(values)) {

			InternalAssertion.isPositive(groupSize, "divideSize must be bigger than 0");

			int groupLength = values.size() / groupSize + ((values.size() % groupSize) > 0 ? 1 : 0);

			List<List<T>> longArryGroup = new LinkedList<>();
			for (int i = 0; i < groupLength; i++) {
				int arrayLength = (i < groupLength - 1 || values.size() % groupSize == 0) ? groupSize : (values.size() % groupSize);

				List<T> valueArray = new ArrayList<>();
				for (int j = 0; j < arrayLength; j++) {
					valueArray.add(values.get(i * groupSize + j));
				}
				longArryGroup.add(valueArray);
			}

			return longArryGroup;
		}
		return null;
	}

	/**
	 * Remove the duplicate element in List according to the specified keys in List bean and return a new list.</br>
	 * 
	 * If the parameters are empty or exception occurred, original list will be returned.
	 * 
	 * @param list   To be processed list
	 * @param fields The fields in List bean as keys
	 * @return
	 */
	public static <E> List<E> escapeDuplication(List<E> list, String... fields) {
		if (Detect.isEmpty(list) || Detect.isEmpty(fields)) {
			return list;
		}

		for (String field : fields) {
			InternalAssertion.notEmpty(field, "field not found, fields=" + ArrayUtil.join(fields));
		}

		List<E> returnValue = new ArrayList<>();
		Set<String> keySet = new HashSet<>();

		for (E t : list) {
			StringBuilder hashCodeKey = new StringBuilder();
			for (String field : fields) {
				try {
					hashCodeKey.append(BeanUtils.getProperty(t, field));
					hashCodeKey.append(COMMA);
				} catch (Exception e) {
					throw new InternalAssertionException(e);
				}
			}
			if (!keySet.contains(hashCodeKey.toString())) {
				keySet.add(hashCodeKey.toString());
				returnValue.add(t);
			}
		}
		return returnValue;
	}

	public static <E> List<E> escapeEmpty(List<E> list) {
		if (Detect.notEmpty(list)) {
			return list;
		}
		return null;
	}

	public static <E> List<E> safeList(List<E> list) {
		if (null == list) {
			list = new ArrayList<>();
		}
		return list;
	}

	public static <E> E firstOne(List<E> list) {
		if (Detect.notEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	public static <E> E firstOne(Collection<E> collection) {
		return null == collection ? null : collection.iterator().next();
	}

	public static <E> E lastOne(List<E> list) {
		if (Detect.notEmpty(list)) {
			return list.get(list.size() - 1);
		}
		return null;
	}

	public static <E> List<E> unmodifiableList(List<E> list) {
		if (Detect.notEmpty(list)) {
			return Collections.unmodifiableList(list);
		}
		return Collections.emptyList();
	}

	public static Set<Long> toSet(List<Long> list) {
		Set<Long> set = new LinkedHashSet<>();
		if (Detect.notEmpty(list)) {
			for (long t : list) {
				set.add(t);
			}
		}
		return set;
	}

	public static List<Long> distinct(List<Long> list) {
		List<Long> newList = new ArrayList<>();
		if (Detect.notEmpty(list)) {
			for (long id : list) {
				if (!newList.contains(id)) {
					newList.add(id);
				}
			}
		}
		return newList;
	}

	public static List<Object> asObjectList(List<String> ids) {
		if (Detect.notEmpty(ids)) {
			List<Object> objs = new ArrayList<>();
			for (String id : ids) {
				objs.add(id);
			}
			return objs;
		}
		return new ArrayList<>();
	}

	public static <E> List<E> addAll(List<E> originalCollection, int index, List<E> addedCollection) {
		if (originalCollection == null || Detect.isEmpty(addedCollection)) {
			return originalCollection;
		}

		int originalSize = originalCollection.size();
		if (originalSize < index) {
			for (int i = originalSize; i < index; i++) {
				originalCollection.add(null);
			}
		}
		originalCollection.addAll(index, addedCollection);

		for (Iterator<E> it = originalCollection.iterator(); it.hasNext();) {
			E item = it.next();
			if (item == null) {
				it.remove();
			}
		}

		return originalCollection;
	}

	public static <T> long[] getLongProperties(List<T> list, String propertyName) {
		if (Detect.notEmpty(list)) {
			long[] resultArray = new long[0];
			for (T t : list) {
				long foreignKeyValue = getLongProperty(t, propertyName);
				if (Detect.isPositive(foreignKeyValue) && !ArrayUtils.contains(resultArray, foreignKeyValue)) {
					resultArray = ArrayUtils.add(resultArray, foreignKeyValue);
				}
			}
			return resultArray;
		}
		return null;
	}

	public static <T> long getLongProperty(T t, String propertyName) {
		Object foreignKeyObject;
		try {
			foreignKeyObject = PropertyUtils.getProperty(t, propertyName);
		} catch (Exception ex) {
			throw new InternalAssertionException("getProperty() error, class=" + t.getClass().getSimpleName() + ", propertyName=" + propertyName, ex);
		}
		return ObjectUtil.asPrimitiveLong(foreignKeyObject);
	}

}