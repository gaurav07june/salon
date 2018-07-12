package com.beatutify.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.beatutify.R;
import com.beatutify.util.AppConstants;
import com.beatutify.util.AppGlobalData;

import io.codetail.animation.ViewAnimationUtils;

import static com.beatutify.util.AppConstants.SPLASH_ANIM_TIME;
import static com.beatutify.util.AppConstants.SPLASH_TIME_OUT;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        startAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppGlobalData.getInstance().setFromPrefs(getApplicationContext()))
                {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
       // disable the back press
    }
private View splashContainer;
    private void startAnimation()
    {
        splashContainer = findViewById(R.id.splash_container);
        splashContainer.post(new Runnable() {
            @Override
            public void run() {
                int cx = (splashContainer.getLeft() + splashContainer.getRight()) / 2;
                int cy = (splashContainer.getTop() + splashContainer.getBottom()) / 2;
                int dx = Math.max(cx, splashContainer.getWidth() - cx);
                int dy = Math.max(cy, splashContainer.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                if (finalRadius<=0)
                {
                    finalRadius=1;
                }
                Animator animator =
                        ViewAnimationUtils.createCircularReveal(splashContainer, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(SPLASH_ANIM_TIME);
                animator.start();
            }
        });

    }
}