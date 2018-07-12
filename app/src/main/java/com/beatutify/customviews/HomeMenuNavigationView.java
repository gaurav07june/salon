package com.beatutify.customviews;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.beatutify.R;
import com.beatutify.activity.CMSActivity;
import com.beatutify.activity.ContactUsActivity;
import com.beatutify.activity.MyProfileActivity;
import com.beatutify.activity.NotificationListActivity;
import com.beatutify.activity.SettingActivity;
import com.beatutify.databinding.HomeMenuNavigationViewBinding;

/**
 * Created by karan.kalsi on 4/17/2018.
 */

public class HomeMenuNavigationView extends FrameLayout implements View.OnClickListener {
    private HomeMenuNavigationViewBinding binding;
    public HomeMenuNavigationView(@NonNull Context context) {
        super(context);
        init();
    }

    public HomeMenuNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeMenuNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init()
    {
        View view =LayoutInflater.from(getContext()).inflate(R.layout.home_menu_navigation_view,this,false);
        addView(view);
        binding=DataBindingUtil.bind(view);
        binding.homeMenuCloseBtn.setOnClickListener(this);
        binding.menuActionDashboard.setOnClickListener(this);
        binding.menuActionMyProfile.setOnClickListener(this);
        binding.menuActionAboutUs.setOnClickListener(this);
        binding.menuActionNotification.setOnClickListener(this);
        binding.menuActionSettings.setOnClickListener(this);
        binding.menuActionMyFavorite.setOnClickListener(this);
        binding.menuActionContactUs.setOnClickListener(this);
        binding.menuActionTAndC.setOnClickListener(this);
        binding.menuActionPrivacyPolicy.setOnClickListener(this);
        binding.menuActionShareApp.setOnClickListener(this);
        binding.menuActionRateUs.setOnClickListener(this);

    }

public interface HomeMenuNavigationListener
{
    void onDashBoardClick();
    void onHomeMenuCloseClick();
}
private HomeMenuNavigationListener homeMenuNavigationListener=null;

    public HomeMenuNavigationListener getHomeMenuNavigationListener() {
        return homeMenuNavigationListener;
    }

    public void setHomeMenuNavigationListener(HomeMenuNavigationListener homeMenuNavigationListener) {
        this.homeMenuNavigationListener = homeMenuNavigationListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.home_menu_close_btn:if(homeMenuNavigationListener!=null)homeMenuNavigationListener.onHomeMenuCloseClick();break;
            case R.id.menu_action_dashboard:if(homeMenuNavigationListener!=null)homeMenuNavigationListener.onDashBoardClick();break;

            case R.id.menu_action_about_us:
                getContext().startActivity(new Intent(getContext(), CMSActivity.class));
                break;
            case R.id.menu_action_notification:
                getContext().startActivity(new Intent(getContext(), NotificationListActivity.class));
                break;
            case R.id.menu_action_my_profile:
                getContext().startActivity(new Intent(getContext(), MyProfileActivity.class));
                break;
            case R.id.menu_action_settings:
                getContext().startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.menu_action_contact_us:
                getContext().startActivity(new Intent(getContext(), ContactUsActivity.class));
                break;
            case R.id.menu_action_t_and_c:
                getContext().startActivity(new Intent(getContext(), CMSActivity.class));
                break;
            case R.id.menu_action_privacy_policy:
                getContext().startActivity(new Intent(getContext(), CMSActivity.class));
                break;
            case R.id.menu_action_rate_us:
                   break;
            case R.id.menu_action_share_app:
                 break;

        }
    }
}
