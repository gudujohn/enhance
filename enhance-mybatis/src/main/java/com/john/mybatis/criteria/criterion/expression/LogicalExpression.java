package com.john.mybatis.criteria.criterion.expression;

import com.john.mybatis.criteria.criterion.Criterion;
import com.john.mybatis.criteria.criterion.CriterionSupport;

public class LogicalExpression extends CriterionSupport {

    private static final long serialVersionUID = -3851800630285981826L;

    private final Criterion lhs;
    private final Criterion rhs;
    private final String op;

    public LogicalExpression(Criterion lhs, Criterion rhs, String op) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toSql() {
        return lhs.toSql() + ' ' + getOp() + ' ' + rhs.toSql();
    }

}
