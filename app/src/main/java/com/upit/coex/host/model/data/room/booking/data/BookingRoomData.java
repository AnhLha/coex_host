package com.upit.coex.host.model.data.room.booking.data;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingRoomData  extends BaseData implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("update_at")
    @Expose
    private String updateAt;
    @SerializedName("check_in")
    @Expose
    private Boolean checkIn;
    @SerializedName("check_out")
    @Expose
    private Boolean checkOut;
    @SerializedName("booking_reference")
    @Expose
    private String bookingReference;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("roomId")
    @Expose
    private String roomId;
    @SerializedName("user")
    @Expose
    private UserData user;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("start_time_date")
    @Expose
    private Integer startTimeDate;
    @SerializedName("duration_date")
    @Expose
    private Integer durationDate;
    @SerializedName("date_time")
    @Expose
    private Long dateTime;
    @SerializedName("start_time")
    @Expose
    private Integer startTime;
    @SerializedName("numPerson")
    @Expose
    private Integer numPerson;

    @SerializedName("payment")
    @Expose
    private boolean mPayment;

    public BookingRoomData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Boolean getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Boolean checkOut) {
        this.checkOut = checkOut;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStartTimeDate() {
        return startTimeDate;
    }

    public void setStartTimeDate(Integer startTimeDate) {
        this.startTimeDate = startTimeDate;
    }

    public Integer getDurationDate() {
        return durationDate;
    }

    public void setDurationDate(Integer durationDate) {
        this.durationDate = durationDate;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getNumPerson() {
        return numPerson;
    }

    public void setNumPerson(Integer numPerson) {
        this.numPerson = numPerson;
    }

    public boolean getPayment() {
        return mPayment;
    }

    public void setPayment(boolean mPayment) {
        this.mPayment = mPayment;
    }
}
