//  Created by Tomer Ben-Rachel on 07/07/2018.
//  Copyright © 2018 Tomer Ben-Rachel. All rights reserved.


package com.example.communicator.jscommunicator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.web_view);

        webView.loadUrl("file:///android_asset/index.html");
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new WebViewJavascriptInterface(this), "myOwnJSHandler");
    }

    public class WebViewJavascriptInterface {

        private Context context;

        public WebViewJavascriptInterface(Context _context) {
            context = _context;
        }

        @JavascriptInterface
        public void receiveMessageFromJS(String message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
}
