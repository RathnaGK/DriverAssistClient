package com.innominds.driverassist.presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.innominds.driverassist.R;
import com.innominds.driverassist.model.DeviceData;
import com.innominds.driverassist.repositories.AddDeviceRepository;
import com.innominds.driverassist.views.AddDeviceView;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.OkHttpClient;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AddDevicePresenter {
    private Context mContext;
    private AddDeviceView mAddDeviceView;
    private AddDeviceRepository mAddRepository;
    public AddDevicePresenter(Context mContext, AddDeviceView mAddDeviceView)
    {
        this.mContext=mContext;
        this.mAddDeviceView=mAddDeviceView;
        mAddRepository=new AddDeviceRepository(mContext);
    }
    public void getDeviceData()
    {
            mAddRepository.getDeviceInfo()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<DeviceData>() {
                        @Override
                        public void call(DeviceData deviceData) {
                            if(deviceData== null)
                            {
                                mAddDeviceView.onError(R.string.can_not_connect_to_device);
                            }
                            else
                                mAddDeviceView.onDeviceData(deviceData);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            mAddDeviceView.onError(R.string.can_not_connect_to_device);
                        }
                    });
    }
}
