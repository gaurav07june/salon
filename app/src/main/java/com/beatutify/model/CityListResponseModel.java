package com.beatutify.model;

import java.util.List;

/**
 * Created by gaurav.singh on 3/29/2018.
 */

public class CityListResponseModel {
    private List<Cities> cities;

    public List<Cities> getCities ()
    {
        return cities;
    }

    public void setCities (List<Cities> cities)
    {
        this.cities = cities;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cities = "+cities+"]";
    }
}
