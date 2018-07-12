package com.beatutify.model;

/**
 * Created by karan.kalsi on 3/23/2018.
 */

public class EmployeeWorkingDaysRequestModel {
    private int empId;
    private String token;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
