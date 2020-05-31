package com.wechat.detal.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    final public static DateTimeFormatter format_y_SSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    final public static DateTimeFormatter format_y_mm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    final public static DateTimeFormatter format_y_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 格式到毫秒
     */
    public final static String YY_UNTIL_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public final static ThreadLocal<DateFormat> DF_YY_UNTIL_SSS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YY_UNTIL_SSS);
        }

        ;
    };

    /**
     * 格式到秒
     */
    public final static String YY_UNTIL_SECOND = "yyyy-MM-dd HH:mm:ss";
    public final static ThreadLocal<DateFormat> DF_YY_UNTIL_SECOND = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YY_UNTIL_SECOND);
        }

        ;
    };
    /**
     * 格式到分钟
     */
    public final static String YY_UNTIL_MI = "yyyy-MM-dd HH:mm";
    public final static ThreadLocal<DateFormat> DF_YY_UNTIL_MI = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YY_UNTIL_MI);
        }

        ;
    };

    /**
     * 格式到天数
     */
    public final static String YY_UNTIL_DD = "yyyy-MM-dd";
    public final static ThreadLocal<DateFormat> DF_YY_UNTIL_DD = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(YY_UNTIL_DD);
        }

        ;
    };

    private static Log log = LogFactory.getLog(DateUtils.class);

    /**
     * 医院最通用的日期格式
     */
    final public static ThreadLocal<SimpleDateFormat> FORMAT_DEF = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(YY_UNTIL_MI);
        }
    };

    /**
     * 医院最通用的日期格式
     */
    final public static ThreadLocal<SimpleDateFormat> FORMAT_YYYY_DD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(YY_UNTIL_DD);
        }
    };

    /**
     * 使用医院最通用的日期格式
     * author lijuntao
     * date 2018年12月28日
     */
    public static String formatDef(Date date) {
        if (date == null) {
            return null;
        }
        return FORMAT_DEF.get().format(date);
    }

    /**
     * 使用医院最通用的日期格式
     * author lijuntao
     * date 2018年12月28日
     */
    public static Date parseDef(String str) {
        return DateUtils.parse(str, FORMAT_DEF.get());
    }

    public static Date parse(String str, DateFormat... formats) {
        if (CommonUtils.empty(formats)) {
            return null;
        }
        for (DateFormat format : formats) {
            try {
                return format.parse(str);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 获取当天最后时间"2017:12:20 15:52:00" --> "2017:12:20 23:59:59"
     * author lijuntao
     * date 2017年12月20日
     */
    public static Date getLastTime(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 获取当天最早时间"2017:12:20 15:52:00" --> "2017:12:20 00:00:00"
     * author lijuntao
     * date 2017年12月20日
     */
    public static Date getFirstTime(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 解析日期，不抛出遗产
     * author lijuntao
     * date 2018年4月12日
     */
    public static Date parse(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (Exception e) {
            log.debug(String.format("日期格式转化失败:%s,%s", dateStr, format));
        }
        return null;
    }

    /**
     * 解析日期，不抛出遗产
     * author lijuntao
     * date 2018年4月12日
     */
    public static Date parse(String dateStr, String... formats) {
        if (CommonUtils.empty(formats)) {
            log.debug(String.format("日期格式转化失败:%s,%s", dateStr, Arrays.toString(formats)));
            return null;
        }
        Date date = null;
        for (String format : formats) {
            try {
                date = new SimpleDateFormat(format).parse(dateStr);
                if (date != null) {
                    return date;
                }
            } catch (Exception e) {
            }
        }
        if (date == null) {
            log.debug(String.format("日期格式转化失败:%s,%s", dateStr, Arrays.toString(formats)));
        }
        return null;
    }

    /**
     * 解析日期，不抛出异常
     * author lijuntao
     * date 2018年4月12日
     */
    public static String format(Date date, String format) {
        if (date == null)
            return null;
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            log.debug(String.format("日期格式转化失败:%s,%s", date, format));
        }
        return null;
    }

    /**
     * 解析日期，不抛出异常
     * author lijuntao
     * date 2018年4月12日
     */
    public static String format(Date date, DateFormat format) {
        if (date == null)
            return null;
        try {
            return format.format(date);
        } catch (Exception e) {
            log.debug(String.format("日期格式转化失败:%s,%s", date, format));
        }
        return null;
    }

    /**
     * 校验日期，不抛出异常
     * author lijuntao
     * date 2018年4月12日
     */
    public static boolean isCorrectFormat(String dateStr, String format) {
        if (CommonUtils.empty(dateStr) && CommonUtils.empty(format))
            return false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(dateStr);
            return dateStr.equals(dateFormat.format(date));
        } catch (Exception e) {
            log.debug(String.format("校验日期格式异常:%s,%s", dateStr, format));
        }
        return false;
    }

    public static Date concatDate(String yyyy, String MMdd, String HHmm, String prefix) {
        Validate validate = new Validate();
        validate.isNotEmpty(yyyy, prefix + "年份不能为空");
        validate.isNotEmpty(MMdd, prefix + "日期不能为空");
        validate.falseThrow(Pattern.matches("\\d{4}", yyyy), prefix + "年份格式不对");
        MMdd = formatWithMMdd(MMdd);
        validate.isNotEmpty(MMdd, prefix + "日期格式不对");
        if (CommonUtils.notEmpty(HHmm)) {
            HHmm = formatWithhhmm(HHmm);
            validate.isNotEmpty(HHmm, prefix + "时间格式不对");
        } else {
            HHmm = "00:00";
        }
        String dateStr = String.format("%s-%s %s", yyyy, MMdd, HHmm);
        Date date = DateUtils.parse(dateStr, DateUtils.YY_UNTIL_MI);
        validate.isNotEmpty(date, "日期转化失败：" + dateStr);
        return date;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 两个日期相差的年数
     * author lijuntao
     * date 2018年8月7日
     */
    public static int differYears(Date date, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int i1 = calendar1.get(Calendar.YEAR);
        int i2 = calendar2.get(Calendar.YEAR);
        return i1 - i2;
    }

    /**
     * 两个日期相差的天数，只比较日期部分
     * 2018-07-07 12:00:00 与 2018-07-07 12:00:00 相差为0天
     * 2018-07-07 23:59:59 与 2018-07-08 00:00:00 相差为1天
     * author lijuntao
     * date 2018年8月7日
     */
    public static int differDays(Date date, Date date2) {
        date = getFirstTime(date);
        date2 = getFirstTime(date2);
        long differMilliSecond = date.getTime() - date2.getTime();
        return (int) ((differMilliSecond) / (1000 * 60 * 60 * 24));
    }

    /**
     * 两个日期相差的小时数
     * author lijuntao
     * date 2018年8月7日
     */
    public static int differHours(Date date, Date date2, boolean ceil) {
        new Validate().isNotEmpty(date, "比较两个时间相差的小时数，第一个时间不能为空");
        new Validate().isNotEmpty(date2, "比较两个时间相差的小时数，第二个时间不能为空");
        if (date == null || date2 == null) {
            return 0;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        long timeInMillis = calendar1.getTimeInMillis();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        long timeInMillis2 = calendar2.getTimeInMillis();
        double result = ((double) (timeInMillis - timeInMillis2)) / (1000 * 60 * 60);
        return (int) (ceil ? Math.ceil(result) : Math.floor(result));
    }

    /**
     * 两个日期相差的小时数
     * author lijuntao
     * date 2018年8月7日
     */
    public static int differHours(Date date, Date date2) {
        return differHours(date, date2, false);
    }

    /**
     * 两个日期相差的分钟数
     * author lijuntao
     * date 2018年8月7日
     */
    public static int differMinute(Date date, Date date2) {
        if (date == null || date2 == null) {
            return 0;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        long timeInMillis = calendar1.getTimeInMillis();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        long timeInMillis2 = calendar2.getTimeInMillis();

        return (int) ((timeInMillis - timeInMillis2) / (1000 * 60));
    }

    /**
     * 判断时间介于某个时间段之间
     * author lijuntao
     * date 2018年8月7日
     */
    public static boolean between(Date date, Date beginDate, Date endDate) {
        if (date == null || date == null || date == null) {
            return false;
        }
        return beginDate.compareTo(date) <= 0 && endDate.compareTo(date) >= 0;
    }

    /**
     * 把月日的字符串格式化为MM-dd
     * author lijuntao
     * date 2018年8月20日
     */
    public static String formatWithMMdd(String MMdd) {
        int[] splitTwoInt = RegexUtils.splitTwoInt(MMdd, 12, 31);
        if (CommonUtils.notEmpty(splitTwoInt) && splitTwoInt[1] > 0) {
            DecimalFormat format = new DecimalFormat("00");
            String formatStr = format.format(splitTwoInt[0]) + "-" + format.format(splitTwoInt[1]);
            return formatStr;
        }
        return null;
    }

    /**
     * 把时分的字符串格式化为hh:mm
     * author lijuntao
     * date 2018年8月20日
     */
    public static String formatWithhhmm(String hhmm) {
        int[] splitTwoInt = RegexUtils.splitTwoInt(hhmm, 23, 59);
        if (CommonUtils.notEmpty(splitTwoInt)) {
            DecimalFormat format = new DecimalFormat("00");
            String formatStr = format.format(splitTwoInt[0]) + ":" + format.format(splitTwoInt[1]);
            return formatStr;
        }
        return null;
    }

    public static Date concatDate(String yyyy, String MMdd, String HHmm) {
        Validate validate = new Validate();
        validate.isNotEmpty(yyyy, "日期年份不能为空");
        validate.isNotEmpty(MMdd, "日期日月不能为空");
        validate.falseThrow(Pattern.matches("\\d{4}", yyyy), "日期年份格式不对");
        MMdd = formatWithMMdd(MMdd);
        validate.isNotEmpty(MMdd, "日期日月格式不对");
        if (CommonUtils.notEmpty(HHmm)) {
            HHmm = formatWithhhmm(HHmm);
            validate.isNotEmpty(HHmm, "日期时分格式不对");
        } else {
            HHmm = "00:00";
        }
        String dateStr = String.format("%s-%s %s", yyyy, MMdd, HHmm);
        return DateUtils.parse(dateStr, DateUtils.YY_UNTIL_MI);
    }

    /**
     * 根据给定的最大、最小时间、月日、时分来推测出时间
     * author lijuntao
     * date 2018年8月30日
     */
    public static Date guessDateWithMonthDay(Date minDate, Date maxDate, String MMdd, String HHmm) {
        Validate validate = new Validate();
        validate.isNotEmpty(minDate, "minDate不能为空").isNotEmpty(maxDate, "maxDate不能为空")
                .isNotEmpty(MMdd, "日期月日不能为空");
        MMdd = formatWithMMdd(MMdd);
        validate.isNotEmpty(MMdd, "日期月日格式不对");
        if (CommonUtils.empty(HHmm)) {
            HHmm = "00:00";
        } else {
            HHmm = formatWithhhmm(HHmm);
            validate.isNotEmpty(HHmm, "日期时分格式不对");
        }
        Date recordDate = concatDate(DateUtils.format(maxDate, "yyyy"), MMdd, HHmm);
        if (recordDate.after(maxDate)) {
            recordDate = DateUtils.addYears(recordDate, -1);
        }
        if (recordDate.before(minDate)) {
            validate.error("日期月日不能大于当前的最大日期");
        }
        return recordDate;
    }


    /**
     * 获取两个日期相差的月数
     *
     * @param d1 较大的日期
     * @param d2 较小的日期
     * @return 如果d1>d2返回 月数差 否则返回0
     * @author 豪
     * @date 2018年10月17日
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 根据给定的最大、最小时间、月日、时分来推测出时间
     * MMdd 不能为空
     * 在最大时间和最小时间的范围内，使拼凑的时间是最大的
     * 如：最大时间2018-10-10， 最小时间2017-11-11，
     * 日期为10-06，则拼凑的时间：2018-10-06
     * 日期为10-19，则拼凑的时间：2017-10-19
     * <p>
     * <p>
     * 如果拼凑的时间的时间，不在最大时间和最小时间的范围内，使用最大时间的年来拼凑时间
     * 如：最大时间2018-10-10， 最小时间2018-09-09，
     * 日期为10-06，则拼凑的时间：2018-10-06
     * 日期为10-19，则拼凑的时间：2018-10-19
     * <p>
     * author lijuntao
     * date 2018年8月30日
     */
    public static Date guessRecordYear(Date minDate, Date maxDate, String MMdd, String HHmm, String prefix) {
        prefix = CommonUtils.nullToEmpty(prefix);
        Validate validate = new Validate();
        validate.isNotEmpty(minDate, "minDate不能为空").isNotEmpty(maxDate, "maxDate不能为空")
                .isNotEmpty(MMdd, prefix + "日期不能为空");
        MMdd = formatWithMMdd(MMdd);
        validate.isNotEmpty(MMdd, prefix + "日期格式不对");
        if (CommonUtils.empty(HHmm)) {
            HHmm = "00:00";
        } else {
            HHmm = formatWithhhmm(HHmm);
            validate.isNotEmpty(HHmm, prefix + "时间格式不对");
        }
        Date currentYearDate = concatDate(DateUtils.format(maxDate, "yyyy"), MMdd, HHmm, null);
        if (currentYearDate.after(maxDate)) {
            Date prevYearDate = DateUtils.addYears(currentYearDate, -1);
            if (!prevYearDate.before(minDate)) {
                return prevYearDate;
            }
        }
        return currentYearDate;
    }

    /**
     * 对比格式化后，字符串是否相等
     *
     * @author lijuntao
     * @date 2019年2月14日
     */
    public static boolean equalsAfterFormat(Date date1, Date date2, String format) {
        if (date1 == null || date2 == null || StringUtils.isBlank(format)) {
            return false;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);

        String dateStr1 = format(date1, dateFormat);
        String dateStr2 = format(date2, dateFormat);

        return StringUtils.isNotBlank(dateStr1) && dateStr1.equals(dateStr2);
    }

    public static Date setDate(Date date, Integer hours, Integer minute, Integer secord, Integer milliSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (hours != null) {
            calendar.set(Calendar.HOUR_OF_DAY, hours);
        }
        if (minute != null) {
            calendar.set(Calendar.MINUTE, minute);
        }
        if (secord != null) {
            calendar.set(Calendar.SECOND, secord);
        }
        if (milliSecond != null) {
            calendar.set(Calendar.MILLISECOND, milliSecond);
        }
        return calendar.getTime();
    }

    /**
     * 获取工作年限
     *
     * @author lijuntao
     * @date 2019年7月18日
     */
    public static int workAge(Date nowTime, Date workTime) {
        int year = 0;
        //当前时间的年月日
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowTime);
        int nowYear = cal.get(Calendar.YEAR);
        int nowMonth = cal.get(Calendar.MONTH);
        int nowDay = cal.get(Calendar.DAY_OF_MONTH);

        //开始工作时间的年月日
        cal.setTime(workTime);
        int workYear = cal.get(Calendar.YEAR);
        int workMonth = cal.get(Calendar.MONTH);
        int workDay = cal.get(Calendar.DAY_OF_MONTH);

        //得到工龄
        year = nowYear - workYear; //得到年差
        //若目前月数少于开始工作时间的月数，年差-1
        if (nowMonth < workMonth) {
            year = year - 1;
        } else if (nowMonth == workMonth) {
            //当月数相等时，判断日数，若当月的日数小于开始工作时间的日数，年差-1
            if (nowDay < workDay) {
                year = year - 1;
            }
        }

        return Math.max(year, 0);
    }

    /**
     * month 从一月开始
     *
     * @author lijuntao
     * @date 2019年10月30日
     */
    public static Date getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * month 从一月开始
     *
     * @author lijuntao
     * @date 2019年10月30日
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int i = 0;
        if (month == 2) {
            i = calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
        } else {
            i = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        calendar.set(Calendar.DAY_OF_MONTH, i);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 判断时间是否在选择时间内（判断两个时间是否有交集）
     *
     * @param selectStartDate 选择的开始时间
     * @param selectEndDate   选择的结束时间
     * @param startDate       开始时间
     * @param endDate         结束时间
     * @return
     * @author qiumingjie
     */
    public static Boolean judgeDateBetweenDate(Date selectStartDate, Date selectEndDate, Date startDate, Date endDate) {
        return between(startDate, selectStartDate, selectEndDate) || between(endDate, selectStartDate, selectEndDate) || between(selectEndDate, startDate, endDate) || between(selectStartDate, startDate, endDate);
    }

    /**
     * 判断两个时间的大小
     *
     * @param dateStr     日期1
     * @param date2Str    日期2
     * @param dateFormat  日期1格式
     * @param date2Format 日期2格式
     * @return 相等返回0，大于返回正
     */
    public static int compareDate(String dateStr, String date2Str, String dateFormat, String date2Format) {
        Validate validate = new Validate();
        if (CommonUtils.empty(dateFormat) && CommonUtils.empty(date2Format)) {
            validate.error("时间格式不能都为空");
        }
        if (CommonUtils.empty(dateFormat) || CommonUtils.empty(date2Format)) {
            if (CommonUtils.empty(dateFormat)) {
                dateFormat = date2Format;
            } else {
                date2Format = dateFormat;
            }
        }
        Date date = parse(dateStr, dateFormat);
        Date date2 = parse(date2Str, date2Format);
        if (date == null || date2 == null) {
            validate.error("日期为空");
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        long timeInMillis = calendar1.getTimeInMillis();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        long timeInMillis2 = calendar2.getTimeInMillis();
        return (int) (timeInMillis - timeInMillis2);
    }

    public static Pattern HHMM_EXTRACT_PATTERN = Pattern.compile("\\s*([0-9]{1,2})\\s*[：:]+\\s*([0-9]{1,2})\\s*");

    /**
     * @Description: 提取HHmm格式的时间点，方法不报错
     * 9:00-13:00/14:00-17:00  -> [09:00, 13:00, 14:00, 17:00]
     * @Author: lijuntao
     * @Date: 2020/1/9
     */
    public static List<String> extractHHmm(String str) {
        List<String> list = Lists.newArrayList();
        Matcher matcher = HHMM_EXTRACT_PATTERN.matcher(str);
        System.out.println(str);
        while (matcher.find()) {
            String group1 = matcher.group(1);
            Integer integer1 = NumberUtils.parseInteger(group1);
            if (NumberUtils.primitive(integer1) < 10) {
                group1 = "0" + integer1;
            }
            String group2 = matcher.group(2);
            Integer integer2 = NumberUtils.parseInteger(group2);
            if (NumberUtils.primitive(integer2) < 10) {
                group2 = "0" + integer2;
            }
            String HHmm = group1 + ":" + group2;
            list.add(HHmm);
        }
        return list;
    }

    public static void main(String[] args) {
        Date date = getFirstDayOfMonth(2019, 3);
        Date date2 = getLastDayOfMonth(2019, 3);

        System.out.println(DateUtils.format(date, DateUtils.YY_UNTIL_SSS));
        System.out.println(DateUtils.format(date2, DateUtils.YY_UNTIL_SSS));
    }
}
