package com.vinaymaneti.assignmentcha.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinay on 03/12/16.
 */

public class FirstSetTransactionModel implements Parcelable {
    private String amount;
    private String sku;
    private String currency;

    public FirstSetTransactionModel() {

    }

    protected FirstSetTransactionModel(Parcel in) {
        amount = in.readString();
        sku = in.readString();
        currency = in.readString();
    }

    public static final Creator<FirstSetTransactionModel> CREATOR = new Creator<FirstSetTransactionModel>() {
        @Override
        public FirstSetTransactionModel createFromParcel(Parcel in) {
            return new FirstSetTransactionModel(in);
        }

        @Override
        public FirstSetTransactionModel[] newArray(int size) {
            return new FirstSetTransactionModel[size];
        }
    };

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(sku);
        dest.writeString(currency);
    }
}
