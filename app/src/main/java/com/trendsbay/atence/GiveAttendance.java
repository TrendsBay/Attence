package com.trendsbay.atence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GiveAttendance extends AppCompatActivity {

    private TextView ClassCode, TeacherName;
    private WifiManager WIFI;
    private WifiP2pManager WIFIP2P;
    private WifiP2pManager.Channel channel;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_attendance);

        Initialise(); // Initialising Variable
        TurOnWifi();

        String _class_code = "NotClass Going On",
                _teacher_name = "No Teacher Available";

        SetText(ClassCode, _class_code);
        SetText(TeacherName, _teacher_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void Initialise(){
        ClassCode = findViewById(R.id.class_code);
        TeacherName = findViewById(R.id.teacher_name);

        WIFI = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WIFIP2P = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE); // Casting Wifi Manager.

        intentFilter = new IntentFilter();
        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        channel = WIFIP2P.initialize(this, getMainLooper(), null);

    }


    private void TurOnWifi(){   if ( !WIFI.isWifiEnabled() )    WIFI.setWifiEnabled(true);  }   // Checking for Wifi Activation if not we will open it
    private void SetText(TextView textView, String content){    textView.setText(content);  }   // Set Text In Text View

    private String getHotspotIPAddress() {

        int ipAddress = Integer.reverseBytes( WIFI.getConnectionInfo().getIpAddress() );

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        try {
            return InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            return  "";
        }
    }

    // On Click for Button Give Attendance
    public void Start(View view)  {
        TurOnWifi();
    }
}