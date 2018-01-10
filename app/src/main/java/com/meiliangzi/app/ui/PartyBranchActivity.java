package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.PartyBranchBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.adapter.ListviewAdapter;
import com.meiliangzi.app.ui.base.BaseActivity;

import java.util.List;


public class PartyBranchActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView listView_partybranch;
    int company_id;
    private List<PartyBranchBean.DataBean> mPdfOutlinesCount;
    ListviewAdapter adapter;
    boolean isonclick=false;
    private String type ="2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        company_id=getIntent().getIntExtra("companyId",0);
        onCreateView(R.layout.activity_bind_phone);
    }

    @Override
    protected void findWidgets() {
        listView_partybranch= (ListView) findViewById(R.id.listView_partybranch);
        listView_partybranch.setOnItemClickListener(this);


    }

    @Override
    protected void initComponent() {
        ProxyUtils.getHttpProxy().querypartbranch(this,company_id);
    }
    private void querypartbranch(PartyBranchBean datas){
        this.mPdfOutlinesCount=datas.getData();
        adapter=new ListviewAdapter(this,mPdfOutlinesCount);
        listView_partybranch.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(!isonclick){
            PreferManager.partyBranchId(String.valueOf(((PartyBranchBean.DataBean)adapter.getItem(position)).getId()));
            PreferManager.partyBranName(String.valueOf(((PartyBranchBean.DataBean)adapter.getItem(position)).getName()));
            if(((PartyBranchBean.DataBean)adapter.getItem(position)).getPartybranchs_info().size()==0){
                //TODO 返回
                Intent intent=new Intent();
                intent.putExtra("id",((PartyBranchBean.DataBean)adapter.getItem(position)).getId());
                intent.putExtra("name",((PartyBranchBean.DataBean)adapter.getItem(position)).getName());

                setResult(RESULT_OK,intent);
                finish();
            }else {
                //TODO　更新数据
                adapter.setdata(((PartyBranchBean.DataBean)adapter.getItem(position)).getPartybranchs_info(),type);
                //PreferManager.partyBranchId(String.valueOf(((PartyBranchBean.DataBean)adapter.getItem(position)).getId()));
                isonclick=true;
                adapter.notifyDataSetChanged();

            }
        }else {
            //TODO  二级菜单直接返回
            Intent intent=new Intent();
            intent.putExtra("id",((PartyBranchBean.DataBean.Partybranchs_info)adapter.getItem(position)).getId());
            intent.putExtra("name",((PartyBranchBean.DataBean.Partybranchs_info)adapter.getItem(position)).getName());
            PreferManager.partyBranchsId(String.valueOf(((PartyBranchBean.DataBean.Partybranchs_info)adapter.getItem(position)).getId()));
            PreferManager.partyBranName(String.valueOf(((PartyBranchBean.DataBean.Partybranchs_info)adapter.getItem(position)).getName()));
            setResult(RESULT_OK,intent);
            finish();
        }


    }
    private long lastClickTime;
    /**
     * @Title: isFastDoubleClick
     * @Description: 判断事件出发时间间隔是否超过预定值
     */
    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
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
}
