package com.enhance.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DateFormatUtil {

	private static transient Logger logger = LoggerFactory.getLogger(DateFormatUtil.class);

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DATETIME_FORMAT_NOSECOND = "yyyy-MM-dd HH:mm";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String COMPACT_DATA_FORMAT = "yyyyMMdd";

	public static final String COMPACT_DATETIME_FORMAT = "yyyyMMddHHmmss";

	public static final String YEARMONTH_DATA_FORMAT = "yyyyMM";

	public static final String RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	public static final String DATETIME_FORMAT_NOSPLIT = "yyyyMMdd HH:mm:ss";

	public static final String YEAR_DATA_FORMAT = "yyyy";

	private static final String TIME_FORMAT = "HH:mm";

	private static final String SECONDTIME_FORMAT = "HH:mm:SS";

	public static final String TIMEINTERVAL_YEAR = "year";
	public static final String TIMEINTERVAL_MONTH = "month";
	public static final String TIMEINTERVAL_WEEK = "week";
	public static final String TIMEINTERVAL_DAY = "day";
	public static final String TIMEINTERVAL_HOUR = "hour";
	public static final String TIMEINTERVAL_MINUTE = "minute";
	public static final String TIMEINTERVAL_SECOND = "second";

	private static ThreadLocal<DateFormat> datetimeFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(DATETIME_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> datetimeFormatWithNoSplit = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(DATETIME_FORMAT_NOSPLIT);
		}
	};

	private static ThreadLocal<DateFormat> datetimeFormatWithNoSecond = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(DATETIME_FORMAT_NOSECOND);
		}
	};

	private static ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> compactDateFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(COMPACT_DATA_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> compactDatetimeFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(COMPACT_DATETIME_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> yearMonthDateFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(YEARMONTH_DATA_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> rfc3339DateFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(RFC3339_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> yearDateFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(YEAR_DATA_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> timeFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(TIME_FORMAT);
		}
	};

	private static ThreadLocal<DateFormat> secondTimeFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected synchronized DateFormat initialValue() {
			return new SimpleDateFormat(SECONDTIME_FORMAT);
		}
	};

	public static DateFormat getDatetimeFormat() {
		return datetimeFormat.get();
	}

	public static DateFormat getDateFormat() {
		return dateFormat.get();
	}

	public static DateFormat getCompactDateFormat() {
		return compactDateFormat.get();
	}

	public static DateFormat getCompactDatetimeFormat() {
		return compactDatetimeFormat.get();
	}

	public static DateFormat getYearMonthDateFormat() {
		return yearMonthDateFormat.get();
	}

	public static DateFormat getRfc3339DateFormat() {
		return rfc3339DateFormat.get();
	}

	public static DateFormat getDatetimeFormatWithNoSecond() {
		return datetimeFormatWithNoSecond.get();
	}

	public static DateFormat getDatetimeFormatWithNoSplit() {
		return datetimeFormatWithNoSplit.get();
	}

	public static DateFormat getYearDateFormat() {
		return yearDateFormat.get();
	}

	public static DateFormat getTimeFormat() {
		return timeFormat.get();
	}

	public static DateFormat getSecondTimeFormat() {
		return secondTimeFormat.get();
	}

	public static Date toDatetime(String string) {
		if (DateFormatUtil.isExtDate(string)) {
			if (string.startsWith("'") && string.endsWith("'")) {
				string = string.substring(1, string.length() - 1);
			}
			return DateFormatUtil.parseExtDate(string);
		}
		try {
			return getDatetimeFormat().parse(string);
		} catch (ParseException e) {
			// logger.error("error", e);
			if (logger.isErrorEnabled()) {
				logger.error("日期格式转换失败: " + string);
			}
		}
		return null;
	}

	public static Date toDatetimeWithNoSecond(String string) {
		if (DateFormatUtil.isExtDate(string)) {
			if (string.startsWith("'") && string.endsWith("'")) {
				string = string.substring(1, string.length() - 1);
			}
			return DateFormatUtil.parseExtDate(string);
		}
		try {
			return getDatetimeFormatWithNoSecond().parse(string);
		} catch (ParseException e) {
			logger.error("error", e);
			if (logger.isErrorEnabled()) {
				logger.error("日期格式转换失败: " + string);
			}
		}
		return null;
	}

	// 形如：20151110 12:23:34
	public static Date toDatetimeFromStringWithNoSplit(String strDate) {
		String strEnd = strDate.substring(8);
		String strBegin = strDate.substring(0, 8);
		String year = strBegin.substring(0, 4);
		String month = strBegin.substring(4, 6);
		String day = strBegin.substring(6, 8);
		strBegin = year + "-" + month + "-" + day;
		strDate = strBegin + strEnd;
		Date parseDate = DateFormatUtil.toDatetime(strDate);
		return parseDate;
	}

	public static String toTimeString(Date date) {
		return date != null ? getTimeFormat().format(date) : StringUtils.EMPTY;
	}

	public static String toDatetimeString(Date date) {
		return date != null ? getDatetimeFormat().format(date) : StringUtils.EMPTY;
	}

	public static String toDatetimeStringWithNoSplit(Date date) {
		return date != null ? getDatetimeFormatWithNoSplit().format(date) : StringUtils.EMPTY;
	}

	public static Date toDate(String string) {
		try {
			if (string.startsWith("'") && string.endsWith("'")) {
				string = string.substring(1, string.length() - 1);
			}
			return getDateFormat().parse(string);
		} catch (ParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("日期格式转换失败: " + string);
			}
		}
		return null;
	}

	public static Date toDateByCompactFormat(String string) {
		try {
			if (string.startsWith("'") && string.endsWith("'")) {
				string = string.substring(1, string.length() - 1);
			}
			return getCompactDateFormat().parse(string);
		} catch (ParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("日期格式转换失败: " + string);
			}
		}
		return null;
	}

	public static Date toYearMonthDateFormat(String string) {
		try {
			if (string.startsWith("'") && string.endsWith("'")) {
				string = string.substring(1, string.length() - 1);
			}
			return getYearMonthDateFormat().parse(string);
		} catch (ParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("日期格式转换失败: " + string);
			}
		}
		return null;
	}

	public static boolean isExtDate(String string) {
		try {
			getRfc3339DateFormat().parse(string);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static Date parseExtDate(String string) {
		try {
			if (string.startsWith("'") && string.endsWith("'")) {
				string = string.substring(1, string.length() - 1);
			}
			return getRfc3339DateFormat().parse(string);
		} catch (ParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("日期格式转换失败: " + string);
			}
		}
		return null;
	}

	public static Date toYearDateFormat(String string) {
		try {
			if (string.startsWith("'") && string.endsWith("'")) {
				string = string.substring(1, string.length() - 1);
			}
			return getYearDateFormat().parse(string);
		} catch (ParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("日期格式转换失败: " + string);
			}
		}
		return null;
	}

	public static String toExtDateString(Date date) {
		return date != null ? getRfc3339DateFormat().format(date) : StringUtils.EMPTY;
	}

	public static String toDateString(Date date) {
		return date != null ? getDateFormat().format(date) : StringUtils.EMPTY;
	}

	public static String toCompactDateString(Date date) {
		return date != null ? getCompactDateFormat().format(date) : StringUtils.EMPTY;
	}

	public static String toCompactDatetimeString(Date date) {
		return null != date ? getCompactDatetimeFormat().format(date) : StringUtils.EMPTY;
	}

	public static String toYearMonthDateString(Date date) {
		return date != null ? getYearMonthDateFormat().format(date) : StringUtils.EMPTY;
	}

	public static String toDatetimeString(XMLGregorianCalendar c) {
		String dateStr = StringUtils.EMPTY;
		if (null != c) {
			dateStr = toDatetimeString(c.toGregorianCalendar().getTime());
		}
		return dateStr;
	}

	public static long dateDiff(Date startDate, Date endDate, String timeInterval) {
		if (timeInterval.equals(TIMEINTERVAL_YEAR)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			int startYear1 = calendar.get(Calendar.YEAR);
			calendar.setTime(endDate);
			int endYear2 = calendar.get(Calendar.YEAR);
			return endYear2 - startYear1;
		}

		if (timeInterval.equals(TIMEINTERVAL_MONTH)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			int startMonth = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
			calendar.setTime(endDate);
			int endMonth = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
			return endMonth - startMonth;
		}

		if (timeInterval.equals(TIMEINTERVAL_WEEK)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			int startWeek = calendar.get(Calendar.YEAR) * 52 + calendar.get(Calendar.WEEK_OF_YEAR);
			calendar.setTime(endDate);
			int endWeek = calendar.get(Calendar.YEAR) * 52 + calendar.get(Calendar.WEEK_OF_YEAR);
			return endWeek - startWeek;
		}

		if (timeInterval.equals(TIMEINTERVAL_DAY)) {
			double startDay = (double) startDate.getTime() / 1000 / 60 / 60 / 24;
			double endDay = (double) endDate.getTime() / 1000 / 60 / 60 / 24;
			return (long) (endDay - startDay);
		}

		if (timeInterval.equals(TIMEINTERVAL_HOUR)) {
			double startHour = (double) startDate.getTime() / 1000 / 60 / 60;
			double endHour = (double) endDate.getTime() / 1000 / 60 / 60;
			return (long) (endHour - startHour);
		}

		if (timeInterval.equals(TIMEINTERVAL_MINUTE)) {
			double startMinute = (double) startDate.getTime() / 1000 / 60;
			double endMinute = (double) endDate.getTime() / 1000 / 60;
			return (long) (endMinute - startMinute);
		}

		if (timeInterval.equals(TIMEINTERVAL_SECOND)) {
			double startSecond = (double) startDate.getTime() / 1000;
			double endSecond = (double) endDate.getTime() / 1000;
			return (long) (endSecond - startSecond);
		}

		return endDate.getTime() - startDate.getTime();
	}

	public static String getDateDiffByDhmFormat(Date startDate, Date endDate) {
		long days = dateDiff(startDate, endDate, TIMEINTERVAL_DAY);
		long hours = dateDiff(startDate, endDate, TIMEINTERVAL_HOUR);
		long minutes = dateDiff(startDate, endDate, TIMEINTERVAL_MINUTE);
		days = !Detect.isNegative(days) ? days : 0;
		hours = !Detect.isNegative(hours) ? hours : 0;
		minutes = !Detect.isNegative(minutes) ? minutes : 0;
		return days + "天" + hours % 24 + "时" + minutes % 60 + "分";
	}

	public static String getDayCountByDhmFormat(double dayCount) {
		long days = new Double(dayCount).longValue();
		long hours = new Double((dayCount - days) * 24).longValue();
		long minutes = new Double((dayCount * 24 * 60 - hours * 60) % 60).longValue();
		days = !Detect.isNegative(days) ? days : 0;
		hours = !Detect.isNegative(hours) ? hours : 0;
		minutes = !Detect.isNegative(minutes) ? minutes : 0;
		return days + "天" + hours + "时" + minutes + "分";
	}

	/**
	 * 增加时间日期
	 * 
	 * @param originDate 要增加的日期
	 * @param incValue   增加的值
	 * @param incFlag    增加标志， 与Calendar的常量一致, 使用时用Calendar.YEAR, Calendar.MONTH等
	 * @return
	 */
	public static Date incrementDate(Date originDate, int incValue, int incFlag) {
		Calendar utilCal = Calendar.getInstance();
		if (originDate == null) {
			throw new IllegalArgumentException(" 日期不能为空");
		}
		utilCal.setTime(originDate);
		utilCal.add(incFlag, incValue);
		return utilCal.getTime();
	}

	/**
	 * 在给定的日期上增加若干天数得到的工作日
	 * 
	 * @param originDate 给定的日期
	 * @param incValue   增加的工作日数目
	 * @param incFlag    增加标志，与Calendar的常量一致, 使用时用Calendar.YEAR, Calendar.MONTH等
	 * @return
	 */
	public static Date incrementWorkdays(Date originDate, int incValue, int incFlag) {
		Calendar utilCal = Calendar.getInstance();
		if (originDate == null) {
			throw new IllegalArgumentException(" 日期不能为空");
		}
		utilCal.setTime(originDate);
		for (int i = 0; i < incValue; i++) {
			// 判断当天是否为周末，如果是周末加1
			if (Calendar.SATURDAY == utilCal.get(Calendar.SATURDAY) || Calendar.SUNDAY == utilCal.get(Calendar.SUNDAY)) {
				incValue = incValue + 1;
				utilCal.set(Calendar.DATE, utilCal.get(Calendar.DATE) + 1);
				continue;
			}
			utilCal.set(Calendar.DATE, utilCal.get(Calendar.DATE) + 1);
			// 当天数加1后， 判断是否为周末 如果是周末再加1
			if (Calendar.SATURDAY == utilCal.get(Calendar.SATURDAY) || Calendar.SUNDAY == utilCal.get(Calendar.SUNDAY)) {
				incValue = incValue + 1;
				utilCal.set(Calendar.DATE, utilCal.get(Calendar.DATE) + 1);
				continue;
			}
		}
		return utilCal.getTime();
	}

	public static void main(String[] args) {
		// Date date1 = DateFormatUtil.toDate("2012-9-1");
		// System.out.println(incrementDate(date1, 2, Calendar.DAY_OF_MONTH));
		// System.out.println(date1);
		// 处理下destFtpRoot（目标目录），需要根据动态处理下第四级目录
		String destFtpRoot = "/zhejiang/jiake/hangzhou/four_date/ziguanhuadan";
		System.out.println(destFtpRoot);
		if (destFtpRoot.contains("four_date")) {
			Date date = new Date();
			String fourdateDir = DateFormatUtil.toCompactDateString(date);
			destFtpRoot = destFtpRoot.replace("four_date", fourdateDir);
		}
		System.out.println(destFtpRoot);
	}

	public static List<Long> calculate(Date startDate, Date endDate) {
		List<Long> monthAndDay = new ArrayList<>();
		long months = 0;
		if (startDate.after(endDate))
			return null;
		Calendar firstDay = Calendar.getInstance();
		Calendar lastDay = Calendar.getInstance();
		firstDay.setTime(startDate);
		lastDay.setTime(endDate);
		// 算出天数总差值
		long allDays = dateDiff(startDate, endDate, TIMEINTERVAL_DAY);
		Calendar loopEndDay = calculateLoopEndOfDate(firstDay, lastDay);
		int month = firstDay.get(Calendar.MONTH);
		while (!firstDay.equals(loopEndDay)) {
			firstDay.add(Calendar.DAY_OF_MONTH, 1);
			allDays--;
			if (month != firstDay.get(Calendar.MONTH)) {
				month = firstDay.get(Calendar.MONTH);
				months = months + 1;
			}
		}
		monthAndDay.add(months);
		monthAndDay.add(allDays);
		return monthAndDay;

	}

	private static Calendar calculateLoopEndOfDate(Calendar startDate, Calendar endDate) {
		int year = endDate.get(Calendar.YEAR);
		int month = endDate.get(Calendar.MONTH);
		int day = startDate.get(Calendar.DAY_OF_MONTH);
		int maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year, month, 1));

		if (year > startDate.get(Calendar.YEAR)) {
			if (month == Calendar.JANUARY) {
				year -= 1;
				month = Calendar.DECEMBER;
			} else {
				if (day > maxDaysInMonth) {
					month -= 1;
					endDate.set(year, month, 1);
					day = getMaxDaysOfMonth(new GregorianCalendar(year, month, 1));
				} else {
					if (day > endDate.get(Calendar.DAY_OF_MONTH)) {
						month -= 1;
						endDate.set(year, month, 1);
						maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year, month, 1));
						if (day > maxDaysInMonth) {
							day = maxDaysInMonth;
						}
					}
				}
			}
		} else {
			if (day > maxDaysInMonth) {
				month -= 1;
				endDate.set(year, month, 1);
				day = getMaxDaysOfMonth(new GregorianCalendar(year, month, 1));
			} else {
				if (day > endDate.get(Calendar.DAY_OF_MONTH)) {
					month -= 1;
					endDate.set(year, month, 1);
					maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year, month, 1));
					if (day > maxDaysInMonth) {
						day = maxDaysInMonth;
					}
				}
			}
		}

		return new GregorianCalendar(year, month, day);
	}

	// 获取一月最大天数,考虑年份是否为润年
	private static int getMaxDaysOfMonth(GregorianCalendar date) {
		int month = date.get(Calendar.MONTH);
		int maxDays = 0;
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.MARCH:
		case Calendar.MAY:
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.OCTOBER:
		case Calendar.DECEMBER:
			maxDays = 31;
			break;
		case Calendar.APRIL:
		case Calendar.JUNE:
		case Calendar.SEPTEMBER:
		case Calendar.NOVEMBER:
			maxDays = 30;
			break;
		case Calendar.FEBRUARY:
			if (date.isLeapYear(date.get(Calendar.YEAR))) {
				maxDays = 29;
			} else {
				maxDays = 28;
			}
			break;
		}
		return maxDays;
	}

	/**
	 * 
	 * @param localeDate 形如：Thu May 28 18:23:17 CST 2015
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String toDateTimeStringByLocale(String localeDate) {
		String formatDate = "";
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatDate = sdf2.format(sdf1.parse(localeDate));
		} catch (Exception e) {

		}
		return formatDate;
	}
}
