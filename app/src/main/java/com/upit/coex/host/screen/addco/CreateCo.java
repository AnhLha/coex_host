package com.upit.coex.host.screen.addco;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.addcoostep2.CoContract;
import com.upit.coex.host.model.data.co.Photo;
import com.upit.coex.host.model.request.co.CoRequest;
import com.upit.coex.host.model.request.co.ServiceRequest;
import com.upit.coex.host.screen.addco.adapter.ListPhotoAdapter;
import com.upit.coex.host.screen.addco.adapter.ListRoomAdapter;
import com.upit.coex.host.screen.addco.adapter.service.Service;
import com.upit.coex.host.screen.addco.adapter.service.ServiceAdapter;
import com.upit.coex.host.screen.addco.dialog.DialogLoading;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.mainscreen.activity.MainScreen;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.co.CoActivityViewModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class CreateCo extends BaseActivity<CoActivityViewModel> implements CoContract.CoInterfaceView {
    DialogLoading loading;
    TextView txtName, txtAbout, txtAddress;
    TextView txtViewService;
    Button btnCreateCo;
    ImageButton imgBack;

    // photo
    CircleIndicator indicator;
    ViewPager viewPager;
    ArrayList<Photo> mList;// = new ArrayList<>();

    // service
    ArrayList<Service> mListService;// = new ArrayList<>();
    ArrayList<String> mListOther;// = new ArrayList<>();
    ServiceAdapter serviceAdapter;
    RecyclerView viewService;
    //
    RecyclerView recyclerView;
    CoRequest co;
    CoRequest coRequest = new CoRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_coo);

        Log.d("cin", "create step 3");
        // tao load
        loading = new DialogLoading(this);

        mViewModal = ViewModelProviders.of(this).get(CoActivityViewModel.class);
        // init
        bindView();
        // nhan intent
        Intent intent = getIntent();
        co = (CoRequest) intent.getSerializableExtra("CoStep2");
        bindView();


        //init live data

        mViewModal.getmLiveSuccess().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if (pair.first.equals(CommonConstants.CREATE_CO)) {
                    Log.d("cin", "click create co1");
                    //CoexToast.makeToast(AddRoom.this, Toast.LENGTH_LONG, pair.second.toString());
                    loading.dissLoadingDialog();
                    Intent intent1 = new Intent(CreateCo.this, MainScreen.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);

                }
            }
        });

        //
        mViewModal.getmLiveFail().observe(this, new Observer<Pair>() {
            @Override
            public void onChanged(Pair pair) {
                if (pair.first.equals(CommonConstants.CREATE_CO)) {
                    loading.dissLoadingDialog();
                    CoexToast.makeToast(CreateCo.this, Toast.LENGTH_LONG, pair.second.toString());

                }
            }
        });

        // show data
        mViewModal.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showCo();
//        showService();
        Log.d("cin", "start step 3");

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
        imgBack = findViewById(R.id.imgBackStep3);
        viewPager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        recyclerView = findViewById(R.id.listStyleRoom);
        txtName = findViewById(R.id.textCooBooking);
        txtAddress = findViewById(R.id.addressBooking);
        txtAbout = findViewById(R.id.simply_dummy);
        txtViewService = findViewById(R.id.view_more);
        viewService = findViewById(R.id.listSeviceCoo);
        btnCreateCo = findViewById(R.id.btnCreateCoo);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(, Step1.class);
//                startActivity(intent);
                Log.d("cin", "click back step 3");
                CreateCo.this.finish();
            }
        });

        txtViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showServiceDiaLog(mListService, mListOther);
            }
        });

        //
        btnCreateCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cin", "click create co");
                loading.startLoadingDialog();
                mViewModal.createCo(co);
            }
        });


    }

    public void add() {
//        Log.d("hehe556", co.getmPhoto().size()+"");
        for (String e : co.getmPhoto()) {
            mList.add(new Photo(e));
        }
//
    }

    public void showCo() {

        // show room

        ListRoomAdapter roomAdapter = new ListRoomAdapter(this, co.getmCo().getmListRoom());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) mLayoutManager).setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        roomAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(roomAdapter);

        // show photo
        mList = new ArrayList<>();
