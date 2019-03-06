package com.innominds.driverassist.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.innominds.driverassist.OnActionListner;
import com.innominds.driverassist.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.rvAvailableDevice)
    RecyclerView rvAvailableDevice;
    WifiManager wifi;
    List<ScanResult> results;
    String ITEM_KEY = "key";
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    protected String mSSID ="";
    int size = 0;
    public static final String PSK = "PSK";
    public static final String WEP = "WEP";
    public static final String OPEN = "Open";
    private WifiScanReceiver mWifiScanReceiver;
    private IntentFilter intentFilter;
    AvailableDeviceAdapter mAvailableDeviceAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.homefragment,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Welcome innominds");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this,mView);
        wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiScanReceiver = new WifiScanReceiver();
        intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        rvAvailableDevice.setLayoutManager(new LinearLayoutManager(getActivity()));
        String s[] ={"device 1","device2","device3","device 4","device 5","device 6"};

         mAvailableDeviceAdapter = new AvailableDeviceAdapter(getContext(),s);
        rvAvailableDevice.setAdapter(mAvailableDeviceAdapter);
        return mView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addDevice:
                ((OnActionListner)getActivity()).onAddDeviceSelected();
                break;
        }
        return true;
    }

    private class WifiScanReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            getActivity().unregisterReceiver(this);
            results = wifi.getScanResults();
            size = results.size();
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
                    mAvailableDeviceAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
