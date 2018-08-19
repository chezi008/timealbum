package com.ibbhub.album;

import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author ：chezi008 on 2018/8/2 0:02
 * @description ：
 * @email ：chezi008@163.com
 */
class FileUtils {
    public static final String TAG = FileUtils.class.getSimpleName();

    public static Date parseDate(File file) {
        ExifInterface exif = null;
        Date date1 = null;
        try {
            exif = new ExifInterface(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
        try {
            if (!TextUtils.isEmpty(date)) {
                date1 = DateUtils.convertToDate(date);
            } else {
                date1 = DateUtils.convertToDate("1995:03:13 22:38:20");
            }
            Log.i("date", date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static boolean isImageFile(String fName) {
        boolean re;
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();
        if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            re = true;
        } else {
            re = false;
        }
        return re;
    }

    public static String obtainFileName(String path) {
        File file = new File(path);
        return file.getName();
    }
}
