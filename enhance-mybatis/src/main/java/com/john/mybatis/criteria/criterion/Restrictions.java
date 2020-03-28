package com.john.mybatis.criteria.criterion;

import java.util.Collection;

import com.john.mybatis.criteria.criterion.expression.BetweenExpression;
import com.john.mybatis.criteria.criterion.expression.Conjunction;
import com.john.mybatis.criteria.criterion.expression.Disjunction;
import com.john.mybatis.criteria.criterion.expression.InExpression;
import com.john.mybatis.criteria.criterion.expression.LikeExpression;
import com.john.mybatis.criteria.criterion.expression.LogicalExpression;
import com.john.mybatis.criteria.criterion.expression.NotInExpression;
import com.john.mybatis.criteria.criterion.expression.NotNullExpression;
import com.john.mybatis.criteria.criterion.expression.NullExpression;
import com.john.mybatis.criteria.criterion.expression.SimpleExpression;
import com.john.mybatis.util.MybatisDetect;

public class Restrictions extends MybatisDetect {

    private Restrictions() {
        // cannot be instantiated
    }

    /**
     * Apply an "equal" constraint to the named property
     * 
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static Criterion eq(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "=");
    }

    /**
     * Apply a "not equal" constraint to the named property
     * 
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static Criterion ne(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<>");
    }

    /**
     * Apply a "like" constraint to the named property
     * 
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static Criterion like(String propertyName, String value) {
        if (MybatisDetect.isEmpty(value))
            return null;
        return new LikeExpression(propertyName, value);
    }

    /**
     * Apply a "greater than" constraint to the named property
     * 
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static Criterion gt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">");
    }

    /**
     * Apply a "less than" constraint to the named property
     * 
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static Criterion lt(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<");
    }

    /**
     * Apply a "less than or equal" constraint to the named property
     * 
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static Criterion le(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, "<=");
    }

    /**
     * Apply a "greater than or equal" constraint to the named property
     * 
     * @param propertyName
     * @param value
     * @return Criterion
     */
    public static Criterion ge(String propertyName, Object value) {
        return new SimpleExpression(propertyName, value, ">=");
    }

    /**
     * Apply a "between" constraint to the named property
     * 
     * @param propertyName
     * @param lo
     *            value
     * @param hi
     *            value
     * @return Criterion
     */
    public static Criterion between(String propertyName, Object lo, Object hi) {
        return new BetweenExpression(propertyName, lo, hi);
    }

    /**
     * Apply an "in" constraint to the named property
     * 
     * @param propertyName
     * @param values
     * @return Criterion
     */
    public static Criterion in(String propertyName, Object[] values) {
        return new InExpression(propertyName, values);
    }

    /**
     * Apply an "in" constraint to the named property
     * 
     * @param propertyName
     * @param values
     * @return Criterion
     */
    public static Criterion in(String propertyName, Collection<?> values) {
        Object[] valueArray = values.toArray();
        return in(propertyName, valueArray);
    }

    /**
     * Apply an "not in" constraint to the named property
     * 
     * @param propertyName
     * @param values
     * @return Criterion
     */
    public static Criterion notIn(String propertyName, Object[] values) {
        return new NotInExpression(propertyName, values);
    }

    /**
     * Apply an "not in" constraint to the named property
     * 
     * @param propertyName
     * @param values
     * @return Criterion
     */
    public static Criterion notIn(String propertyName, Collection<?> values) {
        return new NotInExpression(propertyName, values.toArray());
    }

    /**
     * Apply an "is null" constraint to the named property
     * 
     * @return Criterion
     */
    public static Criterion isNull(String propertyName) {
        return new NullExpression(propertyName);
    }

    /**
     * Apply an "is not null" constraint to the named property
     * 
     * @return Criterion
     */
    public static Criterion isNotNull(String propertyName) {
        return new NotNullExpression(propertyName);
    }

    /**
     * Return the conjuction of two expressions
     * 
     * @param lhs
     * @param rhs
     * @return Criterion
     */
    public static Criterion and(Criterion lhs, Criterion rhs) {
        return new LogicalExpression(lhs, rhs, "and");
    }

    /**
     * Return the disjuction of two expressions
     * 
     * @param lhs
     * @param rhs
     * @return Criterion
     */
    public static Criterion or(Criterion lhs, Criterion rhs) {
        return new LogicalExpression(lhs, rhs, "or");
    }

    /**
     * Group expressions together in a single conjunction (A and B and C...)
     * 
     * @return Conjunction
     */
    public static Conjunction conjunction() {
        return new Conjunction();
    }

    /**
     * Group expressions together in a single disjunction (A or B or C...)
     * 
     * @return Conjunction
     */
    public static Disjunction disjunction() {
        return new Disjunction();
    }

}
