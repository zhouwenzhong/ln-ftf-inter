package com.fymod.ftf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	/**获取年月日等信息，传递参数为Calendar.YEAR等 方式**/
	public static int getField(int fieldType){
        Calendar calendar = new GregorianCalendar();
        return calendar.get(fieldType);
    }
    
	/**指定年月日日期之后offset天的Calendar， 如传递2014,1,2,3表示2014年1月2日之后3天即：2014-01-05**/
    public static Calendar construct(Integer year, Integer month, Integer day, Integer offset){
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day, 0, 0, 0);
        if(offset != null){
            calendar.add(Calendar.DAY_OF_MONTH, offset);
        }
        return calendar;
    }
    
    /**
     * @param plusOrSubtractDays 加减天数
     * @param date 对哪一天进行加减
     * @param parseStr "yyyy-mm-dd"
     * @return
     */
    public static Date getDate(int plusOrSubtractDays,Date date,String partten){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, plusOrSubtractDays);
        return DateUtils.parseDateString(DateUtils.formateDate(cal.getTime(), partten),partten);
    }
    
    /**
     * @param type 类型（YEAR = 1;MONTH = 2;DATE = 5;HOUR = 10;MINUTE = 12;SECOND = 13;MILLISECOND = 14;）
     * @param num 加减数
     * @param date 日期
     * @param partten 格式     13  6  2012-12-12 12:12:12   yyyy-mm-dd HH:mm:ss
     */
    public static Date getDate(int type,int num,Date date,String partten){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(type, num);
        return DateUtils.parseDateString(DateUtils.formateDate(cal.getTime(), partten),partten);
    }
    
    /**
     * 操作某个日期  可加减 年、月、日、时、分、秒     如不需加减的填0
     * @param years 年
     * @param month 月
     * @param plusOrSubtractDays 天
     * @param hours 时
     * @param minute 分
     * @param seconds 秒
     * @param date 对哪个时间进行加减
     * @param partten parseStr "yyyy-MM-dd HH:mm:ss"
     */
    public static Date addAndSubtractDate(int years,int month,int plusOrSubtractDays,int hours,int minute,int seconds,Date date,String partten){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        cal.add(Calendar.MARCH, month);
        cal.add(Calendar.DAY_OF_MONTH, plusOrSubtractDays);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, seconds);
        return DateUtils.parseDateString(DateUtils.formateDate(cal.getTime(), partten),partten);
    }
    
    public static String addAndSubDate(int years,int month,int plusOrSubtractDays,int hours,int minute,int seconds,Date date,String pattern){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        cal.add(Calendar.MARCH, month);
        cal.add(Calendar.DAY_OF_MONTH, plusOrSubtractDays);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, minute);
        cal.add(Calendar.SECOND, seconds);
        return DateUtils.formateDate(cal.getTime(), pattern);
    }
    
    /**  返回在当前系统时间基础上增加或减少天数的时间字符串 **/
    public static String addAndSubtractDate(int plusOrSubtractDays,Date date){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, plusOrSubtractDays);
        return DateUtils.formateDate(cal.getTime(), "yyyy-MM-dd");
    }
    
    /** 返回在当前系统时间基础上增加或减少月份的时间字符串 **/
    public static String addAndSubtractMonth(int plusOrSubtractMonth,Date date,String pattern){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, plusOrSubtractMonth);
        return DateUtils.formateDate(cal.getTime(), pattern);
    }
    
    /**当前月份**/
    public static int getMonthOfYear(){
    	Calendar calendar = Calendar.getInstance();
    	Date date = new Date();
    	calendar.setTime(date);
    	return calendar.get(Calendar.MONTH)+1;
    }
    
    /**当前是星期几（星期日返回0）**/
    public static int getDayOfWeek(){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }
    
    /**当前时间的指定格式 如去掉时分秒传递yyyy-MM-dd**/
    public static Date getToday(String partten){
        Date today = new Date();
        return DateUtils.parseDateString(DateUtils.formateDate(today, partten),partten);
    }
    
    /**日期格式化 转化为字符串**/
    public static String formateDate(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }
    
    /**字符串转化为时间**/
    public static Date parseDateString(String dateStr,String partten){
        SimpleDateFormat sdf = new SimpleDateFormat(partten);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
        	throw new RuntimeException(e.getMessage());
        }
        return date;
    }
    
    /**计算d1、d2之间相差天数**/
    public static long dateDiff(Date d1, Date d2){
    	final long DAY = 24*60*60*1000;
    	return ((d1.getTime() - d2.getTime()) / DAY);
    }
    
    /**计算d1与当天之间相差天数**/
    public static long dateDiff(Date d1){
    	final long DAY = 24*60*60*1000;
    	Date d2= new Date();
    	return ((d1.getTime() - d2.getTime()) / DAY);
    }
    
    /**  根据日期获得星期  **/   
	public static String getWeekOfDate(Date date) {   
	  String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
	  Calendar calendar = Calendar.getInstance();   
	  calendar.setTime(date);   
	  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	  return weekDaysName[intWeek];
    } 
	
	/**当月1日的时间字符串**/
	public static String getFirstDay() {
    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");      
	     Calendar lastDate = Calendar.getInstance();
	     lastDate.set(Calendar.DATE,1);//设为当前月的1号  
	     return sdf.format(lastDate.getTime());
    }
}
