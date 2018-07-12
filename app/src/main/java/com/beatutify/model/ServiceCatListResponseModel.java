package com.beatutify.model;

import java.util.List;

/**
 * Created by karan.kalsi on 3/21/2018.
 */

public class ServiceCatListResponseModel  {
    private List<ServiceCategory> categoriesList;


    public List<ServiceCategory> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<ServiceCategory> categoriesList) {
        this.categoriesList = categoriesList;
    }

}
