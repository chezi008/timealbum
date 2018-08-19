package com.ibbhub.album;

import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 23:18
 * @description ：Single instance
 * @email ：chezi008@163.com
 */
 class TaHelper {

    private List<File> srcFiles = new ArrayList<>();
    private TimeAlbumListener timeAlbumListener;
    private AdapterListener<AlbumBean> adapterListener;

    private TaHelper() {

    }

    public List<File> getSrcFiles() {
        if (srcFiles.isEmpty()) {
            throw new IllegalStateException("Please setSrcFiles albumHelper first!!");
        }
        return srcFiles;
    }

    public void setAdapterListener(AdapterListener<AlbumBean> adapterListener) {
        this.adapterListener = adapterListener;
    }

    public AdapterListener<AlbumBean> getAdapterListener() {
        return adapterListener;
    }

    private ITaDecoration decoration;

    public ITaDecoration getDecoration() {

        return decoration;
    }

    public void loadThumbImage(String path, ImageView iv) {
        if (timeAlbumListener != null) {
            timeAlbumListener.loadOverrideImage(path, iv);
        }
    }

    public void loadImage(String path, ImageView iv) {
        if (timeAlbumListener != null) {
            timeAlbumListener.loadImage(path, iv);
        }
    }

    public void onChooseModeChange(boolean isChoose) {
        if (timeAlbumListener != null) {
            timeAlbumListener.onChooseModeChange(isChoose);
        }
    }

    public static TaHelper getInstance() {
        return AlbumHelperHolder.instance;
    }

    private static class AlbumHelperHolder {
        static final TaHelper instance = new TaHelper();
    }

    /**
     * 设置媒体源的文件路径
     *
     * @param mediaFile
     * @return
     */
    public TaHelper setSrcFiles(File... mediaFile) {
        srcFiles.clear();
        for (File file :
                mediaFile) {
            srcFiles.add(file);
        }
        return this;
    }
    public TaHelper setSrcFiles(List<File> fileList) {
        srcFiles.clear();
        srcFiles.addAll(fileList);
        return this;
    }

    /**
     * 设置加载图片的回调
     *
     * @param listener
     */
    public TaHelper setLoadImageListener(TimeAlbumListener listener) {
        timeAlbumListener = listener;
        return this;
    }

    /**
     * Set decoration of recyclerView
     * @param iTaDecoration
     */
    public TaHelper setTbDecoration(ITaDecoration iTaDecoration) {
        decoration = iTaDecoration;
        return this;
    }

}
