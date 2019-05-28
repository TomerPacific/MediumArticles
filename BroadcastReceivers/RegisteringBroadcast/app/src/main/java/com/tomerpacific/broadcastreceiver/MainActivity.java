package com.tomerpacific.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button sendBroadcastBtn;
    private BroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final IntentFilter intentFilter = new IntentFilter("MY_ACTION");
        this.registerReceiver(myBroadcastReceiver, intentFilter);

        sendBroadcastBtn = findViewById(R.id.sendBroadcastBtn);
        sendBroadcastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("MY_ACTION");
                intent.putExtra("data", "Hello World!");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(myBroadcastReceiver);
    }
}
