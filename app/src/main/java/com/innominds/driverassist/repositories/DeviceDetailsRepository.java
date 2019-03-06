package com.innominds.driverassist.repositories;

import android.content.Context;

import com.innominds.driverassist.model.DeviceData;
import com.innominds.driverassist.rest.apis.DriverAssistAPI;
import com.innominds.driverassist.rest.service.ServiceGenerator;

import retrofit2.Response;
import rx.Observable;

public class DeviceDetailsRepository extends BaseRepository{


    public DeviceDetailsRepository(Context context) {
        super(context);
    }

    public Observable<DeviceData> setDeviceInfo(DeviceData deviceData) {
        DriverAssistAPI service = ServiceGenerator.createService(mContext, DriverAssistAPI.class);
        return service.sendDeviceData(deviceData);
    }
}
