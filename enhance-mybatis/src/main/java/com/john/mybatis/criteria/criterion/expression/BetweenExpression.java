package com.john.mybatis.criteria.criterion.expression;

import com.john.mybatis.criteria.criterion.CriterionSupport;

public class BetweenExpression extends CriterionSupport {

    private static final long serialVersionUID = 3776505282082530871L;

    private final Object lo;
    private final Object hi;

    public BetweenExpression(String propertyName, Object lo, Object hi) {
        this.setPropertyName(propertyName);
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public String toSql() {
        return this.getCloumnName() + " between '" + lo + "' and '" + hi + "'";
    }

}
