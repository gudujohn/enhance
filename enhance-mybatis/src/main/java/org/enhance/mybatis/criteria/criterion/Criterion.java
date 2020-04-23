package org.enhance.mybatis.criteria.criterion;

import java.io.Serializable;

public interface Criterion extends Serializable {

    String getCloumnName();

    String getPropertyName();

    String toSql();

    Criterion and(Criterion criterion);

    Criterion or(Criterion criterion);

}
