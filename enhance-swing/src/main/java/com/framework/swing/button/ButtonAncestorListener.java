package com.framework.swing.button;

import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class ButtonAncestorListener implements AncestorListener {
	public void ancestorAdded(AncestorEvent e) {
		JButton defaultButton = (JButton) e.getComponent();
		JRootPane root = SwingUtilities.getRootPane(defaultButton);
		if (root != null) {
			root.setDefaultButton(defaultButton);
		}
	}

	public void ancestorRemoved(AncestorEvent event) {
	}

	public void ancestorMoved(AncestorEvent event) {
	}
}