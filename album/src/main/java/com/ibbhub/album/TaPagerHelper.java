package com.ibbhub.album;

import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

/**
 * @author ：chezi008 on 2018/8/9 21:55
 * @description ：使得recyclerView达到viewPager的效果
 * @email ：chezi008@163.com
 */
public class TaPagerHelper extends PagerSnapHelper {
    private PageHelperListener listener;

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int pos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        if (listener != null) {
            listener.onPageChanged(pos);
        }
        return pos;
    }

    public void setListener(PageHelperListener listener) {
        this.listener = listener;
    }

    interface PageHelperListener {
        void onPageChanged(int position);
    }
}
