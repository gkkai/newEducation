package com.meiliangzi.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.StudyList;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.ParamUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.AnswerActivity;
import com.meiliangzi.app.ui.ArticalDetailActivity;
import com.meiliangzi.app.ui.MyScoreActivity;
import com.meiliangzi.app.ui.VideoDetailActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 我的成绩
 **/

public class ScoreFragment1 extends BaseFragment implements XListView.IXListViewListener {


    @BindView(R.id.listView)
    XListView listView;

    @BindView(R.id.ll_no)
    LinearLayout ll_no;
    private BaseQuickAdapter<StudyList.DataBean> adapter;

    private int page = 1;
    private MyScoreActivity activity;
    private String type;


    public ScoreFragment1() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return createView(inflater.inflate(R.layout.fragment_lession, null, false));
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        activity = (MyScoreActivity) getActivity();

        adapter = new BaseQuickAdapter<StudyList.DataBean>(getActivity(),listView, R.layout.item_my_score) {
            @Override
            public void convert(BaseViewHolder helper, final StudyList.DataBean item) {
                helper.setImageByUrl(R.id.imImg,item.getUserStudy().getImg(), 0,0);
                helper.setText(R.id.tvTitle,item.getUserStudy().getName());
                helper.setText(R.id.tv_score, item.getStudy_achievement()+"分");
                helper.setText(R.id.tvTime,item.getUserStudy().getCreate_time());
                TextView tvCheckAnswer = helper.getView(R.id.tvCheckAnswer);
                if(item.isIs_study()){
                    helper.setText(R.id.tvFlag, "已学习");
                }else {
                    helper.setText(R.id.tvFlag, "未学习");
                    helper.getView(R.id.tvCheckAnswer).setVisibility(View.GONE);
                }
                if (item.isStudy_status()) {
                    helper.getView(R.id.tvCheckAnswer).setVisibility(View.VISIBLE);
                    tvCheckAnswer.setTextColor(getResources().getColor(R.color.colorRed));
                    helper.getView(R.id.tvCheckAnswer).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ParamUtils paramUtils = ParamUtils.build();
                            paramUtils.put("id",item.getUserStudy().getId());
                            paramUtils.put("checkAnswer",true);
                            IntentUtils.startAtyForResult(getActivity(), AnswerActivity.class, 1002, paramUtils.create());
                        }
                    });
                } else {
                    helper.getView(R.id.tvCheckAnswer).setBackgroundResource(R.drawable.shape_collect);
                    tvCheckAnswer.setTextColor(getResources().getColor(R.color.colorGrgyLight));
                    helper.getView(R.id.tvCheckAnswer).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ParamUtils.NameValue> nameValues = ParamUtils.build()
                        .put("from", "score")
                        .put("id", adapter.getItem(position-1).getUserStudy().getId()).create();
                if(adapter.getItem(position-1).getUserStudy().isType()){
                    IntentUtils.startAtyWithParams(getActivity(), ArticalDetailActivity.class,nameValues);
                }else {
                    IntentUtils.startAtyWithParams(getActivity(), VideoDetailActivity.class,nameValues);
                }
            }
        });
    }

    @Override
    protected void asyncRetrive() {

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
       if(page == 1){
           adapter.pullRefresh(new ArrayList<StudyList.DataBean>());
       }else {
           adapter.pullLoad(new ArrayList<StudyList.DataBean>());
       }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData("2");
    }

    public void getData(String type) {
        ProxyUtils.getHttpProxyNoDialog().userstudylist(this, PreferManager.getUserId(), type, String.valueOf(Constant.PAGESIZE), String.valueOf(page), "1");
    }

    protected void getData(StudyList studyList) {
        if(studyList.getData()!=null&&studyList.getData().size()==0){
            ll_no.setVisibility(View.VISIBLE);
        }else {
            ll_no.setVisibility(View.GONE);
        }
        if (page == 1) {
            adapter.pullRefresh(studyList.getData());
        } else {
            adapter.pullLoad(studyList.getData());
        }
    }


    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(1==errorCode){
            ll_no.setVisibility(View.VISIBLE);
        }
        if (page == 1) {
            adapter.pullRefresh(new ArrayList<StudyList.DataBean>());
        }else {
            adapter.pullLoad(new ArrayList<StudyList.DataBean>());
        }
    }



    @Override
    public void onRefresh() {
        page = 1;
        getData("2");

    }

    @Override
    public void onLoadMore() {
        page++;
        getData("2");
    }
}
