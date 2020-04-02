package com.enhance.swing.tree.node;

import java.util.List;

import com.enhance.swing.tree.service.TreeService;

@SuppressWarnings("serial")
public class TestTreeNode extends EnhanceTreeNode {

	@Override
	public List<EnhanceTreeNode> loadChildNodes() {
		return TreeService.getTreeNode(this);
	}

}
