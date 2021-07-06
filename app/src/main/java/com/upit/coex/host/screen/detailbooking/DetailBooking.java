package com.upit.coex.host.screen.detailbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.contract.base.BaseInterfaceView;
import com.upit.coex.host.model.data.mainscreen.CheckInOutData;
import com.upit.coex.host.model.data.mainscreen.TransactionData;
import com.upit.coex.host.model.data.room.booking.data.BookingRoomData;
import com.upit.coex.host.screen.base.activity.BaseActivity;
import com.upit.coex.host.service.toast.CoexToast;
import com.upit.coex.host.viewmodel.checkstatus.CheckStatusViewModel;

import java.util.Date;

public class DetailBooking extends BaseActivity<CheckStatusViewModel> implements BaseInterfaceView {
    TextView txtEmail, txtPhone, txtNumGuest, txtTimeOfApp, txtTotalMoney, txtDuration, txtBookingReference, txtDateOfApp, txtPayment;
    TextView txtStatus, txtCheckIn, txtCheckOut;
    TextView txtStartTime, txtEndTime, txtShowStatus;
    Button btnCheck;
    ImageButton mImgBack;
    BookingRoomData data;
    Dialog dialogConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_customer_booking);

        mViewModal = ViewModelProviders.of(this).get(CheckStatusViewModel.class);
        Intent intent = getIntent();
        data = (BookingRoomData) intent.getSerializableExtra(CommonConstants.BOOKING_REFEREN);
        //
        bindView();


    }

    @Override
    protected void onStart() {
        super.onStart();
        showBookingRefe(data);

        // check in
        mViewModal.getmLiveSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String checkInOutData) {
                // check in sucess
                // cap nhat lai data
                mViewModal.requestTransactionData(data.getId());
            }
        });

        // check out
