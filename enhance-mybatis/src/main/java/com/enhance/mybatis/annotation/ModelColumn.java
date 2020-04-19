package com.enhance.mybatis.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.ibatis.type.JdbcType;
import org.springframework.core.annotation.AliasFor;

/**
 * 映射数据字段annotation
 * 
 * @author JiangGengchao
 * @date 2018年3月18日
 */
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Inherited
public @interface ModelColumn {

	@AliasFor("columnName")
	String value() default "";

	@AliasFor("value")
	String columnName() default "";

	Class<?> javaType() default void.class;

	JdbcType jdbcType() default JdbcType.UNDEFINED;
}
