package com.shouyi.xue.bussearch.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asia on 2018/3/5.
 */

public class SouData implements Parcelable{
    private String lineTypeName;
    private String id;
    private String lineName;

    public SouData() {
    }

    protected SouData(Parcel in) {
        lineTypeName = in.readString();
        id = in.readString();
        lineName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lineTypeName);
        dest.writeString(id);
        dest.writeString(lineName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SouData> CREATOR = new Creator<SouData>() {
        @Override
        public SouData createFromParcel(Parcel in) {
            return new SouData(in);
        }

        @Override
        public SouData[] newArray(int size) {
            return new SouData[size];
        }
    };

    public String getLineTypeName() {
        return lineTypeName;
    }

    public void setLineTypeName(String lineTypeName) {
        this.lineTypeName = lineTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
