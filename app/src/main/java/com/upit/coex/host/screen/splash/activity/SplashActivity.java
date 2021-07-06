package com.upit.coex.host.screen.splash.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.host.R;
import com.upit.coex.host.screen.addco.Step1;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.login.activity.LoginActivity;
import com.upit.coex.host.screen.mainscreen.activity.MainScreen;
import com.upit.coex.host.service.helper.CoexHelper;
import com.upit.coex.host.viewmodel.splash.SplashActivityViewModel;

import java.util.Date;

public class SplashActivity extends BaseActivity<SplashActivityViewModel> {
    private final int DURATION = 2000;
    private Thread mSplashThread;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mViewModal = ViewModelProviders.of(this).get(SplashActivityViewModel.class);
        checkPermission();
        //mViewModal.checkToken();

        Log.d(TAG, "time : " + CoexHelper.showTime(new Date().getTime()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModal.getmLive().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                mViewModal.checkCo();
            }
        });

        mViewModal.getmLiveSuccess().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                String s = (String) o;
                if (s.equals("have")){
                    Intent intent = new Intent(getApplication(), MainScreen.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplication(), Step1.class);
                    startActivity(intent);
                }
            }
        });

        mViewModal.getmLiveFail().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                //Toast.makeText(getApplicationContext(), getResources().getString(R.string.session_timeout), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void checkPermission(){
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Log.d("bao.nt", "bfdjsbfjdsb");
        mViewModal.requestPermission(permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        L.d(TAG, TAG_ACIVITY, "onRequestPermissionsResult!");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
