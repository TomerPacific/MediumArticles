package com.tomerpacific.alertdialog

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Alert Dialog with a long title example
        alertDialogWithLongTitle()

        //Alert Dialog with a list of items example
        //alertDialogWithList()

        //Alert Dialog with an image
        //alertDialogWithImage()

    }

    private fun alertDialogWithLongTitle() {
        val dialog = AlertDialog.Builder(this)
        val textView = TextView(this)

        with(textView) {
            textView.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nisi neque, pulvinar sit amet tortor vitae, porta suscipit neque."
            textView.textSize = 20.0F
            textView.setTypeface(null, Typeface.BOLD)
            textView.gravity = Gravity.CENTER
        }

        dialog.setCustomTitle(textView)
        dialog.setMessage("AlertDialog Message")
        dialog.setNeutralButton("Dismiss") {dialog, which ->
            //Neutral button click logic
        }
        dialog.show()
    }

    private fun alertDialogWithList() {
        val listAdapter = AlertDialogListAdapter(this)
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Items List")
        dialog.setAdapter(listAdapter, {dialog, which ->
            //Clicking on list element logic here
        })

        dialog.setNeutralButton("Dismiss") {dialog, which ->
            //Click on dismiss button logic here
        }
        dialog.show()
    }

    private fun alertDialogWithImage() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Custom View")
        val customView = layoutInflater.inflate(R.layout.custom_view, null)
        dialog.setView(customView)
        dialog.setNeutralButton("Dismiss") {dialog, which ->
            //Click on dismiss button logic here
        }
        dialog.show()
    }
}
