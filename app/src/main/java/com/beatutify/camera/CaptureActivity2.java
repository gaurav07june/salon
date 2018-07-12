package com.beatutify.camera;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.beatutify.camera.internal.BaseCaptureActivity;
import com.beatutify.camera.internal.Camera2Fragment;


public class CaptureActivity2 extends BaseCaptureActivity {

    @Override
    @NonNull
    public Fragment getFragment() {
        return Camera2Fragment.newInstance();
    }
}