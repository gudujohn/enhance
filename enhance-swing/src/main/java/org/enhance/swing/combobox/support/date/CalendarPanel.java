package org.enhance.swing.combobox.support.date;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * 日历面板
 * 
 * @author JiangGengchao
 * @datae 2018年1月8日
 */
@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {

	private static Border selectedBorder = new LineBorder(Color.black);
	private static Border unselectedBorder = new EmptyBorder(selectedBorder.getBorderInsets(new JLabel()));

	private Calendar calendar = null;
	private JLabel monthLabel = null;
	private JPanel days = null;
	private MouseListener dayBttListener = null;
	private boolean isSupportDateChangeListener = false;

	private Color selectedBackground = UIManager.getColor("ComboBox.selectionBackground");
	private Color selectedForeground = UIManager.getColor("ComboBox.selectionForeground");
	private Color background = UIManager.getColor("ComboBox.background");
	private Color foreground = UIManager.getColor("ComboBox.foreground");

	private Date selectedDate = null;
	/**
	 * 年月格式
	 */
	final SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

	public CalendarPanel() {
		this(null);
	}

	public CalendarPanel(Date selectedDate) {
		this.selectedDate = selectedDate;
		this.calendar = Calendar.getInstance();
		this.dayBttListener = createDayBttListener();

		// << < yyyy/MM/dd > >>
		JPanel pNorth = new JPanel();
		pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.X_AXIS));
		pNorth.setBackground(new Color(0, 0, 128));
		pNorth.setForeground(Color.white);
		pNorth.setPreferredSize(new Dimension(1, 25));

		JLabel label;
		label = createSkipButton(Calendar.YEAR, -1);
		label.setText("<<");
		pNorth.add(Box.createHorizontalStrut(12));
		pNorth.add(label);
		pNorth.add(Box.createHorizontalStrut(12));

		label = createSkipButton(Calendar.MONTH, -1);
		label.setText("< ");
		pNorth.add(label);

		monthLabel = new JLabel("", JLabel.CENTER);
		monthLabel.setBackground(new Color(0, 0, 128));
		monthLabel.setForeground(Color.white);
		pNorth.add(Box.createHorizontalGlue());
		pNorth.add(monthLabel);
		pNorth.add(Box.createHorizontalGlue());

		label = createSkipButton(Calendar.MONTH, 1);
		label.setText(" >");
		pNorth.add(label);

		label = createSkipButton(Calendar.YEAR, 1);
		label.setText(">>");

		pNorth.add(Box.createHorizontalStrut(12));
		pNorth.add(label);
		pNorth.add(Box.createHorizontalStrut(12));

		// 星期日 星期一 星期二 星期三 星期四 星期五 星期六
		JPanel pWeeks = new JPanel(new GridLayout(0, 7));
		pWeeks.setBackground(background);
		pWeeks.setOpaque(true);
		DateFormatSymbols sy = new DateFormatSymbols(Locale.getDefault());
		String strWeeks[] = sy.getShortWeekdays();
		for (int i = 1; i <= 7; i++) {
			label = new JLabel();
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setForeground(foreground);
			label.setText(strWeeks[i].substring(strWeeks[i].length() - 1));
			pWeeks.add(label);
		}

		// 中间放日期的面板
		days = new JPanel(new GridLayout(0, 7));
		days.setBorder(new TopBottomLineBorder(Color.black));
		days.setBackground(background);
		days.setOpaque(true);
		JPanel pCenter = new JPanel(new BorderLayout());
		pCenter.setBackground(background);
		pCenter.setOpaque(true);
		pCenter.add(pWeeks, BorderLayout.NORTH);
		pCenter.add(days, BorderLayout.CENTER);

		// 显示今天的日期,直接单击图标跳到今天
		JLabel lbToday = new DateLabel(new Date(), false);
		lbToday.setForeground(foreground);
		lbToday.addMouseListener(dayBttListener);

		// add by zhou yi shen 2009/1/5 清空时间的button
		JLabel clearLabel = new JLabel("清空", JLabel.CENTER);
		clearLabel.setPreferredSize(new Dimension(40, 20));
		clearLabel.setForeground(Color.blue);
		clearLabel.addMouseListener(createClearLabel());
		JPanel pSouth = new JPanel(new BorderLayout());
		pSouth.setBackground(background);
		pSouth.setForeground(foreground);
		pSouth.add(lbToday, BorderLayout.CENTER);
		pSouth.add(clearLabel, BorderLayout.EAST);

		// renderer this
		setPreferredSize(new Dimension(280, 180));
		setForeground(foreground);
		setBackground(background);
		setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, pNorth);
		add(BorderLayout.CENTER, pCenter);
		add(BorderLayout.SOUTH, pSouth);

		updateDays();
	}

	/**
	 * 创建上一月,下一月,上一年,下一年"按钮"
	 * 
	 * @param field  int
	 * @param amount int
	 * @return JLabel
	 */
	private JLabel createSkipButton(final int field, final int amount) {
		JLabel label = new JLabel();
		label.setBorder(unselectedBorder);
		label.setBackground(new Color(0, 0, 128));
		label.setForeground(Color.white);
		label.setRequestFocusEnabled(false);
		label.addMouseListener(createSkipListener(field, amount));
		return label;
	}

	/**
	 * 更新日期
	 */
	private void updateDays() {
		// 更新月份
		monthLabel.setText(monthFormat.format(calendar.getTime()));

		days.removeAll();
		Calendar selectedCalendar = Calendar.getInstance();
		if (null != selectedDate) {
			selectedCalendar.setTime(selectedDate);
		}
		/**
		 * 1 2 3 4 5 6 7 2 3 4 5 6 7 8 9 10 11 12 13 8 9 10 11 12 13 14 15 16 17 18 19 14 15 16 17 18 19 20 21 22 23 24 25 20 21 22 23 24 25 26 27 28 29 30 31 26 27 28 29 30 31
		 */
		Calendar setupCalendar = (Calendar) calendar.clone();
		setupCalendar.set(Calendar.DAY_OF_MONTH, 1);
		int first = setupCalendar.get(Calendar.DAY_OF_WEEK);
		setupCalendar.add(Calendar.DATE, -first);

		boolean isCurrentMonth = false;
		for (int i = 0; i < 42; i++) {
			setupCalendar.add(Calendar.DATE, 1);
			JLabel label = new DateLabel(setupCalendar.getTime());
			label.setForeground(foreground);
			label.addMouseListener(dayBttListener);

			if ("1".equals(label.getText())) {
				isCurrentMonth = !isCurrentMonth;
			}
			label.setEnabled(isCurrentMonth);
			// 当前选择的日期
			if (null != selectedDate && setupCalendar.get(Calendar.YEAR) == selectedCalendar.get(Calendar.YEAR) && setupCalendar.get(Calendar.MONTH) == selectedCalendar.get(Calendar.MONTH)
					&& setupCalendar.get(Calendar.DAY_OF_MONTH) == selectedCalendar.get(Calendar.DAY_OF_MONTH)) {
				label.setBorder(new LineBorder(selectedBackground, 1));
			}
			days.add(label);
		}
		days.validate();
	}

	private EventListenerList listenerList = new EventListenerList();

	public void addDateChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}

	public void removeDateChangeListener(ChangeListener l) {
		listenerList.remove(ChangeListener.class, l);
	}

	private void fireDateChanged(ChangeEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				((ChangeListener) listeners[i + 1]).stateChanged(e);
			}
		}
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
		this.calendar.setTime(selectedDate);
		updateDays();
		if (isSupportDateChangeListener) {
			fireDateChanged(new ChangeEvent(selectedDate));
		}
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	private MouseListener createClearLabel() {
		return new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JLabel com = (JLabel) e.getComponent();
				if (isEnabled()) {
					com.setOpaque(false);
					com.setBackground(background);
					com.setForeground(foreground);
				}
				CalendarPanel.this.isSupportDateChangeListener = true;
				fireDateChanged(new ChangeEvent(" -- 请选择 --"));
				CalendarPanel.this.isSupportDateChangeListener = false;
				selectedDate = null;
				updateDays();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (isEnabled()) {
					JComponent com = (JComponent) e.getComponent();
					com.setOpaque(true);
					com.setBackground(new Color(206, 231, 255));
					com.setForeground(Color.red);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (isEnabled()) {
					JComponent com = (JComponent) e.getComponent();
					com.setOpaque(false);
					com.setBackground(background);
					com.setForeground(Color.blue);
				}
			}
		};
	}

	private MouseListener createSkipListener(final int field, final int amount) {
		return new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				calendar.add(field, amount);
				updateDays();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				JComponent com = (JComponent) e.getComponent();
				com.setBorder(selectedBorder);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				JComponent com = (JComponent) e.getComponent();
				com.setBorder(unselectedBorder);
			}
		};
	}

	private MouseListener createDayBttListener() {
		return new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DateLabel com = (DateLabel) e.getComponent();
				if (isEnabled()) {
					com.setOpaque(false);
					com.setBackground(background);
					com.setForeground(foreground);
				}
				CalendarPanel.this.isSupportDateChangeListener = true;
				CalendarPanel.this.setSelectedDate(com.getDate());
				CalendarPanel.this.isSupportDateChangeListener = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (isEnabled()) {
					JComponent com = (JComponent) e.getComponent();
					com.setOpaque(true);
					com.setBackground(selectedBackground);
					com.setForeground(selectedForeground);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (isEnabled()) {
					JComponent com = (JComponent) e.getComponent();
					com.setOpaque(false);
					com.setBackground(background);
					com.setForeground(foreground);
				}
			}
		};
	}
}