//         them anh vao list
//        add();
        for (String e : co.getmPhoto()) {
            mList.add(new Photo(e));
        }
        // hien thi anh len viewpage
        Log.d("List", mList.size() + "");
        ListPhotoAdapter adapter = new ListPhotoAdapter(this, mList);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());

        // hien thi data
        txtName.setText(co.getmCo().getName());
        txtAbout.setText(co.getmCo().getAbout());
        txtAddress.setText("Address: " + co.getmCo().getAddress());

        // show service;
        mListOther = new ArrayList<>();
        mListService = new ArrayList<>();
        // kiem tra service
        ServiceRequest serviceData = co.getmCo().getServiceData();
        if (serviceData.getWifi()) {
            mListService.add(new Service(R.drawable.ic_wifi, "Free wifi"));
        }
        if (serviceData.getDrink()) {
            mListService.add(new Service(R.drawable.ic_drink, "Drink"));
        }
        if (serviceData.getPrinter()) {
            mListService.add(new Service(R.drawable.ic_printer, "Printer"));
        }
        if (serviceData.getAirConditioning()) {
            mListService.add(new Service(R.drawable.ic_air_conditioning, "Air conditioning"));
        }
        if (serviceData.getConversionCall()) {
            mListService.add(new Service(R.drawable.ic_conversion_call, "Conversion call"));
        }
        if (serviceData.getOther().size() != 0) {
            for (String s :
                    serviceData.getOther()) {
                mListOther.add(s);
            }
        }
        // hien thi service len recyclerview
//        showService();
        serviceAdapter = new ServiceAdapter(mListService, this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        viewService.setLayoutManager(layoutManager);
        viewService.setAdapter(serviceAdapter);
        //
    }


    public void showServiceDiaLog(ArrayList<Service> mListService, ArrayList<String> mListOtherService) {
        ArrayList<Service> mMoreService = new ArrayList<>();
        mMoreService = mListService;
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.service);
        RecyclerView recyclerViewMore = dialog.findViewById(R.id.moreService);
        TextView txtOther = dialog.findViewById(R.id.txtOther);
        if (mListOtherService.size() != 0) {
            String other = "";
            for (int i = 0; i < mListOtherService.size() - 1; i++) {
                other += mListOtherService.get(i) + ", ";
            }
            other += mListOtherService.get(mListOtherService.size() - 1) + ".";
            txtOther.setText(other);
            mMoreService.add(new Service(R.drawable.ic_home, "Other service:"));
        }

        if (mMoreService.size() != 0) {
            ServiceAdapter moreServiceAdapter = new ServiceAdapter(mListService, this);
            FlexboxLayoutManager layoutManagerMore = new FlexboxLayoutManager(this);
//        layoutManager.setFlexWrap(FlexWrap.);
            layoutManagerMore.setFlexDirection(FlexDirection.COLUMN);
            layoutManagerMore.setJustifyContent(JustifyContent.FLEX_START);
            layoutManagerMore.setAlignItems(AlignItems.FLEX_START);
            recyclerViewMore.setLayoutManager(layoutManagerMore);
            recyclerViewMore.setAdapter(moreServiceAdapter);
        } else {
            mMoreService.add(new Service("Service is empty"));
        }
        dialog.show();
//        TextView txtWifi, txtAir, txtCon, txtDr, txtPri, txtOther;
//        TextView txtOther;
//        ImageButton wifi, dr, pr, con, air, other;
//        wifi = dialog.findViewById(R.id.iconWifi);
//        dr = dialog.findViewById(R.id.iconDrink);
//        pr = dialog.findViewById(R.id.iconPrinter);
//        air = dialog.findViewById(R.id.iconAirCond);
//        other = dialog.findViewById(R.id.iconOther);
//        con = dialog.findViewById(R.id.iconConver);
//        txtOther = dialog.findViewById(R.id.other);
//        if (!serviceData.getAirConditioning()) {
//            air.setBackground(getDrawable(R.drawable.ic_air_dis));
//        }
//        if (!serviceData.getDrink()) {
//            dr.setBackground(getDrawable(R.drawable.ic_drink_dis));
//        }
//        if (!serviceData.getWifi()) {
//            wifi.setBackground(getDrawable(R.drawable.ic_wifi_dis));
//        }
//        if (!serviceData.getConversionCall()) {
//            con.setBackground(getDrawable(R.drawable.ic_conversion_dis));
//        }
//        if (!serviceData.getPrinter()) {
//            pr.setBackground(getDrawable(R.drawable.ic_printer_dis));
//        }
//        if (serviceData.getOther().size() != 0) {
//            String s = "";
//            for (int i = 0; i < serviceData.getOther().size() - 1; i++) {
//                s += serviceData.getOther().get(i) + ", ";
//            }
//            s += serviceData.getOther().get(serviceData.getOther().size() - 1) + ".";
//            txtOther.setText(s);
//        } else {
//            other.setBackground(getDrawable(R.drawable.ic_home_dis));
//            txtOther.setText("");
//        }
//
//        dialog.show();
    }

    public void requestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        mViewModal.requestPermission(permissions);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        Toast.makeText(this, "back 1", Toast.LENGTH_SHORT).show();
//
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
//            this.finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    public void showService() {
        serviceAdapter = new ServiceAdapter(mListService, this);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        viewService.setLayoutManager(layoutManager);
        viewService.setAdapter(serviceAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("cin", "ondestroy step 3");
        L.d("cin", "----------------------");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("cin", "pause step 3");
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        L.d("cin", "new intent step 3");
    }

}
