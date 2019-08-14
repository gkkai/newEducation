package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.LoadingDialog;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.view.Academy.adapter.TopicAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.AnswerWrongBean;
import com.meiliangzi.app.ui.view.Academy.bean.ComintQuestionsBackbean;
import com.meiliangzi.app.ui.view.Academy.bean.ErrorAnswerBean;
import com.meiliangzi.app.ui.view.Academy.bean.OutAnswerBean;
import com.meiliangzi.app.ui.view.Academy.bean.PaperBean;
import com.meiliangzi.app.ui.view.Academy.fragment.ErrorFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.ReadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ErrorBankActivity extends BaseActivity implements View.OnClickListener {
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

   MyDialog myDialog;
    @BindView(R.id.tv_alreadyanswered)
    TextView tv_alreadyanswered;
    @BindView(R.id.tv_totalNumber)
    TextView tv_totalNumber;
    private MsgContentFragmentAdapter adapter;
   
    private int totalNumber=0;
    private ErrorAnswerBean errorAnswerBean;
    private LoadingDialog cmmitdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_error_bank);
    }

    @Override
    protected void findWidgets() {
        LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(this)
                .setMessage("正在提交...")
                .setCancelable(false)
                .setCancelOutside(false);
        cmmitdialog=loadBuilder.create();
        myDialog=new MyDialog(this);
        myDialog=new MyDialog(this);
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


            }
        });
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.6f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });

        tv_alreadyanswered.setText(1+"");

        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.answercard, null);
        recyclerView=(RecyclerView)inflate.findViewById(R.id.list);
        tv_answercard.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_answercard:
                shownavigation( );

                break;
        }

    }
    private Dialog dialog;
    private View inflate;
    private void shownavigation( ) {
        if(topicAdapter!=null){
            topicAdapter.notifyDataSetChanged();
        }


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
        lp.height =  WindowManager.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

    @Override
    protected void initComponent() {
        //TODO 获取试题
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId", NewPreferManager.getId());
        OkhttpUtils.getInstance(this).getErrorList("academyService/examinationUserQuestionsWrong/getList", jsonObject, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(final String json) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Gson gson=new Gson();
                            PaperBean baseBean= gson.fromJson(json,PaperBean.class);
                            if(baseBean!=null&&baseBean.getData()!=null&&baseBean.getData().size()==0){
                                ToastUtils.show("暂无试题");

                            }else {
                                MyApplication.paperBean=baseBean;
                                totalNumber=baseBean.getData().size();
                                String [] s=new String[]{"A","B","C","D","E","F","G","H","I"};
                                List<PaperBean.Data> beans=MyApplication.paperBean.getData();
                                for(int i=0;i<beans.size();i++){
                                    for(int h=0;h<MyApplication.paperBean.getData().get(i).getQuestionOption().size();h++){
                                        MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setOptinon(s[h]);

                                    }
                                }
                                tv_totalNumber.setText("/"+totalNumber);
                                if("0".equals(totalNumber)){
                                    tv_alreadyanswered.setText(0+"");
                                }else {
                                    tv_alreadyanswered.setText(1+"");
                                }
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
    private void initReadViewPager() {
        shadowView = (ImageView) findViewById(R.id.shadowView);
        adapter = new MsgContentFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        if(1==totalNumber){
            tv_next.setText("提交");
        }
        if(0==totalNumber){

        }else {
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
                        tv_next.setText("下一题");
                        viewPager.setCurrentItem(currentItem,true);
                        tv_alreadyanswered.setText(currentItem+1+"");
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
                    //TODO 开始提交数据
                    if(currentItem==MyApplication.paperBean.getData().size()){
                        myDialog.setYesOnclickListener("确认", new MyDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                myDialog.dismiss();
                                errorAnswerBean=new ErrorAnswerBean();

                                ArrayList<ErrorAnswerBean.AnswerBean> AnswerBeans = new ArrayList<>();

                                if(AnswerBeans.size()>=MyApplication.paperBean.getData().size()){


                                }else {

                                    //TODO 提交数据
                                    for(int i=0;i<MyApplication.paperBean.getData().size();i++){
                                        ErrorAnswerBean.AnswerBean answerBean = new ErrorAnswerBean.AnswerBean();
                                        answerBean.setId(MyApplication.paperBean.getData().get(i).getId());
                                        List<PaperBean.Data.UserAnswer> userbeans=new ArrayList<PaperBean.Data.UserAnswer>();
                                        for(int j=0;j<MyApplication.paperBean.getData().get(i).getQuestionOption().size();j++){
                                            PaperBean.Data.UserAnswer questionOption=new PaperBean.Data.UserAnswer();

                                            if(MyApplication.paperBean.getData().get(i).getQuestionOption().get(j).ischos){
                                                questionOption.setKey(MyApplication.paperBean.getData().get(i).getQuestionOption().get(j).getKey());
                                                questionOption.setValue(MyApplication.paperBean.getData().get(i).getQuestionOption().get(j).getValue());
                                                userbeans.add(questionOption);

                                            }
                                        }
                                        answerBean.setRightAnswer(MyApplication.paperBean.getData().get(i).getRightAnswer());
                                        answerBean.setUserAnswe(userbeans);
                                        AnswerBeans.add(answerBean);


                                    }
                                }



                                errorAnswerBean.setAnswerBean(AnswerBeans);
                                Gson gson=new Gson();
                                String rsule=gson.toJson(errorAnswerBean);

                                cmmitdialog.show();
                                //TODO 发送数据
                                //String rsules= JSON.toJSONString(errorAnswerBean);
                                OkhttpUtils.postJson(NewPreferManager.getId(), rsule, "academyService/examinationUserQuestionsWrong/modify", new OkhttpUtils.onCallBack() {
                                    @Override
                                    public void onFaild(Exception e) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                cmmitdialog.dismiss();
                                                ToastUtils.show("提交失败，请重新提交");
                                            }
                                        });


                                    }

                                    @Override
                                    public void onResponse(final String json) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                cmmitdialog.dismiss();
                                                AnswerWrongBean bean=new Gson().fromJson(json,AnswerWrongBean.class);
                                                if("1".equals(bean.getCode())){
                                                    ToastUtils.show(bean.getMessage());
                                                }else {
                                                    Intent intent=new Intent(ErrorBankActivity.this,ErrorReportActivity.class);
                                                    intent.putExtra("currentAnswerWrongNumber",bean.getData().getCurrentAnswerWrongNumber());
                                                    intent.putExtra("currentAnswerRightNumber",bean.getData().getCurrentAnswerRightNumber());
                                                    intent.putExtra("lastWrongNumber",bean.getData().getLastWrongNumber());
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }
                                        });




                                    }
                                });
                            }
                        });
                        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                myDialog.dismiss();
                            }
                        });
                        myDialog.show();


                    }else {
                        if(currentItem+1==MyApplication.paperBean.getData().size()){
                            tv_next.setText("提交");
                            viewPager.setCurrentItem(currentItem,true);
                            tv_alreadyanswered.setText(currentItem+1+"");

                        }else {
                            viewPager.setCurrentItem(currentItem,true);
                            tv_alreadyanswered.setText(currentItem+1+"");
                        }
                    }


                }
            });
        }



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
                    tv_next.setText("提交");
                }else {
                    tv_next.setText("下一题");
                }
                tv_alreadyanswered.setText(position+1+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
        private int prePosition;
        private int curPosition;
    private void initList() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);

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
        public class MsgContentFragmentAdapter extends FragmentPagerAdapter {

            public MsgContentFragmentAdapter(FragmentManager fm) {
                super(fm);
            }
            private static final String ARG_PARAM1 = "param1";
            @Override
            public ErrorFragment getItem(int position) {
                ErrorFragment fragment = new ErrorFragment();
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

}
