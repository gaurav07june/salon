package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/16/2018.
 */

public class ServiceCatListRequestModel {
    private String token;
    private int salonId;
    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public int getSalonId ()
    {
        return salonId;
    }

    public void setSalonId (int salonId)
    {
        this.salonId = salonId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", salonId = "+salonId+"]";
    }
}
