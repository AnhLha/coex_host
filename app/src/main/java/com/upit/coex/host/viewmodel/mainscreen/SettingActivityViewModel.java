package com.upit.coex.host.viewmodel.mainscreen;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.remote.logout.LogoutAPI;
import com.upit.coex.host.model.request.logout.LogoutRequest;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseFragmentViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SettingActivityViewModel extends BaseFragmentViewModel {
    protected CompositeDisposable mCompositeDispose = new CompositeDisposable();

    private MutableLiveData<String> mCoDataLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mCoDataFailData = new MutableLiveData<>();

    public MutableLiveData<String> getmCoDataFailData() {
        return mCoDataFailData;
    }

    public void setmCoDataFailData(MutableLiveData<String> mCoDataFailData) {
        this.mCoDataFailData = mCoDataFailData;
    }

    public MutableLiveData<String> getCoDataLiveData() {
        return this.mCoDataLiveData;
    }

    public void logout() {
        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
        String token_fire_base = CoexSharedPreference.getInstance().get(CommonConstants.COEX_FIREBASE_TOKEN, String.class);
        //RequestBody coworking = RequestBody.create(MediaType.parse("/form-data"), sCoworing);
        LogoutRequest request = new LogoutRequest(token_fire_base);
        L.d("bao.nt", token,"|" + token_fire_base);
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(LogoutAPI.class)
                .doLogout("Bearer " + token, request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {

                    mCoDataLiveData.setValue("Logout Succes");
////                    mLive.setValue(response.size());
                    Log.d("bao.nt1", "jhgkghkho");
//                    if (response.getData().size() > 0) {
//                        Log.d("bao.nt2", response.getData().get(0).getName() + "");
////                        mCoDataLiveData.setValue((CoData) response.getData().get(0));
//                    }

                }, throwable -> {
                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null) {
                        mCoDataFailData.setValue( error.getmMessage());
                        Log.d("bao.nt", error.getmMessage());
                    } else {
                        mCoDataFailData.setValue(throwable.getMessage());
                    }
                    Log.d("bao.nttttt", throwable.getMessage());
                }));
    }
}
