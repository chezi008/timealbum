/**
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
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 */
package com.ibbhub.album;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description ：
 * @author ：chezi008 on 2018/8/19 14:49
 * @email ：chezi008@qq.com
 */
class DateUtils {

    public static String converToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd");

        return df.format(date);
    }

    public static Date convertToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd");
        return df.parse(strDate);
    }

    public static String converToString(long timeMillion) {
        DateFormat df = new SimpleDateFormat("yyyy:MM:dd");
        return df.format(timeMillion);
    }
}
