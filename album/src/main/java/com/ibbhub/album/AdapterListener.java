package com.ibbhub.album;

import android.view.View;

/**
 * @author ：chezi008 on 2018/8/19 14:36
 * @description ：
 * @email ：chezi008@163.com
 */
 interface AdapterListener<T> {

    void onItemClick(T t, View v);

    void onItemLongClick(T t, View v);
}
