//  Created by Tomer Ben-Rachel on 07/07/2018.
//  Copyright © 2018 Tomer Ben-Rachel. All rights reserved.
package com.example.communicator.jscommunicator

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.JavascriptInterface
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView = findViewById<View>(R.id.web_view) as WebView
        webView.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(WebViewJavascriptInterface(context), "myOwnJSHandler")
            loadUrl("file:///android_asset/index.html")
        }
    }

    inner class WebViewJavascriptInterface(private val context: Context) {
        @JavascriptInterface
        fun receiveMessageFromJS(message: String?) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}