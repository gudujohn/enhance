package org.enhance.swing.combobox;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import org.enhance.swing.combobox.support.date.DateComboBoxModel;
import org.enhance.swing.combobox.support.date.DateComboPopup;
import org.enhance.swing.combobox.support.date.DateDocument;
import org.enhance.swing.combobox.util.ComboboxUiUtil;

@SuppressWarnings("serial")
public class DateCombobox extends JComboBox<Object> {
	/**
	 * 日期格式类型
	 */
	public static final int STYLE_CN_DATE = 0;
	public static final int STYLE_CN_DATE1 = 1;
	public static final int STYLE_CN_DATETIME = 2;
	public static final int STYLE_CN_DATETIME1 = 3;
	/**
	 * 日期格式类型
	 */
	private int formatStyle = STYLE_CN_DATE;
	/**
	 * 当前设置日期格式
	 */
	private SimpleDateFormat dateFormat = null;

	/**
	 * 只有一个值的ComboBoxModel
	 */
	private DateComboBoxModel model = new DateComboBoxModel();
	private DateDocument dateDocument = null;
	private DateComboPopup popup;

	/**
	 * 构造式
	 */
	public DateCombobox() throws UnsupportedOperationException {
		this(STYLE_CN_DATE, null);
	}

	/**
	 * 为了可以显示开始和结束时间控件（意义不大）
	 * 
	 * @param NULL_DATE
	 * @throws UnsupportedOperationException
	 */
	public DateCombobox(String NULL_DATE) throws UnsupportedOperationException {
		this(STYLE_CN_DATE, NULL_DATE);
	}

	public DateCombobox(int formatStyle) throws UnsupportedOperationException {
		this(formatStyle, null, null);
	}

	public DateCombobox(int formatStyle, String NULL_DATE) throws UnsupportedOperationException {
		this(formatStyle, null, NULL_DATE);
	}

	@SuppressWarnings("unchecked")
	public DateCombobox(int formatStyle, Date initialDatetime, String NULL_DATE) throws UnsupportedOperationException {

		if (NULL_DATE != null) {
			model.setNULL_DATE(NULL_DATE);
		}
		this.setStyle(formatStyle);
		// 设置可编辑
		this.setEditable(true);
		// 设置编辑器属性(只能输入正确日期)
		JTextField textField = ((JTextField) getEditor().getEditorComponent());
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		dateDocument = new DateDocument(textField, this.dateFormat);
		textField.setDocument(dateDocument);
		// 设置Model为单值Model
		this.setModel(model);
		// 设置当前选择日期
		this.setSelectedItem(initialDatetime);

		// Delete键 to Delete
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (DateCombobox.this.isEnabled() == false) {
					return;
				}
				if (e.getKeyChar() == (char) KeyEvent.VK_BACK_SPACE) {
					setSelectedItem(null);
				} else if (e.getKeyChar() == (char) KeyEvent.VK_DELETE) {
					setSelectedItem(null);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

		});
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (DateCombobox.this.isEnabled() == false) {
					return;
				}
				if (e.getKeyChar() == (char) KeyEvent.VK_BACK_SPACE) {
					setSelectedItem(null);
				} else if (e.getKeyChar() == (char) KeyEvent.VK_DELETE) {
					setSelectedItem(null);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

		});

	}

	/**
	 * 设置日期格式 STYLE_CN_DATE STYLE_CN_DATE1 STYLE_CN_DATETIME STYLE_CN_DATETIME1
	 * 
	 * @param formatStyle int
	 */
	public void setStyle(int formatStyle) throws UnsupportedOperationException {
		this.formatStyle = formatStyle;
		dateFormat = getDateFormat(formatStyle);
		model.setDateFormat(dateFormat);
		if (dateDocument != null) {
			dateDocument.setDateFormat(dateFormat);
		}
	}

	/**
	 * 取得指定类型的日期格式
	 * 
	 * @param formatStyle int
	 * @return SimpleDateFormat
	 * @throws UnsupportedOperationException
	 */
	private static SimpleDateFormat getDateFormat(int formatStyle) throws UnsupportedOperationException {
		switch (formatStyle) {
		case STYLE_CN_DATE:
			return new SimpleDateFormat("yyyy/MM/dd");
		case STYLE_CN_DATE1:
			return new SimpleDateFormat("yyyy-MM-dd");
		case STYLE_CN_DATETIME:
			return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		case STYLE_CN_DATETIME1:
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		default:
			throw new UnsupportedOperationException("invalid formatStyle parameter!");
		}
	}

	/**
	 * 取得日期格式 STYLE_CN_DATE STYLE_CN_DATE1 STYLE_CN_DATETIME STYLE_CN_DATETIME1
	 * 
	 * @return int
	 */
	public int getStyle() {
		return formatStyle;
	}

	/**
	 * 取得当前选择日期的String 型
	 * 
	 * @return
	 */
	public String getSelectedDateString() {
		return getSelectedItem().toString();
	}

	/**
	 * 取得当前选择的日期
	 * 
	 * @return Date
	 */
	public Date getSelectedDate() {
		try {
			return dateFormat.parse(getSelectedItem().toString());
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 设置当前选择的日期
	 * 
	 * @return Date
	 */
	public void setSelectedDate(Date date) throws ParseException {
		if (date != null) {
			this.setSelectedItem(dateFormat.format(date));
		} else {
			this.setSelectedItem(null);
		}
	}

	@Override
	public void setSelectedItem(Object anObject) {
		model.setSelectedItem(anObject);
		super.setSelectedItem(anObject);
	}

	/**
	 * 更新UI
	 */
	@Override
	public void updateUI() {
		if (null == popup) {
			popup = new DateComboPopup(this);
		}
		ComboboxUiUtil.setUi(popup, this);
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

}
