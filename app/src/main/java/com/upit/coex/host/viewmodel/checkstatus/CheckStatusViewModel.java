package com.upit.coex.host.viewmodel.checkstatus;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.base.BaseInterfaceViewModel;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.data.mainscreen.CheckInOutData;
import com.upit.coex.host.model.data.mainscreen.TransactionData;
import com.upit.coex.host.model.remote.mainscreen.fragment.QRCodeFragmentAPI;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CheckStatusViewModel extends BaseActivityViewModel<TransactionData, String, String> implements BaseInterfaceViewModel {

    private MutableLiveData<String> mCheckOutSucess = new MutableLiveData<>();
    private MutableLiveData<String> mCheckOutFail = new MutableLiveData<>();

    @Override
    public boolean requestPermission(String[] permissions) {
        return false;
    }

    @Override
    public void destroyView() {
        mCompositeDispose.clear();
    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return mLive;
    }

    public void checkIn(String id) {
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                .checkIn(CommonConstants.PREFIX_AUTHOR + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(checkInOutResponse -> {
                    Log.d("bao.nt", checkInOutResponse.getCode() + "-----------------");
                    mLiveSuccess.setValue(checkInOutResponse.getData().getKey());
                }, throwable -> {
                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null) {
                        mLiveFail.setValue(error.getmMessage());
                        Log.d("bao.nt", error.getmMessage());
                    } else {
                        mLiveFail.setValue(throwable.getMessage());
                    }
//                    Log.d("bao.nt", "-------------" + throwable.getMessage() + "|" );
                }));
    }


    // check out
    public void checkOut(String id) {
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                .checkOut(CommonConstants.PREFIX_AUTHOR + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class), id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(checkInOutResponse -> {
                    Log.d("bao.nt", checkInOutResponse.getCode() + "-----------------");
                    mCheckOutSucess.setValue(checkInOutResponse.getData().getKey());
                }, throwable -> {

                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null) {
                        mCheckOutFail.setValue(error.getmMessage());
                        Log.d("bao.nt", error.getmMessage());
                    } else {
                        mCheckOutFail.setValue(throwable.getMessage());
                    }
                }));
    }

    public void updateBooking(){

    }

    public MutableLiveData<String> getmCheckOutSucess() {
        return mCheckOutSucess;
    }

    public void setmCheckOutSucess(MutableLiveData<String> mCheckOutSucess) {
        this.mCheckOutSucess = mCheckOutSucess;
    }

    public MutableLiveData<String> getmCheckOutFail() {
        return mCheckOutFail;
    }

    public void setmCheckOutFail(MutableLiveData<String> mCheckOutFail) {
        this.mCheckOutFail = mCheckOutFail;
    }

    public void requestCheckInOut(String r, boolean isCheckin) {
        if (isCheckin){
            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                    .checkIn("Bearer "+CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                            r)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(response -> {
                        this.mLiveSuccess.setValue(response.getData().getKey());
                    }, throwable -> {
                        BaseDataError error = new BaseDataError(throwable);
                        if (error.getmMessage() != null) {
                            this.mLiveFail.setValue(error.getmMessage());
                            Log.d("bao.nt", error.getmMessage());
                        } else {
                            this.mLiveFail.setValue(throwable.getMessage());
                        }
                    }));
        }else{
            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                    .checkOut("Bearer "+CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                            r)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(response -> {
                        this.mLiveSuccess.setValue(response.getData().getKey());
                    }, throwable -> {
                        BaseDataError error = new BaseDataError(throwable);
                        if (error.getmMessage() != null) {
                            this.mLiveFail.setValue(error.getmMessage());
                            Log.d("bao.nt", error.getmMessage());
                        } else {
                            this.mLiveFail.setValue(throwable.getMessage());
                        }
                    }));
        }

    }

    public void requestTransactionData(String r) {
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                .getTransactionInfo("Bearer "+CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                        r)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    this.mLive.setValue(response.getData());
                }, throwable -> {
                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null) {
                        this.mLiveFail.setValue(error.getmMessage());
                        Log.d("bao.nt", error.getmMessage());
                    } else {
                        this.mLiveFail.setValue(throwable.getMessage());
                    }
                }));

    }

}
