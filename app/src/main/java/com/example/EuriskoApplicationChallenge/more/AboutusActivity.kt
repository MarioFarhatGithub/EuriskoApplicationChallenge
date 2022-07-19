package com.example.EuriskoApplicationChallenge.more

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.EuriskoApplicationChallenge.R
import kotlinx.android.synthetic.main.activity_aboutus.*

class AboutusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutus)
        webViewSetup()
    }

    private fun webViewSetup(){
        aboutus_webview.webViewClient = WebViewClient()

        aboutus_webview.apply{
            loadUrl("https://euriskomobility.com/")
        }
    }
}