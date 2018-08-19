package com.ibbhub.album;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.ibbhub.adapterdelegate.AdapterDelegate;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/9 19:34
 * @description ：
 * @email ：chezi008@163.com
 */
 class PreviewPhotoDelegate extends AdapterDelegate<List<AlbumBean>> {
    private String TAG = getClass().getSimpleName();
    @Override
    public boolean isForViewType(@NonNull List<AlbumBean> items, int position) {
        return FileUtils.isImageFile(items.get(position).path);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_photo, parent, false);
        return new PreviewPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<AlbumBean> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AlbumBean mb = items.get(position);
//        Log.d(TAG, "onBindViewHolder: "+mb.path);
        TaHelper.getInstance().loadImage(mb.path, ((PreviewPhotoHolder) holder).ptView);
    }

    static class PreviewPhotoHolder extends RecyclerView.ViewHolder {
        private PhotoView ptView;

        public PreviewPhotoHolder(View itemView) {
            super(itemView);
            ptView = itemView.findViewById(R.id.ptView);
        }
    }
}
