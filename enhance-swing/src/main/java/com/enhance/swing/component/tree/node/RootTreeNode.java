package com.enhance.swing.component.tree.node;

import com.enhance.swing.image.IconFactory;

/**
 * 根节点
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
@SuppressWarnings("serial")
public abstract class RootTreeNode extends MyTreeNode {
	public RootTreeNode() {
		super("root");
		this.setName("root");
		this.setLoading(true);
		this.setIcon(IconFactory.getIcon("picture/swing/tree/tree_root.png"));
	}
}
