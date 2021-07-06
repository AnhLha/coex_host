package com.upit.coex.host.model.data.room;

import com.upit.coex.host.model.data.base.TemplateDataResponce;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DateResponse extends TemplateDataResponce {
    @SerializedName("data")
    @Expose
    private List<Long> data = null;

    public List<Long> getData() {
        if(data == null){
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }
}
