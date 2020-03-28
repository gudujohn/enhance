package com.enhance.swing.utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * <pre>
 * Description : XML related
 * &#64;author yang guanjun
 * </pre>
 */
public class XmlUtil {
	/**
	 * read xml
	 * 
	 * @param fileName
	 *            xml path
	 * @return document object
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public static Document read(String fileName) throws MalformedURLException, DocumentException {
		SAXReader reader = new SAXReader();
		Document document = null;
		InputStream file = XmlUtil.class.getResourceAsStream("/" + fileName);
		if (file == null)
			document = reader.read(new URL(fileName));
		else
			document = reader.read(file);

		return document;
	}

}
