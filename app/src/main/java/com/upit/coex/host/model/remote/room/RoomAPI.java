package com.upit.coex.host.model.remote.room;

import com.upit.coex.host.model.data.base.BaseData;
import com.upit.coex.host.model.data.base.BaseReponce;
import com.upit.coex.host.model.data.co.ListRoomData;
import com.upit.coex.host.model.data.room.DateResponse;
import com.upit.coex.host.model.data.room.addroom.AddRoomData;
import com.upit.coex.host.model.data.room.booking.response.BookingResponse;
import com.upit.coex.host.model.request.co.RoomRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoomAPI {
    @Headers("Content-Type: application/json")
    @GET("coworkings")
    Observable<ListRoomData> addRoom(@Body RoomRequest room);

    //
    @Headers("Content-Type: application/json")
    @GET("rooms/{id}/bookings/history")
    Observable<DateResponse> getDateHaveBooking(@Header("authorization") String auth, @Path("id") String mIdRoom);

    //
    @Headers("Content-Type: application/json")
    @GET("rooms/{id}/bookings/{date}")
    Observable<BookingResponse> getUser(@Header("authorization") String auth, @Path("id") String mIdRoom, @Path("date") Long mBookDate );

    //
    @Headers("Content-Type: application/json")
    @POST("coworking/{id}/rooms/add")
    Observable<BaseReponce<AddRoomData>> addNewRoom(@Header("authorization") String auth, @Path("id") String id, @Body RoomRequest roomRequest );

    @Headers("Content-Type: application/json")
    @PUT("rooms/{id}")
    Observable<BaseReponce<BaseData>> editRoom(@Header("authorization") String auth, @Path("id") String id, @Body RoomRequest roomRequest);

}
