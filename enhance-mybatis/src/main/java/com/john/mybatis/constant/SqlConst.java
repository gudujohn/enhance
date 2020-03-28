package com.john.mybatis.constant;

public interface SqlConst {

    int MAXIMUM_NUMBER_OF_EXPRESSIONS_USING_IN_CLAUSE = 990;

    String NOWAIT_LOCK_TABLE_FRAGMENT = " FOR UPDATE NOWAIT";

    String FUNCTION_AGGREGATE_SUM = "sum";
    String FUNCTION_AGGREGATE_AVG = "avg";
    String FUNCTION_AGGREGATE_MAX = "max";
    String FUNCTION_AGGREGATE_MIN = "min";
    String FUNCTION_AGGREGATE_COUNT = "count";

}
