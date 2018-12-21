package com.ibbhub.album;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

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
    public void onBindViewHolder(@NonNull List<AlbumBean> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AlbumBean albumBean = items.get(position);
        AlbumDelegateHolder mediaHolder = (AlbumDelegateHolder) holder;
        mediaHolder.setAlbumBean(albumBean);
        mediaHolder.taAlbumView.loadImage(albumBean.path);
        //判断类型
        boolean isImage = FileUtils.isImageFile(albumBean.path);
        mediaHolder.taAlbumView.setStyle(isImage ? TaAlbumView.STYLE_PHOTO : TaAlbumView.STYLE_VIDEO);
        mediaHolder.taAlbumView.setChooseStyle(AlbumFragment.isChooseMode);
        mediaHolder.taAlbumView.setChecked(albumBean.isChecked);


        boolean flagVisiable = albumBean.path.contains("super");
        mediaHolder.taAlbumView.ivFlag.setVisibility(flagVisiable?View.VISIBLE:View.GONE);
    }

    public class AlbumDelegateHolder extends RecyclerView.ViewHolder {
        private AlbumBean albumBean;
        TaAlbumView taAlbumView;

        public AlbumDelegateHolder(TaAlbumView itemView) {
            super(itemView);
            this.taAlbumView = itemView;
            taAlbumView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AlbumFragment.isChooseMode) {
                        albumBean.setChecked(!albumBean.isChecked);
                        taAlbumView.setChecked(albumBean.isChecked);
                    }else if (listener != null) {
                        listener.onItemClick(albumBean, v);
                    }
                }
            });
            taAlbumView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (AlbumFragment.isChooseMode) {
                        return false;
                    }
                    if (listener != null && !taAlbumView.isCheckMode()) {
                        listener.onItemLongClick(albumBean, v);
                    }
                    return true;
                }
            });
            taAlbumView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null&&AlbumFragment.isChooseMode) {
                        listener.onItemClick(albumBean, buttonView);
                    }
                }
            });

        }

        public void setAlbumBean(AlbumBean albumBean) {
            this.albumBean = albumBean;
        }
    }
}
