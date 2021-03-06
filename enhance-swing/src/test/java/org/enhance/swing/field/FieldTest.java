package org.enhance.swing.field;

import org.enhance.swing.EnhanceUIManager;
import org.enhance.swing.TestFrame;
import org.enhance.swing.combobox.CheckBoxCombobox;
import org.enhance.swing.combobox.DateCombobox;

/**
 * 定制控件的测试
 * 
 * @author JiangGengchao
 * @date 2016年4月14日
 */
public class FieldTest extends TestFrame {

	private static final long serialVersionUID = 4756172783435755037L;

	@Override
	protected void initUi() {
		initUi1();
		initUi2();
	}

	/**
	 * 时间段控件
	 */
	private void initUi1() {
		DateCombobox periodTimePicker = new DateCombobox();
		this.containerPanel.add(periodTimePicker);
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
		this.containerPanel.add(combobox);
	}

	public static void main(String[] args) throws Exception {
		EnhanceUIManager.changeFlavor(EnhanceUIManager.LOOKANDFEEL_CLASS_WINDOWS);
		new FieldTest();
	}

}
