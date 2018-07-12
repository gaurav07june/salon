package com.beatutify.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.beatutify.R;
import com.beatutify.adapters.BookedBridesmaidAdapter;
import com.beatutify.adapters.BridesmaidAdapter;
import com.beatutify.databinding.ActivityMarriageBookingDetailBinding;
import com.beatutify.databinding.BridesMaidRowBinding;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.BridesMaid;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.MarriageBookingDetailResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.BeautifyDividerItemDecoration;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MarriageBookingDetailActivity extends BaseActivity implements View.OnClickListener{
    ActivityMarriageBookingDetailBinding binding;
    private final int MARRIAGE_BOOKING_LIST_DETAIL_REQ_CODE = 401;
    private final int CANCEL_MARRIAGE_BOOKING_REQ_CODE = 402;
    private BookingListRequestModel bookingListRequestModel;
    int bookingId, reqFromCode;
    private ArrayList<BridesMaid> bridesMaidsList;
    private BookedBridesmaidAdapter bookedBridesmaidAdapter;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_marriage_booking_detail);
        bookingListRequestModel = new BookingListRequestModel();

    }
    @Override
    public void getExtras() {

        Intent intent = getIntent();
        bookingId = intent.getIntExtra(AppConstants.EXTRAS.BOOKING_ID, 0);
        reqFromCode = intent.getIntExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, 0);

    }
    @Override
    public void initViews() {
    }
    @Override
    public void setViews() {

        //binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarriageBookingDetailActivity.super.onBackPressed();
            }
        });

        if (bookingId != 0){
            bookingListRequestModel.setBookingId(bookingId);
        }
        bookingListRequestModel.setToken(AppGlobalData.getInstance().getToken());
        if (reqFromCode != 0){
            if (reqFromCode == AppConstants.CONSTANTS.UPCOMING_MARRIAGE_BOOKING_REQ){
                binding.btnCancelBooking.setVisibility(View.VISIBLE);
            }else if (reqFromCode  == AppConstants.CONSTANTS.PAST_MARRIAGE_BOOKING_REQ){
                binding.btnCancelBooking.setVisibility(View.GONE);
            }
        }
        binding.btnCancelBooking.setOnClickListener(this);
        checkInternetAndHitApi(MARRIAGE_BOOKING_LIST_DETAIL_REQ_CODE);
    }
    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case MARRIAGE_BOOKING_LIST_DETAIL_REQ_CODE:
                if (model != null){
                    GenericResponseModel<MarriageBookingDetailResponseModel> responseModel = (GenericResponseModel<MarriageBookingDetailResponseModel>) model;
                    if (responseModel.getData() != null){
                        setMarriageBookingDetailViews(responseModel.getData());
                    }
                }
                break;
            case CANCEL_MARRIAGE_BOOKING_REQ_CODE:
                if (model != null)
                    Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                break;
        }
    }
    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case MARRIAGE_BOOKING_LIST_DETAIL_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getMarriageBookingDetail(bookingListRequestModel, new APICallback<MarriageBookingDetailResponseModel>(MARRIAGE_BOOKING_LIST_DETAIL_REQ_CODE));
                }
                break;
            case CANCEL_MARRIAGE_BOOKING_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().cancelMarriageBooking(bookingListRequestModel, new APICallback<Object>(CANCEL_MARRIAGE_BOOKING_REQ_CODE));
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setMarriageBookingDetailViews(MarriageBookingDetailResponseModel marriageBookingDetail){
        ImageLoader.getInstance().displayImage(marriageBookingDetail.getSalonLogoUrl(),
                binding.layoutMarriageBookingDetailTopContent.imgSalonLogo);
        binding.layoutMarriageBookingDetailTopContent.txtSalonName.setText(marriageBookingDetail.getSalonName());
        try{
            DateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = inputFormatter1.parse(marriageBookingDetail.getBookingDate());
            DateFormat outputFormatter1 = new SimpleDateFormat("dd MMM,yyyy");
            String output1 = outputFormatter1.format(date1); //
            binding.layoutMarriageBookingDetailTopContent.txtBookingDate
                    .setText(output1);
        }catch (Exception e){

        }

        if (marriageBookingDetail.getTimeSlot() == 1){
            binding.layoutMarriageBookingDetailTopContent.txtBookingSlot.setText(getResources().getString(R.string.morning));
        }else{
            binding.layoutMarriageBookingDetailTopContent.txtBookingSlot.setText(getResources().getString(R.string.evening));
        }
        bridesMaidsList = new ArrayList<BridesMaid>();
        bridesMaidsList =(ArrayList<BridesMaid>) marriageBookingDetail.getBridesMaid();

        bookedBridesmaidAdapter = new BookedBridesmaidAdapter(this, bridesMaidsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.layoutContentMarriageBookingDetail.recyclerViewBridesmaidList.setLayoutManager(mLayoutManager);
       // binding.layoutContentMarriageBookingDetail.recyclerViewBridesmaidList.addItemDecoration(new BeautifyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, AppCommons.dpToPx(10, this)));
        binding.layoutContentMarriageBookingDetail.recyclerViewBridesmaidList.setItemAnimator(new DefaultItemAnimator());
        binding.layoutContentMarriageBookingDetail.recyclerViewBridesmaidList.setAdapter(bookedBridesmaidAdapter);
        binding.layoutContentMarriageBookingDetail.txtBookingAdditionalDetail.setText(marriageBookingDetail.getAdditionalDetail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel_booking:
                checkInternetAndHitApi(CANCEL_MARRIAGE_BOOKING_REQ_CODE);
                break;
        }
    }
}
