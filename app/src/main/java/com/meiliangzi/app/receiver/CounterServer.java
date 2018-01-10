package com.meiliangzi.app.receiver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/9/14.
 */

public class CounterServer extends Service {
    private int counter;
    private Handler handler;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JPushInterface.init(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(3*60000);
                        //doSubmit(String.valueOf(3));
                        doSubmit(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);

    }


    private void doSubmit(int minite){
        if("".equals(PreferManager.getUserId())){

        }else {
            ProxyUtils.getHttpProxyNoDialog().useronlinetimebyid(this,minite, Integer.valueOf(PreferManager.getUserId()));
        }

    }

    protected  void  useronlinetimebyid(BaseBean bean){
        Log.i("grage","成功");
        PreferManager.saveTimeStart("0");
    }
}
