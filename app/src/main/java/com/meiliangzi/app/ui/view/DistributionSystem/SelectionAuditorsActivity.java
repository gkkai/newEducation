package com.meiliangzi.app.ui.view.DistributionSystem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.QueryvehicleListBean;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

/**
 * 选择审核人
 */

public class SelectionAuditorsActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;
    private BaseQuickAdapter<QueryvehicleListBean.QueryvehicleListBeanData> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_selection_auditors);
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
    }

    @Override
    protected void initComponent() {

    }
}
