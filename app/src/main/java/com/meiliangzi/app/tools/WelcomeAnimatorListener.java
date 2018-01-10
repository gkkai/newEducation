package com.meiliangzi.app.tools;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.WelcomeActivity;


public class WelcomeAnimatorListener implements AnimationListener {
    private WelcomeActivity welcomeActivity;

    public WelcomeAnimatorListener(WelcomeActivity welcomeActivity) {
        this.welcomeActivity = welcomeActivity;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {

        IntentUtils.startAty(welcomeActivity, MainActivity.class);
        welcomeActivity.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
