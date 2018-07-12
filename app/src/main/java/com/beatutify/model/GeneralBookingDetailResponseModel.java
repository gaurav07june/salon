package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/20/2018.
 */

public class GeneralBookingDetailResponseModel {

    private int id;

    private String bookingDate;

    private List<ServiceDetail> serviceLists;

    private String salonName;

    private String additionalDetail;

    private String bookingTimeSlot;

    private String bookingStatus;

    private String employeeName;

    private String salonLogoUrl;

    public String getSalonLogoUrl() {
        return salonLogoUrl;
    }

    public void setSalonLogoUrl(String salonLogoUrl) {
        this.salonLogoUrl = salonLogoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingDate ()
    {
        return bookingDate;
    }

    public void setBookingDate (String bookingDate)
    {
        this.bookingDate = bookingDate;
    }

    public List<ServiceDetail> getServiceLists() {
        return serviceLists;
    }

    public void setServiceLists(List<ServiceDetail> serviceLists) {
        this.serviceLists = serviceLists;
    }

    public String getSalonName ()
    {
        return salonName;
    }

    public void setSalonName (String salonName)
    {
        this.salonName = salonName;
    }

    public String getAdditionalDetail ()
    {
        return additionalDetail;
    }

    public void setAdditionalDetail (String additionalDetail)
    {
        this.additionalDetail = additionalDetail;
    }

    public String getBookingTimeSlot ()
    {
        return bookingTimeSlot;
    }

    public void setBookingTimeSlot (String bookingTimeSlot)
    {
        this.bookingTimeSlot = bookingTimeSlot;
    }

    public String getBookingStatus ()
    {
        return bookingStatus;
    }

    public void setBookingStatus (String bookingStatus)
    {
        this.bookingStatus = bookingStatus;
    }

    public String getEmployeeName ()
    {
        return employeeName;
    }

    public void setEmployeeName (String employeeName)
    {
        this.employeeName = employeeName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", bookingDate = "+bookingDate+", serviceLists = "+serviceLists+", salonName = "+salonName+", additionalDetail = "+additionalDetail+", bookingTimeSlot = "+bookingTimeSlot+", bookingStatus = "+bookingStatus+", employeeName = "+employeeName+"]";
    }
}
