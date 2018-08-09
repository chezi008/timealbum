package com.ibbhub.album;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

/**
 * @author ：chezi008 on 2018/8/9 23:01
 * @description ：The share manager of album
 * @email ：chezi008@163.com
 */
public class ALShareManager {
    private ALShareManager() {

    }

    public static ALShareManager getInstance() {
        return ALShareManagerHolder.instance;
    }

    /**
     * 分享
     *
     * @param ctx
     * @param path
     */
    public void openShare(Context ctx, String path) {
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType(path.endsWith(".mp4") ? "video/*" : "image/*");
        ctx.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     *
     * @param ctx
     * @param uris
     */
    public void openShare(Context ctx,ArrayList<Uri> uris){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        shareIntent.setType("image/*");
        ctx.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    private static class ALShareManagerHolder {
        static final ALShareManager instance = new ALShareManager();
    }
}
