package com.upit.coex.host.model.request.co;

import com.upit.coex.host.model.request.base.Request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomRequest extends Request implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("maxPerson")
    @Expose
    private Integer maxPerson;

    public RoomRequest() {
    }

    public RoomRequest(String name, String about, Integer price, Integer maxPerson) {
        this.name = name;
        this.about = about;
        this.price = price;
        this.maxPerson = maxPerson;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(Integer maxPerson) {
        this.maxPerson = maxPerson;
    }


}