package com.troila.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * java获取当前时间前一周、前一月、前一年的时间 
 * @author xuanguojing
 *
 */
public class TimeUtils {
	//短时间格式
	public static SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//长时间格式
	public static SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	/**
	 * 获取前一周的时间
	 * @return
	 */
	public static Date getLastWeek() {
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date());
	    c.add(Calendar.DATE, - 7); 
	    Date lastWeek = c.getTime();
	    String day = longDateFormat.format(lastWeek);
//	    System.out.println("过去七天："+day);
	    return lastWeek;
	}
	
	
	/**
	 * 获取前一个月的时间
	 * @return
	 */
    public static Date getLastMonth() {
        Calendar c = Calendar.getInstance();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date lastMonth = c.getTime();
        String mon = longDateFormat.format(lastMonth);
//        System.out.println("过去一个月："+mon);
        return lastMonth;
    }
    
    /**
     * 获取前一年的时间
     * @return
     */
    public static Date getLastYear() {
    	Calendar c = Calendar.getInstance();
    	c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date lastYear = c.getTime();
        String year = longDateFormat.format(lastYear);
//        System.out.println("过去一年："+year);
        return lastYear;
    }
    
    /**
     * 返回指定格式的时间
     * @param formatter
     * @param date
     * @return
     */
    public static Date getFormatDate(SimpleDateFormat formatter,Date date) {
    	if(date == null) {
    		return null; 		
    	}
	    String dateString = formatter.format(date);
//	    ParsePosition pos = new ParsePosition(8);
//	    Date date_res = formatter.parse(dateString, pos);
	    Date date_res = null;
		try {
			date_res = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return date_res;
    }
    
    public static void main(String[] args) {
		getLastWeek();
		
		getLastMonth();
		
		getLastYear();
		
		Date date = getFormatDate(longDateFormat, new Date());
		System.out.println(date);
		
	}
}
