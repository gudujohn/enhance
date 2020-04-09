package com.enhance.web.enums;

import com.enhance.common.enums.TextedEnum;

public enum RestResultCodeEnum implements TextedEnum {

	SUCCESS("1", "成功"),

	FAILURE("0", "失败");

	private String code;
	private String text;

	RestResultCodeEnum(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public String getCode() {
		return this.code;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public String getName() {
		return this.name();
	}
}
