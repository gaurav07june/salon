package com.beatutify.camera.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beatutify.R;
import com.beatutify.camera.util.CameraUtil;


public abstract class BaseGalleryFragment extends Fragment implements CameraUriInterface, View.OnClickListener {

    BaseCaptureInterface mInterface;
    int mPrimaryColor;
    String mOutputUri;
    View mControlsFrame;
    View mRetry;
    ImageButton mCloseBtn;
    View mConfirm;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mInterface = (BaseCaptureInterface) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOutputUri = getArguments().getString("output_uri");
        mControlsFrame = view.findViewById(R.id.controlsFrame);
        mRetry =  view.findViewById(R.id.retry);
        mCloseBtn = (ImageButton) view.findViewById(R.id.close_btn);
        mConfirm =  view.findViewById(R.id.confirm);

        mPrimaryColor = getArguments().getInt(CameraIntentKey.PRIMARY_COLOR);
        if (CameraUtil.isColorDark(mPrimaryColor)) {
            mPrimaryColor = CameraUtil.darkenColor(mPrimaryColor);
            final int textColor = ContextCompat.getColor(view.getContext(), R.color.mcam_color_light);
          //  mRetry.setTextColor(textColor);
           // mConfirm.setTextColor(textColor);
        } else {
            final int textColor = ContextCompat.getColor(view.getContext(), R.color.mcam_color_dark);
           // mRetry.setTextColor(textColor);
            //mConfirm.setTextColor(textColor);
        }

        mRetry.setVisibility(getArguments().getBoolean(CameraIntentKey.ALLOW_RETRY, true) ? View.VISIBLE : View.GONE);

    }

    @Override
    public String getOutputUri() {
        return getArguments().getString("output_uri");
    }

    void showDialog(String title, String errorMsg) {
        new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(errorMsg)
                .positiveText(android.R.string.ok)
                .show();
    }
}