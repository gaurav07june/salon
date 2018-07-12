package com.beatutify.model;

import java.io.Serializable;

/**
 * Created by karan.kalsi on 3/21/2018.
 */

public class ServiceCategory implements Serializable {
    private int serviceCatId;
    private String serviceCatName;
    private String serviceCatImage;

    public int getServiceCatId() {
        return serviceCatId;
    }

    public void setServiceCatId(int serviceCatId) {
        this.serviceCatId = serviceCatId;
    }

    public String getServiceCatName() {
        return serviceCatName;
    }

    public void setServiceCatName(String serviceCatName) {
        this.serviceCatName = serviceCatName;
    }

    public String getServiceCatImage() {
        return serviceCatImage;
    }

    public void setServiceCatImage(String serviceCatImage) {
        this.serviceCatImage = serviceCatImage;
    }
}
