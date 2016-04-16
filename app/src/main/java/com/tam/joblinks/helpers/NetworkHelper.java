package com.tam.joblinks.helpers;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.IOException;

/**
 * Created by toan on 4/11/2016.
 */
public class NetworkHelper {
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wifi.getConnectionInfo().getMacAddress();
        return (StringHelper.isNullOrEmpty(macAddress) ? "" : macAddress);
    }
}
