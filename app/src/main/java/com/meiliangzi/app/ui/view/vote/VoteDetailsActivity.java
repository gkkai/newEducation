package com.meiliangzi.app.ui.view.vote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.VoteUsersubvoteBean;
import com.meiliangzi.app.model.bean.VpteSubvoteinfoBean;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.widget.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import xiaobo.com.video.VideoPlayer;
import xiaobo.com.video.VideoPlayerStandard;

public class VoteDetailsActivity extends BaseActivity {
    /*@BindView(R.id.gradview)
    MyGridView gradview;*/
    @BindView(R.id.webview)
    WebView webview;
    /*@BindView(R.id.text_vote_title)
    TextView text_vote_title;*/
    /*@BindView(R.id.text_desc)
    TextView text_desc;*/
    @BindView(R.id.text_blow_num)
    TextView text_blow_num;
    @BindView(R.id.text_blow_vote)
    TextView text_blow_vote;
    BaseVoteAdapter<String> voteAdapter;
    List<String> urls=new ArrayList<>();
    private int id;
    private int isvote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_vote_details);
    }

    @Override
    protected void findWidgets() {
        id=getIntent().getIntExtra("id",0000000);
        isvote=getIntent().getIntExtra("isvote",0000000);

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProxyUtils.getHttpProxy().subvoteinfo(this,id,Integer.valueOf(NewPreferManager.getId()));

    }

    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
        //ProxyUtils.getHttpProxy().subvoteinfo(this,id,Integer.valueOf(PreferManager.getUserId()));

    }
    private void getsubvoteinfo(VpteSubvoteinfoBean data){
        text_blow_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_blow_vote.setEnabled(false);
                ProxyUtils.getHttpProxy().usersubvote(VoteDetailsActivity.this,id,Integer.valueOf(NewPreferManager.getId()));


            }
        });
        urls.clear();
        if(data.getData()!=null){
            webview.loadUrl(data.getData().getUrl());
          /* voteAdapter.setDatas(data.getData().getImage());
            text_blow_num.setText(data.getData().getUserVoteLogNumber()+"");
            if("".equals(data.getData().getDesc())||null==data.getData().getDesc()){
                text_desc.setVisibility(View.GONE);
            }else {
                text_desc.setVisibility(View.VISIBLE);
                text_desc.setText(data.getData().getDesc());
            }

            text_vote_title.setText(data.getData().getTitle());*/
            text_blow_num.setText(data.getData().getUserVoteLogNumber()+"");
            if(2==isvote){
                if(data.getData().getIsVote()==2){
                    //TODO 能可以投票
                    text_blow_vote.setText( "投票");
                    text_blow_vote.setBackground(getResources().getDrawable(R.mipmap.votestartlong));
                    text_blow_vote.setEnabled(true);

                }else {
                    //TODO 不能投票
                    text_blow_vote.setText( "已投");
                    text_blow_vote.setBackground(getResources().getDrawable(R.mipmap.voterendlong));
                    text_blow_vote.setEnabled(false);

                }
            }else {
                //TODO 不能投票
                text_blow_vote.setText( "投票");
                text_blow_vote.setBackground(getResources().getDrawable(R.mipmap.voterendlong));
                text_blow_vote.setEnabled(false);
            }


        }
    }
    private void getusersubvote(VoteUsersubvoteBean data){

        ProxyUtils.getHttpProxy().subvoteinfo(this,id,Integer.valueOf(NewPreferManager.getId()));

    }
}
