package org.enhance.swing.tree.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.enhance.swing.tree.node.EnhanceTreeNode;
import org.enhance.swing.tree.node.TestTreeNode;
import org.enhance.swing.utils.XmlUtil;

public class TreeService {
	public static List<EnhanceTreeNode> getTreeNode(EnhanceTreeNode node) {
		List<EnhanceTreeNode> results = new ArrayList<>();
		try {
			Document doc = XmlUtil.read("tree.xml");
			Element root = doc.getRootElement();
			if (node == null) {
				@SuppressWarnings("unchecked")
				List<Element> elements = root.elements();
				if (null != elements) {
					for (Element element : elements) {
						TestTreeNode temp = new TestTreeNode();
						temp.setName(element.attributeValue("name"));
						if (element.attributeValue("leaf").equals("1")) {
							temp.setLeafNode(true);
						} else {
							temp.setLeafNode(false);
						}
						results.add(temp);
					}
				}
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				@SuppressWarnings("unchecked")
				List<Element> elements = root.selectNodes("//node[@name='" + node.getName() + "']");
				if (elements != null) {
					@SuppressWarnings("unchecked")
					List<Element> childs = elements.get(0).elements();
					if (null != elements) {
						for (Element element : childs) {
							TestTreeNode temp = new TestTreeNode();
							temp.setName(element.attributeValue("name"));
							if (element.attributeValue("leaf").equals("1")) {
								temp.setLeafNode(true);
							} else {
								temp.setLeafNode(false);
							}
							results.add(temp);
						}
					}
				}
			}
		} catch (MalformedURLException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
}
