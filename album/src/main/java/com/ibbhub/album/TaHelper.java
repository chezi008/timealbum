package com.ibbhub.album;

import android.widget.ImageView;

import com.ibbhub.album.bean.AlbumBean;
import com.ibbhub.album.view.ITaDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 23:18
 * @description ：Single instance
 * @email ：chezi008@163.com
 */
public class TaHelper {

    private List<File> srcFiles = new ArrayList<>();
    private LoadImageListener loadImageListener;
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
        if (loadImageListener != null) {
            loadImageListener.loadThumbImage(path, iv);
        }
    }

    public void loadImage(String path, ImageView iv) {
        if (loadImageListener != null) {
            loadImageListener.loadImage(path, iv);
        }
    }

    public static TaHelper getInstance() {
        return AlbumHelperHolder.instance;
    }

    static class AlbumHelperHolder {
        static final TaHelper instance = new TaHelper();
    }

    public interface LoadImageListener {
        void loadThumbImage(String path, ImageView iv);

        void loadImage(String path, ImageView iv);
    }

    public static class Builder {
        private TaHelper helper;
        private AlbumFragment fragment;

        public Builder() {
            helper = TaHelper.getInstance();
            fragment = new AlbumFragment();
        }

        /**
         * @param mediaFile
         * @return
         */
        public Builder setSrcFiles(File... mediaFile) {
            for (File file :
                    mediaFile) {
                helper.srcFiles.add(file);
            }
            return this;
        }

        public Builder setLoadImageListener(LoadImageListener listener) {
            helper.loadImageListener = listener;
            return this;
        }

        public Builder setTbDecoration(ITaDecoration iTaDecoration) {
            helper.decoration = iTaDecoration;
            return this;
        }

        public AlbumFragment create() {
            return fragment;
        }
    }

}
