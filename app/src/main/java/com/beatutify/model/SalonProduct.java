package com.beatutify.model;

import java.io.Serializable;

/**
 * Created by gaurav.singh on 4/11/2018.
 */

public class SalonProduct implements Serializable{

    private boolean outOfStock;

    private boolean maxLimitReached;

    private String categoryName;
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    private String imageName;

    private String name;

    private String cost;

    private int quantity;

    private int selectedQuantity = 0;

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategoryName ()
    {
        return categoryName;
    }

    public void setCategoryName (String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getImageName ()
    {
        return imageName;
    }

    public void setImageName (String imageName)
    {
        this.imageName = imageName;
    }

    public String getName ()
    {
        return name;
    }
    public void setName (String name)
    {
        this.name = name;
    }
    public String getCost ()
    {
        return cost;
    }

    public void setCost (String cost)
    {
        this.cost = cost;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [categoryName = "+categoryName+", imageName = "+imageName+", name = "+name+", cost = "+cost+"]";
    }

    public boolean isOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(boolean outOfStock) {
        this.outOfStock = outOfStock;
    }

    public boolean isMaxLimitReached() {
        return maxLimitReached;
    }

    public void setMaxLimitReached(boolean maxLimitReached) {
        this.maxLimitReached = maxLimitReached;
    }

    public void addSelectedQuantity()
    {

        selectedQuantity++;
    }

    public void removeSelectedQuantity()
    {
        selectedQuantity--;
    }
}
