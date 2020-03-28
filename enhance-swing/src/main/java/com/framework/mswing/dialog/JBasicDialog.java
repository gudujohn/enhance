package com.framework.mswing.dialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class JBasicDialog extends JDialog {
	private boolean isPrompt = false;

	public JBasicDialog(Frame owner, String title, Dimension dimension) {
		this(owner, title, dimension, true);
	}

	public JBasicDialog(Dialog owner, String title, Dimension dimension) {
		this(owner, title, dimension, true);
	}

	public JBasicDialog(Frame owner, String title, Dimension dimension, boolean modal) {
		this(owner, title, dimension, modal, false);
	}

	public JBasicDialog(Dialog owner, String title, Dimension dimension, boolean modal) {
		this(owner, title, dimension, modal, false);
	}

	public JBasicDialog(Frame owner, String title, Dimension dimension, boolean modal, boolean isPrompt) {
		this(owner, title, dimension, modal, isPrompt, false);
	}

	public JBasicDialog(Dialog owner, String title, Dimension dimension, boolean modal, boolean isPrompt) {
		this(owner, title, dimension, modal, isPrompt, false);
	}

	public JBasicDialog(Frame owner, String title, Dimension dimension, boolean modal, boolean isPrompt, boolean isResizable) {
		super(owner, title, modal);
		this.isPrompt = isPrompt;
		Dimension screenDimen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension);
		setLocation((int) (screenDimen.getWidth() - dimension.getWidth()) / 2, (int) (screenDimen.getHeight() - dimension.getHeight()) / 2);
		setResizable(true);
		if (isResizable) {
			addComponentListener(new ComponentSizeAdapter(dimension, dimension, null));
		} else {
			addComponentListener(new ComponentSizeAdapter(dimension));
		}
	}

	public JBasicDialog(Dialog owner, String title, Dimension dimension, boolean modal, boolean isPrompt, boolean isResizable) {
		super(owner, title, modal);
		this.isPrompt = isPrompt;
		Dimension screenDimen = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension);
		setLocation((int) (screenDimen.getWidth() - dimension.getWidth()) / 2, (int) (screenDimen.getHeight() - dimension.getHeight()) / 2);
		setResizable(true);
		if (isResizable) {
			addComponentListener(new ComponentSizeAdapter(dimension, dimension, null));
		} else {
			addComponentListener(new ComponentSizeAdapter(dimension));
		}
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (isPrompt) {
			if (e.getID() == WindowEvent.WINDOW_CLOSING) {
				int selectedValue = JOptionPane.showConfirmDialog(getParent(), "确认要退出？", "确认", JOptionPane.YES_NO_OPTION);
				if (selectedValue != JOptionPane.YES_OPTION) {
					return;
				}
			}
		}
		super.processWindowEvent(e);
	}
}
