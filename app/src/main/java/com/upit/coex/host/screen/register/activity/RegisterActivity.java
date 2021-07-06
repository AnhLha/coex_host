package com.upit.coex.host.screen.register.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.register.RegisterContract;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.dialog.DialogLoad;
import com.upit.coex.host.screen.login.activity.LoginActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.register.RegisterActivityViewModel;

import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity<RegisterActivityViewModel> implements RegisterContract.RegisterInterfaceView {
    //
    DialogLoad dialogLoading;
    EditText edtEmail;

    EditText edtPassword;

    EditText edtConfirm;

    Button btnRegister;

    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mViewModal = ViewModelProviders.of(this).get(RegisterActivityViewModel.class);

        bindView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewModal.getmLiveSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                registerSuccess();
            }
        });

        mViewModal.getmLiveFail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                registerFailed();
            }
        });

    }

    @Override
    public void registerSuccess() {
        dialogLoading.dissLoadingDialog();
        CoexToast.makeToast(this, Toast.LENGTH_LONG, mViewModal.getmLiveSuccess().getValue());
        Intent intent = new Intent(getApplication(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerFailed() {
        dialogLoading.dissLoadingDialog();
        CoexToast.makeToast(this, Toast.LENGTH_LONG, mViewModal.getmLiveFail().getValue());
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
        dialogLoading = new DialogLoad(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);


        // click
        btnRegister.setOnClickListener(v -> {
            dialogLoading.startLoadingDialog();

            mViewModal.doRegister(edtEmail.getText().toString(), edtPassword.getText().toString(), edtConfirm.getText().toString(), true);
        });

        txtLogin.setOnClickListener(v -> {
            this.finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mViewModal.destroyView();
    }
}
