package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/16/2018.
 */

public class ServiceListRequestModel {
    private String token;
    private int salonId;
    private int serviceCatId;
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


    public int getServiceCatId() {
        return serviceCatId;
    }

    public void setServiceCatId(int serviceCatId) {
        this.serviceCatId = serviceCatId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", salonId = "+salonId+"]";
    }
}
