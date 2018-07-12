package com.beatutify.model;

/**
 * Created by karan.kalsi on 3/22/2018.
 */

public class EmployeeListRequestModel {

    private int salonId;
    private String token;

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
