package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/16/2018.
 */

public class SalonServiceList {
    private Service service;

    private String serviceCatImage;

    private String serviceCatName;

    public Service getService ()
    {
        return service;
    }

    public void setService (Service service)
    {
        this.service = service;
    }

    public String getServiceCatImage ()
    {
        return serviceCatImage;
    }

    public void setServiceCatImage (String serviceCatImage)
    {
        this.serviceCatImage = serviceCatImage;
    }

    public String getServiceCatName ()
    {
        return serviceCatName;
    }

    public void setServiceCatName (String serviceCatName)
    {
        this.serviceCatName = serviceCatName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [service = "+service+", serviceCatImage = "+serviceCatImage+", serviceCatName = "+serviceCatName+"]";
    }
}
