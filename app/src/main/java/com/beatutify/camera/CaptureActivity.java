package com.beatutify.camera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.beatutify.camera.internal.BaseCaptureActivity;
import com.beatutify.camera.internal.CameraFragment;


public class CaptureActivity extends BaseCaptureActivity {


    @Override
    @NonNull
    public Fragment getFragment() {
        return CameraFragment.newInstance();
    }
}