package com.innominds.driverassist.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppConfig {
    public static String getCurrAppVerName(Context context) {
        PackageInfo pInfo = null;
        String versionName = "";
        try {
            pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionName = pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // Log.e(TAG, e.getMessage());
        }

        return versionName;
    }
}
