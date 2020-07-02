package com.trendsbay.atence;

import android.util.Log;

import java.io.DataOutputStream;
import java.net.Socket;

public class SendData implements Runnable {

    private int Data = 0;
    public boolean isSuccessFul;
    public SendData (final int Data)    {
        this.Data = Data;
    }
    @Override
    public void run() {
        try{
            int socket = 4400;
            Socket s=new Socket("localhost", socket);
            DataOutputStream data_out =new DataOutputStream(s.getOutputStream());
            isSuccessFul = s.isConnected();
            s.close();
        }catch(Exception e){
            Log.e("Data Send : ", e.toString());
        }
    }
}
