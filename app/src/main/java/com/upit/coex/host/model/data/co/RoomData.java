package com.upit.coex.host.model.data.co;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomData extends BaseData implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("coworkingId")
    @Expose
    private String coworkingId;

    public RoomData() {
    }

    public RoomData(String name, String about, Integer price, Integer maxPerson) {
        //this.id = id;
        this.name = name;
        this.about = about;
        this.price = price;
        this.maxPerson = maxPerson;
//        this.coworkingId = coworkingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCoworkingId() {
        return coworkingId;
    }

    public void setCoworkingId(String coworkingId) {
        this.coworkingId = coworkingId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}