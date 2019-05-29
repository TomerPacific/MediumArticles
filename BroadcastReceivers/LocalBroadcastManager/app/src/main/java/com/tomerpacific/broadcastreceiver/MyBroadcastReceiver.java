package com.tomerpacific.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = BroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getExtras().getString("data");
        Toast.makeText(context, "Broadcast Received with data " + data, Toast.LENGTH_LONG).show();
    }
}
