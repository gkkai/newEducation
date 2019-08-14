package com.meiliangzi.app.ui.view.vote;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.VoteBaseBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

public class VoteActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;

    @BindView(R.id.tvEmptyDate)
    TextView tvEmptyDate;
    BaseVoteAdapter<VoteBaseBean.DataBean> voteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_vote);
        onCreateView(R.layout.activity_vote);
    }

    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        voteAdapter = new BaseVoteAdapter<VoteBaseBean.DataBean>(this, listView, R.layout.item_vote_list) {
            @Override
            public void convert(BaseViewHolder helper, final VoteBaseBean.DataBean item) {
                helper.setImageByUrl(R.id.image_background, item.getImage(), R.mipmap.votebackgroud, R.mipmap.votebackgroud);
                helper.setText(R.id.text_vote_title, item.getTitle());
                TextPaint paint = ((TextView)helper.getView(R.id.text_vote_title)).getPaint();
                paint.setFakeBoldText(true);
               // helper.setText(R.id.text_Underline, item.getTitle());
                helper.setText(R.id.text_vote_number, item.getVateNumber()+"人参与  ");
                helper.getView(R.id.text_vote_number).getBackground().setAlpha(100);
               /* if(0==item.getIsEnd()){

                }else {
                    helper.setText(R.id.text_isend, "已结束");
                }*/

            }

        };
        listView.setAdapter(voteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(VoteActivity.this,VoteListActivity.class);
                intent.putExtra("isInfo",voteAdapter.getItem(position-1).getIsInfo());
                intent.putExtra("id",voteAdapter.getItem(position-1).getId());
                startActivity(intent);

            }
        });
    }
    @Override
    protected void initListener() {
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(NewPreferManager.getoldUseId()+"")) {
                    IntentUtils.startAtyWithSingleParam(VoteActivity.this, LoginActivity.class, "activity", "WholeFragment");
                }
            }
        });
    }
    @Override
    protected void initComponent() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(NewPreferManager.getoldUseId()+"")){
//            if(TextUtils.isEmpty(PreferManager.getUserId())){
//                tvEmpty.setText("请先登录");
//            }else if(!PreferManager.getIsComplete()){
//                tvEmpty.setText("请完善个人信息");
//            }
            tvEmpty.setText("请先登录");
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            ProxyUtils.getHttpProxy().votelist(this);
        }
    }


    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();


    }
    private void getvotelist(VoteBaseBean data){
        if(data.getData()!=null){
            tvEmptyDate.setVisibility(View.GONE);
            if(data.getData().size()!=0){
                tvEmptyDate.setVisibility(View.GONE);
                voteAdapter.setDatas(data.getData());
            }else {
                tvEmptyDate.setVisibility(View.VISIBLE);
            }

        }else{
            tvEmptyDate.setVisibility(View.VISIBLE);

        }

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
        tvEmptyDate.setVisibility(View.VISIBLE);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
       // super.showErrorMessage(errorCode, errorMessage);
        if("无数据".equals(errorMessage)){
            tvEmptyDate.setVisibility(View.VISIBLE);
        }

    }
}
