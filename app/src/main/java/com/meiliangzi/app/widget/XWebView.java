package com.meiliangzi.app.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

public class XWebView extends WebView {
    private static final int LOADING_FINISH = 100;
    private Activity activity;

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public XWebView(Context context) {
        super(context);
    }

    public XWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebView();
        this.activity = (Activity) context;
    }

    public XWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        WebSettings webSetting = getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setNeedInitialFocus(false);
        webSetting.setSavePassword(false);
        webSetting.setSupportZoom(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
//        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT | WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

//        setWebChromeClient(new XWebChromClient());
//        setWebViewClient(new XWebViewClient());
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {

        //清理Webview缓存数据库
        try {
            activity.deleteDatabase("webview.db");
            activity.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(activity.getFilesDir().getAbsolutePath() + "com.union.yzx");
//        Log.e("WebView", "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(activity.getCacheDir().getAbsolutePath() + "/webviewCache");
        Log.e("WebView", "webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Log.i("WebView", "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e("webview", "delete file no exists " + file.getAbsolutePath());
        }
    }


    public void showWebPage(String url) {
        loadUrl(url);
    }

    private class WebOperation {

        /**
         * 案例详情js  跳转商品详情
         *
         * @param id
         */
        @JavascriptInterface
        public void doProductGoods(String id) {
//            if (NetUtil.getNetworkState(activity)==0)
//            {
//                ToastUtils.custom("无网路，请检查网络");
//                return;
//            }

        }

    }

    private class XWebChromClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int progress) {
//			activity.setNewProgress(progress);
//			if (progress == LOADING_FINISH)
//			{
//				activity.hideProgressBar();
//			}
        }
    }

    private class XWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String tag="tel:";
            if (url.contains(tag))
            {
                String mobile=url.substring(url.lastIndexOf("/")+1);
                Uri uri=Uri.parse("tel:"+mobile);
                Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                activity.startActivity(intent);
                //这个超连接,java已经处理了，webview不要处理了
                return true;
            }
            if(callBack!=null){
                callBack.callBack(url);
            }
            Log.i("TAG","url++++"+url);
            view.loadUrl(url);
            return true;
        }
    }

    public interface CallBack{
        public void callBack(String url);
    }
}
