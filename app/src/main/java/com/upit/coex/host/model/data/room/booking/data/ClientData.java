package com.upit.coex.host.model.data.room.booking.data;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientData extends BaseData implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("point")
    @Expose
    private Integer point;
    @SerializedName("coin")
    @Expose
    private Integer coin;
    @SerializedName("listCard")
    @Expose
    private List<Object> listCard = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public List<Object> getListCard() {
        return listCard;
    }

    public void setListCard(List<Object> listCard) {
        this.listCard = listCard;
    }
}
