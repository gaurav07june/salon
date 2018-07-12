package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;
import com.beatutify.R;
import com.beatutify.adapters.CityListSpinnerAdapter;
import com.beatutify.databinding.ActivitySignupBinding;
import com.beatutify.model.Cities;
import com.beatutify.model.CityListResponseModel;
import com.beatutify.model.Customer;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.RegistrationRequestModel;
import com.beatutify.model.LoginRegistrationResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.FBLoginAdditionalDetailsDialog;

import java.util.ArrayList;

public class SignUpActivity extends BaseActivity implements View.OnClickListener{
    private ActivitySignupBinding binding;
    private RegistrationRequestModel registrationRequest;
    private static final int REGISTRATION_REQ_CODE = 101;
    private static final int CITY_LIST_REQ_CODE = 109;
    private CityListSpinnerAdapter spinnerAdapter;
    private ArrayList<Cities> cityList;
    @Override
    public void initViews() {
    }

    @Override
    public void setViews() {
        binding.txtBack.setOnClickListener(this);
        binding.txtSkip.setOnClickListener(this);
        binding.btnCreate.setOnClickListener(this);
        binding.cityListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                registrationRequest.setCity_id(cityList.get(position).getCityId());
               //Toast.makeText(SignUpActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        checkInternetAndHitApi(CITY_LIST_REQ_CODE);
    }

    @Override
    public void setContentView() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        registrationRequest = new RegistrationRequestModel();
        binding.setRegRequest(registrationRequest);
        cityList = new ArrayList<Cities>();
    }

    @Override
    public void getExtras() {
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code){
            case REGISTRATION_REQ_CODE:
                try {
                    GenericResponseModel<LoginRegistrationResponseModel> response = (GenericResponseModel<LoginRegistrationResponseModel>) model;
                    if (response.getData().getCustomer() != null && response.getData().getToken() != null) {
                        AppGlobalData.getInstance().setLoggedInCustomer(response.getData().getCustomer());
                        AppGlobalData.getInstance().setToken(response.getData().getToken());
                        AppGlobalData.getInstance().setGuest(false);
                        AppGlobalData.getInstance().getLoggedInCustomer().setCityId(registrationRequest.getCity_id());
                        AppGlobalData.getInstance().saveToPrefs(getApplicationContext());
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    }
                } catch (Exception e) {
                }
                break;
            case CITY_LIST_REQ_CODE:
                if (model != null){
                    //Toast.makeText(this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    GenericResponseModel<CityListResponseModel> responseModel = (GenericResponseModel<CityListResponseModel>) model;
                    cityList = (ArrayList<Cities>) responseModel.getData().getCities();
                    spinnerAdapter = new CityListSpinnerAdapter(SignUpActivity.this, cityList, 0 );
                    binding.cityListSpinner.setAdapter(spinnerAdapter);
                }
                break;
        }
    }
    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        if (model != null && model.getError() != null && model.getError().getMessage() != null)
            Toast.makeText(this, model.getError().getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApiException(Throwable t, int request_code) {
        if (t != null && t.getMessage() != null)
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hitApi(int request) {
        switch (request) {
            case REGISTRATION_REQ_CODE:
                registrationRequest.setCity_id(1);
                WebAPIHelper.getInstance().doRegistration(registrationRequest, new APICallback<LoginRegistrationResponseModel>(REGISTRATION_REQ_CODE));
                break;
            case CITY_LIST_REQ_CODE:
                WebAPIHelper.getInstance().getCityList(new APICallback<CityListResponseModel>(CITY_LIST_REQ_CODE));
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_create:
                if (isValid()) {
                    checkInternetAndHitApi(REGISTRATION_REQ_CODE);
                }
                break;
            case R.id.txtSkip:
                AppGlobalData.getInstance().setGuest(true);
                AppGlobalData.getInstance().setToken("");
                Customer customer = new Customer();
                customer.setCityId(1);
                AppGlobalData.getInstance().setLoggedInCustomer(customer);
                AppGlobalData.getInstance().saveToPrefs(getApplicationContext());
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.txtBack:
                onBackPressed();
                break;
        }
    }

    public boolean isValid() {
        if (!binding.termsAndConditionCheckBox.isChecked()) {
            Toast.makeText(this, R.string.signup_t_and_c_error, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (registrationRequest.getFirst_name() == null || registrationRequest.getFirst_name().trim().isEmpty()) {
            binding.edtInputFirstName.requestFocus();
            binding.edtInputFirstName.setError(getString(R.string.signup_first_name_error));
            return false;
        }

        if (registrationRequest.getLast_name() == null || registrationRequest.getLast_name().trim().isEmpty()) {
            binding.edtInputLastName.requestFocus();
            binding.edtInputLastName.setError(getString(R.string.signup_last_name_error));
            return false;
        }

        if (registrationRequest.getEmail() == null || registrationRequest.getEmail().trim().isEmpty()) {
            binding.edtInputEmail.requestFocus();
            binding.edtInputEmail.setError(getString(R.string.signup_email_error));
            return false;
        }


        if (registrationRequest.getMobile() == null || registrationRequest.getMobile().trim().isEmpty()) {
            binding.edtInputMobileNo.requestFocus();
            binding.edtInputMobileNo.setError(getString(R.string.signup_mobile_error));
            return false;
        }

        if (registrationRequest.getCity_id() == 0) {
            binding.edtInputCity.requestFocus();
            binding.edtInputCity.setError(getString(R.string.signup_city_error));
            return false;
        }
        if (binding.edtInputPassword.getText().toString().trim().isEmpty()) {
            binding.edtInputPassword.requestFocus();
            binding.edtInputPassword.setError(getString(R.string.signup_password_error));
            return false;
        }

        if (binding.edtInputConfirmPassword.getText().toString().trim().isEmpty()) {
            binding.edtInputConfirmPassword.requestFocus();
            binding.edtInputConfirmPassword.setError(getString(R.string.signup_confirm_password_error));
            return false;
        }

        if (!binding.edtInputPassword.getText().toString().equals(binding.edtInputConfirmPassword.getText().toString())) {
            binding.edtInputConfirmPassword.requestFocus();
            binding.edtInputConfirmPassword.setError(getString(R.string.signup_password_not_match_error));
            return false;
        }

        return true;
    }

}
