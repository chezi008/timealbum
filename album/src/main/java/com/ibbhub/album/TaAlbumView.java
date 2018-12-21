package com.ibbhub.album;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * @author ：chezi008 on 2018/8/3 21:18
 * @description ：the view of photo or video
 * @email ：chezi008@163.com
 */
 class TaAlbumView extends FrameLayout {

    public static final int STYLE_PHOTO = 0;
    public static final int STYLE_VIDEO = 1;

    private ImageView iv_thumb, iv_play;
    private CheckBox cb_check;
    private View v_mask;

    public ImageView ivFlag;

    public TaAlbumView(@NonNull Context context) {
        this(context, null);
    }

    public TaAlbumView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_media, this);
        initView();
    }

    private void initView() {
        iv_thumb = findViewById(R.id.iv_thumb);
        iv_play = findViewById(R.id.iv_play);
        cb_check = findViewById(R.id.cb_check);
        v_mask = findViewById(R.id.v_mask);
        ivFlag = findViewById(R.id.ivFlag);
        //The default style
        setStyle(STYLE_PHOTO);
        setChooseStyle(false);

        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                v_mask.setVisibility(isChecked ? VISIBLE : GONE);
            }
        });

    }

    public void setStyle(int style) {
        iv_play.setVisibility(style == STYLE_PHOTO ? GONE : VISIBLE);
    }

    public void setChooseStyle(boolean choose) {

        cb_check.setVisibility(choose ? VISIBLE : GONE);
    }

    public void setChecked(){
        cb_check.setChecked(!cb_check.isChecked());
    }

    public void setChecked(boolean checked) {
        cb_check.setChecked(checked);
    }



    public boolean isCheckMode(){
        return cb_check.getVisibility() == VISIBLE;

    }

    public void setOnCheckedChangeListener(@Nullable CompoundButton.OnCheckedChangeListener listener){
        cb_check.setOnCheckedChangeListener(listener);
    }

    public void loadImage(String path) {
        TaHelper.getInstance().loadThumbImage(path, iv_thumb);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) {
            int dimensSpec = MeasureSpec.makeMeasureSpec(width > height ? width : height, MeasureSpec.EXACTLY);
            super.onMeasure(dimensSpec, dimensSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
