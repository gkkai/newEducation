package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.ArticalList;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/18
 * @description 我的收藏
 **/

public class MyCollectActivity extends BaseActivity implements XListView.IXListViewListener {

    @BindView(R.id.listView)
    XListView listView;

    private BaseQuickAdapter<ArticalList.DataBean> adapter;

    private int page = 1;
    private ArticalList.DataBean item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_my_collect);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        adapter = new BaseQuickAdapter<ArticalList.DataBean>(MyCollectActivity.this, listView, R.layout.item_my_collect) {
            @Override
            public void convert(BaseViewHolder helper, final ArticalList.DataBean item) {
                helper.setImageByUrl(R.id.ivImg, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);
                helper.setText(R.id.tvTitle, item.getName());
                helper.setText(R.id.tvTime, item.getCreate_time());
                helper.setOnClickListener(R.id.tvCancelCollect, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyCollectActivity.this.item = item;
                        collect(item.getId(), "1");
                    }
                });
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void asyncRetrive() {
        ProxyUtils.getHttpProxy().collectionlist(MyCollectActivity.this, PreferManager.getUserId(), String.valueOf(Constant.PAGESIZE), String.valueOf(page));
    }

    public void getData() {
        ProxyUtils.getHttpProxy().collectionlist(MyCollectActivity.this, PreferManager.getUserId(), String.valueOf(Constant.PAGESIZE), String.valueOf(page));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    public void getData(ArticalList articalList) {
        if (page == 1) {
            adapter.pullRefresh(articalList.getData());
        } else {
            adapter.pullLoad(articalList.getData());
        }
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if (page == 1) {
            ToastUtils.custom("暂无收藏");
            adapter.pullRefresh(new ArrayList());
        }else {
            adapter.pullLoad(new ArrayList<ArticalList.DataBean>());
        }
    }


    public void collect(String subjectId, String value) {
        ProxyUtils.getHttpProxy().usercollect(MyCollectActivity.this, PreferManager.getUserId(), subjectId, value);
    }

    protected void getResult(BaseBean baseBean) {
        adapter.getmDatas().remove(item);
        adapter.notifyDataSetChanged();
        ToastUtils.custom("取消收藏成功");
    }


    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(adapter.getItem(position-1).isType()){
                    IntentUtils.startAtyWithSingleParam(MyCollectActivity.this, ArticalDetailActivity.class,"id",adapter.getItem(position-1).getId());
                }else {
                    IntentUtils.startAtyWithSingleParam(MyCollectActivity.this, VideoDetailActivity.class,"id",adapter.getItem(position-1).getId());
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    @Override
    public void onLoadMore() {
        page++;
        getData();
    }
}
