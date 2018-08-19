package com.ibbhub.album;

import com.ibbhub.adapterdelegate.ListDelegationAdapter;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/9 19:33
 * @description ：
 * @email ：chezi008@163.com
 */
 class PreviewAdappter extends ListDelegationAdapter<List<AlbumBean>> {

    public PreviewAdappter(List<AlbumBean> data) {
        delegatesManager.addDelegate(new PreviewPhotoDelegate());
        delegatesManager.addDelegate(new PreviewVideoDelegate());
        setItems(data);
    }
}
