package com.beatutify.retrofit;

import com.beatutify.model.CityListResponseModel;
import com.beatutify.model.EmployeeListResponseModel;
import com.beatutify.model.EmployeeWorkingDaysResponseModel;
import com.beatutify.model.GeneralBookingDetailResponseModel;
import com.beatutify.model.GeneralBookingListResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.LoginRegistrationResponseModel;
import com.beatutify.model.MarriageBookingDetailResponseModel;
import com.beatutify.model.MarriageBookingListResponseModel;
import com.beatutify.model.MyDealsResponseModel;
import com.beatutify.model.ProductBookingDetailResponseModel;
import com.beatutify.model.ProductBookingResponseModel;
import com.beatutify.model.ProductListResponseModel;
import com.beatutify.model.ReserveProductResponseModel;
import com.beatutify.model.SalonDetailResponseModel;
import com.beatutify.model.SalonListResponseModel;
import com.beatutify.model.ServiceCatListResponseModel;
import com.beatutify.model.ServiceListResponseModel;
import com.beatutify.model.TimeSlotReponseModel;
import com.google.gson.annotations.Expose;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebAPI {
    @POST("signup")
    Call<GenericResponseModel<LoginRegistrationResponseModel>> doRegistration(@Body RequestBody request);
    @POST("login")
    Call<GenericResponseModel<LoginRegistrationResponseModel>> doLogin(@Body RequestBody request);


    @POST("salon-list")
    Call<GenericResponseModel<SalonListResponseModel>> showSalonList(@Body RequestBody request);


    @POST("salon-detail")
    Call<GenericResponseModel<SalonDetailResponseModel>> showSalonDetail(@Body RequestBody request);

    @POST("salon-service-list")
    Call<GenericResponseModel<ServiceListResponseModel>> getServiceList(@Body RequestBody request);
    @POST("categories-list")
    Call<GenericResponseModel<ServiceCatListResponseModel>> getServiceCatList(@Body RequestBody request);
    @POST("salon-employee-list")
    Call<GenericResponseModel<EmployeeListResponseModel>> getEmployeeList(@Body RequestBody request);

    @POST("employee-available-slot")
    Call<GenericResponseModel<TimeSlotReponseModel>> getTimeSlots(@Body RequestBody request);
    @POST("employee-working-days")
    Call<GenericResponseModel<EmployeeWorkingDaysResponseModel>> getEmployeeWorkingDays(@Body RequestBody request);

    @POST("marriage-booking")
    Call<GenericResponseModel<Object>> doMarriageBooking(@Body RequestBody request);

    @POST("service-bookings")
    Call<GenericResponseModel<Object>> doServiceBooking(@Body RequestBody request);

    @POST("forgot-password")
    Call<GenericResponseModel<Object>> doForgotPassword(@Body RequestBody request);

    @POST("reset-password")
    Call<GenericResponseModel<Object>> doResetPassword(@Body RequestBody request);

    @GET("city-list")
    Call<GenericResponseModel<CityListResponseModel>> getCityList();
    @POST("check-social-id")
    Call<GenericResponseModel<Object>> checkSocialId(@Body RequestBody request);

    @POST("upcoming-marriage-booking")
    Call<GenericResponseModel<MarriageBookingListResponseModel>> getUpcomingMarriageBookingList(@Body RequestBody request);

    @POST("past-marriage-booking")
    Call<GenericResponseModel<MarriageBookingListResponseModel>> getPastMarriageBookingList(@Body RequestBody request);

    @POST("marriage-booking-detail")
    Call<GenericResponseModel<MarriageBookingDetailResponseModel>> getMarriageBookingDetail(@Body RequestBody request);

    @POST("cancel-marriage-booking")
    Call<GenericResponseModel<Object>> cancelMarriageBooking(@Body RequestBody request);

    @POST("product-upcoming-booking-list")
    Call<GenericResponseModel<ProductBookingResponseModel>> getUpcomingProductBookingList(@Body RequestBody request);


    @POST("product-booking-detail")
    Call<GenericResponseModel<ProductBookingDetailResponseModel>> getProductBookingDetail(@Body RequestBody request);

    @POST("salon-products")
    Call<GenericResponseModel<ProductListResponseModel>> getSalonProductList(@Body RequestBody request);

    @POST("product-past-booking-list")
    Call<GenericResponseModel<ProductBookingResponseModel>> getPastBookingProductList(@Body RequestBody request);

    @POST("reserve-product")
    Call<GenericResponseModel<ReserveProductResponseModel>> doProductReservation(@Body RequestBody request);

    @POST("upcoming-general-booking")
    Call<GenericResponseModel<GeneralBookingListResponseModel>> getGeneralBookingUpcomingList(@Body RequestBody request);

    @POST("past-general-booking")
    Call<GenericResponseModel<GeneralBookingListResponseModel>> getGeneralBookingPastList(@Body RequestBody request);

    @POST("general-booking-detail")
    Call<GenericResponseModel<GeneralBookingDetailResponseModel>> getGeneralBookingDetail(@Body RequestBody request);

    @POST("my-deals")
    Call<GenericResponseModel<MyDealsResponseModel>> getMyDealList(@Body RequestBody request);

}
