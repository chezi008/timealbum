package com.ibbhub.album;

import android.view.View;

/**
 * @author ：chezi008 on 2018/8/6 21:11
 * @description ：
 * @email ：chezi008@163.com
 */
public interface AdapterListener<T> {
    /**
     * 适配器单击事件
     *
     * @param t
     * @param v
     */
    void onItemClick(T t, View v);

    /**
     * 适配器长按事件
     *
     * @param t
     * @param v
     */
    void onItemLongClick(T t, View v);
}
