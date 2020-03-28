package com.enhance.swing.component.statusbar;

import java.awt.*;

import javax.swing.*;

import com.enhance.swing.border.ButtonBorder;
import com.enhance.swing.component.panel.JStatusItem;

public class JStatusBar extends JSplitPane {
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	private static final long serialVersionUID = -9160554634126153528L;

	private JPanel leftPanel;
	private JPanel rightPanel;

	private boolean isCornerVisible = true;

	public JStatusBar() {
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT); // 设置分隔条的方向
		this.setEnabled(false);
		setBorderVisible(true);
		setCornerVisible(false);

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		this.setLeftComponent(leftPanel);
		this.setRightComponent(rightPanel);
	}

	public void setBorderVisible(boolean isBorderVisible) {
		if (isBorderVisible) {
			setPreferredSize(new Dimension(getPreferredSize().width, 20));
			setBorder(new ButtonBorder(ButtonBorder.BORDER_LOWERED));
		} else {
			setPreferredSize(new Dimension(getPreferredSize().width, 18));
			setBorder(null);
		}
	}

	public void setCornerVisible(boolean isCornerVisible) {
		this.isCornerVisible = isCornerVisible;
	}

	public void addItem(JStatusItem item) {
		addItem(item, LEFT);
	}

	public void addItem(JStatusItem item, int justification) {
		if (justification == LEFT) {
			leftPanel.add(item);
		} else if (justification == RIGHT) {
			rightPanel.add(item, BorderLayout.EAST);
		}
	}

	@Override
	public void paintChildren(Graphics g) {
		super.paintChildren(g);
		if (isCornerVisible) {
			int w = getWidth();
			int h = getHeight();
			g.setColor(Color.white);
			g.drawLine(w, h - 12, w - 12, h);
			g.drawLine(w, h - 8, w - 8, h);
			g.drawLine(w, h - 4, w - 4, h);
			g.setColor(new Color(128, 128, 128));
			g.drawLine(w, h - 11, w - 11, h);
			g.drawLine(w, h - 10, w - 10, h);
			g.drawLine(w, h - 7, w - 7, h);
			g.drawLine(w, h - 6, w - 6, h);
			g.drawLine(w, h - 3, w - 3, h);
			g.drawLine(w, h - 2, w - 2, h);
		}
		g.setColor(UIManager.getColor("Panel.background"));
	}
}
