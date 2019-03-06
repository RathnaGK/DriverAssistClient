package com.innominds.driverassist.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.innominds.driverassist.R;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Utils {
    public static void showError(View view, Context context, int messageRes) {
        showError(view, context, context.getString(messageRes));
    }


    public static void showError(View view, Context context, String message) {
        showSnackBar(context, view, message, android.R.color.holo_red_dark);
    }

    public static void showMessage(View view, Context context, String message) {
        showSnackBar(context, view, message, R.color.salem);
    }

    public static void showMessage(View view, Context context, int messageRes) {
        showSnackBar(context, view, context.getString(messageRes), R.color.salem);
    }

    public static void showSnackBar(Context context, View view, String text, int colorRes) {
        Snackbar sb = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        sb.getView().setBackgroundColor(ContextCompat.getColor(context, colorRes));
        TextView tv = (TextView) sb.getView().findViewById(android.support.design.R.id.snackbar_text);
        if (tv != null)
            tv.setTextSize(context.getResources().getDimension(R.dimen._9ssp) / context.getResources().getDisplayMetrics().density);
        tv.setTextColor(Color.WHITE);
        sb.show();
    }

    public static String getApIpAddr(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        byte[] ipAddress = convert2Bytes(dhcpInfo.serverAddress);
        try {
            String apIpAddr = InetAddress.getByAddress(ipAddress).getHostAddress();
            return apIpAddr;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] convert2Bytes(int hostAddress) {
        byte[] addressBytes = {(byte) (0xff & hostAddress),
                (byte) (0xff & (hostAddress >> 8)),
                (byte) (0xff & (hostAddress >> 16)),
                (byte) (0xff & (hostAddress >> 24))};
        return addressBytes;
    }

}
