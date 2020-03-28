package com.john.mybatis.util;

import com.john.mybatis.annotation.ModelMapping;

/**
 * 模型标注工具
 * 
 * @author JiangGengchao
 * @date 2018年3月18日
 */
public abstract class AnnotationUtil {

    /**
     * 获取模型对象上注解中的映射表名
     * 
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz) {
        ModelMapping modelMapping = getModelMapping(clazz);
        String tableName = modelMapping.tableName();
        AssertionUtil.notNull(tableName, "tableName of '" + ModelMapping.class + "' is required. (class: " + clazz + ")");
        return tableName;
    }

    private static ModelMapping getModelMapping(Class<?> clazz) {
        ModelMapping modelMapping = clazz.getAnnotation(ModelMapping.class);
        AssertionUtil.notNull(modelMapping, "missing annotation '" + ModelMapping.class + "' on class. (class: " + clazz + ")");
        return modelMapping;
    }

}
