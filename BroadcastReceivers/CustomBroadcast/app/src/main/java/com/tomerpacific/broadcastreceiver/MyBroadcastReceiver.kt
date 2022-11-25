package com.tomerpacific.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        intent.let {
            val data = it.extras?.getString("data")
            Toast.makeText(context, "Broadcast Received with data $data", Toast.LENGTH_LONG).show()
        }

    }
}