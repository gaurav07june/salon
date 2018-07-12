package com.beatutify.util;

import android.content.Context;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;

import android.support.v4.os.ConfigurationCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;


import com.beatutify.R;
import com.beatutify.datepicker.DatePickerConfig;
import com.beatutify.datepicker.DayItemModel;
import com.beatutify.datepicker.MonthItemModel;
import com.beatutify.model.EmployeeWorkingDaysResponseModel;
import com.beatutify.model.Service;
import com.beatutify.model.TimeSlot;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by karan.kalsi on 2/26/2018.
 */

public class AppCommons {

    private AppCommons() {

    }

    /**
     * Creates App Hidden Directory if not exist and return
     *
     * @param context
     * @return
     */
    public static String getAppHiddenDirectory(Context context) {
        try {
            File hidden_dir = new File(Environment.getExternalStorageDirectory() + File.separator + "." + context.getString(R.string.app_name));
            boolean isInitialized = Prefs.getBoolean(context, AppConstants.PREF.DIRECTORY_INITIALIZED, false);
            if (hidden_dir.exists() && !isInitialized) {
                FileUtils.cleanDirectory(hidden_dir);
                Prefs.saveBoolean(context, AppConstants.PREF.DIRECTORY_INITIALIZED, true);
            }
            if (!hidden_dir.exists()) {
                hidden_dir.mkdirs();
                Prefs.saveBoolean(context, AppConstants.PREF.DIRECTORY_INITIALIZED, true);
            }
            return hidden_dir.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static float[] getDistance(Double sLatitude, Double sLongitude, Double eLatitude, Double eLongitude) {
        float[] results = new float[1];
        if (sLatitude != null && sLongitude != null && eLatitude != null && eLongitude != null) {
            Location.distanceBetween(sLatitude, sLongitude, eLatitude, eLongitude, results);
        } else {
            results = null;
        }

        return results;
    }


    public static final String getMonthName(Context context, int monthIndex) {
        switch (monthIndex) {
            case 0:
                return context.getString(R.string.jan);
            case 1:
                return context.getString(R.string.feb);
            case 2:
                return context.getString(R.string.mar);
            case 3:
                return context.getString(R.string.apr);
            case 4:
                return context.getString(R.string.may);
            case 5:
                return context.getString(R.string.jun);
            case 6:
                return context.getString(R.string.jul);
            case 7:
                return context.getString(R.string.aug);
            case 8:
                return context.getString(R.string.sep);
            case 9:
                return context.getString(R.string.oct);
            case 10:
                return context.getString(R.string.nov);
            case 11:
                return context.getString(R.string.dec);
            default:
                return "";
        }
    }

    public static final String getMonthNameFull(Context context, int monthIndex) {
        switch (monthIndex) {
            case 0:
                return context.getString(R.string.jan_full);
            case 1:
                return context.getString(R.string.feb_full);
            case 2:
                return context.getString(R.string.mar_full);
            case 3:
                return context.getString(R.string.apr_full);
            case 4:
                return context.getString(R.string.may_full);
            case 5:
                return context.getString(R.string.jun_full);
            case 6:
                return context.getString(R.string.jul_full);
            case 7:
                return context.getString(R.string.aug_full);
            case 8:
                return context.getString(R.string.sep_full);
            case 9:
                return context.getString(R.string.oct_full);
            case 10:
                return context.getString(R.string.nov_full);
            case 11:
                return context.getString(R.string.dec_full);
            default:
                return "";
        }
    }

    public static final List<MonthItemModel> getMonthItemList(EmployeeWorkingDaysResponseModel employeeWorkingDays) {
        List<MonthItemModel> monthItemList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        int start_year = cal.get(Calendar.YEAR);
        int start_month = cal.get(Calendar.MONTH);
        int start_day = cal.get(Calendar.DAY_OF_MONTH);

        for (int y = 0; y < DatePickerConfig.YEAR_SIZE; y++) {
            int month_index = 0;
            if (y == 0)
                month_index = start_month;


            for (int m = month_index; m < DatePickerConfig.MONTH_SIZE; m++) {
                MonthItemModel monthItem = new MonthItemModel();
                monthItem.setMonthIndex(m);
                monthItem.setYear(start_year + y);

                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.MONTH, m);
                cal.set(Calendar.YEAR, monthItem.getYear());
                int daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                int dummySize = cal.get(Calendar.DAY_OF_WEEK) - 1;
                addDummyDays(monthItem, dummySize);

                for (int d = 0; d < daysCount; d++) {
                    DayItemModel day = new DayItemModel();
                    day.setColumnStartIndex(dummySize);
                    day.setYear(monthItem.getYear());
                    day.setDay(d + 1);
                    day.setMonthIndex(m);
                    day.setDate(String.format("%d/%d/%d", d + 1, m + 1, day.getYear()));
                    day.setLeaveDate(isLeaveDate(day, employeeWorkingDays));
                    day.setDayOff(isDayOff(day,employeeWorkingDays));
                    day.setStartDate(start_year == day.getYear() && start_month == day.getMonthIndex() && day.getDay() == start_day);
                    day.setPastDate(start_year == day.getYear() && start_month == day.getMonthIndex() && day.getDay() < start_day);

                    monthItem.addDay(day);


                }
                monthItemList.add(monthItem);


            }


        }

        return monthItemList;
    }

    public static final List<DayItemModel> getDayItemList(EmployeeWorkingDaysResponseModel employeeWorkingDays, boolean withDummy) {
        List<DayItemModel> dayItemList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        int start_year = cal.get(Calendar.YEAR);
        int start_month = cal.get(Calendar.MONTH);
        int start_day = cal.get(Calendar.DAY_OF_MONTH);

        for (int y = 0; y < DatePickerConfig.YEAR_SIZE; y++) {
            int month_index = 0;
            if (y == 0)
                month_index = start_month;


            for (int m = month_index; m < DatePickerConfig.MONTH_SIZE; m++) {
                MonthItemModel monthItem = new MonthItemModel();
                monthItem.setMonthIndex(m);
                monthItem.setYear(start_year + y);

                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.MONTH, m);
                cal.set(Calendar.YEAR, monthItem.getYear());
                int daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                int dummySize = cal.get(Calendar.DAY_OF_WEEK) - 1;
                if (withDummy)
                    addDummyDays(dayItemList, dummySize, 0);

                for (int d = 0; d < daysCount; d++) {
                    DayItemModel day = new DayItemModel();
                    day.setColumnStartIndex(dummySize);
                    day.setYear(monthItem.getYear());
                    day.setDay(d + 1);
                    day.setMonthIndex(m);
                    day.setDate(String.format("%d/%d/%d", d + 1, m + 1, day.getYear()));
                    day.setLeaveDate(isLeaveDate(day, employeeWorkingDays));
                    day.setDayOff(isDayOff(day,employeeWorkingDays));
                    day.setStartDate(start_year == day.getYear() && start_month == day.getMonthIndex() && day.getDay() == start_day);
                    day.setPastDate(start_year == day.getYear() && start_month == day.getMonthIndex() && day.getDay() < start_day);

                    dayItemList.add(day);


                }

                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

                dummySize = cal.getActualMaximum(Calendar.DAY_OF_WEEK) - cal.get(Calendar.DAY_OF_WEEK);
                if (withDummy)
                    addDummyDays(dayItemList, dummySize, -1);


            }


        }

        return dayItemList;
    }


