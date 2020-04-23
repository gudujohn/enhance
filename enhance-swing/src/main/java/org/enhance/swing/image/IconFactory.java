package org.enhance.swing.image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

/**
 * 图片工厂
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public class IconFactory {

	private static Map<String, Object> iconPool = new HashMap<String, Object>(); // 图标缓存

	/**
	 * 获取icon
	 * 
	 * @param iconFullPath
	 * @return
	 */
	public static ImageIcon getIcon(String iconFullPath) {
		Object iconObject = iconPool.get(iconFullPath);
		if (iconObject != null) {
			return (ImageIcon) iconObject;
		} else {
			ImageIcon icon = createIcon(iconFullPath);
			if (icon == null) {
				throw new IllegalArgumentException("there is no icon:" + iconFullPath);
			} else {
				iconPool.put(iconFullPath, icon);
			}
			return icon;
		}
	}

	private static ImageIcon createIcon(String iconFullPath) {
		URL iconURL = IconFactory.class.getClassLoader().getResource(iconFullPath);
		if (iconURL != null) {
			return new ImageIcon(iconURL);
		}
		return null;
	}
}
