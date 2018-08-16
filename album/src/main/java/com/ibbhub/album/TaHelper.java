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

    static class AlbumHelperHolder {
        static final TaHelper instance = new TaHelper();
    }

    public interface TimeAlbumListener {
        /**
         * 加载图片，覆盖原来图片的大小
         *
         * @param path
         * @param iv
         */
        void loadOverrideImage(String path, ImageView iv);

        /**
         * 加载图片
         *
         * @param path
         * @param iv
         */
        void loadImage(String path, ImageView iv);

        /**
         * 选择模式改变
         *
         * @param isChoose
         */
        void onChooseModeChange(boolean isChoose);
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
            helper.srcFiles.clear();
            for (File file :
                    mediaFile) {
                helper.srcFiles.add(file);
            }
            return this;
        }

        public Builder setLoadImageListener(TimeAlbumListener listener) {
            helper.timeAlbumListener = listener;
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
