package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 4/18/2018.
 */

public class ReserveProductResponseModel {

    private List<SoldOutItem> soldOut;
    public List<SoldOutItem> getSoldOut() {
        return soldOut;
    }
    public void setSoldOut(List<SoldOutItem> soldOut) {
        this.soldOut = soldOut;
    }
}
