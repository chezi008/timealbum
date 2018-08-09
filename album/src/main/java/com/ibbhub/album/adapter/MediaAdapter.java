package com.ibbhub.album.adapter;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.ibbhub.album.bean.MediaBean;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/3 21:09
 * @description ：
 * @email ：chezi008@163.com
 */
public class MediaAdapter extends ListDelegationAdapter<List<MediaBean>> {

    public MediaAdapter(List<MediaBean> data) {
        delegatesManager.addDelegate(new MediaDelegate());
        setItems(data);
    }

}
