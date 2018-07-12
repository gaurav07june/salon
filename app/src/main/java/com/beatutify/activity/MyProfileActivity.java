package com.beatutify.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.beatutify.R;
import com.beatutify.databinding.ActivityMyProfileBinding;
import com.beatutify.model.GenericResponseModel;


public class MyProfileActivity extends BaseActivity {
    ActivityMyProfileBinding binding;

    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile);
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        setSupportActionBar(binding.myProfileToolbar);
        getSupportActionBar().setTitle(R.string.my_profile);
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
