package com.framework.mswing.icon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * 带阴影的箭头图标
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public class ArrowIcon implements Icon, SwingConstants {
	private Color shadow = UIManager.getColor("controlShadow");
	private Color darkShadow = Color.black;

	private int size;
	private int direction;

	public ArrowIcon(int size, int direction) {
		this.size = size;
		this.direction = direction;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		boolean isEnabled = c.isEnabled();
		Color oldColor = g.getColor();
		int mid, i, j;

		j = 0;
		size = Math.max(size, 2);
		mid = (size / 2) - 1;

		g.translate(x, y);
		if (isEnabled) {
			g.setColor(darkShadow);
		} else {
			g.setColor(shadow);
		}

		switch (direction) {
		case NORTH:
			for (i = 0; i < size; i++) {
				g.drawLine(mid - i, i, mid + i, i);
			}
			break;
		case SOUTH:
			j = 0;
			for (i = size - 1; i >= 0; i--) {
				g.drawLine(mid - i, j, mid + i, j);
				j++;
			}
			break;
		case WEST:
			for (i = 0; i < size; i++) {
				g.drawLine(i, mid - i, i, mid + i);
			}
			break;
		case EAST:
			j = 0;
			for (i = size - 1; i >= 0; i--) {
				g.drawLine(j, mid - i, j, mid + i);
				j++;
			}
			break;
		}
		g.translate(-x, -y);
		g.setColor(oldColor);
	}

	@Override
	public int getIconWidth() {
		return size;
	}

	@Override
	public int getIconHeight() {
		return size;
	}
}