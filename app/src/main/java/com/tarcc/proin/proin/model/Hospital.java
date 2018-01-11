package com.tarcc.proin.proin.model;

import com.google.gson.Gson;


public class Hospital {

    private String imageUrl;
    private String name;
    private String address;
    private String description;
    private String operationHours;
    private double lat;
    private double lng;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Hospital deserialize(String json) {
        return new Gson().fromJson(json, Hospital.class);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Hospital setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getName() {
        return name;
    }

    public Hospital setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Hospital setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Hospital setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getOperationHours() {
        return operationHours;
    }

    public Hospital setOperationHours(String operationHours) {
        this.operationHours = operationHours;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Hospital setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Hospital setLng(double lng) {
        this.lng = lng;
        return this;
    }
}
