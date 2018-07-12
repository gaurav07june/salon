package com.beatutify.model;

/**
 * Created by karan.kalsi on 4/19/2018.
 */

public class BeautifyNotificationModel {


    private String message;

    private String messageCode;

    private String bookingId;

    private String salonName;

    private String date;

    private String bookingType;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getMessageCode ()
    {
        return messageCode;
    }

    public void setMessageCode (String messageCode)
    {
        this.messageCode = messageCode;
    }

    public String getBookingId ()
    {
        return bookingId;
    }

    public void setBookingId (String bookingId)
    {
        this.bookingId = bookingId;
    }

    public String getSalonName ()
    {
        return salonName;
    }

    public void setSalonName (String salonName)
    {
        this.salonName = salonName;
    }

    public String getBookingType ()
    {
        return bookingType;
    }

    public void setBookingType (String bookingType)
    {
        this.bookingType = bookingType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
