package com.ibbhub.album;

import com.ibbhub.adapterdelegate.IbbListDelegateAdapter;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/3 21:09
 * @description ：
 * @email ：chezi008@163.com
 */
 class AlbumAdapter extends IbbListDelegateAdapter<List<AlbumBean>> {

    public AlbumAdapter(List<AlbumBean> data) {
        delegatesManager.addDelegate(new AlbumDelegate());
        setItems(data);
    }

}
