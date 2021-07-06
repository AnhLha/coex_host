package com.upit.coex.host.model.request.base;

import com.upit.coex.host.model.data.base.BaseData;
import com.upit.coex.host.model.data.base.TemplateDataResponce;

import java.util.List;

public class BaseRequestList<T extends BaseData> extends TemplateDataResponce {

    private List<T> data = null;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
