package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.fragment.ExaminationFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.TabExaminationFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KaoShiActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private MsgContentFragmentAdapter adapter;
    private List<String> names;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

        onCreateView(R.layout.activity_kao_shi);
    }

    @Override
    protected void findWidgets() {
        initData();
        adapter = new MsgContentFragmentAdapter(getSupportFragmentManager());
        // 更新适配器数据
        adapter.setList(names);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 50, 50);
            }
        });
        tabLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

    }
    private void initData() {
        names = new ArrayList<>();
        names.add("专题考试");
        names.add("定向考试");

    }


    @Override
    protected void initComponent() {

    }
    public class MsgContentFragmentAdapter extends FragmentPagerAdapter {
        private List<String> names;

        public MsgContentFragmentAdapter(FragmentManager fm) {
            super(fm);
            this.names = new ArrayList<>();
        }

        /**
         * 数据列表
         *
         * @param datas
         */
        public void setList(List<String> datas) {
            this.names.clear();
            this.names.addAll(datas);
            notifyDataSetChanged();
        }
        @Override
        public TabExaminationFragment getItem(int position) {
            TabExaminationFragment fragment = new TabExaminationFragment();
            Bundle bundle = new Bundle();
            bundle.putString("names", names.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }
        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String plateName = names.get(position);
            if (plateName == null) {
                plateName = "";
            } else if (plateName.length() > 15) {
                plateName = plateName.substring(0, 15) + "...";
            }
            return plateName;
        }
    }
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }
}
