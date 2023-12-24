package com.tomerpacific.external

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainActivity : Activity() {
    private val inputToFile = "Hello From Internal Storage!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val isExternalStorageWritable: Boolean
        get() {
            val state: String = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    fun saveFileExternalStorage(view: View?) {
        if (isExternalStorageWritable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    writeFileToExternalStorage()
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        0
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            0 -> {
                writeFileToExternalStorage()
            }
        }
    }

    private fun writeFileToExternalStorage() {
        val root: String = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/saved_files")
        if (!myDir.exists()) {
            myDir.mkdirs()
        }
        try {
            val file = File(myDir, "myfile.txt")
            val out = FileOutputStream(file)
            out.write(inputToFile.toByteArray())
            out.close()

            Toast.makeText(
                applicationContext,
                "File myfile.txt" + " has been saved successfully to external storage",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}