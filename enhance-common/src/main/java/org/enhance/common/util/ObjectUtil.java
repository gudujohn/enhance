package org.enhance.common.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.enhance.common.exception.InternalAssertionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtil extends ObjectUtils {

	public static final short INVALID_NUMBER_VALUE = 0;

	public static <T extends Object> byte[] convertToByteArray(T t) {
		byte[] bytes = null;
		if (t != null) {
			try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);) {
				objectOutputStream.writeObject(t);
				bytes = byteArrayOutputStream.toByteArray();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new InternalAssertionException(e);
			}
		}
		return bytes;
	}

	public static String asString(Object object) {
		return asString(object, null);
	}

	public static String asString(Object object, String defaultValue) {
		if (null != object) {
			return StringUtils.trim(String.valueOf(object));
		}
		return defaultValue;
	}

	public static String asTrimString(Object obj) {
		if (obj == null) {
			return null;
		}
		return ObjectUtil.asString(obj).trim();
	}

	public static byte asPrimitiveByte(Object value) {
		return asPrimitiveByte(value, (byte) INVALID_NUMBER_VALUE);
	}

	public static byte asPrimitiveByte(Object value, byte defaultValue) {
		String strValue = asTrimString(value);
		if (StringUtils.isNotBlank(strValue) && NumberUtils.isCreatable(strValue)) {
			return Byte.parseByte(strValue);
		}
		return defaultValue;
	}

	public static short asPrimitiveShort(Object value) {
		return asPrimitiveShort(value, INVALID_NUMBER_VALUE);
	}

	public static short asPrimitiveShort(Object value, short defaultValue) {
		String strValue = asTrimString(value);
		if (StringUtils.isNotBlank(strValue) && NumberUtils.isCreatable(strValue)) {
			return Short.valueOf(strValue);
		}
		return defaultValue;
	}

	public static int asPrimitiveInt(Object value) {
		return asPrimitiveInt(value, INVALID_NUMBER_VALUE);
	}

	public static int asPrimitiveInt(Object value, int defaultValue) {
		String strValue = asTrimString(value);
		if (StringUtils.isNotBlank(strValue) && NumberUtils.isCreatable(strValue)) {
			return Integer.valueOf(strValue);
		}
		return defaultValue;
	}

	public static long asPrimitiveLong(Object value) {
		return asPrimitiveLong(value, INVALID_NUMBER_VALUE);
	}

	public static long asPrimitiveLong(Object value, long defaultValue) {
		String strValue = asTrimString(value);
		if (StringUtils.isNotBlank(strValue) && NumberUtils.isDigits(strValue)) {
			return Long.valueOf(strValue);
		}
		return defaultValue;
	}

	public static boolean asPrimitiveBoolean(Object value) {
		return asPrimitiveBoolean(value, false);
	}

	public static boolean asPrimitiveBoolean(Object value, boolean defaultValue) {
		String strValue = asTrimString(value);
		if (StringUtils.isNotBlank(strValue)) {
			return Boolean.valueOf(strValue);
		}
		return defaultValue;
	}

	public static float asPrimitiveFloat(Object value) {
		return asPrimitiveFloat(value, INVALID_NUMBER_VALUE);
	}

	public static float asPrimitiveFloat(Object value, float defaultValue) {
		String strValue = asTrimString(value);
		if (StringUtils.isNotBlank(strValue) && NumberUtils.isCreatable(strValue)) {
			return Float.valueOf(strValue);
		}
		return defaultValue;
	}

	public static double asPrimitiveDouble(Object value) {
		return asPrimitiveDouble(value, INVALID_NUMBER_VALUE);
	}

	public static double asPrimitiveDouble(Object value, double defaultValue) {
		String strValue = asTrimString(value);
		if (StringUtils.isNotBlank(strValue) && NumberUtils.isCreatable(strValue)) {
			return Double.valueOf(strValue);
		}
		return defaultValue;
	}

	public static List<?> asList(Object object) {
		if (object != null && object instanceof List<?>) {
			return (List<?>) object;
		}
		return null;
	}

	public static Object[] asArray(List<?> list) {
		if (Detect.notEmpty(list)) {
			return list.toArray();
		}
		return null;
	}

	public static String[] asStringArray(Collection<Object> objects) {
		if (Detect.notEmpty(objects)) {
			String[] result = new String[0];
			for (Object obj : objects) {
				result = ArrayUtils.add(result, asString(obj));
			}
			return result;
		}
		return null;
	}

}
