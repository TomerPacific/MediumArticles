package com.tomerpacific.androidinternalstorage

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : Activity() {

    private val FILENAME = "hello_world_file"
    private val inputToFile = "Hello From Internal Storage!"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun saveFileInternalStorage(view: View?) {
        try {
            val fileOutputStream = openFileOutput(FILENAME, MODE_PRIVATE)
            fileOutputStream.write(inputToFile.toByteArray())
            fileOutputStream.close()
            Toast.makeText(
                applicationContext,
                "File $FILENAME has been saved successfully",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(
                applicationContext,
                "File " + FILENAME + " has not been saved successfully due to an exception " + e.localizedMessage,
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                applicationContext,
                "File " + FILENAME + " has not been saved successfully due to an exception " + e.localizedMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}