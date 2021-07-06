package com.upit.coex.host.viewmodel.register;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.register.RegisterContract;
import com.upit.coex.host.model.data.error.BaseDataError;
import com.upit.coex.host.model.data.register.RegisterData;
import com.upit.coex.host.model.remote.register.RegisterAPI;
import com.upit.coex.host.model.request.register.RegisterRequest;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.compositedisposal.CoexCommonCompositeDisposal;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;

import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.upit.coex.host.constants.login.LoginConstant.listDiposal;

public class RegisterActivityViewModel extends BaseActivityViewModel<RegisterData, String, String> implements RegisterContract.RegisterInterfaceViewModel {

    @Override
    public void doRegister(String email, String password, String passcon, boolean typeUser) {
        if (passcon.equals(password) && !"".equals(email) && !"".equals(passcon)) {
            if (!Pattern.compile(CommonConstants.EMAIL_PATTERN).matcher(email).matches()) {
                mLiveFail.setValue("Email no ");
            } else {
                mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(RegisterAPI.class)
                        .doRegister(new RegisterRequest(email, password, true))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(response -> {

                            mLiveSuccess.setValue("Register success");
                            //mLive.setValue(response.getData());
//                    Log.d("cin:", "    response.code(): " + response.getCode());
//                    Log.d("cin:", "    response.message " + response.getMessage());
//                    Log.d("cin:", "    response.errorBody(): " + response.getStatus() );
                        }, throwable -> {
                            BaseDataError error = new BaseDataError(throwable);
                            if (error.getmMessage() != null) {
                                mLiveFail.setValue(error.getmMessage());
                                Log.d("cin", error.getmMessage());
                            } else {
                                mLiveFail.setValue(throwable.getMessage());
                            }

                            //truong hop loi mang, server die, timeout, url khong ton tai
                        }));
            }
        } else if ("".equals(email)) {
            mLiveFail.setValue("Please enter email!");
        } else if ("".equals(password)) {
            mLiveFail.setValue("Please enter password!");
        } else if ("".equals(passcon)) {
            mLiveFail.setValue("Please enter password confirm!");
        }else {
            mLiveFail.setValue("Password and confirm password do not match");
        }
    }

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
}
