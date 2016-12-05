package com.vinaymaneti.assignmentcha.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinay on 04/12/16.
 */

public class FirstSetRatesModel implements Parcelable {
    private String from;
    private String rate;
    private String to;

    public FirstSetRatesModel() {

    }

    protected FirstSetRatesModel(Parcel in) {
        from = in.readString();
        rate = in.readString();
        to = in.readString();
    }

    public static final Creator<FirstSetRatesModel> CREATOR = new Creator<FirstSetRatesModel>() {
        @Override
        public FirstSetRatesModel createFromParcel(Parcel in) {
            return new FirstSetRatesModel(in);
        }

        @Override
        public FirstSetRatesModel[] newArray(int size) {
            return new FirstSetRatesModel[size];
        }
    };

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(rate);
        dest.writeString(to);
    }
}
