package com.upit.coex.host.model.remote.login;

import com.upit.coex.host.model.data.login.LoginResponse;
import com.upit.coex.host.model.request.login.LoginRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginLoginApi {
    @Headers("Content-Type: application/json")
////    @FormUrlEncoded
    @POST("users/login")
    Observable<LoginResponse> doLogin(@Body LoginRequest login);




//    @POST("coworkings")
//    @Headers({"Content-Type: application/json"})
//    Observable<Response<LoginData>> doLogin(@Body Login request);


//    @POST("login")
////    @FormUrlEncoded
//    @Headers({"Content-Type: application/json"})
////    Observable<Response<Data>> doLogin(@Field("email") String email, @Field("password") String password);
//    Observable<Response<LoginData>> doLogin(@Body Login request);

}
