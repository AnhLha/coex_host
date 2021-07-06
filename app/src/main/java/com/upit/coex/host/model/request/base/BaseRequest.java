package com.upit.coex.host.model.request.base;

public class BaseRequest<T extends Request >{
    private T data = null;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
