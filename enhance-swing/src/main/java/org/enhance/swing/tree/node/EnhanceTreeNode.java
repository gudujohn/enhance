package org.enhance.swing.tree.node;

import java.util.List;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public abstract class EnhanceTreeNode extends DefaultMutableTreeNode {

	private ImageIcon icon;
	private String name; // 结点名称.对应nodes表中node_name字段
	private Boolean loading = false; // 是否加载
	private Boolean leafNode; // 是否为叶子结点.对应node表中is_leaf字段:0-是, 1-不是
	private String desc; // 结点描述.对应nodes表中node_desc字段.暂未使用
	private JTree tree;

	public EnhanceTreeNode() {
		super();
	}

	public EnhanceTreeNode(String nodeName) {
		super(nodeName);
		this.name = nodeName;
	}

	/**
	 * 加载子节点事件
	 * 
	 * @param treeModel
	 * @return
	 */
	public abstract List<EnhanceTreeNode> loadChildNodes();

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getLoading() {
		return loading;
	}

	public void setLoading(Boolean loading) {
		this.loading = loading;
	}

	public Boolean getLeafNode() {
		return leafNode;
	}

	public void setLeafNode(Boolean leafNode) {
		this.leafNode = leafNode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

}
