package com.beatutify.model;
import java.util.List;
public class ProductBookings {
    private String idPicupDate;
    public String getIdPicupDate() {
        return idPicupDate;
    }
    public void setIdPicupDate(String idPicupDate) {
        this.idPicupDate = idPicupDate;
    }
    private int bookingId;
    private String address;
    private String pickupDate;
    private String salonName;
    private String lattitude;
    private int salonId;
    private String salonLogoUrl;
    public String getSalonLogoUrl() {
        return salonLogoUrl;
    }
    public void setSalonLogoUrl(String salonLogoUrl) {
        this.salonLogoUrl = salonLogoUrl;
    }

    private String longitude;
    private String bookingStatus;

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    private List<Products> products;

    public int getBookingId ()
    {
        return bookingId;
    }

    public void setBookingId (int bookingId)
    {
        this.bookingId = bookingId;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getPickupDate ()
    {
        return pickupDate;
    }

    public void setPickupDate (String pickupDate)
    {
        this.pickupDate = pickupDate;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getLattitude ()
    {
        return lattitude;
    }

    public void setLattitude (String lattitude)
    {
        this.lattitude = lattitude;
    }

    public int getSalonId ()
    {
        return salonId;
    }

    public void setSalonId (int salonId)
    {
        this.salonId = salonId;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public List<Products> getProducts ()
    {
        return products;
    }

    public void setProducts (List<Products> products)
    {
        this.products = products;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bookingId = "+bookingId+", address = "+address+", pickupDate = "+pickupDate+", name = "+salonName+", lattitude = "+lattitude+", salonId = "+salonId+", longitude = "+longitude+", products = "+products+"]";
    }
}
