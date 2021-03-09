package com.smartwebarts.ecoosa.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import com.smartwebarts.ecoosa.models.ProductModel;
import com.smartwebarts.ecoosa.retrofit.VariantModel;

@Entity
public class Task implements Serializable {

    @ColumnInfo(name = "unitIn")
    private String unitIn;

    @ColumnInfo(name = "unit")
    private String unit;

    @ColumnInfo(name = "currentprice")
    private String currentprice;

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "productType")
    private String productType;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "vendorId")
    private String vendorId;

    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "minValue")
    private int minValue;
    public Task(String unitIn, String unit, String currentprice, String id, String brand, String productType, String name, String type, String thumbnail, String price, String description, String vendorId, String quantity, String title,String value) {
        this.unitIn = unitIn;
        this.unit = unit;
        this.currentprice = currentprice;
        this.id = id;
        this.brand = brand;
        this.productType = productType;
        this.name = name;
        this.type = type;
        this.thumbnail = thumbnail;
        this.price = price;
        this.description = description;
        this.vendorId = vendorId;
        this.quantity = quantity;
        this.title=title;
        this.value=value;

    }

    public Task(ProductModel product, String quantity, String unit, String unitIn, String currentprice, String price, String titlegetValues, String valuesOfattr) {
        this.id = product.getId();
        this.brand = product.getBrand();
        this.productType = product.getProductType();
        this.name = product.getName();
        this.type = product.getType();
        this.thumbnail = product.getThumbnail();
        this.price = price;
        this.description = product.getDescription();
        this.vendorId = product.getVendorId();
        this.quantity = quantity;
        this.unit = unit;
        this.unitIn = unitIn;
        this.currentprice = currentprice;
        this.title=titlegetValues;
        this.value=valuesOfattr;
        this.minValue=product.getUnits().get(0).getMinUnit();
    }

    public Task(ProductModel product, String quantity, String price) {
        this.unitIn = product.getUnitIn();
        this.unit = product.getUnit();
        this.currentprice = product.getCurrentprice();
        this.id = product.getId();
        this.brand = product.getBrand();
        this.productType = product.getProductType();
        this.name = product.getName();
        this.type = product.getType();
        this.thumbnail = product.getThumbnail();
        this.price = price;
        this.description = product.getDescription();
        this.vendorId = product.getVendorId();
        this.quantity = quantity;
    }

    public Task(VariantModel variantModel, String quantity, String unit, String unitIn, String currentprice, String price, String titlegetValues, String valuesOfattr, int minValue) {
        this.id = variantModel.getId();
        this.brand = "";
        this.productType = variantModel.getProductType();
        this.name = variantModel.getName();
        this.type = variantModel.getType();
        this.thumbnail = variantModel.getThumbnail();
        this.price = price;
        this.description = variantModel.getDescription();
        this.vendorId = variantModel.getVendorId();
        this.quantity = quantity;
        this.unit = unit;
        this.unitIn = unitIn;
        this.currentprice = currentprice;
        this.title=titlegetValues;
        this.value=valuesOfattr;
        this.minValue=variantModel.getUnits().get(0).getMinUnit();
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /*for variant*/
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

    public String getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(String currentprice) {
        this.currentprice = currentprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
