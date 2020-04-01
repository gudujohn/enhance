package com.enhance.swing.component.field;

import javax.swing.*;

public class EnhanceTextField extends AbstractFieldPanel {
	private static final long serialVersionUID = -4806322311882962826L;

	private JTextField textField;

	public EnhanceTextField(String fieldName) {
		super(fieldName);
	}

	@Override
	protected void initView() {
		textField = new JTextField();
		this.add(textField, "1w");
	}

}