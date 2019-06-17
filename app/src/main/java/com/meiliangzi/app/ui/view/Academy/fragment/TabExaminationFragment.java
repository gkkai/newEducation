package com.meiliangzi.app.ui.view.Academy.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.view.Academy.AnalysisActivity;
import com.meiliangzi.app.ui.view.Academy.ExaminationQuestionsActivity;
import com.meiliangzi.app.ui.view.Academy.bean.PageListBean;
import com.meiliangzi.app.widget.XListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabExaminationFragment extends BaseFragment implements XListView.IXListViewListener {

    @BindView(R.id.listView)
    XListView listView;

    BaseVoteAdapter<PageListBean.Data> kaoshiAdapter;
    private String names="";
    private String type;
    private int page;
    private MyDialog myDialog;

    public TabExaminationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = createView(inflater.inflate(R.layout.fragment_tab_examination, container, false));
        return view;
    }
    private int compareTime(String time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Calendar curr = Calendar.getInstance();

        curr.set(Calendar.DAY_OF_MONTH,curr.get(Calendar.DAY_OF_MONTH)-1);

        Date date=curr.getTime();
        String timenow=df.format(date);
        try {
            //
            Date dt1 = df.parse(time);
            Date dt2 = df.parse(timenow);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
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
        names = getArguments().getString("names");
        if("专题考试".equals(getArguments().getString("names"))){
            type="3";
        }
        if("定向考试".equals(getArguments().getString("names"))){
            type="2";
        }
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        kaoshiAdapter=new BaseVoteAdapter<PageListBean.Data>(getContext(),listView,R.layout.examination_item) {
            @Override
            public void convert(BaseViewHolder helper, final PageListBean.Data  item) {
//                if (item.getPublicOrprivate() == 0) {
//
//                } else {
//                    ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle() + "(" + "专业" + ")");
//                }
                ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle());
                if (null == item.getDuration()) {
                    ((TextView) helper.getView(R.id.tv_duration)).setText(item.getTotalNumber() + "道题" + "  " + "0分钟");
                } else {
                    ((TextView) helper.getView(R.id.tv_duration)).setText(item.getTotalNumber() + "道题" + "  " + (Integer.valueOf(item.getDuration()) / 60) + "分钟");
                }

                ((TextView) helper.getView(R.id.tv_Time)).setText(item.getValidityTimeStart() + "-" + item.getValidityTimeEnd());

                if (TextUtils.isEmpty(item.getFixedTimeStart())) {
                    //TODO
                    switch (compareTime(item.getValidityTimeEnd())) {
                        case -1:
                            ((TextView) helper.getView(R.id.tv_startquestions)).setText("已过期");

                            ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.group_list_gray));
                            helper.getView(R.id.tv_checkanalysis).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TODO 产看解析
                                    Intent intent = new Intent(getActivity(), AnalysisActivity.class);
                                    intent.putExtra("paperId", item.getId());
                                    intent.putExtra("title", names);
                                    //TODO
                                    intent.putExtra("type", type);
                                    intent.putExtra("time", item.getDuration());
                                    intent.putExtra("pagetitle", item.getTitle());
                                    intent.putExtra("totalNumber", item.getTotalNumber());
                                    intent.putExtra("countDown", item.getCountDown());
                                    intent.putExtra("mode", item.getMode());
                                    intent.putExtra("userPaperId", item.getUserPaperId());
                                    intent.putExtra("userId", NewPreferManager.getId());
                                    intent.putExtra("finishStatus", item.getFinishStatus());
                                    intent.putExtra("answerTime", item.getAnswerTime());
                                    intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                    intent.putExtra("createType", item.getCreateType());
                                    startActivity(intent);

                                }
                            });
                            if (item.getScore() == null) {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + "0");

                            } else {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + item.getScore());

                            }
                            helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//

                                }
                            });
                            helper.showOrGoneView(R.id.ll_analysis, true);
                            break;
                        default:
                            //TODO 未过期
                            switch (item.getFinishStatus()) {
                                case "0"://结束
                                    ((TextView) helper.getView(R.id.tv_startquestions)).setText("已完成");
                                    ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.group_list_gray));

                                    helper.showOrGoneView(R.id.ll_analysis, true);

                                    helper.getView(R.id.tv_checkanalysis).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //TODO 产看解析
                                            Intent intent = new Intent(getActivity(), AnalysisActivity.class);
                                            intent.putExtra("paperId", item.getId());
                                            intent.putExtra("title", names);
                                            //TODO
                                            intent.putExtra("type", type);
                                            intent.putExtra("time", item.getDuration());
                                            intent.putExtra("pagetitle", item.getTitle());
                                            intent.putExtra("totalNumber", item.getTotalNumber());
                                            intent.putExtra("countDown", item.getCountDown());
                                            intent.putExtra("mode", item.getMode());
                                            intent.putExtra("userPaperId", item.getUserPaperId());
                                            intent.putExtra("userId", NewPreferManager.getId());
                                            intent.putExtra("finishStatus", item.getFinishStatus());
                                            intent.putExtra("answerTime", item.getAnswerTime());
                                            intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                            intent.putExtra("createType", item.getCreateType());
                                            startActivity(intent);

                                        }
                                    });
                                    if (item.getScore() == null) {
                                        ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + "0");

                                    } else {
                                        ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + item.getScore());

                                    }
                                    break;
                                case "1"://未结束
                                    ((TextView) helper.getView(R.id.tv_startquestions)).setText("继续答题");
                                    ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));


                                    helper.showOrGoneView(R.id.ll_analysis, false);
                                    helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //TODO 判断考试有没有完成


                                            //TODO 跳转答题
                                            Intent intent = new Intent(getActivity(), ExaminationQuestionsActivity.class);
                                            intent.putExtra("paperId", item.getId());
                                            intent.putExtra("title", names);
                                            //TODO
                                            intent.putExtra("type", type);
                                            intent.putExtra("time", item.getDuration());
                                            intent.putExtra("pagetitle", item.getTitle());
                                            intent.putExtra("totalNumber", item.getTotalNumber());
                                            intent.putExtra("countDown", item.getCountDown());
                                            intent.putExtra("mode", item.getMode());
                                            intent.putExtra("userPaperId", item.getUserPaperId());
                                            intent.putExtra("userId", NewPreferManager.getId());
                                            intent.putExtra("finishStatus", item.getFinishStatus());
                                            intent.putExtra("answerTime", item.getAnswerTime());
                                            intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                            intent.putExtra("createType", item.getCreateType());
                                            intent.putExtra("createType", item.getCreateType());
                                            startActivity(intent);

                                        }
                                    });
                                    break;
                                case "2"://未答题
                                    helper.showOrGoneView(R.id.ll_analysis, false);
                                    ((TextView) helper.getView(R.id.tv_startquestions)).setText("开始答题");
                                    ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));


                                    helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //TODO 判断考试有没有完成


                                            //TODO 跳转答题
                                            Intent intent = new Intent(getActivity(), ExaminationQuestionsActivity.class);
                                            intent.putExtra("paperId", item.getId());
                                            intent.putExtra("title", names);
                                            //TODO
                                            intent.putExtra("type", type);
                                            intent.putExtra("time", item.getDuration());
                                            intent.putExtra("pagetitle", item.getTitle());
                                            intent.putExtra("totalNumber", item.getTotalNumber());
                                            intent.putExtra("countDown", item.getCountDown());
                                            intent.putExtra("mode", item.getMode());
                                            intent.putExtra("userPaperId", item.getUserPaperId());
                                            intent.putExtra("userId", NewPreferManager.getId());
                                            intent.putExtra("finishStatus", item.getFinishStatus());
                                            intent.putExtra("answerTime", item.getAnswerTime());
                                            intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                            intent.putExtra("createType", item.getCreateType());
                                            // setTargetFragment(getTargetFragment(),intent);
                                            // startActivityForResult();
                                            // TabExaminationFragment.this.startActivityForResult(intent, 1);
                                            startActivity(intent);

                                        }
                                    });
                                    break;

                            }


                    }
                } else {

                    if(compareTime1(item.getFixedTimeStart(),item.getDuration())==-1){
                        //TODO 已经结束
                        if(!"0".equals(item.getFinishStatus())){

                            //TODO  未答题

                            ((TextView) helper.getView(R.id.tv_startquestions)).setText("已结束");
                            ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.group_list_gray));

                            helper.showOrGoneView(R.id.ll_analysis, true);

                            helper.getView(R.id.tv_checkanalysis).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TODO 产看解析
                                    Intent intent = new Intent(getActivity(), AnalysisActivity.class);
                                    intent.putExtra("paperId", item.getId());
                                    intent.putExtra("title", names);
                                    //TODO
                                    intent.putExtra("type", type);
                                    intent.putExtra("time", item.getDuration());
                                    intent.putExtra("pagetitle", item.getTitle());
                                    intent.putExtra("totalNumber", item.getTotalNumber());
                                    intent.putExtra("countDown", item.getCountDown());
                                    intent.putExtra("mode", item.getMode());
                                    intent.putExtra("userPaperId", item.getUserPaperId());
                                    intent.putExtra("userId", NewPreferManager.getId());
                                    intent.putExtra("finishStatus", item.getFinishStatus());
                                    intent.putExtra("answerTime", item.getAnswerTime());
                                    intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                    intent.putExtra("createType", item.getCreateType());
                                    startActivity(intent);

                                }
                            });
                            if (item.getScore() == null) {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + "0");

                            } else {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + item.getScore());

                            }



                        }else {
                            //TODO 已结束  已答题
                            ((TextView) helper.getView(R.id.tv_startquestions)).setText("已完成");
                            ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.group_list_gray));

                            helper.showOrGoneView(R.id.ll_analysis, true);

                            helper.getView(R.id.tv_checkanalysis).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TODO 产看解析
                                    Intent intent = new Intent(getActivity(), AnalysisActivity.class);
                                    intent.putExtra("paperId", item.getId());
                                    intent.putExtra("title", names);
                                    //TODO
                                    intent.putExtra("type", type);
                                    intent.putExtra("time", item.getDuration());
                                    intent.putExtra("pagetitle", item.getTitle());
                                    intent.putExtra("totalNumber", item.getTotalNumber());
                                    intent.putExtra("countDown", item.getCountDown());
                                    intent.putExtra("mode", item.getMode());
                                    intent.putExtra("userPaperId", item.getUserPaperId());
                                    intent.putExtra("userId", NewPreferManager.getId());
                                    intent.putExtra("finishStatus", item.getFinishStatus());
                                    intent.putExtra("answerTime", item.getAnswerTime());
                                    intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                    intent.putExtra("createType", item.getCreateType());
                                    startActivity(intent);

                                }
                            });
                            if (item.getScore() == null) {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + "0");

                            } else {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + item.getScore());

                            }
                        }
                    }else {

                        helper.showOrGoneView(R.id.ll_analysis, false);
                        //((TextView) helper.getView(R.id.tv_startquestions)).setText("开始答题");
                        ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));
                        //TODO  未结束


                            switch (item.getFinishStatus()) {
                        case "0"://结束
                            ((TextView) helper.getView(R.id.tv_startquestions)).setText("已完成");
                            ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.group_list_gray));

                            helper.showOrGoneView(R.id.ll_analysis, true);

                            helper.getView(R.id.tv_checkanalysis).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TODO 产看解析
                                    Intent intent = new Intent(getActivity(), AnalysisActivity.class);
                                    intent.putExtra("paperId", item.getId());
                                    intent.putExtra("title", names);
                                    //TODO
                                    intent.putExtra("type", type);
                                    intent.putExtra("time", (compareTime3(item.getFixedTimeStart(),item.getDuration())));
                                    intent.putExtra("pagetitle", item.getTitle());
                                    intent.putExtra("totalNumber", item.getTotalNumber());
                                    intent.putExtra("countDown", item.getCountDown());
                                    intent.putExtra("mode", item.getMode());
                                    intent.putExtra("userPaperId", item.getUserPaperId());
                                    intent.putExtra("userId", NewPreferManager.getId());
                                    intent.putExtra("finishStatus", item.getFinishStatus());
                                    intent.putExtra("answerTime", item.getAnswerTime());
                                    intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                    intent.putExtra("createType", item.getCreateType());
                                    startActivity(intent);

                                }
                            });
                            if (item.getScore() == null) {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + "0");

                            } else {
                                ((TextView) helper.getView(R.id.tv_examinationresults)).setText("考试成绩：" + item.getScore());

                            }
                            break;
                        case "1"://未结束
                            ((TextView) helper.getView(R.id.tv_startquestions)).setText("继续答题");
                            ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));


                            helper.showOrGoneView(R.id.ll_analysis, false);
                            if(compareTime2(item.getFixedTimeStart())==-1){
                                helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //TODO 判断考试有没有完成


                                        //TODO 跳转答题
                                        Intent intent = new Intent(getActivity(), ExaminationQuestionsActivity.class);
                                        intent.putExtra("paperId", item.getId());
                                        intent.putExtra("title", names);
                                        //TODO
                                        intent.putExtra("type", type);
                                        intent.putExtra("time", (compareTime3(item.getFixedTimeStart(),item.getDuration())));
                                        intent.putExtra("pagetitle", item.getTitle());
                                        intent.putExtra("totalNumber", item.getTotalNumber());
                                        intent.putExtra("countDown", item.getCountDown());
                                        intent.putExtra("mode", item.getMode());
                                        intent.putExtra("userPaperId", item.getUserPaperId());
                                        intent.putExtra("userId", NewPreferManager.getId());
                                        intent.putExtra("finishStatus", item.getFinishStatus());
                                        intent.putExtra("answerTime", item.getAnswerTime());
                                        intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                        intent.putExtra("createType", item.getCreateType());
                                        // setTargetFragment(getTargetFragment(),intent);
                                        // startActivityForResult();
                                        // TabExaminationFragment.this.startActivityForResult(intent, 1);
                                        startActivity(intent);

                                    }
                                });
                                break;
                            }else {

                                //TODO 未开始
                                ToastUtils.show("考试未开始");
                            }

                            break;
                        case "2"://未答题
                            helper.showOrGoneView(R.id.ll_analysis, false);
                            ((TextView) helper.getView(R.id.tv_startquestions)).setText("开始答题");
                            ((TextView) helper.getView(R.id.tv_startquestions)).setTextColor(getResources().getColor(R.color.black3));


                            if(compareTime2(item.getFixedTimeStart())==-1){
                                helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //TODO 判断考试有没有完成


                                        //TODO 跳转答题
                                        Intent intent = new Intent(getActivity(), ExaminationQuestionsActivity.class);
                                        intent.putExtra("paperId", item.getId());
                                        intent.putExtra("title", names);
                                        //TODO
                                        intent.putExtra("type", type);
                                        intent.putExtra("time", (compareTime3(item.getFixedTimeStart(),item.getDuration())));
                                        intent.putExtra("pagetitle", item.getTitle());
                                        intent.putExtra("totalNumber", item.getTotalNumber());
                                        intent.putExtra("countDown", item.getCountDown());
                                        intent.putExtra("mode", item.getMode());
                                        intent.putExtra("userPaperId", item.getUserPaperId());
                                        intent.putExtra("userId", NewPreferManager.getId());
                                        intent.putExtra("finishStatus", item.getFinishStatus());
                                        intent.putExtra("answerTime", item.getAnswerTime());
                                        intent.putExtra("repeatAnswer", item.getRepeatAnswer());
                                        intent.putExtra("createType", item.getCreateType());
                                        // setTargetFragment(getTargetFragment(),intent);
                                        // startActivityForResult();
                                        // TabExaminationFragment.this.startActivityForResult(intent, 1);
                                        startActivity(intent);

                                    }
                                });
                                break;
                            }else {
                                //TODO 未开始
                                ToastUtils.show("考试未开始");
                            }


                    }




                    }


                }
            }


        };
        listView.setAdapter(kaoshiAdapter);

    }
    private long compareTime3(String time,String duration){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime()+Integer.valueOf(duration)*1000;
       long time1= (ts-System.currentTimeMillis())/1000;
        return  time1;


    }
    private int compareTime2(String time){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        if(ts> System.currentTimeMillis()){

            return 1;
        }else {
            return -1;
        }

    }
    private int compareTime1(String time,String duration){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime()+Integer.valueOf(duration)*1000;
        if(ts> System.currentTimeMillis()){

            return 1;
        }else {
            return -1;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        initComponent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void initComponent() {
        //TODO 获取试卷列表
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId",NewPreferManager.getId());
        jsonObject.put("type",type);
        jsonObject.put("pageNumber",page+"");
        jsonObject.put("pageSize","10");
        OkhttpUtils.getInstance(getActivity()).getPageList("academyService/examinationUserPaper/getPageList", jsonObject, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final PageListBean baseBean= gson.fromJson(json,PageListBean.class);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if("1".equals(baseBean.getCode())){
                            ToastUtils.show(baseBean.getMessage());
                        }else {
                            if(baseBean.getData().size()==0){
                                listView.setHeaderDividersEnabled(false);
                                listView.setFooterDividersEnabled(false);

                            }else {
                                listView.setHeaderDividersEnabled(true);
                                listView.setFooterDividersEnabled(true);
                            }
                            if (page == 0) {
                                kaoshiAdapter.pullRefresh(baseBean.getData());
                            } else {
                                kaoshiAdapter.pullLoad(baseBean.getData());
                            }
                        }

                    }
                });


            }
        });


    }
    private void getPageList(PageListBean bean){

        kaoshiAdapter.setDatas(bean.getData());
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
