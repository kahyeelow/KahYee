package com.tarcc.proin.proin.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by yee_l on 13/1/2018.
 */

public class ProductPackage {
    private String nric;
    private String productID;
    private String coverage;
    private String premium;
    private String status;
    private String expireDate;
    private String totPaymentYear;
    public String toJson(){
        return new Gson().toJson(this);
    }

    public static ProductPackage deserialize(String json){
        return new Gson().fromJson(json, ProductPackage.class);
    }

    public static ArrayList<ProductPackage> deserializeList(String json){
        return new Gson().fromJson(json, new TypeToken<ArrayList<ProductPackage>>(){}.getType());
    }

    public ProductPackage(String nric, String productID, String coverage, String premium, String status, String expireDate, String totPaymentYear) {
        this.nric = nric;
        this.productID = productID;
        this.coverage = coverage;
        this.premium = premium;
        this.status = status;
        this.expireDate = expireDate;
        this.totPaymentYear = totPaymentYear;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getTotPaymentYear() {
        return totPaymentYear;
    }

    public void setTotPaymentYear(String totPaymentYear) {
        this.totPaymentYear = totPaymentYear;
    }
}
