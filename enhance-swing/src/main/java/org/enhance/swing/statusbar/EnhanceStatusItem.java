package org.enhance.swing.statusbar;

import javax.swing.*;

import org.enhance.swing.layout.FiledLayout;
import org.enhance.swing.panel.EnhanceButtonPanel;

public class EnhanceStatusItem extends EnhanceButtonPanel {
	private static final long serialVersionUID = 7554130960113602213L;
	private int index = -1;

	public EnhanceStatusItem(JComponent component) {
		this();
		add(component);
	}

	public EnhanceStatusItem() {
		setLayout(new FiledLayout(FiledLayout.ROW, FiledLayout.LEFT, 0));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
