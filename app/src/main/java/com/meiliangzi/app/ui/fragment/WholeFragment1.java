package com.meiliangzi.app.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.ArticalList;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.ArticalDetailActivity;
import com.meiliangzi.app.ui.GroupActivity;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.VideoDetailActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 课程列表
 **/

public class WholeFragment1 extends BaseFragment implements GroupActivity.OnCallBack ,XListView.IXListViewListener{


    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    private BaseQuickAdapter<ArticalList.DataBean> adapter;

    private int page = 1;
    private GroupActivity activity;
    private String type;

    public WholeFragment1(String i) {
        super();
        type=i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return createView(inflater.inflate(R.layout.fragment_lession, null, false));
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        activity = (GroupActivity) getActivity();
        activity.setOnclick(this);
        adapter = new BaseQuickAdapter<ArticalList.DataBean>(getActivity(),listView, R.layout.item_lession) {
            @Override
            public void convert(BaseViewHolder helper, ArticalList.DataBean item) {
                helper.setImageByUrl(R.id.ivImg, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);
                helper.setText(R.id.tvTitle,item.getName());
                helper.setText(R.id.tvTime,item.getCreate_time());
                CheckBox isSupport = helper.getView(R.id.isSupport);
                isSupport.setText(String.valueOf(item.getPraise()));
                isSupport.setEnabled(false);
                if (item.getIs_praise() .equals("0") ) {
                    isSupport.setChecked(true);
                } else {
                    isSupport.setChecked(false);
                }
                if (item.isType()) {
                    helper.getView(R.id.icPlay).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.icPlay).setVisibility(View.VISIBLE);
                }
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getItem(position-1).isType()){
                    IntentUtils.startAtyWithSingleParam(getActivity(), ArticalDetailActivity.class,"id",adapter.getItem(position-1).getId());
                }else {
                    IntentUtils.startAtyWithSingleParam(getActivity(), VideoDetailActivity.class,"id",adapter.getItem(position-1).getId());
                }
            }
        });

        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyWithSingleParam(getActivity(), LoginActivity.class, "activity", "WholeFragment");
                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyWithSingleParam(getActivity(), PersonCenterActivity.class, "activity", "WholeFragment");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()){
            if(TextUtils.isEmpty(PreferManager.getUserId())){
                tvEmpty.setText("请先登录");
            }else if(!PreferManager.getIsComplete()){
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            getData(type,1,10);
        }
    }

    public void getData(String type, int currentPage, int pageSize)
    {
        if(TextUtils.isEmpty(PreferManager.getUserId()) && !PreferManager.getIsComplete()){
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }
        ProxyUtils.getHttpProxyNoDialog().querylist(this, type, String.valueOf(currentPage), String.valueOf(pageSize), PreferManager.getUserId());
    }

    protected void getData(ArticalList articalList) {
        if(page == 1){
            adapter.pullRefresh(articalList.getData());
        }else {
            adapter.pullLoad(articalList.getData());
        }
    }


    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(page == 1){
            adapter.setDatas(new ArrayList<ArticalList.DataBean>());
        }
        else {

        }
    }

    @Override
    public void onCallBack(String type) {
        getData(type, page, Constant.PAGESIZE);
    }

    @Override
    public void onRefresh() {
        page=1;
        getData(type,page,10);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData(type,page,10);
    }
}
