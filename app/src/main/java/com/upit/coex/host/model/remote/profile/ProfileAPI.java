package com.upit.coex.host.model.remote.profile;

import com.upit.coex.host.model.data.base.BaseData;
import com.upit.coex.host.model.data.base.BaseReponce;
import com.upit.coex.host.model.data.profile.ChangePasswordData;
import com.upit.coex.host.model.data.profile.EditProfileData;
import com.upit.coex.host.model.data.profile.ProfileData;
import com.upit.coex.host.model.request.profile.ChangePasswordRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProfileAPI {

    // it me
    @Headers("Content-Type: application/json")
    @GET("users/me")
    Observable<BaseReponce<ProfileData>> doMe(@Header("authorization") String auth);

    //
    @Headers("Content-Type: application/json")
    @POST("/users/changepassword")
    Observable<BaseReponce<ChangePasswordData>> doChangePassword(@Header("authorization") String auth, @Body ChangePasswordRequest body);

    @Headers("Content-Type: application/json")
    @PATCH("coworkings/{id}")
    Observable<BaseReponce<BaseData>> doEditProfile(@Header("authorization") String auth, @Path("id") String id, @Body EditProfileData body);

}
