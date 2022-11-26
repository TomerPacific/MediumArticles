package com.tomerpacific.broadcastreceiver

import android.support.v7.app.AppCompatActivity
import android.content.BroadcastReceiver
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.content.IntentFilter
import android.content.Intent
import android.view.View

class MainActivity : AppCompatActivity() {
    private val myBroadcastReceiver: BroadcastReceiver = MyBroadcastReceiver()
    private val ACTION_KEY: String = "MY_ACTION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(myBroadcastReceiver, IntentFilter(ACTION_KEY))
    }

    fun sendBroadcast(view: View?) {
        val intent = Intent(ACTION_KEY)
        intent.putExtra("data", "Hello World!")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myBroadcastReceiver)
    }
}