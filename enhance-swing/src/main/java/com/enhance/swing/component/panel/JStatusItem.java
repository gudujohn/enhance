package com.enhance.swing.component.panel;

import javax.swing.*;

import com.enhance.swing.layout.FiledLayout;

public class JStatusItem extends JButtonPanel {
	private static final long serialVersionUID = -8666123696424228109L;
	private int index = -1;

	public JStatusItem(JComponent component) {
		this();
		add(component);
	}

	public JStatusItem() {
		setLayout(new FiledLayout(FiledLayout.ROW, FiledLayout.LEFT, 0));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
