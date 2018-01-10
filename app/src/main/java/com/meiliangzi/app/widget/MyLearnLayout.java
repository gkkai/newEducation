package com.meiliangzi.app.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.view.MapNewActivity;

/**
 * Created by kk on 2017/10/12.
 */

public class MyLearnLayout extends LinearLayout {
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    private MapNewActivity activity ;
    public MyLearnLayout(Context context) {
        super(context);
    }

    public MyLearnLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLearnLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 是否传递到子view
     * true  不传递
     * false 传递
     * 一次触摸屏幕中，如果拦截过则不会再次进入onInterceptTouchEvent
     * 触摸屏幕中，如果在ACTION_DOWN时返回true，则不会再有ACTION_MOVE和ACTION_UP拦截
     * 如果ACTION_DOWN返回false,ACTION_MOVE中返回true，则不会有ACTION_UP拦截
     */
  /*  @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mPosX = event.getX();
                mPosY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurPosX = event.getX();
                mCurPosY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
              *//*  if (mCurPosY - mPosY > 10
                        && (Math.abs(mCurPosY - mPosY) > 25)) {
                    //向下滑動
                   // performAnim2();
                    return   true;
                } else if (mCurPosY - mPosY < 10
                        && (Math.abs(mCurPosY - mPosY) > 25)) {
                    //向上滑动
                    //performAnim2();
                    return   true;
                }*//*
                int   r= (int) Math.abs(mCurPosY - mPosY);
                if(Math.abs(mCurPosY - mPosY) > 25||Math.abs(mCurPosY - mPosY) < 25){
                    ToastUtils.show("滑动了");
                    return   false;
                }

                break;
        }
        return true;
    }*/
    /*@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mPosX = event.getX();
                mPosY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurPosX = event.getX();
                mCurPosY = event.getY();

                break;
            case MotionEvent.ACTION_UP:
              *//*  if (mCurPosY - mPosY > 10
                        && (Math.abs(mCurPosY - mPosY) > 25)) {
                    //向下滑動
                   // performAnim2();
                    return   true;
                } else if (mCurPosY - mPosY < 10
                        && (Math.abs(mCurPosY - mPosY) > 25)) {
                    //向上滑动
                    //performAnim2();
                    return   true;
                }*//*
                int   r= (int) Math.abs(mCurPosY - mPosY);
                if(Math.abs(mCurPosY - mPosY) > 25||Math.abs(mCurPosY - mPosY) < 25){
                    ToastUtils.show("滑动了");
                    return   false;
                }

                break;
        }
        return true;
    }*/
    private boolean mScrolling;
    private float touchDownY;
    private float touchDownY2;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownY = event.getY();
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                touchDownY2=event.getY();
                int   r= (int) Math.abs(touchDownY - event.getY());
                if(Math.abs(touchDownY - event.getY()) > 15){
                   // ToastUtils.show("滑动了");
                   // activity.performAnim2();
                    mScrolling = true;
                }
              /*  if (Math.abs(touchDownY - event.getY()) >= ViewConfiguration.get(
                        getContext()).getScaledTouchSlop()) {
                    mScrolling = true;
                } else {
                    mScrolling = false;
                }*/
                break;
            case MotionEvent.ACTION_UP:
                mScrolling = false;
                break;
        }
        return mScrolling;
    }

    float y1 = 0;
    float y2 = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
               // activity.performAnim2();
                return true;
            case MotionEvent.ACTION_MOVE:
                if((touchDownY - event.getY()>0)&&Math.abs(touchDownY - event.getY()) > 15){
                    // ToastUtils.show("滑动了");

                    if(activity.listshow.equals("down")){
                        activity.listshow="up";
                        activity.performAnim2();
                    }


                }else if ((touchDownY - event.getY()<0)&&Math.abs(touchDownY - event.getY()) > 15) {
                    //向下滑动
                    if(activity.listshow.equals("up")){
                        activity.listshow="down";
                        activity.performAnim2();
                    }
                    return   true;
                }

                break;
            case MotionEvent.ACTION_UP:
               // y2 = event.getY();
               /* if((touchDownY - event.getY()>0)&&Math.abs(touchDownY - event.getY()) > 15){
                    // ToastUtils.show("滑动了");

                    if(activity.listshow.equals("down")){
                        activity.listshow="up";
                        activity.performAnim2();
                    }


                }else if ((touchDownY - event.getY()<0)&&Math.abs(touchDownY - event.getY()) > 15) {
                    //向下滑动
                    if(activity.listshow.equals("up")){
                        activity.listshow="down";
                        activity.performAnim2();
                    }
                    return   true;
                }*/
               break;

        }

        return super.onTouchEvent(event);
    }

    public void setActivity(MapNewActivity activity) {
        this.activity = activity;
    }
}
