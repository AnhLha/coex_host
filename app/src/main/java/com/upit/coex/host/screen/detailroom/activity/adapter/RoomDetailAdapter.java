package com.upit.coex.host.screen.detailroom.activity.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.host.R;
import com.upit.coex.host.constants.CommonConstants;
import com.upit.coex.host.model.data.room.booking.data.BookingRoomData;
import com.upit.coex.host.screen.detailbooking.DetailBooking;
import com.upit.coex.host.service.sharepreference.CoexSharedPreference;
import com.upit.coex.host.template.actions.ItemClickListenerRecyclerView;

import java.util.Date;
import java.util.List;

public class RoomDetailAdapter extends RecyclerView.Adapter<RoomDetailAdapter.RoomDetailViewHolder> {
    List<BookingRoomData> mListBooking;
    Activity activity;

    public RoomDetailAdapter(List<BookingRoomData> mListBooking, Activity activity) {
        this.mListBooking = mListBooking;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RoomDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_booking, parent, false);
        return new RoomDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomDetailViewHolder holder, int position) {
        BookingRoomData bookingRoomData = mListBooking.get(position);
        holder.txtEmail.setText(": " + bookingRoomData.getUser().getEmail());
        holder.txtPhone.setText(": " + bookingRoomData.getUser().getClient().getPhone());
        holder.txtBookingReference.setText("#" + bookingRoomData.getBookingReference());
        if (bookingRoomData.getStartTimeDate() < 10) {
            holder.txtStartTime.setText("0" + bookingRoomData.getStartTimeDate() + ":00");
        } else {
            holder.txtStartTime.setText(bookingRoomData.getStartTimeDate() + ":00");
        }
        int endtime = bookingRoomData.getStartTimeDate() + bookingRoomData.getDurationDate();
        Log.d("bao.nt", endtime + "");
//        holder.txtEndTime.setText(DateFormat.format("hh:mm", new Date(endtime)).toString());
        if (endtime < 10) {
            Log.d("bao.nt", endtime + "");
            holder.txtEndTime.setText("0" + endtime + ":00");
        } else {
            if (endtime == 24) {
                holder.txtEndTime.setText("23:59");
            } else {
                holder.txtEndTime.setText(endtime + ":00");

            }
        }

        if (bookingRoomData.getStatus().equals("on_going")){
            holder.txtStatus.setText("on going");
        }else{
            holder.txtStatus.setText(bookingRoomData.getStatus()+"");
        }

        if (bookingRoomData.getStatus().equals(CommonConstants.STATUS_BOOKING_SUCCESS)){
//            holder.mImage.setBackgroundTintList(Color.colorSpace(789));
            holder.mImage.setBackgroundResource(R.drawable.ic_alarm_outline_success);
        }else if (bookingRoomData.getStatus().equals(CommonConstants.STATUS_BOOKING_PENDING) ||
                bookingRoomData.getStatus().equals(CommonConstants.STATUS_BOOKING_ON_GOING)){
            holder.mImage.setBackgroundResource(R.drawable.ic_alarm_outline);
        }else {
            holder.mImage.setBackgroundResource(R.drawable.ic_alarm_outline_cancel);
        }
        holder.setItemClickListenerRecyclerView(new ItemClickListenerRecyclerView() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (position >= 0) {
                    BookingRoomData data = mListBooking.get(position);
//                    CoexSharedPreference.getInstance().put("DAY_UPDATE", data.getDateTime());
                    Log.d("bao.nt", "aaaaaaaaaaaa");
                    Intent intent = new Intent(activity, DetailBooking.class);

                    intent.putExtra(CommonConstants.BOOKING_REFEREN, data);
                    //
                    Log.d("bao.nt", "bbbbbbbbbb");
                    activity.startActivity(intent);
                    Log.d("bao.nt", "cccccccccccc");

//                    // hien thi dialog
//                    Dialog dialog = new Dialog(activity);
//                    dialog.setContentView(R.layout.information_customer_booking);
//                    TextView txtEmail, txtPhone, txtNumGuest, txtTimeOfApp, txtTotalMoney, txtDuration, txtBookingReference, txtDateOfApp;
//                    TextView txtStatus, txtCheckIn, txtCheckOut;
//                    TextView txtStartTime, txtEndTime;
//                    // anh xa
//                    txtEmail = dialog.findViewById(R.id.txtEmail);
//                    txtPhone = dialog.findViewById(R.id.txtPhoneNumber);
//                    txtNumGuest = dialog.findViewById(R.id.txtNumberGuest);
//                    txtTimeOfApp = dialog.findViewById(R.id.txtTimeOfApp);
//                    txtDateOfApp = dialog.findViewById(R.id.txtDateOfApp);
//                    txtTotalMoney = dialog.findViewById(R.id.txtTotalMoney);
//                    txtDuration = dialog.findViewById(R.id.txtDuration);
//                    txtBookingReference = dialog.findViewById(R.id.txtBookingReferenceDetail);
//                    txtStatus = dialog.findViewById(R.id.txtStatus);
//                    txtCheckIn = dialog.findViewById(R.id.txtCheckIn);
//                    txtCheckOut = dialog.findViewById(R.id.txtCheckOut);
//
//                    // set data
//                    txtEmail.setText(data.getUser().getEmail());
//                    txtPhone.setText(data.getUser().getClient().getPhone());
//                    if (data.getNumPerson() > 1) {
//                        txtNumGuest.setText(data.getNumPerson() + " guests");
//                    } else {
//                        txtNumGuest.setText(data.getNumPerson() + " guest");
//                    }
////                    Date date = new Date(data.getDateTime());
//                    String date = DateFormat.format("MM/dd/yyyy", new Date(data.getDateTime())).toString();
//                    txtDateOfApp.setText(date + " VND");
//                    if (data.getStartTime() > 10){
//                        txtTimeOfApp.setText(data.getStartTime() + ":00");
//                    }else {
//                        txtTimeOfApp.setText("0" + data.getStartTime() + ":00");
//                    }
//                    String currency = String.format("%,d", data.getPrice());
//                    txtTotalMoney.setText(currency + " VND");
//                    if (data.getDuration() > 1) {
//                        txtDuration.setText(data.getDuration() + " hours");
//                    } else {
//                        txtDuration.setText(data.getDuration() + " hour");
//                    }
//                    txtBookingReference.setText("Detail ID " + data.getBookingReference());
//                    txtStatus.setText(data.getStatus());
//                    txtCheckIn.setText(data.getCheckIn() + "");
//                    txtCheckOut.setText(data.getCheckOut() + "");
//
//                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mListBooking == null) ? 0 : mListBooking.size();
    }

    public class RoomDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtEmail, txtPhone, txtNumGuest, txtTimeOfApp, txtTotalMoney, txtDuration, txtBookingReference;
        TextView txtStartTime, txtEndTime, txtStatus;
        ImageButton mImage;

        ItemClickListenerRecyclerView itemClickListenerRecyclerView;

        public ItemClickListenerRecyclerView getItemClickListenerRecyclerView() {
            return itemClickListenerRecyclerView;
        }

        public void setItemClickListenerRecyclerView(ItemClickListenerRecyclerView itemClickListenerRecyclerView) {
            this.itemClickListenerRecyclerView = itemClickListenerRecyclerView;
        }

        public RoomDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mImage = itemView.findViewById(R.id.imgItem);
            this.txtStatus = itemView.findViewById(R.id.txtStatus);
            this.txtEmail = itemView.findViewById(R.id.txtItemEmail);
            this.txtPhone = itemView.findViewById(R.id.txtItemPhone);
            this.txtBookingReference = itemView.findViewById(R.id.txtBookingReference);
            txtStartTime = itemView.findViewById(R.id.txtTimeStart);
            txtEndTime = itemView.findViewById(R.id.txtTimeEnd);

            itemView.setOnClickListener(this);
//            this.txtNumGuest = itemView.findViewById(R.id.txtNumberGuest);
//            this.txtTimeOfApp = itemView.findViewById(R.id.txtTimeOfApp);
//            this.txtTotalMoney = itemView.findViewById(R.id.txtTotalMoney);
//            this.txtDuration = itemView.findViewById(R.id.txtDuration);
        }

        @Override
        public void onClick(View v) {
            itemClickListenerRecyclerView.onClick(v, getAdapterPosition(), false);
        }
    }
}
