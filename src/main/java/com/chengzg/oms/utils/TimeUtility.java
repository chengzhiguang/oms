package com.chengzg.oms.utils;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/*****************************************************
 *
 *@auther chengzhiguang
 *@since 2013-5-23
 ****************************************************/
public class TimeUtility {
	
	private static Logger logger = Logger.getLogger(TimeUtility.class);
	
    public static String TIME_FORMAT_YYYY_MM_DD2 = "yyyy/MM/dd";
    public static String TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static String TIME_FORMAT_YYYY_MM = "yyyy-MM";
    public static String TIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static String TIME_FORMAT_YYYYMM = "yyyyMM";
    public static String TIME_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static String TIME_FORMAT_HHMMSS = "HHmmss";
    public static String TIME_FORMAT_YYYYMMDDHHMMss = "yyyyMMddHHmmss";
	
	/**
	 * 格式化时间 yyyy-MM-dd HH:mm:ss
	 * @param time 需要格式化的date对象
	 * @return 格式化后的字符串
	 */
	public static String formatTimeStr(Object time){
        //想要得到的日期显示格式  
        DateFormat to_type   = new SimpleDateFormat(TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
        String str = to_type.format(time);
        return str;
	}
	
	/**
	 * 格式化时间
	 * @param time 需要格式化的date对象
	 * @param format 时间格式
	 * @return 格式化后的字符串
	 */
	public static String formatTimeStr(Object time, String format){
		DateFormat to_type   = new SimpleDateFormat(format);            
        String str = to_type.format(time);
        return str;
	}
	
	/**
	 * 获得一天的时间（Long）
	 *
	 *@author zhanjunwei
	 *
	 *@date Nov 1, 2011 1:03:36 PM
	 * @return
	 */
	public static Long getADayLongTime() {
		return 24 * 60 * 60 * 1000L;
	}
	
	@SuppressWarnings("deprecation")
	public static Date dateAddYears(Date time, int year){
		if(time == null) {
			return null;
		}
		Date result = (Date)time.clone();
		result.setMonth(time.getYear()+year); 
		
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public static Date dateAddMonths(Date time, int month){
		if(time == null) {
			return null;
		}
		Date result = (Date)time.clone();
		result.setMonth(time.getMonth()+month); 
		
		return result;
	}
	

	/**
	 * 给指定date对象增加指定天数
	 * @param time 要操作的时间对象
	 * @param days 增加的天数
	 * @return 增加后的date对象
	 */
	@SuppressWarnings("deprecation")
	public static Date dateAddDays(Date time, int days){
		if(time == null) {
			return null;
		}
		Date result = (Date)time.clone();
		result.setDate(time.getDate()+days); 
		
		return result;
	}
	
	public static Date dateAddMinutes (Date time, int minutes) {
		if(time == null) {
			return null;
		}
		Date result = (Date)time.clone();

		result.setMinutes(time.getMinutes() + minutes);
		return result;
	}
	/**
	 * 获取某天的00:00:00点时间
	 * @param time
	 * @return
	 */
	public static Date getZeroDateByDate(Date time) {
		if(time == null) {
			return null;
		}
		
		String timeStr = formatTimeStr(time, TIME_FORMAT_YYYY_MM_DD) + " 00:00:00";
		
		Date result = getDateByStr(timeStr, TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
		
		return result;
	}
	/**
	 * 获取某天的23:59:59点时间
	 * @param time
	 * @return
	 */
	public static Date get23DateByDate(Date time) {
		if(time == null) {
			return null;
		}
		String timeStr = formatTimeStr(time, TIME_FORMAT_YYYY_MM_DD) + " 23:59:59";
		
		Date result = getDateByStr(timeStr, TIME_FORMAT_YYYY_MM_DD_HH_MM_SS);
		
		return result;
	}
	
	/**
	 * 根据时间字符串转化为date对象
	 * @param dateStr 要转化的字符串
	 * @param format 时间格式
	 * @return 转化后的date对象
	 */
	public static Date getDateByStr(String dateStr,String format){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch(Exception e){
			logger.error(e.getMessage(), new Throwable(e));
		}
		return null;
	}
	
    /**
     *
     * 获取某一个月第一天和最后一天
     * @param date
     * @return
     */
    public static Map<String, String> getFirstdayLastdayMonth(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.add(Calendar.MONTH, -1);
        Date theDate = calendar.getTime();
        
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    /**
     * 获取 当月第一天
     * @return
     */
    private static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }
    
    /**
     * 获取 当月最后一天
     * @return
     */
    private static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }
    
    /**
	 * 获得string 对应的long时间
	 * 
	 * @author zhanjunwei
	 * 
	 * @date Nov 1, 2011 12:40:31 PM
	 * @param str
	 * @return
	 */
	public static Long getLongByStr(String str) {

		if (str == null) {
			return null;
		}
		Long time =	null;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = f.parse(str);
			time = date.getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return time;
	}
	
	/**
	 * 获得string 对应的long时间
	 * 
	 * @author zhanjunwei
	 * 
	 * @date Nov 1, 2011 12:40:31 PM
	 * @param str
	 * @return
	 */
	public static Long getLongByStr(String str, String format) {

		if (str == null) {
			return null;
		}
		Long time =	null;
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = f.parse(str);
			time = date.getTime();
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return time;
	}

	/**
	 * 获得long 对应的String时间
	 * 
	 * @author zhanjunwei
	 * 
	 * @date Nov 1, 2011 12:40:31 PM
	 * @param
	 * @return
	 */
	public static String getStrByLong(Long date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.format(date);
	}
	
	
	/**
	 * 获得long 对应的String时间
	 * 
	 * @author zhanjunwei
	 * 
	 * @date Nov 1, 2011 12:40:31 PM
	 * @param
	 * @return
	 */
	public static String getStrByLong(Long date, String format) {
		if (date == null || format == null) {
			return null;
		}
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(date);
	}
	
    /**
     * 获取两个时间差值
     * @param timest 开始时间
     * @param timeed 结束时间
     * @param type 1 天，2 时， 3 分， 4 秒， 5 毫秒（ms）
     * @return 成功返回时间差值，不成功返回null
     */
	public static Long getTimeDiff(Date timest, Date timeed, int type) {
		try {
			if(timest == null) {
				logger.info("getTimeDiff timest 为空！");
				return null;
			}
			if (timeed == null) {
				logger.info("getTimeDiff timeed 为空！");
				return null;
			}
			
			Long result= null;
			switch (type) {
			case 1:
				result = getDiffDays(timest, timeed);
				break;
			case 2:
				result = getDiffHours(timest, timeed);
				break;
			case 3:
				result = getDiffMinutes(timest, timeed);
				break;
			case 4:
				result = getDiffSeconds(timest, timeed);
				break;
			case 5:
				result = getDiffStamp(timest, timeed);
				break;
			default:
				logger.info("getTimeDiff type未定义！");
				break;
			}
			return result;
			
		} catch (Exception e) {
			logger.error("getTimeDiff 异常：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 获取两个时间相差的天数
	 * @param timest 开始时间
	 * @param timeed 结束时间
	 * @return 成功返回相差天数，不成功返回null
	 */
	private static Long getDiffDays(Date timest, Date timeed) {
		try {
			Calendar calst = Calendar.getInstance();
			Calendar caled = Calendar.getInstance();
			calst.setTime(timest);
			caled.setTime(timeed);
			logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd"));
			logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd"));

			//设置时间为0时   
			calst.set(Calendar.HOUR_OF_DAY, 0);
			calst.set(Calendar.MINUTE, 0);
			calst.set(Calendar.SECOND, 0);
			
			caled.set(Calendar.HOUR_OF_DAY, 0);
			caled.set(Calendar.MINUTE, 0);
			caled.set(Calendar.SECOND, 0);
			//得到两个日期相差的天数   
			Long days = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000)) / 3600 / 24;   
			  
			return days;   
		} catch (Exception e) {
			logger.error("LongDiffDays 异常：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 获取两个时间相差的分钟数
	 * @param timest 开始时间
	 * @param timeed 结束时间
	 * @return 成功返回相差分钟数，不成功返回null
	 */
	private static Long getDiffHours(Date timest, Date timeed) {
		try {
			Calendar calst = Calendar.getInstance();
			Calendar caled = Calendar.getInstance();
			calst.setTime(timest);
			caled.setTime(timeed);
			logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
			logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

			//设置时间为0时   
//			calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
			calst.set(Calendar.MINUTE, 0);
			calst.set(Calendar.SECOND, 0);
			
//			caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
			caled.set(Calendar.MINUTE, 0);
			caled.set(Calendar.SECOND, 0);
			//得到两个日期相差的天数   
			Long hoerss = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000)) / 3600;   
			  
			return hoerss;   
		} catch (Exception e) {
			logger.error("getDiffHours 异常：" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取两个时间相差的分钟数
	 * @param timest 开始时间
	 * @param timeed 结束时间
	 * @return 成功返回相差分钟数，不成功返回null
	 */
	private static Long getDiffMinutes(Date timest, Date timeed) {
		try {
			Calendar calst = Calendar.getInstance();
			Calendar caled = Calendar.getInstance();
			calst.setTime(timest);
			caled.setTime(timeed);
			logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
			logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

			//设置时间为0时   
//			calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
//			calst.set(java.util.Calendar.MINUTE, 0);   
			calst.set(Calendar.SECOND, 0);
			
//			caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
//			caled.set(java.util.Calendar.MINUTE, 0);   
			caled.set(Calendar.SECOND, 0);
			//得到两个日期相差的天数   
			Long minutess = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000)) / 60;   
			  
			return minutess;   
		} catch (Exception e) {
			logger.info("getDiffMinutes 异常：" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取两个时间相差的数据刻度
	 * @param timest 开始时间
	 * @param timeed 结束时间
	 * @return 成功返回时间刻度，不成功返回null
	 */
	private static Long getDiffSeconds(Date timest, Date timeed) {
		try {
			Calendar calst = Calendar.getInstance();
			Calendar caled = Calendar.getInstance();
			calst.setTime(timest);
			caled.setTime(timeed);
			logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
			logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

			//设置时间为0时   
//			calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
//			calst.set(java.util.Calendar.MINUTE, 0);   
//			calst.set(java.util.Calendar.SECOND, 0); 
			
//			caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
//			caled.set(java.util.Calendar.MINUTE, 0);   
//			caled.set(java.util.Calendar.SECOND, 0); 
			//得到两个日期相差的天数   
			Long minutess = ((Long) (caled.getTime().getTime() / 1000) - (Long) (calst.getTime().getTime() / 1000));   
			  
			return minutess;   
		} catch (Exception e) {
			logger.error("getDiffSeconds 异常：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 获取两个时间的相差时间刻度
	 * @param timest 开始时间
	 * @param timeed 结束时间
	 * @return 成功返回时间刻度，不成功返回null
	 */
	private static Long getDiffStamp(Date timest, Date timeed) {
		try {
			Calendar calst = Calendar.getInstance();
			Calendar caled = Calendar.getInstance();
			calst.setTime(timest);
			caled.setTime(timeed);
			logger.info("LongDiffDays 开始时间：" + formatTimeStr(timest, "yyyy-MM-dd HH:mm:ss"));
			logger.info("LongDiffDays 结束时间：" + formatTimeStr(timeed, "yyyy-MM-dd HH:mm:ss"));

			//设置时间为0时   
//			calst.set(java.util.Calendar.HOUR_OF_DAY, 0);   
//			calst.set(java.util.Calendar.MINUTE, 0);   
//			calst.set(java.util.Calendar.SECOND, 0); 
			
//			caled.set(java.util.Calendar.HOUR_OF_DAY, 0);   
//			caled.set(java.util.Calendar.MINUTE, 0);   
//			caled.set(java.util.Calendar.SECOND, 0); 
			//得到两个日期相差的天数   
			Long stamps = ((Long) (caled.getTime().getTime()) - (Long) (calst.getTime().getTime()));   

			return stamps;   
		} catch (Exception e) {
			logger.error("getDiffStamp 异常：" + e.getMessage());
		}
		return null;
	}
	
	public static List<Date> findDates(Date dBegin, Date dEnd) {  
        List lDate = new ArrayList();  
        lDate.add(dBegin);  
        Calendar calBegin = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间    
        calBegin.setTime(dBegin);  
        Calendar calEnd = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间    
        calEnd.setTime(dEnd);  
        // 测试此日期是否在指定日期之后    
        while (dEnd.after(calBegin.getTime())) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量    
            calBegin.add(Calendar.DAY_OF_MONTH, 1);  
            lDate.add(calBegin.getTime());  
        }  
        return lDate;  
    }  
	
	/**
     * @param args
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException {
    	
    	Date now = new Date();
    	System.out.println(formatTimeStr(now, "dd"));
    	System.out.println(now.getDate());
    }

}
