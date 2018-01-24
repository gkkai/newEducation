package com.meiliangzi.app.tools;

import android.content.Context;
import android.util.AttributeSet;

import xiaobo.com.video.CustomView.MyVideoPlayerStandard;

/**
 * Created by kk on 2018/1/24.
 */

public class MyPlayUtil extends MyVideoPlayerStandard {
    public MyPlayUtil(Context context) {
        super(context);
    }

    public MyPlayUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //主要方法在这里
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


}
