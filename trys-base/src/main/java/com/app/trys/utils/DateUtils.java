package com.app.trys.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.function.BiFunction;

/**
 * @author linjf
 * @since 2023/4/24
 */
public class DateUtils {

	public static final String hh_mm_ss = "HH:mm:ss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 允许的计算的字段
	 */
	private static final int[] SUPPORT_FIELDS = new int[]{ //
			Calendar.MONTH,
			Calendar.DATE,
			Calendar.AM_PM,
			Calendar.HOUR,
			Calendar.MINUTE,
			Calendar.SECOND,
			Calendar.MILLISECOND
	};
	/**
	 * 忽略的计算的字段
	 */
	private static final int[] IGNORE_FIELDS = new int[]{ //
			Calendar.HOUR_OF_DAY, // 与HOUR同名
			Calendar.DAY_OF_WEEK_IN_MONTH, // 不参与计算
			Calendar.DAY_OF_YEAR, // DAY_OF_MONTH体现
			Calendar.WEEK_OF_MONTH, // 特殊处理
			Calendar.WEEK_OF_YEAR // WEEK_OF_MONTH体现
	};

	public static String defaultFormat(Date date){
		return format(date, yyyy_MM_dd);
	}

	public static String formatHhmmss(Date date){
		return format(date, hh_mm_ss);
	}

	public static String format(Date date, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return Actions.doTry(() -> format.format(date),"日期转换失败, date = {0}", date.toString());
	}

	public static String current() {
		return formatHhmmss(new Date());
	}


	public static Date parse(String source) {
		return parse(source, yyyy_MM_dd);
	}

	public static Date parse(String source, String pattern) {
		if(StringUtils.isEmpty(source)){
			return null;
		}
		DateFormat formatter = new SimpleDateFormat(pattern);
		return Actions.doTry(() -> formatter.parse(source), "日期格式错误: {0}", source);
	}

	public static Date toDate(LocalDate localDate) {
		return localDate == null ? null : Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate toLocalDate(String source) {
		if(StringUtils.isEmpty(source)){
			return null;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateUtils.yyyy_MM_dd_hh_mm_ss);
		return LocalDate.parse(source, formatter);
	}

	public static LocalDate toLocalDate(Date date) {
		return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date add(Date date, int calendarField, int amount) {
		if(date == null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	private static Date modify(Date date, int fromCalendarField, BiFunction<Calendar, Integer, Integer> valueMapper) {
		if(!ArrayUtil.contains(SUPPORT_FIELDS, fromCalendarField)){
			throw new UnsupportedOperationException("不支持的变更字段:" + fromCalendarField);
		}
		if(date == null){
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		for(int f = fromCalendarField + 1; f <= Calendar.MILLISECOND; f++){
			if(f == Calendar.DAY_OF_WEEK && fromCalendarField != Calendar.DAY_OF_WEEK){
				// 遇到需要一周的首日，但传进来的不是DAY_OF_WEEK，则跳过
				continue;
			}
			if(!ArrayUtil.contains(IGNORE_FIELDS, f)) {
				int value = valueMapper.apply(c, f);
				c.set(f, value);
			}
		}
		return c.getTime();
	}

	public static Date bgnOf(Date date, int calendarField) {
		return modify(date, calendarField, Calendar::getActualMinimum);
	}

	public static Date endOf(Date date, int calendarField) {
		return modify(date, calendarField, Calendar::getActualMaximum);
	}

	/**
	 * 是否为上午
	 *
	 * @param calendar {@link Calendar}
	 * @return 是否为上午
	 */
	public static boolean isAM(Calendar calendar) {
		return Calendar.AM == calendar.get(Calendar.AM_PM);
	}

	enum FieldEnum{
		YEAR(Calendar.YEAR),
		MONTH(Calendar.MONTH),
		WEEK_OF_YEAR(Calendar.WEEK_OF_YEAR),
		WEEK_OF_MONTH(Calendar.WEEK_OF_MONTH),
		DATE(Calendar.DATE),
		DAY_OF_MONTH(Calendar.DAY_OF_MONTH),
		DAY_OF_YEAR(Calendar.DAY_OF_YEAR),
		DAY_OF_WEEK(Calendar.DAY_OF_WEEK),
		DAY_OF_WEEK_IN_MONTH(Calendar.DAY_OF_WEEK_IN_MONTH),
		AM_PM(Calendar.AM_PM),
		HOUR(Calendar.HOUR),
		HOUR_OF_DAY(Calendar.HOUR_OF_DAY),
		MINUTE(Calendar.MINUTE),
		SECOND(Calendar.SECOND),
		MILLISECOND(Calendar.MILLISECOND),
		;
		private int id;
		private FieldEnum(int id){
			this.id = id;
		}
		public static FieldEnum getById(int id){
			for (FieldEnum value : values()) {
				if(value.id == id){
					return value;
				}
			}
			return null;
		}
	}
}
