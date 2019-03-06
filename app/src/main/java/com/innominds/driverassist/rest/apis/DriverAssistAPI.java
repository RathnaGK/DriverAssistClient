package com.innominds.driverassist.rest.apis;

import com.innominds.driverassist.model.DeviceData;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface DriverAssistAPI {


    @GET("/getdata")
    public Observable<DeviceData> getDeviceData();

    @POST("/setdata")
    public Observable<DeviceData> sendDeviceData(@Body DeviceData deviceData);
}
