package com.upit.coex.host.screen.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.host.R;
import com.upit.coex.host.contract.login.LoginContract;
import com.upit.coex.host.model.data.login.LoginData;
import com.upit.coex.host.screen.addco.Step1;
import com.upit.coex.host.screen.addco.dialog.DialogLoading;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.dialog.DialogLoad;
import com.upit.coex.host.screen.forgotpassword.activity.ForgotPasswordActivity;
import com.upit.coex.host.screen.mainscreen.activity.MainScreen;
import com.upit.coex.host.screen.register.activity.RegisterActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.login.LoginActivityViewModel;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginActivityViewModel> implements LoginContract.LoginInterfaceView {

    DialogLoad dialogLoading;

    private static final String TAG = "LoginActivity";
    //@BindView(R.id.lnBackground)
    LinearLayout lnBackground;

    //@BindView(R.id.edtPHone)
    EditText edtEmail;

    //@BindView(R.id.edtPassword)
    EditText edtPassword;

    //@BindView(R.id.btnSubmit)
    Button btnLogin;

    TextView txtRegister;
    TextView txtFogotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mViewModal = ViewModelProviders.of(this).get(LoginActivityViewModel.class);
        ///
        bindView();

        //
        mViewModal.getmLiveSuccess().observe(this, new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {
                Log.d("cin", "1");
                dialogLoading.startLoadingDialog();
                loginSuccess();
            }
        });

        mViewModal.getmLive().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s.equals("have")){
                    Log.d("pbm","login");
                    Intent intent = new Intent(getApplication(), MainScreen.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplication(), Step1.class);
                    startActivity(intent);
                }
                dialogLoading.dissLoadingDialog();
            }
        });




//


        mViewModal.getmLiveFail().observe(this, s -> {
            loginFailed();
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        Log.d("cin", "------------------++++------------");



        //CoexHelper.loadImageFromResource(this,"background",lnBackground,"linearLoadImage");

//        mViewModal.getmLiveSuccess().observe(this, new Observer<LoginData>() {
//            @Override
//            public void onChanged(LoginData loginData) {
//                Log.d("cin", "1");
//                dialogLoading.startLoadingDialog();
//                loginSuccess();
//            }
//        });
//
//        mViewModal.getmLive().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                dialogLoading.dissLoadingDialog();
//                if (s.equals("have")){
//                    Intent intent = new Intent(getApplication(), MainScreen.class);
//                    startActivity(intent);
//                }else {
//                    Intent intent = new Intent(getApplication(), Step1.class);
//                    startActivity(intent);
//                }
//            }
//        });
//
//
//
//
////
//
//
//        mViewModal.getmLiveFail().observe(this, s -> {
//            loginFailed();
//        });

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

        dialogLoading = new DialogLoad(this);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        txtFogotPass = findViewById(R.id.txtForgotPassword);
        //CoexHelper.loadImageFromResource(this,"background",lnBackground,"mydiposal");
        btnLogin.setOnClickListener(v -> {
            dialogLoading.startLoadingDialog();
            mViewModal.doLogin(edtEmail.getText().toString(), edtPassword.getText().toString());
            dialogLoading.dissLoadingDialog();
        });

        txtRegister.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        txtFogotPass.setOnClickListener(v -> {
            Intent forgotIntent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(forgotIntent);
        });

    }

    @Override
    public void loginFailed() {
        dialogLoading.dissLoadingDialog();
        CoexToast.makeToast(this, Toast.LENGTH_LONG, mViewModal.getmLiveFail().getValue());
    }

    @Override
    public void loginSuccess() {
        Log.d("cin", "2");
        mViewModal.checkCo();
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
