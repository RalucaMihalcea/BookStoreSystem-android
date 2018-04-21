package com.example.raluca.storebooksystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class AuthorActivity extends AppCompatActivity {

    private WebView webView;
    private String authorURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        Intent intent = getIntent();
        authorURL = (String) intent.getStringExtra("authorURL");

        webView= (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(authorURL);


    }
}
