package com.enhance.swing.layout;

import java.awt.*;

import javax.swing.*;

import com.enhance.swing.TestFrame;

public class EnhanceBoxLayoutTest extends TestFrame {

	private static final long serialVersionUID = 4111831992647587931L;

	@Override
	protected void initUi() {
		this.containerPanel.setLayout(new GridLayout(2, 1));

		JPanel yPanel = new JPanel(new EnhanceBoxLayout(EnhanceBoxLayout.BoxType.Y_AXIS));
		yPanel.setBackground(Color.blue);
		JPanel j1 = new JPanel();
		j1.setBackground(Color.black);
		yPanel.add(j1, "100px");
		JPanel j2 = new JPanel();
		j2.setBackground(Color.RED);
		yPanel.add(j2, "1w");
		JPanel zPanel = new JPanel(new EnhanceBoxLayout(EnhanceBoxLayout.BoxType.X_AXIS));
		yPanel.setBackground(Color.red);
		JPanel j3 = new JPanel();
		j3.setBackground(Color.black);
		zPanel.add(j3, "100px");
		JPanel j4 = new JPanel();
		j4.setBackground(Color.yellow);
		zPanel.add(j4, "1w");
		this.containerPanel.add(yPanel);
		this.containerPanel.add(zPanel);
	}

	public static void main(String[] args) {
		new EnhanceBoxLayoutTest();
	}

}