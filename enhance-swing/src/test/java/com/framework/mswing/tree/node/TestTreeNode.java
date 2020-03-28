package com.framework.mswing.tree.node;

import java.util.List;

import com.framework.mswing.tree.TreeService;

@SuppressWarnings("serial")
public class TestTreeNode extends MyTreeNode {

	@Override
	public List<MyTreeNode> loadChildNodes() {
		return TreeService.getTreeNode(this);
	}

}
