package com.enhance.mybatis.criteria;

import com.enhance.common.enums.ValuedEnum;
import com.enhance.common.util.EnumUtil;

/**
 * 
 * CriteriaType枚举
 * 
 */
public enum CriteriaType implements ValuedEnum {

	IGNORE((short) 0),

	EQUAL((short) 21),

	NOTEQUAL((short) 22),

	LIKE((short) 23),

	GREATERTHAN((short) 24),

	LESSTHAN((short) 25),

	LESSTHANOREQUAL((short) 26),

	GREATERTHANOREQUAL((short) 27),

	IN((short) 30),

	ISNULL((short) 31),

	ISNOTNULL((short) 32),

	BETWEEN((short) 30),

	NATIVESQL((short) 40);

	private short value;

	private CriteriaType(short value) {
		this.value = value;
	}

	public static CriteriaType get(short value) {
		return EnumUtil.getByValue(CriteriaType.class, value);
	}

	public static CriteriaType get(String name) {
		return EnumUtil.getByName(CriteriaType.class, name);
	}

	/**
	 * @return the name
	 */
	@Override
	public String getName() {
		return name();
	}

	/**
	 * @return the value
	 */
	@Override
	public short getValue() {
		return value;
	}

}
