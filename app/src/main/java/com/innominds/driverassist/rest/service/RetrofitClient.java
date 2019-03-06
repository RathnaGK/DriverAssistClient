package com.innominds.driverassist.rest.service;

import android.content.Context;

import com.innominds.driverassist.R;
import com.innominds.driverassist.utils.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(OkHttpClient httpClient, Context context) {
            String ipaddress = Utils.getApIpAddr(context);

            retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.get_device_data_url, ipaddress))
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).build();


        return retrofit;
    }

}
