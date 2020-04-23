package org.enhance.swing.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;

import org.enhance.common.util.Detect;

public class EnhanceSelectTable extends JTable implements MouseListener {
	private static final long serialVersionUID = 7297874376294846584L;

	private JMenuItem removeSelected = null, removeAll = null;
	private JPopupMenu pop;

	public EnhanceSelectTable() {
		super();
		init();
	}

	public EnhanceSelectTable(TableModel tm) {
		super(tm);
		init();
	}

	private void init() {
		this.addMouseListener(this);
		this.addMouseListener(this);
		removeSelected = new JMenuItem("Remove Selected");
		removeAll = new JMenuItem("Remove All");
		pop = new JPopupMenu();
		pop.add(removeSelected);
		pop.add(removeAll);
		removeSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(e);
			}
		});
		removeAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(e);
			}
		});
	}

	private void remove(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equals(removeSelected.getText())) { // Remove Selected
			DefaultTableModel model = (DefaultTableModel) this.getModel();
			int[] selectRowIndexs = this.getSelectedRows();
			for (int selectRowIndex : selectRowIndexs) {
				model.removeRow(selectRowIndex);
			}
		} else if (str.equals(removeAll.getText())) { // Remove All
			removeAllData();
		}
	}

	public void removeAllData() {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		for (int i = this.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			int[] selectIndex = this.getSelectedRows();
			if (Detect.notEmpty(selectIndex)) {
				removeSelected.setEnabled(true);
			} else {
				removeSelected.setEnabled(false);
			}
			if (this.getRowCount() > 0) {
				removeAll.setEnabled(true);
			} else {
				removeAll.setEnabled(false);
			}
			pop.show(this, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void addValue(String[] data) {
		((DefaultTableModel) this.getModel()).addRow(data);
	}

	public void addValue(String[][] datas) {
		for (String[] data : datas) {
			int rowIndex = getRowIndexByKey(data[0]);
			if (Detect.isNegative(rowIndex)) {
				addValue(data);
			} else {
				setValue(data[0], data[1]);
			}
		}
	}

	public String getValueByKey(String keyValue) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = model.getValueAt(i, 0).toString().trim();
			if (StringUtils.equals(key, keyValue)) {
				return model.getValueAt(i, 1).toString().trim();
			}
		}
		return StringUtils.EMPTY;
	}

	private int getRowIndexByKey(String keyValue) {
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = model.getValueAt(i, 0).toString().trim();
			if (StringUtils.equals(key, keyValue)) {
				return i;
			}
		}
		return -1;
	}

	public void setValue(String key, String value) {
		int rowIndex = getRowIndexByKey(key);
		if (Detect.isNegative(rowIndex)) {
			addValue(new String[] { key, value });
		} else {
			DefaultTableModel model = (DefaultTableModel) this.getModel();
			model.setValueAt(value, rowIndex, 1);
		}
	}

	public Map<String, String> getAll() {
		Map<String, String> data = new HashMap<>();
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = model.getValueAt(i, 0).toString().trim();
			String value = model.getValueAt(i, 1).toString().trim();
			data.put(key, value);
		}
		return data;
	}

}
