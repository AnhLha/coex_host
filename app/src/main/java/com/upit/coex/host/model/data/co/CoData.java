package com.upit.coex.host.model.data.co;

import com.upit.coex.host.model.data.base.BaseData;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoData extends BaseData implements Serializable {

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
    @SerializedName("photo")
    @Expose
    private List<String> photo = null;
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
    private ServiceData serviceData;

    @SerializedName("rooms")
    @Expose
    private List<RoomData> mListRoom;

    public CoData() {

    }

    public CoData(String name, String about, List<String> photo, String address, List<Double> location) {
        this.name = name;
        this.about = about;
        this.photo = photo;
        this.address = address;
        this.location = location;
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

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
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

    public ServiceData getServiceData() {
        if(serviceData == null){
            serviceData = new ServiceData();
        }
        return serviceData;
    }

    public void setServiceData(ServiceData serviceData) {
        this.serviceData = serviceData;
    }

    public List<RoomData> getmListRoom() {
        return mListRoom;
    }

    public void setmListRoom(List<RoomData> mListRoom) {
        this.mListRoom = mListRoom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return getName()+""+getPhoto();
    }

}