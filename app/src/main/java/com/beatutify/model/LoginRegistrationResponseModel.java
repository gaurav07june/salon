package com.beatutify.model;

/**
 * Created by gaurav.singh on 3/12/2018.
 */

public class LoginRegistrationResponseModel {

    private String message;

    private Customer customer;


    private String token;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Customer getCustomer ()
    {
        return customer;
    }

    public void setCustomer (Customer customer)
    {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", customer = "+customer+"]";
    }
}
