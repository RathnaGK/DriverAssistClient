package com.innominds.driverassist.repositories;

import android.content.Context;

import com.innominds.driverassist.model.DeviceData;

import com.innominds.driverassist.rest.apis.DriverAssistAPI;
import com.innominds.driverassist.rest.service.ServiceGenerator;

import rx.Observable;

public class AddDeviceRepository extends BaseRepository{

    public AddDeviceRepository(Context context) {
        super(context);
    }

    public Observable<DeviceData> getDeviceInfo()
    {
        DriverAssistAPI service = ServiceGenerator.createService(mContext, DriverAssistAPI.class);
        return service.getDeviceData();
    }

}
