package com.framework.mswing.utils;

import java.awt.Component;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * 基础Swing工具
 * 
 * @author JiangGengchao
 * @datae 2017年12月17日
 */
public class SwingUtil {
	/**
	 * 获取所有容器
	 * 
	 * @return
	 */
	public static Component[] getAllComponents() {
		Window[] allWindows = Window.getWindows();
		int componentCount = 0;
		for (Window w : allWindows) {
			if (w instanceof Component) {
				componentCount++;
			}
		}
		Component[] components = new Component[componentCount];
		int i = 0;
		for (Window w : allWindows) {
			if (w instanceof Component) {
				components[i++] = w;
			}
		}
		return components;
	}

	/**
	 * 直接获取JDialog
	 * 
	 * @return 返回所有的JDialog的数组
	 */
	public static JDialog[] getAllDialog() {
		Window[] allWindows = Window.getWindows();
		int dialogCount = 0;
		for (Window w : allWindows) {
			if (w instanceof JDialog) {
				dialogCount++;
			}
		}
		JDialog[] dialogs = new JDialog[dialogCount];
		int c = 0;
		for (Window w : allWindows) {
			if (w instanceof JDialog) {
				dialogs[c++] = (JDialog) w;
			}
		}
		return dialogs;
	}

	/**
	 * 通过dialog的标题获取dialog
	 * 
	 * @param title
	 * @return
	 */
	public static List<JDialog> getDialogByTitle(String title) {
		JDialog[] src = getAllDialog();
		List<JDialog> tar = new ArrayList<JDialog>();
		for (JDialog d : src) {
			if (title.equals(d.getTitle())) {
				tar.add(d);
			}
		}
		return tar;
	}

	/**
	 * 直接获取JFrame
	 * 
	 * @return 返回所有的JFrame的数组
	 */
	public static JFrame[] getAllFrame() {
		Window[] allWindows = Window.getWindows();
		int frameCount = 0;
		for (Window w : allWindows) {
			if (w instanceof JFrame) {
				frameCount++;
			}
		}
		JFrame[] frames = new JFrame[frameCount];
		int c = 0;
		for (Window w : allWindows) {
			if (w instanceof JFrame) {
				frames[c++] = (JFrame) w;
			}
		}
		return frames;
	}

	/**
	 * 获取最顶层组件
	 * 
	 * @return
	 */
	public static Component getTopComponent() {
		return Window.getWindows()[Window.getWindows().length - 1];
	}
}