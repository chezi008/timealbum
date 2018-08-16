package com.ibbhub.album.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.ibbhub.album.bean.TimeBean;
import com.ibbhub.album.R;
import com.ibbhub.album.view.GridDecoration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 23:01
 * @description ：
 * @email ：chezi008@163.com
 */
public class TimeDelegate extends AdapterDelegate<List<TimeBean>> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected boolean isForViewType(@NonNull List<TimeBean> items, int position) {
        return true;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album, parent, false);
        AlbumDelegateHolder holder = new AlbumDelegateHolder(item);
        return holder;
    }

    @Override
    protected void onBindViewHolder(@NonNull final List<TimeBean> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        AlbumDelegateHolder albumHolder = (AlbumDelegateHolder) holder;
        String date = dateFormat.format(new Date(items.get(position).date));
        albumHolder.tv_date.setText(date);

        MediaAdapter adapter = new MediaAdapter(items.get(position).itemList);
        albumHolder.rc_list.setAdapter(adapter);

    }

    static class AlbumDelegateHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        RecyclerView rc_list;

        public AlbumDelegateHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            rc_list = itemView.findViewById(R.id.rc_list);

            rc_list.addItemDecoration(new GridDecoration(4, 5, true));
        }
    }
}
