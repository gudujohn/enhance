package org.enhance.swing.utils;

import java.awt.*;

public class Tools {

	/**
	 * 对象居中
	 *
	 * @param comp
	 */
	public static void moveCenter(Component comp) {
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Point p = new Point();
		p.x = dimScreen.width / 2 - comp.getSize().width / 2;
		p.y = dimScreen.height / 2 - comp.getSize().height / 2;

		comp.setLocation(p);
	}

	/**
	 * 输出调试语句
	 *
	 * @param msg
	 */
	public static void trace(String msg) {
		String DEBUG = System.getProperty("DEBUG");
		if (DEBUG != null && DEBUG.equalsIgnoreCase("true"))
			System.out.println(msg);
	}

	public static void assertTrue(boolean condition) {
		String DEBUG = System.getProperty("DEBUG");
		if (DEBUG != null && DEBUG.equalsIgnoreCase("true") && !condition) {
			throw new RuntimeException("Violate Assertion!");
		}
	}

	/**
	 * @deprecated not used now
	 */
	@Deprecated
	public static boolean checkPermission(String permission) {
		return true; // CommonClientEnvironment.getAccessControl().checkPermission(permission);
	}

}
