package com.ibbhub.album;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author ：chezi008 on 2018/8/9 23:01
 * @description ：The share manager of album
 * @email ：chezi008@163.com
 */
 class TaShareManager {
    private TaShareManager() {

    }

    public static TaShareManager getInstance() {
        return ALShareManagerHolder.instance;
    }


    private String fileProviderName;

    public void setFileProviderName(String fileProviderName) {
        this.fileProviderName = fileProviderName;
    }

    /**
     * 分享
     *
     * @param ctx
     * @param path
     */
    public void openShare(Context ctx, String path) {
        if (TextUtils.isEmpty(fileProviderName)) {
            throw new NullPointerException("请在 albumfragment 设置fileProviderName");
        }
//        BuildConfig.APPLICATION_ID + ".provider"
        Uri imageUri =FileProvider.getUriForFile(ctx, fileProviderName, new File(path));
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
        static final TaShareManager instance = new TaShareManager();
    }
}
