package com.bingo.demo.data;

import android.os.Parcel;
import android.os.Parcelable;

public class PayInfo implements Parcelable {
    public static final Parcelable.Creator<PayInfo> CREATOR = new Parcelable.Creator<PayInfo>() {
        @Override
        public PayInfo createFromParcel(Parcel source) {
            return new PayInfo(source);
        }

        @Override
        public PayInfo[] newArray(int size) {
            return new PayInfo[size];
        }
    };
    private String payMode;
    private float payMoney;
    private String payTip;

    public PayInfo() {
    }

    protected PayInfo(Parcel in) {
        this.payMode = in.readString();
        this.payMoney = in.readFloat();
        this.payTip = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payMode);
        dest.writeFloat(this.payMoney);
        dest.writeString(this.payTip);
    }
}
