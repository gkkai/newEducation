package com.meiliangzi.app.tools.picompressor;

import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;

/**
 * Created by kk on 2017/10/2.
 */

public class MyWebChromeClient extends WebChromeClient {
    //配置权限（同样在WebChromeClient中实现）

    public MyWebChromeClient() {
        super();

    }

    public GeolocationPermissions.Callback getCallback() {
        return getCallback();
    }

    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }
}
