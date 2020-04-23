package org.enhance.mybatis.criteria.criterion.expression;

import java.util.ArrayList;
import java.util.List;

import org.enhance.mybatis.criteria.criterion.Criterion;
import org.enhance.mybatis.criteria.criterion.CriterionSupport;
import org.apache.commons.lang3.StringUtils;

public abstract class Junction extends CriterionSupport {

    private static final long serialVersionUID = -2499984304851253268L;

    private final List<Criterion> criteria = new ArrayList<Criterion>();

    private final String op;

    protected Junction(String op) {
        this.op = op;
    }

    public Junction add(Criterion criterion) {
        criteria.add(criterion);
        return this;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toSql() {
        return '(' + StringUtils.join(criteria.iterator(), ' ' + op + ' ') + ')';
    }

}
