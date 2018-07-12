package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.beatutify.R;
import com.beatutify.adapters.MarriageBookingListAdapter;
import com.beatutify.databinding.ActivityGeneralBookingListBinding;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.GeneralBookingListResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.MarriageBookingDetail;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;


import java.util.ArrayList;

public class GeneralBookingListActivity extends BaseActivity implements View.OnClickListener, MarriageBookingListAdapter.MarriageBookingListListener{
    private ActivityGeneralBookingListBinding binding;
    private final int UPCOMING_GENERAL_BOKKING_LIST_REQ_CODE = 601;
    private final int PAST_GENERAL_BOKKING_LIST_REQ_CODE = 602;
    private BookingListRequestModel bookingListRequestModel;
    private ArrayList<MarriageBookingDetail> generalBookingDetailList;
    private MarriageBookingListAdapter generalBookingListAdapter;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_general_booking_list);
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {
        //init toolbar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.headerTitle.setText(getResources().getString(R.string.general_booking));

        bookingListRequestModel = new BookingListRequestModel();
        bookingListRequestModel.setToken(AppGlobalData.getInstance().getToken());
        bookingListRequestModel.setCustomerId(AppGlobalData.getInstance().getLoggedInCustomer().getId());
    }
    @Override
    public void setViews() {
        binding.layoutContentGeneralBookingList.btnGeneralBookUpcomming.setOnClickListener(this);
        binding.layoutContentGeneralBookingList.btnGeneralBookPast.setOnClickListener(this);
        checkInternetAndHitApi(UPCOMING_GENERAL_BOKKING_LIST_REQ_CODE);
    }
    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        if (model != null){
            GenericResponseModel<GeneralBookingListResponseModel> responseModel = (GenericResponseModel<GeneralBookingListResponseModel>) model;

            switch (request_code){
                case UPCOMING_GENERAL_BOKKING_LIST_REQ_CODE:
                    if (responseModel.getData().getUpcomingBookingLists() != null){
                        generalBookingDetailList = (ArrayList<MarriageBookingDetail>) responseModel.getData().getUpcomingBookingLists();
                        if (generalBookingDetailList.size() == 0){
                            binding.layoutContentGeneralBookingList.txtNoBooking.setVisibility(View.VISIBLE);
                        }else{
                            binding.layoutContentGeneralBookingList.txtNoBooking.setVisibility(View.GONE);
                        }
                        generalBookingListAdapter = new MarriageBookingListAdapter(this, generalBookingDetailList, this, UPCOMING_GENERAL_BOKKING_LIST_REQ_CODE);
                    }
                    break;
                case PAST_GENERAL_BOKKING_LIST_REQ_CODE:
                    if (responseModel.getData().getGeneralBookingLists() != null){
                        generalBookingDetailList = (ArrayList<MarriageBookingDetail>) responseModel.getData().getGeneralBookingLists();
                        if (generalBookingDetailList.size() == 0){
                            binding.layoutContentGeneralBookingList.txtNoBooking.setVisibility(View.VISIBLE);
                        }else{
                            binding.layoutContentGeneralBookingList.txtNoBooking.setVisibility(View.GONE);
                        }
                        generalBookingListAdapter = new MarriageBookingListAdapter(this, generalBookingDetailList, this, PAST_GENERAL_BOKKING_LIST_REQ_CODE);
                    }
                    break;
            }
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            binding.layoutContentGeneralBookingList.recyclerViewGeneralBookingList.setLayoutManager(mLayoutManager);
            binding.layoutContentGeneralBookingList.recyclerViewGeneralBookingList.setItemAnimator(new DefaultItemAnimator());

            binding.layoutContentGeneralBookingList.recyclerViewGeneralBookingList.setAdapter(generalBookingListAdapter);

        }


    }
    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
    }
    @Override
    public void onApiException(Throwable t, int request_code) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }
    @Override
    public void hitApi(int request) {
        switch (request){
            case UPCOMING_GENERAL_BOKKING_LIST_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getGeneralBookingUpcomingList(bookingListRequestModel,
                            new APICallback<GeneralBookingListResponseModel>(UPCOMING_GENERAL_BOKKING_LIST_REQ_CODE));
                }
                break;
            case PAST_GENERAL_BOKKING_LIST_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getGeneralBookingPastList(bookingListRequestModel,
                            new APICallback<GeneralBookingListResponseModel>(PAST_GENERAL_BOKKING_LIST_REQ_CODE));
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
            case R.id.btn_general_book_upcomming:
                toggleButtonClick(R.id.btn_general_book_upcomming);
                break;
            case R.id.btn_general_book_past:
                toggleButtonClick(R.id.btn_general_book_past);
                break;
        }
    }
    private void toggleButtonClick(int btnId){
        switch (btnId){
            case R.id.btn_general_book_upcomming:
                binding.layoutContentGeneralBookingList.btnGeneralBookUpcomming.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.button_left_corner_round_brown, null));
                binding.layoutContentGeneralBookingList.btnGeneralBookPast.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.button_right_corner_round_white, null));
                binding.layoutContentGeneralBookingList.btnGeneralBookUpcomming.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.layoutContentGeneralBookingList.btnGeneralBookPast.setTextColor(getResources().getColor(R.color.beutify_light_white));
                checkInternetAndHitApi(UPCOMING_GENERAL_BOKKING_LIST_REQ_CODE);
                break;
            case R.id.btn_general_book_past:
                binding.layoutContentGeneralBookingList.btnGeneralBookUpcomming.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_left_corner_round_white));
                binding.layoutContentGeneralBookingList.btnGeneralBookPast.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_right_corner_round_brown));
                binding.layoutContentGeneralBookingList.btnGeneralBookUpcomming.setTextColor(getResources().getColor(R.color.beutify_light_white));
                binding.layoutContentGeneralBookingList.btnGeneralBookPast.setTextColor(getResources().getColor(R.color.colorWhite));
                checkInternetAndHitApi(PAST_GENERAL_BOKKING_LIST_REQ_CODE);
                break;
        }
    }

    @Override
    public void onViewBooking(View v, int position, View salonImage) {

        Intent intent = new Intent(this, GeneralBookingDetailActivity.class);
        if (v.getId() == R.id.btn_view_booking){
            intent.putExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, AppConstants.CONSTANTS.UPCOMING_GENERAL_BOOKING_REQ);
        }
        else{
            intent.putExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, AppConstants.CONSTANTS.PAST_GENERAL_BOOKING_REQ);
        }
        intent.putExtra(AppConstants.EXTRAS.BOOKING_ID, generalBookingDetailList.get(position).getBookingId());
        startActivity(intent);
    }
}
