package com.innominds.driverassist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceData implements Parcelable{
    private String batteryLevel;
    private String operator;
    private String firmware;
    private String speedLimit;
    private String deviation;
    private boolean saveToCloud;
    private String serialNumber;
    private boolean internet;
    private boolean audioPrompt;

    @Override
    public String toString() {
        return "DeviceData{" +
                "batteryLevel='" + batteryLevel + '\'' +
                ", operator='" + operator + '\'' +
                ", firmware='" + firmware + '\'' +
                ", speedLimit='" + speedLimit + '\'' +
                ", deviation='" + deviation + '\'' +
                ", saveToCloud=" + saveToCloud +
                ", serialNumber='" + serialNumber + '\'' +
                ", internet=" + internet +
                ", audioPrompt=" + audioPrompt +
                '}';
    }

    public boolean isAudioPrompt() {
        return audioPrompt;
    }

    public void setAudioPrompt(boolean audioPrompt) {
        this.audioPrompt = audioPrompt;
    }

    protected DeviceData(Parcel in) {
        batteryLevel = in.readString();
        operator = in.readString();
        firmware = in.readString();
        speedLimit = in.readString();
        deviation = in.readString();
        saveToCloud = in.readByte() != 0;
        serialNumber = in.readString();
        internet = in.readByte() != 0;
    }

    public static final Creator<DeviceData> CREATOR = new Creator<DeviceData>() {
        @Override
        public DeviceData createFromParcel(Parcel in) {
            return new DeviceData(in);
        }

        @Override
        public DeviceData[] newArray(int size) {
            return new DeviceData[size];
        }
    };

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DeviceData() {
    }

    public String getBatteryLevel() {

        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(String speedLimit) {
        this.speedLimit = speedLimit;
    }

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public boolean isSaveToCloud() {
        return saveToCloud;
    }

    public void setSaveToCloud(boolean saveToCloud) {
        this.saveToCloud = saveToCloud;
    }

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(batteryLevel);
        parcel.writeString(operator);
        parcel.writeString(firmware);
        parcel.writeString(speedLimit);
        parcel.writeString(deviation);
        parcel.writeByte((byte) (saveToCloud ? 1 : 0));
        parcel.writeString(serialNumber);
        parcel.writeByte((byte) (internet ? 1 : 0));
    }
}
