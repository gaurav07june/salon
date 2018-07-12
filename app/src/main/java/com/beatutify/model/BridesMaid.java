package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/26/2018.
 */

public class BridesMaid {
    private String brideName;

    private int serviceType;

    public String getBrideName() {
        return brideName;
    }

    public void setBrideName(String brideName) {
        this.brideName = brideName;
    }

    public int getServiceType ()
    {
        return serviceType;
    }

    public void setServiceType (int serviceType)
    {
        this.serviceType = serviceType;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [brideName = "+brideName+", serviceName = "+serviceType+"]";
    }
}
