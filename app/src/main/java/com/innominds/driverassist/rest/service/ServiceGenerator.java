package com.innominds.driverassist.rest.service;

import android.content.Context;

import okhttp3.OkHttpClient;

public class ServiceGenerator {

    public static <S> S createService(final Context context, Class<S> serviceClass) {
        OkHttpClient httpClient = new OkHttpClient();
        return RetrofitClient.getClient(httpClient,context).create(serviceClass);
    }
}
