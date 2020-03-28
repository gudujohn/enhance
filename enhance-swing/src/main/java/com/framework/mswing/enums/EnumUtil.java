package com.framework.mswing.enums;

import java.util.HashMap;
import java.util.Map;

public class EnumUtil {

	public final static <T extends ValuedEnum> T get(Class<T> enumClass, short value, T defaultValue) {
		for (T e : enumClass.getEnumConstants()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return defaultValue;
	}

	public final static <T extends ValuedEnum> T get(Class<T> enumClass, short value) {
		return get(enumClass, value, null);
	}

	public final static <T extends TextedEnum> T getByText(Class<T> enumClass, String text) {
		for (T e : enumClass.getEnumConstants()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return null;
	}

	public final static <T extends TextedEnum> T getByText(Class<T> enumClass, String text, T defaultValue) {
		for (T e : enumClass.getEnumConstants()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return defaultValue;
	}

	public final static <T extends Enum<T>> T get(Class<T> enumClass, String name, T defaultValue) {
		T e = Enum.valueOf(enumClass, name);
		if (null == e) {
			return defaultValue;
		}
		// for (Object o : enumClass.getEnumConstants()) {
		// NamedEnum e = (NamedEnum) o;
		// if (e.getName().equals(name)) {
		// return (T) e;
		// }
		// }
		return e;
	}

	public final static <T extends Enum<T>> T get(Class<T> enumClass, String name) {
		return get(enumClass, name, null);
	}

	public final static <T extends ValuedEnum> String getTextByValue(Class<T> enumClass, short value) {
		for (T e : enumClass.getEnumConstants()) {
			if (e.getValue() == value) {
				return e.getName();
			}
		}
		return "";
	}

	public final static <T extends ValuedEnum> Map<Short, String> getValueNameMap(Class<T> enumClass) {
		Map<Short, String> result = new HashMap<Short, String>();
		for (T e : enumClass.getEnumConstants()) {
			result.put(e.getValue(), e.getName());
		}
		return result;
	}

	public final static <T extends TextedEnum> String getNameByText(Class<T> enumClass, String text) {
		for (T e : enumClass.getEnumConstants()) {
			boolean isEqual = false;
			if (null == e.getText() && null == text) {
				isEqual = true;
			}
			if (null != e.getText() && null != text && e.getText().equals(text)) {
				isEqual = true;
			}
			if (isEqual) {
				return e.getName();
			}
		}
		return text;
	}

}
