package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.customviews.NMGButton;
import com.beatutify.databinding.ActivityForgotPasswordBinding;
import com.beatutify.model.Customer;
import com.beatutify.model.ForgotPasswordRequestModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.LoginRegistrationResponseModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener{
    private ActivityForgotPasswordBinding binding;
    private ForgotPasswordRequestModel forgotPasswordRequestModel;
    private final static int FORGOT_PASS_REQ_CODE = 104;
    @Override
    public void initViews() {

    }
    @Override
    public void setViews() {
        binding.btnSubmit.setOnClickListener(this);
        binding.txtBack.setOnClickListener(this);
        binding.txtSkip.setOnClickListener(this);
    }
    @Override
    public void setContentView() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        forgotPasswordRequestModel = new ForgotPasswordRequestModel();
        binding.setForgotPasswordRequest(forgotPasswordRequestModel);
    }
    @Override
    public void getExtras() {

    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code) {
            case FORGOT_PASS_REQ_CODE:
                if (model != null && model.getMessage()!= null) {
                    Toast.makeText(this, model.getMessage(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ResetPassActivity.class)
                            .putExtra(AppConstants.EXTRAS.EMAIL_ADDRESS, forgotPasswordRequestModel.getEmail()));
                }
                break;
        }
    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        if (model!=null && model.getError()!=null && model.getError().getMessage()!=null)
        {
            Toast.makeText(this,model.getError().getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case FORGOT_PASS_REQ_CODE:
                WebAPIHelper.getInstance().doForgotPassword(forgotPasswordRequestModel, new APICallback<Object>(FORGOT_PASS_REQ_CODE));
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
            case R.id.btn_submit:
                if (isValid()) {
                    checkInternetAndHitApi(FORGOT_PASS_REQ_CODE);
                }
                break;
            case R.id.txtSkip:
                onBackPressed();
                break;
            case R.id.txtBack:
                onBackPressed();
                break;
        }
    }

    public boolean isValid() {

        if (forgotPasswordRequestModel.getEmail() == null || forgotPasswordRequestModel.getEmail().trim().isEmpty()) {
            binding.edtForgotEmail.setError(getString(R.string.enter_email_error));
            return false;
        }
        if (!AppCommons.isValidEmail(forgotPasswordRequestModel.getEmail())){
            binding.edtForgotEmail.setError(getString(R.string.enter_valid_email_error));
            return false;
        }
        return true;
    }
}
