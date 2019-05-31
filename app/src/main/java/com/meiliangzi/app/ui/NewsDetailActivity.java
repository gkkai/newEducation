package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.IndexNewsTypeBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.fragment.BlankFragment;
import com.meiliangzi.app.ui.fragment.NewsFragment;
import com.meiliangzi.app.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;



/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/15
 * @description 资讯详情
 **/


public class NewsDetailActivity extends BaseActivity {

    private LayoutInflater Inflater;
    LinearLayout ll;
    PagerSlidingTabStrip tabLayout;
    ViewPager viewPager;
    private MyAdapter adapter;
    //public NewsDetailActivity.OnCallBack onclick;
    List<TextView> listviews;
    List<IndexNewsTypeBean.DataBean> dataBeens;
    private static NewsFragment[] fragments;
    private OnCallBack onclick;
    private PagerSlidingTabStrip.OnClickTabListener onClickTabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.newsactivity = this;
        listviews = new ArrayList<>();

        onCreateView(R.layout.activity_news_detail);
        //addView2();
        Inflater = LayoutInflater.from(this);

    }

    @Override
    protected void findWidgets() {
        ll = (LinearLayout) findViewById(R.id.layout_News);
        tabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    @Override
    protected void initComponent() {
       /* newFragment1 = new NewsFragment();
        newFragment2 = new NewsFragment();
        newFragment3 = new NewsFragment();
        newFragment5 = new NewsFragment();
        newFragment4 = new NewsFragment();*/

        ProxyUtils.getHttpProxy().newstypelist(this);

    }

    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();

    }

    protected void newstypelist(IndexNewsTypeBean indexNewsTypeBean) {
        dataBeens = indexNewsTypeBean.getData();
        MyApplication.type=dataBeens.get(0).getId();

        addView();


    /*tabLayout.setOnClickTabListener(new PagerSlidingTabStrip.OnClickTabListener() {
        @Override
        public void onClickTab(View tab, int index) {

            ToastUtils.show("index="+index);
            MyAdapter2 myAdapter2 =new MyAdapter2(getSupportFragmentManager());

            viewPager.setAdapter(myAdapter2);
        }
    });*/
        adapter = new MyAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(0);
        tabLayout.setViewPager(viewPager);
       tabLayout.setOnClickTabListener(new PagerSlidingTabStrip.OnClickTabListener() {
            @Override
            public void onClickTab(View tab, int index) {
                tabLayout.onPageChangeListener.onPageSelected(index);
                //tabLayout.selectedTab(index);

                MyApplication.type=dataBeens.get(index).getId();
                viewPager.setCurrentItem(index);
            }
        });
        //tabLayout.s
    }

    private View addView1() {
        ll.removeAllViews();
        for (int i = 0; i < listviews.size(); i++) {

            ll.addView(listviews.get(i));//将TextView 添加到子View 中
        }
        return ll;

    }
    private void addView() {
        listviews.clear();
        ll.removeAllViews();
        for (int i = 0; i < dataBeens.size(); i++) {
            View view = Inflater.inflate(R.layout.horizontalscrollview_tap_news,// 找到布局文件
                    ll, false);
            TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);

            if(i==0){
                txt.setTextColor(getResources().getColor(R.color.colorRed));
            }
            txt.setText(dataBeens.get(i).getTitle());
            ViewGroup parent = (ViewGroup) txt.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            listviews.add(txt);
            ll.addView(txt);
        }
    }
    private View addView2() {
        // TODO 动态添加布局(java方式)
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        vlp.gravity = Gravity.CENTER_VERTICAL;
        for (int i = 0; i < dataBeens.size(); i++) {
            final TextView tv1 = new TextView(this);
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(14f);
            tv1.setLayoutParams(vlp);
            tv1.setText(dataBeens.get(i).getTitle());
            if (i == 0) {
                tv1.setTextColor(getResources().getColor(R.color.colorRed));
            }
            listviews.add(tv1);
            ll.addView(tv1);//将TextView 添加到子View 中
        }


        return ll;
    }

    public class MyAdapter extends FragmentPagerAdapter {
        int type;

        public MyAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public NewsFragment getItem(int position) {
            if(fragments==null){
                fragments=new NewsFragment[dataBeens.size()];
                for (int i=0;i<dataBeens.size();i++){
                    fragments[i]=new NewsFragment(dataBeens.get(i).getId());
                }
            }


            return   new NewsFragment(dataBeens.get(position).getId());


        }

        @Override
        public int getCount() {
            return listviews.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            return super.instantiateItem(container, position);
        }
    }



    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
        ToastUtils.show(errorMessage);
    }


    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
        ToastUtils.show(errorMessage);
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

                    onclick.onCallBack(dataBeens.get(position).getId());
                }
                for (int i=0;i<dataBeens.size();i++){
                    if(i==position){
                        listviews.get(i).setTextColor(getResources().getColor(R.color.colorRed));
                    }else {
                        listviews.get(i).setTextColor(getResources().getColor(R.color.colorTextGray));
                    }
                }
                addView1();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
               /* if (onclick != null) {
                    onclick.onCallBack(dataBeens.get(state).getId());
                }*/
            }
        });
    }
    public void setOnclick(OnCallBack onclick) {
        this.onclick = onclick;
    }

    public void setOnclick(PagerSlidingTabStrip.OnClickTabListener onclick) {
        this.onClickTabListener = onclick;
    }
    public interface OnCallBack {
        public void onCallBack(int type);
    }
}
