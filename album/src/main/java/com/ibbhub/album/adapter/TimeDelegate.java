package com.ibbhub.album.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.ibbhub.album.TaHelper;
import com.ibbhub.album.bean.TimeBean;
import com.ibbhub.album.view.TaTimeView;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 23:01
 * @description ：
 * @email ：chezi008@163.com
 */
public class TimeDelegate extends AdapterDelegate<List<TimeBean>> {


    @Override
    protected boolean isForViewType(@NonNull List<TimeBean> items, int position) {
        return true;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TimeDelegateHolder(new TaTimeView(parent.getContext()));
    }

    @Override
    protected void onBindViewHolder(@NonNull final List<TimeBean> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        TimeDelegateHolder albumHolder = (TimeDelegateHolder) holder;
        albumHolder.timeView.notify(items.get(position));
    }

    static class TimeDelegateHolder extends RecyclerView.ViewHolder {
        TaTimeView timeView;

        public TimeDelegateHolder(TaTimeView itemView) {
            super(itemView);
            timeView = itemView;

        }
    }
}
