package com.meiliangzi.app.ui.view.Academy;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.PageListBean;
import com.meiliangzi.app.widget.XListView;


import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class WeekAnswerActivity  extends BaseActivity implements XListView.IXListViewListener {
    @BindView(R.id.listView)
    XListView listView;
    BaseVoteAdapter<PageListBean.Data> kaoshiAdapter;
    private int page;
    private String type;

    @BindView(R.id.tv_title)
    TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getStringExtra("type");
        onCreateView(R.layout.activity_week_answer);
    }

    private int compareTime(String time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String timenow=df.format(new Date());
        try {
            Date dt1 = df.parse(time);
            Date dt2 = df.parse(timenow);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                //TODO 过期
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        if("0".equals(type)){
            tv_title.setText("智能答题");
        }
        kaoshiAdapter=new BaseVoteAdapter<PageListBean.Data>(this,listView,R.layout.week_answer_list) {
            @Override
            public void convert(BaseViewHolder helper, final PageListBean.Data item) {
                if(item.getType().equals("0")){
                    helper.getView(R.id.rl_0).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_1).setVisibility(View.GONE);
                    ((TextView)helper.getView(R.id.tv_isanswer0)).setText(item.getTotalNumber()+"道题");
                    ((TextView)helper.getView(R.id.tv_time)).setText(item.getValidityTimeStart());
                }else {
                    helper.getView(R.id.rl_1).setVisibility(View.VISIBLE);
                    helper.getView(R.id.rl_0).setVisibility(View.GONE);
                    ((TextView)helper.getView(R.id.tv_isanswer1)).setText("未答题");
                }

                switch (compareTime(item.getValidityTimeEnd())){
                    case -1:
                        ((TextView)helper.getView(R.id.tv_startquestions)).setText("已过期");
                        ((TextView)helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.group_list_gray));
                        helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO 产看解析
                                Intent intent=new Intent(getBaseContext(), AnalysisActivity.class);
                                intent.putExtra("paperId",item.getId());
                                //TODO
                                intent.putExtra("title",tv_title.getText().toString());
                                intent.putExtra("type",type);
                                intent.putExtra("time",item.getDuration());
                                intent.putExtra("pagetitle",item.getTitle());
                                intent.putExtra("totalNumber",item.getTotalNumber());
                                intent.putExtra("countDown",item.getCountDown());
                                intent.putExtra("mode",item.getMode());
                                intent.putExtra("userPaperId",item.getUserPaperId());
                                intent.putExtra("userId", NewPreferManager.getId());
                                intent.putExtra("finishStatus",item.getFinishStatus());
                                intent.putExtra("answerTime",item.getAnswerTime());
                                intent.putExtra("repeatAnswer",item.getRepeatAnswer());
                                intent.putExtra("createType",item.getCreateType());
                                startActivity(intent);

                            }
                        });
                        break;
                    default:

                            //TODO 未过期
                            switch (item.getFinishStatus()){
                                case "0"://结束
                                    ((TextView)helper.getView(R.id.tv_startquestions)).setText("重新答题");
                                    ((TextView)helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));

                                    helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
