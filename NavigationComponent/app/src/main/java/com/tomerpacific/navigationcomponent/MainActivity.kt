package com.tomerpacific.navigationcomponent

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityABtn : Button = findViewById(R.id.fragment_a_btn)
        val activityBBtn : Button = findViewById(R.id.fragment_b_btn)

        activityABtn.setOnClickListener {button ->
            val myFragment : FragmentA = FragmentA()
            supportFragmentManager.beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit()
        }

        activityBBtn.setOnClickListener {button ->
            val myFragment : FragmentB = FragmentB()
            supportFragmentManager.beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit()
        }
    }
}
