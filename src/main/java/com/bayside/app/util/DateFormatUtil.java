package com.bayside.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateFormatUtil {
	/**
	 * 
	 * <p>方法名称：stringFormatDate</p>
	 * <p>方法描述：字符串转为日期格式  不带时间 yyyy-MM-dd</p>
	 * @param dateString
	 * @return
	 * @throws ParseException
	 * @author sql
	 * @since  2016年7月14日
	 * <p> history 2016年7月14日 sql  创建   <p>
	 */
    public static Date stringFormatDate(String dateString) throws ParseException {  
    	if (dateString!=null&&!"".equals(dateString)) {
    		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");   
            Date date = bartDateFormat.parse(dateString);   
            return date;		
         }
        return null;  
    }  
   /**
    * 
    * <p>方法名称：stringFormatDateTime</p>
    * <p>方法描述：字符串转为日期格式  带时间：yyyy-MM-dd HH:mm:ss</p>
    * @param dateString
    * @return
    * @throws ParseException
    * @author Administrator
    * @since  2016年7月14日
    * <p> history 2016年7月14日 Administrator  创建   <p>
    */
    public static Date stringFormatDateTime(String dateString) throws ParseException{  
    	if (dateString!=null&&!"".equals(dateString)) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        Date date = bartDateFormat.parse(dateString);   
        return date;  
    	}
    	return null;
    }  
    /**
     * 
     * <p>方法名称：getDateList</p>
     * <p>方法描述：</p>
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param days 多少天的数据
     * @return
     * @author Administrator
     * @since  2016年11月30日
     * <p> history 2016年11月30日 Administrator  创建   <p>
     */
     public static List<String> getDateList(String startTime,String endTime,int days){
    		Date startDate = DateFormatUtil.stringFormatDateType(startTime, "yyyy-MM-dd");
    		Date endDate = DateFormatUtil.stringFormatDateType(endTime, "yyyy-MM-dd");
    		Date date = new Date();
    		int diffDay =0;
    		Calendar c = Calendar.getInstance();
    		if(date.getTime()>endDate.getTime()){
    			diffDay = (int) ((endDate.getTime()-startDate.getTime())/(24 * 60 * 60 * 1000));
    			c.setTime(endDate);
    		}else{	
    			diffDay = (int) ((date.getTime()-startDate.getTime())/(24 * 60 * 60 * 1000));
    		}
    		if(diffDay<15){
    			days=diffDay;
    		}
    		List<String> dateList = new ArrayList<String>();
    		dateList.add(DateFormatUtil.dateFormatStringType(c.getTime(), "yyyy-MM-dd"));
    		for (int i = 0; i < diffDay; i++) {
    			c.add(Calendar.DAY_OF_YEAR, -1);
    			dateList.add(DateFormatUtil.dateFormatStringType(c.getTime(), "yyyy-MM-dd"));
    		}
    		return dateList;
     }
     public static List<String> getHourList(){
		 	List<String> list = new ArrayList<String>();
		 	Calendar c = Calendar.getInstance();
		 	Date startDate = stringFormatDateType(dateFormatStringType(c.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
		 	Date endDate = new Date();
		 	int diffHour = (int) ((endDate.getTime()-startDate.getTime())/(1000*60*60));
		 	String currHour = dateFormatStringType(c.getTime(), "HH");
		 	list.add(currHour);
		 	for (int i = 1; i <diffHour; i++) {
		 		c.add(Calendar.HOUR, -1);
				list.add(dateFormatStringType(c.getTime(), "HH"));
			}
		 	if(!"0".equals(currHour)){
		 		list.add(dateFormatStringType(startDate, "HH"));
		 	}
			return list;
	 }
     public static List<String> getHourList(String startTime,String endTime){
		 	List<String> list = new ArrayList<String>();
		 	Calendar c = Calendar.getInstance();
		 	Date startDate = stringFormatDateType(startTime, "yyyy-MM-dd");
		 	Date endDate = stringFormatDateType(endTime, "yyyy-MM-dd");
		 	int diffHour = (int) ((endDate.getTime()-startDate.getTime())/(1000*60*60));
		 	c.setTime(endDate);
		 	for (int i = 1; i <diffHour; i++) {
		 		c.add(Calendar.HOUR, -1);
				list.add(dateFormatStringType(c.getTime(), "HH"));
			}
			return list;
	 }
    /**
     * 
     * <p>方法名称：stringFormatDateType</p>
     * <p>方法描述：字符串类型转化为日期类型</p>
     * @param dateString
     * @param dateType
     * @return
     * @throws ParseException
     * @author sql
     * @since  2016年7月14日
     * <p> history 2016年7月14日 sql  创建   <p>
     */
    public static Date stringFormatDateType(String dateString,String dateType){
    	if (dateString!=null&&!"".equals(dateString)&&dateType!=null&&!"".equals(dateType)) {
	    	SimpleDateFormat bartDateFormat = new SimpleDateFormat(dateType);
	    	bartDateFormat.setLenient(false);
	        Date date = new Date();
			try {
				date = bartDateFormat.parse(dateString);
			} catch (ParseException e) {
				System.out.println(e);
			}   
            return date;  
    	}
    	return null;
    }
    /**
     * 
     * <p>方法名称：stringFormatDateType</p>
     * <p>方法描述：字将日期转化为指定的字符串类型</p>
     * @param dateString
     * @param dateType
     * @return
     * @throws ParseException
     * @author sql
     * @since  2016年7月14日
     * <p> history 2016年7月14日 sql  创建   <p>
     */
    public static String dateFormatStringType(Date date,String dateType){
    	if (date!=null&&dateType!=null&&!"".equals(dateType)) {
    		SimpleDateFormat bartDateFormat = new SimpleDateFormat(dateType);
    		bartDateFormat.setLenient(false);
    		String  dateString =   bartDateFormat.format(date);
    		return dateString;  
    	}
    	return null;
    }
    /** 
     * 将时间格式化为含时分秒的字符串 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String dateTimeFormatString(Date date){  
    	if (date!=null) {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            return dateFormat.format(date);  
		}
        return null;
    }  
      
    /** 
     * 将时间格式化为不含时分秒的字符串 yyyy-MM-dd
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String dateFormatString(Date date){  
    	if (date!=null) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        return dateFormat.format(date);  
    	}
    	return null;
    }  
    public static String dateFormatHString(Date date){  
    	if (date!=null) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH");  
        return dateFormat.format(date);  
    	}
    	return null;
    }  
    /**
     * 字符串转时间戳
     * <p>方法名称：StringFormatTimeStemp</p>
     * <p>方法描述：</p>
     * @param dateString
     * @return
     * @throws ParseException
     * @author Administrator
     * @since  2016年6月7日
     * <p> history 2016年6月7日 Administrator  创建   <p>
     */
    public static int StringFormatTimeStemp(String dateString,String dateType) throws ParseException{
    	if (dateString!=null&&!"".equals(dateString)&&dateType!=null&&!"".equals(dateType)){ 
    	Date date = stringFormatDateType(dateString,dateType);
    	int timeStemp = (int) (date.getTime()/1000);
    	return timeStemp;
    	}
    	return 0;
    }
    /**
     * 
     * <p>方法名称：timeString</p>
     * <p>方法描述：</p>
     * @param date
     * @return
     * @throws ParseException
     * @author Administrator
     * @since  2016年10月2日
     * <p> history 2016年10月2日 Administrator  创建   <p>
     */
    public static String timeString(Date date) {  
    	if (date!=null) {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
            return dateFormat.format(date);  
		}
        return null;
    }  
   /**
    * 
    * <p>方法名称：getCurrentTime</p>
    * <p>方法描述：</p>
    * @param dateType 时间格式
    * @return
    * @author Administrator
    * @since  2016年9月28日
    * <p> history 2016年9月28日 Administrator  创建   <p>
    */
   	public static String getCurrentTime(String dateType){
   		Date date = new Date();
   		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
   		return sdf.format(date);
   	}
   	/**
   	 * 
   	 * <p>方法名称：getYesterdayDate</p>
   	 * <p>方法描述：获取当前时间的前一天的日期</p>
   	 * @return
   	 * @author sql
   	 * @since  2016年10月2日
   	 * <p> history 2016年10月2日 sql  创建   <p>
   	 */
   	public static Date getYesterdayDate(){
   		Calendar c = Calendar.getInstance();
   		c.add(Calendar.DAY_OF_MONTH, -1);
   		Date date = c.getTime();
   		
   		return date;
   	}
   	/**
   	 * 
   	 * <p>方法名称：getLastMonday</p>
   	 * <p>方法描述：获取某周一的日期</p>
   	 * @return
   	 * @param n 推迟周数1本周-1向前推迟周2周依类推
   	 * @author Administrator
   	 * @since  2016年10月2日
   	 * <p> history 2016年10月2日 Administrator  创建   <p>
   	 */
   	public static String getMonday(int n){
   		Calendar cal = Calendar.getInstance();
  	  //n推迟周数1本周-1向前推迟周2周依类推
  	  cal.add(Calendar.DATE, n*7);
  	  //想周几传几Calendar.MONDAY（TUESDAY...）
  	  cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
  	  String  monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	 return monday;
   	}
	
   	/**
   	 * 
   	 * <p>方法名称：getLastSunday</p>
   	 * <p>方法描述：获取上周天</p>
   	 * @return
   	 * @author Administrator
   	 * @since  2016年10月3日
   	 * <p> history 2016年10月3日 Administrator  创建   <p>
   	 */
   	public static String getLastSunday(){
   		Calendar cal = Calendar.getInstance();
    	  //n推迟周数1本周-1向前推迟周2周依类推
    	  int n = 0;
    	  cal.add(Calendar.DATE, n*7);
    	  //想周几传几Calendar.MONDAY（TUESDAY...）
    	  cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
    	String  monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    	  return monday;
   	}
   	/**
   	 * 
   	 * <p>方法名称：getFristDayLastMonth</p>
   	 * <p>方法描述：获取上个月的第一天</p>
   	 * @author Administrator
   	 * @throws ParseException 
   	 * @since  2016年10月3日
   	 * <p> history 2016年10月3日 Administrator  创建   <p>
   	 */
   	public static String getFristDayLastMonth(){
   		Calendar calendar = Calendar.getInstance();
   		calendar.add(Calendar.MONTH, -1);
   		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return dateFormatStringType(calendar.getTime(), "yyyy-MM-dd");
		
   	}
   	/**
   	 * 
   	 * <p>方法名称：getFristDayLastMonth</p>
   	 * <p>方法描述：获取本月月的第一天</p>
   	 * @author Administrator
   	 * @throws ParseException 
   	 * @since  2016年10月3日
   	 * <p> history 2016年10月3日 Administrator  创建   <p>
   	 */
   	public static String getFristDayThisMonth(){
   		Calendar calendar = Calendar.getInstance();
   		calendar.set(Calendar.DAY_OF_MONTH, 1);
   		return dateFormatStringType(calendar.getTime(), "yyyy-MM-dd");
   	}
   /**
    * 
    * <p>方法名称：getYearFirst</p>
    * <p>方法描述：获取某年第一天日期</p>
    * @param year
    * @return
    * @author Administrator
    * @since  2016年10月3日
    * <p> history 2016年10月3日 Administrator  创建   <p>
    */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }
     
    /**
     * <p>方法名称：getYearLast</p>
     * <p>方法描述：获取某年最后一天日期</p>
     * @param year
     * @return
     * @author Administrator
     * @since  2016年10月3日
     * <p> history 2016年10月3日 Administrator  创建   <p>
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }
   	/**
   	 * 
   	 * <p>方法名称：main</p>
   	 * <p>方法描述：</p>
   	 * @param args
   	 * @throws ParseException
   	 * @author Administrator
   	 * @since  2016年10月2日
   	 * <p> history 2016年10月2日 Administrator  创建   <p>
   	 */
    public static void main(String[] args) throws ParseException {
    	List<String> list = getDateList("2017-03-06","2017-03-09",15);
    	for (String string : list) {
			System.out.println(string);
		}
	}
}
