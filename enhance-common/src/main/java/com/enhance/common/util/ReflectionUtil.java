package com.enhance.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReflectionUtil extends ReflectionUtils {

	public static String[] getAllStringConst(Class<?> constClass) {
		Field[] fields = ReflectionUtil.getDeclaredField(constClass);
		String[] values = ArrayUtils.EMPTY_STRING_ARRAY;
		if (Detect.notEmpty(fields)) {
			try {
				for (Field field : fields) {
					int modifiers = field.getModifiers();
					if (!String.class.isAssignableFrom(field.getType()) || !Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers)) {
						continue;
					}
					String value = Objects.toString(field.get(constClass));
					values = ArrayUtils.add(values, value);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return values;
	}

	public static Field[] getDeclaredField(Class<?> entityClass) {
		Field[] fields = new Field[] {};
		for (; entityClass != Object.class; entityClass = entityClass.getSuperclass()) {
			fields = ArrayUtils.addAll(fields, entityClass.getDeclaredFields());
		}
		return fields;
	}

}
