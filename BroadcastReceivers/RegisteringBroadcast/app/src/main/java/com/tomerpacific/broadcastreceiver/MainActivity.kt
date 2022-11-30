package com.tomerpacific.broadcastreceiver

import android.support.v7.app.AppCompatActivity
import android.content.BroadcastReceiver
import com.tomerpacific.broadcastreceiver.MyBroadcastReceiver
import android.os.Bundle
import com.tomerpacific.broadcastreceiver.R
import android.content.IntentFilter
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var sendBroadcastBtn: Button? = null
    private val myBroadcastReceiver: BroadcastReceiver = MyBroadcastReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentFilter = IntentFilter("MY_ACTION")
        this.registerReceiver(myBroadcastReceiver, intentFilter)
        sendBroadcastBtn = findViewById(R.id.sendBroadcastBtn)
        sendBroadcastBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent("MY_ACTION")
            intent.putExtra("data", "Hello World!")
            sendBroadcast(intent)
        })
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadcastReceiver)
    }
}