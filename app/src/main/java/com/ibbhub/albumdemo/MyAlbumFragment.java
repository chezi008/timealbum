package com.ibbhub.albumdemo;

import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ibbhub.album.AlbumBean;
import com.ibbhub.album.AlbumFragment;
import com.ibbhub.album.ITaDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/19 14:57
 * @description ：
 * @email ：chezi008@163.com
 */
public class MyAlbumFragment extends AlbumFragment {
    @Override
    public List<File> buildAlbumSrc() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/DCIM/Camera";
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(path));
        return fileList;
    }

    @Override
    public Boolean obtainFile(File file) {
        return file.getName().endsWith(".mp4") || file.getName().endsWith(".jpg");
    }

    @Override
    public ITaDecoration buildDecoration() {
        return null;
    }

    @Override
    public String fileProviderName() {
        return BuildConfig.APPLICATION_ID+".album.provider";
    }

    @Override
    public void loadOverrideImage(String path, ImageView iv) {
        Glide.with(iv)
                .load(path)
                .thumbnail(0.1f)
                .apply(buildOptions())
                .into(iv);
    }

    @Override
    public void loadImage(String path, ImageView iv) {
        Glide.with(iv)
                .load(path)
                .thumbnail(0.1f)
                .into(iv);
    }

    @Override
    public void onChooseModeChange(boolean isChoose) {
        ((AlbumActivity)getActivity()).onChooseModeChange(isChoose);
    }

    public static RequestOptions buildOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(100, 100);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        return requestOptions;
    }

    @Override
    public void start2Preview(ArrayList<AlbumBean> data, int pos) {
        MyPreviewActivity.start(getContext(), data, pos);
    }
}
