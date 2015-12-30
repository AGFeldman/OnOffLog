package com.example.aaron.testapplication;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class BroadcastMonitoringService extends Service {
    private static final String TAG = BroadcastMonitoringService.class.getSimpleName();
    private AnyBroadcastReceiver mAnyBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mAnyBroadcastReceiver = new AnyBroadcastReceiver();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent pIntent, int pFlags, int pStartId) {
        Log.d(TAG, "onStartCommand");
        registerAnyBroadcastReceiver();
        return Service.START_STICKY;
    }

    @Override
    // TODO(agf): This should log something, e.g., call saveReceivedBroadcastDetails
    public void onDestroy() {
        try {
            unregisterReceiver(mAnyBroadcastReceiver);
        } catch (Exception e) {
            // TODO Skipping Exception which might be raising when the receiver is not yet
            // registered
            Log.d(TAG,
                    "Skipping Exception which might be raising when the receiver is not yet registered");

        }
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    private void registerAnyBroadcastReceiver() {
        try {
            registerBroadcastReceiverForActions();
            registerBroadcastReceiverForActionsWithDataType();
            registerBroadcastReceiverForActionsWithSchemes();
            Log.d(TAG, "Registered receivers.");

        } catch (Exception e) {
            Log.d(TAG, "Exception while registering: " + e.getMessage());
        }
    }

    private void registerBroadcastReceiverForActions() {
        IntentFilter intentFilter = new IntentFilter();
        addAllKnownActions(intentFilter);
        registerReceiver(mAnyBroadcastReceiver, intentFilter);
    }

    private void registerBroadcastReceiverForActionsWithDataType()
            throws IntentFilter.MalformedMimeTypeException {
        IntentFilter intentFilter = new IntentFilter();

        // This needed for broadcasts like new picture, which is data type: "image/*"
        intentFilter.addDataType("*/*");

        addAllKnownActions(intentFilter);
        registerReceiver(mAnyBroadcastReceiver, intentFilter);
    }

    private void registerBroadcastReceiverForActionsWithSchemes() throws IntentFilter.MalformedMimeTypeException {
        IntentFilter intentFilter = new IntentFilter();

        // needed for uninstalls
        intentFilter.addDataScheme("package");

        // needed for file system mounts
        intentFilter.addDataScheme("file");

        // other schemes
        intentFilter.addDataScheme("geo");
        intentFilter.addDataScheme("market");
        intentFilter.addDataScheme("http");
        intentFilter.addDataScheme("tel");
        intentFilter.addDataScheme("mailto");
        intentFilter.addDataScheme("about");
        intentFilter.addDataScheme("https");
        intentFilter.addDataScheme("ftps");
        intentFilter.addDataScheme("ftp");
        intentFilter.addDataScheme("javascript");

        addAllKnownActions(intentFilter);
        registerReceiver(mAnyBroadcastReceiver, intentFilter);
    }

    /**
     * Since we don't want to filter which actions have data and which don't we register two
     * different receivers with full list of actions.
     *
     * @param pIntentFilter
     */
    private void addAllKnownActions(IntentFilter pIntentFilter) {
        // System Broadcast
        List<String> sysBroadcasts = Arrays.asList(getResources().getStringArray(R.array.system_broadcast));
        for (String sysBroadcast : sysBroadcasts) {
            pIntentFilter.addAction(sysBroadcast);
        }

        // Custom Broadcast
        List<String> customBroadcasts = Arrays.asList(getResources().getStringArray(R.array.system_broadcast));
        for (String customBroadcast : customBroadcasts) {
            pIntentFilter.addAction(customBroadcast);
        }
    }
}
