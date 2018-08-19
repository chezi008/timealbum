package com.ibbhub.album;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * @author ：chezi008 on 2018/8/16 20:26
 * @description ：The decoration of timeAlbum
 * @email ：chezi008@163.com
 */
 class TaDecoration implements ITaDecoration {

    private TextView mTvDate, mTvNum;
    private LinearLayout llParent;

    public TaDecoration(Context ctx) {
        int lPadding = dip2px(ctx, 10);
        int tPadding = dip2px(ctx, 5);
        llParent = new LinearLayout(ctx);
        llParent.setOrientation(LinearLayout.HORIZONTAL);
        llParent.setPadding(lPadding, tPadding, lPadding, tPadding);

        mTvDate = new TextView(ctx);
        mTvDate.setTextSize(16);
        mTvNum = new TextView(ctx);
        mTvNum.setTextSize(16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = tPadding;
        llParent.addView(mTvDate);
        llParent.addView(mTvNum, params);

        llParent.setBackgroundColor(Color.parseColor("#DAEDF4"));
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void showDate(long date) {
        String sDate = dateFormat.format(date);
        mTvDate.setText(sDate);
    }

    @Override
    public void showNum(int num) {
        String sNum = num > 0 ? String.format("(%d)", num) : "";
        mTvNum.setText(sNum);
    }

    @Override
    public View buildView() {
        return llParent;
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
