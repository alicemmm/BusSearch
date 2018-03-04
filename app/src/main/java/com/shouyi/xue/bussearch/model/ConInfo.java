package com.shouyi.xue.bussearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by asia on 2018/3/4.
 */

public class ConInfo implements Parcelable{

    String plan;
    String distance;
    List<Info> infoList;

    public ConInfo() {
    }

    public static class Info implements Parcelable{
        String workName;
        String siteId;
        String lineName;
        String orderNum;
        String explain;
        String siteName;
        String lineId;

        public Info() {
        }

        protected Info(Parcel in) {
            workName = in.readString();
            siteId = in.readString();
            lineName = in.readString();
            orderNum = in.readString();
            explain = in.readString();
            siteName = in.readString();
            lineId = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(workName);
            dest.writeString(siteId);
            dest.writeString(lineName);
            dest.writeString(orderNum);
            dest.writeString(explain);
            dest.writeString(siteName);
            dest.writeString(lineId);
        }

        public String getWorkName() {
            return workName;
        }

        public void setWorkName(String workName) {
            this.workName = workName;
        }

        public String getSiteId() {
            return siteId;
        }

        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getLineId() {
            return lineId;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel in) {
                return new Info(in);
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[size];
            }
        };
    }

    protected ConInfo(Parcel in) {
        plan = in.readString();
        distance = in.readString();
        infoList = in.createTypedArrayList(Info.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plan);
        dest.writeString(distance);
        dest.writeTypedList(infoList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConInfo> CREATOR = new Creator<ConInfo>() {
        @Override
        public ConInfo createFromParcel(Parcel in) {
            return new ConInfo(in);
        }

        @Override
        public ConInfo[] newArray(int size) {
            return new ConInfo[size];
        }
    };

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }
}
