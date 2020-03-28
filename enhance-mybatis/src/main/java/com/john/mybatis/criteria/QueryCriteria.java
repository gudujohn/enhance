package com.john.mybatis.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.john.mybatis.constant.SqlConst;
import com.john.mybatis.criteria.criterion.Criterion;
import com.john.mybatis.util.MybatisDetect;

public class QueryCriteria extends MybatisDetect implements Serializable, SqlConst {

    private static final long serialVersionUID = -6429514902913357772L;

    public List<Criterion> criteria;
    private Ordering ordering;
    private Class<?> modelClass;

    public QueryCriteria(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    public QueryCriteria add(Criterion criterion) {
        if (null != criterion) {
            this.getCriteria().add(criterion);
        }
        return this;
    }

    public QueryCriteria add(QueryCriteria queryCriteria) {
        if (null != queryCriteria && notEmpty(queryCriteria.getCriteria())) {
            this.getCriteria().addAll(queryCriteria.getCriteria());
        }
        return this;
    }

    /**
     * 
     * @param propertyName
     * @param type
     *            Ordering.DESC or Ordering.ASC
     * @return
     */
    public QueryCriteria addOrdering(String propertyName, String type) {
        if (Objects.isNull(this.ordering)) {
            this.ordering = new Ordering(propertyName, type);
        } else {
            this.ordering.add(propertyName, type);
        }
        return this;
    }

    public QueryCriteria addAsc(String propertyName) {
        if (Objects.isNull(this.ordering)) {
            this.ordering = new Ordering(propertyName, Ordering.ASC);
        } else {
            this.ordering.add(propertyName, Ordering.ASC);
        }
        return this;
    }

    public QueryCriteria addDesc(String propertyName) {
        if (Objects.isNull(this.ordering)) {
            this.ordering = new Ordering(propertyName, Ordering.DESC);
        } else {
            this.ordering.add(propertyName, Ordering.DESC);
        }
        return this;
    }

    public boolean isPropertyInCriterion(String property) {
        for (Criterion criterion : criteria) {
            if (property.toLowerCase().equals(criterion.getPropertyName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the criteria
     */
    public List<Criterion> getCriteria() {
        if (null == criteria) {
            criteria = new ArrayList<Criterion>();
        }
        return criteria;
    }

    public String toSql() {
        if (notEmpty(criteria)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < criteria.size(); i++) {
                if (i > 0) {
                    sb.append("and ");
                }
                sb.append(criteria.get(i).toSql());
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * @return the modelClass
     */
    public Class<?> getModelClass() {
        return modelClass;
    }

    /**
     * @return the ordering
     */
    public Ordering getOrdering() {
        return ordering;
    }

}
