package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.databinding.ActivityResetPasswordBinding;
import com.beatutify.model.ForgotPasswordRequestModel;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.ResetPasswordRequestModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppConstants;

public class ResetPassActivity extends BaseActivity implements View.OnClickListener{
    private ActivityResetPasswordBinding binding;
    private ResetPasswordRequestModel resetPasswordRequestModel;
    private ForgotPasswordRequestModel forgotPasswordRequestModel;
    private final int RESET_PASSWORD_REQ_CODE = 107;
    private final int FORGOT_PASSWORD_REQ_CODE = 104;
    private String emailAddress;
    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        SpannableString content = new SpannableString(getResources().getString(R.string.resent_passcode));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.txtResendPasscode.setText(content);
        binding.btnSubmit.setOnClickListener(this);
        binding.txtResendPasscode.setOnClickListener(this);
        binding.txtBack.setOnClickListener(this);
        binding.txtSkip.setOnClickListener(this);

    }

    @Override
    public void setContentView() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        resetPasswordRequestModel = new ResetPasswordRequestModel();
        resetPasswordRequestModel.setEmail(emailAddress);
        binding.setResetPassRequest(resetPasswordRequestModel);
    }

    @Override
    public void getExtras() {
        emailAddress = getIntent().getStringExtra(AppConstants.EXTRAS.EMAIL_ADDRESS);
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code) {
            case RESET_PASSWORD_REQ_CODE:
                if (model != null && model.getMessage()!= null) {
                    Toast.makeText(this, model.getMessage(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    this.finish();
                }
                break;
            case FORGOT_PASSWORD_REQ_CODE:
                if (model != null && model.getMessage()!= null) {
                    Toast.makeText(this, model.getMessage(),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {
        if (model!=null && model.getError()!=null && model.getError().getMessage()!=null) {
            Toast.makeText(this,model.getError().getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request){
            case RESET_PASSWORD_REQ_CODE:
                WebAPIHelper.getInstance().doResetPassword(resetPasswordRequestModel, new APICallback<Object>(RESET_PASSWORD_REQ_CODE));
                break;
            case FORGOT_PASSWORD_REQ_CODE:
                WebAPIHelper.getInstance().doForgotPassword(forgotPasswordRequestModel, new APICallback<Object>(FORGOT_PASSWORD_REQ_CODE));
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
                    checkInternetAndHitApi(RESET_PASSWORD_REQ_CODE);
                }
                break;
            case R.id.txt_resend_passcode:
                forgotPasswordRequestModel = new ForgotPasswordRequestModel();
                forgotPasswordRequestModel.setEmail(emailAddress);
                checkInternetAndHitApi(FORGOT_PASSWORD_REQ_CODE);
                break;
            case R.id.txtSkip:
                // move to login page
                Intent intent = new Intent(this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.txtBack:
                onBackPressed();
                break;
        }
    }
    public boolean isValid() {
        if (resetPasswordRequestModel.getOtp() == null || resetPasswordRequestModel.getOtp().trim().isEmpty()) {
            binding.edtInputPasscode.setError(getString(R.string.enter_passcode_error));
            return false;
        }
        if (resetPasswordRequestModel.getPassword() == null || resetPasswordRequestModel.getPassword().trim().isEmpty()){
            binding.edtInputPassword.setError(getString(R.string.login_password_error));
            return  false;
        }
        if (resetPasswordRequestModel.getConfirmPassword() == null || resetPasswordRequestModel.getConfirmPassword().trim().isEmpty()){
            binding.edtInputConfirmPassword.setError(getString(R.string.login_password_error));
            return false;
        }
        if (!resetPasswordRequestModel.getConfirmPassword().equals(resetPasswordRequestModel.getPassword())){
            binding.edtInputPassword.setError(getString(R.string.password_not_match_error));
            binding.edtInputConfirmPassword.setError(getString(R.string.password_not_match_error));
            return false;
        }
        return true;
    }
}
