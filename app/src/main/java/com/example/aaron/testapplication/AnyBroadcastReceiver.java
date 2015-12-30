package com.example.aaron.testapplication;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class AnyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = AnyBroadcastReceiver.class.getSimpleName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        if (pIntent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            pContext.startService(new Intent(pContext, BroadcastMonitoringService.class));
        }

        Log.d(TAG, "Received Broadcast's intent is: " + pIntent.toString());
        String action = pIntent.getAction();

        saveReceivedBroadcastDetails(pContext, action);
    }

    private void saveReceivedBroadcastDetails(Context pContext, String pAction) {
        // TODO(agf): Refactor this block
        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "mytestdir");

                dir.mkdirs();

                File file = new File(dir, "mytestfile");

                String timestamp = dateFormat.format(Calendar.getInstance().getTime());
                String messageToWrite = timestamp + " " + pAction + "\n";

                FileOutputStream outputStream = new FileOutputStream(file, true);
                outputStream.write(messageToWrite.getBytes());
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "Saved broadcast: Action: " + pAction + ", Time: " + dateFormat.format(Calendar.getInstance().getTime()));
    }
}
