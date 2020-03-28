package com.framework.mswing.button;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class ButtonActionRegister {
	public static void registerToEnterKey(AbstractButton button, ActionListener actionListener) {
		button.setMnemonic('E');
		// button.setText(button.getText() + "(E)");
		button.registerKeyboardAction(actionListener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		button.addAncestorListener(new ButtonAncestorListener());
	}

	public static void registerToEscKey(AbstractButton button, ActionListener actionListener) {
		button.setMnemonic('C');
		// button.setText(button.getText() + "(C)");
		button.registerKeyboardAction(actionListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
}