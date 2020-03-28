package com.framework.swing.message;

import javax.swing.JOptionPane;

import com.framework.swing.enums.OptionedEnum;
import com.framework.utils.EnumUtil;

public enum MessageType implements OptionedEnum {

	Information((short) JOptionPane.INFORMATION_MESSAGE, "提示"), //
	Warnning((short) JOptionPane.WARNING_MESSAGE, "警告"), //
	Error((short) JOptionPane.ERROR_MESSAGE, "错误"), //
	YesOrNo((short) JOptionPane.YES_NO_OPTION, "提示");

	private short value;
	private String text;

	private MessageType(short value, String text) {
		this.value = value;
		this.text = text;
	}

	public static MessageType get(short value) {
		return EnumUtil.get(MessageType.class, value);
	}

	public static MessageType get(String name) {
		return EnumUtil.get(MessageType.class, name);
	}

	@Override
	public String getText() {
		return this.text;
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
