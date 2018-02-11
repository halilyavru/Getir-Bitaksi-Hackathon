package com.halilyavru.getirbitaksihackathon;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by halilmac on 10/02/2018.
 */

public class Record implements Parcelable {

    private String id;
    private String key;
    private String value;
    private String ceratedAt;
    private int totalCount;

    public Record() {}

    public Record(String id, String key, String value, String ceratedAt, int totalCount) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.ceratedAt = ceratedAt;
        this.totalCount = totalCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCeratedAt() {
        return ceratedAt;
    }

    public void setCeratedAt(String ceratedAt) {
        this.ceratedAt = ceratedAt;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public static Creator<Record> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(value);
        parcel.writeString(ceratedAt);
        parcel.writeInt(totalCount);
    }


    private Record(Parcel in){
        this.id = in.readString();
        this.key = in.readString();
        this.value = in.readString();
        this.ceratedAt = in.readString();
        this.totalCount = in.readInt();;
    }

    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {

        @Override
        public Record createFromParcel(Parcel source) {
            return new Record(source);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}
