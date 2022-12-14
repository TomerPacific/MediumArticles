package com.tomerpacific.viewvisibility

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myCustomView = MyCustomView(applicationContext)
        myCustomView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)
        findViewById<Button>(R.id.addCustomViewBtn).apply {
            setOnClickListener {
                mainLayout.addView(myCustomView)
            }
        }
    }
}