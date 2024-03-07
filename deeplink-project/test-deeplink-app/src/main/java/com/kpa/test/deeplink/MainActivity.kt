package com.kpa.test.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.testBtn).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("deeplink://detail")).apply {
                toUri(Intent.URI_INTENT_SCHEME)
            })
        }
       initWebView()
    }

    private fun initWebView() {
        findViewById<WebView>(R.id.testWebView).apply {
            settings.javaScriptEnabled = true
            loadUrl("file:///android_asset/index.html")
        }
    }
}