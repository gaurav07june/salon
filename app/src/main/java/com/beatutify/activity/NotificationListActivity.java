package com.beatutify.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.beatutify.R;
import com.beatutify.adapters.NotificationListAdapter;
import com.beatutify.databinding.ActivityNotificationListBinding;
import com.beatutify.model.GenericResponseModel;
import com.beatutify.model.NotificationListResponseModel;


public class NotificationListActivity extends BaseActivity {

    private ActivityNotificationListBinding binding;
    private NotificationListAdapter notificationListAdapter;
    private static final int NOTIFICATION_LIST_REQ_CODE = 101;

    @Override
    public void setContentView() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_list);
    }

    @Override
    public void getExtras() {

    }

    @Override
    public void initViews() {
        notificationListAdapter = new NotificationListAdapter();
    }

    @Override
    public void setViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.notifications);
        binding.notificationListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.notificationListView.setAdapter(notificationListAdapter);
        checkInternetAndHitApi(NOTIFICATION_LIST_REQ_CODE);
    }

    @Override
    public <D> void onApiSuccess(GenericResponseModel<D> model, int request_code) {
        switch (request_code) {
            case NOTIFICATION_LIST_REQ_CODE:
                if (model.getData() instanceof NotificationListResponseModel) {
                    notificationListAdapter.setNotificationList(((NotificationListResponseModel) model.getData()).getNotificationList());
                }
                break;
        }
    }

    @Override
    public <D> void onApiFail(GenericResponseModel<D> model, int request_code) {

    }

    @Override
    public void onApiException(Throwable t, int request_code) {

    }

    @Override
    public void hitApi(int request) {
        switch (request) {
            case NOTIFICATION_LIST_REQ_CODE:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
