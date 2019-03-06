package com.innominds.driverassist.views;

import com.innominds.driverassist.model.DeviceData;

public interface AddDeviceView {
    public void onDeviceData(DeviceData deviceData);
    public void onError(int resID);
    public void onProgress(int resID);
    public void onSuccess(int resID);
}
