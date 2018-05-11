package com.meiliangzi.app.ui.view.checkSupervise;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.GroupActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.fragment.CheckFragment;
import com.meiliangzi.app.ui.fragment.WholeFragment;
import com.meiliangzi.app.ui.fragment.WholeFragment1;
import com.meiliangzi.app.ui.fragment.WholeFragment2;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.PagerSlidingTabStrip;

import java.util.List;

import butterknife.BindView;

public class ProjectDetalisRecordActivity extends BaseActivity  {

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
    private int id;
    private CheckFragment checkFragment;
    private String[] title = {"创建记录", "成果书记录", "变更记录"};
    private MyAdapter adapter;
    private OnCallBack onclick;
    private String usercheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_project_detalis_record);
    }

    @Override
    protected void findWidgets() {
        id=getIntent().getIntExtra("id",id);
        usercheck=getIntent().getStringExtra("usercheck");
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
                        tvWhole.setTextColor(getResources().getColor(R.color.ac_filter_string_color));
                        tvBase.setTextColor(getResources().getColor(R.color.colorText));
                        tvDe.setTextColor(getResources().getColor(R.color.colorText));
                        break;
                    case 1:
                        tvBase.setTextColor(getResources().getColor(R.color.ac_filter_string_color));
                        tvWhole.setTextColor(getResources().getColor(R.color.colorText));
                        tvDe.setTextColor(getResources().getColor(R.color.colorText));
                        break;
                    case 2:
                        tvDe.setTextColor(getResources().getColor(R.color.ac_filter_string_color));
                        tvBase.setTextColor(getResources().getColor(R.color.colorText));
                        tvWhole.setTextColor(getResources().getColor(R.color.colorText));
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
            if(position == 0)
            {
                return new CheckFragment(0,id,usercheck);
            }else if(position == 1)
            {
                return new CheckFragment(1,id,usercheck);
            }else
            {
                return new CheckFragment(2,id,usercheck);
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
    public void setOnclick(OnCallBack onclick) {
        this.onclick = onclick;
    }
    public ViewPager getViewPager() {
        return viewPager;
    }
}
