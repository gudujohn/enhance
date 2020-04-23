package org.enhance.swing;

import java.awt.*;

import javax.swing.*;

public abstract class TestFrame extends JFrame {

	private static final long serialVersionUID = -2739278479548348375L;

	protected JPanel containerPanel;

	public TestFrame() {
		this.setTitle(this.getClass().getName());
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		containerPanel = new JPanel();
		this.initUi();
		this.add(containerPanel, BorderLayout.CENTER);

		this.setVisible(true);
	}

	protected abstract void initUi();

}