package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;



public class WebViewActivity extends BaseActivity {
    WebView webView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url=getIntent().getStringExtra("url");
        onCreateView(R.layout.activity_web_view);
        //setContentView(R.layout.activity_web_view);


    }

    @Override
    protected void findWidgets() {
        webView= (WebView) findViewById(R.id.webView);
    }

    @Override
    protected void initComponent() {


    }
    @Override
    protected void asyncRetrive() {

       webView.loadUrl(url);
    }
}
