package com.innominds.driverassist.presenters;

import android.content.Context;

import com.google.gson.Gson;
import com.innominds.driverassist.R;
import com.innominds.driverassist.model.DeviceData;
import com.innominds.driverassist.repositories.DeviceDetailsRepository;
import com.innominds.driverassist.utils.Utils;
import com.innominds.driverassist.views.DeviceDetailsFragmentView;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Timeout;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DeviceDetailsFragmentPresenter {
    private Context mContext;
    private DeviceDetailsFragmentView mDeviceDetailsFragmentView;
    private  OkHttpClient _client;
    private DeviceDetailsRepository mDeviceDetailsRepository;
    public DeviceDetailsFragmentPresenter(Context mContext, DeviceDetailsFragmentView mDeviceDetailsFragmentView)
    {
        this.mContext = mContext;
        this.mDeviceDetailsFragmentView=mDeviceDetailsFragmentView;
        _client = new OkHttpClient.Builder().build();
        mDeviceDetailsRepository=new DeviceDetailsRepository(mContext);
    }

    public void sendDataToServer(DeviceData deviceData)
    {
        String ipaddress = Utils.getApIpAddr(mContext);
        if (ipaddress != null) {
            final String url = mContext.getResources().getString(R.string.set_device_data_url, ipaddress);
            Gson gson = new Gson();
            setDeviceInfo(url,gson.toJson(deviceData))
                    // setDeviceInfo111(url,gson.toJson(deviceData))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Response>() {
                        @Override
                        public void call(Response response) {
                            mDeviceDetailsFragmentView.onSuccess(R.string.values_set);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mDeviceDetailsFragmentView.onError(R.string.could_not_set_data);
                            throwable.getMessage();
                        }
                    });

        }
        else
            mDeviceDetailsFragmentView.onError(R.string.can_not_connect_to_device);
    }

    public Observable<Response> setDeviceInfo(String url, String deviceData) {
        RequestBody body = new FormBody.Builder()
                .add("data", deviceData)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return Observable.create(new rx.Observable.OnSubscribe<Response>() {
            @Override
            public void call(Subscriber<? super Response> subscriber) {
                try{
                    okhttp3.Response response = _client.newCall(request).execute();
                    if (!response.isSuccessful())
                    {
                        if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST)
                            subscriber.onError(new RuntimeException("error code " + response.code()));
                        else
                            subscriber.onError(new RuntimeException("error code " + response.code()));
                    }
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
