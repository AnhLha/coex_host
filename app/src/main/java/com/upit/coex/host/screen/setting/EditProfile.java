package com.upit.coex.host.screen.setting;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.model.data.profile.EditProfileData;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.profile.ProfileActivityViewModel;

public class EditProfile extends BaseActivity<ProfileActivityViewModel> implements BaseInterfaceView {

    ImageButton imgBackProfile;
    EditText edtName, edtPhone;
    Button btnSubmitProfile;
    Dialog dialogConfirm;
    Intent intent;
    CoData coData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mViewModal = ViewModelProviders.of(this).get(ProfileActivityViewModel.class);

        intent = getIntent();
        bindView();

        Intent intent = getIntent();
        coData = (CoData) intent.getSerializableExtra("SEND_EDIT_PROFILE");
//        Log.d("bao.nt", coData.getName() + "|" + coData.getPhone());

        // set data
    }

    @Override
    protected void onStart() {
        super.onStart();
        showEditProfile();

        mViewModal.getmLiveEditProfileSuccess().observe(this, new Observer<EditProfileData>() {
            @Override
            public void onChanged(EditProfileData s) {
//                intent.putExtra("name", edtName.getText().toString());
//                intent.putExtra("phone", edtPhone.getText().toString());
                Log.d("bao.nt", "vao day hihihihihi");
                Intent data = new Intent(EditProfile.this, Profile.class);
                data.putExtra("update_profile", s);
                setResult(RESULT_OK, data);
                EditProfile.this.finish();
            }
        });

        // fail
        mViewModal.getmLiveEditProfileFail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                CoexToast.makeToast(EditProfile.this, Toast.LENGTH_LONG, s);
            }
        });
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        imgBackProfile = findViewById(R.id.imgBackProfile);
        imgBackProfile.setOnClickListener(v -> {
            EditProfile.this.finish();
        });

        //
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnSubmitProfile = findViewById(R.id.btnSubmitProfile);

        // forcus
        edtName.setFocusable(true);
        edtName.requestFocus();

        //
//        EditProfileData data = (EditProfileData) intent.getSerializableExtra("SEND_PROFILE");

        btnSubmitProfile.setOnClickListener(v -> {
            startDialogConfirm();
        });

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

        txtMessage.setText("Do you sure want to change room??\n\nAfter changing the room, it will return to the main page.");
        txtTitle.setText("Confirm your change password");

        btnAccepted.setOnClickListener(v -> {
            //
            mViewModal.editProfile(coData.getId(), edtName.getText().toString(), edtPhone.getText().toString());
            dialogConfirm.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialogConfirm.dismiss();
        });

        dialogConfirm = builder.create();
        dialogConfirm.show();
    }

    public void showEditProfile(){
        edtPhone.setText(coData.getPhone());
        edtName.setText(coData.getName());
    }

}
