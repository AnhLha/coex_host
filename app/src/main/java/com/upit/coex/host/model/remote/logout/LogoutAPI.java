package com.upit.coex.host.model.remote.logout;

import com.upit.coex.host.model.data.base.BaseReponce;
import com.upit.coex.host.model.data.login.LogoutData;
import com.upit.coex.host.model.request.logout.LogoutRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LogoutAPI {

    @Headers("Content-Type: application/json")
    @POST("users/logout")
    Observable<BaseReponce<LogoutData>> doLogout(@Header("authorization") String auth, @Body LogoutRequest request);


}
