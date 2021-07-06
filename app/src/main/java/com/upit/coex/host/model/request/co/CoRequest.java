package com.upit.coex.host.model.request.co;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CoRequest implements Serializable {
    @SerializedName("photo")
    @Expose
    private List<String> mPhoto;
    @SerializedName("coworking")
    @Expose
    private CoWorking mCo = new CoWorking();

    public CoRequest() {
    }

    public CoRequest(String name, String about, String phone, List<String> photo, String address, List<Double> location) {
        this.mCo.setName(name);
        this.mCo.setAbout(about);
        this.mPhoto = photo;
        this.mCo.setAddress(address);
        this.mCo.setLocation(location);
        this.mCo.setPhone(phone);
    }



    public List<String> getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(List<String> mPhoto) {
        this.mPhoto = mPhoto;
    }

    public CoWorking getmCo() {
        return mCo;
    }

    public void setmCo(CoWorking mCo) {
        this.mCo = mCo;
    }
}
