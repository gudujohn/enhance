package org.enhance.mybatis.criteria.criterion.expression;

import org.enhance.mybatis.criteria.criterion.CriterionSupport;

public class NullExpression extends CriterionSupport {

    private static final long serialVersionUID = -1621204374094364140L;

    public NullExpression(String propertyName) {
        super(propertyName);
    }

    @Override
    public String toSql() {
        return this.getPropertyName() + " is null ";
    }

}
