package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/20/2018.
 */

public class GeneralBookingListResponseModel {

    private List<MarriageBookingDetail> upcomingBookingLists;

    private List<MarriageBookingDetail> generalBookingLists;

    public List<MarriageBookingDetail> getGeneralBookingLists() {
        return generalBookingLists;
    }

    public void setGeneralBookingLists(List<MarriageBookingDetail> generalBookingLists) {
        this.generalBookingLists = generalBookingLists;
    }

    public List<MarriageBookingDetail> getUpcomingBookingLists() {
        return upcomingBookingLists;
    }

    public void setUpcomingBookingLists(List<MarriageBookingDetail> upcomingBookingLists) {
        this.upcomingBookingLists = upcomingBookingLists;
    }
}
