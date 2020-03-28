package com.enhance.mybatis.criteria.criterion.expression;

import com.enhance.mybatis.criteria.criterion.CriterionSupport;

public class LikeExpression extends CriterionSupport {

    private static final long serialVersionUID = -4456348334582033124L;

    private final String value;

    public LikeExpression(String propertyName, String value) {
        super(propertyName);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toSql() {
        // TODO Auto-generated method stub
        return this.getCloumnName() + " like '%" + value + "%'";
    }

}
