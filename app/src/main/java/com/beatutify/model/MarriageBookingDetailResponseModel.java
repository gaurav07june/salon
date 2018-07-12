package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/9/2018.
 */

public class MarriageBookingDetailResponseModel {

    private int id;
    private String salonName;
    private String bookingDate;
    private String additionalDetail;
    private String salonLogoUrl;
    private List<BridesMaid> bridesMaid;
    private int timeSlot;

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

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

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getAdditionalDetail() {
        return additionalDetail;
    }

    public void setAdditionalDetail(String additionalDetail) {
        this.additionalDetail = additionalDetail;
    }

    public List<BridesMaid> getBridesMaid() {
        return bridesMaid;
    }

    public void setBridesMaid(List<BridesMaid> bridesMaid) {
        this.bridesMaid = bridesMaid;
    }
}
