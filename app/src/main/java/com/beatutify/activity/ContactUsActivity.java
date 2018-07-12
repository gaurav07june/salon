package com.beatutify.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.beatutify.R;
import com.beatutify.model.GenericResponseModel;


public class ContactUsActivity extends BaseActivity {

    @Override
    public void setContentView() {

        setContentView(R.layout.activity_contact_us);
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
