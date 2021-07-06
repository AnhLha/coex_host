package com.upit.coex.host.model.request.logout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("firebase_token")
    @Expose
    private String mFirebaseToken;

    public LogoutRequest(String mFirebaseToken) {
        this.mFirebaseToken = mFirebaseToken;
    }

    public String getmFirebaseToken() {
        return mFirebaseToken;
    }

    public void setmFirebaseToken(String mFirebaseToken) {
        this.mFirebaseToken = mFirebaseToken;
    }
}
