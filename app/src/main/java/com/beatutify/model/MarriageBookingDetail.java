package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/9/2018.
 */

public class MarriageBookingDetail {

    private int bookingId;

    private String bookingDate;

    private String address;

    private String salonName;

    private String bookingTimeSlot;

    private int timeSlot;

    private String salonLogoUrl ;

    private String longitude;

    private String latitude;

    private String additionalDetail;

    private String bookingStatus;

    private String employeeName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate ()
    {
        return bookingDate;
    }

    public void setBookingDate (String bookingDate)
    {
        this.bookingDate = bookingDate;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getSalonName ()
    {
        return salonName;
    }

    public void setSalonName (String salonName)
    {
        this.salonName = salonName;
    }

    public String getBookingTimeSlot() {
        return bookingTimeSlot;
    }
    public void setBookingTimeSlot(String bookingTimeSlot) {
        this.bookingTimeSlot = bookingTimeSlot;
    }
    public String getSalonLogoUrl() {
        return salonLogoUrl;
    }

    public void setSalonLogoUrl(String salonLogoUrl) {
        this.salonLogoUrl = salonLogoUrl;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getAdditionalDetail ()
    {
        return additionalDetail;
    }

    public void setAdditionalDetail (String additionalDetail)
    {
        this.additionalDetail = additionalDetail;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+bookingId+", bookingDate = "+bookingDate+", address = "+address+", salonName = "+salonName+", bookingSlot = "+bookingTimeSlot+", logoUrl  = "+salonLogoUrl +", longitude = "+longitude+", latitude = "+latitude+", additionalDetail = "+additionalDetail+"]";
    }
}
