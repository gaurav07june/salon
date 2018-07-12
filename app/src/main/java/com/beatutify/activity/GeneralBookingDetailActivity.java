package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.beatutify.R;
import com.beatutify.adapters.BookedServiceListAdapter;
import com.beatutify.adapters.ServiceDetailListAdapter;
import com.beatutify.databinding.ActivityGeneralBookingDetailBinding;
import com.beatutify.model.BookingListRequestModel;
import com.beatutify.model.GeneralBookingDetailResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.ServiceDetail;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GeneralBookingDetailActivity extends BaseActivity {
    private ActivityGeneralBookingDetailBinding binding;
    private final int GENERAL_BOOKING_DETAIL_REQ_CODE = 401;
    private final int CANCEL_GENERAL_BOOKING_REQ_CODE = 402;
    private BookingListRequestModel bookingListRequestModel;
    private ArrayList<ServiceDetail> serviceDetailList;
    private BookedServiceListAdapter bookedServiceListAdapter;

    private ServiceDetailListAdapter serviceDetailListAdapter;
    int bookingId, reqFromCode;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_general_booking_detail);
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
        if (bookingId != 0){
            bookingListRequestModel.setBookingId(bookingId);
        }
        bookingListRequestModel.setToken(AppGlobalData.getInstance().getToken());
        bookingListRequestModel.setCustomerId(AppGlobalData.getInstance().getLoggedInCustomer().getId());
    }

    @Override
    public void setViews() {
        checkInternetAndHitApi(GENERAL_BOOKING_DETAIL_REQ_CODE);
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        if (model != null){
            Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
            GenericResponseModel<GeneralBookingDetailResponseModel> responseModel = (GenericResponseModel<GeneralBookingDetailResponseModel>)model;
            if (responseModel.getData() != null){
                ImageLoader.getInstance().displayImage(responseModel.getData().getSalonLogoUrl(),
                        binding.layoutGeneralBookingDetailTopContent.imgSalonLogo);
                binding.layoutGeneralBookingDetailTopContent.txtSalonName.setText(responseModel.getData().getSalonName());
                DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                try{
                    date = inputFormatter.parse(responseModel.getData().getBookingDate());
                }catch (Exception e){
                }
                DateFormat outputFormatter = new SimpleDateFormat("dd MMM,yyyy");
                String output = outputFormatter.format(date);
                binding.layoutGeneralBookingDetailTopContent.txtPickupDate.setText(output);
                binding.layoutContentGeneralBookingDetail.txtServiceCategoryName.setText(responseModel.getData().getServiceLists().get(0).getCategoryName());
                serviceDetailList= (ArrayList<ServiceDetail>) responseModel.getData().getServiceLists();
                if (serviceDetailList != null || serviceDetailList.size() > 0){
                    bookedServiceListAdapter = new BookedServiceListAdapter(this, responseModel.getData());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.layoutContentGeneralBookingDetail.recyclerViewServiceDetail.setLayoutManager(mLayoutManager);
                    binding.layoutContentGeneralBookingDetail.recyclerViewServiceDetail.setItemAnimator(new DefaultItemAnimator());
                    binding.layoutContentGeneralBookingDetail.recyclerViewServiceDetail.setAdapter(bookedServiceListAdapter);
                }
            }
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
            case GENERAL_BOOKING_DETAIL_REQ_CODE:
                if (bookingListRequestModel != null){
                    WebAPIHelper.getInstance().getGeneralBookingDetail(bookingListRequestModel,
                            new APICallback<GeneralBookingDetailResponseModel>(GENERAL_BOOKING_DETAIL_REQ_CODE));
                }
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
