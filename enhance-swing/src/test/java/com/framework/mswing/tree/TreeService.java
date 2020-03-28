package com.framework.mswing.tree;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.framework.mswing.tree.node.MyTreeNode;
import com.framework.mswing.tree.node.TestTreeNode;
import com.framework.utils.XmlUtil;

public class TreeService {
	public static List<MyTreeNode> getTreeNode(MyTreeNode node) {
		List<MyTreeNode> results = new ArrayList<>();
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
