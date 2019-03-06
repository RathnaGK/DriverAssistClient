package com.innominds.driverassist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.innominds.driverassist.OnActionListner;
import com.innominds.driverassist.R;
import com.innominds.driverassist.presenters.MainPresenter;
import com.innominds.driverassist.ui.CameraActivity;
import com.innominds.driverassist.utils.AppConfig;
import com.innominds.driverassist.utils.Utils;
import com.innominds.driverassist.views.MainFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class MainFragment extends Fragment implements MainFragmentView {
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.pbProgress)
    ProgressBar pbProgress;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.tvRegisterUser)
    TextView tvRegisterUser;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    private MainPresenter mMainPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.mainfragment,container,false);
        ButterKnife.bind(this,mView);
        tvVersion.setText(getString(R.string.app_version,AppConfig.getCurrAppVerName(getActivity())+""));
        mMainPresenter=new MainPresenter(getContext(),this);
        setHasOptionsMenu(true);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMessage(-1, false);
                if (etUserName.getText()!=null && etUserName.getText().toString().trim().length()==0)
                {
                    etUserName.requestFocus();
                    etUserName.setError(getString(R.string.enter_valid_username));
                    return;
                }
                else {
                    etUserName.setError(null);
                }
                if(etPwd.getText()!=null && etPwd.getText().toString().trim().length()==0)
                {
                    etPwd.requestFocus();
                    etPwd.setError(getString(R.string.password_is_required));
                    return;
                }
                else {
                    etUserName.setError(null);
                }
                pbProgress.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                tvRegisterUser.setVisibility(View.INVISIBLE);
                btnRegister.setVisibility(View.INVISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMainPresenter.getLoginData();
                    }
                }, 1000);
                try {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OnActionListner)getActivity()).onRegisterSelected();
            }
        });
        return mView;

    }



    @Override
    public void onError(int resID) {
        pbProgress.setVisibility(View.INVISIBLE);
        btnLogin.setEnabled(true);
        setMessage(resID, true);
    }


    @Override
    public void onSuccess(String resID)
    {
        pbProgress.setVisibility(View.INVISIBLE);
        Utils.showMessage(getActivity().getWindow().getDecorView(),getContext(),resID);
        etUserName.setText("");
        etPwd.setText("");
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //((OnActionListner)getActivity()).onRegisterSuccessSelected();
                Intent mIntent = new Intent(getActivity(), CameraActivity.class);
                startActivity(mIntent);
            }
        },2000);

    }
    private void setMessage(int messageRes, boolean error) {
        if (messageRes == -1)
            tvMessage.setText("");
        else
            tvMessage.setText(messageRes);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Welcome");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }
}
