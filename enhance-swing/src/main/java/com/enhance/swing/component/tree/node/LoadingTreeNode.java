package com.enhance.swing.component.tree.node;

import java.util.List;

import com.enhance.swing.image.IconFactory;

/**
 * 缓冲jiedian
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
@SuppressWarnings("serial")
public class LoadingTreeNode extends MyTreeNode {

	public LoadingTreeNode() {
		super();
		this.setName("载入中...");
		this.setLoading(true);
		this.setIcon(IconFactory.getIcon("image/tree/loading1.gif"));
	}

	@Override
	public List<MyTreeNode> loadChildNodes() {
		return null;
	}

}
