package com.meiliangzi.app.ui.view.creativecommons;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.CommonsListBean;
import com.meiliangzi.app.model.bean.Validate;
import com.meiliangzi.app.tools.CountDownHandler;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.RegisterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

public class CommonsListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {

    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.add)
    ImageView add;

    @BindView(R.id.text_login)
    TextView text_login;
    @BindView(R.id.edit_)
    EditText edit_;
    @BindView(R.id.listView)
    XListView listView;
    BaseVoteAdapter<CommonsListBean.DataBean> Adapter;
    private String search;
    private int currentPage=1;
    private int pageSize=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_commons_list);
    }

    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        Adapter = new BaseVoteAdapter<CommonsListBean.DataBean>(CommonsListActivity.this,listView, R.layout.commonslistitemlayout) {
            @Override
            public void convert(BaseViewHolder helper, CommonsListBean.DataBean item) {
                helper.setText(R.id.text_proName,item.getTitle());
                helper.setText(R.id.text_time,item.getCreateAt());
                helper.setText(R.id.text_neirong,item.getContent());


            }
        };
        listView.setAdapter(Adapter);
        add.setOnClickListener(this);
        edit_.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //写点击搜索键后的操作
                    currentPage=1;
                    search=edit_.getText().toString().trim();
                    ProxyUtils.getHttpProxy().indexrepository(CommonsListActivity.this,search,currentPage,pageSize);
                    return true;
                }
                return false;

            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    // TODO  知识共享
                    Intent commmons=new Intent(CommonsListActivity.this, CommonsDataActivity.class);
                    commmons.putExtra("repositoryId",Adapter.getItem(position-1).getId());
                    startActivity(commmons);
                }

            }
        });
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyForResult(CommonsListActivity.this, LoginActivity.class, 1003, "activity", "index");

                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyForResult(CommonsListActivity.this, PersonCenterActivity.class, 1003, "activity", "index");
                }
            }
        });
    }

    @Override
    protected void initComponent() {
        search="";
        ProxyUtils.getHttpProxy().indexrepository(this,search,currentPage,pageSize);

    }

    protected void getindexrepository(CommonsListBean bean){
       // Adapter.setDatas(bean.getData());

        if(tvEmpty.getVisibility()==View.VISIBLE){
            tvEmpty.setVisibility(View.GONE);
        }
        if (currentPage == 1) {
            Adapter.pullRefresh(bean.getData());
        } else {
            Adapter.pullLoad(bean.getData());
            //listViewAdapter.pullRefresh(articalList.getData());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                // TODO  知识共享
                Intent commmons=new Intent(this, NewBuildComminsActivity.class);
                startActivity(commmons);
                break;

        }
        }


    @Override
    public void onRefresh() {
        currentPage=1;
        getData();
    }

    private void getData() {
        ProxyUtils.getHttpProxy().indexrepository(this,search,currentPage,pageSize);

    }

    @Override
    public void onLoadMore() {
        currentPage++;
        getData();
    }
    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
        tvEmpty.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isLogin();
        }
    }

    private void isLogin() {
        if (TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()) {
            if (TextUtils.isEmpty(PreferManager.getUserId())) {
                text_login.setText("请先登录");
            } else {
                text_login.setText("请完善个人信息");
            }
            text_login.setVisibility(View.VISIBLE);
        }else {
            text_login.setVisibility(View.GONE);
        }

    }
}
