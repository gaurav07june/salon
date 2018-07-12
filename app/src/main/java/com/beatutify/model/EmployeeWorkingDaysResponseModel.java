package com.beatutify.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan.kalsi on 3/23/2018.
 */

public class EmployeeWorkingDaysResponseModel implements Serializable{

    private String leaveFrom;
    private String leaveTo;
    private List<String> availableOn;

    public String getLeaveFrom() {
        return leaveFrom;
    }

    public void setLeaveFrom(String leaveFrom) {
        this.leaveFrom = leaveFrom;
    }

    public String getLeaveTo() {
        return leaveTo;
    }

    public void setLeaveTo(String leaveTo) {
        this.leaveTo = leaveTo;
    }

    public List<String> getAvailableOn() {
        return availableOn;
    }

    public void setAvailableOn(List<String> availableOn) {
        this.availableOn = availableOn;
    }
}
