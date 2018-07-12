package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/18/2018.
 */

public class ReserveProductRequestModel {
    private String token;
    private int salonId;
    private List<SalonProduct> products;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public List<SalonProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SalonProduct> products) {
        this.products = products;
    }
}
