package com.example.wordlistapp.web;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wordlistapp.R;

public class WebActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_dictionary);

        webView = findViewById(R.id.wvDictionary);
        webView.loadUrl("https://dictionary.cambridge.org/dictionary/english/");

        WebSettings settings = webView.getSettings();
        String ua = settings.getUserAgentString();

        settings.setUserAgentString(ua);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

}
