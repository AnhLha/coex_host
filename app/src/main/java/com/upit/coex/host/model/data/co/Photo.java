package com.upit.coex.host.model.data.co;

public class Photo {
    int id;
    String url;

    public Photo(int id) {
        this.id = id;
    }

    public Photo(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
