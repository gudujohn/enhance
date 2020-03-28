package com.john.mybatis.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 表模型映射annotation
 * 
 * @author JiangGengchao
 * @date 2018年3月18日
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface ModelMapping {

    String tableName();

    String sequenceName() default "";

    String[] forceIgnoreProperties() default {};

}
