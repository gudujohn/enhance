package com.enhance.mybatis.criteria.criterion.expression;

import com.enhance.mybatis.criteria.criterion.CriterionSupport;

public class NotNullExpression extends CriterionSupport {

    private static final long serialVersionUID = -1621204374094364140L;

    public NotNullExpression(String propertyName) {
        super(propertyName);
    }

    @Override
    public String toSql() {
        return this.getPropertyName() + " is not null ";
    }

}
