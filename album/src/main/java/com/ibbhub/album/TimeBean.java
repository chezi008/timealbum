package com.ibbhub.album;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 22:58
 * @description ：
 * @email ：chezi008@163.com
 */
 class TimeBean implements Parcelable {
    public long date;
    public List<AlbumBean> itemList = new ArrayList<>();

    public TimeBean() {

    }

    public TimeBean(long date) {
        this.date = date;
    }

    protected TimeBean(Parcel in) {
        date = in.readLong();
        itemList = in.createTypedArrayList(AlbumBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date);
        dest.writeTypedList(itemList);
    }

    public static final Creator<TimeBean> CREATOR = new Creator<TimeBean>() {
        @Override
        public TimeBean createFromParcel(Parcel in) {
            return new TimeBean(in);
        }

        @Override
        public TimeBean[] newArray(int size) {
            return new TimeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<AlbumBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<AlbumBean> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeBean) {
            TimeBean ab = (TimeBean) obj;
            return this.date == ab.date;
        }
        return super.equals(obj);
    }
}
