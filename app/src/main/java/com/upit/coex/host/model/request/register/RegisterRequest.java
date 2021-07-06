package com.upit.coex.host.model.request.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("email")
    @Expose
    private String mEmail;
    @SerializedName("password")
    @Expose
    private String mPassword;
    @SerializedName("typeUser")
    @Expose
    private Boolean mTypeUser;

    public RegisterRequest(String mEmail, String mPassword, Boolean mTypeUser) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mTypeUser = mTypeUser;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public Boolean getTypeUser() {
        return mTypeUser;
    }

    public void setTypeUser(Boolean typeUser) {
        this.mTypeUser = typeUser;
    }

}