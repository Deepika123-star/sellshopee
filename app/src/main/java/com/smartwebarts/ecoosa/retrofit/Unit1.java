package com.smartwebarts.ecoosa.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Unit1 implements Serializable {



    @SerializedName("unit_in")
    @Expose
    private String unitIn;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("min_unit")
    @Expose
    private int minUnit;
    @SerializedName("buingprice")
    @Expose
    private String buingprice;
    @SerializedName("currentprice")
    @Expose
    private String currentprice;

    public String getUnitIn() {
        return unitIn;
    }

    public void setUnitIn(String unitIn) {
        this.unitIn = unitIn;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMinUnit() {
        return minUnit;
    }

    public void setMinUnit(int minUnit) {
        this.minUnit = minUnit;
    }

    public String getBuingprice() {
        return buingprice;
    }

    public void setBuingprice(String buingprice) {
        this.buingprice = buingprice;
    }

    public String getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(String currentprice) {
        this.currentprice = currentprice;
    }
}
