package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.WelcomeAnimatorListener;
import com.meiliangzi.app.ui.base.BaseActivity;


public class WelcomeActivity extends BaseActivity {

    private ImageView loadingItem;
    private Animation welcomeAnimation;

    private SQLHelper helper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_welcome);
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        if (!PreferManager.getUserId().isEmpty())
        {
            PreferManager.saveTimeStart(System.currentTimeMillis()+"");
        }
        if(helper ==null){
            helper = new SQLHelper(this);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏
    }

    @Override
    protected void findWidgets() {
        loadingItem = findView(R.id.iv_welcome_loading_item);
    }

    @Override
    protected void initComponent() {
        welcomeAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_loading);
        welcomeAnimation.setAnimationListener(new WelcomeAnimatorListener(this));

    }

    @Override
    protected void excuteOther() {
        loadingItem.startAnimation(welcomeAnimation);
    }
    private void getmentusernumber(DepartmentuserNumberBean data){
        MyApplication.numberBean=data;
        // TODO Auto-generated method stub

    }
}
