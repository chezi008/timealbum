package com.ibbhub.album;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.ibbhub.adapterdelegate.AdapterDelegate;

import java.util.List;

import chuangyuan.ycj.videolibrary.listener.VideoInfoListener;
import chuangyuan.ycj.videolibrary.video.ManualPlayer;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;

/**
 * @author ：chezi008 on 2018/8/9 19:41
 * @description ：
 * @email ：chezi008@163.com
 */
 class PreviewVideoDelegate extends AdapterDelegate<List<AlbumBean>> {
    @Override
    public boolean isForViewType(@NonNull List<AlbumBean> items, int position) {
        return items.get(position).path.endsWith(".mp4");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preview_video, parent, false);
        return new PreviewVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<AlbumBean> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final PreviewVideoHolder pHolder = (PreviewVideoHolder) holder;
        AlbumBean mb = items.get(position);
        pHolder.vp.setTitle(FileUtils.obtainFileName(mb.path));
        //thumb
        pHolder.vp.getPreviewImage().setScaleType(ImageView.ScaleType.FIT_CENTER);
        TaHelper.getInstance().loadImage(mb.path, pHolder.vp.getPreviewImage());

        pHolder.mp.setTag(position);
        pHolder.mp.setPlayUri(mb.path);
        pHolder.mp.addVideoInfoListener(new VideoInfoListener() {
            @Override
            public void onPlayStart(long currPosition) {

            }

            @Override
            public void onLoadingChanged() {

            }

            @Override
            public void onPlayerError(@Nullable ExoPlaybackException e) {

            }

            @Override
            public void onPlayEnd() {
                pHolder.mp.setPosition(0);
            }

            @Override
            public void isPlaying(boolean playWhenReady) {

            }
        });
    }

    static class PreviewVideoHolder extends RecyclerView.ViewHolder {
        public VideoPlayerView vp;
        public ManualPlayer mp;

        public PreviewVideoHolder(View itemView) {
            super(itemView);
            vp = itemView.findViewById(R.id.exo_play_context_id);
            mp = new ManualPlayer((Activity) itemView.getContext(), vp);
        }
    }
}
