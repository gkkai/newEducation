package com.meiliangzi.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.RankList;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author xiaobo
 * @date   2017/8/16
 * @version  1.0
 * @description  排行榜
 *
 **/

public class RankListFragment extends BaseFragment implements XListView.IXListViewListener{


    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private BaseQuickAdapter adapter;
    private int page = 1;

    private int filter_type = 2;
    private int filter_department = 0;
    private int order_avgscore = 1;
    private int timescreening = 0;
    private int filter_dpartybranch=0;
    private int filter_dpartybranchs=0;

    public RankListFragment() {
    }

    public static RankListFragment newInstance() {
        RankListFragment fragment = new RankListFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return createView(inflater.inflate(R.layout.fragment_rand_list,null,false));
    }

    @Override
    protected void findWidgets() {
        listView.setXListViewListener(this);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
    }

    @Override
    protected void asyncRetrive() {
        if(TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()){
            if(TextUtils.isEmpty(PreferManager.getUserId())){
                tvEmpty.setText("请先登录");
            }else if(!PreferManager.getIsComplete()){
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            tvEmpty.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            getData(filter_type,filter_department,order_avgscore,timescreening);
        }


    }

    @Override
    protected void initListener() {
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyForResult(getActivity(), LoginActivity.class, 1003, "activity", "RankListFragment");

                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyForResult(getActivity(), PersonCenterActivity.class, 1003, "activity", "RankListFragment");
                }
            }
        });
    }

    @Override
    protected void initComponent() {
        adapter = new BaseQuickAdapter<RankList.DataBean>(getActivity(),listView,R.layout.item_rank_list) {
            @Override
            public void convert(BaseViewHolder helper, RankList.DataBean item) {
                TextView tvNum = helper.getView(R.id.tvNum);
                helper.setText(R.id.tvUserName,item.getUserName());
                helper.setText(R.id.tvWorkNum,String.valueOf(item.getUserCode()));
                helper.setText(R.id.tvRate,item.getCoursesCounts()+"%");
                helper.setText(R.id.tvAvg,String.valueOf(item.getAvgAchievement()));
                switch (helper.getPosition()){
                    case 0:
                        tvNum.setBackgroundColor(getResources().getColor(R.color.colorLevel1));
                        break;
                    case 1:
                        tvNum.setBackgroundColor(getResources().getColor(R.color.colorLevel2));
                        break;
                    case 2:
                        tvNum.setBackgroundColor(getResources().getColor(R.color.colorLevel3));
                        break;
                    case 3:
                        tvNum.setBackgroundColor(getResources().getColor(R.color.colorLevel4));
                        break;
                    case 4:
                        tvNum.setBackgroundColor(getResources().getColor(R.color.colorLevel5));
                        break;
                    default:
                        tvNum.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        tvNum.setTextColor(getResources().getColor(R.color.colorBlack));
                        break;
                }
                tvNum.setText(String.valueOf(helper.getPosition()+1));
            }
        };
        listView.setAdapter(adapter);
    }



    public void getData(int filter_type, int filter_department, int order_avgscore,int timescreening){
        ProxyUtils.getHttpProxy().getranking(this,filter_type,filter_department,order_avgscore, Constant.PAGESIZE,page,timescreening, Integer.valueOf(PreferManager.getUserId()),filter_dpartybranch,filter_dpartybranchs);
    }
    public void getData(int filter_type, int filter_department, int order_avgscore,int timescreening,int filter_dpartybranch,int filter_dpartybranchs){
        this.filter_type=filter_type;
        this.filter_department=filter_department;
        this.order_avgscore=order_avgscore;
        this.timescreening=timescreening;
        this.filter_dpartybranch=filter_dpartybranch;
        this.filter_dpartybranchs=filter_dpartybranchs;
        page=1;
        /*System.out.println("是否按照党员筛选============="+filter_type);
        System.out.println("是否按照部门筛选============="+filter_department);
        System.out.println("是否按照党员筛选============="+filter_type);*/
        ProxyUtils.getHttpProxy().getranking(this,filter_type,filter_department,order_avgscore, Constant.PAGESIZE,page,timescreening, Integer.valueOf(PreferManager.getUserId()),filter_dpartybranch,filter_dpartybranchs);
    }

    protected void getRank(RankList rankList){
        if (page==1){
            adapter.pullRefresh(rankList.getData());
        }else {
            adapter.pullLoad(rankList.getData());
        }

    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(page == 1){
            adapter.pullRefresh(new ArrayList());
        }else {
            adapter.pullLoad(new ArrayList());
        }
    }

    @Override
    public void onRefresh() {
        page=1;
        asyncRetrive();
    }

    @Override
    public void onLoadMore() {
        page++;
        asyncRetrive();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        asyncRetrive();
    }
}
