package com.upit.coex.host.model.remote.splash;

import com.upit.coex.host.model.data.base.BaseReponce;
import com.upit.coex.host.model.data.splash.SplashData;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface SplashCheckTokenApi {
    //@Headers("access_token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlNzQzNzQyYmY4MzgyNWEwYzI0OGE1MSIsImVtYWlsIjoiYWJjMTM1NzlAZ21haWwuY29tIiwidHlwZVVzZXIiOmZhbHNlLCJpYXQiOjE1ODUyNzk2MjYsImV4cCI6MTkwMDYzOTYyNn0.iym_JYmtbHfM68E9WlZq7f7co-nXHrJPn1-R7Ms6CIQ")
    @Headers("Content-Type: application/json")
    @GET("users/me")
    Single<BaseReponce<SplashData>> checkToken(@Header("authorization") String auth);

}
