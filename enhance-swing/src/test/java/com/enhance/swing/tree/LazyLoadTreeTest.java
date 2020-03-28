package com.enhance.swing.tree;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

import com.enhance.swing.EnhanceUIManager;
import com.enhance.swing.component.tree.MyLazyLoadTree;
import com.enhance.swing.component.tree.node.MyTreeNode;
import com.enhance.swing.component.tree.node.RootTreeNode;
import com.enhance.swing.tree.service.TreeService;

@SuppressWarnings("serial")
public class LazyLoadTreeTest extends JFrame {

	public LazyLoadTreeTest() {
		super("asynch_load_tree");
		DefaultTreeModel model = new DefaultTreeModel(new RootTreeNode() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<MyTreeNode> loadChildNodes() {
				return TreeService.getTreeNode(null);
			}
		});
		MyLazyLoadTree tree = new MyLazyLoadTree(model);
		JScrollPane treePanel = new JScrollPane(tree);
		this.getContentPane().add(treePanel, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 450);
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