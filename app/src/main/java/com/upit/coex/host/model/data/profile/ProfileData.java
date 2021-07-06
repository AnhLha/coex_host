package com.upit.coex.host.model.data.profile;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileData extends BaseData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("typeUser")
    @Expose
    private Boolean typeUser;
    @SerializedName("client")
    @Expose

    private Object client;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(Boolean typeUser) {
        this.typeUser = typeUser;
    }

    public Object getClient() {
        return client;
    }

    public void setClient(Object client) {
        this.client = client;
    }

}