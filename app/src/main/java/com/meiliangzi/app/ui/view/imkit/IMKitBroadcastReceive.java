package com.meiliangzi.app.ui.view.imkit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.meiliangzi.app.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class IMKitBroadcastReceive extends PushMessageReceiver {

    private Uri uri;
    List<PushNotificationMessage> list=new ArrayList<>();
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        //list.add(pushNotificationMessage);
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {

            Intent i;
            //TODO 多人发的消息
            i = new Intent(context, MainActivity.class);
            i.putExtra("from",2);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);


        return true;
    }
}
