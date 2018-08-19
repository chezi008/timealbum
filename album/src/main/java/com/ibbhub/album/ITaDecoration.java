package com.ibbhub.album;

import android.view.View;

/**
 * @author ：chezi008 on 2018/8/16 20:28
 * @description ：
 * @email ：chezi008@163.com
 */
public interface ITaDecoration {
    /**
     * 显示日期
     *
     * @param date
     */
    void showDate(long date);

    /**
     * 显示相册下面的视频、图片的数量
     *
     * @param num
     */
    void showNum(int num);

    /**
     * 构建decoration 的视图
     * @return
     */
    View buildView();
}
