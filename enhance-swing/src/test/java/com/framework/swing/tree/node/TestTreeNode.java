package com.framework.swing.tree.node;

import java.util.List;

import com.framework.swing.component.tree.node.MyTreeNode;
import com.framework.swing.tree.service.TreeService;

@SuppressWarnings("serial")
public class TestTreeNode extends MyTreeNode {

	@Override
	public List<MyTreeNode> loadChildNodes() {
		return TreeService.getTreeNode(this);
	}

}
