package com.framework.swing.dialog.thread;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.framework.swing.border.ShadowBorder;
import com.framework.swing.button.ButtonActionRegister;
import com.framework.swing.dialog.JBasicDialog;
import com.framework.swing.icon.IconFactory;

@SuppressWarnings("serial")
public class JobDialog extends JBasicDialog {
	private JLabel percentLabel;
	private JLabel textLabel;
	private JProgressBar progressBar;

	private BlockProgressMonitor monitor;

	public JobDialog(Frame parentFrame, String title, BlockProgressMonitor monitor) {
		super(parentFrame, title, JDialog.isDefaultLookAndFeelDecorated() ? new Dimension(380, 150) : new Dimension(380, 127)); // new Dimension(400, 135), new Dimension(380, 115)
		this.monitor = monitor;

		initComponents();
	}

	public JobDialog(Dialog parentDialog, String title, BlockProgressMonitor monitor) {
		super(parentDialog, title, JDialog.isDefaultLookAndFeelDecorated() ? new Dimension(380, 150) : new Dimension(380, 127)); // new Dimension(400, 135), new Dimension(380, 115)
		this.monitor = monitor;

		initComponents();
	}

	private void initComponents() {
		getContentPane().add(new TaskPanel());
		setDefaultCloseOperation(JBasicDialog.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				monitor.componentShown();
			}
		});
	}

	public JLabel getPercentLabel() {
		return percentLabel;
	}

	public JLabel getTextLabel() {
		return textLabel;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public class TaskPanel extends JPanel {
		public TaskPanel() {
			percentLabel = new JLabel(IconFactory.getIcon("image/thread.png"));
			textLabel = new JLabel();
			textLabel.setHorizontalAlignment(JLabel.LEADING);
			JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			labelPanel.add(percentLabel);
			labelPanel.add(Box.createHorizontalStrut(5)); // 10
			labelPanel.add(textLabel);

			progressBar = new JProgressBar();
			JPanel centerPanel = new JPanel(new BorderLayout());
			centerPanel.add(Box.createHorizontalStrut(5), BorderLayout.WEST);
			centerPanel.add(Box.createHorizontalStrut(5), BorderLayout.EAST);
			centerPanel.add(progressBar);

			JButton hideButton = new JButton("后台运行");
			ActionListener hideActionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException ex) {

					}
					dispose();
				}
			};
			hideButton.addActionListener(hideActionListener);
			ButtonActionRegister.registerToEnterKey(hideButton, hideActionListener);

			JButton cancelButton = new JButton("撤销任务");
			ActionListener cancelActionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (true) {
						JOptionPane.showMessageDialog(getParent(), "该任务被锁定，无法撤消！", "错误", JOptionPane.WARNING_MESSAGE);
						return;
					}
					@SuppressWarnings("unused")
					int selectedValue = JOptionPane.showConfirmDialog(JobDialog.this.getParent(), "确认要撤消该任务？", "确认", JOptionPane.YES_NO_OPTION);
					if (selectedValue != JOptionPane.YES_OPTION) {
						return;
					}
					monitor.setCanceled(true);
					try {
						Thread.sleep(300);
					} catch (InterruptedException ex) {
					}
					dispose();
				}
			};
			cancelButton.addActionListener(cancelActionListener);
			ButtonActionRegister.registerToEscKey(cancelButton, cancelActionListener);
			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.add(hideButton);
			buttonPanel.add(cancelButton);

			JPanel southPartPanel = new JPanel(new BorderLayout());
			southPartPanel.add(centerPanel, BorderLayout.NORTH);
			southPartPanel.add(buttonPanel, BorderLayout.CENTER);

			setLayout(new BorderLayout());
			setBorder(new ShadowBorder());
			add(labelPanel, BorderLayout.CENTER);
			add(southPartPanel, BorderLayout.SOUTH);
			// add(labelPanel, BorderLayout.NORTH);
			// add(centerPanel, BorderLayout.CENTER);
			// add(buttonPanel, BorderLayout.SOUTH);
		}
	}
}
