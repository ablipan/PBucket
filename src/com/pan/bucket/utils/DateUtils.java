/**
 * author :  lipan
 * filename :  DateUtils.java
 * create_time : 2014年4月23日 下午3:12:44
 */
package com.pan.bucket.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.SparseIntArray;

/**
 * @author : lipan
 * @create_time : 2014年4月23日 下午3:12:44
 * @desc : 日期工具类
 * @update_time :
 * @update_desc :
 *
 */
public class DateUtils
{

    public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_2 = "HH:mm yyyy-MM-dd";
    
    public static final int MAX_DAYS_BIG_MONTH = 31; //大月天数
    public static final int MAX_DAYS_SMALL_MONTH = 30; //小月天数
    public static final int MAX_DAYS_LEAP_YEAR = 29; //闰年2月最大天数
    public static final int MAX_DAYS_NON_LEAP_YEAR = 28; //平年2月最大天数
    public static final String DEF_MONTH_FMT = "yyyy年MM月";/**默认月份格式**/
    public static final String DEF_MONTH_FMT_1 = "yyyyMM";/**月份参数格式**/
    public static final String DEF_MONTH_FMT_2 = "yyyy-MM-dd";
    
    /**
     * 获得相差月数的时间字符串
     * @param timestamp 时间戳
     * @param pattern  时间表达式，null 时默认为 C.DEF_MONTH_FMT
     * @param offMonth 偏移月数
     * @return
     */
    public static String getMonthStr(long timestamp, String pattern , int offMonth) {
        //默认月份格式
        if(null == pattern || "".equals(pattern))
        {
            pattern = DEF_MONTH_FMT;
        }
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTimeInMillis(timestamp);
        cal.add(Calendar.MONTH, offMonth);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,Locale.CHINA);
        return formatter.format(cal.getTime());
    }
    
    /**
     * 获得当前时间字符串
     * @param pattern
     * @return
     */
    public static String currentDateStr(String pattern)
    {
        return millisToDateString(System.currentTimeMillis(), pattern);
    }
    
    
    /**
     * 获得指定毫秒时间字符串
     * @param pattern 时间表达式
     * @return
     */
    public static String millisToDateString(long millis,String pattern)
    {
    	Calendar cal = Calendar.getInstance(Locale.CHINA);
    	cal.setTimeInMillis(millis);
    	SimpleDateFormat formatter = new SimpleDateFormat(pattern,Locale.CHINA);
    	return formatter.format(cal.getTime());
    }
    
    /**
     * 获得当前时间毫秒数
     * @param pattern
     * @return
     */
    public static Long getCurrentMillis()
    {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        return cal.getTimeInMillis();
    }
    
    /**
     * 判断指定日期是否为闰年
     *
     * @param date
     *            指定的日期，null时返回 false
     * @return 是否为闰年
     */
    public static boolean isLeap(Date date) {
        boolean isLeap = false;
        if (null != date) {
            Calendar cal = Calendar.getInstance(Locale.CHINA);
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
                isLeap = true;
        }
        return isLeap;
    }
    
    /**
     * 获得各个时间类型的值
     * @return
     */
    public static int getCalVal(Calendar calendar ,int type)
    {
        if(type == Calendar.MONTH) //月份+1
        {
            return calendar.get(type) +1 ;
        }
        return calendar.get(type);
    }
    
    /**
     * 获得指定日期所在年的每个月的最大天数
     * @return
     */
    public static SparseIntArray getMonthMaxDays(Calendar calendar)
    {
        SparseIntArray maxDays = new SparseIntArray();
        
        Integer[] big = {1, 3, 5, 7, 8, 10, 12};
        Integer[] small = {4, 6, 9, 11};
        List<Integer> bigList = Arrays.asList(big); // 大月
        List<Integer> smallList = Arrays.asList(small); //小月
        
        if(calendar == null)
        {
            calendar = Calendar.getInstance();
        }
        
        for(int i=1 ; i<=12 ; i++)
        {
            if(bigList.contains(i))
            {
                maxDays.put(i, MAX_DAYS_BIG_MONTH);
            }else if(smallList.contains(i))
            {
                maxDays.put(i, MAX_DAYS_SMALL_MONTH);
            }else
            {
                if(isLeap(calendar.getTime()))
                {
                    maxDays.put(i, MAX_DAYS_LEAP_YEAR);
                }else
                {
                    maxDays.put(i, MAX_DAYS_NON_LEAP_YEAR);
                }
            }
        }
        return maxDays;
    }
    
    /**
	 * 把日期毫秒转化为字符串。默认格式：yyyy-MM-dd HH:mm:ss。
	 * @param millis 要转化的日期毫秒数。
	 * @return 返回日期字符串（如："2013-02-19 11:48:31"）。
	 */
	public static String millisToDateString(long millis){
		return millisToStringFilename(millis, DATE_FORMAT_1);
	}
	
    /**
     * 把日期毫秒转化为下划线字符串（文件名）。
     * @param millis 要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串（yyyy_MM_dd_HH_mm_ss）。
     */
    public static String millisToStringFilename(long millis, String pattern){
        String dateStr = millisToDateString(millis, pattern);
        return dateStr.replaceAll("[- :]", "_");
    }
}
