package com.meiliangzi.app.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.model.bean.CommonList;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/15
 * @description 评论
 **/

public class CommentListActivity extends BaseActivity implements XListView.IXListViewListener {

    private String id;
    private int page = 1;

    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.etContent)
    EditText etContent;

    private BaseQuickAdapter<CommonList.DataBean> adapter;
    private int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_comment_list);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        id = getIntent().getStringExtra("id");
        adapter = new BaseQuickAdapter<CommonList.DataBean>(CommentListActivity.this, listView, R.layout.item_common) {
            @Override
            public void convert(final BaseViewHolder helper, final CommonList.DataBean item) {
                CircleImageView ivImg = helper.getView(R.id.ivImg);
                helper.setImageByUrl(ivImg, item.getUserinfo().getAvatar(), R.mipmap.ic_default_star, R.mipmap.ic_default_star);
                helper.setText(R.id.tvName, item.getUserinfo().getNickname());
                helper.setText(R.id.tvTime, item.getCreate_time());
                helper.setText(R.id.tvContent, item.getContent());
                TextView tvParise = helper.getView(R.id.tvParise);
                if (item.getPraise() == 0) {
                    tvParise.setText("");
                } else {
                    tvParise.setVisibility(View.VISIBLE);
                    tvParise.setText("+" + item.getPraise());
                }
                if (item.getIs_praise() == 1) {
                    tvParise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_support_normal_), null, null, null);
                    tvParise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pos = helper.getPosition();
                            supportCommon(String.valueOf(item.getId()), String.valueOf(item.getUserinfo().getId()));
                        }
                    });
                    tvParise.setTextColor(Color.parseColor("#606060"));
                } else {
                    tvParise.setTextColor(getResources().getColor(R.color.colorRed));
                    tvParise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_support_selected_), null, null, null);
                    tvParise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pos = helper.getPosition();
                            unSupportCommon(String.valueOf(item.getId()));
                        }
                    });
                }

            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void asyncRetrive() {
        getData();
    }

    public void getData() {
        ProxyUtils.getHttpProxy().getallcomment(CommentListActivity.this, id, String.valueOf(Constant.PAGESIZE), String.valueOf(page), PreferManager.getUserId());
    }

    protected void getData(CommonList commonList) {
        if (page == 1) {
            adapter.pullRefresh(commonList.getData());
        } else {
            adapter.pullLoad(commonList.getData());
        }
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(page == 1){
            adapter.pullRefresh(new ArrayList<CommonList.DataBean>());
            ToastUtils.custom("暂无评论");
        }else {
            adapter.pullLoad(new ArrayList<CommonList.DataBean>());
        }
    }

    public void send(String content) {
        if (PreferManager.getUserId().isEmpty()){
            IntentUtils.startAty(this,LoginActivity.class);
            return;
        }
        ProxyUtils.getHttpProxy().publishcomment(CommentListActivity.this, PreferManager.getUserId(), id, "-1", content);
    }

    protected void getCommon(BaseBean baseBean) {
        asyncRetrive();
        etContent.setText("");
    }

    @OnClick(R.id.tvSend)
    public void onClick() {
        try {
            RuleCheckUtils.checkEmpty(etContent.getText().toString(), "请输入内容");
            send(etContent.getText().toString());
        } catch (Exception e) {
            ToastUtils.custom(e.getMessage());
            e.printStackTrace();
        }
    }

    public void supportCommon(String praiseId, String praise_userid) {
        if (PreferManager.getUserId().isEmpty()){
            IntentUtils.startAty(this,LoginActivity.class);
            return;
        }
        ProxyUtils.getHttpProxy().ispraiselog(CommentListActivity.this, praiseId, PreferManager.getUserId(), praise_userid);
    }

    protected void getPariseResult(BaseBean baseBean) {
        adapter.getmDatas().get(pos).setIs_praise(0);
        adapter.getmDatas().get(pos).setPraise(adapter.getmDatas().get(pos).getPraise() + 1);
        adapter.notifyDataSetChanged();
    }

    public void unSupportCommon(String praiseId) {
        if (PreferManager.getUserId().isEmpty()){
            IntentUtils.startAty(this,LoginActivity.class);
            return;
        }
        ProxyUtils.getHttpProxy().undopraiselog(CommentListActivity.this, praiseId, PreferManager.getUserId());
    }

    protected void unParise(BaseBean baseBean) {
        adapter.getmDatas().get(pos).setIs_praise(1);
        adapter.getmDatas().get(pos).setPraise(adapter.getmDatas().get(pos).getPraise() - 1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        page = 1;
        asyncRetrive();
    }

    @Override
    public void onLoadMore() {
        page++;
        asyncRetrive();
    }
}
