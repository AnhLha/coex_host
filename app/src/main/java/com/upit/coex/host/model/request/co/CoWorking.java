package com.upit.coex.host.model.request.co;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CoWorking implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("about")
    @Expose
    private String about;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private List<Double> location = null;

    @SerializedName("service")
    @Expose
    private ServiceRequest serviceData = new ServiceRequest();

    @SerializedName("rooms")
    @Expose
    private List<RoomRequest> mListRoom = new ArrayList<>();

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public ServiceRequest getServiceData() {
        return serviceData;
    }

    public void setServiceData(ServiceRequest serviceData) {
        this.serviceData = serviceData;
    }

    public List<RoomRequest> getmListRoom() {
        return mListRoom;
    }

    public void setmListRoom(List<RoomRequest> mListRoom) {
        this.mListRoom = mListRoom;
    }
}
