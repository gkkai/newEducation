package com.meiliangzi.app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.StudyInfo;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.RankingListActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 我的
 **/

public class GroupFragment extends BaseFragment {

    Unbinder unbinder;
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
    private String[] title = {"全部课程", "基础课程", "党员教育"};


    public OnCallBack onclick;
    private MyAdapter adapter;
//    private WholeFragment wholeFragment;


    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setOnclick(OnCallBack onclick) {
        this.onclick = onclick;
    }


    public GroupFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = createView(inflater.inflate(R.layout.fragment_group, null, false));
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void findWidgets() {
        submitTime();
    }

    @Override
    protected void initComponent() {
        adapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
//        wholeFragment = new WholeFragment();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    @OnClick({R.id.llLevel,R.id.ivImg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llLevel:

                    if (TextUtils.isEmpty(PreferManager.getUserId())) {
                        IntentUtils.startAtyForResult(getActivity(), LoginActivity.class,1002);
                    } else {
                        IntentUtils.startAty(getActivity(), RankingListActivity.class);
                    }


                break;
            case R.id.ivImg:
                if(TextUtils.isEmpty(PreferManager.getUserId())){
                    IntentUtils.startAtyForResult(getActivity(), LoginActivity.class,1002);
                }else {
                    IntentUtils.startAty(getActivity(), PersonCenterActivity.class);
                }

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface OnCallBack {
        public void onCallBack(String type);
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

    @Override
    protected void asyncRetrive() {

    }
    public void submitTime(){
        if(!TextUtils.isEmpty(PreferManager.getUserId())){
            ProxyUtils.getHttpProxyNoDialog().queryuserloginlog(this, PreferManager.getUserId());
        }
    }

    protected void getStatus(JSONObject jsonObject){

    }
    public void getTime() {
        String string= PreferManager.getUserId();
        ProxyUtils.getHttpProxyNoDialog().studyinfo(this, PreferManager.getUserId());
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

    protected void getTime(StudyInfo studyInfo) {
        tvLearn.setText(String.valueOf(studyInfo.getData().getLogDays())+"天");
        tvTime.setText(String.valueOf(studyInfo.getData().getStudyTime()));
        tvLession.setText(String.valueOf(studyInfo.getData().getYesCourses()));
    }


    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if("今天已记录".equals(errorMessage)){

        }else {
            ToastUtils.custom(errorMessage);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getTime();
    }

}
