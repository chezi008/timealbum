package com.ibbhub.album.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.ibbhub.album.AdapterListener;
import com.ibbhub.album.AlbumFragment;
import com.ibbhub.album.AlbumHelper;
import com.ibbhub.album.bean.AlbumBean;
import com.ibbhub.album.bean.MediaBean;
import com.ibbhub.album.util.FileUtils;
import com.ibbhub.album.view.MediaView;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/3 21:10
 * @description ：
 * @email ：chezi008@163.com
 */
public class MediaDelegate extends AdapterDelegate<List<MediaBean>> {

    private AdapterListener<MediaBean> listener;
    @Override
    protected boolean isForViewType(@NonNull List<MediaBean> items, int position) {
        return true;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        listener = AlbumHelper.getInstance().getAdapterListener();
        return new MediaDelegateHolder(new MediaView(parent.getContext()));
    }

    @Override
    protected void onBindViewHolder(@NonNull final List<MediaBean> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final MediaBean mediaBean = items.get(position);
        final MediaDelegateHolder mediaHolder = (MediaDelegateHolder) holder;
        mediaHolder.mediaView.loadImage(mediaBean.path);
        //判断类型
        boolean isImage = FileUtils.isImageFile(mediaBean.path);
        mediaHolder.mediaView.setStyle(isImage ? MediaView.STYLE_PHOTO : MediaView.STYLE_VIDEO);
        mediaHolder.mediaView.setChooseStyle(AlbumFragment.isChooseMode);
        mediaHolder.mediaView.setChecked(mediaBean.isChecked);
        mediaHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlbumFragment.isChooseMode) {
                    mediaBean.setChecked(!mediaBean.isChecked);
                    mediaHolder.mediaView.setChecked(mediaBean.isChecked);
                }
                if (listener != null) {
                    listener.onItemClick(mediaBean, v);
                }
            }
        });
        mediaHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (AlbumFragment.isChooseMode) {
                    return false;
                }
                if (listener != null && !mediaHolder.mediaView.isCheckMode()) {
                    listener.onItemLongClick(items.get(position), v);
                }
                return true;
            }
        });
    }

    public class MediaDelegateHolder extends RecyclerView.ViewHolder {
        MediaView mediaView;

        public MediaDelegateHolder(MediaView itemView) {
            super(itemView);
            this.mediaView = itemView;
        }
    }
}
