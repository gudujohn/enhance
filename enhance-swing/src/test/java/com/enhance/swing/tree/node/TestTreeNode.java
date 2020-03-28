package com.enhance.swing.tree.node;

import java.util.List;

import com.enhance.swing.component.tree.node.MyTreeNode;
import com.enhance.swing.tree.service.TreeService;

@SuppressWarnings("serial")
public class TestTreeNode extends MyTreeNode {

	@Override
	public List<MyTreeNode> loadChildNodes() {
		return TreeService.getTreeNode(this);
	}

}
