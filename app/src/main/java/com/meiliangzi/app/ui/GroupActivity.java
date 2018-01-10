package com.meiliangzi.app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.StudyInfo;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.fragment.GroupFragment;
import com.meiliangzi.app.ui.fragment.WholeFragment;
import com.meiliangzi.app.ui.fragment.WholeFragment1;
import com.meiliangzi.app.ui.fragment.WholeFragment2;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/20
 * @description 社群
 **/

public class GroupActivity extends BaseActivity {


    @BindView(R.id.tvLearn)
    TextView tvLearn;
    @BindView(R.id.tvLession)
    TextView tvLession;
    @BindView(R.id.tvTime)
    TextView tvTime;
    private List<BaseFragment> fragments;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    PagerSlidingTabStrip tabLayout;
    @BindView(R.id.tvWhole)
    TextView tvWhole;
    @BindView(R.id.tvBase)
    TextView tvBase;
    @BindView(R.id.tvDe)
    TextView tvDe;
    @BindView(R.id.ivImg)
    CircleImageView ivImg;
    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    public OnCallBack onclick;
    private MyAdapter adapter;


    private String[] title = {"全部课程", "基础课程", "党员教育"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_group);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferManager.getUserId())){
            ivImg.setImageResource(R.mipmap.ic_default_star);
            tvUserName.setText("登录/注册");
            tvLearn.setText("0天");
            tvLession.setText("0");
            tvTime.setText("0时");
            return;
        }
        getTime();
        if(PreferManager.getUserStar().startsWith("http")){
            ImageLoader.getInstance().displayImage(PreferManager.getUserStar(),ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star,R.mipmap.ic_default_star));
        }else {
            ImageLoader.getInstance().displayImage("file:///"+ PreferManager.getUserStar(),ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star,R.mipmap.ic_default_star));
        }
        tvUserName.setText(PreferManager.getUserName());
    }

    @Override
    protected void findWidgets() {
        submitTime();
    }
    public void submitTime(){
        if(!TextUtils.isEmpty(PreferManager.getUserId())){
            ProxyUtils.getHttpProxyNoDialog().queryuserloginlog(this, PreferManager.getUserId());
        }
    }

    protected void getStatus(JSONObject jsonObject){

    }
    @Override
    protected void initComponent() {
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    protected void initListener() {
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (onclick != null) {
                    onclick.onCallBack(String.valueOf(position));
                }
                switch (position) {
                    case 0:
                        tvWhole.setTextColor(Color.parseColor("#EA3241"));
                        tvBase.setTextColor(getResources().getColor(R.color.colorTextDeep));
                        tvDe.setTextColor(getResources().getColor(R.color.colorTextDeep));
                        break;
                    case 1:
                        tvBase.setTextColor(Color.parseColor("#EA3241"));
                        tvWhole.setTextColor(getResources().getColor(R.color.colorTextDeep));
                        tvDe.setTextColor(getResources().getColor(R.color.colorTextDeep));
                        break;
                    case 2:
                        tvDe.setTextColor(Color.parseColor("#EA3241"));
                        tvBase.setTextColor(getResources().getColor(R.color.colorTextDeep));
                        tvWhole.setTextColor(getResources().getColor(R.color.colorTextDeep));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @OnClick({R.id.llLevel,R.id.ivImg,R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llLevel:

                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyForResult(this, LoginActivity.class,1002);
                } else {
                    IntentUtils.startAty(this, RankingListActivity.class);
                }


                break;
            case R.id.ivImg:
                if(TextUtils.isEmpty(PreferManager.getUserId())){
                    IntentUtils.startAtyForResult(this, LoginActivity.class,1002);
                }else {
                    IntentUtils.startAty(this, PersonCenterActivity.class);
                }

                break;
            case R.id.ivBack:

               finish();


                break;
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int position) {
            if(position == 0)
            {
                return new WholeFragment1("0");
            }else if(position == 1)
            {
                return new WholeFragment("1");
            }else
            {
                return new WholeFragment2("2");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
    public interface OnCallBack {
        public void onCallBack(String type);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTime();
    }
    public void getTime() {
        String string= PreferManager.getUserId();
        ProxyUtils.getHttpProxyNoDialog().studyinfo(this, PreferManager.getUserId());
    }
    protected void getTime(StudyInfo studyInfo) {
        tvLearn.setText(String.valueOf(studyInfo.getData().getLogDays())+"天");
        tvTime.setText(String.valueOf(studyInfo.getData().getStudyTime()));
        tvLession.setText(String.valueOf(studyInfo.getData().getYesCourses()));
    }
    public void setOnclick(OnCallBack onclick) {
        this.onclick = onclick;
    }
    public ViewPager getViewPager() {
        return viewPager;
    }
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if("今天已记录".equals(errorMessage)){

        }else {
            ToastUtils.custom(errorMessage);
        }

    }
}
