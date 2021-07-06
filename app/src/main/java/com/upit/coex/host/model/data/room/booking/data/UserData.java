package com.upit.coex.host.model.data.room.booking.data;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserData extends BaseData implements Serializable {
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
    private ClientData client = new ClientData();

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

    public ClientData getClient() {
        return client;
    }

    public void setClient(ClientData client) {
        this.client = client;
    }
}