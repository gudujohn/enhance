package com.framework.mswing.tree;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class NodeImageObserver implements ImageObserver {

	JTree tree;
	DefaultTreeModel model;
	Vector<DefaultMutableTreeNode> nodes = new Vector<>();

	public NodeImageObserver(JTree tree) {
		super();
		this.tree = tree;
		this.model = (DefaultTreeModel) tree.getModel();
	}

	public void add(DefaultMutableTreeNode node) {
		for (int i = 0; i < nodes.size(); ++i) {
			DefaultMutableTreeNode temp = nodes.get(i);
			if (temp.equals(node))
				return;
		}
		nodes.add(node);
	}

	@Override
	public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
		if ((flags & (FRAMEBITS | ALLBITS)) != 0) {
			for (int i = 0; i < nodes.size(); ++i) {
				TreeNode node = nodes.get(i);
				TreePath path = new TreePath(model.getPathToRoot(node));
				java.awt.Rectangle rect = tree.getPathBounds(path);
				if (rect != null) {
					tree.repaint(rect);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return (flags & (ALLBITS | ABORT)) == 0;
	}
}