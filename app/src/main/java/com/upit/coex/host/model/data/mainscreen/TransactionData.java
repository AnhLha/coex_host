package com.upit.coex.host.model.data.mainscreen;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionData extends BaseData implements Serializable {

    @SerializedName("start_time")
    @Expose
    private long mStartTime;

    @SerializedName("duration")
    @Expose
    private int mDuration;

    @SerializedName("numPer")
    @Expose
    private int mNumberPerson;

    @SerializedName("email")
    @Expose
    private String mEmail;

    @SerializedName("phone")
    @Expose
    private String mPhoneNumber;

    @SerializedName("price")
    @Expose
    private int mPrice;

    @SerializedName("roomName")
    @Expose
    private String mRoomName;

    @SerializedName("payment")
    @Expose
    private boolean mPayment;

    @SerializedName("status")
    @Expose
    private String mStatus;

    @SerializedName("key")
    @Expose
    private String mKey;

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public int getNumberPerson() {
        return mNumberPerson;
    }

    public void setNumberPerson(int mNumberPerson) {
        this.mNumberPerson = mNumberPerson;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public boolean getPayment() {
        return mPayment;
    }

    public void setPayment(boolean mPayment) {
        this.mPayment = mPayment;
    }

}
