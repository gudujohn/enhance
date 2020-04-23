package org.enhance.swing.combobox.support.checkbox;

import org.enhance.swing.combobox.CheckBoxCombobox;

/**
 * 下拉选择元素
 * 
 * @author JiangGengchao
 * @datae 2018年1月8日
 */
public class ComboCheckBoxEntry implements ComboCheckBoxItem {
	private boolean checked;// 是否选中
	private boolean state;// 是否可选！
	private String text;// 显示名称
	private Object userObject;// 用户对象
	private CheckBoxCombobox parent;

	public ComboCheckBoxEntry() {
		this.checked = false;
		this.state = true;
		this.text = "";
		this.userObject = null;
	}

	public ComboCheckBoxEntry(Object userObject, CheckBoxCombobox parent) {
		this.checked = false;
		this.state = true;
		this.parent = parent;
		this.userObject = userObject;
		// TODO
		this.text = userObject.toString();

	}

	public ComboCheckBoxEntry(Object userObject) {
		this.checked = false;
		this.state = true;
		this.userObject = userObject;
		// TODO
		this.text = userObject.toString();
	}

	public ComboCheckBoxEntry(String text, Object userObject) {
		this.checked = false;
		this.state = true;
		this.text = text;
		this.userObject = userObject;
	}

	public ComboCheckBoxEntry(boolean checked, String text, Object userObject) {
		this.checked = checked;
		this.state = true;
		this.text = text;
		this.userObject = userObject;
	}

	public ComboCheckBoxEntry(boolean checked, boolean state, String text, Object userObject) {
		this.checked = checked;
		this.state = state;
		this.text = text;
		this.userObject = userObject;
	}

	@Override
	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public Object getUserObject() {
		return userObject;
	}

	@Override
	public boolean getState() {
		return state;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}

	public CheckBoxCombobox getParent() {
		return parent;
	}

	public void setParent(CheckBoxCombobox parent) {
		this.parent = parent;
	}

}
