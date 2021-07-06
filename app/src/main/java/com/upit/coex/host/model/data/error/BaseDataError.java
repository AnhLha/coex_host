package com.upit.coex.host.model.data.error;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.upit.coex.host.CoexApplication;
import com.upit.coex.host.model.data.base.BaseData;

import java.io.IOException;

public class BaseDataError extends BaseData{
    @SerializedName("code")
    @Expose
    private Long mCode;

    @SerializedName("message")
    @Expose
    private String mMessage;

    //
    public BaseDataError(Throwable e) {
        try {
            BaseDataError error = CoexApplication.self().getGSon().fromJson(((HttpException) e).response().errorBody().string(), BaseDataError.class);
            this.mCode = error.getmCode();
            this.mMessage = error.getmMessage();
        } catch (Exception io) {
            // loi mang
            Log.d("cin", "------------------");
        }
    }

    //
    public BaseDataError(String s){
        BaseDataError error = CoexApplication.self().getGSon().fromJson(s, BaseDataError.class);
        this.mCode = error.getmCode();
        this.mMessage = error.getmMessage();
    }

    public Long getmCode() {
        return mCode;
    }

    public void setmCode(Long mCode) {
        this.mCode = mCode;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
