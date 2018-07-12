package com.beatutify.retrofit;

import com.beatutify.model.CheckSocialIdRequestModel;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.CityListResponseModel;
import com.beatutify.model.EmployeeListRequestModel;
import com.beatutify.model.EmployeeListResponseModel;
import com.beatutify.model.EmployeeWorkingDaysRequestModel;
import com.beatutify.model.EmployeeWorkingDaysResponseModel;
import com.beatutify.model.ForgotPasswordRequestModel;
import com.beatutify.model.GeneralBookingDetailResponseModel;
import com.beatutify.model.GeneralBookingListResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.LoginRegistrationResponseModel;
import com.beatutify.model.LoginRequestModel;
import com.beatutify.model.MarriageBookingDetailResponseModel;
import com.beatutify.model.MarriageBookingListResponseModel;
import com.beatutify.model.MarriageBookingRequestModel;
import com.beatutify.model.MyDealsResponseModel;
import com.beatutify.model.ProductBookingDetailResponseModel;
import com.beatutify.model.ProductBookingResponseModel;
import com.beatutify.model.ProductListRequestModel;
import com.beatutify.model.ProductListResponseModel;
import com.beatutify.model.RegistrationRequestModel;
import com.beatutify.model.ReserveProductRequestModel;
import com.beatutify.model.ReserveProductResponseModel;
import com.beatutify.model.ResetPasswordRequestModel;
import com.beatutify.model.SalonDetailRequestModel;
import com.beatutify.model.SalonDetailResponseModel;
import com.beatutify.model.SalonListRequestModel;
import com.beatutify.model.SalonListResponseModel;
import com.beatutify.model.ServiceBookingRequestModel;
import com.beatutify.model.ServiceCatListRequestModel;
import com.beatutify.model.ServiceCatListResponseModel;
import com.beatutify.model.ServiceListRequestModel;
import com.beatutify.model.ServiceListResponseModel;
import com.beatutify.model.TimeSlotReponseModel;
import com.beatutify.model.TimeSlotRequestModel;
import com.beatutify.util.AppConstants;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class WebAPIHelper {
    private static WebAPIHelper ourInstance = new WebAPIHelper();
    public static WebAPIHelper getInstance() {
        return ourInstance;
    }
    private WebAPIHelper() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(AppConstants.RETROFIT.TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(AppConstants.RETROFIT.TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApi = retrofit.create(WebAPI.class);
    }
    WebAPI retrofitApi = null;
    public void doRegistration(RegistrationRequestModel model, Callback<GenericResponseModel<LoginRegistrationResponseModel>> responseCallback) {
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<LoginRegistrationResponseModel>> call = retrofitApi.doRegistration(getRequestBody(new Gson().toJson(model)));
             call.enqueue(responseCallback);
        }
    }
    public void doLogin(LoginRequestModel model, Callback<GenericResponseModel<LoginRegistrationResponseModel>> responseCallback) {
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<LoginRegistrationResponseModel>> call = retrofitApi.doLogin(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void showSalonList(SalonListRequestModel model, Callback<GenericResponseModel<SalonListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<SalonListResponseModel>> call = retrofitApi.showSalonList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void showSalonDetail(SalonDetailRequestModel model, Callback<GenericResponseModel<SalonDetailResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<SalonDetailResponseModel>> call = retrofitApi.showSalonDetail(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void getServiceCatList(ServiceCatListRequestModel model, Callback<GenericResponseModel<ServiceCatListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<ServiceCatListResponseModel>> call = retrofitApi.getServiceCatList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void getServiceList(ServiceListRequestModel model, Callback<GenericResponseModel<ServiceListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<ServiceListResponseModel>> call = retrofitApi.getServiceList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void getEmployeeList(EmployeeListRequestModel model, Callback<GenericResponseModel<EmployeeListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<EmployeeListResponseModel>> call = retrofitApi.getEmployeeList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void getTimeSlots(TimeSlotRequestModel model, Callback<GenericResponseModel<TimeSlotReponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<TimeSlotReponseModel>> call = retrofitApi.getTimeSlots(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public EmployeeWorkingDaysResponseModel getEmployeeWorkingDaysSync(EmployeeWorkingDaysRequestModel model){
        try
        {
            if (retrofitApi != null && model != null) {

                Call<GenericResponseModel<EmployeeWorkingDaysResponseModel>> call = retrofitApi.getEmployeeWorkingDays(getRequestBody(new Gson().toJson(model)));
                Response<GenericResponseModel<EmployeeWorkingDaysResponseModel>> response = call.execute();
                if (response!=null)return response.body().getData();
            }
        }
        catch (Exception e)
        {

        }
            return null;
    }
    public void doMarriageBooking(MarriageBookingRequestModel model, Callback<GenericResponseModel<Object>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<Object>> call = retrofitApi.doMarriageBooking(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void doServiceBooking(ServiceBookingRequestModel model, Callback<GenericResponseModel<Object>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<Object>> call = retrofitApi.doServiceBooking(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void checkSocialId(CheckSocialIdRequestModel model, Callback<GenericResponseModel<Object>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<Object>> call = retrofitApi.checkSocialId(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void doForgotPassword(ForgotPasswordRequestModel model, Callback<GenericResponseModel<Object>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<Object>> call = retrofitApi.doForgotPassword(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void doResetPassword(ResetPasswordRequestModel model, Callback<GenericResponseModel<Object>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<Object>> call = retrofitApi.doResetPassword(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void getCityList(Callback<GenericResponseModel<CityListResponseModel>> responseCallback){
        if (retrofitApi != null) {
            Call<GenericResponseModel<CityListResponseModel>> call = retrofitApi.getCityList();
            call.enqueue(responseCallback);
        }
    }

    public void getUpcomingMarriageBookingList(BookingListRequestModel model, Callback<GenericResponseModel<MarriageBookingListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<MarriageBookingListResponseModel>> call = retrofitApi.getUpcomingMarriageBookingList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getPastMarriageBookingList(BookingListRequestModel model, Callback<GenericResponseModel<MarriageBookingListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<MarriageBookingListResponseModel>> call = retrofitApi.getPastMarriageBookingList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getMarriageBookingDetail(BookingListRequestModel model, Callback<GenericResponseModel<MarriageBookingDetailResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<MarriageBookingDetailResponseModel>> call = retrofitApi.getMarriageBookingDetail(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void cancelMarriageBooking(BookingListRequestModel model, Callback<GenericResponseModel<Object>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<Object>> call = retrofitApi.cancelMarriageBooking(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void getUpcomingProductBookingList(BookingListRequestModel model, Callback<GenericResponseModel<ProductBookingResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {

            Call<GenericResponseModel<ProductBookingResponseModel>> call = retrofitApi.getUpcomingProductBookingList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void getProductBookingDetail(BookingListRequestModel model, Callback<GenericResponseModel<ProductBookingDetailResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<ProductBookingDetailResponseModel>> call = retrofitApi.getProductBookingDetail(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getSalonProductList(ProductListRequestModel model, Callback<GenericResponseModel<ProductListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<ProductListResponseModel>> call = retrofitApi.getSalonProductList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getPastBookingProductList(BookingListRequestModel model, Callback<GenericResponseModel<ProductBookingResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<ProductBookingResponseModel>> call = retrofitApi.getPastBookingProductList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }
    public void doProductReservation(ReserveProductRequestModel model, Callback<GenericResponseModel<ReserveProductResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<ReserveProductResponseModel>> call = retrofitApi.doProductReservation(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getGeneralBookingUpcomingList(BookingListRequestModel model, Callback<GenericResponseModel<GeneralBookingListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<GeneralBookingListResponseModel>> call = retrofitApi.getGeneralBookingUpcomingList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getGeneralBookingPastList(BookingListRequestModel model, Callback<GenericResponseModel<GeneralBookingListResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<GeneralBookingListResponseModel>> call = retrofitApi.getGeneralBookingPastList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getGeneralBookingDetail(BookingListRequestModel model, Callback<GenericResponseModel<GeneralBookingDetailResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<GeneralBookingDetailResponseModel>> call = retrofitApi.getGeneralBookingDetail(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }

    public void getMyDealList(BookingListRequestModel model, Callback<GenericResponseModel<MyDealsResponseModel>> responseCallback){
        if (retrofitApi != null && model != null) {
            Call<GenericResponseModel<MyDealsResponseModel>> call = retrofitApi.getMyDealList(getRequestBody(new Gson().toJson(model)));
            call.enqueue(responseCallback);
        }
    }



    private RequestBody getRequestBody(String json) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }
}
