package com.beatutify.model;

import java.io.Serializable;

/**
 * Created by karan.kalsi on 3/27/2018.
 */

public class TimeSlot implements Serializable {

    private String time;
    private int id;
    private boolean disabled;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSlot timeSlot = (TimeSlot) o;

        return id == timeSlot.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
