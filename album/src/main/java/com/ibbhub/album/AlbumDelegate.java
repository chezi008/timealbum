package com.ibbhub.album;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ibbhub.adapterdelegate.AbsFallbackAdapterDelegate;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/3 21:10
 * @description ：
 * @email ：chezi008@163.com
 */
 class AlbumDelegate extends AbsFallbackAdapterDelegate<List<AlbumBean>> {

    private AdapterListener<AlbumBean> listener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        listener = TaHelper.getInstance().getAdapterListener();
        return new AlbumDelegateHolder(new TaAlbumView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull final List<AlbumBean> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        super.onBindViewHolder(items,position,holder,payloads);
        final AlbumBean albumBean = items.get(position);
        final AlbumDelegateHolder mediaHolder = (AlbumDelegateHolder) holder;
        mediaHolder.taAlbumView.loadImage(albumBean.path);
        //判断类型
        boolean isImage = FileUtils.isImageFile(albumBean.path);
        mediaHolder.taAlbumView.setStyle(isImage ? TaAlbumView.STYLE_PHOTO : TaAlbumView.STYLE_VIDEO);
        mediaHolder.taAlbumView.setChooseStyle(AlbumFragment.isChooseMode);
        mediaHolder.taAlbumView.setChecked(albumBean.isChecked);
        mediaHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlbumFragment.isChooseMode) {
                    albumBean.setChecked(!albumBean.isChecked);
                    mediaHolder.taAlbumView.setChecked(albumBean.isChecked);
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
                if (listener != null && !mediaHolder.taAlbumView.isCheckMode()) {
                    listener.onItemLongClick(items.get(position), v);
                }
                return true;
            }
        });
    }

    public class AlbumDelegateHolder extends RecyclerView.ViewHolder {
        TaAlbumView taAlbumView;

        public AlbumDelegateHolder(TaAlbumView itemView) {
            super(itemView);
            this.taAlbumView = itemView;
        }
    }
}
