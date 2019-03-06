package com.innominds.driverassist.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innominds.driverassist.OnActionListner;
import com.innominds.driverassist.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AvailableDeviceAdapter extends RecyclerView.Adapter<AvailableDeviceAdapter.ViewHolder> {
    Context mContext;
    ArrayList<HashMap<String, String>> arrayList;
   String[] s;

    AvailableDeviceAdapter(Context mContext, String[] s) {
        this.mContext=mContext;
        this.s=s;
    }


    @NonNull
    @Override
    public AvailableDeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.available_device_list_item,viewGroup,false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableDeviceAdapter.ViewHolder deviceViewHolder, int i)
    {
            deviceViewHolder.tvDeviceName.setText(s[i]);

    }

    @Override
    public int getItemCount() {
        return  s.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDevice;
        TextView tvDeviceName;
        ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ivDevice=(ImageView)itemView.findViewById(R.id.ivDevice);
            tvDeviceName=(TextView)itemView.findViewById(R.id.tvDeviceName);
        }
    }
}
