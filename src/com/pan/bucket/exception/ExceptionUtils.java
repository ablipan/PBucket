/**
 * author :  lipan
 * filename :  ExceptionUtils.java
 * create_time : 2014-12-6 下午3:04:14
 */
package com.pan.bucket.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author : lipan
 * @create_time : 2014-12-6 下午3:04:14
 * @desc : 异常日志
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class ExceptionUtils
{

    public static String getDetailInfo(Exception e)
    {
        StringWriter sw = new StringWriter();   
        PrintWriter pw = new PrintWriter(sw, true);   
        e.printStackTrace(pw);   
        pw.flush();   
        sw.flush();   
        return sw.toString();  
    }
}
