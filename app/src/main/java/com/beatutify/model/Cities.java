package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/29/2018.
 */

public class Cities {
    private int cityId;

    private String cityName;

    public int getCityId ()
    {
        return cityId;
    }

    public void setCityId (int cityId)
    {
        this.cityId = cityId;
    }

    public String getCityName ()
    {
        return cityName;
    }

    public void setCityName (String cityName)
    {
        this.cityName = cityName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cityId = "+cityId+", cityName = "+cityName+"]";
    }
}
