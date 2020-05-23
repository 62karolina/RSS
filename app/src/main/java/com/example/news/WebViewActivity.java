package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    Document doc;
    //String html ;
    final String mime = "text/html";
    final String encoding = "utf-8";
    String page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        //Display "Loading..." message while waiting
        //Invoke the AsyncTask
        Intent intent = getIntent();
        String html = intent.getStringExtra("keyName");

        webView = findViewById(R.id.webView);

        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(html);

    }


}
