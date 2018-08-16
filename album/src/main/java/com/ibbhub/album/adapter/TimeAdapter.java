package com.ibbhub.album.adapter;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.ibbhub.album.bean.TimeBean;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 22:57
 * @description ：
 * @email ：chezi008@163.com
 */
public class TimeAdapter extends ListDelegationAdapter<List<TimeBean>> {

    private TimeDelegate timeDelegate;
    public TimeAdapter(List<TimeBean> data) {
        addDelegate();
        setItems(data);
    }

    private void addDelegate() {
        timeDelegate = new TimeDelegate();
        delegatesManager.addDelegate(timeDelegate);
    }

}
