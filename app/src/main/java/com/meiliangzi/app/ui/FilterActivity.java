package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.Partment;
import com.meiliangzi.app.model.bean.PartyBranchBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.fragment.RankListFragment;
import com.meiliangzi.app.ui.view.CustomDrawerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class FilterActivity extends BaseActivity {


    @BindView(R.id.drawer_layout)
    CustomDrawerLayout drawer_layout;
    @BindView(R.id.myGridView)
    GridView myGridView;//部门列表
    @BindView(R.id.myGridViewType)
    GridView myGridViewType;
    @BindView(R.id.partGridView)
    GridView partGridView;//二级党支部列表
    @BindView(R.id.partbranchGridView)
    GridView partbranchGridView;//一级党支部列表
    @BindView(R.id.tview_branch)
    TextView tview_branch;
    @BindView(R.id.tview_part)
    TextView tview_part;
    String result="";
    private BaseQuickAdapter<Partment.DataBean> adapter;
    private BaseQuickAdapter<PartyBranchBean.DataBean> partadapter;
    private BaseQuickAdapter<PartyBranchBean.DataBean.Partybranchs_info> Partybranchs_infodapter;
    private BaseQuickAdapter<String> typeAdapter;


    private int filter_type = 0;
    private int filter_department = 0;
    private int order_avgscore = 1;
    private int timescreening = 0;
    private String nameType = "党员教育";
    private String nameDepartment = "不限";
    private String nameDepartment2 = "不限";
    public int departmentPos = -1;
    public int typePos = -1;

    private List<String> type;
    private RankListFragment baseFragment;
    //部门列表
    List<Partment.DataBean> databranch=new ArrayList<>();
    //党支部列表
    List<PartyBranchBean.DataBean> datapartybranch=new ArrayList<>();
    //党支部列表
    List<PartyBranchBean.DataBean.Partybranchs_info> Partybranchs_info=new ArrayList<PartyBranchBean.DataBean.Partybranchs_info>();
    public int getFilter_type() {
        return filter_type;
    }

    public void setFilter_type(int filter_type) {
        this.filter_type = filter_type;
    }

    public int getFilter_department() {
        return filter_department;
    }

    public void setFilter_department(int filter_department) {
        this.filter_department = filter_department;
    }

    public int getOrder_avgscore() {
        return order_avgscore;
    }

    public void setOrder_avgscore(int order_avgscore) {
        this.order_avgscore = order_avgscore;
    }

    public int getTimescreening() {
        return timescreening;
    }

    public void setTimescreening(int timescreening) {
        this.timescreening = timescreening;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_filter);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        type = new ArrayList<>();
        //type.add("不限");
        type.add("党员教育");
        type.add("基础知识");

        //replaceFragment();
        adapter = new BaseQuickAdapter<Partment.DataBean>(FilterActivity.this, R.layout.item_filter) {
            @Override
            public void convert(BaseViewHolder helper, Partment.DataBean item) {
                helper.setText(R.id.ckContent, item.getName());
                CheckBox checkBox = helper.getView(R.id.ckContent);
                if (nameDepartment.equals(item.getName())) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        };

        typeAdapter = new BaseQuickAdapter<String>(FilterActivity.this, R.layout.item_filter) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.ckContent, item);
                CheckBox checkBox = helper.getView(R.id.ckContent);
                if (nameType.equals(item)) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        };
        partadapter = new BaseQuickAdapter<PartyBranchBean.DataBean>(FilterActivity.this, R.layout.item_filter) {
            @Override
            public void convert(BaseViewHolder helper, PartyBranchBean.DataBean item) {
                helper.setText(R.id.ckContent, item.getName());
                CheckBox checkBox = helper.getView(R.id.ckContent);
                if ( nameDepartment.equals(item.getName())) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        };
        Partybranchs_infodapter = new BaseQuickAdapter<PartyBranchBean.DataBean.Partybranchs_info>(FilterActivity.this, R.layout.item_filter) {
            @Override
            public void convert(BaseViewHolder helper, PartyBranchBean.DataBean.Partybranchs_info item) {
                helper.setText(R.id.ckContent, item.getName());
                CheckBox checkBox = helper.getView(R.id.ckContent);
                if (nameDepartment2.equals(item.getName())) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
        };
        myGridView.setAdapter(adapter);
        partbranchGridView.setAdapter(partadapter);
        myGridViewType.setAdapter(typeAdapter);
        partGridView.setAdapter(Partybranchs_infodapter);
        adapter.setDatas(databranch);
        partadapter.setDatas(datapartybranch);
        Partybranchs_infodapter.setDatas(Partybranchs_info);
        typeAdapter.setDatas(type);
        partbranchGridView.setVisibility(View.VISIBLE);
        partGridView.setVisibility(View.GONE);
        myGridView.setVisibility(View.GONE);
    }

    @Override
    protected void asyncRetrive() {
        getPartment();
        getpartybranch();
    }

    private void getpartybranch() {
        ProxyUtils.getHttpProxy().querypartbranch(FilterActivity.this,Integer.valueOf(PreferManager.getCompanId()));
    }
    private void querypartbranch(PartyBranchBean partyBranchbean){
        PartyBranchBean.DataBean dataBean=new PartyBranchBean.DataBean();
        dataBean.setId(2);
        dataBean.setName("不限");
        datapartybranch.add(dataBean);
        datapartybranch.addAll(partyBranchbean.getData());
        partadapter.setDatas(datapartybranch);

    }

    @OnClick(R.id.ivFilter)
    public void onViewClicked() {
        drawer_layout.openDrawer(Gravity.RIGHT);
    }

   /* private void replaceFragment() {
        baseFragment = RankListFragment.newInstance();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ly_content, baseFragment);
        fragmentTransaction.commit();
    }*/

    public void getPartment() {
        ProxyUtils.getHttpProxy().querydepartment(FilterActivity.this,Integer.valueOf(PreferManager.getUserId()));
    }

    public void getData(Partment partment) {

        Partment.DataBean dataBean=new Partment.DataBean();
        dataBean.setId(2);
        dataBean.setName("不限");
        databranch.add(dataBean);
        databranch.addAll(partment.getData());
        adapter.setDatas(databranch);

    }



    @Override
    protected void initListener() {
        myGridViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typePos = position;
                nameType =typeAdapter.getItem(position).toString();
                /*CheckBox checkBox = (CheckBox) adapter.getView(position,view,parent);
                if(!checkBox.isChecked()){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }*/
                if("基础知识".equals(nameType)){
                    tview_branch.setText("部门");
                    //myGridViewType.setAdapter(adapter);
                    adapter.setDatas(databranch);


                    /*if(partbranchGridView.getVisibility()==View.GONE){
                        partbranchGridView.setVisibility(View.VISIBLE);
                    }else {
                        partbranchGridView.setVisibility(View.GONE);
                    }
                    if(myGridViewType.getVisibility()==View.GONE){
                        myGridViewType.setVisibility(View.VISIBLE);
                    }else {
                        myGridViewType.setVisibility(View.GONE);
                    }*/
                    partGridView.setVisibility(View.GONE);
                    partbranchGridView.setVisibility(View.GONE);
                    myGridView.setVisibility(View.VISIBLE);
                    tview_part.setVisibility(View.GONE);
                }else if("党员教育".equals(nameType)){
                    tview_branch.setText("党支部");
                    //myGridViewType.setAdapter(partadapter);
                    partadapter.setDatas(datapartybranch);
                    partbranchGridView.setVisibility(View.VISIBLE);
                    /*if(partbranchGridView.getVisibility()==View.GONE){
                        partbranchGridView.setVisibility(View.VISIBLE);
                    }else {
                        partbranchGridView.setVisibility(View.GONE);
                    }*/
                    /*if(myGridViewType.getVisibility()==View.GONE){
                        myGridViewType.setVisibility(View.VISIBLE);
                    }else {
                        myGridViewType.setVisibility(View.GONE);
                    }*/
                    myGridView.setVisibility(View.GONE);
                    tview_part.setVisibility(View.GONE);

                }
                typeAdapter.notifyDataSetChanged();
            }
        });
        //一级党支部选择事件
        partbranchGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
               /* CheckBox checkBox = (CheckBox) adapter.getView(i,view,parent);
                if(!checkBox.isChecked()){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }*/
                //partadapter.notifyDataSetChanged();
                if(partadapter.getItem(i).getPartybranchs_info().size()==0){
                    //TODO 直接返回
                    nameDepartment=result=partadapter.getItem(i).getName();
                }else {
                    nameDepartment=result=partadapter.getItem(i).getName();
                    Partybranchs_info.clear();
                    PartyBranchBean.DataBean.Partybranchs_info dataBean=new PartyBranchBean.DataBean.Partybranchs_info();
                    dataBean.setId(2);
                    dataBean.setName("不限");
                    Partybranchs_info.add(dataBean);

                    Partybranchs_info.addAll(partadapter.getItem(i).getPartybranchs_info());
                    Partybranchs_infodapter.setDatas(Partybranchs_info);
                    //TODO 显示二级党支部
                    // tview_part.setVisibility(View.VISIBLE);
                    if(tview_part.getVisibility()==View.GONE){
                        tview_part.setVisibility(View.VISIBLE);
                    }else {
                        tview_part.setVisibility(View.GONE);
                    }
                    if(partGridView.getVisibility()==View.GONE){
                        partGridView.setVisibility(View.VISIBLE);
                    }else {
                        partGridView.setVisibility(View.GONE);
                    }
                }
                partadapter.notifyDataSetChanged();
            }

        });
        //二级党支部选择事件
        partGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                nameDepartment2= result=Partybranchs_infodapter.getItem(i).getName();
                Partybranchs_infodapter.notifyDataSetChanged();

            }
        });
        // 部门选择事件
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departmentPos = position;
                nameDepartment=adapter.getItem(position).getName();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.tvReset, R.id.tvDone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvReset:
                typePos = -1;
                departmentPos = -1;
                adapter.notifyDataSetChanged();
                typeAdapter.notifyDataSetChanged();
                filter_type = 0;
                filter_department = 0;
                baseFragment.getData(filter_type,filter_department,order_avgscore,timescreening);
                drawer_layout.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.tvDone:
                if(typePos == -1){
                    filter_type = 0;
                }else {
                    switch (typePos){
                        case 0:
                            filter_type = 0;
                            break;
                        case 1:
                            filter_type = 0;
                            break;
                        case 2:
                            filter_type = 0;
                            break;
                    }
                }
                if(departmentPos == -1||departmentPos == 0){
                    filter_department = 0;
                }else {
                    filter_department = adapter.getmDatas().get(departmentPos).getId();
                }
                baseFragment.getData(filter_type,filter_department,order_avgscore,timescreening);
                drawer_layout.closeDrawer(Gravity.RIGHT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            baseFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
