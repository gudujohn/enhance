package com.enhance.swing.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.enhance.common.util.Detect;

public class EnhanceSelectTable extends JTable implements MouseListener {
	private static final long serialVersionUID = 7297874376294846584L;

	private JMenuItem removeSelected = null, removeAll = null;
	private JPopupMenu pop;

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
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		if (str.equals(removeSelected.getText())) { // Remove Selected
			int[] selectRowIndexs = this.getSelectedRows();
			for (int selectRowIndex : selectRowIndexs) {
				model.removeRow(selectRowIndex);
			}
		} else if (str.equals(removeAll.getText())) { // Remove All
			for (int i = this.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}
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
}
