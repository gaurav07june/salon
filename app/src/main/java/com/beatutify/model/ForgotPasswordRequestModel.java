package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/29/2018.
 */

public class ForgotPasswordRequestModel {
    private String email;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+"]";
    }
}
