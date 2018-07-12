package com.beatutify.util;

import android.content.Context;

import com.beatutify.R;
import com.beatutify.datepicker.DayItemModel;

/**
 * Created by karan.kalsi on 2/26/2018.
 */

public class AppConstants {

    public interface API
    {
        String BASE_URL="http://demo.newmediaguru.co/beautify/api/";
        int STATUS_CODE_SUCCESS= 1;
        int TOKEN_EXPIRED_CODE = 401;

    }
    public interface RETROFIT
    {
        int TIMEOUT=60;
    }
    public interface PREF
    {
        String DIRECTORY_INITIALIZED="pref_directory_initialized";
        String LOGGED_IN_CUSTOMER_DATA="pref_logged_in_customer_data";
        String CUSTOMER_TOKEN="pref_customer_token";
        String IS_GUEST = "prefs_is_guest";
    }

    public interface CONSTANTS{
        int MARRIAGE_BOOKING_REQ = 71;
        int RESERVE_PRODUCT_REQ = 11;
        int UPCOMING_MARRIAGE_BOOKING_REQ = 31;
        int PAST_MARRIAGE_BOOKING_REQ = 32;
        int UPCOMING_PRODUCT_BOOKING_REQ = 51;
        int PAST_PRODUCT_BOOKING_REQ = 52;
        int UPCOMING_GENERAL_BOOKING_REQ = 61;
        int PAST_GENERAL_BOOKING_REQ = 62;
    }
    public interface EXTRAS
    {
        String SERVICE_CATEGORY_DATA="extra_service_category_data";
        String SALON_DATA="extra_salon_data";
        String SELECTED_SERVICES="extra_selected_services";
        String SELECTED_EMPLOYEE="extra_selected_employee";
        String SELECTED_DATE="extra_selected_date";
        String EMPLOYEE_WORKING_DAYS="extra_employee_working_days";
        String EMAIL_ADDRESS = "extra_email_address";
        String SELECTED_TIME_SLOT="extra_selected_time_slot";
        String AVAILABLE_TIME_SLOTS = "extra_available_time_slots";
        String SALON_IMAGE_URL = "extra_salon_image_url";
        String SALON_ID = "extra_salon_id";
        String MARRIAGE_BOOKING_MSG = "extra_marriage_booking_msg";
        String RESERVE_PRODUCT_MSG = "extra_res_pro_msg";
        String REQ_FROM_PAGE = "extra_request_code";
        String BOOKING_ID = "extra_booking_id";
        String RESERVED_PRODUCT_DETAIL = "extra_reserve_product";
        String PICK_UP_DATE = "extra_pickup_date";
        String SUBMIT_PICK_DATE = "extra_submit_date";

    }
    public interface  SERVICE_BOOKING
    {
        int TIME_SLOT_DIFF=30;
    }
    public static final int SPLASH_TIME_OUT =  3000;
    public static final int SPLASH_ANIM_TIME=400;
}
