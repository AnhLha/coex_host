package com.upit.coex.host.screen.room.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.addcoostep2.CoContract;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.screen.addco.Step2;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.co.CoActivityViewModel;

public class AddRoom extends BaseActivity<CoActivityViewModel> implements CoContract.CoInterfaceView {

    EditText name;
    EditText about;
    EditText price;
    EditText maxPersion;
    Button btnCreateRoom;
    ImageButton imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_style_room);

        mViewModal = ViewModelProviders.of(this).get(CoActivityViewModel.class);
        bindView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewModal.getmLiveSuccess().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if (pair.first.equals(CommonConstants.ADD_ROOM)){
                    RoomRequest roomRequest = new RoomRequest();
                    roomRequest.setAbout(about.getText().toString());
                    roomRequest.setName(name.getText().toString());
                    roomRequest.setMaxPerson(Integer.valueOf(maxPersion.getText().toString()));
                    roomRequest.setPrice(Integer.valueOf(price.getText().toString()));

                    CoexToast.makeToast(AddRoom.this, Toast.LENGTH_LONG, pair.second.toString());
                    addRoom(roomRequest);
                }
            }
        });

        mViewModal.getmLiveFail().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if (pair.first.equals(CommonConstants.ADD_ROOM)){
                    CoexToast.makeToast(AddRoom.this, Toast.LENGTH_LONG, pair.second.toString());
                }
            }
        });

    }

    @Override
    public void isEmpty() {
        CoexToast.makeToast(this, Toast.LENGTH_LONG, "Create fails!");
    }

    public void addRoom(RoomRequest roomData) {
//        CoexToast.makeToast(this, Toast.LENGTH_LONG, "Create success!");
        Intent data = new Intent(AddRoom.this, Step2.class);
        data.putExtra("room", roomData);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void isNotEmpty() {
//        CoexToast.makeToast(this, Toast.LENGTH_LONG, "Create success!");
        Intent data = new Intent(AddRoom.this, Step2.class);
        data.putExtra("room", mViewModal.getmLive().getValue());
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        name = findViewById(R.id.edtStyleRoom);
        about = findViewById(R.id.edtAboutRoom);
        price = findViewById(R.id.edtPrice);
        maxPersion = findViewById(R.id.edtMaxPer);
        btnCreateRoom = findViewById(R.id.btnCreateStyle);
        imgBack = findViewById(R.id.imgClose);

        // forcus
        name.setFocusable(true);
        name.requestFocus();


        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModal.addRoom(name.getText().toString(), about.getText().toString(), price.getText().toString(), maxPersion.getText().toString());
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
