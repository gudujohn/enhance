package com.john.mybatis.criteria;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Criteria {

    public String BY_DEFAULT_RULE = "?byDefaultRule?";

    /** default: CriteriaType.EQUAL */
    CriteriaType type() default CriteriaType.EQUAL;

    /** by default, columnName can be got by CriteriaType & fieldName */
    String columnName() default BY_DEFAULT_RULE;

    /** default: "" */
    String value() default "";
}
