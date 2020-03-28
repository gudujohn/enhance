package com.framework.swing.ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.framework.utils.SwingUtil;

/**
 * 
 * @author JiangGengchao
 * @datae 2017年12月19日
 */
@SuppressWarnings("serial")
public class EnhanceUIManager extends UIManager {
	public final static int LOOKANDFEEL_METAL = 1;
	public final static int LOOKANDFEEL_NIMBUS = 2;
	public final static int LOOKANDFEEL_WINDOWS = 3;
	public final static int LOOKANDFEEL_CLASS_WINDOWS = 4;
	public final static int LOOKANDFEEL_MOTIFYITEM = 5;

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
		case LOOKANDFEEL_METAL:
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			break;
		case LOOKANDFEEL_NIMBUS:
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			break;
		case LOOKANDFEEL_WINDOWS:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			break;
		case LOOKANDFEEL_CLASS_WINDOWS:
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			break;
		case LOOKANDFEEL_MOTIFYITEM:
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
