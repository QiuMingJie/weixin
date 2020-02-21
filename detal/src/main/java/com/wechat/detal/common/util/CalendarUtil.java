package com.wechat.detal.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarUtil {

	public static void main(String[] args) throws ParseException {
		String start = "2019-07-07";
		
		String end = "2020-2-31";
		String key = "month";
		List<Map<String, String>> monthsList = getMonthsList(StringTransDate(start, "yyyy-MM"),StringTransDate(end, "yyyy-MM"),key,"yyyy-MM");
		for (Map<String, String> map : monthsList) {
			String date = map.get(key);
			map.put("startDate", getBeginDayOfMonth(date));
			map.put("endDate", getEndDayOfMonth(date));
		}
		/*String prevWeekTime = getWeekTime(StringTransDate(start, "yyyy-MM-dd"), -6);*/
		Integer prevWeekTime = getDateSubtract("2019-07-01", "2019-07-07","yyyy-MM-dd");
		System.out.println(StringTransDate(end,"yyyy-MM"));
	}

	public final static int MONDAY = 2;

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获取本周日的日期
	 * 
	 * @return
	 * @author 豪
	 * @date 2018年5月26日
	 */
	public static String getCurrentWeekday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public static String getMondayOFWeek() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取上周以前得日期
	 * 
	 * @author 豪
	 * @date 2018年7月26日
	 */
	public static String getPrevWeekTime(Date date, Integer total) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		int num;
		if (dayOfWeek == 1) {
			num = 0;
		}else{
			num = 1 - dayOfWeek;
		}
		cd.add(Calendar.DATE, num - total);
		Date date2 = cd.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(date2);
		return preMonday;
	}

	/**
	 * 获取上周以前得日期
	 *
	 * @author 豪
	 * @date 2018年7月26日
	 */
	public static String getWeekTime(Date date, Integer total) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		int num;
		if (dayOfWeek == 1) {
			num = 0;
			cd.add(Calendar.DATE, num - total);
		} else if(dayOfWeek == 0){
			num = 0 ;
			if(String.valueOf(total).contains("-")){
				cd.add(Calendar.DATE, num);
			}else{
				cd.add(Calendar.DATE, num - 6);
			}
		}else{
			num = 1 - dayOfWeek;
			cd.add(Calendar.DATE, num - total);
		}
		Date date2 = cd.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(date2);
		return preMonday;
	}

	/**
	 * 获取上周以前得日期
	 *
	 * @author 豪
	 * @date 2018年7月26日
	 */
	public static Date getWeekTimeDate(Date date, Integer total) throws ParseException {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		int num;
		if (dayOfWeek == 1) {
			num = 0;
			cd.add(Calendar.DATE, num - total);
		} else if(dayOfWeek == 0){
			num = 0 ;
			if(String.valueOf(total).contains("-")){
				cd.add(Calendar.DATE, num);
			}else{
				cd.add(Calendar.DATE, num - 6);
			}
		}else{
			num = 1 - dayOfWeek;
			cd.add(Calendar.DATE, num - total);
		}
		Date date2 = cd.getTime();
		return CalendarUtil.getDateFormat(date2,"yyyy-MM-dd");
	}

	/**
	 * 获取当前日期是一年中得第几周数
	 * @author 豪
	 * @date 2019年3月21日
	 */
	public static int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(MONDAY);// 设置周一为一周的第一天
		cal.setTime(date);
		int week = Calendar.WEEK_OF_YEAR;
		int num = cal.get(week);
		cal.getFirstDayOfWeek();
		return num;
	}

	/**
	 * String转date型日期
	 * 
	 * @author 豪
	 * @date 2019年3月21日
	 */
	public static Date StringTransDate(String date, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(date);
	}

	/**
	 * date转String型日期
	 * @author 豪
	 * @date 2019年3月21日
	 */
	public static String DateTransString(Date date, String format){
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 功能描述:DATE日期获取指定格式
	 * @auther: 豪
	 * @date: 2019/7/7 22:10
	 */
	public static Date getDateFormat(Date date, String format) throws ParseException {
		String s = new SimpleDateFormat(format).format(date);
		Date date1 = new SimpleDateFormat(format).parse(s);
		return date1;
	}

	/**
	 * 功能描述:string日期获取指定格式
	 * @auther: 豪
	 * @date: 2019/7/7 22:10
	 */
	public static String getStringFormat(String date, String format) throws ParseException {
		Date date1 = new SimpleDateFormat(format).parse(date);
		String s = new SimpleDateFormat(format).format(date1);
		return s;
	}

	/**
	 * 功能描述:
	 * @auther: 豪
	 * @date: 2019/7/13 11:18
	 */
	public static Integer getDateSubtract(String startDate,String endDate, String format) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long diff;
		//获得两个时间的毫秒时间差异
		diff = sd.parse(endDate).getTime() - sd.parse(startDate).getTime();
		long day = diff/nd;//计算差多少天
		day+=1;
		long hour = diff%nd/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
		Integer days = Integer.valueOf(String.valueOf(day));
		return days;
	}

	/**
	 * 通过时间段获取相应得月份得列表
	 * @author 豪
	 * @date 2019年3月21日
	 */
	public static List<Map<String, String>> getMonthsList(Date startDate, Date endDate,String key,String format) throws ParseException {
		List<Map<String, String>> list = Lists.newArrayList();
		Map<String, String> maps = null;
		while (startDate.compareTo(endDate) <= 0) {
			maps = new HashMap<>();
			String date = DateFormatUtils.format(startDate, format);
			startDate = DateUtils.addMonths(startDate, 1);
			maps.put(key, date);
			list.add(maps);
		}
		return list;
	}

	
	// 获取本月的开始时间
	public static String getBeginDayOfMonth(String date) throws ParseException {
		Date time = StringTransDate(date, "yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(time), getNowMonth(time) - 1, 1);
		Timestamp startTime = getDayStartTime(calendar.getTime());
		return DateTransString(startTime, "yyyy-MM-dd");
	}

	// 获取本月的结束时间
	public static String getEndDayOfMonth(String date) throws ParseException {
		Date time = StringTransDate(date, "yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(time), getNowMonth(time) - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(time), getNowMonth(time) - 1, day);
		Timestamp startTime = getDayEndTime(calendar.getTime());
		return DateTransString(startTime, "yyyy-MM-dd");
	}

	// 获取本月是哪一月
	public static int getNowMonth(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	// 获取今年是哪一年
	public static Integer getNowYear(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}


	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}
}
