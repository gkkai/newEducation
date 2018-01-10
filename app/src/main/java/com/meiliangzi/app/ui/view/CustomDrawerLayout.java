package com.meiliangzi.app.ui.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.ViewConfiguration;

/**
 * Created by kk on 2017/9/22.
 */

public class CustomDrawerLayout  extends DrawerLayout {
    public CustomDrawerLayout(Context context) {
        this(context, null);
    }
    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final ViewConfiguration configuration = ViewConfiguration
                .get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }
    private int mTouchSlop;
    private float mLastMotionX;
    private float mLastMotionY;


}
