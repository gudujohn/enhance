package org.enhance.common.util;

import java.util.HashMap;
import java.util.Map;

import org.enhance.common.enums.NamedEnum;
import org.enhance.common.enums.TextedEnum;
import org.enhance.common.enums.ValuedEnum;

public class EnumUtil {

	public static <T extends ValuedEnum> T getByValue(Class<T> enumClass, short value) {
		return getByValue(enumClass, value, null);
	}

	public static <T extends TextedEnum> T getByText(Class<T> enumClass, String text) {
		return getByText(enumClass, text, null);
	}

	public static <T extends NamedEnum> T getByName(Class<T> enumClass, String name) {
		return getByName(enumClass, name, null);
	}

	public static <T extends ValuedEnum> T getByValue(Class<T> enumClass, short value, T defaultValue) {
		for (T e : enumClass.getEnumConstants()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return defaultValue;
	}

	public static <T extends TextedEnum> T getByText(Class<T> enumClass, String text, T defaultValue) {
		for (T e : enumClass.getEnumConstants()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		return defaultValue;
	}

	public static <T extends NamedEnum> T getByName(Class<T> enumClass, String name, T defaultValue) {
		for (T e : enumClass.getEnumConstants()) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return defaultValue;
	}

	public static <T extends ValuedEnum> Map<Short, String> getValueNameMap(Class<T> enumClass) {
		Map<Short, String> result = new HashMap<Short, String>();
		for (T e : enumClass.getEnumConstants()) {
			result.put(e.getValue(), e.getName());
		}
		return result;
	}

}
