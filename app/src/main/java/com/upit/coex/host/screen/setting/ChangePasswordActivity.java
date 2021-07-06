package com.upit.coex.host.screen.setting;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.upit.coex.host.R;
import com.upit.coex.host.contract.profile.ProfileContract;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.profile.ProfileActivityViewModel;

public class ChangePasswordActivity extends BaseActivity<ProfileActivityViewModel> implements ProfileContract.ProfileInterfaceView {
    ImageButton imgBackProfile;
    Button btnChange;
    EditText edtPassOld, edtPassNew, edtCon;
    Dialog dialogConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mViewModal = ViewModelProviders.of(this).get(ProfileActivityViewModel.class);
        bindView();
    }


    @Override
    protected void onStart() {
        super.onStart();

        mViewModal.getmLiveSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                CoexToast.makeToast(ChangePasswordActivity.this, Toast.LENGTH_LONG, s);
                resetEditText();

//                // chuyen ve man hinh login
//                CoexSharedPreference.getInstance().put(CommonConstants.TOKEN, "");
//                CoexSharedPreference.getInstance().put(CommonConstants.SERVICE_CO, "");
//                CoexSharedPreference.getInstance().put(CommonConstants.COEX_ID, "");
//                CoexSharedPreference.getInstance().put(CommonConstants.CO,"");
////                Log.d("bao.nt", "aaaaaaaaaaa");
//                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
            }
        });


        mViewModal.getmLiveFail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                CoexToast.makeToast(ChangePasswordActivity.this, Toast.LENGTH_LONG, s);
            }
        });
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {

        edtPassOld = findViewById(R.id.edtPassOld);
        edtPassNew = findViewById(R.id.edtPass);
        edtCon = findViewById(R.id.edtConfirm);
        btnChange = findViewById(R.id.btnChangePass);

        // forcus
        edtPassOld.setFocusable(true);
        edtPassOld.requestFocus();


        //
        btnChange.setOnClickListener(v -> {
            Log.d("bao.nt", "Co vao day nay");
            startDialogConfirm();
//            mViewModal.changPassword(edtPassOld.getText().toString(), edtPassNew.getText().toString(), edtCon.getText().toString());
        });


        imgBackProfile = findViewById(R.id.imgBackProfile);

        //
        imgBackProfile.setOnClickListener(v -> {
            ChangePasswordActivity.this.finish();
        });
    }

    public void resetEditText(){
        edtCon.setText("");
        edtPassNew.setText("");
        edtPassOld.setText("");
    }

    public void startDialogConfirm(){
        Log.d("bao.nt", "Co vao day nay1");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.confirm_change, null);
        builder.setView(view);
        builder.setCancelable(true);
        //
        Button btnCancel, btnAccepted;
        TextView txtTitle, txtMessage;

        txtMessage = view.findViewById(R.id.txtMessage);
        txtTitle = view.findViewById(R.id.txtTitle);
        btnAccepted = view.findViewById(R.id.btnAccepted);
        btnCancel = view.findViewById(R.id.btnCancel);

        txtMessage.setText("Do you sure want to change password??\n\nAfter changing the password, it will return to the login page.");
        txtTitle.setText("Confirm your change password");

        btnAccepted.setOnClickListener(v -> {
            mViewModal.changPassword(edtPassOld.getText().toString(), edtPassNew.getText().toString(), edtCon.getText().toString());
            dialogConfirm.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialogConfirm.dismiss();
        });

        dialogConfirm = builder.create();
        dialogConfirm.show();
    }


}
