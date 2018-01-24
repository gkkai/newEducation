package com.meiliangzi.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.GroupActivity;
import com.meiliangzi.app.ui.NewsDetailActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.view.MapNewActivity;
import com.meiliangzi.app.ui.view.ZoomActivity;
import com.meiliangzi.app.ui.view.vote.VoteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SheQunFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.rl_shequn_study_center)
    RelativeLayout rl_shequn_study_center;
    @BindView(R.id.rl_shequn_map)
    RelativeLayout rl_shequn_map;
    @BindView(R.id.rl_shequn_zoommeet)
    RelativeLayout rl_shequn_zoommeet;
    @BindView(R.id.rl_shequn_industry)
    RelativeLayout rl_shequn_industry;
    @BindView(R.id.rl_shequn_vote)
    RelativeLayout rl_shequn_vote;

    public SheQunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                View view = inflater.inflate(R.layout.fragment_she_qun, container, false);
        //ButterKnife.bind(this, view);
        createView(view);
        return view;
    }

    @Override
    protected void findWidgets() {
        rl_shequn_study_center.setOnClickListener(this);
        rl_shequn_map.setOnClickListener(this);

        rl_shequn_zoommeet.setOnClickListener(this);

        rl_shequn_industry.setOnClickListener(this);
        rl_shequn_vote.setOnClickListener(this);


    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_shequn_study_center:
                // TODO  查看更多
                Intent intentstudy=new Intent(MyApplication.activity, GroupActivity.class);
                getActivity().startActivity(intentstudy);
                break;
            case R.id.rl_shequn_map:
                // TODO  查看更多
                Intent intentmap=new Intent(MyApplication.activity, MapNewActivity.class);
                getActivity().startActivity(intentmap);
                break;
            case R.id.rl_shequn_zoommeet:
                // TODO  视频会议
                Intent intentZoom=new Intent(MyApplication.activity, ZoomActivity.class);
                getActivity().startActivity(intentZoom);
                break;
            case R.id.rl_shequn_industry:
                // TODO  行业资讯查看更多
                MyApplication.type=5;
                Intent intent=new Intent(MyApplication.activity, NewsDetailActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.rl_shequn_vote:
                // TODO  行业资讯查看更多
                Intent intentvote=new Intent(MyApplication.activity, VoteActivity.class);
                getActivity().startActivity(intentvote);
                break;
        }

    }
}
