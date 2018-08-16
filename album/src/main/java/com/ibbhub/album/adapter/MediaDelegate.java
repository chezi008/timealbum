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
import com.ibbhub.album.util.FileUtils;
import com.ibbhub.album.view.AlbumView;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/3 21:10
 * @description ：
 * @email ：chezi008@163.com
 */
public class MediaDelegate extends AdapterDelegate<List<AlbumBean>> {

    private AdapterListener<AlbumBean> listener;
    @Override
    protected boolean isForViewType(@NonNull List<AlbumBean> items, int position) {
        return true;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        listener = AlbumHelper.getInstance().getAdapterListener();
        return new MediaDelegateHolder(new AlbumView(parent.getContext()));
    }

    @Override
    protected void onBindViewHolder(@NonNull final List<AlbumBean> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AlbumBean albumBean = items.get(position);
        final MediaDelegateHolder mediaHolder = (MediaDelegateHolder) holder;
        mediaHolder.albumView.loadImage(albumBean.path);
        //判断类型
        boolean isImage = FileUtils.isImageFile(albumBean.path);
        mediaHolder.albumView.setStyle(isImage ? AlbumView.STYLE_PHOTO : AlbumView.STYLE_VIDEO);
        mediaHolder.albumView.setChooseStyle(AlbumFragment.isChooseMode);
        mediaHolder.albumView.setChecked(albumBean.isChecked);
        mediaHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlbumFragment.isChooseMode) {
                    albumBean.setChecked(!albumBean.isChecked);
                    mediaHolder.albumView.setChecked(albumBean.isChecked);
                }
                if (listener != null) {
                    listener.onItemClick(albumBean, v);
                }
            }
        });
        mediaHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (AlbumFragment.isChooseMode) {
                    return false;
                }
                if (listener != null && !mediaHolder.albumView.isCheckMode()) {
                    listener.onItemLongClick(items.get(position), v);
                }
                return true;
            }
        });
    }

    public class MediaDelegateHolder extends RecyclerView.ViewHolder {
        AlbumView albumView;

        public MediaDelegateHolder(AlbumView itemView) {
            super(itemView);
            this.albumView = itemView;
        }
    }
}
