package com.john.mybatis.criteria.criterion.expression;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.john.mybatis.constant.SqlConst;
import com.john.mybatis.criteria.criterion.CriterionSupport;
import com.john.mybatis.util.AssertionUtil;
import com.john.mybatis.util.MybatisDetect;
import com.john.mybatis.util.MybatisStringUtil;

public class InExpression extends CriterionSupport {

    private static final long serialVersionUID = -8830563969879164446L;

    private final Object[] values;
    private final List<Object[]> valuesSet;

    public InExpression(String propertyName, Object[] values) {
        this.setPropertyName(propertyName);
        this.values = values;
        this.valuesSet = grouping(values, SqlConst.MAXIMUM_NUMBER_OF_EXPRESSIONS_USING_IN_CLAUSE);
    }

    @Override
    public String toSql() {
        StringBuffer fragment = new StringBuffer();
        if (MybatisDetect.notEmpty(valuesSet) && valuesSet.size() > 1) {
            fragment.append("(");

            for (int i = 0; i < valuesSet.size(); i++) {
                if (i == 0)
                    fragment.append(this.getCloumnName()).append(" in ( '" + MybatisStringUtil.join(valuesSet.get(i), "','") + "' )");
                else
                    fragment.append(" OR ").append(this.getCloumnName()).append(" in ( '" + MybatisStringUtil.join(valuesSet.get(i), "','") + "' )");
            }
            fragment.append(")");
        } else {
            fragment.append(this.getCloumnName()).append(" in ( '" + MybatisStringUtil.join(valuesSet.get(0), "','") + "' )");
        }
        return fragment.toString();
    }

    @Override
    public String toString() {
        return this.getCloumnName() + " in (" + ArrayUtils.toString(values) + ')';
    }

    private List<Object[]> grouping(Object[] values, int groupSize) {

        if (null != values && values.length > 0) {

            AssertionUtil.isPositive(groupSize, "divideSize must be bigger than 0");

            int groupLength = values.length / groupSize + ((values.length % groupSize) > 0 ? 1 : 0);

            List<Object[]> longArryGroup = new LinkedList<Object[]>();
            Object[] valueArray = null;
            for (int i = 0; i < groupLength; i++) {
                int arrayLength = (i < groupLength - 1 || values.length % groupSize == 0) ? groupSize : (values.length % groupSize);

                valueArray = new Object[arrayLength];
                for (int j = 0; j < arrayLength; j++) {
                    valueArray[j] = values[i * groupSize + j];
                }
                longArryGroup.add(valueArray);
            }
            return longArryGroup;
        }
        return null;
    }

    public Object[] getValues() {
        return values;
    }

    public List<Object[]> getValuesSet() {
        return valuesSet;
    }

}
