package com.enhance.swing.combobox.support.date;

import java.awt.*;

import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class TopBottomLineBorder extends AbstractBorder {

	private Color lineColor;

	public TopBottomLineBorder(Color color) {
		lineColor = color;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		g.setColor(lineColor);
		g.drawLine(0, 0, c.getWidth(), 0);
		g.drawLine(0, c.getHeight() - 1, c.getWidth(), c.getHeight() - 1);
	}
}
