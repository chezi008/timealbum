package com.ibbhub.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chezi008 on 2018/8/1 22:58
 * @description ：
 * @email ：chezi008@163.com
 */
public class AlbumBean implements Parcelable {
    public long date;
    public List<MediaBean> itemList = new ArrayList<>();

    public AlbumBean() {

    }

    protected AlbumBean(Parcel in) {
        date = in.readLong();
        itemList = in.createTypedArrayList(MediaBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date);
        dest.writeTypedList(itemList);
    }

    public static final Creator<AlbumBean> CREATOR = new Creator<AlbumBean>() {
        @Override
        public AlbumBean createFromParcel(Parcel in) {
            return new AlbumBean(in);
        }

        @Override
        public AlbumBean[] newArray(int size) {
            return new AlbumBean[size];
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

    public List<MediaBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<MediaBean> itemList) {
        this.itemList = itemList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlbumBean) {
            AlbumBean ab = (AlbumBean) obj;
            return this.date == ab.date;
        }
        return super.equals(obj);
    }
}
