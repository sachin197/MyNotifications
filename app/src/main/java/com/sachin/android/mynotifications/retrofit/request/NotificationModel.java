package com.sachin.android.mynotifications.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ANDROID on 11/4/2017.
 */

public class NotificationModel implements Serializable{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("is_image")
    @Expose
    private int isImage;
    @SerializedName("url")
    @Expose
    private String url;

    public NotificationModel(String title, String message, int isImage, String url) {
        this.title = title;
        this.message = message;
        this.isImage = isImage;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIsImage() {
        return isImage;
    }

    public void setIsImage(int isImage) {
        this.isImage = isImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NotificationModel{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", isImage=" + isImage +
                ", url='" + url + '\'' +
                '}';
    }
}
