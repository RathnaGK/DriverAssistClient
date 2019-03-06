package com.innominds.driverassist.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.innominds.driverassist.OnActionListner;
import com.innominds.driverassist.R;
import com.innominds.driverassist.presenters.RegisterPresenter;
import com.innominds.driverassist.utils.Utils;
import com.innominds.driverassist.views.RegisterFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class RegisterFragment extends Fragment implements RegisterFragmentView {

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.pbProgress)
    ProgressBar pbProgress;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    private RegisterPresenter mRegisterPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mview=inflater.inflate(R.layout.registerfragment,container,false);
        ButterKnife.bind(this,mview);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Register");
        setHasOptionsMenu(true);

        mRegisterPresenter=new RegisterPresenter(getActivity(),this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMessage(-1,false);
                if (etUserName.getText()!=null && etUserName.getText().toString().trim().length()==0)
                {
                    etUserName.requestFocus();
                    etUserName.setError(getString(R.string.enter_valid_username));
                    return;
                }
                if(etPwd.getText()!=null && etPwd.getText().toString().trim().length()==0)
                {
                    etPwd.requestFocus();
                    etPwd.setError(getString(R.string.password_is_required));
                    return;
                }
                pbProgress.setVisibility(View.VISIBLE);
                btnRegister.setEnabled(false);
               Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRegisterPresenter.registerUser();
                    }
                }, 1000);
                //mRegisterPresenter.registerUser();
                try {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }
        });

        return mview;
    }

    @Override
    public void onError(int resID) {
        pbProgress.setVisibility(View.INVISIBLE);
        btnRegister.setEnabled(true);
        setMessage(resID, true);
    }

    @Override
    public void onSuccess(String resID) {
        pbProgress.setVisibility(View.GONE);
        Utils.showMessage(getActivity().getWindow().getDecorView(),getContext(),resID);
        etUserName.setText("");
        etPwd.setText("");
        ((OnActionListner)getActivity()).onLoginSelected();
    }
    private void setMessage(int messageRes, boolean error) {
        if (messageRes == -1)
            tvMessage.setText("");
        else
            tvMessage.setText(messageRes);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStackImmediate();
                break;
        }
        return true;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
    }

}
