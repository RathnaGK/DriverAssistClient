package com.innominds.driverassist.presenters;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.innominds.driverassist.R;
import com.innominds.driverassist.model.DeviceData;
import com.innominds.driverassist.utils.Utils;
import com.innominds.driverassist.views.CameraActivityView;

import java.net.HttpURLConnection;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CameraActivityPresenter {

    private Context mContext;
    private CameraActivityView mCameraActivityview;
    private OkHttpClient _client;

    public CameraActivityPresenter(Context mContext, CameraActivityView mCameraActivityview) {
        this.mContext=mContext;
        this.mCameraActivityview=mCameraActivityview;
        _client = new OkHttpClient.Builder().build();
    }

    public void sendRotation(String rotation)
    {
        String ipaddress = Utils.getApIpAddr(mContext);
        if (ipaddress!=null) {
            String url = mContext.getResources().getString(R.string.send_rotation_data_url, ipaddress);

            sendRotationInfo(url,rotation)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String response) {
                            mCameraActivityview.onSuccess(response);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            mCameraActivityview.onError(R.string.can_not_connect_to_device);
                        }
                    });
        }
        else
            Log.e("CameraActivity", "ip null" );

    }
    private Observable<String> sendRotationInfo(String url, String rotation) {
        RequestBody body = new FormBody.Builder()
                .add("rotationData", rotation)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    okhttp3.Response response = _client.newCall(request).execute();
                    if (!response.isSuccessful())
                    {
                        if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST)
                            subscriber.onError(new RuntimeException("error code " + response.code()));
                        else
                            subscriber.onError(new RuntimeException("error code " + response.code()));
                    }

                    Gson gson = new Gson();
                    subscriber.onNext(gson.fromJson(response.body().toString(),String.class));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void sendStopCommand(String stopData)
    {
        String ipaddress = Utils.getApIpAddr(mContext);
        if (ipaddress!=null) {
            String url = mContext.getResources().getString(R.string.send_stop_command_url, ipaddress);

            sendstopCommandInfo(url,stopData)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String response) {
                            mCameraActivityview.onSuccess(response);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            mCameraActivityview.onError(R.string.can_not_connect_to_device);
                        }
                    });
        }
        else
            Log.e("CameraActivity", "ip null" );
    }
    private Observable<String> sendstopCommandInfo(String url, String stopData) {
        RequestBody body = new FormBody.Builder()
                .add("stopCommand", stopData)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    okhttp3.Response response = _client.newCall(request).execute();
                    if (!response.isSuccessful())
                    {
                        if(response.code() == HttpURLConnection.HTTP_BAD_REQUEST)
                            subscriber.onError(new RuntimeException("error code " + response.code()));
                        else
                            subscriber.onError(new RuntimeException("error code " + response.code()));
                    }

                    Gson gson = new Gson();
                    subscriber.onNext(gson.fromJson(response.body().toString(),String.class));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}

