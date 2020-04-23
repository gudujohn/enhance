package org.enhance.swing.layout;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnhanceBoxLayout implements LayoutManager2 {

	private BoxType boxType;

	private List<Item> items = new ArrayList<>();
	private int gap = 2;
	private boolean usePerferredSize = false; // 竖立方向是否占满

	public EnhanceBoxLayout(BoxType boxType) {
		this.boxType = boxType;
	};

	/**
	 * addLayoutComponent
	 * 
	 * @param comp
	 * @param constraints 100px|30%|auto|1w
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		EnhanceBoxLayout.Item item = new EnhanceBoxLayout.Item();
		item.comp = comp;
		item.constraints = (String) constraints;
		items.add(item);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(30, 30);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		EnhanceBoxLayout.Item item = new EnhanceBoxLayout.Item();
		item.comp = comp;
		item.constraints = "auto";
		items.add(item);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		Iterator<EnhanceBoxLayout.Item> iter = items.iterator();
		while (iter.hasNext()) {
			EnhanceBoxLayout.Item item = iter.next();
			if (item.comp == comp) {
				iter.remove();
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(30, 30);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(30, 30);
	}

	@Override
	public void layoutContainer(Container parent) {
		// 得到内矩形
		Rectangle rect = new Rectangle(parent.getWidth(), parent.getHeight());
		// Rectangle rect = parent.getBounds();
		Insets insets = parent.getInsets();
		rect.x += insets.left;
		rect.y += insets.top;
		rect.width -= (insets.left + insets.right);
		rect.height -= (insets.top + insets.bottom);

		// 第一轮：过滤到无效的 Item ( 有些控件是隐藏的 )
		List<EnhanceBoxLayout.Item> validItems = new ArrayList<>();
		for (EnhanceBoxLayout.Item it : items) {
			if (it.comp.isVisible()) {
				validItems.add(it);
			}
		}

		// 第二轮处理：百分比，像素，auto的，直接计算出结果; 权重的，在第三轮计算
		int totalGapSize = gap * (validItems.size() - 1);// 间距大小
		int validSize = boxType == BoxType.X_AXIS ? rect.width - totalGapSize : rect.height - totalGapSize;
		int totalSize = 0;
		int totalWeight = 0;
		for (EnhanceBoxLayout.Item it : validItems) {
			Dimension preferred = it.comp.getPreferredSize();
			it.width = boxType == BoxType.X_AXIS ? preferred.width : usePerferredSize ? preferred.width : rect.width;
			it.height = boxType == BoxType.X_AXIS ? usePerferredSize ? preferred.height : rect.height : preferred.height;
			it.weight = 0;
			int calculateNum;

			// 计算宽/高度
			String cstr = it.constraints;
			if (cstr == null || cstr.length() == 0 || cstr.equals("auto")) {
				calculateNum = boxType == BoxType.X_AXIS ? it.width : it.height;
			} else if (cstr.endsWith("%")) { // 按百分比
				int num = Integer.valueOf(cstr.substring(0, cstr.length() - 1));
				calculateNum = validSize * num / 100;
			} else if (cstr.endsWith("w")) { // 按权重
				int num = Integer.valueOf(cstr.substring(0, cstr.length() - 1));
				calculateNum = 0;
				it.weight = num;
			} else if (cstr.endsWith("px")) { // 按权重
				calculateNum = Integer.valueOf(cstr.substring(0, cstr.length() - 2));
			} else { // 按像素
				int num = Integer.valueOf(cstr);
				calculateNum = num;
			}
			if (boxType == BoxType.X_AXIS) {
				it.width = calculateNum;
			} else {
				it.height = calculateNum;
			}

			totalSize += calculateNum;
			totalWeight += it.weight;
		}

		// 第三轮: 剩余空间按权重分配
		if (totalWeight > 0) {
			int remainSize = validSize - totalSize;
			double unit = (double) remainSize / totalWeight;
			for (EnhanceBoxLayout.Item it : validItems) {
				if (it.weight > 0) {
					int num = (int) (unit * it.weight);
					if (boxType == BoxType.X_AXIS) {
						it.width = num;
					} else {
						it.height = num;
					}
				}
			}
		}
		// 第四轮: 按宽度和高度布局
		if (boxType == BoxType.X_AXIS) {
			int x = 0;
			for (EnhanceBoxLayout.Item it : validItems) {
				int y = (rect.height - it.height) / 2;
				if (x + it.width > rect.width) {
					it.width = rect.width - x;
				}
				if (it.width <= 0) {
					break;
				}

				it.comp.setBounds(rect.x + x, rect.y + y, it.width, it.height);

				// System.out.println("宽度: " + it.width);
				x += it.width;
				x += gap; // 间距
			}
		} else {
			int y = 0;
			for (Item it : validItems) {
				int x = 0; // 水平靠左
				if (y + it.height > rect.height) {
					it.height = rect.height - y;
				}
				if (it.height <= 0) {
					break;
				}

				it.comp.setBounds(rect.x + x, rect.y + y, it.width, it.height);

				// System.out.println("宽度: " + it.width);
				y += it.height;
				y += gap; // 间距
			}
		}

	}

	///////////////////////
	private static class Item {
		Component comp;
		String constraints = "auto";
		int width = 0;
		int height = 0;
		int weight = 0; // 权重
	}

	public enum BoxType {
		// 横向布局
		X_AXIS(),
		// 纵向布局
		Y_AXIS();
	}
}
