package ru.mirea.makarov.workmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;

public class UploadWorker extends Worker {
    static final String TAG = "UploadWorker";
    private WifiManager wifiManager;
    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }
    @Override
    public Result doWork() {
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Bundle bundle = new Bundle();
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED){
             Log.d(TAG, "Wifi is enabled");
        }else{
            Log.d(TAG, "Wifi is DISABLED");
        }
        /*Log.d(TAG, "doWork: start");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "doWork: end");*/
        return Result.success();
    }

}
