package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/25/2018.
 */

public class MyDealsResponseModel {
    private List<DealList> dealList;

    public List<DealList> getDealList() {
        return dealList;
    }

    public void setDealList(List<DealList> dealList) {
        this.dealList = dealList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dealList = "+dealList+"]";
    }
}
