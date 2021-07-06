package com.upit.coex.host.viewmodel.base;

import androidx.lifecycle.ViewModel;

import com.upit.coex.host.service.fetchandsave.ApiRepository;

import retrofit2.Retrofit;

public class BaseFragmentViewModel extends ViewModel {


    protected Retrofit getRetrofit(String baseUrl) {
        return ApiRepository.getInstance()
                .setUrl(baseUrl)
                .createRetrofit();
    }
}
