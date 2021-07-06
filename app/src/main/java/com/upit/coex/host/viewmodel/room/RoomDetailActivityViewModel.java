package com.upit.coex.host.viewmodel.room;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.room.RoomDetailContract;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.data.room.booking.data.BookingRoomData;
import com.upit.coex.host.model.remote.room.RoomAPI;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RoomDetailActivityViewModel extends BaseActivityViewModel<List<BookingRoomData>, List<Long>, String> implements RoomDetailContract.RoomDetailInterfaceViewModel {
    private static final String TAG = "RoomDetailActivityViewModel";
    MutableLiveData<RoomRequest> mLiveEditRoomSuccess = new MutableLiveData<>();
    MutableLiveData<String> mLiveEditRoomFail = new MutableLiveData<>();

    public MutableLiveData<RoomRequest> getmLiveEditRoomSuccess() {
        return mLiveEditRoomSuccess;
    }

    public void setmLiveEditRoomSuccess(MutableLiveData<RoomRequest> mLiveEditRoomSuccess) {
        this.mLiveEditRoomSuccess = mLiveEditRoomSuccess;
    }

    public MutableLiveData<String> getmLiveEditRoomFail() {
        return mLiveEditRoomFail;
    }

    public void setmLiveEditRoomFail(MutableLiveData<String> mLiveEditRoomFail) {
        this.mLiveEditRoomFail = mLiveEditRoomFail;
    }

    @Override
    public boolean requestPermission(String[] permissions) {
        L.d(TAG, "reqestpermission");
        if (!isPermission(permissions)) {
            L.d(TAG, "reqestpermission false");

            mPermissions.postValue(permissions);
            return false;
        }
        L.d(TAG, "reqestpermission true");

        return true;
    }

    @Override
    public void destroyView() {

    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return null;
    }

    @Override
    public void getDates(String id) {
        Log.d(TAG, CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class) + "| " + id);
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(RoomAPI.class)
                .getDateHaveBooking("Bearer " + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                        id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(dateResponse -> {
                    Log.d(TAG, "phong  duoc book " + dateResponse.getData().size());
                    if (dateResponse.getData().size() > 0) {
                        mLiveSuccess.setValue(dateResponse.getData());
                        Log.d(TAG, "phong  duoc book " + dateResponse.getData().get(0));
                        //
                    }else {
                        mLiveFail.setValue("No booking");
                    }
                }, throwable -> {
                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null){
                        mLiveFail.setValue(error.getmMessage());
                        Log.d("cin", error.getmMessage());
                    }else {
                        mLiveFail.setValue(throwable.getMessage());
                    }
                    Log.d(TAG, throwable.getMessage());
                }));

    }

    @Override
    public void getUsers(String id, Long date) {
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(RoomAPI.class)
                .getUser("Bearer " + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                        id, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(listRoomData -> {
                    if (listRoomData.getData().size() > 0) {
                        mLive.setValue(listRoomData.getData());
                        Log.d(TAG, "vao duoc day nay");
                    }
                }, throwable -> {
                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null){
                        mLiveFail.setValue(error.getmMessage());
                        Log.d("cin", error.getmMessage());
                    }else {
                        mLiveFail.setValue(throwable.getMessage());
                    }
                    Log.d(TAG, "fali vao duoc day nay" + throwable.getMessage());
                }));

    }

    @Override
    public void editRoom(String id, String name, String about, String price, String maxPer) {
        if (!"".equals(name) && !"".equals(about) && !"".equals(price) && !"".equals(maxPer)) {
            RoomRequest room = new RoomRequest(name, about, Integer.valueOf(price), Integer.valueOf(maxPer));
            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(RoomAPI.class)
                    .editRoom(CommonConstants.PREFIX_AUTHOR + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                            id, room)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(baseData -> {
                        mLiveEditRoomSuccess.setValue(room);
                    }, throwable -> {
                        Log.d("cin", "vao day nay" + throwable.getMessage());
                        mLiveEditRoomFail.setValue("Update fail");
                    }));
        }else if ("".equals(name)){
            mLiveEditRoomFail.setValue("Please enter name!");
        }else if ("".equals(about)){
            mLiveEditRoomFail.setValue("Please enter about room!");
        }else if ("".equals(price)){
            mLiveEditRoomFail.setValue("Please enter price room!");
        }else if ("".equals(maxPer)){
            mLiveEditRoomFail.setValue("Please enter max person of room!");
        }
    }

}
