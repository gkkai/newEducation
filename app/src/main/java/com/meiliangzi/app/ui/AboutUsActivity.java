package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_about_us);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        webView.loadUrl("file:///android_asset/about.html");
    }
}
