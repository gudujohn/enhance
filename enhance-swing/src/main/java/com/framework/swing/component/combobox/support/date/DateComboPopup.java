package com.framework.swing.component.combobox.support.date;

import java.awt.BorderLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboPopup;

import com.framework.swing.component.combobox.DateCombobox;

/**
 * DatePopup 选择框弹出的日期选择面板
 * 
 * @author JiangGengchao
 * @datae 2018年1月8日
 */
@SuppressWarnings("serial")
public class DateComboPopup extends BasicComboPopup implements ChangeListener {

	private CalendarPanel calendarPanel = null;
	private DateCombobox combobox;

	public DateComboPopup(DateCombobox box) {
		super(box);
		this.combobox = box;
		setLayout(new BorderLayout());
		calendarPanel = new CalendarPanel();
		calendarPanel.addDateChangeListener(this);
		add(calendarPanel, BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder());
	}

	/**
	 * 显示弹出面板
	 */
	@Override
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (propertyName.equals("visible")) {
			if (oldValue.equals(Boolean.FALSE) && newValue.equals(Boolean.TRUE)) { // SHOW
				try {
					String strDate = comboBox.getSelectedItem().toString();
					Date selectionDate = combobox.getDateFormat().parse(strDate);
					calendarPanel.setSelectedDate(selectionDate);
				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			} else if (oldValue.equals(Boolean.TRUE) && newValue.equals(Boolean.FALSE)) { // HIDE
			}
		}
		super.firePropertyChange(propertyName, oldValue, newValue);
	}

	/*
	 * CalendarPanel.setSelectedDate 会调用这个方法 add comment by zhou yi shen
	 * 
	 * @see
	 * javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof Date == true) {
			Date selectedDate = (Date) e.getSource();
			String strDate = combobox.getDateFormat().format(selectedDate);
			if (comboBox.isEditable() && comboBox.getEditor() != null) {
				comboBox.configureEditor(comboBox.getEditor(), strDate);
			}
			comboBox.setSelectedItem(strDate);
			comboBox.setPopupVisible(false);
		} else {
			if (comboBox.isEditable() && comboBox.getEditor() != null) {
				comboBox.configureEditor(comboBox.getEditor(), ((DateComboBoxModel) combobox.getModel()).getNULL_DATE());
			}
			comboBox.setSelectedItem(null);
			comboBox.setPopupVisible(false);
		}
	}
}