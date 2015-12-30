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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnyBroadcastReceiver extends BroadcastReceiver {
    /*
    public AnyBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */

    private static final String TAG = AnyBroadcastReceiver.class.getSimpleName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static final String EXTRA_ACTION = "EXTRA_ACTION";
    public static final String EXTRA_EXTRAS = "EXTRA_EXTRAS";

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        if (pIntent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            pContext.startService(new Intent(pContext, BroadcastMonitoringService.class));
        }

        Log.d(TAG, "Received Broadcast's intent is: " + pIntent.toString());
        String action = pIntent.getAction();
        String extrasString = getExtrasString(pIntent);

        saveReceivedBroadcastDetails(pContext, action, extrasString);

        // TODO implement setting so this would be optional vibrate(pContext);

        // BroadcastMonitoringService.showNotification(pContext, action);
    }

    /**
     * @param pContext
     * @param pAction
     * @param pExtrasString
     */
    private void saveReceivedBroadcastDetails(Context pContext, String pAction, String pExtrasString) {
        /*ContentValues values = new ContentValues();
        values.put(BroadcastTable.COLUMN_ACTION, pAction);
        values.put(BroadcastTable.COLUMN_EXTRAS, pExtrasString);
        values.put(BroadcastTable.COLUMN_TIMESTAMP,
                dateFormat.format(Calendar.getInstance().getTime()));

        pContext.getContentResolver().insert(BroadcastContentProvider.CONTENT_URI, values);*/

        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "mytestdir");

                dir.mkdirs();

                File file = new File(dir, "mytestfile");

                // Intent intent = getIntent();
                // String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
                // String messageToWrite = message + "\n";

                String timestamp = dateFormat.format(Calendar.getInstance().getTime());
                String messageToWrite = timestamp + " " + pAction;
                if (pExtrasString != "") {
                    messageToWrite += " " + pExtrasString;
                }
                messageToWrite += "\n";

                FileOutputStream outputStream = new FileOutputStream(file, true);
                outputStream.write(messageToWrite.getBytes());
                outputStream.close();

                /*TextView textView = new TextView(this);
                textView.setTextSize(40);
                textView.setText(message);
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
                layout.addView(textView);*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i(TAG, "Saved broadcast: Action: " + pAction  + ", Extras: " + pExtrasString + ", Time: " + dateFormat.format(Calendar.getInstance().getTime()));
    }

    private String getExtrasString(Intent pIntent) {
        String extrasString = "";
        // TODO(agf): Enable extras
        /*
        Bundle extras = pIntent.getExtras();
        try {
            if (extras != null) {
                Set<String> keySet = extras.keySet();
                for (String key : keySet) {
                    try {
                        String extraValue = pIntent.getExtras().get(key).toString();
                        extrasString += key + ": " + extraValue + "\n";
                    } catch (Exception e) {
                        Log.d(TAG, "Exception 2 in getExtrasString(): " + e.toString());
                        extrasString += key + ": Exception:" + e.getMessage() + "\n";
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in getExtrasString(): " + e.toString());
            extrasString += "Exception:" + e.getMessage() + "\n";
        }
        */
        Log.d(TAG, "extras=" + extrasString);
        return extrasString;
    }

    // private void vibrate(Context pContext) {
    // Vibrator vibrator = (Vibrator)pContext.getSystemService(Service.VIBRATOR_SERVICE);
    // vibrator.vibrate(30);
    // }
}
