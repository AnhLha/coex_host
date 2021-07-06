package com.upit.coex.host.model.remote.register;

import com.upit.coex.host.model.data.register.RegisterResponse;
import com.upit.coex.host.model.request.register.RegisterRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterAPI {
    @Headers("Content-Type: application/json")
////    @FormUrlEncoded
    @POST("users/register")
    Observable<RegisterResponse> doRegister(@Body RegisterRequest login);
}
