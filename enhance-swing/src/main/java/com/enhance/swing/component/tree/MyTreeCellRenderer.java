package com.enhance.swing.component.tree;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.enhance.swing.component.tree.node.LoadingTreeNode;
import com.enhance.swing.component.tree.node.MyTreeNode;
import com.enhance.swing.image.IconFactory;

/**
 * 自定义树渲染器
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
@SuppressWarnings("serial")
public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

	private String style;

	public MyTreeCellRenderer() {
		this("image/tree/");
	}

	public MyTreeCellRenderer(String style) {
		this.style = style;
		this.setOpenIcon(IconFactory.getIcon(style + "tree_open.png"));
		this.setClosedIcon(IconFactory.getIcon(style + "tree_closed.png"));
		this.setLeafIcon(IconFactory.getIcon(style + "tree_leaf.png"));
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		// 执行父类原型操作
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		// 得到每个节点的TreeNode
		MyTreeNode node = (MyTreeNode) value;

		this.setText(node.getName());

		if (node.isRoot()) {
			setIcon(IconFactory.getIcon(style + "tree_root.png"));
		}

		// if (expanded) {
		// // 节点打开
		// } else {
		// // 节点关闭
		// }

		if (value instanceof LoadingTreeNode) {
			NodeImageObserver observer = new NodeImageObserver(tree);
			observer.add(node);
			ImageIcon gif = node.getIcon();
			gif.setImageObserver(observer);
			setIcon(gif);
		} else if (null != node.getIcon()) {
			setIcon(node.getIcon());
		}
		return this;
	}

}