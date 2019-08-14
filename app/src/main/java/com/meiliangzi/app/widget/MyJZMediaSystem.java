package com.meiliangzi.app.widget;

import android.app.Activity;
import android.media.MediaPlayer;

import com.meiliangzi.app.ui.view.Academy.VideoPlayActivity;

import cn.jzvd.JZMediaSystem;

/**
 * Created by kk on 2019/6/27.
 */

public class MyJZMediaSystem  extends JZMediaSystem {
    VideoPlayActivity activity;
    public MyJZMediaSystem(VideoPlayActivity activity) {
        this.activity=activity;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        super.onPrepared(mediaPlayer);
        //activity.starplay();
        activity.isplay=true;


    }
}
