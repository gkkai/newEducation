package com.meiliangzi.app.ui.view.vote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.Company;
import com.meiliangzi.app.model.bean.VoteBaseBean;
import com.meiliangzi.app.model.bean.VoteSubvotelistBean;
import com.meiliangzi.app.model.bean.VoteUsersubvoteBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.ZoomActivity;
import com.meiliangzi.app.widget.MyGridView;

import butterknife.BindView;

public class VoteListActivity extends BaseActivity {
    @BindView(R.id.gradview)
    MyGridView gradview;
    BaseVoteAdapter<VoteSubvotelistBean.DataBean.VotesubList> voteAdapter;
    @BindView(R.id.text_rule_voteNumber)
    TextView text_rule_voteNumber;
    @BindView(R.id.text_rule_voteFrequency)
    TextView text_rule_voteFrequency;
    @BindView(R.id.text_time)
    TextView text_time;
    private int isInfo;
    private int id;
    private int type;
    private int isvote;
    private boolean isonclick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_vote_list);
    }

    @Override
    protected void findWidgets() {

        isInfo=getIntent().getIntExtra("isInfo",4);
        id=getIntent().getIntExtra("id",0000);
        final View headView = getLayoutInflater().inflate(R.layout.vote_list_header, null, false);

        if(0==isInfo){
            voteAdapter = new BaseVoteAdapter<VoteSubvotelistBean.DataBean.VotesubList>(this, gradview, R.layout.item_vote_project_list) {
                @Override
                public void convert(final BaseViewHolder helper, final VoteSubvotelistBean.DataBean.VotesubList item) {
                    helper.setImageByUrl(R.id.image_, item.getImage(), R.mipmap.votelisebackground, R.mipmap.votelisebackground);

                    helper.setText(R.id.text_title, item.getTitle());
                    helper.setText(R.id.text_vote_num, item.getUserVoteLogNumber()+"");
                    helper.getView(R.id.text_ranking).getBackground().setAlpha(100);
                    if(item.getUserVoteLogNumber()!=0){
                        helper.setText(R.id.text_ranking,voteAdapter.getPosition()+1+"");
                       // R.id.text_vote_number
                        helper.showOrGoneView(R.id.text_ranking,true);
                    }else {
                        helper.showOrGoneView(R.id.text_ranking,false);
                    }
                    if(2==item.getIsVote()){
                        if(2==isvote){
                            helper.getView(R.id.text_vote).setEnabled(true);
                            //TODO 可以投票
                            helper.setText(R.id.text_vote, "投票");
                            helper.getView(R.id.text_vote).setBackground(getResources().getDrawable(R.mipmap.votestart));
                            helper.getView(R.id.text_vote).setOnClickListener(new View.OnClickListener(){

                                @Override
                                public void onClick(View v) {

                                    if(!((TextView)helper.getView(R.id.text_vote)).getText().equals("已投")){
                                        ProxyUtils.getHttpProxy().usersubvote(VoteListActivity.this,item.getId(),Integer.valueOf(PreferManager.getUserId()));

                                    }
                                    helper.setText(R.id.text_vote, "已投");
                                    helper.getView(R.id.text_vote).setBackground(getResources().getDrawable(R.mipmap.voteendnodata));
                                    helper.getView(R.id.text_vote).setEnabled(false);

                                }
                            });

                        }else {
                            //TODO 不能可以投票
                            helper.setText(R.id.text_vote, "投票");
                            helper.getView(R.id.text_vote).setBackground(getResources().getDrawable(R.mipmap.voteendnodata));
                            helper.getView(R.id.text_vote).setEnabled(false);

                        }
                    }else {
                        //TODO 不能可以投票
                        helper.setText(R.id.text_vote, "已投");
                        helper.getView(R.id.text_vote).setBackground(getResources().getDrawable(R.mipmap.voteendnodata));

                        helper.getView(R.id.text_vote).setEnabled(false);
                    }



                }

            };
            gradview.setAdapter(voteAdapter);
            /*gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });*/
        }else if(1==isInfo){
            voteAdapter = new BaseVoteAdapter<VoteSubvotelistBean.DataBean.VotesubList>(this, gradview, R.layout.item_vote_project_list_video) {
                @Override
                public void convert(BaseViewHolder helper, final VoteSubvotelistBean.DataBean.VotesubList item) {
                helper.setImageByUrl(R.id.image_, item.getImage(), R.mipmap.votelisebackground, R.mipmap.votelisebackground);
                helper.setText(R.id.text_title, item.getTitle());
                helper.setText(R.id.text_vote_num, item.getUserVoteLogNumber()+"");
                    helper.getView(R.id.text_ranking).getBackground().setAlpha(100);
                    if(item.getUserVoteLogNumber()!=0){
                        helper.setText(R.id.text_ranking,voteAdapter.getPosition()+1+"");
                        helper.showOrGoneView(R.id.text_ranking,true);
                    }else {
                        helper.showOrGoneView(R.id.text_ranking,false);
                    }
                    if(2!=item.getType()){
                        helper.getView(R.id.icPlay).setVisibility(View.GONE);
                    }else {
                        helper.getView(R.id.icPlay).setVisibility(View.VISIBLE);
                    }
                    if(2==item.getIsVote()){

                        helper.setText(R.id.text_vote, "去投票");
                        ((TextView)helper.getView(R.id.text_vote)).setTextColor(getResources().getColor(R.color.zm_red));

                        if(2==isvote){
                            //TODO 可以投票
                            helper.setText(R.id.text_vote, "去投票");
                            ((TextView)helper.getView(R.id.text_vote)).setTextColor(getResources().getColor(R.color.zm_red));
                            helper.getView(R.id.text_vote).setOnClickListener(new View.OnClickListener(){

                                @Override
                                public void onClick(View v) {
                                    if(1==item.getType()){
                                        Intent intent =new Intent(VoteListActivity.this,VoteDetailsActivity.class);
                                        intent.putExtra("isvote",isvote);
                                        intent.putExtra("id",item.getId());

                                        startActivity(intent);
                                    }else if(2==item.getType()){
                                        Intent intent =new Intent(VoteListActivity.this,VoteDateVoideActivity.class);
                                        intent.putExtra("isvote",isvote);
                                        intent.putExtra("id",item.getId());
                                        startActivity(intent);
                                    }
                                }
                            });


                        } else {
                            //TODO 不可以投票
                            helper.setText(R.id.text_vote, "去投票");
                            ((TextView)helper.getView(R.id.text_vote)).setTextColor(getResources().getColor(R.color.group_list_gray));

                        }
                    }else {
                        helper.setText(R.id.text_vote, "已投");
                        ((TextView)helper.getView(R.id.text_vote)).setTextColor(getResources().getColor(R.color.group_list_gray));
                    }

                }

            };
            gradview.setAdapter(voteAdapter);
            gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(1==voteAdapter.getmDatas().get(position).getType()){
                        Intent intent =new Intent(VoteListActivity.this,VoteDetailsActivity.class);
                        intent.putExtra("isvote",isvote);
                        intent.putExtra("id",voteAdapter.getmDatas().get(position).getId());
                        startActivity(intent);
                    }else if(2==voteAdapter.getmDatas().get(position).getType()){
                        Intent intent =new Intent(VoteListActivity.this,VoteDateVoideActivity.class);
                        intent.putExtra("id",voteAdapter.getmDatas().get(position).getId());
                        intent.putExtra("isvote",isvote);
                        startActivity(intent);
                    }
                }
            });
        }
       /* voteAdapter = new BaseVoteAdapter<VoteBaseBean.DataBean>(this, gradview, R.layout.item_vote_project_list) {
            @Override
            public void convert(BaseViewHolder helper, final VoteBaseBean.DataBean item) {
                *//*helper.setImageByUrl(R.id.ivImg, item.getImage(), R.mipmap.defaule, R.mipmap.defaule);
                helper.setText(R.id.tvTitle, item.getTitle());
                helper.setText(R.id.tvTime, item.getStart_at());*//*

            }

        };
        gradview.setAdapter(voteAdapter);
        gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        ProxyUtils.getHttpProxy().subvotelist(this,id,Integer.valueOf(PreferManager.getUserId()));

    }

    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
        }
    private void getvotelist(VoteSubvotelistBean data){
        if(data.getData()!=null){
            isvote=data.getData().getIsVote();
            voteAdapter.setDatas(data.getData().getVotesubList());
            if(data.getData().getVoteFrequency()==2){
                text_rule_voteFrequency.setText("2 .投票期间每人只能投一次");
            }else if(data.getData().getVoteFrequency()==1){
                text_rule_voteFrequency.setText("2 .投票期间每人每天只能投一次");
            }
            //if(data.getData().getVoteNumber()){}
            text_rule_voteNumber.setText("1 .每次投票只能投选"+data.getData().getVoteNumber()+"人");
            text_time.setText("4 . 本次投票的截止日期 ："+data.getData().getEndAt());
        }
        isonclick=false;
    }
    private void getusersubvote(VoteUsersubvoteBean data){
        ProxyUtils.getHttpProxy().subvotelist(this,id,Integer.valueOf(PreferManager.getUserId()));

    }

    @Override
    protected void onCreateView(int layoutResID) {
        super.onCreateView(layoutResID);
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }
}
