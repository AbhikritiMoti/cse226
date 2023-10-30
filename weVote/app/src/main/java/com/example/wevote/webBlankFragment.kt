package com.example.wevote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class webBlankFragment : Fragment() {
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_web_blank, container, false)
        webView = rootView.findViewById(R.id.webView)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient()
        webView.loadUrl("https://github.com/abhikritimoti/")

        return rootView
    }


    private inner class MyWebViewClient : WebViewClient() {
        @SuppressLint("NewApi")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

            if (url != null && url.contains("button_click_event")) {
                return true
            }
            return false
        }
    }
}
