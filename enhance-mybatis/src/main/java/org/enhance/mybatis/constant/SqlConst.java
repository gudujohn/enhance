package org.enhance.mybatis.constant;

public class SqlConst {

	private SqlConst() {
		throw new IllegalStateException("Constant class");
	}

	public static final int MAXIMUM_NUMBER_OF_EXPRESSIONS_USING_IN_CLAUSE = 990;

	public static final String NOWAIT_LOCK_TABLE_FRAGMENT = " FOR UPDATE NOWAIT";
	public static final String FUNCTION_AGGREGATE_SUM = "sum";
	public static final String FUNCTION_AGGREGATE_AVG = "avg";
	public static final String FUNCTION_AGGREGATE_MAX = "max";
	public static final String FUNCTION_AGGREGATE_MIN = "min";
	public static final String FUNCTION_AGGREGATE_COUNT = "count";

	public static final String PROPERTYNAMESUFFIX_NE = "_Ne";
	public static final String PROPERTYNAMESUFFIX_LIKE = "_Like";
	public static final String PROPERTYNAMESUFFIX_ISNULL = "_IsNull";
	public static final String PROPERTYNAMESUFFIX_ISNOTNULL = "_IsNotNull";
	public static final String PROPERTYNAMESUFFIX_IN = "_in";
	public static final String PROPERTYNAMESUFFIX_NOTIN = "_NotIn";
	public static final String PROPERTYNAMESUFFIX_LT = "_Lt";
	public static final String PROPERTYNAMESUFFIX_GT = "_Gt";
	public static final String PROPERTYNAMESUFFIX_LE = "_Le";
	public static final String PROPERTYNAMESUFFIX_GE = "_Ge";

	public static final String PROPERTYNAMESUFFIX_DESC = "_desc";
	public static final String PROPERTYNAMESUFFIX_ASC = "_asc";

}
