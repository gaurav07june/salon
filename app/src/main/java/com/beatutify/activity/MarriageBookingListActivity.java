package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.adapters.MarriageBookingListAdapter;
import com.beatutify.databinding.ActivityMarriageBookingListBinding;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.MarriageBookingDetail;
import com.beatutify.model.MarriageBookingListResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.BeautifyDividerItemDecoration;

import java.util.ArrayList;


public class MarriageBookingListActivity extends BaseActivity implements View.OnClickListener, MarriageBookingListAdapter.MarriageBookingListListener{
    private ActivityMarriageBookingListBinding binding;
    private final int UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE = 301;
    private final int PAST_MARRIAGE_BOKKING_LIST_REQ_CODE = 302;
    private BookingListRequestModel bookingListRequestModel;
    private ArrayList<MarriageBookingDetail> marriageBookingDetailList;
    private MarriageBookingListAdapter marriageBookingListAdapter;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_marriage_booking_list);

    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
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
        binding.headerTitle.setText(getResources().getString(R.string.marriage_booking));
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/oswald_regular.ttf");
        bookingListRequestModel = new BookingListRequestModel();
        // populate the bookingListRequestModel with customerId and token
        bookingListRequestModel.setCustomerId(AppGlobalData.getInstance().getLoggedInCustomer().getId());
        bookingListRequestModel.setToken(AppGlobalData.getInstance().getToken());
        marriageBookingDetailList = new ArrayList<MarriageBookingDetail>();
        binding.layoutContentMarriageBookList.btnMarriageBookUpcomming.setOnClickListener(this);
        binding.layoutContentMarriageBookList.btnMarriageBookPast.setOnClickListener(this);
        //binding.layoutContentMarriageBookList.layoutMarriageBookingListRow.btnViewBooking.setOnClickListener(this);
        checkInternetAndHitApi(UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (marriageBookingListAdapter != null){
            checkInternetAndHitApi(UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE);
        }

    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        if (model != null ){
            //Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
            GenericResponseModel<MarriageBookingListResponseModel> responseModel = (GenericResponseModel<MarriageBookingListResponseModel>) model;
            if (responseModel.getData().getBookingDetail() != null){

                marriageBookingDetailList =(ArrayList<MarriageBookingDetail>) responseModel.getData().getBookingDetail();
                if (marriageBookingDetailList.size() == 0){
                    binding.layoutContentMarriageBookList.txtNoBooking.setVisibility(View.VISIBLE);
                }else{
                    binding.layoutContentMarriageBookList.txtNoBooking.setVisibility(View.GONE);
                }
                if (request_code == UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE){
                    marriageBookingListAdapter = new MarriageBookingListAdapter(this, marriageBookingDetailList, this, UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE);
                }else{
                    marriageBookingListAdapter = new MarriageBookingListAdapter(this, marriageBookingDetailList, this, PAST_MARRIAGE_BOKKING_LIST_REQ_CODE);
                }

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                binding.layoutContentMarriageBookList.recyclerViewMarriageBookingList.setLayoutManager(mLayoutManager);
                binding.layoutContentMarriageBookList.recyclerViewMarriageBookingList.setItemAnimator(new DefaultItemAnimator());
                binding.layoutContentMarriageBookList.recyclerViewMarriageBookingList.setAdapter(marriageBookingListAdapter);
            }
        }
    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        binding.layoutContentMarriageBookList.txtNoBooking.setVisibility(View.VISIBLE);
    }

    @Override
    public void onApiException(Throwable t, int request_code) {
        binding.layoutContentMarriageBookList.txtNoBooking.setVisibility(View.VISIBLE);
    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getUpcomingMarriageBookingList(bookingListRequestModel, new APICallback<MarriageBookingListResponseModel>(UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE));
                }
                break;
            case PAST_MARRIAGE_BOKKING_LIST_REQ_CODE:
                if (bookingListRequestModel != null){

                    WebAPIHelper.getInstance().getPastMarriageBookingList(bookingListRequestModel, new APICallback<MarriageBookingListResponseModel>(PAST_MARRIAGE_BOKKING_LIST_REQ_CODE));
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void toggleButtonClick(int btnId){
        switch (btnId){
            case R.id.btn_marriage_book_upcomming:
                // showing design
               // binding.layoutContentMarriageBookList.layoutMarriageBookingListRow.cardviewMarriageBookingList.setAlpha(1);
               // binding.layoutContentMarriageBookList.layoutMarriageBookingListRow.btnViewBooking.setVisibility(View.VISIBLE);
                //binding.layoutContentMarriageBookList.layoutMarriageBookingListRow.txtPastBookingStatus.setVisibility(View.GONE);
                // showing design end
                binding.layoutContentMarriageBookList.btnMarriageBookUpcomming.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_left_corner_round_brown));
                binding.layoutContentMarriageBookList.btnMarriageBookPast.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_right_corner_round_white));
                binding.layoutContentMarriageBookList.btnMarriageBookUpcomming.setTextColor(getResources().getColor(R.color.colorWhite));
                binding.layoutContentMarriageBookList.btnMarriageBookPast.setTextColor(getResources().getColor(R.color.beutify_light_white));
                checkInternetAndHitApi(UPCOMING_MARRIAGE_BOKKING_LIST_REQ_CODE);
                break;
            case R.id.btn_marriage_book_past:
                //showing design
               // binding.layoutContentMarriageBookList.layoutMarriageBookingListRow.cardviewMarriageBookingList.setAlpha((float)0.5);
               // binding.layoutContentMarriageBookList.layoutMarriageBookingListRow.btnViewBooking.setVisibility(View.GONE);
               // binding.layoutContentMarriageBookList.layoutMarriageBookingListRow.txtPastBookingStatus.setVisibility(View.VISIBLE);
                //showing design end
                binding.layoutContentMarriageBookList.btnMarriageBookUpcomming.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_left_corner_round_white));
                binding.layoutContentMarriageBookList.btnMarriageBookPast.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_right_corner_round_brown));
                binding.layoutContentMarriageBookList.btnMarriageBookUpcomming.setTextColor(getResources().getColor(R.color.beutify_light_white));
                binding.layoutContentMarriageBookList.btnMarriageBookPast.setTextColor(getResources().getColor(R.color.colorWhite));
                checkInternetAndHitApi(PAST_MARRIAGE_BOKKING_LIST_REQ_CODE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_marriage_book_upcomming:
                toggleButtonClick(R.id.btn_marriage_book_upcomming);
                break;
            case R.id.btn_marriage_book_past:
                toggleButtonClick(R.id.btn_marriage_book_past);
                break;
            case R.id.btn_view_booking:
                startActivity(new Intent(this, MarriageBookingDetailActivity.class));
        }
    }

    @Override
    public void onViewBooking(View v, int position, View salonImageView) {
        Intent intent = new Intent(this, MarriageBookingDetailActivity.class);
        if (v.getId() == R.id.btn_view_booking){
            intent.putExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, AppConstants.CONSTANTS.UPCOMING_MARRIAGE_BOOKING_REQ);
        }
        else{
            intent.putExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, AppConstants.CONSTANTS.PAST_MARRIAGE_BOOKING_REQ);
        }
        intent.putExtra(AppConstants.EXTRAS.BOOKING_ID, marriageBookingDetailList.get(position).getBookingId());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, salonImageView, getString(R.string.salon_transition));
            startActivity(intent, options.toBundle());
        }else{
            startActivity(intent);
        }
    }
}
