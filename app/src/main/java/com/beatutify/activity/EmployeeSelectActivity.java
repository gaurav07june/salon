package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.beatutify.R;
import com.beatutify.adapters.EmployeeListAdapter;
import com.beatutify.customviews.AppSearchViewLight;
import com.beatutify.databinding.ActivityEmployeeSelectBinding;
import com.beatutify.model.Employee;
import com.beatutify.model.EmployeeListRequestModel;
import com.beatutify.model.EmployeeListResponseModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.Salon;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;


public class EmployeeSelectActivity extends BaseActivity implements View.OnClickListener,AppSearchViewLight.OnSearchQueryListener {
    private ActivityEmployeeSelectBinding binding;
    private Bundle extras = null;
    private Salon salon = null;
    private static final int EMPLOYEE_LIST_REQ_CODE = 101;
    private EmployeeListAdapter employeeListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {

        getSupportActionBar().setTitle(R.string.select_employee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.btnNext.setOnClickListener(this);
        checkInternetAndHitApi(EMPLOYEE_LIST_REQ_CODE);
        employeeListAdapter=new EmployeeListAdapter();
        binding.employeeListView.setAdapter(employeeListAdapter);
        binding.employeeListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.employeeSelectSearchBar.setOnSearchQueryListener(this);
    }

    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_select);
    }

    @Override
    public void getExtras() {
        extras = getIntent().getExtras();
        salon = (Salon) extras.getSerializable(AppConstants.EXTRAS.SALON_DATA);
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
    switch (request_code)
    {
        case EMPLOYEE_LIST_REQ_CODE:
            if (model.getData()!=null && model.getData() instanceof EmployeeListResponseModel)
            {
                employeeListAdapter.setEmployeeList(((EmployeeListResponseModel) model.getData()).getEmployeeList());
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
        switch (request)
        {
            case EMPLOYEE_LIST_REQ_CODE:
                if (salon!=null)
                {
                    EmployeeListRequestModel employeeListRequestModel = new EmployeeListRequestModel();
                    employeeListRequestModel.setSalonId(salon.getSalon_id());
                    employeeListRequestModel.setToken(AppGlobalData.getInstance().getToken());
                    WebAPIHelper.getInstance().getEmployeeList(employeeListRequestModel,new APICallback<EmployeeListResponseModel>(EMPLOYEE_LIST_REQ_CODE));

                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                Employee selectedEmployee = employeeListAdapter.getSelectedEmployee();
                if (selectedEmployee!=null)
                {
                    extras.putSerializable(AppConstants.EXTRAS.SELECTED_EMPLOYEE,selectedEmployee);

                    Intent intent = new Intent(this,DatePickerActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onSearchQuery(String query) {

    }

    @Override
    public void onSearchQueryChange(String query) {
    employeeListAdapter.filter(query);
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
