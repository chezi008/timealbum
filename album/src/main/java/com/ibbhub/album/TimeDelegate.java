package com.ibbhub.album;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ibbhub.adapterdelegate.AbsFallbackAdapterDelegate;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 23:01
 * @description ：
 * @email ：chezi008@163.com
 */
 class TimeDelegate extends AbsFallbackAdapterDelegate<List<TimeBean>> {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TimeDelegateHolder(new TaTimeView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull List<TimeBean> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        super.onBindViewHolder(items, position, holder, payloads);
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
