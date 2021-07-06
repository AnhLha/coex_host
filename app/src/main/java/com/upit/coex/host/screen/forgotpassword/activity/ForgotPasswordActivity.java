package com.upit.coex.host.screen.forgotpassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.host.R;
import com.upit.coex.host.contract.login.LoginContract;
import com.upit.coex.host.model.data.login.LoginData;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.login.LoginActivityViewModel;


import butterknife.ButterKnife;

public class ForgotPasswordActivity extends BaseActivity<LoginActivityViewModel> implements LoginContract.LoginInterfaceView {

    //@BindView(R.id.edtPHone)
    EditText edtForgotPassEmail;

    //@BindView(R.id.btnSubmit)
    Button btnResetPass;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mViewModal = ViewModelProviders.of(this).get(LoginActivityViewModel.class);
        ///
        bindView();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //CoexHelper.loadImageFromResource(this,"background",lnBackground,"linearLoadImage");

        mViewModal.getmLiveSuccess().observe(this, new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {

            }
        });

        mViewModal.getmLiveFail().observe(this, s -> {
//            loginFailed();
        });

    }

    @Override
    public void observeLifeCycle() {
        mViewModal.getMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                // L.d(TAG,TAG_ACTIVITY,);
            }
        });
    }


    @Override
    public void bindView() {
        ButterKnife.bind(this);
        edtForgotPassEmail = findViewById(R.id.edtForgotPassEmail);
        btnResetPass = findViewById(R.id.btnForgotPassword);
        btnBack = findViewById(R.id.btnBackForgotPass);
        //CoexHelper.loadImageFromResource(this,"background",lnBackground,"mydiposal");

        btnResetPass.setOnClickListener(v -> {
            Toast.makeText(this, "CLICK RESET PASS", Toast.LENGTH_LONG).show();
        });

        btnBack.setOnClickListener(v -> {
            this.finish();
        });
    }

    @Override
    public void loginFailed() {

        CoexToast.makeToast(this, Toast.LENGTH_LONG, mViewModal.getmLiveFail().getValue());
        // quay lai login
        Intent intent = new Intent(getApplication(), com.upit.coex.host.screen.login.activity.LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginSuccess() {

        Intent intent = new Intent(getApplication(), ForgotPasswordActivity.class);
        startActivity(intent);
//        CoexToast.makeToast(this, Toast.LENGTH_LONG, mViewModal.getmLiveSuccess().getValue());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mViewModal.removeDisposal();

        //mViewModal.destroyView();


    }


    //btn onclick
//    @OnClick(R.id.btnSubmit)
//    public void submit(View view){
//
//    }
}