    private static final void addDummyDays(MonthItemModel monthItem, int dummySize) {
        for (int i = 0; i < dummySize; i++) {
            DayItemModel dummyModel = new DayItemModel();
            dummyModel.setColumnStartIndex(dummySize);
            monthItem.addDay(dummyModel);
        }
    }

    private static final void addDummyDays(List<DayItemModel> dayList, int dummySize, int dayValue) {
        for (int i = 0; i < dummySize; i++) {
            DayItemModel dummyModel = new DayItemModel();
            dummyModel.setColumnStartIndex(dummySize);
            dummyModel.setDay(dayValue);
            dayList.add(dummyModel);
        }
    }


    public static final boolean isLeaveDate(DayItemModel day, EmployeeWorkingDaysResponseModel employeeWorkingDays) {
        boolean isLeaveDate = false;
        if (employeeWorkingDays == null) return isLeaveDate;
        Date leaveFrom = formatWebDate(employeeWorkingDays.getLeaveFrom());
        Date leaveTo = formatWebDate(employeeWorkingDays.getLeaveTo());
        if (leaveFrom == null || leaveTo == null) return isLeaveDate;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fromDateS = df.format(leaveFrom);
        String toDateS = df.format(leaveTo);
        try {


            Date current = df.parse(day.getDay() + "/" + (day.getMonthIndex() + 1) + "/" + day.getYear());
            Date from = df.parse(fromDateS);
            Date to = df.parse(toDateS);
            isLeaveDate = current.getTime() >= from.getTime() && current.getTime() <= to.getTime();


            return isLeaveDate ;





        } catch (Exception e) {

        }

        return false;
    }