//        mViewModal.getmCheckOutSucess().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String checkInOutData) {
//                mViewModal.requestTransactionData(data.getId());
//                // check out
//                // cap nhat lai data
//                // gone button ----> visiable text, gan text
//            }
//        });


        // fail
        mViewModal.getmLiveFail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                CoexToast.makeToast(DetailBooking.this, Toast.LENGTH_LONG, s);
            }
        });

        mViewModal.getmLive().observe(this, new Observer<TransactionData>() {
            @Override
            public void onChanged(TransactionData transactionData) {
                data.setStatus(transactionData.getStatus());
                if (transactionData.getKey().equals(CommonConstants.CHECK_IN)) {
                    data.setStatus(CommonConstants.STATUS_BOOKING_PENDING);
                    data.setCheckIn(false);
                    data.setCheckOut(false);
                } else if (transactionData.getKey().equals(CommonConstants.CHECK_OUT)) {
                    data.setStatus(CommonConstants.STATUS_BOOKING_ON_GOING);
                    data.setCheckIn(true);
                    data.setCheckOut(false);
                } else {
                    data.setStatus(CommonConstants.STATUS_BOOKING_SUCCESS);
                    data.setCheckIn(true);
                    data.setCheckOut(true);
                }

                showBookingRefe(data);
            }
        });


    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        // anh xa
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhoneNumber);
        txtNumGuest = findViewById(R.id.txtNumberGuest);
        txtTimeOfApp = findViewById(R.id.txtTimeOfApp);
        txtDateOfApp = findViewById(R.id.txtDateOfApp);
        txtTotalMoney = findViewById(R.id.txtTotalMoney);
        txtDuration = findViewById(R.id.txtDuration);
        txtBookingReference = findViewById(R.id.txtBookingReferenceDetail);
        txtStatus = findViewById(R.id.txtStatus);
        txtCheckIn = findViewById(R.id.txtCheckIn);
        txtCheckOut = findViewById(R.id.txtCheckOut);
        mImgBack = findViewById(R.id.imgBackRoomDetail);
        btnCheck = findViewById(R.id.btnCheckStatusBooking);
        txtShowStatus = findViewById(R.id.txtShowStatus);
        txtPayment = findViewById(R.id.txtPayment);
        //

        mImgBack.setOnClickListener(v -> {
            DetailBooking.this.finish();
        });


    }

    public void showBookingRefe(BookingRoomData data) {
        txtEmail.setText(data.getUser().getEmail());
        txtPhone.setText(data.getUser().getClient().getPhone());
        txtNumGuest.setText(data.getNumPerson() + " Guest(s)");
        txtPayment.setText(data.getPayment()?"Yes":"No");

        String date = DateFormat.format("dd/MM/yyyy", new Date(data.getDateTime())).toString();
        txtDateOfApp.setText(date);
        if (data.getStartTime() > 10) {
            txtTimeOfApp.setText(data.getStartTime() + ":00");
        } else {
            txtTimeOfApp.setText("0" + data.getStartTime() + ":00");
        }
        String currency = String.format("%,d", data.getPrice());
        txtTotalMoney.setText(currency + " VND");
//        if (data.getDuration() > 1) {
        txtDuration.setText(data.getDuration() + " hour(s)");
//        } else {
//            txtDuration.setText(data.getDuration() + " hour");
//        }
        txtBookingReference.setText("Detail ID " + data.getBookingReference());
        txtStatus.setText(data.getStatus());
        txtCheckIn.setText(data.getCheckIn() + "");
        txtCheckOut.setText(data.getCheckOut() + "");

        // show button

        if (data.getStatus().equals(CommonConstants.STATUS_BOOKING_PENDING)) {
            // check in
            btnCheck.setText("Check in");
            btnCheck.setOnClickListener(v -> {
//                mViewModal.checkIn(data.getId());
                startDialogConfirmCheckInOut(data.getId(), true);
            });
        } else if (data.getStatus().equals(CommonConstants.STATUS_BOOKING_ON_GOING)) {
            // check out
            btnCheck.setText("Check out");
            btnCheck.setOnClickListener(v -> {
//                mViewModal.checkOut(data.getId());
                startDialogConfirmCheckInOut(data.getId(), false);
            });
        } else if (data.getStatus().equals(CommonConstants.STATUS_BOOKING_SUCCESS)) {
            // success
            btnCheck.setVisibility(View.GONE);
            txtShowStatus.setVisibility(View.VISIBLE);
            txtShowStatus.setText(CommonConstants.STATUS_SUCCESS);
            txtShowStatus.setTextColor(Color.parseColor("#098FB3"));
        } else {
            // cancel -- da huy booking
            btnCheck.setVisibility(View.GONE);
            txtShowStatus.setVisibility(View.VISIBLE);
            txtShowStatus.setText(CommonConstants.STATUS_CANCEL);
            txtShowStatus.setTextColor(Color.parseColor("#C4C4C4"));
        }
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

    public void startDialogConfirmCheckInOut(String id, boolean check) {
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
        if (check){
            txtMessage.setText("Do you sure want to check-in?");
            txtTitle.setText("Confirm your check-in Co-working");
        }else {
            txtMessage.setText("Do you sure want to check-out?");
            txtTitle.setText("Confirm your check-out Co-working");
        }
        btnAccepted.setOnClickListener(v -> {
            //
            mViewModal.requestCheckInOut(id, check);
            dialogConfirm.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialogConfirm.dismiss();
        });

        dialogConfirm = builder.create();
        dialogConfirm.show();
    }

    public void startDialogConfirmCheckOut() {
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

        txtMessage.setText("Do you sure want to check in/out?");
        txtTitle.setText("Confirm your check in/out Co-working");

        btnAccepted.setOnClickListener(v -> {
            //
            mViewModal.checkOut(data.getId());
            dialogConfirm.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialogConfirm.dismiss();
        });

        dialogConfirm = builder.create();
        dialogConfirm.show();
    }


}
