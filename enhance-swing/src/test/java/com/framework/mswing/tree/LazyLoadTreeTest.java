package com.framework.mswing.tree;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;

import com.framework.mswing.swing.EnhanceUIManager;
import com.framework.mswing.tree.node.MyTreeNode;
import com.framework.mswing.tree.node.RootTreeNode;

public class LazyLoadTreeTest extends JFrame {

	private static final long serialVersionUID = 1L;

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

	public static void main(String args[]) throws Exception {
		EnhanceUIManager.changeFlavor(2);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LazyLoadTreeTest().setVisible(true);
			}
		});
	}
}