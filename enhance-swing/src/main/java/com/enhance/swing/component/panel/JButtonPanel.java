package com.enhance.swing.component.panel;

import javax.swing.*;

public class JButtonPanel extends JPanel {

	private static final long serialVersionUID = 3833152654246677638L;

	public JButtonPanel() {
		setBorder(null);
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
}