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

    private val JAVASCRIPT_BRIDGE_NAME: String = "myOwnJSHandler"
    private val LOCAL_HTML_FILE_PATH = "file:///android_asset/index.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView = findViewById<View>(R.id.web_view) as WebView
        webView.apply {
            settings.javaScriptEnabled = true
            addJavascriptInterface(WebViewJavascriptInterface(context), JAVASCRIPT_BRIDGE_NAME)
            loadUrl(LOCAL_HTML_FILE_PATH)
        }
    }

    inner class WebViewJavascriptInterface(private val context: Context) {
        @JavascriptInterface
        fun receiveMessageFromJS(message: String?) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}