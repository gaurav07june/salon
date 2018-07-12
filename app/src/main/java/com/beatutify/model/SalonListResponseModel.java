package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 3/14/2018.
 */

public class SalonListResponseModel {
    private List<Salon> salonList;

    public List<Salon> getSalonList ()
    {
        return salonList;
    }

    public void setSalonList (List<Salon> salonList)
    {
        this.salonList = salonList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [salonList = "+salonList+"]";
    }
}
