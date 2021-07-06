package com.upit.coex.host.viewmodel.mainscreen;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.data.mainscreen.TransactionData;
import com.upit.coex.host.model.remote.mainscreen.fragment.QRCodeFragmentAPI;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseFragmentViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class QRCodeFragmentViewModel extends BaseFragmentViewModel {
    private MutableLiveData<TransactionData> mTransactionLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mError = new MutableLiveData<>();
    private MutableLiveData<Pair> mKeyCheckInOutLiveData = new MutableLiveData<>();

    public MutableLiveData<Pair> getKeyCheckInOutLiveData(){
        return mKeyCheckInOutLiveData;
    }

    protected CompositeDisposable mCompositeDispose = new CompositeDisposable();

    public MutableLiveData<TransactionData> getTransactionLiveData(){
        return this.mTransactionLiveData;
    }

    public MutableLiveData<String> getErrorLiveData(){
        return this.mError;
    }

    public void requestTransactionData(String r) {
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                .getTransactionInfo("Bearer "+CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                        r)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    Log.d("bao.nt","-----------------------");
                    this.mTransactionLiveData.setValue(response.getData());
                }, throwable -> {
                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null) {
                        this.mError.setValue(error.getmMessage());
                        Log.d("bao.nt", error.getmMessage());
                    } else {
                        this.mError.setValue(throwable.getMessage());
                    }
                }));

    }

    public void destroyView() {
        mCompositeDispose.clear();

    }

    public void requestCheckInOut(String r, boolean isCheckin) {
        if (isCheckin){
            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                    .checkIn("Bearer "+CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                            r)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(response -> {
                        this.mKeyCheckInOutLiveData.setValue(new Pair<String, String>(CommonConstants.CHECK_IN,response.getData().getKey()));
                    }, throwable -> {
                        BaseDataError error = new BaseDataError(throwable);
                        if (error.getmMessage() != null) {
                            this.mError.setValue(error.getmMessage());
                            Log.d("bao.nt", error.getmMessage());
                        } else {
                            this.mError.setValue(throwable.getMessage());
                        }
                    }));
        }else{
            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(QRCodeFragmentAPI.class)
                    .checkOut("Bearer "+CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                            r)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(response -> {
                        this.mKeyCheckInOutLiveData.setValue(new Pair<String, String>(CommonConstants.CHECK_OUT,response.getData().getKey()));
                    }, throwable -> {
                        BaseDataError error = new BaseDataError(throwable);
                        if (error.getmMessage() != null) {
                            this.mError.setValue(error.getmMessage());
                            Log.d("bao.nt", error.getmMessage());
                        } else {
                            this.mError.setValue(throwable.getMessage());
                        }
                    }));
        }

    }

    public void clearData(){
        this.mKeyCheckInOutLiveData = new MutableLiveData<>();
        mError = new MutableLiveData<>();
        mTransactionLiveData = new MutableLiveData<>();
        mCompositeDispose.clear();
    }
}
