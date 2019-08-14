package com.meiliangzi.app.ui.view.Academy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.meiliangzi.app.ui.view.Academy.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CIndexTFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private MsgContentFragmentAdapter adapter;
    Gson gson = new Gson();
    private List<IndexColumnBean.Data> names;
    @BindView(R.id.im_menu)
    ImageView im_menu;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.tv_topersonalcentenr)
    TextView tv_topersonalcentenr;
    @BindView(R.id.rl_integral)
    RelativeLayout rl_integral;
    String id;

    @BindView(R.id.tv_code)
    TextView tv_code;
    public CIndexTFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.fragment_cindex, container, false));
    }
    @Override
    public void onResume() {
        super.onResume();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.de_draft_color));
       // tv_code.setText(NewPreferManager.getUserTotalScore());
        userInfo();
    }
    private void userInfo(){
        Map<String,String> map=new HashMap();
        map.put("userId",NewPreferManager.getId());
        map.put("orgId",NewPreferManager.getOrgId());
        OkhttpUtils.getInstance(getContext()).getList("academyService//userInfo/findByUserInfo", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                UserInfoBean user=gson.fromJson(json,UserInfoBean.class);
                NewPreferManager.saveUserSex(user.getData().getUserSex());
                NewPreferManager.saveOrganizationName(user.getData().getOrganizationName());
                NewPreferManager.saveOrganizationId(user.getData().getOrganizationId());
                NewPreferManager.savePhoto(user.getData().getPhoto());
                NewPreferManager.saveUserName(user.getData().getUserName());
                NewPreferManager.saveBirthDate(user.getData().getBirthDate());
                NewPreferManager.saveNativePlace(user.getData().getNativePlace());
                NewPreferManager.savePartyMasses(user.getData().getPartyMasses());
                NewPreferManager.savePartyName(user.getData().getPartyName());
                NewPreferManager.savePartyPositionName(user.getData().getPartyPositionName());
                NewPreferManager.savePhone(user.getData().getPhone());
                NewPreferManager.saveUserCode(user.getData().getUserCode());
                NewPreferManager.saveWorkNumber(user.getData().getWorkNumber());
                NewPreferManager.savePositionName(user.getData().getPositionName());
                NewPreferManager.savePositionId(user.getData().getPositionId());
                NewPreferManager.saveId(user.getData().getId());
                NewPreferManager.saveUserTotalScore(user.getData().getUserTotalScore());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_code.setText(NewPreferManager.getUserTotalScore());
                    }
                });

            }
        });


    }

    @Override
    protected void findWidgets() {


        im_menu.setOnClickListener(this);
        search.setOnClickListener(this);
        tv_topersonalcentenr.setOnClickListener(this);
        rl_integral.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_menu:
                IntentUtils.startAtyForResult(this, AlloptionsActivity.class,1);
                break;
            case R.id.search:
               Intent intent =new Intent(getActivity(),TopSearchActivity.class);
                intent.putExtra("type","文章");
                startActivity(intent);
                break;
            case R.id.tv_topersonalcentenr:
                ((MainActivity)getActivity()).getmTabHost().setCurrentTab(3);
                break;
            case R.id.rl_integral:
                IntentUtils.startAtyForResult(this, TotalscoreActivity.class,1);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1006:
//                names= gson.fromJson(PreferUtils.getString("TAB",""),List.class);
//                adapter.setList(names);
                break;
            case 1005:
               int i= data.getIntExtra("position",0);
//                names= gson.fromJson(PreferUtils.getString("TAB",""),List.class);
                viewPager.setCurrentItem(data.getIntExtra("position",0));
                tabLayout.getTabAt(data.getIntExtra("position",0)).select();

                break;
        }



    }

    @Override
    protected void initComponent() {
        //TODO 获取栏目

      Map<String,String> map=new HashMap<>();
        map.put("columnType","1");
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
                            // 更新适配器数据
                            adapter.setList(bean.getData());
                            viewPager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(viewPager);
                            tabLayout.getTabAt(0).select();

                        }catch (Exception e){
                            ToastUtils.show(e.getMessage());

                        }
                    }
                });

            }
        });

    }


    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
        ToastUtils.show(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
        ToastUtils.show(errorCode+errorMessage);
    }

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
        public TabIndexFragment getItem(int position) {
            TabIndexFragment fragment = new TabIndexFragment();
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
