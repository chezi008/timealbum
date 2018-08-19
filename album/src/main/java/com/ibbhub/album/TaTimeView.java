package com.ibbhub.album;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/16 20:18
 * @description ：
 * @email ：chezi008@163.com
 */
 class TaTimeView extends LinearLayout {
    public TaTimeView(Context context) {
        this(context, null);
    }

    public TaTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private ITaDecoration decoration;
    private AlbumAdapter adapter;
    private List<AlbumBean> data = new ArrayList<>();

    private void initView() {
        setOrientation(VERTICAL);

        decoration = TaHelper.getInstance().getDecoration();
        if (decoration == null) {
            decoration = new TaDecoration(getContext());
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (decoration != null) {
            addView(decoration.buildView(), params);
        }
        RecyclerView rcView = new RecyclerView(getContext());
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        addView(rcView, params);

        adapter = new AlbumAdapter(data);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rcView.setLayoutManager(gridLayoutManager);
        rcView.setAdapter(adapter);
        rcView.addItemDecoration(new GridDecoration(4, 5, true));
    }

    public void notify(TimeBean timeBean) {

        decoration.showDate(timeBean.date);
        int size = timeBean.itemList.size();
        decoration.showNum(size);

        data.clear();
        data.addAll(timeBean.itemList);
        adapter.notifyDataSetChanged();
    }
}
