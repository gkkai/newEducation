package com.meiliangzi.app.ui.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.githang.statusbar.StatusBarCompat;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.ToastUtils;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void findWidgets();

    protected abstract void initComponent();

    protected Context mContext;

    protected void onCreateView(int layoutResID) {
//        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
      // getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        initSystemBarTint();
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mContext = this;
        findWidgets();
        initComponent();
        initListener();
        initHandler();
        excuteOther();
        asyncRetrive();
        pushAtyToStack();

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white));
    }






    /**
     * 后退按钮响应事件
     */
    public void onBackClick(View v) {
        finish();
    }

    @SuppressWarnings("unchecked")
    protected <T> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 初始化Listener，子类根据需要自行重写
     */
    protected void initListener() {
        return;
    }

    /**
     * 初始化Handler，子类根据需要自行重写
     */
    protected void initHandler() {
        return;
    }

    /**
     * 做一些其他的事情，如开启一个定时器或者线程、getIntentExtra、显示一个WebPage等等..
     */
    protected void excuteOther() {
        return;
    }

    /**
     * 异步查询网络数据，子类根据需要自行重写
     */
    protected void asyncRetrive() {
        return;
    }



    /**
     * 显示失败信息，默认显示吐司，子类有需要显示界面可自行重写
     */
    protected void showErrorMessage(String errorMessage) {
        ToastUtils.custom(errorMessage);
    }

    /**
     * 显示失败信息，默认显示吐司，子类有需要显示界面可自行重写
     */
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        ToastUtils.custom(errorMessage);
    }

    /**
     * 把当前Activity Push栈中
     */
    private void pushAtyToStack() {
        MyApplication.push(this);
    }

    /**
     * 把当前Activity Pop出栈
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.pop(this);
    }

    public void setFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }



    protected void showProgress(String message,
       boolean isCanceledOnTouchOutside, boolean isCancelable) {
    }

    protected void dismissProgressDialog() {
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    /**
     * 后退按钮响应事件
     */
    public void onBackClick() {
    }
    protected void initSystemBarTint() {

        Window window = getWindow();



            // 沉浸式状态栏

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |           View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                window.setStatusBarColor(Color.BLACK);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            }
            return;
        }

}
