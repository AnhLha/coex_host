package com.upit.coex.host.model.data.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseReponce<T extends BaseData> extends TemplateDataResponce {
    @SerializedName("data")
    @Expose
    private T data = null;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
