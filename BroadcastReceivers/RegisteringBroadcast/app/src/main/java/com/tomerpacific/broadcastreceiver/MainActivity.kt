package com.tomerpacific.broadcastreceiver

import androidx.appcompat.app.AppCompatActivity
import android.content.BroadcastReceiver
import android.os.Bundle
import android.content.IntentFilter
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val myBroadcastReceiver: BroadcastReceiver = MyBroadcastReceiver()
    private val ACTION_KEY: String = "MY_ACTION"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentFilter = IntentFilter(ACTION_KEY)
        this.registerReceiver(myBroadcastReceiver, intentFilter)
        findViewById<Button?>(R.id.sendBroadcastBtn).apply {
            setOnClickListener(View.OnClickListener {
                val intent = Intent(ACTION_KEY)
                intent.putExtra("data", "Hello World!")
                sendBroadcast(intent)
            })
        }

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadcastReceiver)
    }
}