    public static final boolean isDayOff(DayItemModel day, EmployeeWorkingDaysResponseModel employeeWorkingDays) {
        boolean isDayOff = false;
        if (employeeWorkingDays == null) return isDayOff;

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date current = df.parse(day.getDay() + "/" + (day.getMonthIndex() + 1) + "/" + day.getYear());

            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(current);

            if (employeeWorkingDays.getAvailableOn()!=null)
            {
                isDayOff=true;
                for (String workingDay : employeeWorkingDays.getAvailableOn())
                {

                    if(workingDay.equals(getDayNameWeb(currentCalendar.get(Calendar.DAY_OF_WEEK))))
                    {
                        isDayOff=false;
                        break;
                    }

                }
            }

            return isDayOff ;





        } catch (Exception e) {

        }

        return false;
    }


    public static final Date formatAppDate(String webDate) {

        if (webDate == null) return null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return df.parse(webDate);
        } catch (Exception e) {

        }

        return null;
    }

    public static final Date formatWebDate(String webDate) {

        if (webDate == null) return null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(webDate);
        } catch (Exception e) {

        }

        return null;
    }

    public static final String toWebDate(String appDateS) {

        if (appDateS == null) return null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date appDate = df.parse(appDateS);
            return df2.format(appDate);
        } catch (Exception e) {

        }

        return "";
    }


    public static final String getWeekDayName(Context context, int dayofWeek) {

        switch (dayofWeek) {
            case 1:
                return context.getString(R.string.sun);
            case 2:
                return context.getString(R.string.mon);
            case 3:
                return context.getString(R.string.tue);
            case 4:
                return context.getString(R.string.wed);
            case 5:
                return context.getString(R.string.thu);
            case 6:
                return context.getString(R.string.fri);
            case 7:
                return context.getString(R.string.sat);
        }

        return "";
    }

    public static final String formatCost(float cost) {
        return new DecimalFormat("#.##").format(cost);
    }


    public static final String to12H(Context context, String timeSlot24h) {
        try {

            String[] time24Ar = timeSlot24h.split(":");
            int hours = Integer.parseInt(time24Ar[0]);
            int minutes = Integer.parseInt(time24Ar[1]);

            if (hours <= 12)
                return hours + ":" + minutes + " " + context.getString(R.string.am);
            else
                return (24 - hours) + ":" + minutes + " " + context.getString(R.string.am);

        } catch (Exception e) {

        }
        return timeSlot24h;

    }

    public static final String getUIDateTime(Context context, DayItemModel selectedDate) {
        return selectedDate.getDay() + " " + getMonthName(context, selectedDate.getMonthIndex()) + ", " + to12H(context, selectedDate.getDate());
    }

    public static final String getUIMonthYear(Context context, DayItemModel selectedDate) {
        return getMonthNameFull(context, selectedDate.getMonthIndex()) + " " + selectedDate.getYear();
    }

    public static final int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static final boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isLocationEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        return (gps_enabled || network_enabled);
    }

    public static final int getServicesTotalTime(List<Service> serviceList) {
        int totalTime = 0;
        if (serviceList == null) return totalTime;

        for (Service service : serviceList) {
            if (service.isSelected()) {
                totalTime += service.getTime();
            }

        }
        return totalTime;
    }


    public static final List<TimeSlot> getAvailableTimeSlot(TimeSlot timeSlot, List<TimeSlot> timeSlotList) {
        int timeSlotIndex = timeSlotList.indexOf(timeSlot);
        List<TimeSlot> availableTimeSlots = new ArrayList<>();
        while (timeSlotIndex >= 0 && timeSlotIndex < timeSlotList.size()) {
            if (timeSlotList.get(timeSlotIndex).isDisabled()) break;
            availableTimeSlots.add(timeSlotList.get(timeSlotIndex));
            timeSlotIndex++;
        }
        return availableTimeSlots;
    }

    public static final String getWebReponseMessage(Context context, int msg_code) {
        switch (msg_code) {
            case 300:
                return context.getString(R.string.web_response_300);
            case 301:
                return context.getString(R.string.web_response_301);
            case 302:
                return context.getString(R.string.web_response_302);
            case 303:
                return context.getString(R.string.web_response_303);
            case 304:
                return context.getString(R.string.web_response_304);
            case 305:
                return context.getString(R.string.web_response_305);
            case 306:
                return context.getString(R.string.web_response_306);
            case 307:
                return context.getString(R.string.web_response_307);
            case 308:
                return context.getString(R.string.web_response_308);
            case 309:
                return context.getString(R.string.web_response_309);
            case 310:
                return context.getString(R.string.web_response_310);
            case 311:
                return context.getString(R.string.web_response_311);
            case 312:
                return context.getString(R.string.web_response_312);
            case 313:
                return context.getString(R.string.web_response_313);
            case 314:
                return context.getString(R.string.web_response_314);
            case 315:
                return context.getString(R.string.web_response_315);
            case 316:
                return context.getString(R.string.web_response_316);
            case 317:
                return context.getString(R.string.web_response_317);
            case 318:
                return context.getString(R.string.web_response_318);
            case 319:
                return context.getString(R.string.web_response_319);
            case 320:
                return context.getString(R.string.web_response_320);
            case 321:
                return context.getString(R.string.web_response_321);
            case 322:
                return context.getString(R.string.web_response_322);
            case 323:
                return context.getString(R.string.web_response_323);
            case 3240:
                return context.getString(R.string.web_response_324);

        }
        return "";
    }

    public static final String getCurrentLanguage(Context context) {
        Locale current = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
        String lang = current.getLanguage();
        if (lang == null || lang.isEmpty()) {
            return "en";
        } else {
            return lang;
        }
    }

    public static final DisplayImageOptions getCustomImageOptions(int drawable_placeholder) {
        DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(drawable_placeholder)
                .showImageOnFail(drawable_placeholder)
                .showImageOnLoading(drawable_placeholder)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .build();
        return imageOptions;
    }
    public static final String getDayNameWeb(int dayOfWeek){
        int index = dayOfWeek-1;
        String[] monthNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        if (index<0 || index>=monthNames.length)return "";
        return monthNames[index];
    }

    @SuppressWarnings("deprecation")
    private void setLocale(Context context,Locale locale){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        } else{
            configuration.locale=locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration,displayMetrics);
        }
    }

    public static final String getShortName(String name)
    {
        if (name==null)return "";
        String shortName="";
        String[] nameAr = name.split(" ");
        shortName += nameAr.length>0 ? nameAr[0] : "";
        shortName += nameAr.length>1 ? nameAr[1] : "";
        return shortName;
    }

    public static String get12HrFormat(String time){
        String timeFormate= "";
        if (!time.equals("")){
            String hourString= time.split(":")[0];
            String minString = time.split(":")[1];
            if (Integer.parseInt(hourString) == 12){
                timeFormate = "12:"+minString+"pm";
            }
            if (Integer.parseInt(hourString) > 12){
                timeFormate = (Integer.parseInt(hourString) - 12)+":"+minString+"pm";
            }
            if (Integer.parseInt(hourString) < 12){
                timeFormate = hourString+":"+minString+"am";
            }
            if (Integer.parseInt(hourString) == 24){
                timeFormate = "00:00am";
            }
        }
        return timeFormate;
    }
}
