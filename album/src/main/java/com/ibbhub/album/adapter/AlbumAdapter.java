package com.ibbhub.album.adapter;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.ibbhub.album.AdapterListener;
import com.ibbhub.album.bean.AlbumBean;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 22:57
 * @description ：
 * @email ：chezi008@163.com
 */
public class AlbumAdapter extends ListDelegationAdapter<List<AlbumBean>> {

    private AlbumDelegate albumDelegate;
    public AlbumAdapter(List<AlbumBean> data) {
        addDelegate();
        setItems(data);
    }

    private void addDelegate() {
        albumDelegate = new AlbumDelegate();
        delegatesManager.addDelegate(albumDelegate);
    }

}
