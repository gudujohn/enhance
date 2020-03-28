package com.enhance.swing.message;

import javax.swing.*;

import com.enhance.common.enums.OptionedEnum;
import com.enhance.common.util.EnumUtil;

public enum MessageType implements OptionedEnum {

	INFORMATION((short) JOptionPane.INFORMATION_MESSAGE, "提示"),

	WARNNING((short) JOptionPane.WARNING_MESSAGE, "警告"),

	ERROR((short) JOptionPane.ERROR_MESSAGE, "错误"),

	YES_OR_NO((short) JOptionPane.YES_NO_OPTION, "提示");

	private short value;
	private String text;

	private MessageType(short value, String text) {
		this.value = value;
		this.text = text;
	}

	public static MessageType get(short value) {
		return EnumUtil.getByValue(MessageType.class, value);
	}

	public static MessageType get(String text) {
		return EnumUtil.getByText(MessageType.class, text);
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public short getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name();
	}
}
