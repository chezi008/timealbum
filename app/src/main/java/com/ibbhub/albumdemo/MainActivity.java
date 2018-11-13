package com.ibbhub.albumdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ibbhub.album.AlbumFragment;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;
/**
 * @description ：
 * @author ：chezi008 on 2018/8/19 15:12
 * @email ：chezi008@qq.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        findViewById(R.id.btnAlbum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlbumActivity.start(MainActivity.this);
            }
        });
    }

    private void initVariable() {
        requestPermission();
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



}
