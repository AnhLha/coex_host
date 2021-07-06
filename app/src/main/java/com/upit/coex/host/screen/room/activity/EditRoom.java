package com.upit.coex.host.screen.room.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.upit.coex.host.R;
import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.model.data.co.RoomData;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.detailroom.activity.RoomDetailActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.room.RoomDetailActivityViewModel;

public class EditRoom extends BaseActivity<RoomDetailActivityViewModel> implements BaseInterfaceView {
    EditText name;
    EditText about;
    EditText price;
    EditText maxPersion;
    Button btnCreateRoom;
    ImageButton imgBack;
    Dialog dialogConfirm;
    RoomData roomData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        mViewModal = ViewModelProviders.of(this).get(RoomDetailActivityViewModel.class);

        // get intent
        Intent intent = getIntent();
        roomData = (RoomData) intent.getSerializableExtra("ID_ROOM");
        Log.d("cin", roomData.getId() + "|" + roomData.getName() + "");
        bindView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        show(roomData);
//        name.setFocusable(true);

        mViewModal.getmLiveEditRoomSuccess().observe(this, new Observer<RoomRequest>() {
            @Override
            public void onChanged(RoomRequest s) {
                //
                Intent data = new Intent(EditRoom.this, RoomDetailActivity.class);
                data.putExtra("update_room", s);
                setResult(RESULT_OK, data);
                Log.d("cin", "chac la vao day");
                EditRoom.this.finish();
            }
        });

        mViewModal.getmLiveEditRoomFail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //
                CoexToast.makeToast(EditRoom.this, Toast.LENGTH_LONG, s);
            }
        });

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
        //

        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cinwww1", "vao onclik");
//                mViewModal.editRoom(id, name.getText().toString(), about.getText().toString(), price.getText().toString(), maxPersion.getText().toString() );
                startDialogConfirm();

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditRoom.this.finish();
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


    public void startDialogConfirm(){
        Log.d("cin", "Co vao day nay1");
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
            mViewModal.editRoom(roomData.getId(), name.getText().toString(), about.getText().toString(), price.getText().toString(), maxPersion.getText().toString() );
            dialogConfirm.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialogConfirm.dismiss();
        });

        dialogConfirm = builder.create();
        dialogConfirm.show();
    }

    public void show(RoomData roomData){
        name.setText(roomData.getName());
        about.setText(roomData.getAbout());
        price.setText(roomData.getPrice()+"");
        maxPersion.setText(roomData.getMaxPerson()+"");
    }
}
