package com.upit.coex.host.model.data.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BaseResponceList<T extends BaseData> extends TemplateDataResponce {
    @SerializedName("data")
    @Expose
    private List<T> data = null;

    public List<T> getData() {
        if(data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
