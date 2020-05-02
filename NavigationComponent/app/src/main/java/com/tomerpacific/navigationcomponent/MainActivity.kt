package com.tomerpacific.navigationcomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myFragment : StartFragment = StartFragment()
        supportFragmentManager.beginTransaction().add(R.id.container, myFragment).addToBackStack(null).commit()
    }
}
