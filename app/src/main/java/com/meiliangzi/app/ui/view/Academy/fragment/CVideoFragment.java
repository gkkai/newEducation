package com.meiliangzi.app.ui.view.Academy.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.view.Academy.AlloptionsActivity;
import com.meiliangzi.app.ui.view.Academy.TopSearchActivity;
import com.meiliangzi.app.ui.view.Academy.TotalscoreActivity;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CVideoFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.tv_topersonalcentenr)
    TextView tv_topersonalcentenr;
    @BindView(R.id.rl_integral)
    RelativeLayout rl_integral;
    @BindView(R.id.tv_code)
    TextView tv_code;
    private MsgContentFragmentAdapter adapter;
    public CVideoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return createView(inflater.inflate(R.layout.fragment_cvideo, container, false));


    }
    private void initData() {
//

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                Intent intent =new Intent(getActivity(),TopSearchActivity.class);
                intent.putExtra("type","视频");
                startActivity(intent);
                break;
            case R.id.tv_topersonalcentenr:
                ((MainActivity)getActivity()).getmTabHost().setCurrentTab(3);
                break;
            case R.id.rl_integral:
                IntentUtils.startAty(getActivity(), TotalscoreActivity.class);
                break;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.de_draft_color));
        tv_code.setText(NewPreferManager.getUserTotalScore());
    }

    @Override
    protected void findWidgets() {
        search.setOnClickListener(this);
        tv_topersonalcentenr.setOnClickListener(this);
        rl_integral.setOnClickListener(this);


    }


    @Override
    protected void initComponent() {
        //TODO
        //ProxyUtils.gethttpchanyexutyan().getList(this,"2");
        Map<String,String> map=new HashMap<>();
        map.put("columnType","2");
        map.put("orgId",NewPreferManager.getOrgId());
        OkhttpUtils.getInstance(getContext()).getList("academyService/column/getList", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(final String json) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson=new Gson();
                            IndexColumnBean bean=gson.fromJson(json,IndexColumnBean.class);
                            adapter = new MsgContentFragmentAdapter(getChildFragmentManager());
                        if(bean.getData()!=null&&bean.getData().size()!=0){
                            // 更新适配器数据
                            adapter.setList(bean.getData());
                            viewPager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(viewPager);
                            tabLayout.getTabAt(0).select();
                        }else {
                            ToastUtils.show("暂无数据");
                        }


                        }catch (Exception e){
                            ToastUtils.show(e.getMessage());

                        }
                    }
                });

            }
        });

    }

//    public void getList(IndexColumnBean bean){
//        adapter = new MsgContentFragmentAdapter(getChildFragmentManager());
//        MyApplication.indexColumnBean=bean;
//        // 更新适配器数据
//        adapter.setList(bean.getData());
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(0).select();
//
//
//    }

    public class MsgContentFragmentAdapter extends FragmentPagerAdapter {
        private List<IndexColumnBean.Data> names;

        public MsgContentFragmentAdapter(FragmentManager fm) {
            super(fm);
            this.names = new ArrayList<>();
        }

        /**
         * 数据列表
         *
         * @param datas
         */
        public void setList(List<IndexColumnBean.Data> datas) {
            this.names.clear();
            this.names.addAll(datas);
            notifyDataSetChanged();
        }
        @Override
        public TabViedoFragment getItem(int position) {
            TabViedoFragment fragment = new TabViedoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("position", position+"");
            bundle.putSerializable("data",  names.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }
        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String plateName = names.get(position).getColumnName();
            if (plateName == null) {
                plateName = "";
            } else if (plateName.length() > 15) {
                plateName = plateName.substring(0, 15) + "...";
            }
            return plateName;
        }
    }



}


