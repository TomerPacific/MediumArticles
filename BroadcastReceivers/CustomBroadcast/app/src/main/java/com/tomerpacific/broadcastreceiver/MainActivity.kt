package com.tomerpacific.broadcastreceiver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val CUSTOM_INTENT_ACTION: String = "com.example.broadcast.MY_NOTIFICATION"

    private val receiver: MyBroadcastReceiver = MyBroadcastReceiver()
    private val intentFilter: IntentFilter = IntentFilter(CUSTOM_INTENT_ACTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button?>(R.id.sendBroadcastBtn).apply {
            setOnClickListener {
                val intent = Intent()
                intent.action = CUSTOM_INTENT_ACTION
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