package com.sachin.android.mynotifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sachin.android.mynotifications.retrofit.ApiServices;
import com.sachin.android.mynotifications.retrofit.Client;
import com.sachin.android.mynotifications.retrofit.request.NotificationModel;
import com.sachin.android.mynotifications.retrofit.request.NotificationRequest;
import com.sachin.android.mynotifications.retrofit.response.NotificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("offers");
        Log.d(TAG, "onCreate: "+ FirebaseInstanceId.getInstance().getToken());

        sendNotification();
    }

    private void sendNotification() {
        NotificationModel nm = new NotificationModel("Google Play Protect for Android","Working to keep your device and data safe from misbehaving apps by scanning over 50 billion apps per day, even the ones you haven\'t installed yet!",1,"https://www.android.com/static/2016/img/versions/oreo/emoji/featured_1x.jpg");
        NotificationRequest request = new NotificationRequest();
        request.setData(nm);
        request.setTo("/topics/offers");

        Log.d(TAG, "sendNotification: "+request);

        ApiServices services = Client.getServices("AAAAaHCZWsw:APA91bEGVxpIlzrJPFLB8812AZciPPNrP5yUhdIGlLyY2vp5Q7lCcmq4dIm_udmb54FiRUs8CJ2mBTuc1iCJFd0GVHGF8inP4bXKxfnSUW27AhW0K7GNy984qdapToRgJhB2wC6ATtmn");
        Call<NotificationResponse> call = services.sendNotification(request);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response.isSuccessful() && response.errorBody()==null){
                    Log.d(TAG, "onResponse: "+response.body().getMessageId());
                    Log.d(TAG, "onResponse: "+call.request().url());
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
