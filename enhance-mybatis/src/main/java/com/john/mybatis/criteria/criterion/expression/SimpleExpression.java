package com.john.mybatis.criteria.criterion.expression;

import com.john.mybatis.criteria.criterion.CriterionSupport;

public class SimpleExpression extends CriterionSupport {

    private static final long serialVersionUID = -7328220394133875203L;

    private final Object value;
    private final String op;

    public SimpleExpression(String propertyName, Object value, String op) {
        super(propertyName);
        this.value = value;
        this.op = op;
    }

    @Override
    public String toSql() {
        return this.getPropertyName() + op + "'" + value + "'";
    }

}
