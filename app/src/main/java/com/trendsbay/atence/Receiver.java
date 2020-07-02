/*
*   AttendanceReceiver Class is basically a BroadcastReceiver
*   We are Extending BroadcastReceiver Class to overload some of it's method as per our need
*   Extended Details are in the code
*/

package com.trendsbay.atence;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class Receiver extends BroadcastReceiver {

    Context context; // We Need Context Here to Show Toast in certain Activity.
    WifiManager WIFI;
    WifiP2pManager WIFIP2P;

    // Contractor
    public Receiver(Context context, WifiManager WIFI, WifiP2pManager WIFIP2P){
        this.context =  context;
        this.WIFI = WIFI;
        this.WIFIP2P = WIFIP2P;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wifi P2P mode is enabled or not, alert
            // the Activity.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "WIFIP2P Is Enabled", Toast.LENGTH_SHORT).show();
            } else {

                if ( WIFI.isP2pSupported() )
                    TurnOnWifi();
                else
                    Toast.makeText(context, "This App is not Supported In your device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void TurnOnWifi(){   if ( !WIFI.isWifiEnabled() )    WIFI.setWifiEnabled(true);  }
}
