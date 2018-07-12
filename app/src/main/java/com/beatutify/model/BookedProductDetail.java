package com.beatutify.model;

/**
 * Created by gaurav.singh on 4/12/2018.
 */

public class BookedProductDetail {

    private String salonLogoUrl;

    private int bookingId;

    private String category;

    private String price;

    private String address;

    private String salonName;

    private String pickupDate;

    private int salonId;

    private int quantity;

    private String productLogo;

    private String bookingStatus;

    private String productname;

    private int productId;

    public String getSalonLogoUrl ()
    {
        return salonLogoUrl;
    }

    public void setSalonLogoUrl (String salonLogoUrl)
    {
        this.salonLogoUrl = salonLogoUrl;
    }

    public int getBookingId ()
    {
        return bookingId;
    }

    public void setBookingId (int bookingId)
    {
        this.bookingId = bookingId;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
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

    public String getPickupDate ()
    {
        return pickupDate;
    }

    public void setPickupDate (String pickupDate)
    {
        this.pickupDate = pickupDate;
    }

    public int getSalonId ()
    {
        return salonId;
    }

    public void setSalonId (int salonId)
    {
        this.salonId = salonId;
    }

    public int getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (int quantity)
    {
        this.quantity = quantity;
    }

    public String getProductLogo ()
    {
        return productLogo;
    }

    public void setProductLogo (String productLogo)
    {
        this.productLogo = productLogo;
    }

    public String getBookingStatus ()
    {
        return bookingStatus;
    }

    public void setBookingStatus (String bookingStatus)
    {
        this.bookingStatus = bookingStatus;
    }

    public String getProductname ()
    {
        return productname;
    }

    public void setProductname (String productname)
    {
        this.productname = productname;
    }

    public int getProductId ()
    {
        return productId;
    }

    public void setProductId (int productId)
    {
        this.productId = productId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [salonLogoUrl = "+salonLogoUrl+", bookingId = "+bookingId+", category = "+category+", price = "+price+", address = "+address+", salonName = "+salonName+", pickupDate = "+pickupDate+", salonId = "+salonId+", quantity = "+quantity+", productLogo = "+productLogo+", bookingStatus = "+bookingStatus+", productname = "+productname+", productId = "+productId+"]";
    }
}
