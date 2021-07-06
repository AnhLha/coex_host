package com.upit.coex.host.viewmodel.splash;

import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.CoexApplication;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.splash.SplashContract;
import com.upit.coex.host.model.data.base.BaseReponce;
import com.upit.coex.host.model.data.splash.SplashData;
import com.upit.coex.host.model.remote.co.CoAPI;
import com.upit.coex.host.model.remote.splash.SplashCheckTokenApi;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.compositedisposal.CoexCommonCompositeDisposal;
import com.upit.coex.host.service.fetchandsave.ApiRepository;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.upit.coex.host.constants.splash.SplashConstant.SPLASH_CHECK_TOKEN_DISPOSAL;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class SplashActivityViewModel extends BaseActivityViewModel implements SplashContract.SplashInterfaceViewModel{

    private static final String TAG = "SplashActivityViewModel";

    //api
    private SplashCheckTokenApi api;

    public SplashActivityViewModel(){
        super();
    }

    @Override
    public boolean requestPermission(String[] permissions) {

        // Với API >= 23,  hỏi người dùng cho phép xem vị trí của họ.
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d("bao.nt", "1");

            boolean isFull = isFullPermissions(permissions);
            Log.d("bao.nt", isFull+"");
            getPermissions().postValue(permissions);
            return isFull;
        }else{
            Log.d("bao.nt", "2");
            return true;
        }
    }

    @Override
    public void destroyView() {

    }

    private boolean isFullPermissions (String[] permissions){
        for (int i = 0; i < permissions.length; i++){
            if (ContextCompat.checkSelfPermission(CoexApplication.self(), permissions[i]) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @Override
    public MutableLiveData<SplashData> getMutableLiveData() {
        return mLive;
    }

    @Override
    public void checkToken() {
        Log.d("bao.nt", "0");
        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
        L.d(TAG, "token: " + token);
        ApiRepository
                .getInstance()
                .setUrl(CommonConstants.BASE_URL+"")
                .createRetrofit()
                .create(SplashCheckTokenApi.class)
                .checkToken(CommonConstants.PREFIX_AUTHOR + token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<BaseReponce<SplashData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        CoexCommonCompositeDisposal.getInstance().addDisposal(SPLASH_CHECK_TOKEN_DISPOSAL,d);
                    }

                    @Override
                    public void onSuccess(BaseReponce<SplashData> value) {
                        L.d(TAG, "Check token res: " + value.getMessage(), value.getCode() + "", value.getData().toString());
                        if(value.getCode() == CommonConstants.RES_CODE_200){
                            mLive.setValue("SUCCESS");
                        } else {
                            mLiveFail.setValue("FAILED");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d(TAG, "Check token err: " + e.toString());
                        mLiveFail.setValue("FAILED");

                    }
                });
    }

    @Override
    public void removeDisposal() {
        CoexCommonCompositeDisposal.getInstance().removeDisposal(SPLASH_CHECK_TOKEN_DISPOSAL);
    }

    public void checkCo() {
        Log.d("bao.nt", "3");
        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
        L.d("bao.nt",token);
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(CoAPI.class)
                .doCoo("Bearer "+token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    Log.d("aaaaaaa", response.getData().toString()+"");
                    if (response.getData().size() > 0){
                        mLiveSuccess.setValue("have");
                    }else {
                        mLiveSuccess.setValue("none");
                    }

                }, throwable -> {
                    Log.d("aaaaaaa", "A");
                }));
    }
}
