package com.framework.swing.action;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

/**
 * 事件处理工厂
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public class HandleFactory {
	/**
	 * 获取组件
	 * 
	 * @param component
	 * @param clazz
	 * @return
	 */
	public static Component getComponent(Component component, @SuppressWarnings("rawtypes") Class clazz) {
		if (component == null) {
			return null;
		}

		if (component.getClass() == clazz) {
			return component;
		}

		Container parent = component.getParent();
		while (parent != null) {
			if (parent.getClass() == clazz) {
				return parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	/**
	 * 获取窗口
	 * 
	 * @param component
	 * @return
	 */
	public static Window getWindow(Component component) {
		if (component == null) {
			return null;
		}

		if (component instanceof Window) {
			return (Window) component;
		}

		Container parent = component.getParent();
		while (parent != null) {
			if (parent instanceof Window) {
				return (Window) parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	/**
	 * 获取框架
	 * 
	 * @param component
	 * @return
	 */
	public static Frame getFrame(Component component) {
		if (component == null) {
			return null;
		}

		if (component instanceof Frame) {
			return (Frame) component;
		}

		Container parent = component.getParent();
		while (parent != null) {
			if (parent instanceof Frame) {
				return (Frame) parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	/**
	 * 获取应用
	 * 
	 * @param component
	 * @return
	 */
	public static Applet getApplet(Component component) {
		if (component == null) {
			return null;
		}

		if (component instanceof Applet) {
			return (Applet) component;
		}

		Container parent = component.getParent();
		while (parent != null) {
			if (parent instanceof Applet) {
				return (Applet) parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	/**
	 * 获取会话
	 * 
	 * @param component
	 * @return
	 */
	public static Dialog getDialog(Component component) {
		if (component == null) {
			return null;
		}

		if (component instanceof Dialog) {
			return (Dialog) component;
		}

		Container parent = component.getParent();
		while (parent != null) {
			if (parent instanceof Dialog) {
				return (Dialog) parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	/**
	 * 获取会话模式
	 * 
	 * @param window
	 * @return
	 */
	public static Window getModalDialog(Window window) {
		Window[] windows = window.getOwnedWindows();
		for (int i = 0; i < windows.length; i++) {
			if (windows[i].isVisible() && windows[i] instanceof Dialog && ((Dialog) windows[i]).isModal()) {
				return (getModalDialog(windows[i]));
			}
		}
		return window;
	}

	/**
	 * 获取祖先组件
	 * 
	 * @param window
	 * @return
	 */
	public static Component getAncestorComponent(Window window) {
		Window[] windows = window.getOwnedWindows();
		for (int i = 0; i < windows.length; i++) {
			if (windows[i].isVisible() && windows[i] instanceof Dialog && ((Dialog) windows[i]).isModal()) {
				return (getModalDialog(windows[i]));
			}
		}
		return window;
	}
}
