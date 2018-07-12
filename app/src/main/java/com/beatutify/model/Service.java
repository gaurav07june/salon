package com.beatutify.model;

import java.io.Serializable;

/**
 * Created by gaurav.singh on 3/16/2018.
 */

public class Service implements Serializable {
    private int time;

    private float cost;
    private int serviceId;
    private String serviceName;

    private String serviceDesc;

    private boolean isSelected=false;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName ()
    {
        return serviceName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }



    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public void setServiceName (String serviceName)
    {
        this.serviceName = serviceName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [time = "+time+", cost = "+cost+", serviceName = "+serviceName+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        return serviceId == service.serviceId;

    }

    @Override
    public int hashCode() {
        return serviceId;
    }
}
