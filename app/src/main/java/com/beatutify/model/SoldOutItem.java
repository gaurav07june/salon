package com.beatutify.model;

/**
 * Created by gaurav.singh on 4/18/2018.
 */

public class SoldOutItem {

    private int productId;

    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
