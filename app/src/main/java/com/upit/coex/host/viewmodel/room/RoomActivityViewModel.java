package com.upit.coex.host.viewmodel.room;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.listroom.RoomContract;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.remote.room.RoomAPI;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RoomActivityViewModel extends BaseActivityViewModel<CoData, String, String> implements RoomContract.RoomInterfaceViewModel {

    @Override
    public void addRoom(String name, String about, String price, String maxPer) {
        if (!"".equals(name) && !"".equals(about) && !"".equals(price) && !"".equals(maxPer)) {
            RoomRequest room = new RoomRequest(name, about, Integer.valueOf(price), Integer.valueOf(maxPer));
            String id = CoexSharedPreference.getInstance().get(CommonConstants.COEX_ID, String.class);
            String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
            Log.d("bao.nt2222", id + "\n" + token);

//            mLiveSuccess.setValue("Add room success");
            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(RoomAPI.class)
                    .addNewRoom("Bearer " + token,
                            id,
                            room)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(bookingResponse -> {
                        // xu li
                        Log.d("bao.ntRoom", "them roi nhe");
                        mLiveSuccess.setValue("Add room success!");
                    }, throwable -> {
                        BaseDataError error = new BaseDataError(throwable);
                        if (error.getmMessage() != null){
                            mLiveFail.setValue(error.getmMessage());
                            Log.d("bao.nt", error.getmMessage());
                        }else {
                            mLiveFail.setValue(throwable.getMessage());
                        }
                    }));

        }else if ("".equals(name)){
            mLiveFail.setValue("Please enter name!");
        }else if ("".equals(about)){
            mLiveFail.setValue("Please enter about room!");
        }else if ("".equals(price)){
            mLiveFail.setValue("Please enter price room!");
        }else if ("".equals(maxPer)){
            mLiveFail.setValue("Please enter max person of room!");
        }
    }


//    @Override
//    public void listRoom() {
//        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
//        L.d("bao.nt", token);
//        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(CoAPI.class)
//                .doCoo("Bearer " + token)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(response -> {
////                    mLive.setValue(response.size());
//                    Log.d("bao.nt1", response.getData().size() + "");
//                    if (response.getData().size() > 0) {
//                        Log.d("bao.nt2", response.getData().get(0).getName() + "");
//                        mLive.setValue((CoData) response.getData().get(0));
//                        Log.d("bao.nt3", mLive.getValue().getName());
//                    }
//
//                }, throwable -> {
//                    Log.d("bao.nttttt", throwable.getMessage());
//                }));
//    }

    @Override
    public boolean requestPermission(String[] permissions) {
        return false;
    }

    @Override
    public void destroyView() {

    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return null;
    }


}
