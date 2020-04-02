package com.enhance.swing.tree;

import java.util.List;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.enhance.swing.tree.node.LoadingTreeNode;
import com.enhance.swing.tree.node.EnhanceTreeNode;

/**
 * 自定义延迟加载树
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
@SuppressWarnings("serial")
public class EnhanceLazyLoadTree extends JTree {

	public EnhanceLazyLoadTree(DefaultTreeModel treeModel) {
		super();
		setModel(treeModel);
		setShowsRootHandles(true); // 显示根结点左边的控制手柄
		initNodeListener();
		setCellRenderer(new EnhanceTreeCellRenderer());
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
				EnhanceTreeNode expandNode = (EnhanceTreeNode) event.getPath().getLastPathComponent();
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
	private long loadingNode(final EnhanceTreeNode expandNode) {
		// 本次异步加载的处理时长
		long handleTime = 0L;
		// 开始处理的时刻
		long start = System.currentTimeMillis();
		if (expandNode.getLoading() == false) {
			expandNode.setLoading(true);
			new Thread(new Runnable() {
				@Override
				public void run() {
					List<EnhanceTreeNode> childNodeList = expandNode.loadChildNodes();
					if (null != childNodeList) {
						for (EnhanceTreeNode child : childNodeList) {
							EnhanceLazyLoadTree.this.addNode(expandNode, child);
						}
					}
					EnhanceLazyLoadTree.this.removeNode((LoadingTreeNode) expandNode.getFirstChild());
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
		List<EnhanceTreeNode> childNodeList = ((EnhanceTreeNode) treeModel.getRoot()).loadChildNodes();
		if (null != childNodeList) {
			for (EnhanceTreeNode child : childNodeList) {
				if (child.getLeafNode() == false) {
					this.addLoadingNode(child);
				}
				this.addNode((EnhanceTreeNode) treeModel.getRoot(), child);
			}
		}
	}

	/**
	 * 增加加载节点
	 * 
	 * @param parent
	 * @return
	 */
	public EnhanceTreeNode addLoadingNode(EnhanceTreeNode parent) {
		EnhanceTreeNode loading = new LoadingTreeNode();
		addNode(parent, loading);
		return loading;
	}

	/**
	 * 删除指定节点
	 * 
	 * @param node
	 */
	public void removeNode(EnhanceTreeNode node) {
		((DefaultTreeModel) treeModel).removeNodeFromParent(node);
	}

	/**
	 * 添加指定节点
	 * 
	 * @param parent
	 * @param child
	 */
	public void addNode(EnhanceTreeNode parent, EnhanceTreeNode child) {
		((DefaultTreeModel) treeModel).insertNodeInto(child, parent, parent.getChildCount());
	}

}
