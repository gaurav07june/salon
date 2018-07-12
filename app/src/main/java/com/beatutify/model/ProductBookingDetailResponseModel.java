package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/10/2018.
 */

public class ProductBookingDetailResponseModel {
    private ProductBookings productBookings;

    public ProductBookings getProductBookings ()
    {
        return productBookings;
    }

    public void setProductBookings (ProductBookings productBookings)
    {
        this.productBookings = productBookings;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [productBookings = "+productBookings+"]";
    }
}
