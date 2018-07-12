package com.beatutify.model;

/**
 * Created by gaurav.singh on 4/9/2018.
 */

public class BookingListRequestModel {
    private int customerId;
    private String token;
    private int bookingId;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId ()
    {
        return customerId;
    }
    public void setCustomerId (int customerId)
    {
        this.customerId = customerId;
    }
    public String getToken ()
    {
        return token;
    }
    public void setToken (String token)
    {
        this.token = token;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [customerId = "+customerId+", token = "+token+"]";
    }
}
