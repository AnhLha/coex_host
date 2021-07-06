package com.upit.coex.host.screen.setting;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.upit.coex.host.R;
import com.upit.coex.host.contract.profile.ProfileContract;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.model.data.profile.EditProfileData;
import com.upit.coex.host.model.data.profile.ProfileData;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.viewmodel.profile.ProfileActivityViewModel;

public class Profile extends BaseActivity<ProfileActivityViewModel> implements ProfileContract.ProfileInterfaceView {
    TextView txtName, txtID, txtEmail, txtPhone;
    TextView txtEditProfile, txtChangePassword, txtShowProfile;
    ImageButton imgBackSetting;
    RelativeLayout imgAvata;
    public static final int AVATA_CODE = 5;
    public static final int REQUEST_EDIT_PROFILE = 6;
    CoData co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mViewModal = ViewModelProviders.of(this).get(ProfileActivityViewModel.class);
        Log.d("bao.nt", "vao day dau tien nhe");

        bindView();


    }

    @Override
    protected void onStart() {
        super.onStart();

        // hien thi thong tin
        mViewModal.checkCo();

        mViewModal.getmLiveCoSuccess().observe(this, new Observer<CoData>() {
            @Override
            public void onChanged(CoData coData) {
                co = coData;
                showProfile();
            }
        });


        mViewModal.me();
//        showProfile(co);
        Log.d("bao.nt", "tiếp tuck vao day");
        mViewModal.getmLive().observe(this, new Observer<ProfileData>() {
            @Override
            public void onChanged(ProfileData profileData) {
                txtEmail.setText(profileData.getEmail());

            }
        });

    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        txtChangePassword = findViewById(R.id.changePassword);
        txtEditProfile = findViewById(R.id.edtProfile);
        txtShowProfile = findViewById(R.id.txtShowProfile);

        //
        txtShowProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, DetailCo.class);
            startActivity(intent);
        });

        //
        txtChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        //
        txtEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, EditProfile.class);
            EditProfileData editProfileData = new EditProfileData(co.getName(), co.getPhone());
            intent.putExtra("SEND_EDIT_PROFILE", co);
            startActivityForResult(intent, REQUEST_EDIT_PROFILE);
        });

        //
        txtName = findViewById(R.id.space);
        txtID = findViewById(R.id.edtGenId);
        txtEmail = findViewById(R.id.edtEmail);
        txtPhone = findViewById(R.id.edtPhone);

        //
//        imgAvata = findViewById(R.id.imgAvata);
        imgBackSetting = findViewById(R.id.imgBack);

        imgBackSetting.setOnClickListener(v -> {
            Profile.this.finish();
        });

//        imgAvata.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent, AVATA_CODE);
//        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AVATA_CODE && resultCode == RESULT_OK){
            final Uri imgUri = data.getData();
//            Glide.with(Profile.this).load(imgUri).into(imgAvata);
        }


        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == RESULT_OK){
//            String name = data.getStringExtra("name");
//            String phone =
            Log.d("bao.nt", "aaaaaaaaaa");
            EditProfileData editProfileData = (EditProfileData) data.getSerializableExtra("update_profile");
            txtName.setText(editProfileData.getName());
            txtPhone.setText(editProfileData.getPhone());
        }
    }

    public void showProfile(){
        txtName.setText(co.getName());
        txtID.setText(co.getId());
        txtPhone.setText(co.getPhone());

    }


}
