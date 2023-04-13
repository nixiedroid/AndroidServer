package com.nixiedroid.server.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class CheckMeteredConnection {
    Context context;

    public CheckMeteredConnection(Context context) {
        this.context = context;
    }
    private boolean meterConnectionCheck(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                if( wifiInfo.getNetworkId() == -1 ){
                    return false;
                }
                 return true; // Connected to an access point
            }
            else {
                  return false; // Wi-Fi adapter is OFF
            }
        } else {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return mWifi.isConnected();
        }
    }
}
