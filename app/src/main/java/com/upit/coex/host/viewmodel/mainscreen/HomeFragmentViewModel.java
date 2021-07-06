package com.upit.coex.host.viewmodel.mainscreen;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.model.remote.co.CoAPI;
import com.upit.coex.host.service.CoexCheckNull.CoexOptional;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.viewmodel.base.BaseFragmentViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HomeFragmentViewModel extends BaseFragmentViewModel {
    protected CompositeDisposable mCompositeDispose = new CompositeDisposable();

    private MutableLiveData<CoData> mCoDataLiveData = new MutableLiveData<>();


    public  MutableLiveData<CoData> getCoDataLiveData(){
        return this.mCoDataLiveData;
    }

    public void listRoom() {
        String token = CoexSharedPreference.getInstance().get(CommonConstants.TOKEN, String.class);
        L.d("bao.nt", token);
        mCompositeDispose.add(((Retrofit) CoexOptional.getInstance().setObject(super.getRetrofit(CommonConstants.BASE_URL + "")).getValue()).create(CoAPI.class)
                .doCoo("Bearer " + token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
//                    mLive.setValue(response.size());
                    Log.d("bao.nt1", response.getData().size() + "");
                    if (response.getData().size() > 0) {

                        Log.d("bao.nt2", response.getData().get(0).getName() + "");
                        mCoDataLiveData.setValue((CoData) response.getData().get(0));
                        CoexSharedPreference.getInstance().put(CommonConstants.CO, response.getData().get(0));
                        CoexSharedPreference.getInstance().put(CommonConstants.COEX_ID, response.getData().get(0).getId());
                        CoexSharedPreference.getInstance().put(CommonConstants.SERVICE_CO, response.getData().get(0).getServiceData());
                    }

                }, throwable -> {
                    Log.d("bao.nttttt", throwable.getMessage());
                }));
    }
}
