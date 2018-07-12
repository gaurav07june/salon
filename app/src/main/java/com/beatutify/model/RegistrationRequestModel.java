package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/12/2018.
 */

public class RegistrationRequestModel {
    private int city_id=0;

    private String first_name="";

    private String email="";

    private String address="";

    private String last_name;

    private String social_id="";

    private String password="";

    private String lang="en";

    private String mobile="";

    public int getCity_id ()
    {
        return city_id;
    }

    public void setCity_id (int city_id)
    {
        this.city_id = city_id;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }


    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
    }

    public String getSocial_id ()
    {
        return social_id;
    }

    public void setSocial_id (String social_id)
    {
        this.social_id = social_id;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getLang ()
    {
        return lang;
    }

    public void setLang (String lang)
    {
        this.lang = lang;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
