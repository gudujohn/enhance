package com.enhance.swing.component.field;

import javax.swing.*;

import com.enhance.swing.layout.EnhanceBoxLayout;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFieldPanel extends JPanel {

	private static final long serialVersionUID = 412497981206884990L;

	protected JLabel fieldLable;

	public AbstractFieldPanel(String fieldName) {
		this.setLayout(new EnhanceBoxLayout(EnhanceBoxLayout.BoxType.X_AXIS));
		fieldLable = new JLabel(fieldName + ":");
		fieldLable.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(fieldLable, "30%");
		initView();
	}

	protected abstract void initView();

}