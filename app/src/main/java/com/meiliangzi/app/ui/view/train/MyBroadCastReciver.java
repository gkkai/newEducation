package com.meiliangzi.app.ui.view.train;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.meiliangzi.app.MyApplication;

/**
 * Created by kk on 17/11/7.
 */

public class MyBroadCastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mBootIntent = new Intent(context, MyApplication.clsaaname);
        mBootIntent.putExtra("Reciver",1000);
        context.startActivity(mBootIntent);
        switch (intent.getAction()) {
            case WpsModel.Reciver.ACTION_BACK://返回键广播
                //System.out.println(WpsModel.Reciver.ACTION_BACK);
                //context.startActivity(mBootIntent);
                break;
            case WpsModel.Reciver.ACTION_CLOSE://关闭文件时候的广播
                System.out.println(WpsModel.Reciver.ACTION_CLOSE);
                //mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //mBootIntent.putExtra("Reciver",1000);
                //context.startActivity(mBootIntent);

                break;
            case WpsModel.Reciver.ACTION_HOME://home键广播
                System.out.println(WpsModel.Reciver.ACTION_HOME);

                break;
            case WpsModel.Reciver.ACTION_SAVE://保存广播
                System.out.println(WpsModel.Reciver.ACTION_SAVE);

                break;
            default:
                break;
        }
    }
}
