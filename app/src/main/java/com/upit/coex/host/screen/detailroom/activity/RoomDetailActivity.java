package com.upit.coex.host.screen.detailroom.activity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.upit.coex.host.CoexApplication;
import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.room.RoomDetailContract;
import com.upit.coex.host.model.data.co.RoomData;
import com.upit.coex.host.model.data.co.ServiceData;
import com.upit.coex.host.model.data.room.booking.data.BookingRoomData;
import com.upit.coex.host.model.request.co.RoomRequest;
import com.upit.coex.host.screen.addco.adapter.service.Service;
import com.upit.coex.host.screen.addco.adapter.service.ServiceAdapter;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.screen.detailroom.activity.adapter.RoomDetailAdapter;
import com.upit.coex.host.screen.room.activity.EditRoom;
import com.upit.coex.host.service.logger.L;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.template.actions.IActionCalendar;
import com.upit.coex.host.template.widget.CalendarView;
import com.upit.coex.host.viewmodel.room.RoomDetailActivityViewModel;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoomDetailActivity extends BaseActivity<RoomDetailActivityViewModel> implements RoomDetailContract.RoomDetailInterfaceView {
    CalendarView mCalendarView;
    TextView txtNameRoom, txtAboutRoom, txtPriceRoom, txtMaxPersonRoom, txtMoreService, txtEditRoom;
    ImageButton imgBack;
    RecyclerView recyclerView;
    RoomData roomData;
    // Service
    ArrayList<String> mListOther;
    ArrayList<Service> mListService;
    ServiceAdapter serviceAdapter;

    // list date
    List<Long> mListDateBooking;

    // List user
    TextView txtStatus;
    RecyclerView recyclerViewUser;
    ProgressBar mProgressBar;
    List<BookingRoomData> mListBookingRoomDate;
    RoomDetailAdapter roomDetailAdapter;

    //
    Intent intent;
    public static final int REQUEST_EDIT_ROOM = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_detail);
        mViewModal = ViewModelProviders.of(this).get(RoomDetailActivityViewModel.class);

        //
        bindView();
        // nhan data cua room
        intent = getIntent();
        roomData = (RoomData) intent.getSerializableExtra(CommonConstants.INTENT_DATA_ROOM);
        Log.d("cin111111", roomData.getId());

        //
        showRoom();

    }

    @Override
    protected void onStart() {
        super.onStart();


        Log.d("cin", "chac la vao dayq1");

//        mViewModal.getmLiveEditRoomSuccess().observe(this, new Observer<RoomRequest>() {
//            @Override
//            public void onChanged(RoomRequest roomRequest) {
//                roomData.setAbout(roomRequest.getAbout());
//                roomData.setName(roomRequest.getName());
//                roomData.setMaxPerson(roomRequest.getMaxPerson());
//                roomData.setPrice(roomRequest.getPrice());
//                showRoom();
//                Log.d("cin", "sao khong vao day");
//
//            }
//        });
//
//
//        //
//        mViewModal.getUsers(roomData.getId(), mListDateBooking.get(i));
//        CoexSharedPreference.getInstance().put("DAY_UPDATE", data.getDateTime());

        // lay date tu server
        mViewModal.getDates(roomData.getId());
        // lang nghe data
        onEventCalendar();
        // lang nghe data
        mViewModal.getmLiveSuccess().observe(this, new Observer<List<Long>>() {
            @Override
            public void onChanged(List<Long> longs) {
                txtStatus.setVisibility(View.GONE);
                mListDateBooking = longs;
                Log.d("cin22222", mListDateBooking.size() + "");
                // hien thi len calendar
                // hien thi thang hien tai
                mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(mCalendarView.getmCalendar()));
            }
        });

        mViewModal.getmLiveFail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                recyclerViewUser.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                txtStatus.setText(s);
                txtStatus.setVisibility(View.VISIBLE);

            }
        });



        mViewModal.getmLive().observe(this, new Observer<List<BookingRoomData>>() {
            @Override
            public void onChanged(List<BookingRoomData> bookingRoomData) {
                mProgressBar.setVisibility(View.GONE);
                txtStatus.setVisibility(View.GONE);
                Log.d("cin", "lay duoc du lieu roi nhe bạn roi ");
                recyclerViewUser.setVisibility(View.VISIBLE);
                showInfor(bookingRoomData);

                //



            }
        });

        //


    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        //
        mCalendarView = findViewById(R.id.calendar);
        mListDateBooking = new ArrayList<>();

        //
        txtNameRoom = findViewById(R.id.titleSharedRoom);
        txtAboutRoom = findViewById(R.id.txtYouWill);
        txtMaxPersonRoom = findViewById(R.id.txtMaxPersonRoom);
        txtPriceRoom = findViewById(R.id.txtCost);
        txtEditRoom = findViewById(R.id.editRoom);
        txtMoreService = findViewById(R.id.view_more);
        recyclerView = findViewById(R.id.listSeviceRoom);
        //
        imgBack = findViewById(R.id.imgBackMainScreen);
        //
        recyclerViewUser = findViewById(R.id.listUsers);
        mProgressBar = findViewById(R.id.progressBar);
        txtStatus = findViewById(R.id.txtStatus);

        // event back
        imgBack.setOnClickListener(v -> {
            RoomDetailActivity.this.finish();
        });

        // event
        // edit room
        txtEditRoom.setOnClickListener(v -> {
            // chuyen sang intent
            Intent intent = new Intent(RoomDetailActivity.this, EditRoom.class);
            intent.putExtra("ID_ROOM", roomData);
            startActivityForResult(intent, REQUEST_EDIT_ROOM);

        });

        // view service
        txtMoreService.setOnClickListener(v -> {
            // hien thi dialog
            showServiceDiaLog(mListService, mListOther);

        });


    }

    public void onEventCalendar() {
        // hien thi thang hien tai
        mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(mCalendarView.getmCalendar()));
        for (int i = 0; i < mListDateBooking.size(); i++) {
            mViewModal.getUsers(roomData.getId(), mListDateBooking.get(i));
        }
        //
        mCalendarView.onCalendarViewListener(new IActionCalendar.CalendarViewListener() {
            @Override
            public void onClickedPrevious(Calendar calendar) {
                // hien thi day
                mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(calendar));
            }

            @Override
            public void onClickedNext(Calendar calendar) {
//                int month = calendar.get(Calendar.MONTH);
//                Log.d("cin1111mottttt", month + "");
//                mCalendarView.setChoosedDaysByMonthArrayList(listDate[month]);
                mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(calendar));
            }

            @Override
            public void onClickedItemDay(Calendar calendar) {
                recyclerViewUser.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);

                int flag = 0;
                // hien gọi api get user
                Log.d("cin123456", calendar.get(Calendar.YEAR) + "|" + calendar.get(Calendar.MONTH) + "|" + calendar.get(Calendar.DATE));
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int day = calendar.get(Calendar.DATE);
                for (int i = 0; i < mListDateBooking.size(); i++) {
                    Date date = new Date(mListDateBooking.get(i));
                    if ((date.getYear() + 1900) == year && date.getMonth() == month && day == date.getDate()) {
                        // call api
//                        mProgressBar.setVisibility(View.VISIBLE);
                        txtStatus.setVisibility(View.GONE);
                        flag = 1;
                        Log.d("cin2222221", "co vào đây nhé" + date + "|" + date.getYear() + "|" + date.getMonth() + "|" + date.getDate());
                        mViewModal.getUsers(roomData.getId(), mListDateBooking.get(i));
                    }
                }
                if (flag == 0 ){
                    mProgressBar.setVisibility(View.GONE);
                    recyclerViewUser.setVisibility(View.GONE);
                    txtStatus.setVisibility(View.VISIBLE);
                    txtStatus.setText("No booking");
                }
            }
        });
    }


    public void requestPermission() {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        mViewModal.requestPermission(permissions);
    }

    // show data room
    public void showRoom() {
        txtNameRoom.setText(roomData.getName());
        String currency = String.format("%,d", roomData.getPrice());
        txtPriceRoom.setText(currency + " VND/hour/person");
        txtAboutRoom.setText(roomData.getAbout());
        if (roomData.getMaxPerson() == 1) {
            txtMaxPersonRoom.setText("Max " + roomData.getMaxPerson() + " person");
        } else {
            txtMaxPersonRoom.setText("Max " + roomData.getMaxPerson() + " persons");
        }


        // service
        mListOther = new ArrayList<>();
        mListService = new ArrayList<>();
        ServiceData serviceData = CoexSharedPreference.getInstance().get(CommonConstants.SERVICE_CO, ServiceData.class);
        // kiem tra service
        //ServiceRequest serviceData = co.getmCo().getServiceData();
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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(serviceAdapter);


    }

    public void showService(ServiceData serviceData) {
        // show service;
        mListOther = new ArrayList<>();
        mListService = new ArrayList<>();
        // kiem tra service
        //ServiceRequest serviceData = co.getmCo().getServiceData();
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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(serviceAdapter);

    }

    // dialog service
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

    // lay ngay hien thi len calender
    public ArrayList<Integer> setDataCalender(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        Log.d("cin123123", year + "|" + month);
        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 0; i < mListDateBooking.size(); i++) {
            Date date = new Date(mListDateBooking.get(i));
            Log.d("cin234", date.getYear() + "|" + date.getMonth() + "|" + date.getDate() + "|" + date.getDay());
            if ((date.getYear() + 1900) == year && date.getMonth() == month) {
                days.add(date.getDate());
                Log.d("cin123123", days.get(days.size() - 1) + "");
            }
        }
        return days;
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

    public void showInfor(List<BookingRoomData> list){
        mListBookingRoomDate = new ArrayList<>();
        mListBookingRoomDate = list;
        roomDetailAdapter = new RoomDetailAdapter(list, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CoexApplication.self());
        ((LinearLayoutManager) mLayoutManager).setOrientation(RecyclerView.VERTICAL);
        recyclerViewUser.setLayoutManager(mLayoutManager);
        roomDetailAdapter.notifyDataSetChanged();
        recyclerViewUser.setAdapter(roomDetailAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_EDIT_ROOM) {
            if (resultCode == Activity.RESULT_OK) {
                RoomRequest room = (RoomRequest) data.getSerializableExtra("update_room");
                L.d("cin", "result" + room.getName());
                txtNameRoom.setText(room.getName());
                txtPriceRoom.setText(room.getPrice().toString() + " vnd/hour/person");
                txtAboutRoom.setText(room.getAbout());
                if (room.getMaxPerson() == 1) {
                    txtMaxPersonRoom.setText("Max " + room.getMaxPerson() + " person");
                } else {
                    txtMaxPersonRoom.setText("Max " + room.getMaxPerson() + " persons");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
