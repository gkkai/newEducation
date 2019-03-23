package com.meiliangzi.app.ui.view;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.tools.picompressor.NativePlugin;
import com.meiliangzi.app.ui.base.BaseActivity;



import butterknife.BindView;

public class AddMapActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.wView_map)
    WebView webview;
    @BindView(R.id.sure)
    TextView sure;
    private LocationManager locMan;
    private NativePlugin nativePlugin;
    private LocationListener mLocationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_add_map);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sure:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:+androidGetAddress()");
                    }
                });
                break;
        }

    }

    @Override
    protected void findWidgets() {
        locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                nativePlugin.setfLoction(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        //webview.loadUrl("http://z.meiliangzi.cn:8087/api/maps");
        webview.loadUrl(Constant.BASE_URL+"maps");
        //webview.loadUrl("http://dev-2.meiliangzi.cn:8087/api/maps");
        WebSettings webSettings = webview.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 设置与Js交互的权限
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        nativePlugin=   new NativePlugin(locMan,webview, this, this,mLocationListener);
        //webview.loadUrl("file:///android_asset/map.html");
        webview.addJavascriptInterface(nativePlugin, "NativePlugin");
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationDatabasePath(this.getFilesDir().getPath());
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        //webview.loadUrl("file:///android_asset/map.html");
        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
                Log.i("TAG", "onGeolocationPermissionsHidePrompt");
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin,
                                                           final GeolocationPermissions.Callback callback) {

                super.onGeolocationPermissionsShowPrompt(origin, callback);
                Log.i("TAG", "onGeolocationPermissionsShowPrompt");
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        //启用地理定位
        webSettings.setGeolocationEnabled(true);

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initListener() {
        sure.setOnClickListener(this);
        super.initListener();
    }
}
