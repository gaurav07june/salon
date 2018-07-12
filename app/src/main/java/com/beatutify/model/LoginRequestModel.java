package com.beatutify.model;

/**
 * Created by karan.kalsi on 3/15/2018.
 */

public class LoginRequestModel {

    private String email_phone;

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    private String password;
    private String social_id="";
    public String getEmail_phone() {
        return email_phone;
    }

    public void setEmail_phone(String email_phone) {
        this.email_phone = email_phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
