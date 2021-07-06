package com.upit.coex.host.model.data.co;

import com.upit.coex.host.model.data.base.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ServiceData extends BaseData implements Serializable {

    @SerializedName("wifi")
    @Expose
    private Boolean wifi;
    @SerializedName("conversionCall")
    @Expose
    private Boolean conversionCall;
    @SerializedName("drink")
    @Expose
    private Boolean drink;
    @SerializedName("printer")
    @Expose
    private Boolean printer;
    @SerializedName("airConditioning")
    @Expose
    private Boolean airConditioning;
    @SerializedName("other")
    @Expose
    private List<String> other = null;

    public ServiceData() {
    }

    public ServiceData(Boolean wifi, Boolean conversionCall, Boolean drink, Boolean printer, Boolean airConditioning, List<String> other) {
        this.wifi = wifi;
        this.conversionCall = conversionCall;
        this.drink = drink;
        this.printer = printer;
        this.airConditioning = airConditioning;
        this.other = other;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getConversionCall() {
        return conversionCall;
    }

    public void setConversionCall(Boolean conversionCall) {
        this.conversionCall = conversionCall;
    }

    public Boolean getDrink() {
        return drink;
    }

    public void setDrink(Boolean drink) {
        this.drink = drink;
    }

    public Boolean getPrinter() {
        return printer;
    }

    public void setPrinter(Boolean printer) {
        this.printer = printer;
    }

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(Boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public List<String> getOther() {
        return other;
    }

    public void setOther(List<String> other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "ServiceData{" +
                "wifi=" + wifi +
                ", conversionCall=" + conversionCall +
                ", drink=" + drink +
                ", printer=" + printer +
                ", airConditioning=" + airConditioning +
                ", other=" + other +
                '}';
    }
}