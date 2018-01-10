package com.meiliangzi.app.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.fragment.ScoreFragment;
import com.meiliangzi.app.ui.fragment.ScoreFragment1;
import com.meiliangzi.app.ui.fragment.ScoreFragment2;
import com.meiliangzi.app.widget.PagerSlidingTabStrip;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/18
 * @description 我的成绩
 **/

public class MyScoreActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tvWhole)
    TextView tvWhole;
    @BindView(R.id.tvBase)
    TextView tvBase;
    @BindView(R.id.tvDe)
    TextView tvDe;

    private FragmentPagerAdapter pagerAdapter;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    PagerSlidingTabStrip tabLayout;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }



    private String[] title = {"全部", "已学习", "待学习"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_my_score);
    }

    @Override
    protected void findWidgets() {
    }

    @Override
    protected void initComponent() {

        pagerAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    protected void asyncRetrive() {

    }

    @Override
    protected void initListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

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

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int position) {
            if(position == 0){
                return new ScoreFragment();
            }else if(position ==1){
                return new ScoreFragment1();
            }else {
                return new ScoreFragment2();
            }
        }

        @Override
        public int getCount() {
            return  3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }


}