// //TODO 判断考试有没有完成
                                            //TODO 跳转答题
                                            Intent intent=new Intent(getBaseContext(), ExaminationQuestionsActivity.class);
                                            intent.putExtra("paperId",item.getId());
                                            //TODO
                                            intent.putExtra("title",tv_title.getText().toString());
                                            intent.putExtra("type",type);
                                            intent.putExtra("time",item.getDuration());
                                            intent.putExtra("pagetitle",item.getTitle());
                                            intent.putExtra("totalNumber",item.getTotalNumber());
                                            intent.putExtra("countDown",item.getCountDown());
                                            intent.putExtra("mode",item.getMode());
                                            intent.putExtra("userPaperId",item.getUserPaperId());
                                            intent.putExtra("userId",NewPreferManager.getId());
                                            intent.putExtra("finishStatus",item.getFinishStatus());
                                            intent.putExtra("answerTime",item.getAnswerTime());
                                            intent.putExtra("repeatAnswer",item.getRepeatAnswer());
                                            intent.putExtra("createType",item.getCreateType());
                                            startActivity(intent);

                                        }
                                    });
                                    break;
                                case "1"://未结束
                                    ((TextView)helper.getView(R.id.tv_startquestions)).setText("开始答题");
                                    ((TextView)helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));

                                    helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //TODO 判断考试有没有完成
                                            //TODO 跳转答题
                                            Intent intent=new Intent(getBaseContext(), ExaminationQuestionsActivity.class);
                                            intent.putExtra("paperId",item.getId());
                                            //TODO
                                            intent.putExtra("type",type);
                                            intent.putExtra("title",tv_title.getText().toString());
                                            intent.putExtra("time",item.getDuration());
                                            intent.putExtra("pagetitle",item.getTitle());
                                            intent.putExtra("totalNumber",item.getTotalNumber());
                                            intent.putExtra("countDown",item.getCountDown());
                                            intent.putExtra("mode",item.getMode());
                                            intent.putExtra("userPaperId",item.getUserPaperId());
                                            intent.putExtra("userId",NewPreferManager.getId());
                                            intent.putExtra("finishStatus",item.getFinishStatus());
                                            intent.putExtra("answerTime",item.getAnswerTime());
                                            intent.putExtra("repeatAnswer",item.getRepeatAnswer());
                                            intent.putExtra("createType",item.getCreateType());
                                            startActivity(intent);

                                        }
                                    });
                                    break;
                                case "2"://未答题
                                    ((TextView)helper.getView(R.id.tv_startquestions)).setText("开始答题");
                                    ((TextView)helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));

                                    helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //TODO 判断考试有没有完成


                                            //TODO 跳转答题
                                            Intent intent=new Intent(getBaseContext(), ExaminationQuestionsActivity.class);
                                            intent.putExtra("paperId",item.getId());
                                            //TODO
                                            intent.putExtra("title",tv_title.getText().toString());
                                            intent.putExtra("type",type);
                                            intent.putExtra("time",item.getDuration());
                                            intent.putExtra("pagetitle",item.getTitle());
                                            intent.putExtra("totalNumber",item.getTotalNumber());
                                            intent.putExtra("countDown",item.getCountDown());
                                            intent.putExtra("mode",item.getMode());

                                            intent.putExtra("createType",item.getCreateType());
                                            intent.putExtra("userPaperId",item.getUserPaperId());
                                            intent.putExtra("userId",NewPreferManager.getId());
                                            intent.putExtra("finishStatus",item.getFinishStatus());
                                            intent.putExtra("answerTime",item.getAnswerTime());
                                            intent.putExtra("repeatAnswer",item.getRepeatAnswer());
                                            intent.putExtra("createType",item.getCreateType());
                                            startActivity(intent);

                                        }
                                    });
                                    break;
                        }







                }
                if(item.getPublicOrprivate()==0){
                    ((TextView)helper.getView(R.id.tv_title)).setText(item.getTitle()+"("+"公共"+")");
                }else {
                    ((TextView)helper.getView(R.id.tv_title)).setText(item.getTitle()+"("+"专业"+")");
                }



            }
        };
        listView.setAdapter(kaoshiAdapter);


    }

    @Override
    protected void initComponent() {
        //TODO 获取试卷列表
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId",NewPreferManager.getId());
        jsonObject.put("type",type);
        jsonObject.put("pageNumber",page+"");
        jsonObject.put("pageSize","10");
        OkhttpUtils.getInstance(this).getPageList("academyService/examinationUserPaper/getPageList", jsonObject, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final PageListBean baseBean= gson.fromJson(json,PageListBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if("1".equals(baseBean.getCode())){
                                ToastUtils.show(baseBean.getMessage());
                            }else {
                                if (page == 0) {
                                    kaoshiAdapter.pullRefresh(baseBean.getData());
                                } else {
                                    kaoshiAdapter.pullLoad(baseBean.getData());
                                }
                            }
                        }catch (Exception e){

                        }


                    }
                });


            }
        });

    }
    private void getPageList(PageListBean bean){

       // kaoshiAdapter.setDatas(bean.getData());
    }
    @Override
    public void onRefresh() {
        page = 0;
        initComponent();
    }

    @Override
    public void onLoadMore() {
        page++;
        initComponent();

    }

}
