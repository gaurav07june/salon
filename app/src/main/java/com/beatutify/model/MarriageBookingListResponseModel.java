package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/9/2018.
 */

public class MarriageBookingListResponseModel {

    private List<MarriageBookingDetail> bookingLists;

    public List<MarriageBookingDetail> getBookingDetail ()
    {
        return bookingLists;
    }

    public void setBookingDetail (List<MarriageBookingDetail> bookingLists)
    {
        this.bookingLists = bookingLists;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bookingDetail = "+bookingLists+"]";
    }
}
