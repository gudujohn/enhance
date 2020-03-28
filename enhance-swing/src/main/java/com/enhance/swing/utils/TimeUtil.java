package com.enhance.swing.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class TimeUtil {
	/**
	 * 获取当前年份
	 * 
	 * @return int
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 * 
	 * @return int
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前日子
	 * 
	 * @return int
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 获取当前小时
	 * 
	 * @return int
	 */
	public static int getCurrentHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前分钟
	 * 
	 * @return int
	 */
	public static int getCurrentMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 获取当前秒
	 * 
	 * @return int
	 */
	public static int getCurrentSecond() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}

	/**
	 * 获取当前毫秒
	 * 
	 * @return int
	 */
	public static int getCurrentMilliSecond() {
		return Calendar.getInstance().get(Calendar.MILLISECOND);
	}

	/**
	 * 获取当前日期，格式为YY-MM-DD
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		int currentYear = getCurrentYear();
		int currentMonth = getCurrentMonth();
		int currentDay = getCurrentDay();

		String currentYearStr = "" + currentYear;
		String currentMonthStr = "";
		String currentDayStr = "";
		if (currentMonth < 10) {
			currentMonthStr = "0" + currentMonth;
		} else {
			currentMonthStr = "" + currentMonth;
		}

		if (currentDay < 10) {
			currentDayStr = "0" + currentDay;
		} else {
			currentDayStr = "" + currentDay;
		}
		return currentYearStr + "-" + currentMonthStr + "-" + currentDayStr;
	}

	/**
	 * 获取某个日期的字符串格式，格式为YY-MM-DD
	 * 
	 * @return String
	 */
	public static String getDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentDay = calendar.get(Calendar.DATE);

		String currentYearStr = "" + currentYear;
		String currentMonthStr = "";
		String currentDayStr = "";
		if (currentMonth < 10) {
			currentMonthStr = "0" + currentMonth;
		} else {
			currentMonthStr = "" + currentMonth;
		}

		if (currentDay < 10) {
			currentDayStr = "0" + currentDay;
		} else {
			currentDayStr = "" + currentDay;
		}
		return currentYearStr + "-" + currentMonthStr + "-" + currentDayStr;
	}

	/**
	 * 获取当前完整时间，格式为YY-MM-DD HH：MM：SS
	 * 
	 * @return String
	 */
	public static String getCurrentTime() {
		int currentYear = getCurrentYear();
		int currentMonth = getCurrentMonth();
		int currentDay = getCurrentDay();
		int currentHour = getCurrentHour();
		int currentMinute = getCurrentMinute();
		int currentSecond = getCurrentSecond();

		String currentYearStr = "" + currentYear;
		String currentMonthStr = "";
		String currentDayStr = "";
		String currentHourStr = "";
		String currentMinuteStr = "";
		String currentSecondStr = "";
		if (currentMonth < 10) {
			currentMonthStr = "0" + currentMonth;
		} else {
			currentMonthStr = "" + currentMonth;
		}

		if (currentDay < 10) {
			currentDayStr = "0" + currentDay;
		} else {
			currentDayStr = "" + currentDay;
		}

		if (currentHour < 10) {
			currentHourStr = "0" + currentHour;
		} else {
			currentHourStr = "" + currentHour;
		}

		if (currentMinute < 10) {
			currentMinuteStr = "0" + currentMinute;
		} else {
			currentMinuteStr = "" + currentMinute;
		}

		if (currentSecond < 10) {
			currentSecondStr = "0" + currentSecond;
		} else {
			currentSecondStr = "" + currentSecond;
		}
		return currentYearStr + "-" + currentMonthStr + "-" + currentDayStr + " " + currentHourStr + ":" + currentMinuteStr + ":" + currentSecondStr;
	}

	/**
	 * 获取某时刻的完整时间，格式为YY-MM-DD HH：MM：SS
	 * 
	 * @return String
	 */
	public static String getTime(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentDay = calendar.get(Calendar.DATE);
		int currentHour = calendar.get(Calendar.HOUR);
		int currentMinute = calendar.get(Calendar.MINUTE);
		int currentSecond = calendar.get(Calendar.SECOND);

		String currentYearStr = "" + currentYear;
		String currentMonthStr = "";
		String currentDayStr = "";
		String currentHourStr = "";
		String currentMinuteStr = "";
		String currentSecondStr = "";
		if (currentMonth < 10) {
			currentMonthStr = "0" + currentMonth;
		} else {
			currentMonthStr = "" + currentMonth;
		}

		if (currentDay < 10) {
			currentDayStr = "0" + currentDay;
		} else {
			currentDayStr = "" + currentDay;
		}

		if (currentHour < 10) {
			currentHourStr = "0" + currentHour;
		} else {
			currentHourStr = "" + currentHour;
		}

		if (currentMinute < 10) {
			currentMinuteStr = "0" + currentMinute;
		} else {
			currentMinuteStr = "" + currentMinute;
		}

		if (currentSecond < 10) {
			currentSecondStr = "0" + currentSecond;
		} else {
			currentSecondStr = "" + currentSecond;
		}
		return currentYearStr + "-" + currentMonthStr + "-" + currentDayStr + " " + currentHourStr + ":" + currentMinuteStr + ":" + currentSecondStr;
	}

	/**
	 * 获取今天是一个星期的第几天
	 * 
	 * @return int
	 */
	public static int getCurrentDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取某天是一个星期的第几天
	 * 
	 * @param year  int
	 * @param month int
	 * @param date  int
	 * @return int
	 */
	public static int getDayOfWeek(int year, int month, int date) {
		Calendar calendar = new GregorianCalendar(year, month - 1, date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取今天是一年的第几个星期
	 *
	 * @return int
	 */
	public static int getCurrentWeekOfYear() {
		return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取某天是一年的第几个星期
	 * 
	 * @param year  int
	 * @param month int
	 * @param date  int
	 * @return int
	 */
	public static int getWeekOfYear(int year, int month, int date) {
		Calendar calendar = new GregorianCalendar(year, month - 1, date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 根据完整的时间，获取日期对象
	 * 
	 * @param time String
	 * @return Date
	 * @throws NumberFormatException
	 */
	public static Date parseToTime(String time) throws NumberFormatException {
		time = time.trim();
		StringTokenizer st = new StringTokenizer(time, "-/ :", false);
		String[] timeArray = new String[10];
		Date date = null;
		try {
			int dataCount = st.countTokens();
			for (int i = 0; i < dataCount; i++) {
				timeArray[i] = st.nextToken().trim();
			}
			if (dataCount < 6 && dataCount >= 3) {
				int part1 = Integer.parseInt(timeArray[0]);
				int month = Integer.parseInt(timeArray[1]);
				int part3 = Integer.parseInt(timeArray[2]);
				int year = 0;
				int day = 0;
				if (part1 > 1000) {
					year = part1;
					day = part3;
				} else {
					year = part3;
					day = part1;
				}
				Calendar mCalendar = new GregorianCalendar(year, month - 1, day);
				date = mCalendar.getTime();
			} else if (dataCount == 6) {
				int part1 = Integer.parseInt(timeArray[0]);
				int month = Integer.parseInt(timeArray[1]);
				int part3 = Integer.parseInt(timeArray[2]);
				int year = 0;
				int day = 0;
				if (part1 > 1000) {
					year = part1;
					day = part3;
				} else {
					year = part3;
					day = part1;
				}
				Calendar calendar = new GregorianCalendar(year, month - 1, day, Integer.parseInt(timeArray[3]), Integer.parseInt(timeArray[4]), Integer.parseInt(timeArray[5]));
				date = calendar.getTime();
			} else {
				throw new NumberFormatException("'" + time + "' is a invalid date format!");
			}

		} catch (NoSuchElementException ex) {
			ex.printStackTrace();
			return null;
		} catch (NumberFormatException ex) {
			throw new NumberFormatException("'" + time + "' is a invalid date format!");
		}
		return date;
	}

	/**
	 * 根据完整的时间，获取GregorianCalendar对象
	 * 
	 * @param time String
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar parseToCalendar(String time) {
		GregorianCalendar gc = new GregorianCalendar();
		Date date = TimeUtil.parseToTime(time);
		gc.setTime(date);
		return gc;
	}

	public static String getTimeInterval(long startTimeInMillis, long endTimeInMillis) {
		long timeInMillis = (endTimeInMillis - startTimeInMillis) / 1000;
		if (timeInMillis < 0) {
			timeInMillis = Math.abs(timeInMillis);
		}
		long hours = timeInMillis / 3600;
		timeInMillis = timeInMillis - (hours * 3600);
		long minutes = timeInMillis / 60;
		timeInMillis = timeInMillis - (minutes * 60);
		long seconds = timeInMillis;

		String hoursStr = "";
		if (hours < 10) {
			hoursStr = "0" + hours;
		} else {
			hoursStr = "" + hours;
		}

		String minutesStr = "";
		if (minutes < 10) {
			minutesStr = "0" + minutes;
		} else {
			minutesStr = "" + minutes;
		}

		String secondsStr = "";
		if (seconds < 10) {
			secondsStr = "0" + seconds;
		} else {
			secondsStr = "" + seconds;
		}
		return hoursStr + ":" + minutesStr + ":" + secondsStr;
	}

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {

		}
		long t2 = System.currentTimeMillis();
		System.out.println(TimeUtil.getTimeInterval(t1, t2));
	}
}
