package com.ibbhub.album;

import android.widget.ImageView;

import com.ibbhub.album.bean.MediaBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 23:18
 * @description ：Single instance
 * @email ：chezi008@163.com
 */
public class AlbumHelper {

    private List<File> srcFiles = new ArrayList<>();
    private LoadImageListener loadImageListener;
    private AdapterListener<MediaBean> adapterListener;

    private AlbumHelper() {

    }

    public AlbumHelper setSrcFiles(File... mediaFile) {
        for (File file :
                mediaFile) {
            srcFiles.add(file);
        }
        return this;
    }

    public List<File> getSrcFiles() {
        if (srcFiles.isEmpty()) {
            throw new IllegalStateException("Please setSrcFiles albumHelper first!!");
        }
        return srcFiles;
    }

    public AlbumHelper setLoadImageListener(LoadImageListener listener) {
        loadImageListener = listener;
        return this;
    }

    public void setAdapterListener(AdapterListener<MediaBean> adapterListener) {
        this.adapterListener = adapterListener;
    }

    public AdapterListener<MediaBean> getAdapterListener() {
        return adapterListener;
    }


    public void loadThumbImage(String path, ImageView iv) {
        if (loadImageListener != null) {
            loadImageListener.loadThumbImage(path, iv);
        }
    }

    public void loadImage(String path, ImageView iv){
        if (loadImageListener != null) {
            loadImageListener.loadImage(path,iv);
        }
    }

    public static AlbumHelper getInstance() {
        return AlbumHelperHolder.instance;
    }

    static class AlbumHelperHolder {
        static final AlbumHelper instance = new AlbumHelper();
    }

    public interface LoadImageListener {
        void loadThumbImage(String path, ImageView iv);
        void loadImage(String path, ImageView iv);
    }

}
