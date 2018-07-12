package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.adapters.ServiceListAdapter;
import com.beatutify.customviews.AppSearchViewLight;
import com.beatutify.databinding.ActivityServiceSelectBinding;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.Salon;
import com.beatutify.model.ServiceCategory;
import com.beatutify.model.ServiceListRequestModel;
import com.beatutify.model.ServiceListResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;


public class ServiceSelectActivity extends BaseActivity implements AppSearchViewLight.OnSearchQueryListener, View.OnClickListener {

    private ActivityServiceSelectBinding binding;
    private ServiceCategory serviceCategory;
    private Salon salon;
    private ServiceListAdapter serviceListAdapter;
    private static final int SERVICE_LIST_REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        if (serviceCategory == null || salon == null) return;
        getSupportActionBar().setTitle(serviceCategory.getServiceCatName().toUpperCase());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        serviceListAdapter = new ServiceListAdapter(this);
        binding.serviceListView.setAdapter(serviceListAdapter);
        binding.serviceListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.serviceSelectSearchBar.setOnSearchQueryListener(this);
        binding.btnNext.setOnClickListener(this);
        checkInternetAndHitApi(SERVICE_LIST_REQ_CODE);
    }

    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_select);

    }

    @Override
    public void getExtras() {
        if (getIntent().getExtras() != null) {

            serviceCategory = (ServiceCategory) getIntent().getExtras().getSerializable(AppConstants.EXTRAS.SERVICE_CATEGORY_DATA);
            salon = (Salon) getIntent().getExtras().getSerializable(AppConstants.EXTRAS.SALON_DATA);
        }
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {

        switch (request_code) {
            case SERVICE_LIST_REQ_CODE:
                if (model.getData() instanceof ServiceListResponseModel) {
                    serviceListAdapter.setServiceList(((ServiceListResponseModel) model.getData()).getServiceList())
                    ;
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
        switch (request) {
            case SERVICE_LIST_REQ_CODE:
                ServiceListRequestModel requestModel = new ServiceListRequestModel();
                requestModel.setToken(AppGlobalData.getInstance().getToken());
                requestModel.setSalonId(salon.getSalon_id());
                requestModel.setServiceCatId(serviceCategory.getServiceCatId());
                WebAPIHelper.getInstance().getServiceList(requestModel, new APICallback<ServiceListResponseModel>(SERVICE_LIST_REQ_CODE));
                break;
        }
    }

    @Override
    public void onSearchQuery(String query) {
    }

    @Override
    public void onSearchQueryChange(String query) {
        serviceListAdapter.filter(query);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:

                if (isValid()) {

                    Intent intent = new Intent(this, EmployeeSelectActivity.class);

                    intent.putExtra(AppConstants.EXTRAS.SALON_DATA, salon);
                    intent.putExtra(AppConstants.EXTRAS.SELECTED_SERVICES, serviceListAdapter.getServiceList());
                    intent.putExtra(AppConstants.EXTRAS.SERVICE_CATEGORY_DATA, serviceCategory);
                    startActivity(intent);
                }


                break;
        }
    }

    public boolean isValid() {

        boolean isValid = serviceListAdapter.getSelectedServices().size() > 0;

        if (!isValid) {
            Toast.makeText(this, R.string.select_service_error, Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
