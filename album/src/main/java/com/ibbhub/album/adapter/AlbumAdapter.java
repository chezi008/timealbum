package com.ibbhub.album.adapter;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.ibbhub.album.bean.AlbumBean;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/3 21:09
 * @description ：
 * @email ：chezi008@163.com
 */
public class AlbumAdapter extends ListDelegationAdapter<List<AlbumBean>> {

    public AlbumAdapter(List<AlbumBean> data) {
        delegatesManager.addDelegate(new AlbumDelegate());
        setItems(data);
    }

}
