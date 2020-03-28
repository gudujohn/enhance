package com.framework.swing.component;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import com.framework.swing.component.combobox.CheckBoxCombobox;
import com.framework.swing.component.combobox.DateCombobox;
import com.framework.swing.ui.EnhanceUIManager;

/**
 * 定制控件的测试
 * 
 * @author JiangGengchao
 * @date 2016年4月14日
 */

@SuppressWarnings("serial")
public class MyComponentTest extends JFrame {

	public MyComponentTest() throws Exception {
		this.setTitle("MyComponentTest");
		this.setSize(500, 400);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new FlowLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		initUi1();
		initUi2();

		this.setVisible(true);
	}

	/**
	 * 时间段控件
	 */
	private void initUi1() {
		DateCombobox periodTimePicker = new DateCombobox();
		this.add(periodTimePicker);
	}

	private void initUi2() {
		CheckBoxCombobox combobox = new CheckBoxCombobox();
		combobox.addItem("test1");
		combobox.addItem("test2");
		combobox.addItem("test3");
		combobox.addItem("test4");
		combobox.addItem("test5");
		combobox.addItem("test6");
		combobox.addItem("test7");
		combobox.addItem("test8");
		this.add(combobox);
	}

	public static void main(String[] args) throws Exception {
		EnhanceUIManager.changeFlavor(EnhanceUIManager.LOOKANDFEEL_CLASS_WINDOWS);
		new MyComponentTest();
	}

}
