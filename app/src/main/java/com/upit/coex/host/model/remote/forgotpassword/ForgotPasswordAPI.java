package com.upit.coex.host.model.remote.forgotpassword;

import com.upit.coex.host.model.data.base.BaseReponce;
import com.upit.coex.host.model.data.login.LoginData;

import io.reactivex.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ForgotPasswordAPI {
    @POST("signin")
    Single<BaseReponce<LoginData>> doLogin(@Query("phoneNumber") String phoneNumber, @Query("password") String password, @Query("firebaseToken") String firebaseToken);


}
