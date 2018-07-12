package com.beatutify.customviews;

import android.app.Activity;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beatutify.R;
import com.beatutify.databinding.BeautifyAlertBinding;

/**
 * Created by gaurav.singh on 4/24/2018.
 */

public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public NMGButton yes, no;
    public NMGTextView txtAlertMessage;
    private AlertListener alertListener;
    private String yesButtonText, noButtonText, alertMessage;

    public CustomAlertDialog(Activity activity, AlertListener alertListener) {
        super(activity);
        this.activity = activity;
        this.alertListener = alertListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.beautify_alert);
        this.setCancelable(false);
        yes =(NMGButton) findViewById(R.id.btn_yes);
        no =(NMGButton) findViewById(R.id.btn_no);
        txtAlertMessage = (NMGTextView) findViewById(R.id.txt_alert_message);
        yes.setText(yesButtonText);
        no.setText(noButtonText);
        txtAlertMessage.setText(alertMessage);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    public void setYesButtonText(String text){
        yesButtonText = text;
    }
    public void setNoButtonText(String text){
        noButtonText = text;
    }
    public void setAlertMessage(String text){
        alertMessage = text;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                alertListener.onYesButtonClicked();
                break;
            case R.id.btn_no:
                alertListener.onNoButtonClicked();
                break;
            default:
                break;
        }
        dismiss();
    }
    public interface AlertListener{
        void onYesButtonClicked();
        void onNoButtonClicked();
    }
}
