package com.beatutify.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.beatutify.R;
import com.beatutify.databinding.ActivityChangePasswordBinding;
import com.beatutify.model.GenericResponseModel;


public class ChangePasswordActivity extends BaseActivity {
    private ActivityChangePasswordBinding binding;
    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password);
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        getSupportActionBar().setTitle(R.string.change_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:onBackPressed();break;
        }
        return super.onOptionsItemSelected(item);

    }
}
