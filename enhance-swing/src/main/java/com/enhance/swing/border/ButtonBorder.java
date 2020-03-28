package com.enhance.swing.border;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

public class ButtonBorder implements Border {
	public final static int BORDER_RAISED = 0;
	public final static int BORDER_LOWERED = 1;

	private Color brighter = UIManager.getColor("Button.background").brighter();
	private Color darker = UIManager.getColor("Button.background").darker();

	private Color top;
	private Color left;
	private Color bottom;
	private Color right;

	public ButtonBorder(int borderType) {
		switch (borderType) {
		case BORDER_RAISED:
			top = brighter;
			left = brighter;
			bottom = darker;
			right = darker;
			break;
		case BORDER_LOWERED:
			top = darker;
			left = darker;
			bottom = brighter;
			right = brighter;
			break;
		}
	}

	public ButtonBorder(Color top, Color left, Color bottom, Color right) {
		top = top;
		left = left;
		bottom = bottom;
		right = right;
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(top == null ? 0 : 1, left == null ? 0 : 1, bottom == null ? 0 : 1, right == null ? 0 : 1);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (bottom != null) {
			g.setColor(bottom);
			g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
		}

		if (right != null) {
			g.setColor(right);
			g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
		}

		if (top != null) {
			g.setColor(top);
			g.drawLine(x, y, x + width - 1, y);
		}

		if (left != null) {
			g.setColor(left);
			g.drawLine(x, y, x, y + height - 1);
		}
	}
}
