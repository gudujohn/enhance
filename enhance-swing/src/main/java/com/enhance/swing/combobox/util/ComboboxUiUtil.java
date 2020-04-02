package com.enhance.swing.combobox.util;

import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.plaf.synth.SynthComboBoxUI;

/**
 * comboxui适配工具
 * 
 * @author JiangGengchao
 * @data 2017年4月26日
 */
@SuppressWarnings("restriction")
public class ComboboxUiUtil {
	/**
	 * combobox设置ui
	 * 
	 * @param comboPopup
	 * @param component
	 */
	public static void setUi(ComboPopup comboPopup, JComboBox<?> component) {
		ComboBoxUI cui = (ComboBoxUI) UIManager.getUI(component);// com.sun.java.swing.plaf.windows.WindowsComboBoxUI
		// com.sun.java.swing.plaf.motif.MotifComboBoxUI
		if (cui instanceof MetalComboBoxUI) {
			cui = new MetalJComponentComboBoxUI(comboPopup);
		} else if (cui instanceof SynthComboBoxUI) {
			cui = new SynthJComponentComboBoxUI(comboPopup);
		} else {
			// 不存在的ui使用系统默认
			cui = new MetalJComponentComboBoxUI(comboPopup);
		}
		component.setUI(cui);
	}

	static class MetalJComponentComboBoxUI extends MetalComboBoxUI {
		private ComboPopup comboPopup;

		public MetalJComponentComboBoxUI() {
			super();
		}

		public MetalJComponentComboBoxUI(ComboPopup comboPopup) {
			super();
			this.comboPopup = comboPopup;
		}

		@Override
		protected ComboPopup createPopup() {
			return this.comboPopup;
		}
	}

	static class SynthJComponentComboBoxUI extends SynthComboBoxUI {
		private ComboPopup comboPopup;

		public SynthJComponentComboBoxUI() {
			super();
		}

		public SynthJComponentComboBoxUI(ComboPopup comboPopup) {
			super();
			this.comboPopup = comboPopup;
		}

		@Override
		protected ComboPopup createPopup() {
			return this.comboPopup;
		}
	}

}
