package com.meiliangzi.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;


/**
 * Created by Administrator on 2017/8/31.
 */

public class MyVideoView extends VideoView {
    private OnClickCallBack onClick;

    public void setOnClick(OnClickCallBack onClick) {
        this.onClick = onClick;
    }

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getWidth(), widthMeasureSpec);
        int height = getDefaultSize(getHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        onClick.pause();;
    }

    @Override
    public void resume() {
        super.resume();
        onClick.resume();
    }

    @Override
    public void start() {
        super.start();
        onClick.onstart();
    }

    public interface OnClickCallBack{
        void pause();
        void resume();
        void onstart();
    }
}
