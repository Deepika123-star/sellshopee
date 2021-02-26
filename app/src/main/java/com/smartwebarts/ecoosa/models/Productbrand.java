package com.smartwebarts.ecoosa.models;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Productbrand  {
    @SerializedName("unit_in")
    @Expose
    private String unitIn;
    @SerializedName("unit")
    @Expose
    private String unit;
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
