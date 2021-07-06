package com.upit.coex.host.model.remote.co;

import com.upit.coex.host.model.data.base.BaseReponce;
import com.upit.coex.host.model.data.base.BaseResponceList;
import com.upit.coex.host.model.data.co.CoData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CoAPI {

    @Headers("Content-Type: application/json")
    @GET("coworkings")
    Observable<BaseResponceList<CoData>> doCoo(@Header("authorization") String auth);


    @Multipart
    @POST("coworkings/create")
    Observable<BaseReponce<CoData>> create(@Header("authorization") String auth,
                                                @Part("coworking") RequestBody coworking,
                                                @Part("photo\";filename=\"pp.jpg\"") RequestBody ...photos);

    //@Headers({"Content-Type: multipart/form-data","Content-Type: text/plain"})
//    @Headers("Content-Type: multipart/form-data;")// boundary=--eriksboundry--
//    @POST("coworkings/create")
//    Observable<BaseReponce<CoData>> create(@Header("authorization") String auth,
//                                  //@Part("coworking") RequestBody coworking,
//                                  @Body MultipartBody body);


}
