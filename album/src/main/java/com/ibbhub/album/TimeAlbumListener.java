package com.ibbhub.album;

import android.widget.ImageView;

/**
 * @author ：chezi008 on 2018/8/19 14:46
 * @description ：
 * @email ：chezi008@163.com
 */
interface TimeAlbumListener {

    /**
     * 加载图片，覆盖原来图片的大小
     * 预览多个小图片的时候使用
     *
     * @param path
     * @param iv
     */
    void loadOverrideImage(String path, ImageView iv);

    /**
     * 加载图片
     * 单个图片预览的时候使用
     *
     * @param path
     * @param iv
     */
    void loadImage(String path, ImageView iv);

    /**
     * 选择模式改变
     *
     * @param isChoose
     */
    void onChooseModeChange(boolean isChoose);
}
