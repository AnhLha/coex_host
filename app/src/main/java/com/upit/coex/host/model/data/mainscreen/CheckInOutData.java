package com.upit.coex.host.model.data.mainscreen;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckInOutData extends BaseData implements Serializable {

    @SerializedName("key")
    @Expose
    private String mKey;

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }
}
