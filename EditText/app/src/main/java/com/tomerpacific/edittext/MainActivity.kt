package com.tomerpacific.edittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edittext : EditText = findViewById(R.id.edit_text)

        edittext.addTextChangedListener(object: TextWatcher {

            var delay : Long = 1000
            var timer = Timer()

            override fun afterTextChanged(p0: Editable?) {

                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        //Logic when user finishes typing
                    }
                }, delay)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer.cancel()
                timer.purge()
            }
        })
    }


}
