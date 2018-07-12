package com.beatutify.model;

/**
 * Created by gaurav.singh on 4/25/2018.
 */

public class DealList {
    private String validTo;

    private String dealDescription;

    private String salonName;

    private String dealTitle;

    private String validFrom;

    private int salonId;

    private String dealImageUrl;

    private int dealId;

    private String salonAddress;

    public String getValidTo ()
    {
        return validTo;
    }

    public void setValidTo (String validTo)
    {
        this.validTo = validTo;
    }

    public String getDealDescription ()
    {
        return dealDescription;
    }

    public void setDealDescription (String dealDescription)
    {
        this.dealDescription = dealDescription;
    }

    public String getSalonName ()
    {
        return salonName;
    }

    public void setSalonName (String salonName)
    {
        this.salonName = salonName;
    }

    public String getDealTitle ()
    {
        return dealTitle;
    }

    public void setDealTitle (String dealTitle)
    {
        this.dealTitle = dealTitle;
    }

    public String getValidFrom ()
    {
        return validFrom;
    }

    public void setValidFrom (String validFrom)
    {
        this.validFrom = validFrom;
    }

    public int getSalonId() {
        return salonId;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }

    public String getDealImageUrl ()
    {
        return dealImageUrl;
    }

    public void setDealImageUrl (String dealImageUrl)
    {
        this.dealImageUrl = dealImageUrl;
    }


    public String getSalonAddress ()
    {
        return salonAddress;
    }

    public void setSalonAddress (String salonAddress)
    {
        this.salonAddress = salonAddress;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [validTo = "+validTo+", dealDescription = "+dealDescription+", salonName = "+salonName+", dealTitle = "+dealTitle+", validFrom = "+validFrom+", salonId = "+salonId+", dealImageUrl = "+dealImageUrl+", dealId = "+dealId+", salonAddress = "+salonAddress+"]";
    }
}
