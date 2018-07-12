package com.beatutify.datepicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan.kalsi on 3/22/2018.
 */

public class MonthItemModel {
    private int monthIndex;
    private int year;
    private List<DayItemModel> days=new ArrayList<>();

    public int getMonthIndex() {
        return monthIndex;
    }

    public void setMonthIndex(int monthIndex) {
        this.monthIndex = monthIndex;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<DayItemModel> getDays() {
        return days;
    }

    public void setDays(List<DayItemModel> days) {
        this.days = days;
    }
    public void addDay(DayItemModel day) {
        this.days.add(day);
    }
}
