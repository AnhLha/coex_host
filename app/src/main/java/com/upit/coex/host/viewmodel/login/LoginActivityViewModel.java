package com.upit.coex.host.viewmodel.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.login.LoginContract;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.data.login.LoginData;
import com.upit.coex.host.model.remote.co.CoAPI;
import com.upit.coex.host.model.remote.login.LoginLoginApi;
import com.upit.coex.host.model.request.login.LoginRequest;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.compositedisposal.CoexCommonCompositeDisposal;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.upit.coex.host.constants.login.LoginConstant.listDiposal;


public class
LoginActivityViewModel extends BaseActivityViewModel<String, LoginData, String> implements LoginContract.LoginInterfaceViewModel {

    private static final String TAG = "LoginActivityViewModel";

    @Override
    public boolean requestPermission(String[] permissions) {
        return false;
    }

    @Override
    public void destroyView() {
        for (String iem :
                listDiposal) {
            CoexCommonCompositeDisposal.getInstance().removeDisposal(iem);
        }

    }


    @Override
    public MutableLiveData getMutableLiveData() {
        return mLive;
    }

    @Override
    public void doLogin(String email, String passWord) {
        if(!"".equals(email) && !"".equals(passWord)) {

            Log.d("cin", "token : " + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class));

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    L.i(TAG, "getInstanceId failed", task.getException().toString());
                    return;
                }
                if (!Pattern.compile(CommonConstants.EMAIL_PATTERN).matcher(email).matches()) {
                    mLiveFail.setValue("Invalid email");
                } else {
                    String firebaseToken = task.getResult().getToken();
                    L.d(TAG, "Firebase token: " + firebaseToken);
                    mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(LoginLoginApi.class)
                            .doLogin(new LoginRequest(email, passWord, firebaseToken))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(response -> {
                                L.d(TAG, "On login success: ", response.getMessage(), response.getCode() + "", response.getData().getToken());
                                CoexSharedPreference.getInstance().put(CommonConstants.TOKEN, response.getData().getToken());
                                CoexSharedPreference.getInstance().put(CommonConstants.COEX_FIREBASE_TOKEN, firebaseToken);
                                mLiveSuccess.setValue(response.getData());
                            }, throwable -> {
                                BaseDataError error = new BaseDataError(throwable);
                                if (error.getmMessage() != null) {
                                    mLiveFail.setValue(error.getmMessage());
                                    Log.d("cin", error.getmMessage());
                                } else {
                                    mLiveFail.setValue(throwable.getMessage());
                                }

                            }));
                }
            });
        }else if("".equals(email)){
            mLiveFail.setValue("Please enter email!");
        }else{
            mLiveFail.setValue("Please enter password!");
        }
    }

    @Override
    public void checkCo() {
        Log.d("cin", "3");
        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
        L.d("cin", token);
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(CoAPI.class)
                .doCoo("Bearer " + token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    Log.d("aaaaaaa", response.getData().toString() + "");
                    if (response.getData().size() > 0) {
                        mLive.setValue("have");
                    } else {
                        mLive.setValue("none");
                    }
                }, throwable -> {
                    BaseDataError error = new BaseDataError(throwable);
                    if (error.getmMessage() != null){
                        mLiveFail.setValue(error.getmMessage());
                        Log.d("cin", error.getmMessage());
                    }else {
                        mLiveFail.setValue(throwable.getMessage());
                    }
                }));
    }


}
