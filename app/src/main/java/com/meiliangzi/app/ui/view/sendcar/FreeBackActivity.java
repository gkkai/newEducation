package com.meiliangzi.app.ui.view.sendcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.IndexSendacarBean;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

public class FreeBackActivity extends BaseActivity implements XListView.IXListViewListener {
    public int plateStatus;
    public int currentPage=1;
    public int pageSize=10;
    @BindView(R.id.listView)
    XListView listView;
    private BaseQuickAdapter<IndexSendacarBean.IndexSendacarData> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_free_back);
    }

    @Override
    protected void findWidgets() {

        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        adapter = new BaseQuickAdapter<IndexSendacarBean.IndexSendacarData>(this,listView, R.layout.item_dfk_sendcar) {

            @Override
            public void convert(BaseViewHolder helper, final IndexSendacarBean.IndexSendacarData item) {
                helper.setText(R.id.text_sendcar_departmentName, item.getDepartmentName());
                helper.setText(R.id.text_sendcar_plateNumber, item.getPlateNumber());
                helper.setText(R.id.text_sendcar_start, item.getStart());
                helper.setText(R.id.text_sendcar_end, item.getEnd());
                String start = item.getStartAt().substring(item.getStartAt().lastIndexOf(" "));
                String end = item.getEndAt().substring(item.getEndAt().lastIndexOf(" "));
                helper.setText(R.id.text_sendcar_startAt_endAt, start + "~" + end);

                helper.getView(R.id.text_sumit_fk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(item.getDriverUserId()==NewPreferManager.getoldUseId()){
                            Intent i=new Intent(FreeBackActivity.this,FkDataActivity.class);
                            i.putExtra("sendACarId",item.getId());
                            startActivity(i);
                        }else {
                            ToastUtils.show("您不是驾驶员，您不能进行反馈");
                        }


                    }
                });


            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(FreeBackActivity.this,SendaCarinfoActivity.class);
                intent.putExtra("sendACarId",adapter.getItem(position-1).getId());
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initComponent() {
        //ProxyUtils.getHttpProxy().indexsendacar(this,3,currentPage,pageSize);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProxyUtils.getHttpProxy().indexsendacar(this,3,currentPage,pageSize);
    }

    protected void getindexsendacar(IndexSendacarBean bean){
        //adapter.setDatas(bean.getData());
        if (currentPage == 1) {
            adapter.pullRefresh(bean.getData());
        } else {
            adapter.pullLoad(bean.getData());
            //listViewAdapter.pullRefresh(articalList.getData());
        }

    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        getData(3, currentPage, Constant.PAGESIZE);
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        getData(3, currentPage, Constant.PAGESIZE);
    }
    public void getData(int type,int currentPage,int pageSize ){
        ProxyUtils.getHttpProxy().indexsendacar(this,type,currentPage,pageSize);
    }
}
