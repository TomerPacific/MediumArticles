package com.tomerpacific.broadcastreceiver

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val receiver: MyBroadcastReceiver = MyBroadcastReceiver()
    private val intentFilter: IntentFilter = IntentFilter("com.example.broadcast.MY_NOTIFICATION")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button?>(R.id.sendBroadcastBtn).apply {
            setOnClickListener {
                val intent = Intent()
                intent.action = "com.example.broadcast.MY_NOTIFICATION"
                intent.putExtra("data", "Hello World!")
                sendBroadcast(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}