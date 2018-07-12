package com.beatutify.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beatutify.R;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;


public class OrderPlacedActivity extends BaseActivity implements View.OnClickListener{

    private int REQUEST_FROM;
    private  String marriageBookingMsg;
    private String reserveProductMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        findViewById(R.id.order_placed_skip_btn).setOnClickListener(this);
        findViewById(R.id.add_more_services_btn).setOnClickListener(this);
    }

    @Override
    public void setContentView() {

        setContentView(R.layout.activity_order_placed);
        if (REQUEST_FROM == AppConstants.CONSTANTS.MARRIAGE_BOOKING_REQ){
            findViewById(R.id.add_more_services_btn).setVisibility(View.GONE);
            TextView txtOrderPlacesMsg = (TextView) findViewById(R.id.txt_order_placed_msg);
            txtOrderPlacesMsg.setText(marriageBookingMsg);
        }

        if (REQUEST_FROM == AppConstants.CONSTANTS.RESERVE_PRODUCT_REQ){
            findViewById(R.id.add_more_services_btn).setVisibility(View.GONE);
            TextView txtOrderPlacesMsg = (TextView) findViewById(R.id.txt_order_placed_msg);
            txtOrderPlacesMsg.setText(reserveProductMsg);
        }
    }

    @Override
    public void getExtras() {
        Intent intent = getIntent();
        REQUEST_FROM = intent.getIntExtra(AppConstants.EXTRAS.REQ_FROM_PAGE, 0);
        marriageBookingMsg = intent.getStringExtra(AppConstants.EXTRAS.MARRIAGE_BOOKING_MSG);

        reserveProductMsg = intent.getStringExtra(AppConstants.EXTRAS.RESERVE_PRODUCT_MSG);
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {

    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {

    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_more_services_btn:gotToHomeScreen();break;
            case R.id.order_placed_skip_btn:gotToHomeScreen();break;
        }
    }

    private void gotToHomeScreen() {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        gotToHomeScreen();
    }
}
