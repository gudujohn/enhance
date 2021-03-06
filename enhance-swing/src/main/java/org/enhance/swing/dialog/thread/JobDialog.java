package org.enhance.swing.dialog.thread;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import org.enhance.swing.border.ShadowBorder;
import org.enhance.swing.button.ButtonActionRegister;
import org.enhance.swing.dialog.EnhanceBasicDialog;
import org.enhance.swing.image.IconFactory;

@SuppressWarnings("serial")
public class JobDialog extends EnhanceBasicDialog {
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
		setDefaultCloseOperation(EnhanceBasicDialog.DO_NOTHING_ON_CLOSE);
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

			JButton hideButton = new JButton("????????????");
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

			JButton cancelButton = new JButton("????????????");
			ActionListener cancelActionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (true) {
						JOptionPane.showMessageDialog(getParent(), "????????????????????????????????????", "??????", JOptionPane.WARNING_MESSAGE);
						return;
					}
					@SuppressWarnings("unused")
					int selectedValue = JOptionPane.showConfirmDialog(JobDialog.this.getParent(), "???????????????????????????", "??????", JOptionPane.YES_NO_OPTION);
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
		}
	}
}
