package com.ibbhub.album;

import com.ibbhub.adapterdelegate.IbbListDelegateAdapter;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 22:57
 * @description ：
 * @email ：chezi008@163.com
 */
 class TimeAdapter extends IbbListDelegateAdapter<List<TimeBean>> {

    private TimeDelegate timeDelegate;
    public TimeAdapter(List<TimeBean> data) {
        addDelegate();
        setItems(data);
    }

    private void addDelegate() {
        timeDelegate = new TimeDelegate();
        delegatesManager.setFallbackDelegate(timeDelegate);
    }

}
