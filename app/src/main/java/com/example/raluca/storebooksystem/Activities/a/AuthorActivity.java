package com.example.raluca.storebooksystem.Activities.a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.raluca.storebooksystem.R;

public class AuthorActivity extends AppCompatActivity {

    private WebView webView;
    private String authorURL;
    private static final String TAG = "AuthorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        Intent intent = getIntent();
        authorURL = (String) intent.getStringExtra("authorURL");

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        Log.i(TAG, "Load URL for author image");
        webView.loadUrl(authorURL);


    }
}
