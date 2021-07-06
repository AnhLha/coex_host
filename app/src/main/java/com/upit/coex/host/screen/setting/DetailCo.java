package com.upit.coex.host.screen.setting;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.upit.coex.host.R;
import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.model.data.co.CoData;
import com.upit.coex.host.model.data.co.Photo;
import com.upit.coex.host.model.data.co.ServiceData;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.screen.addco.adapter.ListPhotoAdapter;
import com.upit.coex.host.screen.addco.adapter.ListRoomAdapter;
import com.upit.coex.host.screen.addco.adapter.service.Service;
import com.upit.coex.host.screen.addco.adapter.service.ServiceAdapter;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.setting.adapter.ListAdapterRes;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.viewmodel.profile.ProfileActivityViewModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailCo extends BaseActivity<ProfileActivityViewModel> implements BaseInterfaceView {

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
    CoData co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_coo);

        mViewModal = ViewModelProviders.of(this).get(ProfileActivityViewModel.class);


//        co = CoexSharedPreference.getInstance().get(CommonConstants.CO, CoData.class);
        bindView();
    }


    @Override
    protected void onStart() {
        super.onStart();

        mViewModal.checkCo();

        mViewModal.getmLiveCoSuccess().observe(this, new Observer<CoData>() {
            @Override
            public void onChanged(CoData coData) {
                co = coData;
                showCo();
            }
        });


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


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(, Step1.class);
//                startActivity(intent);
                Log.d("anhtu", "click back step 3");
                DetailCo.this.finish();
            }
        });

        txtViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showServiceDiaLog(mListService, mListOther);
            }
        });

        //


    }

    public void showCo() {

        // show room
        List<RoomRequest> list = new ArrayList<>();
        for (int i = 0; i < co.getmListRoom().size(); i++){
            RoomRequest request = new RoomRequest(co.getmListRoom().get(i).getName(), co.getmListRoom().get(i).getAbout(), co.getmListRoom().get(i).getPrice(), co.getmListRoom().get(i).getMaxPerson());
            list.add(request);
        }

        ListRoomAdapter roomAdapter = new ListRoomAdapter(this, list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);
        roomAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(roomAdapter);

        // show photo
        mList = new ArrayList<>();
//         them anh vao list
//        add();
        for (String e : co.getPhoto()) {
            mList.add(new Photo(e));
        }
        Log.d("bao.nt", "----------------------------------" + mList.size());
        // hien thi anh len viewpage
        Log.d("List", mList.size() + "");
        ListAdapterRes adapter = new ListAdapterRes(this, mList);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());

        // hien thi data
        txtName.setText(co.getName());
        txtAbout.setText(co.getAbout());
        txtAddress.setText("Address: " + co.getAddress());

        // show service;
        mListOther = new ArrayList<>();
        mListService = new ArrayList<>();
        // kiem tra service
        ServiceData serviceData = co.getServiceData();
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
    }


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
        L.d("bao.nt", "ondestroy step 3");
        L.d("bao.nt", "----------------------");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("anhtu", "pause step 3");
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
        L.d("bao.nt", "new intent step 3");
    }

}


