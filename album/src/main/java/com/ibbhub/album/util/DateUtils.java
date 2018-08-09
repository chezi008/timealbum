/**
 *
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 */
package com.ibbhub.album.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


 public class DateUtils {

    public static String converToString(Date date)
    {  
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd");  
          
        return df.format(date);  
    }  

    public static Date convertToDate(String strDate) throws Exception
    {  
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd");  
        return df.parse(strDate);  
    }

    public static String converToString(long timeMillion) {
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd");
        return df.format(timeMillion);
    }
}
