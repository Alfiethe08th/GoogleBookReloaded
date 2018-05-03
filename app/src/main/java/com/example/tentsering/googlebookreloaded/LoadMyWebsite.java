package com.example.tentsering.googlebookreloaded;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class LoadMyWebsite extends AppCompatActivity {

    ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_my_website);
        Intent intent = getIntent();
        String thatUrl = intent.getStringExtra(BookSearch.UNIQUE_MESSAGE);

        WebView webView = (WebView)findViewById(R.id.myWeb);
        webView.loadUrl(thatUrl);
        webView.setWebViewClient(new MyOwnBrowser());
        webView.getSettings().setJavaScriptEnabled(true);

    }

    private class MyOwnBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
