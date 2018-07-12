package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 3/26/2018.
 */

public class MarriageBookingRequestModel {
    private int timeSlot;

    private List<BridesMaid> bridesMaid;

    private String token;

    private int salonId;

    private String additionalDetails;

    private String date;

    private String type;

    public int getTimeSlot ()
    {
        return timeSlot;
    }

    public void setTimeSlot (int time)
    {
        this.timeSlot = time;
    }

    public List<BridesMaid> getBridesMaid ()
    {
        return bridesMaid;
    }

    public void setBridesMaid (List<BridesMaid> bridesMaid)
    {
        this.bridesMaid = bridesMaid;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public int getSalonId ()
    {
        return salonId;
    }

    public void setSalonId (int salonId)
    {
        this.salonId = salonId;
    }

    public String getAdditionalDetails ()
    {
        return additionalDetails;
    }

    public void setAdditionalDetails (String additionalDetails)
    {
        this.additionalDetails = additionalDetails;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [time = "+timeSlot+", bridesMaid = "+bridesMaid+", token = "+token+", salonId = "+salonId+", additionalDetails = "+additionalDetails+", date = "+date+", type = "+type+"]";
    }
}
