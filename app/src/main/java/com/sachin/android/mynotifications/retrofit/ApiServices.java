package com.sachin.android.mynotifications.retrofit;

import com.sachin.android.mynotifications.retrofit.request.NotificationRequest;
import com.sachin.android.mynotifications.retrofit.response.NotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by ANDROID on 11/4/2017.
 */

public interface ApiServices {

    @POST("/fcm/send")
    Call<NotificationResponse> sendNotification(@Body NotificationRequest notificationRequest);

}
