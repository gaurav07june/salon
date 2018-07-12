package com.beatutify.model;

/**
 * Created by gaurav.singh on 4/20/2018.
 */

public class ServiceDetail {

    private int id;

    private String categoryName;

    private String serviceId;

    private String serviceTime;

    private String serviceComment;

    private String categoryId;

    private String cost;

    private String serviceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName ()
    {
        return categoryName;
    }

    public void setCategoryName (String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getServiceId ()
    {
        return serviceId;
    }

    public void setServiceId (String serviceId)
    {
        this.serviceId = serviceId;
    }

    public String getServiceTime ()
    {
        return serviceTime;
    }

    public void setServiceTime (String serviceTime)
    {
        this.serviceTime = serviceTime;
    }

    public String getServiceComment ()
    {
        return serviceComment;
    }

    public void setServiceComment (String serviceComment)
    {
        this.serviceComment = serviceComment;
    }

    public String getCategoryId ()
    {
        return categoryId;
    }

    public void setCategoryId (String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCost ()
    {
        return cost;
    }

    public void setCost (String cost)
    {
        this.cost = cost;
    }

    public String getServiceName ()
    {
        return serviceName;
    }

    public void setServiceName (String serviceName)
    {
        this.serviceName = serviceName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", categoryName = "+categoryName+", serviceId = "+serviceId+", serviceTime = "+serviceTime+", serviceComment = "+serviceComment+", categoryId = "+categoryId+", cost = "+cost+", serviceName = "+serviceName+"]";
    }
}
