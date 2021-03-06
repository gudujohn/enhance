package org.enhance.mybatis.criteria.criterion;

import java.util.Collection;

import org.enhance.common.util.Detect;
import org.enhance.mybatis.criteria.criterion.expression.BetweenExpression;
import org.enhance.mybatis.criteria.criterion.expression.Conjunction;
import org.enhance.mybatis.criteria.criterion.expression.Disjunction;
import org.enhance.mybatis.criteria.criterion.expression.InExpression;
import org.enhance.mybatis.criteria.criterion.expression.LikeExpression;
import org.enhance.mybatis.criteria.criterion.expression.LogicalExpression;
import org.enhance.mybatis.criteria.criterion.expression.NotInExpression;
import org.enhance.mybatis.criteria.criterion.expression.NotNullExpression;
import org.enhance.mybatis.criteria.criterion.expression.NullExpression;
import org.enhance.mybatis.criteria.criterion.expression.SimpleExpression;

public class Restrictions {

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
		if (Detect.isEmpty(value)) {
			return null;
		}
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
	 * @param lo           value
	 * @param hi           value
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
