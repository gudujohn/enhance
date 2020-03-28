package com.framework.swing.component.combobox.support.date;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class DateLabel extends JLabel {

	private Date date = null;

	/**
	 * 日期格式（TODAY/TIP用）
	 */
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	/**
	 * 日格式
	 */
	private final SimpleDateFormat dayFormat = new SimpleDateFormat("d");

	public DateLabel(Date date) {
		this(date, true);
	}

	public DateLabel(Date date, boolean isSmallLabel) {
		setPreferredSize(new Dimension(40, 20));
		setToolTipText(dateFormat.format(date));
		this.date = date;
		if (isSmallLabel) {
			setHorizontalAlignment(JLabel.CENTER);
			setText(dayFormat.format(date));
		} else {
			setText("Today:" + dateFormat.format(new Date()));
			setHorizontalAlignment(JLabel.LEFT);
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
