package com.tomerpacific.edittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
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

        edittext.setOnEditorActionListener {view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                Toast.makeText(this, "You typed " + edittext.text.toString(), Toast.LENGTH_SHORT).show()
                true
            }
            false
        }
    }


}
