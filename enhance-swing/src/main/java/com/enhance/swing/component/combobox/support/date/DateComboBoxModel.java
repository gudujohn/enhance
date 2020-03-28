package com.enhance.swing.component.combobox.support.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

@SuppressWarnings("rawtypes")
public class DateComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private static final long serialVersionUID = 1L;

	/** NULL */
	private String NULL_DATE = "-- 请选择 --";
	private SimpleDateFormat dateFormat;
	private String selectedDate = "";

	public DateComboBoxModel() {
	}

	@Override
	public void setSelectedItem(Object anItem) {
		if (anItem == null) {
			selectedDate = NULL_DATE;
			fireContentsChanged(this, -1, -1);
			return;
		}
		if (anItem instanceof Date) {
			try {
				selectedDate = this.dateFormat.format(anItem);
			} catch (Exception ex) {
				selectedDate = NULL_DATE;
				ex.printStackTrace();
			}
		} else {
			if (NULL_DATE.equals(anItem) == true) {
				selectedDate = NULL_DATE;
			} else {
				try {
					String strDate = anItem.toString().trim();
					if (strDate.length() != 10 && strDate.length() != 19) {
						return;
					}
					String pattern = dateFormat.toPattern();
					if (strDate.length() == 10 && pattern.length() == 19) {
						strDate = strDate + selectedDate.substring(10);
					}
					dateFormat.parse(strDate);
					selectedDate = strDate;
				} catch (Exception ex) {
					throw new UnsupportedOperationException("Invalid datetime: string [" + anItem + "], format is [" + dateFormat.toPattern() + "]. ");
				}
			}
		}
		fireContentsChanged(this, -1, -1);
	}

	@Override
	public Object getSelectedItem() {
		return selectedDate;
	}

	@Override
	public Object getElementAt(int index) {
		return selectedDate;
	}

	@Override
	public int getSize() {
		return 1;
	}

	public String getNULL_DATE() {
		return NULL_DATE;
	}

	public void setNULL_DATE(String nULL_DATE) {
		NULL_DATE = nULL_DATE;
	}

	public DateComboBoxModel(SimpleDateFormat dateFormat) {
		setDateFormat(dateFormat);
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
}
