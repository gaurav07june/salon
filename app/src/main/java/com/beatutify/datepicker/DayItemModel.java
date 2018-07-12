package com.beatutify.datepicker;

import java.io.Serializable;

/**
 * Created by karan.kalsi on 3/22/2018.
 */

public class DayItemModel implements Serializable{
    private int day;
    private int monthIndex;
    private int year;
    private String date=""; // dd/MM/yyyy
    private boolean isPastDate=false;
    private boolean isStartDate=false;
    private boolean isLeaveDate=false;
    private boolean isDayOff=false;
    private int columnStartIndex;
    private boolean isSelected =false;



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public int getColumnStartIndex() {
        return columnStartIndex;
    }

    public void setColumnStartIndex(int columnStartIndex) {
        this.columnStartIndex = columnStartIndex;
    }

    public boolean isLeaveDate() {
        return isLeaveDate;
    }

    public void setLeaveDate(boolean leaveDate) {
        isLeaveDate = leaveDate;
    }

    public boolean isPastDate() {
        return isPastDate;
    }

    public void setPastDate(boolean pastDate) {
        isPastDate = pastDate;
    }

    public boolean isStartDate() {
        return isStartDate;
    }

    public void setStartDate(boolean startDate) {
        isStartDate = startDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDayOff() {
        return isDayOff;
    }

    public void setDayOff(boolean dayOff) {
        isDayOff = dayOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DayItemModel that = (DayItemModel) o;

        if (day != that.day) return false;
        if (monthIndex != that.monthIndex) return false;
        return year == that.year;

    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + monthIndex;
        result = 31 * result + year;
        return result;
    }
}
