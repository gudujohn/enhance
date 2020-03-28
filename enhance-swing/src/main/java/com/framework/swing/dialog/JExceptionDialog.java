package com.framework.swing.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.framework.swing.button.ButtonActionRegister;
import com.framework.swing.icon.IconFactory;

public class JExceptionDialog extends JBasicDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4277158609879925495L;

	private static JExceptionDialog dialog;

	private JTextArea promptTextArea;
	private JButton detailButton;
	private JPanel downPanel;
	private JTextArea textArea;
	private boolean opened = false;

	public static void traceException(Component component, String prompt, Exception ex) {
		traceException(component, prompt, "详细异常信息", ex);
	}

	public static void traceException(Component component, String prompt, String digest, Exception ex) {
		if (dialog == null) {
			if (component instanceof Dialog) {
				dialog = new JExceptionDialog((Dialog) component);
			} else if (component instanceof Frame) {
				dialog = new JExceptionDialog((Frame) component);
			}
		}
		if (!dialog.isVisible()) {
			dialog.setException(prompt, digest, ex);
			dialog.setVisible(true);
		} else {
			dialog.addException(ex);
		}
	}

	public JExceptionDialog(Dialog parentDialog) {
		super(parentDialog, "异常", new Dimension(500, 120));
		initComponents();
	}

	public JExceptionDialog(Frame parentFrame) {
		super(parentFrame, "异常", new Dimension(500, 120));
		initComponents();
	}

	private void initComponents() {

		JLabel iconLabel = new JLabel(IconFactory.getIcon("image/warning.png"));
		JPanel iconPanel = new JPanel();
		iconPanel.add(iconLabel);
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(iconPanel, BorderLayout.NORTH);
		leftPanel.add(new JPanel(), BorderLayout.CENTER);

		promptTextArea = new JTextArea("请查看详细异常信息", 5, 40);
		promptTextArea.setEditable(false);
		promptTextArea.setLineWrap(true);
		promptTextArea.setAutoscrolls(true);
		JPanel contextPanel = new JPanel();
		contextPanel.setLayout(new BorderLayout());
		contextPanel.add(promptTextArea, BorderLayout.CENTER);

		JButton closeButton = new JButton("关  闭");
		closeButton.setPreferredSize(new Dimension(80, 25));
		ActionListener closeActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
		closeButton.addActionListener(closeActionListener);
		ButtonActionRegister.registerToEscKey(closeButton, closeActionListener);

		detailButton = new JButton("详细资料");
		detailButton.setPreferredSize(new Dimension(80, 25));
		ActionListener detailActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (opened) {
					downPanel.setVisible(false);
					detailButton.setText("详细资料");
					setSize(500, 120);
				} else {
					downPanel.setVisible(true);
					detailButton.setText("隐藏资料");
					setSize(500, 320);
				}
				opened = !opened;
				// validate();
			}
		};
		detailButton.addActionListener(detailActionListener);
		ButtonActionRegister.registerToEnterKey(detailButton, detailActionListener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(closeButton, BorderLayout.NORTH);
		buttonPanel.add(detailButton, BorderLayout.SOUTH);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(buttonPanel, BorderLayout.NORTH);
		rightPanel.add(new JPanel(), BorderLayout.CENTER);

		JPanel blankPanel = new JPanel();
		blankPanel.setPreferredSize(new Dimension(500, 8));
		JPanel upCtxPanel = new JPanel();
		upCtxPanel.setLayout(new BorderLayout());
		upCtxPanel.setPreferredSize(new Dimension(500, 80));
		upCtxPanel.add(leftPanel, BorderLayout.WEST);
		upCtxPanel.add(contextPanel, BorderLayout.CENTER);
		upCtxPanel.add(rightPanel, BorderLayout.EAST);
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new BorderLayout());
		upPanel.add(blankPanel, BorderLayout.NORTH);
		upPanel.add(upCtxPanel, BorderLayout.CENTER);

		textArea = new JTextArea("无异常详细信息");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(490, 200));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getViewport().add(textArea);

		downPanel = new JPanel();
		downPanel.add(scrollPane);
		downPanel.setVisible(false);

		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.add(upPanel, BorderLayout.NORTH);
		container.add(downPanel, BorderLayout.CENTER);

		setResizable(false);
		for (int i = 0; i < getComponentListeners().length; i++) {
			ComponentListener componentListener = getComponentListeners()[i];
			if (componentListener instanceof ComponentSizeAdapter) {
				removeComponentListener(componentListener);
				break;
			}
		}
	}

	public void setException(String prompt, String digest, Exception e) {
		String exceptionContent = transformException(e);
		digest = (digest == null ? "" : digest + "\n");
		promptTextArea.setText(prompt + "\n");
		textArea.setText(digest + exceptionContent + "\n");
	}

	public void addException(Exception e) {
		String exceptionContent = transformException(e);
		textArea.setText(textArea.getText() + exceptionContent + "\n");
	}

	public String transformException(Exception e) {
		if (e instanceof InvocationTargetException) {
			e = (Exception) ((InvocationTargetException) e).getTargetException();
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			e.printStackTrace(new PrintStream(out));
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String(out.toByteArray()).trim();
	}

	public String getExceptionContent() {
		return textArea.getText();
	}
}
