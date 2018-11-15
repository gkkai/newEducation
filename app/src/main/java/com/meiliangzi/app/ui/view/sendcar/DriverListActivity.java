package com.meiliangzi.app.ui.view.sendcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.QueryuserBean;
import com.meiliangzi.app.model.bean.QueryvehicleListBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

public class DriverListActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;
    private BaseQuickAdapter<QueryvehicleListBean.QueryvehicleListBeanData> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_driver_list);
    }

    @Override
    protected void findWidgets() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        adapter=new BaseQuickAdapter<QueryvehicleListBean.QueryvehicleListBeanData>(this,R.layout.car_list_item) {
            @Override
            public void convert(BaseViewHolder helper, QueryvehicleListBean.QueryvehicleListBeanData item) {
                ((TextView)helper.getView(R.id.tv_plateNumberr)).setText(item.getName()+"-"+item.getPlateNumber());

            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                mIntent.putExtra("positino",getIntent().getIntExtra("positino",1));
                mIntent.putExtra("id",adapter.getItem(position-1).getId());
                mIntent.putExtra("plateNumber",adapter.getItem(position-1).getPlateNumber());
                // 设置结果，并进行传送
                setResult(102, mIntent);
                finish();
            }
        });
    }

    @Override
    protected void initComponent() {
        //TODO 查询公司下的所有子公司
        ProxyUtils.getHttpProxy().queryvehiclelist(this);

    }
    public void getQueryvehicleListBean(QueryvehicleListBean bean){

        adapter.setDatas(bean.getData());
    }
}
