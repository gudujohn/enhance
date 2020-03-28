package com.enhance.swing.component.dialog;

import java.awt.*;

import javax.swing.*;

import com.enhance.swing.image.IconFactory;
import com.enhance.swing.action.HandleFactory;
import com.enhance.swing.border.ShadowBorder;

@SuppressWarnings("serial")
public abstract class ThreadDialog extends BasicDialog implements Runnable {
	private JLabel label;

	private String text;
	private Icon icon;

	private Thread thread;

	public ThreadDialog(Frame parentFrame, String title, String text) {
		this(parentFrame, title, text, IconFactory.getIcon("image/thread.png"));
	}

	public ThreadDialog(Dialog parentDialog, String title, String text) {
		this(parentDialog, title, text, IconFactory.getIcon("image/thread.png"));
	}

	public ThreadDialog(Frame parentFrame, String title, String text, Icon icon) {
		super(parentFrame, title, JDialog.isDefaultLookAndFeelDecorated() ? new Dimension(330, 120) : new Dimension(330, 97));
		this.text = text;
		this.icon = icon;
		initComponents();
	}

	public ThreadDialog(Dialog parentDialog, String title, String text, Icon icon) {
		super(parentDialog, title, JDialog.isDefaultLookAndFeelDecorated() ? new Dimension(330, 120) : new Dimension(330, 97));
		this.text = text;
		this.icon = icon;
		initComponents();
	}

	public void initComponents() {
		label = new JLabel(text);
		label.setIconTextGap(20);
		label.setIcon(icon);
		label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new ShadowBorder());
		panel.add(label, BorderLayout.CENTER);
		getContentPane().add(panel);
		setDefaultCloseOperation(BasicDialog.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
			}
		});

		try {
			Thread.sleep(300);
		} catch (InterruptedException ex) {
		}

		try {
			execute();
		} catch (final Exception ex) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					ExceptionDialog.traceException(HandleFactory.getFrame(ThreadDialog.this), "请求数据异常", ex);
					ex.printStackTrace();
				}
			});
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(false);
				dispose();
			}
		});
	}

	public abstract void execute() throws Exception;

	public void updateText(String text) {
		label.setText(text);
	}
}
