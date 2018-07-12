package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/15/2018.
 */

public class SalonDetailResponseModel {
    private Salon salonData;

    public Salon getSalon()
    {
        return salonData;
    }

    public void setSalon(Salon salon)
    {
        this.salonData = salon;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [salon = "+ salonData +"]";
    }
}
