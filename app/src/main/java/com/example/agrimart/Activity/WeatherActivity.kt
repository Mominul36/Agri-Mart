package com.example.agrimart.Activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.agrimart.R

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val webView = findViewById<WebView>(R.id.weatherWebView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)


        webView.settings.javaScriptEnabled = true


        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = ProgressBar.GONE
            }
        }


        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = ProgressBar.GONE
                } else {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
            }
        }

        webView.loadUrl("https://www.msn.com/en-xl/weather/forecast/in-Dhaka,Bangladesh?loc=eyJyIjoiRGhha2EiLCJjIjoiQmFuZ2xhZGVzaCIsImkiOiJCRCIsImciOiJlbi14bCIsIngiOiI5MC4zMzQ2NDgxMzIzMjQyMiIsInkiOiIyMy43OTg3ODk5NzgwMjczNDQifQ%3D%3D&weadegreetype=C")  // Replace with your desired URL
    }
}
