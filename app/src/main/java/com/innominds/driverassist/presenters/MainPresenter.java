package com.innominds.driverassist.presenters;

import android.content.Context;

import com.innominds.driverassist.R;
import com.innominds.driverassist.repositories.MainRepository;
import com.innominds.driverassist.views.MainFragmentView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainPresenter {
    private MainFragmentView mMainView;
    private Context mContext;
    private MainRepository mMainRepository;


    public MainPresenter(Context mContext, MainFragmentView mMainView) {
        this.mMainView=mMainView;
        this.mContext=mContext;
        mMainRepository = new MainRepository(mContext);
    }
    public void getLoginData()
    {
        getLogin()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                               @Override
                               public void call(String loginData) {
                                    mMainView.onSuccess(loginData);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mMainView.onError(R.string.problem_in_login);
                            }
                        }
                );
    }
    public Observable<String> getLogin() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Login Success");
                subscriber.onCompleted();
            }
        });
    }
}
