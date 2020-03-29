package com.enhance.swing.tree;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

import com.enhance.swing.EnhanceUIManager;
import com.enhance.swing.TestFrame;
import com.enhance.swing.component.tree.MyLazyLoadTree;
import com.enhance.swing.component.tree.node.MyTreeNode;
import com.enhance.swing.component.tree.node.RootTreeNode;
import com.enhance.swing.tree.service.TreeService;

public class LazyLoadTreeTest extends TestFrame {

	private static final long serialVersionUID = -1342186578887167258L;

	public LazyLoadTreeTest() {

	}

	@Override
	protected void initUi() {
		this.containerPanel.setLayout(new BorderLayout());

		DefaultTreeModel model = new DefaultTreeModel(new RootTreeNode() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<MyTreeNode> loadChildNodes() {
				return TreeService.getTreeNode(null);
			}
		});
		MyLazyLoadTree tree = new MyLazyLoadTree(model);
		JScrollPane treePanel = new JScrollPane(tree);
		this.containerPanel.add(treePanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) throws Exception {
		EnhanceUIManager.changeFlavor(2);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LazyLoadTreeTest().setVisible(true);
			}
		});
	}
}