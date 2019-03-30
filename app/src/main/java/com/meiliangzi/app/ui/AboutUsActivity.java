package com.meiliangzi.app.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.text)
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_about_us);
    }

    @Override
    protected void findWidgets() {
        text.setText("当前版本号：V"+getVersionCode(this));

    }

    @Override
    protected void initComponent() {
        webView.loadUrl("file:///android_asset/about.html");
    }
    //获取版本号
    public static  String getVersionCode(Context context) {
        return getPackageInfo(context).versionName;
    }
    //通过PackageInfo得到的想要启动的应用的包名
 private static PackageInfo getPackageInfo(Context context) {
             PackageInfo pInfo = null;

             try {
                 //通过PackageManager可以得到PackageInfo
                     PackageManager pManager = context.getPackageManager();
                     pInfo = pManager.getPackageInfo(context.getPackageName(),
                                     PackageManager.GET_CONFIGURATIONS);

                    return pInfo;
                 } catch (Exception e) {
                    e.printStackTrace();
                 }

             return pInfo;
         }

}
