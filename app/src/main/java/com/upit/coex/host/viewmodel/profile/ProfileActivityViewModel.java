package com.upit.coex.host.viewmodel.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.profile.ProfileContract;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.model.data.profile.EditProfileData;
import com.upit.coex.host.model.data.profile.ProfileData;
import com.upit.coex.host.model.remote.co.CoAPI;
import com.upit.coex.host.model.remote.profile.ProfileAPI;
import com.upit.coex.host.model.request.profile.ChangePasswordRequest;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.compositedisposal.CoexCommonCompositeDisposal;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseActivityViewModel;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static com.upit.coex.host.constants.login.LoginConstant.listDiposal;

public class ProfileActivityViewModel extends BaseActivityViewModel<ProfileData, String, String> implements ProfileContract.ProfileInterfaceViewModel {

    MutableLiveData<EditProfileData> mLiveEditProfileSuccess = new MutableLiveData<>();
    MutableLiveData<String> mLiveEditProfileFail = new MutableLiveData<>();

    MutableLiveData<CoData> mLiveCoSuccess = new MutableLiveData<>();
    MutableLiveData<String> mLiveCoFail = new MutableLiveData<>();

    public MutableLiveData<CoData> getmLiveCoSuccess() {
        return mLiveCoSuccess;
    }

    public void setmLiveCoSuccess(MutableLiveData<CoData> mLiveCoSuccess) {
        this.mLiveCoSuccess = mLiveCoSuccess;
    }

    public MutableLiveData<String> getmLiveCoFail() {
        return mLiveCoFail;
    }

    public void setmLiveCoFail(MutableLiveData<String> mLiveCoFail) {
        this.mLiveCoFail = mLiveCoFail;
    }

    public static final String TAG = "ProfileActivityViewModel";

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
    public void changPassword(String oldP, String newP, String conP) {
        if (!"".equals(oldP) && !"".equals(newP) && !"".equals(conP)) {
            if (!newP.equals(conP)) {
                mLiveFail.setValue("Mật khẩu không giống nhau.");
            } else if (newP.equals(oldP)) {
                mLiveFail.setValue("Mật khẩu mới không được trùng với mật khẩu cũ.");
            } else {
                Log.d(TAG, "ok");
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        L.i(TAG, "getInstanceId failed", task.getException().toString());
                        return;
                    }
                    String firebaseToken = task.getResult().getToken();
                    L.d(TAG, "Firebase token: " + firebaseToken);
                    L.d(TAG, firebaseToken, CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class), oldP, newP);
                    mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(ProfileAPI.class)
                            .doChangePassword(CommonConstants.PREFIX_AUTHOR + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                                    new ChangePasswordRequest(oldP, newP, firebaseToken))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(changePasswordDataBaseReponce -> {
                                if (changePasswordDataBaseReponce.getCode() == 200) {
                                    mLiveSuccess.setValue("Đổi mật khẩu thành công!");
                                }

                            }, throwable -> {
                                Log.d("cin", "sai roi nay" + throwable.getMessage());
                                mLiveFail.setValue("Mật khẩu của bạn không đúng!");
                            }));


                });
            }
        } else {
            mLiveFail.setValue("Vui lòng điền đầy đủ thông tin.!");
        }

    }

    @Override
    public void me() {
        Log.d("cin", "ở dây vẫn vào được nè :" + CommonConstants.PREFIX_AUTHOR + "|" + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class));
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(ProfileAPI.class)
                .doMe(CommonConstants.PREFIX_AUTHOR + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(profileDataBaseReponce -> {
                    // data
                    mLive.setValue(profileDataBaseReponce.getData());
                    Log.d("cin", "Vao day roi nhe ban oi ");
                }, throwable -> {
                    Log.d("cin", "sai ơ day nay" + throwable.getMessage());
                }));

    }

    @Override
    public void editProfile(String idco, String name, String phone) {
        if (!"".equals(name) && !"".equals(phone)) {
            EditProfileData data = new EditProfileData(name, phone);
            mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(ProfileAPI.class)
                    .doEditProfile(CommonConstants.PREFIX_AUTHOR + CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class),
                            idco, data)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(baseDataBaseReponce -> {
                        mLiveEditProfileSuccess.setValue(data);
                    }, throwable -> {
                        Log.d("cin", throwable.getMessage());
                        mLiveEditProfileFail.setValue("Không thành công");
                    }));


        } else {
            mLiveEditProfileFail.setValue("Vui lòng điền đầy đủ thông tin.!");
        }
    }

    public MutableLiveData<EditProfileData> getmLiveEditProfileSuccess() {
        return mLiveEditProfileSuccess;
    }

    public void setmLiveEditProfileSuccess(MutableLiveData<EditProfileData> mLiveEditProfileSuccess) {
        this.mLiveEditProfileSuccess = mLiveEditProfileSuccess;
    }

    public MutableLiveData<String> getmLiveEditProfileFail() {
        return mLiveEditProfileFail;
    }

    public void setmLiveEditProfileFail(MutableLiveData<String> mLiveEditProfileFail) {
        this.mLiveEditProfileFail = mLiveEditProfileFail;
    }

    public void checkCo() {
        Log.d("cin", "3");
        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
        L.d("cin",token);
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(CoAPI.class)
                .doCoo("Bearer "+token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    Log.d("aaaaaaa", response.getData().toString()+"");
                    if (response.getData().size() > 0){
                        Log.d("cin", "aaaaaaaaaaaaaaaa");
                        mLiveCoSuccess.setValue(response.getData().get(0));
                    }
                }, throwable -> {
                    Log.d("aaaaaaa", "A");
                }));
    }


}