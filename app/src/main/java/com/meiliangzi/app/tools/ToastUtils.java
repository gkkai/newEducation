package com.meiliangzi.app.tools;

import android.content.Context;
import android.widget.Toast;

import com.meiliangzi.app.widget.CustomToast;


public class ToastUtils {
    private static Context context;
    private static CustomToast customToast;

    private ToastUtils() {
    }

    public static void init(Context context) {
        ToastUtils.context = context;
        customToast = new CustomToast(context);
    }

    public static void custom(CharSequence text) {
        customToast.setShowMsg(text);
        customToast.show();
    }

    @Deprecated
    public static void show(int resId) {
        Toast.makeText(context, context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public static void show(CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
