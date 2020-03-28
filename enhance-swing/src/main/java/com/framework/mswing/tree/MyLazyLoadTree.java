package com.framework.mswing.tree;

import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.framework.mswing.tree.node.LoadingTreeNode;
import com.framework.mswing.tree.node.MyTreeNode;

/**
 * 自定义延迟加载树
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
@SuppressWarnings("serial")
public class MyLazyLoadTree extends JTree {

	public MyLazyLoadTree(DefaultTreeModel treeModel) {
		super();
		setModel(treeModel);
		setShowsRootHandles(true); // 显示根结点左边的控制手柄
		initNodeListener();
		setCellRenderer(new MyTreeCellRenderer());
		initFirstLevelTree();
		expandPath(new TreePath(treeModel.getRoot()));
	}

	/**
	 * 设置打开子目录监听器
	 */
	private void initNodeListener() {
		this.addTreeExpansionListener(new TreeExpansionListener() {
			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				// 首先获取展开的结点
				MyTreeNode expandNode = (MyTreeNode) event.getPath().getLastPathComponent();
				loadingNode(expandNode);
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
			}
		});
	}

	/**
	 * 加载子节点
	 * 
	 * @return
	 */
	private long loadingNode(MyTreeNode expandNode) {
		// 本次异步加载的处理时长
		long handleTime = 0L;
		// 开始处理的时刻
		long start = System.currentTimeMillis();
		if (expandNode.getLoading() == false) {
			expandNode.setLoading(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					List<MyTreeNode> childNodeList = expandNode.loadChildNodes();
					if (null != childNodeList) {
						for (MyTreeNode child : childNodeList) {
							MyLazyLoadTree.this.addNode(expandNode, child);
						}
					}
					MyLazyLoadTree.this.removeNode((LoadingTreeNode) expandNode.getFirstChild());
				}
			}).start();
		}
		handleTime = System.currentTimeMillis() - start;
		return handleTime;
	}

	/**
	 * 初始化树的第一层
	 */
	private void initFirstLevelTree() {
		List<MyTreeNode> childNodeList = ((MyTreeNode) treeModel.getRoot()).loadChildNodes();
		if (null != childNodeList) {
			for (MyTreeNode child : childNodeList) {
				if (child.getLeafNode() == false) {
					this.addLoadingNode(child);
				}
				this.addNode((MyTreeNode) treeModel.getRoot(), child);
			}
		}
	}

	/**
	 * 增加加载节点
	 * 
	 * @param parent
	 * @return
	 */
	public MyTreeNode addLoadingNode(MyTreeNode parent) {
		MyTreeNode loading = new LoadingTreeNode();
		addNode(parent, loading);
		return loading;
	}

	/**
	 * 删除指定节点
	 * 
	 * @param node
	 */
	public void removeNode(MyTreeNode node) {
		((DefaultTreeModel) treeModel).removeNodeFromParent(node);
	}

	/**
	 * 添加指定节点
	 * 
	 * @param parent
	 * @param child
	 */
	public void addNode(MyTreeNode parent, MyTreeNode child) {
		((DefaultTreeModel) treeModel).insertNodeInto(child, parent, parent.getChildCount());
	}

}
