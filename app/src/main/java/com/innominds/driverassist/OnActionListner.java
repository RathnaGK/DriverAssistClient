package com.innominds.driverassist;

import com.innominds.driverassist.model.DeviceData;

public interface OnActionListner {
     void onLoginSelected();
     void onRegisterSelected();
     void onRegisterSuccessSelected();
     void onAddDeviceSelected();
     void onDeviceSelected(String mSSID, DeviceData deviceData);
}
