package com.agfeldman.onofflog;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(View view) {
        refresh();
    }

    public void start(View view) {
        startService(new Intent(this, BroadcastMonitoringService.class));
        refresh();
    }

    public void stop(View view) {
        stopService(new Intent(this, BroadcastMonitoringService.class));
        refresh();
    }

    // From <http://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-on-android>
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isBroadcastRunning() {
        return isMyServiceRunning(BroadcastMonitoringService.class);
    }

    private void refresh() {
        TextView textView = (TextView) findViewById(R.id.message);
        textView.setText(R.string.refreshing_message);
        if (isBroadcastRunning()) {
            textView.setText(R.string.running_message);
        } else {
            textView.setText(R.string.stopped_message);
        }
    }
}
