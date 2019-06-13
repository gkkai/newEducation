package com.meiliangzi.app.ui.view.Academy;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.adapter.TopicAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.ComintQuestionsBackbean;
import com.meiliangzi.app.ui.view.Academy.bean.OutAnswerBean;
import com.meiliangzi.app.ui.view.Academy.bean.PaperBean;
import com.meiliangzi.app.ui.view.Academy.dialog.SubmitDialog;
import com.meiliangzi.app.ui.view.Academy.fragment.AnalysisFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.ReadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnalysisActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.shadowView)
    ImageView shadowView;
    private int prePosition2;
    private int curPosition2;
    private TopicAdapter topicAdapter;
    RecyclerView recyclerView;
    @BindView(R.id.tv_answercard)
    TextView tv_answercard;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_pre)
    TextView tv_pre;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    @BindView(R.id.tv_no_duration)
    TextView tv_no_duration;
    @BindView(R.id.tv_pagetitle)
    TextView tv_pagetitle;
    @BindView(R.id.tv_alreadyanswered)
    TextView tv_alreadyanswered;
    @BindView(R.id.tv_totalNumber)
    TextView tv_totalNumber;
    String type;
    private MsgContentFragmentAdapter adapter;
    private String time;
    private String pagetitle;
    private String totalNumber;
    String userId;
    String finishStatus;
    String answerTime;
    String paperId;
    String title;
    String repeatAnswer;
    private String mode;
    private String userPaperId;
    private SubmitDialog submitDialog;
    public OutAnswerBean outAnswerBean;
    private Dialog dialog;
    private View inflate;
    private String createType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paperId=getIntent().getStringExtra("paperId");
        type=getIntent().getStringExtra("type");
        title=getIntent().getStringExtra("title");
        pagetitle=getIntent().getStringExtra("pagetitle");
        totalNumber=getIntent().getStringExtra("totalNumber");
        time = getIntent().getStringExtra("time");
        mode=getIntent().getStringExtra("mode");
        userPaperId=getIntent().getStringExtra("userPaperId");
        userId=getIntent().getStringExtra("userId");
        finishStatus=getIntent().getStringExtra("finishStatus");
        answerTime=getIntent().getStringExtra("answerTime");
        repeatAnswer=getIntent().getStringExtra("repeatAnswer");
        createType=getIntent().getStringExtra("createType");
        onCreateView(R.layout.activity_analysis);
    }

    @Override
    protected void findWidgets() {
        tv_title.setText(title);
        tv_pagetitle.setText(pagetitle);

        tv_alreadyanswered.setText(1+"");
        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.answercard, null);
        recyclerView=(RecyclerView)inflate.findViewById(R.id.list);
        tv_answercard.setOnClickListener(this);

    }

    @Override
    protected void initComponent() {
//TODO 获取试题
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId", NewPreferManager.getId());
        jsonObject.put("paperId",paperId);
        if("3".equals(createType)){
            OkhttpUtils.getInstance(this).getList("academyService/examinationQuestionsImport/getListByApp", jsonObject, new OkhttpUtils.onCallBack() {
                @Override
                public void onFaild(Exception e) {

                }

                @Override
                public void onResponse(final String json) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson=new Gson();
                                PaperBean baseBean= gson.fromJson(json,PaperBean.class);
                                if("1".equals(baseBean.getCode())){
                                    ToastUtils.show(baseBean.getMessage());
                                }else {
                                    MyApplication.paperBean=baseBean;
                                    String [] s=new String[]{"A","B","C","D","E","F","G","H","I"};
                                    List<PaperBean.Data> beans=MyApplication.paperBean.getData();
                                    for(int i=0;i<beans.size();i++){
                                        for (int h = 0; h < MyApplication.paperBean.getData().get(i).getQuestionOption().size(); h++) {
                                            MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setOptinon(s[h]);
                                            if( MyApplication.paperBean.getData().get(i).getUserAnswer()==null){

                                            }else {
                                                for (int j = 0; j < MyApplication.paperBean.getData().get(i).getUserAnswer().size(); j++) {


                                                    if (MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).getKey().equals(MyApplication.paperBean.getData().get(i).getUserAnswer().get(j).getKey())) {
                                                        MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setIschos(true);
                                                        MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setValue(MyApplication.paperBean.getData().get(i).getUserAnswer().get(j).getValue());
                                                    }
                                                }
                                            }

                                            for (int j = 0; j < MyApplication.paperBean.getData().get(i).getRightAnswer().size(); j++) {
                                                if (MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).getKey().equals(MyApplication.paperBean.getData().get(i).getRightAnswer().get(j).getKey())) {
                                                    MyApplication.paperBean.getData().get(i).getRightAnswer().get(j).setOptinon(s[h]);
                                                }
                                            }
                                        }

//

                                    }
                                    tv_totalNumber.setText("/"+beans.size());
                                    initReadViewPager();
                                    initList();
                                }

                            }
                        });


                    }catch (Exception e){
                        ToastUtils.show(e.getMessage());
                    }



                }
            });

        }else {
            //academyService/examinationUserPaperQuestions/getList
            //
            OkhttpUtils.getInstance(this).getList("academyService/examinationUserPaperQuestions/getList", jsonObject, new OkhttpUtils.onCallBack() {
                @Override
                public void onFaild(Exception e) {

                }

                @Override
                public void onResponse(final String json) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Gson gson = new Gson();
                                PaperBean baseBean = gson.fromJson(json, PaperBean.class);
                                if("1".equals(baseBean.getCode())){
                                    ToastUtils.show(baseBean.getMessage());
                                }else {
                                    MyApplication.paperBean = baseBean;
                                    String[] s = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I"};
                                    List<PaperBean.Data> beans = MyApplication.paperBean.getData();
                                    for (int i = 0; i < beans.size(); i++) {
                                        for (int h = 0; h < MyApplication.paperBean.getData().get(i).getQuestionOption().size(); h++) {
                                            MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setOptinon(s[h]);
                                            if( MyApplication.paperBean.getData().get(i).getUserAnswer()==null){

                                            }else {
                                                for (int j = 0; j < MyApplication.paperBean.getData().get(i).getUserAnswer().size(); j++) {
                                                    if (MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).getKey().equals(MyApplication.paperBean.getData().get(i).getUserAnswer().get(j).getKey())) {
                                                        MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setIschos(true);
                                                        MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setValue(MyApplication.paperBean.getData().get(i).getUserAnswer().get(j).getValue());

                                                    }
                                                }
                                            }

                                            for (int j = 0; j < MyApplication.paperBean.getData().get(i).getRightAnswer().size(); j++) {
                                                if (MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).getKey().equals(MyApplication.paperBean.getData().get(i).getRightAnswer().get(j).getKey())) {
                                                    MyApplication.paperBean.getData().get(i).getRightAnswer().get(j).setOptinon(s[h]);
                                                }
                                            }
                                        }

//

                                    }
                                    tv_totalNumber.setText("/"+beans.size());
                                    initReadViewPager();
                                    initList();
                                }


                            }catch (Exception e){
                                ToastUtils.show(e.getMessage());
                            }

                        }
                    });


                }
            });
        }

    }
    private int prePosition;
    private int curPosition;
    private void initList() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);

        topicAdapter = new TopicAdapter(this,MyApplication.paperBean.getData());
        topicAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(topicAdapter);


        topicAdapter.setOnTopicClickListener(new TopicAdapter.OnTopicClickListener() {
            @Override
            public void onClick(TopicAdapter.TopicViewHolder holder, int position) {
                curPosition = position;
                viewPager.setCurrentItem(position);

                topicAdapter.notifyCurPosition(curPosition);
                topicAdapter.notifyPrePosition(prePosition);

                prePosition = curPosition;
                dialog.dismiss();
            }
        });


    }
    private void initReadViewPager() {
        shadowView = (ImageView) findViewById(R.id.shadowView);
        adapter = new MsgContentFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        if("1".equals(totalNumber)){
            tv_next.setText("下一题");
            tv_next.setBackground(getResources().getDrawable(R.color.group_list_gray));
        }
        tv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                currentItem = currentItem-1;
                if (currentItem>MyApplication.paperBean.getData().size()-1){
                    currentItem=MyApplication.paperBean.getData().size()-1;
                }

                if(currentItem==-1){

                }else {
                    viewPager.setCurrentItem(currentItem,true);
                    tv_alreadyanswered.setText(currentItem+1+"");
                    tv_next.setText("下一题");
                }
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                currentItem = currentItem+1;
                if (currentItem<0){
                    currentItem=0;
                }
                //TODO 最后一道题
                if(currentItem==MyApplication.paperBean.getData().size()){


                }else {
                    if(currentItem+1==MyApplication.paperBean.getData().size()){
                        tv_next.setText("下一题");
                        viewPager.setCurrentItem(currentItem,true);
                        tv_alreadyanswered.setText(currentItem+1+"");

                    }else {
                        viewPager.setCurrentItem(currentItem,true);
                        tv_alreadyanswered.setText(currentItem+1+"");
                    }
                }


            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                shadowView.setTranslationX(viewPager.getWidth()-positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
                topicAdapter.notifyCurPosition(curPosition2);
                topicAdapter.notifyPrePosition(prePosition2);

                prePosition2 = curPosition2;

                if(viewPager.getCurrentItem()+1==MyApplication.paperBean.getData().size()){
                    tv_next.setText("下一题");
                    tv_next.setBackground(getResources().getDrawable(R.color.group_list_gray));
                }else {
                    tv_next.setText("下一题");
                    tv_next.setBackground(getResources().getDrawable(R.color.de_draft_color));
                }
                tv_alreadyanswered.setText(position+1+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_answercard:
                shownavigation( );

                break;
        }
    }
    private void shownavigation( ) {

        topicAdapter.notifyDataSetChanged();
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
//填充对话框的布局
        if (inflate != null) {
            ViewGroup parentViewGroup = (ViewGroup) inflate.getParent();
            if (parentViewGroup != null ) {
                parentViewGroup.removeView(inflate);
            }
        }
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }
    public class MsgContentFragmentAdapter extends FragmentPagerAdapter {

        public MsgContentFragmentAdapter(FragmentManager fm) {
            super(fm);
        }


        private static final String ARG_PARAM1 = "param1";
        @Override
        public AnalysisFragment getItem(int position) {
            AnalysisFragment fragment = new AnalysisFragment();
            Bundle args = new Bundle();
            args.putString("position", String.valueOf(position));
            args.putSerializable(ARG_PARAM1, MyApplication.paperBean.getData().get(position));
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public int getCount() {
            return MyApplication.paperBean.getData().size();
        }


    }
    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
               getResources().getDisplayMetrics());
    }
}
