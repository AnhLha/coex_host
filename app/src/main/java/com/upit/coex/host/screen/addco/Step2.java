package com.upit.coex.host.screen.addco;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.addcoostep2.CoContract;
import com.upit.coex.host.model.data.co.ServiceData;
import com.upit.coex.host.model.request.co.CoRequest;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.screen.addco.adapter.ListOtherAdapter;
import com.upit.coex.host.screen.addco.adapter.ListRoomAdapter;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.dialog.DialogLoad;
import com.upit.coex.host.screen.room.activity.AddRoom;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.viewmodel.co.CoActivityViewModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class Step2 extends BaseActivity<CoActivityViewModel> implements CoContract.CoInterfaceView {

    private static final int REQUEST_CODE = 100;
    DialogLoad dialogLoad;
    EditText edtOther;
    Button addOther;
    Button btnNextTep3;
    Button btnAddRoom;
    CheckBox cw, cd, cp, ca, cc;
    ServiceData serviceData;
    RecyclerView viewOther;
    RecyclerView listRoom;
    ImageButton imgBack;
    ListRoomAdapter roomAdapter;
    ListOtherAdapter otherAdapter;
    List<RoomRequest> mRoom = new ArrayList<>();
    //    ServiceData mService;
    List<String> mOther = new ArrayList<>();
    CoRequest co = new CoRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("cin", "oncreate step 2", getIntent().getStringExtra("room"));

        setContentView(R.layout.add_new_coo_step_2);
        mViewModal = ViewModelProviders.of(this).get(CoActivityViewModel.class);

        //
        Intent intent = getIntent();
        co = (CoRequest) intent.getSerializableExtra("co");
//        L.d("cin", (RoomRequest) intent.getSerializableExtra("room") + "");
//        L.d("ListPic", co.getmPhoto().size() + "");
        bindView();


        Log.d("cin", "create step 2");
        //
        mViewModal.getmLiveSuccess().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if (pair.first.equals(CommonConstants.STEP_2)) {
                    Log.d("cin", "create step 2---------------------------");
//                    CoexToast.makeToast(Step2.this, Toast.LENGTH_LONG, pair.second.toString());
//                    isNotEmpty();
                    Intent intent23 = new Intent(Step2.this, CreateCo.class);
                    //
                    co.getmCo().getServiceData().setWifi(cw.isChecked());
                    co.getmCo().getServiceData().setDrink(cd.isChecked());
                    co.getmCo().getServiceData().setConversionCall(cc.isChecked());
                    co.getmCo().getServiceData().setPrinter(cp.isChecked());
                    co.getmCo().getServiceData().setAirConditioning(ca.isChecked());
                    co.getmCo().getServiceData().setOther(mOther);
                    co.getmCo().setmListRoom(mRoom);
                    //Log.d("hehe55557",co.getmCo().getmListRoom().size()+"");
                    intent23.putExtra("CoStep2", co);
                    startActivity(intent23);
                }
            }
        });

        mViewModal.getmLiveFail().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if (pair.first.equals(CommonConstants.STEP_2)) {
//                    CoexToast.makeToast(Step2.this, Toast.LENGTH_LONG, pair.second.toString());
                }
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        L.d("cin", "onstart step 2");
        // list room
        addControl();
        addOther();



    }

    @Override
    public void isEmpty() {

    }

    @Override
    public void isNotEmpty() {
        //
        Intent intent = new Intent(Step2.this, CreateCo.class);
        //
        co.getmCo().getServiceData().setWifi(cw.isChecked());
        co.getmCo().getServiceData().setDrink(cd.isChecked());
        co.getmCo().getServiceData().setConversionCall(cc.isChecked());
        co.getmCo().getServiceData().setPrinter(cp.isChecked());
        co.getmCo().getServiceData().setAirConditioning(ca.isChecked());
        co.getmCo().getServiceData().setOther(mOther);
        co.getmCo().setmListRoom(mRoom);
        //Log.d("hehe55557",co.getmCo().getmListRoom().size()+"");
        intent.putExtra("CoStep2", co);
        startActivity(intent);
//        finish();
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        dialogLoad = new DialogLoad(this);
        cw = findViewById(R.id.checkWifi);
        ca = findViewById(R.id.checkAir);
        cc = findViewById(R.id.checkCon);
        cp = findViewById(R.id.checkPrinter);
        cd = findViewById(R.id.checkDrink);
        edtOther = findViewById(R.id.edtAddOtherNew);
        viewOther = findViewById(R.id.viewOther);
        btnAddRoom = findViewById(R.id.btnAddMoreRoom);
        btnNextTep3 = findViewById(R.id.btnNextStep3);
        listRoom = findViewById(R.id.listRoom);
        imgBack = findViewById(R.id.imgBackStep1);
        addOther = findViewById(R.id.btnAddOthe);

        addOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edtOther.getText().toString();
                if (s.length() != 0) {
                    mOther.add(s);
                    edtOther.setText("");
                    otherAdapter.notifyDataSetChanged();
                    viewOther.setAdapter(otherAdapter);
                }
                Log.d("aaaaaaa", s);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplication(), Step1.class);
                //startActivity(intent);
                Step2.this.finish();
            }
        });


        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step2.this, AddRoom.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        btnNextTep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModal.doStep3(cw.isChecked(), cd.isChecked(), cp.isChecked(), ca.isChecked(), cc.isChecked(), mOther, mRoom);
            }
        });


    }

    private void addControl() {
        //mRoom = CoexSharedPreference.getInstance().get("room", ListRoomData.class );
        Log.d("hehe", mRoom + "");
        roomAdapter = new ListRoomAdapter(this, mRoom);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) mLayoutManager).setOrientation(RecyclerView.HORIZONTAL);
        listRoom.setLayoutManager(mLayoutManager);
        roomAdapter.notifyDataSetChanged();
        listRoom.setAdapter(roomAdapter);
    }

    private void addOther() {
        otherAdapter = new ListOtherAdapter(mOther, this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.CENTER);
        viewOther.setLayoutManager(layoutManager);
        otherAdapter.notifyDataSetChanged();
        viewOther.setAdapter(otherAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                RoomRequest room = (RoomRequest) data.getSerializableExtra("room");
                L.d("hehe", "result" + room.getName());
                mRoom.add(room);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Toast.makeText(this, "back 1", Toast.LENGTH_SHORT).show();
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("cin", "ondestroy step 2");
        L.d("cin", "----------------------");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("cin", "pau step 2");
    }
}
