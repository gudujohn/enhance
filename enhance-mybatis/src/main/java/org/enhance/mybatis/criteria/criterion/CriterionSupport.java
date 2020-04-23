package org.enhance.mybatis.criteria.criterion;

import org.enhance.common.util.Detect;

@SuppressWarnings("serial")
public abstract class CriterionSupport implements Criterion {

	private String cloumnName;
	private String propertyName;

	public CriterionSupport() {
	}

	public CriterionSupport(String propertyName, String cloumnName) {
		if (Detect.notEmpty(cloumnName)) {
			this.cloumnName = cloumnName;
		} else {
			this.propertyName = propertyName;
		}
		this.cloumnName = propertyName;
	}

	public CriterionSupport(String propertyName) {
		this.propertyName = propertyName;
		this.cloumnName = propertyName;
	}

	@Override
	public Criterion and(Criterion criterion) {
		return Restrictions.and(this, criterion);
	}

	@Override
	public Criterion or(Criterion criterion) {
		return Restrictions.or(this, criterion);
	}

	@Override
	public String getCloumnName() {
		return cloumnName;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param cloumnName the cloumnName to set
	 */
	public final void setCloumnName(String cloumnName) {
		this.cloumnName = cloumnName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public final void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
