package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.beatutify.R;
import com.beatutify.adapters.ProductBookingListAdapter;
import com.beatutify.adapters.ProductBookingListAdapter1;
import com.beatutify.databinding.ActivityProductBookingListBinding;

import com.beatutify.model.BookedProductDetail;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.ProductBookingResponseModel;
import com.beatutify.model.ProductBookings;
import com.beatutify.model.Products;
import com.beatutify.model.TestModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.BeautifyDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class ProductBookingListActivity extends BaseActivity implements View.OnClickListener,
        ProductBookingListAdapter.ProductBookingListListener{
    private ActivityProductBookingListBinding binding;
    private final int UPCOMING_PRODUCT_BOOKING_LIST_REQ_CODE = 501;
    private final int PAST_PRODUCT_BOOKING_LIST_REQ_CODE = 502;
    private BookingListRequestModel bookingListRequestModel;
    private ArrayList<BookedProductDetail> bookedProductList;
    private ProductBookingListAdapter1 productBookingListAdapter1;
    LinkedHashMap<String, List<BookedProductDetail>> resultHash;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_booking_list);
        bookingListRequestModel = new BookingListRequestModel();
        // populate the request model
        bookingListRequestModel.setToken(AppGlobalData.getInstance().getToken());
        bookingListRequestModel.setCustomerId(AppGlobalData.getInstance().getLoggedInCustomer().getId());

    }
    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        resultHash = new LinkedHashMap<>();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductBookingListActivity.super.onBackPressed();
            }
        });
        binding.headerTitle.setText(getResources().getString(R.string.product_booking));
        binding.layoutContentProductBookingList.btnProductBookUpcomming.setOnClickListener(this);
        binding.layoutContentProductBookingList.btnProductBookPast.setOnClickListener(this);

        checkInternetAndHitApi(UPCOMING_PRODUCT_BOOKING_LIST_REQ_CODE);
    }
    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        if (model != null ){
            GenericResponseModel<ProductBookingResponseModel> responseModel = (GenericResponseModel<ProductBookingResponseModel>) model;
            if (responseModel.getData().getProductBookings() != null){
                //Toast.makeText(this, responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                bookedProductList = new ArrayList<>();
                bookedProductList = (ArrayList<BookedProductDetail>) responseModel.getData().getProductBookings();
                // transform the list to show data grouped as salon name and pickup date
                if (bookedProductList.size() == 0){
                    binding.layoutContentProductBookingList.txtNoBooking.setVisibility(View.VISIBLE);
                    resultHash.clear();
                }else{
                    binding.layoutContentProductBookingList.txtNoBooking.setVisibility(View.GONE);
                    resultHash = transformList(bookedProductList);

                }

                if (request_code == UPCOMING_PRODUCT_BOOKING_LIST_REQ_CODE){
                    productBookingListAdapter1 = new ProductBookingListAdapter1(this, resultHash, UPCOMING_PRODUCT_BOOKING_LIST_REQ_CODE);
                }else{
                    productBookingListAdapter1 = new ProductBookingListAdapter1(this, resultHash, PAST_PRODUCT_BOOKING_LIST_REQ_CODE);
                }
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                binding.layoutContentProductBookingList.recyclerViewProductBookingList.setLayoutManager(mLayoutManager);
                binding.layoutContentProductBookingList.recyclerViewProductBookingList.setItemAnimator(new DefaultItemAnimator());
                binding.layoutContentProductBookingList.recyclerViewProductBookingList.setAdapter(productBookingListAdapter1);
            }
        }
    }

    private LinkedHashMap<String, List<BookedProductDetail>> transformList(ArrayList<BookedProductDetail> bookedProductList){
        LinkedHashMap<String, List<BookedProductDetail>> resultHashmap = new LinkedHashMap<>();
        // create a list to group the products
        ArrayList<BookedProductDetail> groupedProductList = new ArrayList<BookedProductDetail>();
        String uniqueKey = bookedProductList.get(0).getPickupDate()+"-"+bookedProductList.get(0).getSalonId();
        for (BookedProductDetail bookedProductDetail : bookedProductList){
            String productUniqueKey = bookedProductDetail.getPickupDate()+"-"+bookedProductDetail.getSalonId();
            // create a product
            if (productUniqueKey.equals(uniqueKey)){
                // in same group of salon
                groupedProductList.add(bookedProductDetail);
            }else{
                // add list to salonGroup
                resultHashmap.put(uniqueKey, groupedProductList);
                // create a new list
                groupedProductList = new ArrayList<BookedProductDetail>();
                // add the product to the list
                groupedProductList.add(bookedProductDetail);
                // change the unique key
                uniqueKey = productUniqueKey;
            }
        }
        if (groupedProductList.size() > 0){
            // there are items that are not grouped yet
            resultHashmap.put(uniqueKey, groupedProductList);
        }
        return resultHashmap;
    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {

        if (model != null && model.getError().getCode() == AppConstants.API.TOKEN_EXPIRED_CODE){
            //relogin and proceed
            Toast.makeText(this, "token expired", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApiException(Throwable t, int request_code) {
        binding.layoutContentProductBookingList.txtNoBooking.setVisibility(View.VISIBLE);
    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case UPCOMING_PRODUCT_BOOKING_LIST_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getUpcomingProductBookingList(bookingListRequestModel, new APICallback<ProductBookingResponseModel>(UPCOMING_PRODUCT_BOOKING_LIST_REQ_CODE));
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_product_book_upcomming:
                toggleButtonClick(R.id.btn_product_book_upcomming);
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getUpcomingProductBookingList(bookingListRequestModel, new APICallback<ProductBookingResponseModel>(UPCOMING_PRODUCT_BOOKING_LIST_REQ_CODE));
                }
                break;
            case R.id.btn_product_book_past:
                toggleButtonClick(R.id.btn_product_book_past);
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getPastBookingProductList(bookingListRequestModel, new APICallback<ProductBookingResponseModel>(PAST_PRODUCT_BOOKING_LIST_REQ_CODE));
                }
                break;
        }
    }
    private void toggleButtonClick(int btnId){
        switch (btnId){
            case R.id.btn_product_book_upcomming:
                binding.layoutContentProductBookingList.btnProductBookUpcomming.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.button_left_corner_round_brown, null));
                binding.layoutContentProductBookingList.btnProductBookPast.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.button_right_corner_round_white, null));
                binding.layoutContentProductBookingList.btnProductBookUpcomming.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
                binding.layoutContentProductBookingList.btnProductBookPast.setTextColor(ResourcesCompat.getColor(getResources(), R.color.beutify_light_white, null));
                break;
            case R.id.btn_product_book_past:
                binding.layoutContentProductBookingList.btnProductBookUpcomming.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_left_corner_round_white));
                binding.layoutContentProductBookingList.btnProductBookPast.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.button_right_corner_round_brown));
                binding.layoutContentProductBookingList.btnProductBookUpcomming.setTextColor(ContextCompat.getColor(this, R.color.beutify_light_white));
                binding.layoutContentProductBookingList.btnProductBookPast.setTextColor(ContextCompat.getColor(this,R.color.colorWhite));
                break;
        }
    }

    @Override
    public void onViewBooking(View v, int position) {

    }
}
