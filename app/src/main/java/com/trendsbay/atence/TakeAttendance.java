package com.trendsbay.atence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TakeAttendance extends AppCompatActivity {

    private Spinner ClassCodes;
    private WifiP2pManager WIFIP2P;
    private WifiP2pManager.Channel channel;
    private WifiManager WIFI;
    private IntentFilter intentFilter;
    private Receiver receiver;

    private static String[] ClassList = {"CS658", "CS897", "CS908"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_take_attendance);
            }
        }).start();

        // I think Donno I just use thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                Initialise(); // initialising objects
            }
        }).start();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ClassList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ClassCodes.setAdapter(arrayAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void Initialise() {
        ClassCodes = findViewById(R.id.class_codes);

        WIFI = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WIFIP2P = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE); // Casting Wifi Manager.

        intentFilter = new IntentFilter();

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);   // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);   // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);  // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION); // Indicates this device's details have changed.

        channel = WIFIP2P.initialize(this, getMainLooper(), null);

        receiver = new Receiver(this, WIFI, WIFIP2P);
    }

    public void Start(View view) {
        registerReceiver(receiver, intentFilter);
    }
}