package com.meiliangzi.app.ui.listener;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class ApplicationListener implements Application.ActivityLifecycleCallbacks {
    private int foregroundCount = 0; // 位于前台的 Activity 的数目
    @Override
    public void onActivityStarted(final Activity activity) {
        if (foregroundCount <= 0) {
            // TODO 这里处理从后台恢复到前台的逻辑
        }
        foregroundCount++;
    }
    @Override
    public void onActivityStopped(Activity activity) {
        foregroundCount--;
        if (foregroundCount <= 0) {
            // TODO 这里处理从前台进入到后台的逻辑
        }
    }
    /*
     * 下面回调，我们都不需要
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
    @Override
    public void onActivityResumed(Activity activity) {}
    @Override
    public void onActivityPaused(Activity activity) {}
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
    @Override
    public void onActivityDestroyed(Activity activity) {}
}