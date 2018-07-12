package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/14/2018.
 */

public class SalonListRequestModel {
    private int city_id;

    private String token;

    private String country_id;

    public int getCity_id ()
    {
        return city_id;
    }

    public void setCity_id (int city_id)
    {
        this.city_id = city_id;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public String getCountry_id ()
    {
        return country_id;
    }

    public void setCountry_id (String country_id)
    {
        this.country_id = country_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [city_id = "+city_id+", token = "+token+", country_id = "+country_id+"]";
    }
}
