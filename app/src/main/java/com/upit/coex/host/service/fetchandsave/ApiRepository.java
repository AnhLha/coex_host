package com.upit.coex.host.service.fetchandsave;


import com.upit.coex.host.service.fetchandsave.remote.RetrofitService;

import retrofit2.Retrofit;

public class ApiRepository {

    private static volatile ApiRepository sInstance = null;

    private String url;

    private ApiRepository(){

    }

    public static ApiRepository getInstance(){
        if(sInstance == null){
            synchronized (ApiRepository.class){
                sInstance = new ApiRepository();
            }
        }
        return sInstance;
    }

    public ApiRepository setUrl(String url){
        this.url = url;
        return this;
    }

    public Retrofit createRetrofit(){
        return RetrofitService.createService(this.url);
    }



}
