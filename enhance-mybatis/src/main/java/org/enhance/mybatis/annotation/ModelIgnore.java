package org.enhance.mybatis.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 不映射魔仙字段annotation
 * 
 * @author JiangGengchao
 * @date 2018年3月18日
 */
@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Inherited
public @interface ModelIgnore {
    boolean ignore() default true;
}
