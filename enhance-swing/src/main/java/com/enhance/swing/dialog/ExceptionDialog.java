package com.enhance.swing.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

import com.enhance.swing.button.ButtonActionRegister;
import com.enhance.swing.image.IconFactory;

public class ExceptionDialog extends EnhanceBasicDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4277158609879925495L;

	private static ExceptionDialog dialog;

	private JLabel promptLabel;
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
				dialog = new ExceptionDialog((Dialog) component);
			} else if (component instanceof Frame) {
				dialog = new ExceptionDialog((Frame) component);
			}
		}
		if (!dialog.isVisible()) {
			dialog.setException(prompt, digest, ex);
			dialog.setVisible(true);
		} else {
			dialog.addException(ex);
		}
	}

	public ExceptionDialog(Dialog parentDialog) {
		super(parentDialog, "异常", new Dimension(500, 120));
		initComponents();
	}

	public ExceptionDialog(Frame parentFrame) {
		super(parentFrame, "异常", new Dimension(500, 120));
		initComponents();
	}

	private void initComponents() {

		JLabel iconLabel = new JLabel(IconFactory.getIcon("image/warning.png"));
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(iconLabel, BorderLayout.CENTER);

		promptLabel = new JLabel("请查看详细异常信息");
		promptLabel.setEnabled(false);
		JPanel contextPanel = new JPanel();
		contextPanel.setLayout(new BorderLayout());
		contextPanel.add(promptLabel, BorderLayout.CENTER);

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

		detailButton = new JButton("详  细");
		detailButton.setPreferredSize(new Dimension(80, 25));
		ActionListener detailActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (opened) {
					downPanel.setVisible(false);
					detailButton.setText("详  细");
					setSize(500, 120);
				} else {
					downPanel.setVisible(true);
					detailButton.setText("隐  藏");
					setSize(500, 320);
				}
				opened = !opened;
				// validate();
			}
		};
		detailButton.addActionListener(detailActionListener);
		ButtonActionRegister.registerToEnterKey(detailButton, detailActionListener);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(closeButton);
		buttonPanel.add(detailButton);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(buttonPanel, BorderLayout.CENTER);

		JPanel upPanel = new JPanel();
		upPanel.setLayout(new BorderLayout());
		upPanel.add(leftPanel, BorderLayout.WEST);
		upPanel.add(contextPanel, BorderLayout.CENTER);
		upPanel.add(rightPanel, BorderLayout.EAST);

		textArea = new JTextArea("无异常详细信息");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getViewport().add(textArea);

		downPanel = new JPanel(new BorderLayout());
		downPanel.add(scrollPane, BorderLayout.CENTER);
		downPanel.setVisible(false);

		this.setLayout(new BorderLayout());
		this.add(upPanel, BorderLayout.NORTH);
		this.add(downPanel, BorderLayout.CENTER);

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
		promptLabel.setText(digest + "\n");
		textArea.setText(exceptionContent + "\n");
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
