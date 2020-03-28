package com.framework.mswing.swing;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.framework.mswing.utils.SwingUtil;

/**
 * 
 * @author JiangGengchao
 * @datae 2017年12月19日
 */
@SuppressWarnings("serial")
public class EnhanceUIManager extends UIManager {
	/**
	 * 动态改变lookandfeel
	 * 
	 * @param flavor
	 *            1:"Metal风格";2:"Nimbus风格";3:"Windows风格";4:"Windows经典风格";5:"MotifyItem风格":
	 * @throws Exception
	 */
	public static void changeFlavor(int flavor) throws Exception {
		switch (flavor) {
		// 设置Metal风格
		case 1:
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			break;
		case 2:
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			break;
		case 3:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			break;
		case 4:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			break;
		case 5:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			break;
		}
		// 更新f窗口内顶级容器以及内部所有组件的UI
		// SwingUtilities.updateComponentTreeUI(component);
		JFrame[] allFrame = SwingUtil.getAllFrame();
		JDialog[] allDialog = SwingUtil.getAllDialog();
		for (JFrame frame : allFrame) {
			SwingUtilities.updateComponentTreeUI(frame);
		}
		for (JDialog dialog : allDialog) {
			SwingUtilities.updateComponentTreeUI(dialog);
		}
	}
}
