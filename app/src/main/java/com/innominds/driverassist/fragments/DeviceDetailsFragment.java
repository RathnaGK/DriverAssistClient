package com.innominds.driverassist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.innominds.driverassist.R;
import com.innominds.driverassist.model.DeviceData;
import com.innominds.driverassist.presenters.DeviceDetailsFragmentPresenter;
import com.innominds.driverassist.ui.CameraActivity;
import com.innominds.driverassist.ui.InstallationActivity;
import com.innominds.driverassist.utils.Utils;
import com.innominds.driverassist.views.DeviceDetailsFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceDetailsFragment extends Fragment implements DeviceDetailsFragmentView{
    @BindView(R.id.tvDevice)
    TextView tvDevice;
    @BindView(R.id.tvSerialNumber)
    TextView tvSerialNumber;
    @BindView(R.id.tvBattery)
    TextView tvBattery;
    @BindView(R.id.tvOperator)
    TextView tvOperator;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvInternet)
    TextView tvInternet;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.pbProgress)
    ProgressBar pbProgress;
    @BindView(R.id.btnSetupCamera)
    Button btnSetupCamera;
    @BindView(R.id.cbAudioPrompt)
    CheckBox cbAudioPrompt;
    @BindView(R.id.btnSetupInstallation)
    Button btnSetupInstallation;

    DeviceData mDeviceData;
    private DeviceDetailsFragmentPresenter mDeviceDetailsFragmentPresenter;
    boolean checked;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.device_detailsfragment,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Device");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this,mView);
         mDeviceData = getArguments().getParcelable("Devicedata");
        tvDevice.setText(getArguments().getString("SSID"));
        tvSerialNumber.setText(mDeviceData.getSerialNumber());
        tvBattery.setText(mDeviceData.getBatteryLevel());
        tvInternet.setText(mDeviceData.isInternet()?"Yes":"No");
        tvOperator.setText(mDeviceData.getOperator());
        tvVersion.setText(mDeviceData.getFirmware());
        cbAudioPrompt.setChecked(Boolean.parseBoolean(mDeviceData.isAudioPrompt()?"true":"false"));

        mDeviceDetailsFragmentPresenter=new DeviceDetailsFragmentPresenter(getContext(),this);
        setHasOptionsMenu(true);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDeviceData!=null)
                {
                    mDeviceData.setAudioPrompt(cbAudioPrompt.isChecked());
                    pbProgress.setVisibility(View.VISIBLE);
                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          mDeviceDetailsFragmentPresenter.sendDataToServer(mDeviceData);
                      }
                  },1000);


                }
            }
        });
        btnSetupCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent mIntent = new Intent(getActivity(), CameraActivity.class);
                        startActivity(mIntent);
            }
        });
        btnSetupInstallation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), InstallationActivity.class);
                startActivity(mIntent);
            }
        });
        return mView;
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

    @Override
    public void onError(int resID) {
        pbProgress.setVisibility(View.INVISIBLE);
        Utils.showError(getActivity().getWindow().getDecorView(),getContext(),resID);
    }

    @Override
    public void onSuccess(int resID) {
       pbProgress.setVisibility(View.INVISIBLE);
        Utils.showMessage(getActivity().getWindow().getDecorView(),getContext(),resID);
    }
}
