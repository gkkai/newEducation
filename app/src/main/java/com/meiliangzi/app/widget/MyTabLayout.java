package com.meiliangzi.app.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by kk on 2019/4/29.
 */

public class MyTabLayout  extends TabLayout{
    Context context;


    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dp10 = (int)ScreenUtils.dpToPx(getContext(), 10);
        LinearLayout mTabStrip = (LinearLayout) this.getChildAt(0);
        try {
            Field mTabs = TabLayout.class.getDeclaredField("mTabs");
            mTabs.setAccessible(true);
            ArrayList<Tab> tabs = (ArrayList<Tab>) mTabs.get(this);
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                Tab tab = tabs.get(i);
                Field mView = tab.getClass().getDeclaredField("mView");
                mView.setAccessible(true);
                Object tabView = mView.get(tab);
                Field mTextView = context.getClassLoader().loadClass("android.support.design.widget.TabLayout$TabView").getDeclaredField("mTextView");
                mTextView.setAccessible(true);
                TextView textView = (TextView) mTextView.get(tabView);
                float textWidth = textView.getPaint().measureText(textView.getText().toString());
                View child = mTabStrip.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) textWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                params.leftMargin = dp10;
                params.rightMargin = dp10;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
