package com.ibbhub.album;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ：chezi008 on 2018/8/1 23:15
 * @description ：
 * @email ：chezi008@163.com
 */
 public class AlbumBean implements Parcelable {
    public String path;
    public long date;
    public boolean isChecked;

    public AlbumBean() {

    }

    protected AlbumBean(Parcel in) {
        path = in.readString();
        date = in.readLong();
        isChecked = in.readByte() != 0;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeLong(date);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AlbumBean){
            AlbumBean albumBean = (AlbumBean) obj;
            return albumBean.path.equals(path);
        }
        return super.equals(obj);
    }
}
