package com.tomerpacific.camera2api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView

class MainActivity : AppCompatActivity() {

    private lateinit var textureView: TextureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textureView = findViewById(R.id.texture_view)
    }
}