package com.upit.coex.host.viewmodel.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.upit.coex.host.CoexApplication;
import com.upit.coex.host.service.fetchandsave.ApiRepository;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/**
 * Created by cin on 3/9/2020.
 */

public abstract class BaseActivityViewModel<T, V, Q> extends ViewModel {
    protected MutableLiveData<T> mLive;
    protected MutableLiveData<V> mLiveSuccess;
    protected MutableLiveData<Q> mLiveFail;
    protected MutableLiveData<String[]> mPermissions;
    protected CompositeDisposable mCompositeDispose;
    protected T mView;
    private MutableLiveData<Boolean> mLiveDataDialog;

    public BaseActivityViewModel() {
        mLive = new MutableLiveData<>();
        mLiveSuccess = new MutableLiveData<>();
        mLiveFail = new MutableLiveData<>();
        mLiveDataDialog = new MutableLiveData<>();
        mPermissions = new MutableLiveData<>();
        mCompositeDispose = new CompositeDisposable();
    }

    public MutableLiveData<T> getmLive() {
        return mLive;
    }

    public MutableLiveData<Q> getmLiveFail() {
        return mLiveFail;
    }

    public MutableLiveData<V> getmLiveSuccess() {
        return mLiveSuccess;
    }

    public MutableLiveData<Boolean> getLiveDataDialog() {
        return mLiveDataDialog;
    }

    public MutableLiveData<String[]> getPermissions() {
        Log.d("aaaaaaaaaaa", "a");
        return this.mPermissions;
    }

    //protected WeakReference<T> mView;

    protected Retrofit getRetrofit(String baseUrl) {
//        if (!isNetworkAvailable(CoexApplication.self())){
//            mLiveDataDialog.postValue(false);
//            return null;
//        }
        return ApiRepository.getInstance()
                .setUrl(baseUrl)
                .createRetrofit();
    }

//    protected Retrofit getRetrofitWithHeader (String baseUrl){
//        return ApiRepository.getInstance()
//                .setUrl(baseUrl)
//                .createRetrofit();
//    }

//    protected boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
//        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
//    }


    protected boolean isPermission(String[] arrs) {
        if (Build.VERSION.SDK_INT >= 23) {

            for (String permission : arrs) {
                if (ContextCompat.checkSelfPermission(CoexApplication.self(), permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            return true;
        }
        return true;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDispose.clear();
    }
}