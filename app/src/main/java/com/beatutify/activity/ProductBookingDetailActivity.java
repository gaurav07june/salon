package com.beatutify.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;

import android.view.View;

import android.view.animation.AlphaAnimation;


import com.beatutify.R;
import com.beatutify.databinding.ActivityProductBookingDetailBinding;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.ProductBookingDetailResponseModel;
import com.beatutify.model.ProductBookings;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;

public class ProductBookingDetailActivity extends BaseActivity {
    ActivityProductBookingDetailBinding binding;
    private final int PRODUCT_BOOKING_DETAIL_REQ_CODE = 601;
    private BookingListRequestModel bookingListRequestModel;
    private int bookingId;
    private Typeface typeface;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_booking_detail);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bookingListRequestModel = new BookingListRequestModel();
    }
    @Override
    public void getExtras() {
        Intent intent = getIntent();
        bookingId = intent.getIntExtra(AppConstants.EXTRAS.BOOKING_ID,0);
    }
    @Override
    public void initViews() {
    }
    @Override
    public void setViews() {
        typeface = Typeface.createFromAsset(this.getAssets(), "font/oswald_regular.ttf");
        binding.appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.setTitle("KINKI CROWN NATURALS");
                    binding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    binding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    isShow = true;
                } else if(isShow) {
                    binding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
        if (bookingId != 0){
            // populate the request model
            bookingListRequestModel.setToken(AppGlobalData.getInstance().getToken());
            bookingListRequestModel.setBookingId(bookingId);
        }

        checkInternetAndHitApi(PRODUCT_BOOKING_DETAIL_REQ_CODE);
    }
    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case PRODUCT_BOOKING_DETAIL_REQ_CODE:
                if (model != null){
                    GenericResponseModel<ProductBookingDetailResponseModel> responseModel = (GenericResponseModel<ProductBookingDetailResponseModel>) model;
                    if (responseModel.getData().getProductBookings() != null){
                        setProductDetailView(responseModel.getData().getProductBookings());
                    }


                }
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
            case PRODUCT_BOOKING_DETAIL_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getProductBookingDetail(bookingListRequestModel,
                            new APICallback<ProductBookingDetailResponseModel>(PRODUCT_BOOKING_DETAIL_REQ_CODE));
                }
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void setProductDetailView(ProductBookings productBookingDetail){


    }



    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

}
