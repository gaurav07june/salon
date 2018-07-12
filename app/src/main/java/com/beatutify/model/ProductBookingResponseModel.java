package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/10/2018.
 */

public class ProductBookingResponseModel {
    private List<BookedProductDetail> productLists;

    public List<BookedProductDetail> getProductBookings ()
    {
        return productLists;
    }

    public void setProductBookings (List<BookedProductDetail> productLists)
    {
        this.productLists = productLists;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [productBookings = "+productLists+"]";
    }
}
