package com.innominds.driverassist.presenters;

import android.content.Context;
import android.os.Handler;

import com.innominds.driverassist.OnActionListner;
import com.innominds.driverassist.R;
import com.innominds.driverassist.repositories.RegisterRepository;
import com.innominds.driverassist.views.RegisterFragmentView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RegisterPresenter {
    private Context mContext;
    private RegisterFragmentView mRegisterView;
    private RegisterRepository mRegisterRepository;
    private OnActionListner mOnActionListner;
    public RegisterPresenter(Context mContext,RegisterFragmentView mRegisterView)
    {
        this.mContext=mContext;
        this.mRegisterView=mRegisterView;
        mRegisterRepository=new RegisterRepository(mContext);

    }

    public void registerUser()
    {
        getRegister()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                               @Override
                               public void call(String registerData) {
                                   mRegisterView.onSuccess(registerData);
                                  Handler handler = new Handler();
                                   handler.postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           ((OnActionListner)mContext).onLoginSelected();
                                       }
                                   }, 2000);

                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mRegisterView.onError(R.string.problem_in_login);
                            }
                        }
                );
    }
    public Observable<String> getRegister() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("Registered Successfully");
                subscriber.onCompleted();
            }
        });
    }

}
