package com.example.aaron.testapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FileOutputStream outputStream;
        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "mytestdir");

                dir.mkdirs();

                File file = new File(dir, "mytestfile");

                Intent intent = getIntent();
                String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
                String messageToWrite = message + "\n";

                outputStream = new FileOutputStream(file, true);
                outputStream.write(messageToWrite.getBytes());
                outputStream.close();

                TextView textView = new TextView(this);
                textView.setTextSize(40);
                textView.setText(message);
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
                layout.addView(textView);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
