package com.beatutify.model;

import java.io.Serializable;

/**
 * Created by gaurav.singh on 3/15/2018.
 */

public class Salon implements Serializable{

    private  int salon_id;
    private String address;
    private String description;
    private String image;

    private String longitude;

    private String work_to_time;

    private String latitude;

    private String salon_name;

    private String work_from_time;

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public  String getWork_to_time ()
    {
        return work_to_time;
    }

    public void setWork_to_time (String work_to_time)
    {
        this.work_to_time = work_to_time;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getSalon_name ()
    {
        return salon_name;
    }

    public void setSalon_name (String salon_name)
    {
        this.salon_name = salon_name;
    }

    public String getWork_from_time ()
    {
        return work_from_time;
    }

    public void setWork_from_time (String work_from_time)
    {
        this.work_from_time = work_from_time;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [address = "+address+", description = "+description+", image = "+image+", longitude = "+longitude+", work_to_time = "+work_to_time+", latitude = "+latitude+", salon_name = "+salon_name+", work_from_time = "+work_from_time+"]";
    }



    public int getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(int salon_id) {
        this.salon_id = salon_id;
    }

}
