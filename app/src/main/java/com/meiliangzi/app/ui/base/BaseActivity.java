package com.meiliangzi.app.ui.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.tools.ToastUtils;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void findWidgets();

    protected abstract void initComponent();

    protected Context mContext;

    protected void onCreateView(int layoutResID) {
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



    /**
     * 后退按钮响应事件
     */
    public void onBackClick() {
    }

}
