package com.enhance.swing.component.combobox;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.enhance.swing.component.combobox.support.checkbox.ComboCheckPopUp;
import com.enhance.swing.component.combobox.support.checkbox.ComboCheckBoxEntry;
import com.enhance.swing.component.combobox.util.ComboboxUiUtil;

/**
 * CheckBoxCombobox
 * 
 * @author JiangGengchao
 * @datae 2018年1月8日
 */
@SuppressWarnings("serial")
public class CheckBoxCombobox extends JComboBox<Object> {

	public static final String SELECT_ALL = "-- 全部选中 --";
	private ComboCheckPopUp popup;

	public CheckBoxCombobox() {
		super();
		this.setRenderer(new ComboCheckBoxRenderer());
		// 增加一个全选
		this.addItem(new ComboCheckBoxEntry(false, true, SELECT_ALL, SELECT_ALL));

		this.setToolTipText("按Ctrl + 鼠标左键进行多选");

		this.setEditable(true);
	}

	/**
	 * add item
	 * 
	 * @param item
	 */
	public void addItem(ComboCheckBoxEntry item) {
		super.addItem(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComboBox#addItem(java.lang.Object)
	 */
	@Override
	public void addItem(Object anObject) {
		ComboCheckBoxEntry item = new ComboCheckBoxEntry(anObject);
		this.addItem(item);
	}

	/**
	 * 删除全部！
	 * 
	 * @see javax.swing.JComboBox#removeAllItems()
	 */
	@Override
	public void removeAllItems() {
		super.removeAllItems();
		// 增加一个全选
		this.addItem(new ComboCheckBoxEntry(false, true, SELECT_ALL, SELECT_ALL));
	}

	/**
	 * 选中全部记录
	 */
	public void setSelectAllItem() {
		// 全选
		for (int i = 0; i < this.getItemCount(); i++) {
			ComboCheckBoxEntry ccbe = (ComboCheckBoxEntry) this.getItemAt(i);

			if (ccbe.getState() == true) {
				ccbe.setChecked(true);
			}
		}

		// repaint
		this.repaint();
	}

	/**
	 * 取消全部选中记录
	 */
	public void setUnSelectAllItem() {
		// 全选
		for (int i = 0; i < this.getItemCount(); i++) {
			ComboCheckBoxEntry ccbe = (ComboCheckBoxEntry) this.getItemAt(i);

			if (ccbe.getState() == true) {
				ccbe.setChecked(false);
			}
		}
		// repaint
		this.repaint();
	}

	/**
	 * 选中 被选中的items
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public void setCheckedItems(List values) {
		DefaultComboBoxModel model = (DefaultComboBoxModel) getModel();

		for (int i = 0; i < model.getSize(); i++) {
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) model.getElementAt(i);
			item.setChecked(false);
		}

		if (values == null || values.size() == 0) {
			// notify UI
			this.updateUI();
			return;
		}

		// 选中应该选中的
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			Object value = iter.next();
			int index = this.isInThisComboBox(value);
			if (index != -1) {
				ComboCheckBoxEntry item = (ComboCheckBoxEntry) model.getElementAt(index);
				item.setChecked(true);
			}
		}

		// 是否全选中
		boolean isSelectAll = true;
		for (int i = 0; i < model.getSize(); i++) {
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) model.getElementAt(i);
			// 全选
			if (SELECT_ALL.equals(item.getUserObject())) {
				continue;
			}
			if (item.getChecked() == false) {
				isSelectAll = false;
				break;
			}
		}

		ComboCheckBoxEntry selectAllItem = (ComboCheckBoxEntry) model.getElementAt(0);
		if (isSelectAll == true) {
			selectAllItem.setChecked(true);
		} else {
			selectAllItem.setChecked(false);
		}

		// notify UI
		this.updateUI();
	}

	@Override
	public void repaint() {
		super.repaint();
		if (null != this.getEditor()) {
			String text = "";
			for (int i = 0; i < this.getItemCount(); i++) {
				ComboCheckBoxEntry item = (ComboCheckBoxEntry) this.getItemAt(i);
				if (item.getText().toString().equals(SELECT_ALL))
					continue;
				if (item.getChecked()) {
					if (text.equals("")) {
						text = item.getText().toString();
					} else {
						text = text + "," + item.getText().toString();
					}
				}
			}
			this.getEditor().setItem(text);

			if (((JTextField) this.getEditor().getEditorComponent()).isEditable()) {
				((JTextField) this.getEditor().getEditorComponent()).setEditable(false);
			}
		}

	}

	/**
	 * get all checked UserObject
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCheckedUserObjects() {
		List userObjects = new ArrayList();
		DefaultComboBoxModel model = (DefaultComboBoxModel) getModel();
		for (int i = 0; i < model.getSize(); i++) {
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) model.getElementAt(i);
			boolean checked = item.getChecked();
			// 全选
			if (SELECT_ALL.equals(item.getUserObject()) && checked) {
				continue;
			}
			if (i != 0 && checked) {
				userObjects.add(item.getUserObject());
			}
		}

		return userObjects;
	}

	// Added by qlj，能够获取字典值
	public List<?> getAllUserObjects() {
		List<Object> userObjects = new ArrayList<Object>();
		DefaultComboBoxModel<Object> model = (DefaultComboBoxModel<Object>) getModel();
		for (int i = 0; i < model.getSize(); i++) {
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) model.getElementAt(i);
			userObjects.add(item.getUserObject());
		}

		return userObjects;
	}

	/**
	 * 该userObject是否已经存在下拉列表里了 如果UserObject为Model. 则比较Model.getId是否相等
	 * 其他情况直接用userObject.equals()方法进行比较
	 * 
	 * @param userObject
	 * @return -1: 没找到。 >0 找到的位置
	 */
	public int isInThisComboBox(Object userObject) {
		if (userObject == null)
			return -1;

		DefaultComboBoxModel<Object> model = (DefaultComboBoxModel<Object>) getModel();
		for (int i = 0; i < model.getSize(); i++) {
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) model.getElementAt(i);
			// TODO
			// 不是就直接比较好了
			if (userObject.equals(item.getUserObject())) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public void updateUI() {
		super.updateUI();
		if (null == popup) {
			popup = new ComboCheckPopUp(this);
		}
		ComboboxUiUtil.setUi(popup, this);
	}

	/**
	 * render
	 * 
	 * @author Zhou Yi Shen Dec 25, 2007
	 */
	private static class ComboCheckBoxRenderer extends JCheckBox implements ListCellRenderer<Object>, Serializable {
		private static final long serialVersionUID = 1L;
		protected static Border noFocusBorder;

		/**
		 * Constructs a default renderer object for an item in a list.
		 */
		public ComboCheckBoxRenderer() {
			super();
			if (noFocusBorder == null) {
				noFocusBorder = new EmptyBorder(1, 1, 1, 1);
			}
			setOpaque(true);
			setBorder(noFocusBorder);
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			setComponentOrientation(list.getComponentOrientation());
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			if (value instanceof ComboCheckBoxEntry) {
				ComboCheckBoxEntry item = (ComboCheckBoxEntry) value;
				setSelected(item.getChecked());
				setText(item.toString());
				// 根据status决定是否enabled
				this.setEnabled(item.getState());
			} else {
				setText((value == null) ? "" : value.toString());
			}

			setFont(list.getFont());
			setBorder((cellHasFocus) ? UIManager.getBorder("List.focusCellHighlightBorder") : noFocusBorder);

			return this;
		}

	}
}
