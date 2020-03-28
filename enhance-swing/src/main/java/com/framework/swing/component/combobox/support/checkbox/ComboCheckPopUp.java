package com.framework.swing.component.combobox.support.checkbox;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.plaf.basic.BasicComboPopup;

import com.framework.swing.component.combobox.CheckBoxCombobox;

/**
 * ComboCheckPopUp
 * 
 * @author JiangGengchao
 * @datae 2018年1月8日
 */
@SuppressWarnings("serial")
public class ComboCheckPopUp extends BasicComboPopup {

	private boolean isMultiSel = true;
	@SuppressWarnings("unused")
	private CheckBoxCombobox combobox;

	public ComboCheckPopUp(CheckBoxCombobox combobox) {
		super(combobox);
		this.combobox = combobox;
	}

	@Override
	protected MouseListener createListMouseListener() {
		return new CheckBoxListMouseHandler();
	}

	@Override
	protected KeyListener createKeyListener() {
		return new CheckBoxKeyHandler();
	}

	protected class CheckBoxKeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			// ctrl
			isMultiSel = e.isControlDown();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// ctrl
			isMultiSel = e.isControlDown();
		}

	}

	private class CheckBoxListMouseHandler extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent anEvent) {
			int index = list.getSelectedIndex();
			ComboCheckBoxEntry item = (ComboCheckBoxEntry) list.getModel().getElementAt(index);
			boolean checked = !item.getChecked();
			int size = list.getModel().getSize();

			if (isMultiSel) {
				if (CheckBoxCombobox.SELECT_ALL.equals(item.getUserObject())) {
					for (int i = 0; i < size; i++) {
						ComboCheckBoxEntry ccbe = (ComboCheckBoxEntry) list.getModel().getElementAt(i);

						if (ccbe.getState() == true) {
							ccbe.setChecked(checked);
						}
					}
				} else {
					if (item.getState() == true) {
						// 按ctrl 进行挨个选。
						item.setChecked(checked);
						// 则全选不能选中了。
						for (int i = 0; i < size; i++) {
							ComboCheckBoxEntry allSelCcbe = (ComboCheckBoxEntry) list.getModel().getElementAt(i);

							if (allSelCcbe.getState() == true && CheckBoxCombobox.SELECT_ALL.equals(allSelCcbe.getUserObject())) {
								allSelCcbe.setChecked(false);
								break;
							}
						}
					}
				}

			} else {
				// 全选
				if (CheckBoxCombobox.SELECT_ALL.equals(item.getUserObject())) {
					for (int i = 0; i < size; i++) {
						ComboCheckBoxEntry ccbe = (ComboCheckBoxEntry) list.getModel().getElementAt(i);

						if (ccbe.getState() == true) {
							ccbe.setChecked(checked);
						}
					}
				} else {
					// if(item.getState() == true){
					// item.setChecked(checked);
					// }
					for (int i = 0; i < size; i++) {
						ComboCheckBoxEntry ccbe = (ComboCheckBoxEntry) list.getModel().getElementAt(i);
						if (ccbe.getState() == true) {
							ccbe.setChecked(false);
						}
					}
					item.setChecked(true);
				}
			}
			updateListBoxSelectionForEvent(anEvent, false);
			Rectangle rect = list.getCellBounds(0, size - 1);
			list.repaint(rect);
		}

		@Override
		public void mouseReleased(MouseEvent anEvent) {
			if (!isMultiSel) {
				ComboCheckBoxEntry item = (ComboCheckBoxEntry) list.getSelectedValue();
				if (item.getChecked()) {
					comboBox.setSelectedIndex(list.getSelectedIndex());
					comboBox.setPopupVisible(false);
				}
			}
			comboBox.repaint();
		}
	}

}
