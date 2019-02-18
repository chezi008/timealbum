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
//                date1 = DateUtils.convertToDate("1995:03:13 22:38:20");
                date1 = new Date(file.lastModified());
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

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
        } else if (file.isFile()) {
            return deleteFile(fileName);
        }
        return false;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
