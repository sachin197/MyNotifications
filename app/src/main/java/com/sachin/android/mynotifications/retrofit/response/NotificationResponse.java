package com.sachin.android.mynotifications.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 11/4/2017.
 */

public class NotificationResponse {

    @SerializedName("message_id")
    @Expose
    private String messageId;

    @SerializedName("error")
    @Expose
    private String error;


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
