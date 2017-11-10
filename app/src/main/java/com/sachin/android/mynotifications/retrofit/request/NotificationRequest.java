package com.sachin.android.mynotifications.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ANDROID on 11/4/2017.
 */

public class NotificationRequest implements Serializable {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("data")
    @Expose
    private NotificationModel notificationModel;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationModel getData() {
        return notificationModel;
    }

    public void setData(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "to='" + to + '\'' +
                ", notificationModel=" + notificationModel +
                '}';
    }
}
