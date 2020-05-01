package com.tomerpacific.navigationcomponent

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityABtn : Button = findViewById(R.id.activty_a_btn)
        val activityBBtn : Button = findViewById(R.id.activity_b_btn)

        activityABtn.setOnClickListener {button ->
            val intentA = Intent(this, ActivityA::class.java)
            startActivity(intentA)
        }

        activityBBtn.setOnClickListener {button ->
            val intentA = Intent(this, ActivityB::class.java)
            startActivity(intentA)
        }
    }
}
