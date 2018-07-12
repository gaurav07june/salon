package com.beatutify.model;

/**
 * Created by karan.kalsi on 4/16/2018.
 */

public class CheckSocialIdRequestModel {
    String email="";
    String socialId="";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }
}
