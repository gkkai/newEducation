package com.meiliangzi.app.ui.view.Academy;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.view.Academy.adapter.TopicAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.ComintQuestionsBackbean;
import com.meiliangzi.app.ui.view.Academy.bean.OutAnswerBean;
import com.meiliangzi.app.ui.view.Academy.bean.PaperBean;
import com.meiliangzi.app.ui.view.Academy.dialog.SubmitDialog;
import com.meiliangzi.app.ui.view.Academy.fragment.ReadFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.WeekFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WeekExaminationActivity extends BaseActivity implements View.OnClickListener {
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
    private MyThread myThread;
    private MsgContentFragmentAdapter adapter;
    private String time;
    private String pagetitle;
    private String totalNumber;
    String userId;
    String finishStatus;
    String answerTime="0";
    String paperId;
    String title;
    String repeatAnswer;
    String createType;
    String countDown;
    private String mode;
    private String userPaperId;
    private SubmitDialog submitDialog;
    private MyDialog myDialog;
    public OutAnswerBean outAnswerBean;
    public boolean iscommit=false;
    public  int totle=0;
    private ArrayList<OutAnswerBean.AnswerBean.QuestionOption> listquestionOption;

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
        countDown=getIntent().getStringExtra("countDown");
        //TODO
        finishStatus=getIntent().getStringExtra("finishStatus");
        answerTime=getIntent().getStringExtra("answerTime");
        repeatAnswer=getIntent().getStringExtra("repeatAnswer");
        createType=getIntent().getStringExtra("createType");
        onCreateView(R.layout.activity_main3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_answercard:
                shownavigation( );

                break;
        }

    }


    @Override
    protected void findWidgets() {
        submitDialog = new SubmitDialog(this);
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
        submitDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


            }
        });
        submitDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.6f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        tv_title.setText(title);
        tv_pagetitle.setText(pagetitle);
        tv_totalNumber.setText("/"+totalNumber);
        tv_alreadyanswered.setText(1+"");
        if("1".equals(type)){
            //TODO 每周一答
            tv_no_duration.setVisibility(View.VISIBLE);
            tv_duration.setVisibility(View.GONE);
        }else if("0".equals(type)){
            //TODO 智能答题
            tv_no_duration.setVisibility(View.VISIBLE);
            tv_duration.setVisibility(View.GONE);

        }else {
            tv_duration.setVisibility(View.VISIBLE);
            tv_no_duration.setVisibility(View.GONE);
        }
        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.answercard, null);
        recyclerView=(RecyclerView)inflate.findViewById(R.id.list);
        tv_answercard.setOnClickListener(this);
        //TODO 答题卡按钮

    }
    private Dialog dialog;
    private View inflate;
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
        lp.height =  WindowManager.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

    @Override
    protected void initComponent() {
        //TODO 获取试题
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId",NewPreferManager.getId());
        jsonObject.put("paperId",paperId);
        if("3".equals(createType)){
            OkhttpUtils.getInstance(this).getList("academyService/examinationQuestionsImport/getListByApp", jsonObject, new OkhttpUtils.onCallBack() {
                @Override
                public void onFaild(Exception e) {

                }

                @Override
                public void onResponse(final String json) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Gson gson=new Gson();
                                PaperBean baseBean= gson.fromJson(json,PaperBean.class);
                                if("1".equals(baseBean.getCode())){
                                    ToastUtils.show(baseBean.getMessage());
                                }else {
                                    MyApplication.paperBean=baseBean;
                                    String [] s=new String[]{"A","B","C","D","E","F","G","H","I"};
                                    List<PaperBean.Data> beans=MyApplication.paperBean.getData();
                                    for(int i=0;i<beans.size();i++){
                                        for(int h=0;h<MyApplication.paperBean.getData().get(i).getQuestionOption().size();h++){
                                            MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setOptinon(s[h]);
//                                            if(beans.get(i).getUserAnswer()!=null&&beans.get(i).getUserAnswer().size()!=0){
//                                                for(int j=0;j<beans.get(i).getUserAnswer().size();j++){
//                                                    if(MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).getKey().equals(beans.get(i).getUserAnswer().get(j).getKey())){
//                                                        MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setIschos(true);
//                                                        break;
//                                                    }
//                                                }
//                                            }


                                        }
                                    }
                                    if("0".equals(countDown)){
                                        if(null==time){
                                            time="0";
                                        }
                                        tv_duration.setVisibility(View.VISIBLE);
                                        tv_no_duration.setVisibility(View.GONE);
                                        myThread = new MyThread(Integer.valueOf(time));
                                        myThread.start();
                                    }else {
                                        tv_duration.setVisibility(View.GONE);
                                        tv_no_duration.setVisibility(View.VISIBLE);
                                        tv_no_duration.setText("不计时");
                                    }
                                    totalNumber=String.valueOf(beans.size());
                                    tv_totalNumber.setText("/"+totalNumber);
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

        }else {
            OkhttpUtils.getInstance(this).getList("academyService/examinationUserPaperQuestions/getList", jsonObject, new OkhttpUtils.onCallBack() {
                @Override
                public void onFaild(Exception e) {

                }

                @Override
                public void onResponse(final String json) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Gson gson=new Gson();
                                PaperBean baseBean= gson.fromJson(json,PaperBean.class);
                                if("1".equals(baseBean.getCode())){
                                    ToastUtils.show(baseBean.getMessage());
                                }else {

                                    MyApplication.paperBean=baseBean;
                                    String [] s=new String[]{"A","B","C","D","E","F","G","H","I"};
                                    List<PaperBean.Data> beans=MyApplication.paperBean.getData();
                                    for(int i=0;i<beans.size();i++){
                                        for(int h=0;h<MyApplication.paperBean.getData().get(i).getQuestionOption().size();h++){
                                            MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setOptinon(s[h]);
//                                            if(beans.get(i).getUserAnswer()!=null&&beans.get(i).getUserAnswer().size()!=0){
//                                                for(int j=0;j<beans.get(i).getUserAnswer().size();j++){
//                                                    if(MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).getKey().equals(beans.get(i).getUserAnswer().get(j).getKey())){
//                                                        //MyApplication.paperBean.getData().get(i).getQuestionOption().get(h).setIschos(true);
//                                                        break;
//                                                    }
//                                                }
//                                            }


                                        }
                                    }
                                    if("0".equals(countDown)){
                                        if(null==time){
                                            time="0";
                                        }
                                        tv_duration.setVisibility(View.VISIBLE);
                                        tv_no_duration.setVisibility(View.GONE);
                                        myThread = new MyThread(Integer.valueOf(time));
                                        myThread.start();
                                    }else {
                                        tv_duration.setVisibility(View.GONE);
                                        tv_no_duration.setVisibility(View.VISIBLE);
                                        tv_no_duration.setText("不计时");
                                    }
                                    totalNumber=String.valueOf(beans.size());
                                    tv_totalNumber.setText("/"+totalNumber);
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
    /*
            * 将秒数转为时分秒
            * */
    public String change(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return h + ":" + d + ":" + s + "";
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    int during = (int) msg.obj;
//                    Log.i("info", "onPostExecute: =" + during);
//                    int min = (int) (during / 60);
//                    int second = (int) (during % 60);
//                    String minStr = (min < 10) ? ("0" + min) : String.valueOf(min);
//                    String secondStr = (second < 10) ? ("0" + second) : String.valueOf(second);
//                    tv_duration.setText("倒计时：" + minStr + ":" + secondStr);
                    tv_duration.setText("倒计时：" + change(during));
//                    if (minStr.equals("00") && secondStr.equals("00")) {
//
//
//
//                    }
                    break;
            }
        }
    };
    public class MyThread extends Thread {
        int timeStamp = 0;


        public MyThread(int timestamp) {
            this.timeStamp = timestamp;
        }

        @Override
        public void run() {
            while (timeStamp != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isBackGround){
                    timeStamp--;
                    answerTime=String.valueOf(timeStamp);
//                publishProgress(params[0]);
                    handler.sendMessage(handler.obtainMessage(0, timeStamp));
                }else {

                }


            }
        }
    }
    private boolean isBackGround;
    @Override
    protected void onPause() {
        super.onPause();
        isBackGround = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isBackGround =false;
    }
    private void commit(final int ctype){
        if(!iscommit){
            iscommit=true;
            outAnswerBean= new OutAnswerBean();
            outAnswerBean.setPaperId(paperId);
            if(1==ctype){
                outAnswerBean.setFinishStatus("1");
            }else {
                outAnswerBean.setFinishStatus("0");
            }

            outAnswerBean.setRepeatAnswer(repeatAnswer);
            if(null==answerTime){
                answerTime="0";
            }
            outAnswerBean.setAnswerTime(answerTime);
            List<OutAnswerBean.AnswerBean> lstAnswerBean=new ArrayList<OutAnswerBean.AnswerBean>();
            //TODO 提交数据
            for(int i=0;i<MyApplication.paperBean.getData().size();i++){
                OutAnswerBean.AnswerBean bean=new OutAnswerBean.AnswerBean();
                bean.setQuestionOptionid(MyApplication.paperBean.getData().get(i).getId());
                listquestionOption=new ArrayList<OutAnswerBean.AnswerBean.QuestionOption>();
//
                for(int j=0;j<MyApplication.paperBean.getData().get(i).getQuestionOption().size();j++){
                    OutAnswerBean.AnswerBean.QuestionOption questionOption=new OutAnswerBean.AnswerBean.QuestionOption();
                    if(MyApplication.paperBean.getData().get(i).getQuestionOption().get(j).ischos){
                        questionOption.setKey(MyApplication.paperBean.getData().get(i).getQuestionOption().get(j).getKey());
                        questionOption.setValue(MyApplication.paperBean.getData().get(i).getQuestionOption().get(j).getValue());
                        listquestionOption.add(questionOption);

                    }
                }
                bean.setType((MyApplication.paperBean.getData().get(i).getType()));
                bean.setFraction((MyApplication.paperBean.getData().get(i).getFraction()));
                bean.setQuestionOption(listquestionOption);
                bean.setRightAnswer(MyApplication.paperBean.getData().get(i).getRightAnswer());
                lstAnswerBean.add(bean);

            }
            outAnswerBean.setAnswerBean(lstAnswerBean);
            //TODO 发送数据
            totle=0;
            final String rsule= JSON.toJSONString(outAnswerBean);
            for(int i=0;i<outAnswerBean.getAnswerBean().size();i++){
                if(outAnswerBean.getAnswerBean().get(i).getQuestionOption().size()!=0){
                    totle=totle+1;
                }
            }
            if(ctype==0){
              int result=  Integer.valueOf(totalNumber)-totle;
                if(result!=0){

                    submitDialog.setMessage("您还有"+result+"道题未作答，请完成后在提交");

                    //if(){}
                    submitDialog.setYesOnclickListener("确认", new SubmitDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            iscommit=false;
                            submitDialog.dismiss();



                        }
                    });
                    submitDialog.setNoOnclickListener("取消", new SubmitDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            iscommit=false;
                            submitDialog.dismiss();

                        }
                    });
                    submitDialog.show();
                    submitDialog.messageTv1.setVisibility(View.VISIBLE);
                    submitDialog.titleTv.setVisibility(View.GONE);
                    submitDialog.messageTv.setVisibility(View.GONE);

                }else {
                    myDialog.setYesOnclickListener("确认", new MyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            iscommit=false;
                            myDialog.dismiss();
                            OkhttpUtils.postJson(NewPreferManager.getId(), rsule, "academyService/examinationUserPaperQuestions/add", new OkhttpUtils.onCallBack() {
                                @Override
                                public void onFaild(Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            iscommit=false;
                                            ToastUtils.show("提交失败，请重新提交");
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(String json) {
                                    iscommit=true;
                                    final ComintQuestionsBackbean questionsBackbean=new Gson().fromJson(json,ComintQuestionsBackbean.class);
                                    if("1".equals(questionsBackbean.getCode())){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                iscommit=false;
                                                ToastUtils.show(questionsBackbean.getMessage());
                                            }
                                        });
                                    }else {
                                        if(1==ctype){
                                            finish();
                                        }else {
                                            Intent intent=new Intent(WeekExaminationActivity.this,AnswerReportActivity.class);
                                            repeatAnswer=getIntent().getStringExtra("repeatAnswer");
                                            intent.putExtra("paperId",paperId);
                                            intent.putExtra("score",questionsBackbean.getData().getScore()+"");
                                            intent.putExtra("pass",questionsBackbean.getData().getPass()+"");
                                            intent.putExtra("type",type);
                                            intent.putExtra("userId", NewPreferManager.getId());
                                            intent.putExtra("pagetitle",pagetitle);
                                            intent.putExtra("time",time);
                                            intent.putExtra("createType",createType);
                                            intent.putExtra("finishStatus",questionsBackbean.getData().getExaminationUserPaperMap().getFinishStatus());
                                            intent.putExtra("answerTime",questionsBackbean.getData().getExaminationUserPaperMap().getAnswerTime());
                                            intent.putExtra("repeatAnswer",questionsBackbean.getData().getExaminationUserPaperMap().getRepeatAnswer());
                                            intent.putExtra("totalNumber",questionsBackbean.getData().getExaminationUserPaperMap().getTotalNumber()+"");
                                            intent.putExtra("title",title);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }


                                }
                            });


                        }
                    });
                    myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            iscommit=false;
                            myDialog.dismiss();

                        }
                    });

                    myDialog.show();
                }

            }else {
                OkhttpUtils.postJson(NewPreferManager.getId(), rsule, "academyService/examinationUserPaperQuestions/add", new OkhttpUtils.onCallBack() {
                    @Override
                    public void onFaild(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iscommit=false;
                                ToastUtils.show("提交失败，请重新提交");
                            }
                        });
                    }

                    @Override
                    public void onResponse(String json) {
                        iscommit=true;
                        final ComintQuestionsBackbean questionsBackbean=new Gson().fromJson(json,ComintQuestionsBackbean.class);
                        if("1".equals(questionsBackbean.getCode())){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    iscommit=false;
                                    ToastUtils.show(questionsBackbean.getMessage());
                                }
                            });
                        }else {
                            if(1==ctype){
                                finish();
                            }else {
                                Intent intent=new Intent(WeekExaminationActivity.this,AnswerReportActivity.class);
                                repeatAnswer=getIntent().getStringExtra("repeatAnswer");
                                intent.putExtra("paperId",paperId);
                                intent.putExtra("score",questionsBackbean.getData().getScore()+"");
                                intent.putExtra("pass",questionsBackbean.getData().getPass()+"");
                                intent.putExtra("type",type);
                                intent.putExtra("userId", NewPreferManager.getId());
                                intent.putExtra("pagetitle",pagetitle);
                                intent.putExtra("time",time);
                                intent.putExtra("createType",createType);
                                intent.putExtra("finishStatus",questionsBackbean.getData().getExaminationUserPaperMap().getFinishStatus());
                                intent.putExtra("answerTime",questionsBackbean.getData().getExaminationUserPaperMap().getAnswerTime());
                                intent.putExtra("repeatAnswer",questionsBackbean.getData().getExaminationUserPaperMap().getRepeatAnswer());
                                intent.putExtra("totalNumber",questionsBackbean.getData().getExaminationUserPaperMap().getTotalNumber()+"");
                                intent.putExtra("title",title);
                                startActivity(intent);
                                finish();
                            }

                        }


                    }
                });

            }


               }else {
           // ToastUtils.show("正在提交请稍后");
        }



    }
    private void initReadViewPager() {
        shadowView = (ImageView) findViewById(R.id.shadowView);
        adapter = new MsgContentFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        if("1".equals(totalNumber)){
            tv_next.setText("提交");
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

                //TODO 开始提交数据
                if(currentItem==MyApplication.paperBean.getData().size()){
                    commit(0);

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

    @Override
    public void onBackClick(View v) {
        onKeyDown(4, new KeyEvent(4,4));

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
    public class MsgContentFragmentAdapter extends FragmentPagerAdapter {

        public MsgContentFragmentAdapter(FragmentManager fm) {
            super(fm);
        }


        private static final String ARG_PARAM1 = "param1";
        @Override
        public ReadFragment getItem(int position) {
            ReadFragment fragment = new ReadFragment();
            Bundle args = new Bundle();
            args.putString("position", String.valueOf(position));
            args.putString("type",""+type);
            args.putString("mode",""+mode);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

//            if("1".equals(type)){
//                //TODO 每周一答
//                submitDialog.setMessage("退出后数据不会被保存");
//
//            }else if("0".equals(type)){
//                //TODO 每周一答
//                submitDialog.setMessage("退出后数据不会被保存");
//
//            }else {
//                //TODO
//                submitDialog.setMessage("退出后数据会被保存");
//
//            }
            iscommit=false;
            submitDialog.show();
            submitDialog.setMessage("退出后数据不会被保存");
            submitDialog.messageTv1.setVisibility(View.GONE);
            submitDialog.titleTv.setVisibility(View.VISIBLE);
            submitDialog.messageTv.setVisibility(View.VISIBLE);

            //if(){}
            submitDialog.setYesOnclickListener("确认", new SubmitDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    if("1".equals(type)){
                        //TODO 每周一答
                        finish();

                    }else if("0".equals(type)){
                        //TODO 智能答题
                        finish();

                    }else {
                        //TODO 提交数据
                        commit(1);

                    }
                    submitDialog.dismiss();

                }
            });
            submitDialog.setNoOnclickListener("取消", new SubmitDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    submitDialog.dismiss();

                }
            });
            submitDialog.show();
           // commit(1);
            return true;
        }
        return false;
    }
}
