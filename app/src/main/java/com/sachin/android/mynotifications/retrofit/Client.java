package com.sachin.android.mynotifications.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ANDROID on 11/4/2017.
 */

public class Client {

    private static Retrofit retrofit;
    private static final String base_url="https://fcm.googleapis.com";

    private static Retrofit getInstance(final String token) {
        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "key=" + token)
                        .addHeader("Content-Type","application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        if (retrofit==null)
           retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static final ApiServices getServices(String token){
        return getInstance(token).create(ApiServices.class);
    }
}
