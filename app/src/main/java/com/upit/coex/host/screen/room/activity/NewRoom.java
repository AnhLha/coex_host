package com.upit.coex.host.screen.room.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.host.R;
import com.upit.coex.host.contract.listroom.RoomContract;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.dialog.DialogConfirm;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.room.RoomActivityViewModel;

public class NewRoom extends BaseActivity<RoomActivityViewModel> implements RoomContract.RoomInterfaceView {
    EditText name;
    EditText about;
    EditText price;
    EditText maxPersion;
    Button btnCreateRoom;
    ImageButton imgBack;
    DialogConfirm dialogConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_style_room);
        mViewModal = ViewModelProviders.of(this).get(RoomActivityViewModel.class);
        bindView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewModal.getmLiveSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                CoexToast.makeToast(NewRoom.this, Toast.LENGTH_LONG, s);
                reset();
                // dialog
                dialogConfirm = new DialogConfirm(NewRoom.this, "Thêm phòng thành công", "Bạn có muốn quay về trang chủ không?");
                dialogConfirm.startLoadingDialog();

            }
        });

        mViewModal.getmLiveFail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                CoexToast.makeToast(NewRoom.this, Toast.LENGTH_LONG, s);
            }
        });
    }

    @Override
    public void isEmpty() {

    }

    @Override
    public void isNotEmpty() {

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


//        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    String currency = String.format("%,d", Integer.valueOf(price.getText().toString()));
//                    price.setText(currency);
//                }
//            }
//        });

        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bao.nt11", "vao onclik");
                mViewModal.addRoom(name.getText().toString(), about.getText().toString(), price.getText().toString(), maxPersion.getText().toString() );

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewRoom.this.finish();
            }
        });

    }

    // back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Toast.makeText(this, "back 1", Toast.LENGTH_SHORT).show();
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void reset(){
        name.setText("");
        about.setText("");
        price.setText("");
        maxPersion.setText("");
    }


}
