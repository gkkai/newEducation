package com.meiliangzi.app.ui.view.Academy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.library.tabstrip.PagerSlidingTabStrip;
import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.SpecialExaminationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SpecialExaminationActivity extends BaseActivity  {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    PagerSlidingTabStrip tabLayout;
    private MyPageAdapter adapter;
    private String[] title = {"试题", "考试记录"};
    private List<SpecialExaminationFragment> list = new ArrayList<SpecialExaminationFragment>();
    private SpecialExaminationFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_special_examination);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        setTabs();
    }

    private void setTabs() {
        for (int i = 0; i < title.length; i++) {
          fragment= new SpecialExaminationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("content", title[i]);
            fragment.setArguments(bundle);
            list.add(fragment);
        }
        adapter = new MyPageAdapter(getSupportFragmentManager(), title, list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        setTabsValue();
    }

        private void setTabsValue() {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            // 设置Tab底部选中的指示器Indicator的高度
            tabLayout.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, dm));
            // 设置Tab底部选中的指示器 Indicator的颜色
            tabLayout.setIndicatorColor(Color.RED);
            //设置Tab标题文字的颜色
            tabLayout.setTextColor(getResources().getColor(R.color.group_list_gray));
            // 设置Tab标题文字的大小
            tabLayout.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 17, dm));
            //设置Tab底部分割线的高度
//        tabs.setUnderlineHeight(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, dm));
            //设置Tab底部分割线的颜色
            tabLayout.setUnderlineColor(Color.TRANSPARENT);
            // 设置点击某个Tab时的背景色,设置为0时取消背景色
            tabLayout.setTabBackground(0);
            // 设置Tab是自动填充满屏幕的
            tabLayout.setShouldExpand(true);
            // 设置选中的Tab文字的颜色
            tabLayout.setSelectedTextColor(getResources().getColor(R.color.ac_filter_nature));
            //tab间的分割线
            tabLayout.setDividerColor(Color.TRANSPARENT);
            //底部横线与字体宽度一致
            tabLayout.setIndicatorinFollower(true);
        //与ViewPager关联，这样指示器就可以和ViewPager联动
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

                switch (position) {
                    case 0:
                        list.get(position).settype("考试");
                        break;
                    case 1:
                        list.get(position).settype("考试记录");
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }




    class MyPageAdapter extends FragmentPagerAdapter {
        String[] titles;
        List<SpecialExaminationFragment> lists = new ArrayList<>();

        public MyPageAdapter(FragmentManager fm, String[] titles, List<SpecialExaminationFragment> list) {
            super(fm);
            this.titles = titles;
            this.lists = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }




}
