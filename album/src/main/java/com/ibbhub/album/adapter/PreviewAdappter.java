package com.ibbhub.album.adapter;

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter;
import com.ibbhub.album.bean.MediaBean;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/9 19:33
 * @description ：
 * @email ：chezi008@163.com
 */
public class PreviewAdappter extends ListDelegationAdapter<List<MediaBean>> {

    public PreviewAdappter(List<MediaBean> data) {
        delegatesManager.addDelegate(new PreviewPhotoDelegate());
        delegatesManager.addDelegate(new PreviewVideoDelegate());
        setItems(data);
    }
}
