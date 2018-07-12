package com.beatutify.model;

import java.util.List;

/**
 * Created by karan.kalsi on 4/19/2018.
 */

public class NotificationListResponseModel {
    private List<BeautifyNotificationModel> notificationList;

    public List<BeautifyNotificationModel> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<BeautifyNotificationModel> notificationList) {
        this.notificationList = notificationList;
    }
}
