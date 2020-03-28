package com.enhance.swing.component.combobox.support.checkbox;

/**
 * 带是否可选的功能的Item
 * 
 * @author JiangGengchao
 * @datae 2018年1月8日
 */
public interface ComboCheckBoxItem {
	/**
	 * 获得项目的对象
	 * 
	 * @return String
	 */
	Object getUserObject();

	/**
	 * 获得项目的名称
	 * 
	 * @return String
	 */
	String getText();

	/**
	 * 获得项目是否可以选择的状态
	 * 
	 * @return boolean
	 */
	boolean getState();

	/**
	 * 获得项目是否已经点击的状态
	 * 
	 * @return boolean
	 */
	boolean getChecked();

}
