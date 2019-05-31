package com.meiliangzi.app.ui.view.Academy.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.library.tabstrip.PagerSlidingTabStrip;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferUtils;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.view.Academy.AlloptionsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CIndexFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    PagerSlidingTabStrip tabLayout;
    @BindView(R.id.im_menu)
    ImageView im_menu;
    private String[] title1 = {"试题", "考试记录","试题", "考试记录","试题", "考试记录","试题", "考试记录"};
    private MyPageAdapter adapter;
    private List<TabIndexFragment> list = new ArrayList<TabIndexFragment>();
    private TabIndexFragment fragment;
    private List<String> title=new ArrayList<>();
    Gson gson = new Gson();
    public CIndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.de_draft_color));
        return createView(inflater.inflate(R.layout.fragment_cindex, container, false));
    }

    @Override
    protected void findWidgets() {


        setTabs();
        im_menu.setOnClickListener(this);

    }

//    private void saveTab() {
//
//        String data = gson.toJson(title);
//        PreferUtils.put("TAB",data);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_menu:
                IntentUtils.startAtyForResult(this, AlloptionsActivity.class,1005);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1006:
                adapter.setdata();
                break;
            case 1005:
               int i= data.getIntExtra("position",0);
                adapter.setdata();
                viewPager.setCurrentItem(data.getIntExtra("position",0));

                break;
        }



    }

    @Override
    protected void initComponent() {

    }
    private void setTabs() {

        for (int i = 0; i < title1.length; i++) {
            fragment= new TabIndexFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("content", title1[i]);
//            fragment.setArguments(bundle);
            list.add(i,fragment);
            title.add(title1[i]);
        }
       // new FragmentManager();
        //saveTab();
        adapter = new MyPageAdapter(getActivity().getSupportFragmentManager(), title, list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        setTabsValue();
    }

    private void setTabsValue() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        // 设置Tab底部选中的指示器Indicator的高度
        tabLayout.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.5f, dm));
        // 设置Tab底部选中的指示器 Indicator的颜色
        tabLayout.setIndicatorColor(Color.RED);
        //设置Tab标题文字的颜色
        tabLayout.setTextColor(getResources().getColor(R.color.group_list_gray));
        // 设置Tab标题文字的大小
        tabLayout.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
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

        tabLayout.setTabPaddingLeftRight(40);

        //tab间的分割线
        tabLayout.setDividerColor(Color.TRANSPARENT);
        //底部横线与字体宽度一致
        tabLayout.setIndicatorinFollower(true);
        //与ViewPager关联，这样指示器就可以和ViewPager联动
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

//
//                }
                list.get(position).settype(title.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }



    class MyPageAdapter extends FragmentPagerAdapter {
        List<String> titles;
        List<TabIndexFragment> lists = new ArrayList<>();

        public MyPageAdapter(FragmentManager fm, List<String> titles, List<TabIndexFragment> list) {
            super(fm);
            this.titles = titles;
            this.lists = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return lists.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
        public void  setdata(){
        String result=    PreferUtils.getString("TAB","");
            if("".equals(result)){

            }else {
                titles= gson.fromJson(result,List.class);
                tabLayout.notifyDataSetChanged();
            }

        }
    }
}
