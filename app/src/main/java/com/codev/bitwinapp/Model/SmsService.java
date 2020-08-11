package com.codev.bitwinapp.Model;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import afu.org.checkerframework.checker.nullness.qual.Nullable;

public class SmsService extends Service {
    private MySmsReceiver smsReceiver = null;
    public static Runnable runnable = null;
    public Handler handler = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

                //Toast.makeText(getApplicationContext(), "service", Toast.LENGTH_SHORT).show();

                // Create an IntentFilter instance.
                IntentFilter intentFilter = new IntentFilter();
                // Add network connectivity change action.
                intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
                Toast.makeText(getApplicationContext(), "wah", Toast.LENGTH_SHORT).show();
                // Set broadcast receiver priority.
                intentFilter.setPriority(100);
                // Create a network change broadcast receiver.
                smsReceiver = new MySmsReceiver();
                // Register the broadcast receiver with the intent filter object.
                registerReceiver(smsReceiver, intentFilter);
                handler.postDelayed(runnable, 10000);

            }

        };

        handler.postDelayed(runnable, 15000);

    }
    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }


}