package com.john.mybatis.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

public abstract class ReflectionUtil extends ReflectionUtils {

    private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericType(final Class<?> clazz) {
        return (Class<T>) getGenericType(clazz, 0);
    }

    public static Class<?> getGenericType(final Class<?> clazz, final int index) {

        Type genericType = clazz.getGenericSuperclass();

        if (!(genericType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class<?>) params[index];
    }

    public static Object invokeGetterMethod(Class<?> clazz, String propertyName, Object target) {
        String getterMethodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

        // boolean type method name start isxxxxx() not is getxxxx() so ......
        Field field = ReflectionUtil.findField(clazz, propertyName);
        if (Objects.equals(boolean.class, field.getType())) {
            getterMethodName = "is" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        }
        Method getterMethod = findMethod(clazz, getterMethodName);
        return invokeMethod(getterMethod, target);
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * 
     * 子类对象
     * 
     * @return 父类中的属性对象
     */
    public static Field[] getDeclaredField(Class<?> entityClass) {
        Field[] fields = new Field[] {};
        for (; entityClass != Object.class; entityClass = entityClass.getSuperclass()) {
            fields = ArrayUtils.addAll(fields, entityClass.getDeclaredFields());
        }
        return fields;
    }
}
