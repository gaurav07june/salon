package com.beatutify.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;

import com.beatutify.R;
import com.beatutify.databinding.ActivitySettingBinding;
import com.beatutify.model.GenericResponseModel;


public class SettingActivity extends BaseActivity implements View.OnClickListener {
private ActivitySettingBinding binding;
private Animation rotateDownAnim;
private Animation rotateUpAnim;

    @Override
    public void setContentView() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setting);
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {

        rotateDownAnim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_down);
        rotateUpAnim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_up);
    }

    @Override
    public void setViews() {
        binding.settingMenuActionChangePass.setOnClickListener(this);

        binding.englishLangRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                binding.arabicLangRadioBtn.setChecked(false);
            }
        });
        binding.arabicLangRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    binding.englishLangRadioBtn.setChecked(false);
            }
        });
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
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.setting_menu_action_change_pass:break;
        }
    }
}
