package com.tarcc.proin.proin.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class Product {

    //Put product Id here
    private String productId;
    private String name;
    private String description;
    private ArrayList<Benefit> benefits;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Product deserialize(String json) {
        return new Gson().fromJson(json, Product.class);
    }

    public static ArrayList<Product> deserializeList(String json) {
        return new Gson().fromJson(json, new TypeToken<ArrayList<Product>>() {
        }.getType());
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;

    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public ArrayList<Benefit> getBenefits() {
        return benefits;
    }

    public Product setBenefits(ArrayList<Benefit> benefits) {
        this.benefits = benefits;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public Product setProductId(String productId) {
        this.productId = productId;
        return this;
    }
}
