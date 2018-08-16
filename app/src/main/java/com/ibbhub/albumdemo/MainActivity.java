package com.ibbhub.albumdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ibbhub.album.AlbumFragment;
import com.ibbhub.album.TaHelper;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/DCIM/Camera";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initVariable();
        initView();
    }

    private void initVariable() {

    }

    private void initView() {
        AlbumFragment albumFragment = (AlbumFragment) getSupportFragmentManager().findFragmentByTag("album");
        if (albumFragment == null) {
            albumFragment = new TaHelper.Builder()
                    .setSrcFiles(new File(path))
                    .setLoadImageListener(new TaHelper.LoadImageListener() {
                        @Override
                        public void loadThumbImage(String path, ImageView iv) {
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
                    })
                    .create();

        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.clParent, albumFragment);
        ft.commit();
    }

    private void requestPermission() {
        //获取storage权限
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }

    public static RequestOptions buildOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(100, 100);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        return requestOptions;
    }
}
