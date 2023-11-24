package com.tomerpacific.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    private val TAG = BroadcastReceiver::class.java.simpleName
    override fun onReceive(context: Context, intent: Intent) {
        val data = intent.extras?.getString("data")
        Toast.makeText(context, "Broadcast Received with data $data", Toast.LENGTH_LONG).show()
    }
}