package com.upit.coex.host.model.data.login;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class LoginData extends BaseData {



    @SerializedName("token")
    @Expose
    private String token;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
