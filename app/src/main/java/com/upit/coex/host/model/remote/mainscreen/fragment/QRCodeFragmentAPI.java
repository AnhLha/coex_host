package com.upit.coex.host.model.remote.mainscreen.fragment;

import com.upit.coex.host.model.data.mainscreen.CheckInOutResponse;
import com.upit.coex.host.model.data.mainscreen.TransactionResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface QRCodeFragmentAPI {
    @Headers("Content-Type: application/json")
    @GET("transactions/{transaction_id}")
    Observable<TransactionResponse> getTransactionInfo(@Header("authorization") String auth, @Path("transaction_id") String transaction_id);

    @Headers("Content-Type: application/json")
    @GET("check-in/{transaction_id}")
    Observable<CheckInOutResponse> checkIn(@Header("authorization") String auth, @Path("transaction_id") String transaction_id);

    @Headers("Content-Type: application/json")
    @GET("check-out/{transaction_id}")
    Observable<CheckInOutResponse> checkOut(@Header("authorization") String auth, @Path("transaction_id") String transaction_id);
}
