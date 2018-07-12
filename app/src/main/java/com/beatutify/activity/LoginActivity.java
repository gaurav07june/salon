package com.beatutify.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.customviews.NMGButton;
import com.beatutify.customviews.NMGTextView;
import com.beatutify.databinding.ActivityLoginBinding;
import com.beatutify.model.CheckSocialIdRequestModel;
import com.beatutify.model.Customer;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.LoginRegistrationResponseModel;
import com.beatutify.model.LoginRequestModel;
import com.beatutify.model.RegistrationRequestModel;
import com.beatutify.retrofit.WebAPIHelper;
import com.beatutify.util.AppCommons;
import com.beatutify.util.AppGlobalData;
import com.beatutify.util.FBLoginAdditionalDetailsDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends BaseActivity implements FacebookCallback<LoginResult>, View.OnClickListener {

    private ActivityLoginBinding binding;
    private LoginRequestModel loginRequestModel;
    private RegistrationRequestModel loginViaFBRequest;
    private CallbackManager callbackManager;
    private static final int REGISTRATION_API_REQ_CODE = 101;
    private static final int LOGIN_API_REQ_CODE = 102;
    private static final int LOGIN_VIA_FB_API_REQ_CODE = 103;
    private static final int CHECK_SOCIAL_ID_REQ_CODE = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {


    }

    @Override
    public void setViews() {

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
        SpannableString content = new SpannableString(getResources().getString(R.string.forgot_pass));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.txtForgotPassword.setText(content);
        binding.imgBtnShowPassword.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnLoginFacebook.setOnClickListener(this);
        binding.txtForgotPassword.setOnClickListener(this);
        binding.txtSignup.setOnClickListener(this);
        binding.txtSkip.setOnClickListener(this);
        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (binding.imgBtnShowPassword.getVisibility() != View.VISIBLE) {
                    binding.imgBtnShowPassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void setContentView() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginRequestModel = new LoginRequestModel();
        loginViaFBRequest = new RegistrationRequestModel();
        loginViaFBRequest.setLang(AppCommons.getCurrentLanguage(this));
        binding.setLoginRequest(loginRequestModel);


    }

    @Override
    public void getExtras() {

    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {

        try {
            switch (request_code) {
                case LOGIN_API_REQ_CODE:
                    GenericResponseModel<LoginRegistrationResponseModel> response = (GenericResponseModel<LoginRegistrationResponseModel>) model;
                    if (response.getData().getCustomer() != null && response.getData().getToken() != null) {
                        AppGlobalData.getInstance().setLoggedInCustomer(response.getData().getCustomer());
                        AppGlobalData.getInstance().setToken(response.getData().getToken());
                        AppGlobalData.getInstance().setGuest(false);
                        AppGlobalData.getInstance().saveToPrefs(getApplicationContext());
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    }
                    break;
                case LOGIN_VIA_FB_API_REQ_CODE:
                    response = (GenericResponseModel<LoginRegistrationResponseModel>) model;
                    if (response.getData().getCustomer() != null && response.getData().getToken() != null) {
                        AppGlobalData.getInstance().setLoggedInCustomer(response.getData().getCustomer());
                        AppGlobalData.getInstance().setToken(response.getData().getToken());
                        AppGlobalData.getInstance().setGuest(false);
                        AppGlobalData.getInstance().saveToPrefs(getApplicationContext());
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    }
                    break;

                case CHECK_SOCIAL_ID_REQ_CODE:
                    if (model.getStatus() == 1) {
                        checkInternetAndHitApi(LOGIN_VIA_FB_API_REQ_CODE);
                    } else {
                        new FBLoginAdditionalDetailsDialog(LoginActivity.this, loginViaFBRequest, new FBLoginAdditionalDetailsDialog.OnFbMissingDetailsAddListener() {
                            @Override
                            public void onFbMissingDetailAdd(RegistrationRequestModel loginViaFBRequest) {
                                checkInternetAndHitApi(LOGIN_VIA_FB_API_REQ_CODE);
                            }
                        }).show();

                    }
                    break;
            }
        } catch (Exception e) {

        }

    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {

        if (model != null && model.getError() != null && model.getError().getMessage() != null) {
            Toast.makeText(this, model.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request) {
            case LOGIN_API_REQ_CODE:
                WebAPIHelper.getInstance().doLogin(loginRequestModel, new APICallback<LoginRegistrationResponseModel>(LOGIN_API_REQ_CODE));
                break;
            case LOGIN_VIA_FB_API_REQ_CODE:
                WebAPIHelper.getInstance().doRegistration(loginViaFBRequest, new APICallback<LoginRegistrationResponseModel>(LOGIN_VIA_FB_API_REQ_CODE));
                break;
            case CHECK_SOCIAL_ID_REQ_CODE:
                CheckSocialIdRequestModel checkSocialIdRequest = new CheckSocialIdRequestModel();
                checkSocialIdRequest.setEmail(loginViaFBRequest.getEmail());
                checkSocialIdRequest.setSocialId(loginViaFBRequest.getSocial_id());
                WebAPIHelper.getInstance().checkSocialId(checkSocialIdRequest, new APICallback<>(CHECK_SOCIAL_ID_REQ_CODE));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_show_password:

                binding.imgBtnShowPassword.setSelected(!binding.imgBtnShowPassword.isSelected());
                binding.edtPassword.setInputType(
                        binding.imgBtnShowPassword.isSelected() ?
                                InputType.TYPE_CLASS_TEXT
                                : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                );
                binding.edtPassword.setTypeface(binding.edtEmail.getTypeface());
                binding.edtPassword.setSelection(binding.edtPassword.length());

                break;

            case R.id.txt_signup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.txt_forgot_password:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.txt_skip:
                AppGlobalData.getInstance().setGuest(true);
                AppGlobalData.getInstance().setToken("");
                Customer customer = new Customer();
                customer.setCityId(1);
                AppGlobalData.getInstance().setLoggedInCustomer(customer);
                AppGlobalData.getInstance().saveToPrefs(getApplicationContext());
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.btn_login:
                if (isValid()) {
                    checkInternetAndHitApi(LOGIN_API_REQ_CODE);
                }
                break;
            case R.id.btn_login_facebook:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;
        }
    }


    public boolean isValid() {

        if (loginRequestModel.getEmail_phone() == null || loginRequestModel.getEmail_phone().trim().isEmpty()) {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError(getString(R.string.login_username_error));
            return false;
        }

        if (loginRequestModel.getPassword() == null || loginRequestModel.getPassword().trim().isEmpty()) {
            binding.edtPassword.requestFocus();
            binding.edtPassword.setError(getString(R.string.login_password_error));
            binding.imgBtnShowPassword.setVisibility(View.GONE);
            return false;

        }

        return true;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {


                        } else {


                            String name = me.optString("name");
                            String email = me.optString("email");
                            String[] names = name.split(" ");

                            String id = me.optString("id");

                            if (names.length > 0)
                                loginViaFBRequest.setFirst_name(names[0]);
                            if (names.length > 1)
                                loginViaFBRequest.setLast_name(names[1]);
                            loginViaFBRequest.setSocial_id(id);
                            if (email != null && !email.isEmpty()) {
                                loginViaFBRequest.setEmail(email);

                            }


                            checkInternetAndHitApi(CHECK_SOCIAL_ID_REQ_CODE);


                        }
                    }
                });
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
