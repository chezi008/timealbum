package com.ibbhub.albumdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ibbhub.album.AlbumBean;

import java.util.ArrayList;

public class MyPreviewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_preview);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        MyPreviewFragment fragment = (MyPreviewFragment) getSupportFragmentManager().findFragmentByTag("preview");
        if (fragment == null) {
            fragment = new MyPreviewFragment();
        }
        ArrayList<AlbumBean> data = getIntent().getParcelableArrayListExtra("data");

        toolbar.setSubtitle("1/"+data.size());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        bundle.putInt("pos", getIntent().getIntExtra("pos", 0));
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, fragment, "preview");
        ft.commit();
    }

    public static void start(Context context, ArrayList<AlbumBean> data, int pos) {
        Intent starter = new Intent(context, MyPreviewActivity.class);
        starter.putParcelableArrayListExtra("data", data);
        starter.putExtra("pos", pos);
        context.startActivity(starter);
    }

    public void setSubtitle(String subtitle){
        toolbar.setSubtitle(subtitle);
    }
}
