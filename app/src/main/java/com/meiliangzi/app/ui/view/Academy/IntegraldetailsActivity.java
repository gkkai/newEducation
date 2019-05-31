package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.PersonalScorebean;
import com.meiliangzi.app.widget.XListView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class IntegraldetailsActivity extends BaseActivity implements XListView.IXListViewListener {
    @BindView(R.id.listView)
    XListView listView;

    BaseVoteAdapter<PersonalScorebean.Data> detailAdapter;
    private String type;
    private int page;
    private String pageSize="10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_integraldetails);

    }

    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        detailAdapter=new BaseVoteAdapter<PersonalScorebean.Data>(this,listView,R.layout.personalscorelist) {
            @Override
            public void convert(BaseViewHolder helper, final PersonalScorebean.Data  item) {
                ((TextView)helper.getView(R.id.tv_integralName)).setText(item.getIntegralName());
                ((TextView)helper.getView(R.id.tv_createTime)).setText(item.getCreateTime());
                ((TextView)helper.getView(R.id.tv_number)).setText("+"+item.getIntegralScore());



            }
                };
        listView.setAdapter(detailAdapter);
            }


    @Override
    protected void initComponent() {
        getlsit();
    }

    private void getlsit(){
        Map<String,String> maps=new HashMap<>();
        maps.put("userId", NewPreferManager.getId());
        maps.put("pageNum",page+"");
        maps.put("pageSize",pageSize+"");
        OkhttpUtils.getInstance(this).getList("academyService/detail/list", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(final String json) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        PersonalScorebean bean=   gson.fromJson(json,PersonalScorebean.class);

                        if (page == 0) {
                            detailAdapter.pullRefresh(bean.getData());
                        } else {
                            detailAdapter.pullLoad(bean.getData());
                        }
                    }
                });


            }
        });
    }
    private void getList(PersonalScorebean bean){
        if (page == 0) {
            detailAdapter.pullRefresh(bean.getData());
        } else {
            detailAdapter.pullLoad(bean.getData());
        }

    }


    @Override
    public void onRefresh() {
        page = 0;
        getlsit();
    }

    @Override
    public void onLoadMore() {
        page++;
        getlsit();

    }
};






