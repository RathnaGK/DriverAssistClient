package com.innominds.driverassist.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.innominds.driverassist.OnActionListner;
import com.innominds.driverassist.R;
import com.innominds.driverassist.model.DeviceData;
import com.innominds.driverassist.presenters.AddDevicePresenter;
import com.innominds.driverassist.utils.Utils;
import com.innominds.driverassist.views.AddDeviceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDeviceFragment extends Fragment implements AddDeviceView {
    @BindView(R.id.rvDevice)
    RecyclerView rvDevice;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.pbProgress)
    ProgressBar pbProgress;
    WifiManager wifi;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    List<ScanResult> results;
    String ITEM_KEY = "key";
    ArrayList<HashMap<String, String>>   arraylist = new ArrayList<HashMap<String, String>>();
    int size = 0;
    public static final String PSK = "PSK";
    public static final String WEP = "WEP";
    public static final String OPEN = "Open";
    private WifiScanReceiver mWifiScanReceiver;
    private IntentFilter intentFilter;
    AddDeviceAdapter mAddDeviceAdapter;
    private AddDevicePresenter mAddDevicePresenter;
    protected String mSSID ="";
    ProgressDialog mProgressDialog;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.add_devicefragment,container,false);
        ButterKnife.bind(this,mView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Device");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
       rvDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiScanReceiver = new WifiScanReceiver();

        intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mAddDevicePresenter = new AddDevicePresenter(getActivity(),this);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mProgressDialog.setCancelable(false);

        return mView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!arraylist.isEmpty())
        {
            mAddDeviceAdapter = new AddDeviceAdapter();
            rvDevice.setAdapter(mAddDeviceAdapter);
            mAddDeviceAdapter.notifyDataSetChanged();
        }
        else
        {
            scanAP();
            mAddDeviceAdapter = new AddDeviceAdapter();
            rvDevice.setAdapter(mAddDeviceAdapter);
        }
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
    public void onDeviceData(DeviceData deviceData) {
        mProgressDialog.dismiss();
        ((OnActionListner)getActivity()).onDeviceSelected(mSSID,deviceData);
    }

    @Override
    public void onError(int resID) {
        mProgressDialog.dismiss();
        Utils.showError(getActivity().getWindow().getDecorView(),getContext(),resID);

    }

    @Override
    public void onProgress(int resID) {

    }

    @Override
    public void onSuccess(int resID) {

    }

    private class WifiScanReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().unregisterReceiver(this);
            results = wifi.getScanResults();
            size = results.size();
            pbProgress.setVisibility(View.GONE);
            tvEmpty.setText("");
            try {
                size = size - 1;
                while (size >= 0) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put(ITEM_KEY,
                            results.get(size).SSID);
                    //item.put(ITEM_KEY, results.get(size).SSID + "  " + results.get(size).capabilities);
                    if(!arraylist.contains(item)){
                        arraylist.add(item);
                    }
                    size--;
                    mAddDeviceAdapter.notifyDataSetChanged();
                    pbProgress.setVisibility(View.GONE);
                    tvEmpty.setText("");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void scanAP()
    {
        if (wifi.isWifiEnabled() == false) {
            tvMessage.setText(R.string.wifi_is_disabled_enabling_it);
            wifi.setWifiEnabled(true);
        }
        getActivity().registerReceiver(mWifiScanReceiver,intentFilter);
        wifi.startScan();
        pbProgress.setVisibility(View.VISIBLE);
        tvEmpty.setText(R.string.searching_nearby_devices);
    }
    public class AddDeviceAdapter extends RecyclerView.Adapter<AddDeviceAdapter.ViewHolder> {

        public AddDeviceAdapter() {

        }


        @Override
        public AddDeviceAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
            View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.available_device_list_item,viewGroup,false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder, final  int position) {
            String hashValue=arraylist.get(position).get(ITEM_KEY);

                viewHolder.tvDeviceName.setText(hashValue);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        tvMessage.setText("");
                        mSSID = arraylist.get(position).get(ITEM_KEY);
                        for (ScanResult scanResult : results) {
                            if (scanResult.SSID.equals(mSSID)) {
                                String security  = getScanResultSecurity(scanResult);
                                if(security.equalsIgnoreCase(OPEN))
                                {
                                    final WifiConfiguration conf = new WifiConfiguration();
                                    conf.SSID = "\"" + mSSID + "\"";
                                    conf.allowedKeyManagement
                                            .set(WifiConfiguration.KeyMgmt.NONE);
                                    int netId = wifi.addNetwork(conf);
                                    IntentFilter intentFilter = new IntentFilter();
                                    intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
                                    getActivity().registerReceiver(mConnectionBroadcastReceiver, intentFilter);
                                    wifi.disconnect();
                                    wifi.enableNetwork(netId, true);
                                    wifi.reconnect();
                                }
                                else
                                {
                                    tvMessage.setText(R.string.not_a_valid_device);
                                }
                            }
                        }
                    }
                });


        }

        @Override
        public int getItemCount() {
            return arraylist.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivDevice;
            TextView tvDeviceName;
            ViewHolder( View itemView)
            {
                super(itemView);
                ivDevice=(ImageView)itemView.findViewById(R.id.ivDevice);
                tvDeviceName=(TextView)itemView.findViewById(R.id.tvDeviceName);
            }
        }
    }
    private BroadcastReceiver mConnectionBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                Bundle extras = intent.getExtras();
                NetworkInfo networkInfo = extras != null ? (NetworkInfo) extras
                        .getParcelable("networkInfo") : null;
                if (networkInfo == null
                        || (networkInfo.getState() != NetworkInfo.State.CONNECTED
                        && networkInfo.getState() != NetworkInfo.State.CONNECTING && networkInfo
                        .getState() != NetworkInfo.State.DISCONNECTED))
                    return;
                WifiInfo wifiInfo = extras.getParcelable("wifiInfo");
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED
                        && wifiInfo == null)
                    return;
                if (networkInfo.isConnected()) {
                    mProgressDialog.show();
                    mProgressDialog.setMessage("Loading");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvMessage.setText(mSSID);
                            mAddDevicePresenter.getDeviceData();
                        }
                    },3000);
                } else if (networkInfo.getState() == NetworkInfo.State.CONNECTING) {
                    tvMessage.setText(getResources().getString(R.string.connecting_to,mSSID));
                } else {
                    tvMessage.setText(getResources().getString(R.string.could_not_connect_to,mSSID));
                }
            }
            getActivity().unregisterReceiver(this);
        }
    };
    private String getScanResultSecurity(ScanResult scanResult) {
        final String cap = scanResult.capabilities;
        final String[] securityModes = {WEP, PSK};
        for (int i = securityModes.length - 1; i >= 0; i--) {
            if (cap.contains(securityModes[i])) {
                return securityModes[i];
            }
        }
        return OPEN;
    }
}
