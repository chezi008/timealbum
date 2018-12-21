package com.ibbhub.albumdemo;

import com.ibbhub.album.AlbumPreviewFragment;

/**
 * @author ：chezi008 on 2018/12/21 11:02
 * @description ：
 * @email ：chezi008@163.com
 */
public class MyPreviewFragment extends AlbumPreviewFragment {
    @Override
    public void onPageChanged(int position) {
        super.onPageChanged(position);
        if (position<mData.size()){
            String title = (position+1)+"/"+mData.size();
            ((MyPreviewActivity)getActivity()).setSubtitle(title);
        }
    }
}
