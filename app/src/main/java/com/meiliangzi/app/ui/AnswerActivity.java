package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.model.bean.QuestionList;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.HttpCallback;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.MiddleView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.meiliangzi.app.R;

import static com.meiliangzi.app.config.Constant.BASE_URL;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/20
 * @description 答题页面
 **/

public class AnswerActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    QuestionList.DataBean Questionitem;
    private BaseQuickAdapter<QuestionList.DataBean> adapter;
    //private HttpCallback callback;
    private String id;
    private float score;
    private int time=1;
    private String timetype="";
    private long orginalTime;
    private long duringTime;
    private MyThread myThread;
    private Map<Integer, QuestionList.DataBean> wholeQuestionMap;
    private List<QuestionList.DataBean> currentQuestion;
    private int questionIndex;
    private int tempA1;
    private int tempA2;
    private int tempA3;
    private int tempA4;
    private boolean isSubmit;
    private StringBuilder sb;
    private  boolean checkAnswer;
    private boolean isBackGround;
    private boolean isExit;
    private BigDecimal ss;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    private List<String> answerList = new ArrayList<>();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    long during = (Long) msg.obj;
                    Log.i("info", "onPostExecute: =" + during);
//                    int day = (int) (during  / 3600/ 24);
//                    int hour = (int) (during /3600);
//                    int min = (int) (during % 3600 / 60);
//                    int second = (int) (during  % 3600 % 60);
//                    String hourStr = (hour < 10) ? ("0" + hour) : String.valueOf(hour);
                    duringTime = during;
                    int min = (int) (during / 60);
                    int second = (int) (during % 60);
                    String minStr = (min < 10) ? ("0" + min) : String.valueOf(min);
                    String secondStr = (second < 10) ? ("0" + second) : String.valueOf(second);
                    tvTitle.setText("倒计时：" + minStr + ":" + secondStr);
                    if (minStr.equals("00") && secondStr.equals("00")) {
                        answerList.clear();
                        sb = new StringBuilder();
                        if(tempA1 == 1){
                            //answerList.add("A");
                            answerList.add(Questionitem.getRealAnswer("A"));
                        }
                        if(tempA2 == 1){
                            // answerList.add("B");
                            answerList.add(Questionitem.getRealAnswer("B"));
                        }
                        if(tempA3 == 1){
                            //answerList.add("C");
                            answerList.add(Questionitem.getRealAnswer("C"));
                        }
                        if(tempA4 == 1){
                            // answerList.add("D");
                            answerList.add(Questionitem.getRealAnswer("D"));
                        }
                        Collections.sort(answerList);
                        for (int i=0;i<answerList.size();i++){
                            sb.append(answerList.get(i)).append(",");
                        }
                        if(sb.toString().contains(",")){
                            sb.deleteCharAt(sb.length()-1);
                        }
                        if(Questionitem==null){
                            timetype="timeOver";
                            submitData(String.valueOf(MyApplication.score));
                        }else {
                            int rsult=Questionitem.getCurrentItemScore(sb.toString());
                            MyApplication.score= MyApplication.score+rsult;
                            timetype="timeOver";
                            submitData(String.valueOf(MyApplication.score));
                        }


                    }
                    break;
            }
        }
    };
    private long costTime;
    private long answerTime;
    private String isverrrde="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.score=0;
        onCreateView(R.layout.activity_answer);
    }

    @Override
    protected void findWidgets() {


    }

    private void savescore(String userid, String id, String score, String costTime) {
        String url = BASE_URL+"community/savescore";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("userId", userid)
                .add("subjectId", id)
                .add("score", score)
                .add("answerTime", costTime)
    .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isverrrde="verride";
                        isSubmit=false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.custom("提交失败，请重新提交");
                            }
                        });


                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                Gson gson=new Gson();
               // BaseBean baseBean= gson.fromJson(response.body().string(),BaseBean.class);
                getResult();
            }
        });

    }

    @Override
    protected void initComponent() {
        checkAnswer = getIntent().getBooleanExtra("checkAnswer",false);
        if(checkAnswer){
            tvSubmit.setVisibility(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
        }else {
            tvSubmit.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.GONE);
        }
        currentQuestion = new ArrayList<>();
        wholeQuestionMap = new HashMap<>();
        id = getIntent().getStringExtra("id");
        time = getIntent().getIntExtra("answer_tiem", 0);
        if(time == 0){
            tvTitle.setText("查看答案");
        }else {
            orginalTime = time * 60;
            myThread = new MyThread(time * 60);
            myThread.start();
        }
        adapter = new BaseQuickAdapter<QuestionList.DataBean>(AnswerActivity.this, R.layout.item_answer) {
            @Override
            public void convert(final BaseViewHolder helper, final QuestionList.DataBean item) {
                Questionitem=item;
                final CheckBox cbA = helper.getView(R.id.ck1);
                final CheckBox cbB = helper.getView(R.id.ck2);
                final CheckBox cbC = helper.getView(R.id.ck3);
                final CheckBox cbD = helper.getView(R.id.ck4);

                helper.setText(R.id.tvPos, String.valueOf(questionIndex + 1));
                if(time == 0){
                    //以前的答案规则排布
               helper.setText(R.id.tvAnswerA, item.getAnswerA());
                helper.setText(R.id.tvAnswerB, item.getAnswerB());
                helper.setText(R.id.tvAnswerC, item.getAnswerC());
                helper.setText(R.id.tvAnswerD, item.getAnswerD());
                }else {
                   /* helper.setText(R.id.tvAnswerA, item.getAnswerA());
                    helper.setText(R.id.tvAnswerB, item.getAnswerB());
                    helper.setText(R.id.tvAnswerC, item.getAnswerC());
                    helper.setText(R.id.tvAnswerD, item.getAnswerD());*/
                    //随机打乱排布
                    helper.setText(R.id.tvAnswerA,item.getRandomAnswerA());
                    helper.setText(R.id.tvAnswerB,item.getRandomAnswerB());
                    helper.setText(R.id.tvAnswerC, item.getRandomAnswerC());
                    helper.setText(R.id.tvAnswerD, item.getRandomAnswerD());
                }


                helper.setText(R.id.tvTitle, item.getTitle());
                final String answer =  item.getCorrectnessAnswer();
                boolean QuestType=item.getStudy_status();
                System.out.println("答案类型=========================="+QuestType);
                if(QuestType){
                    helper.setText(R.id.tvPos, "多选题");
                }else {
                    helper.setText(R.id.tvPos, "单选题");
                    if (item.getSelect() != null) {
                        switch (item.getSelect()) {
                            case "A":
                                tempA1 = 1;
                                tempA2 = 0;
                                tempA3 = 0;
                                tempA4 = 0;
                                cbA.setChecked(true);
                                cbB.setChecked(false);
                                cbC.setChecked(false);
                                cbD.setChecked(false);
                                break;
                            case "B":
                                tempA1 = 0;
                                tempA2 = 1;
                                tempA3 = 0;
                                tempA4 = 0;
                                cbA.setChecked(false);
                                cbB.setChecked(true);
                                cbC.setChecked(false);
                                cbD.setChecked(false);
                                break;
                            case "C":
                                tempA1 = 0;
                                tempA2 = 0;
                                tempA3 = 1;
                                tempA4 = 0;
                                cbA.setChecked(false);
                                cbB.setChecked(false);
                                cbC.setChecked(true);
                                cbD.setChecked(false);
                                break;
                            case "D":
                                tempA1 = 0;
                                tempA2 = 0;
                                tempA3 = 0;
                                tempA4 = 1;
                                cbA.setChecked(false);
                                cbB.setChecked(false);
                                cbC.setChecked(false);
                                cbD.setChecked(true);
                                break;
                        }
                    } else {
                        cbA.setChecked(false);
                        cbB.setChecked(false);
                        cbC.setChecked(false);
                        cbD.setChecked(false);
                    }
                }
                if(checkAnswer){

                    if(answer.contains("A")){
                        cbA.setChecked(true);
                    }else {
                        cbA.setChecked(false);
                    }
                    if(answer.contains("B")){
                        cbB.setChecked(true);
                    }else {
                        cbB.setChecked(false);
                    }
                    if(answer.contains("C")){
                        cbC.setChecked(true);
                    }else {
                        cbC.setChecked(false);
                    }
                    if(answer.contains("D")){
                        cbD.setChecked(true);
                    }else {
                        cbD.setChecked(false);
                    }

                }else {
                    //判定是不是多选
                    if(item.getStudy_status()){
                        cbA.setChecked(false);
                        cbB.setChecked(false);
                        cbC.setChecked(false);
                        cbD.setChecked(false);
                        helper.setOnClickListener(R.id.llAnswerA, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (cbA.isChecked()) {
                                    tempA1 = 0;
                                    cbA.setChecked(false);
                                }else {
                                    cbA.setChecked(true);
                                    tempA1=1;
                                }
                            }
                        });
                        helper.setOnClickListener(R.id.llAnswerB, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (cbB.isChecked()) {
                                    tempA2 = 0;
                                    cbB.setChecked(false);
                                }else {
                                    tempA2 = 1;
                                    cbB.setChecked(true);
                                }
                            }
                        });
                        helper.setOnClickListener(R.id.llAnswerC, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (cbC.isChecked()) {
                                    cbC.setChecked(false);
                                    tempA3 = 0;
                                }else {
                                    tempA3 = 1;
                                    cbC.setChecked(true);
                                }
                            }
                        });
                        helper.setOnClickListener(R.id.llAnswerD, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (cbD.isChecked()) {
                                    tempA4 = 0;
                                    cbD.setChecked(false);
                                }else {
                                    tempA4 = 1;
                                    cbD.setChecked(true);
                                }
                            }
                        });
                    }else {

                        helper.setOnClickListener(R.id.llAnswerA, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adapter.getItem(helper.getPosition()).setSelect("A");
                                adapter.notifyDataSetChanged();
                            }
                        });
                        helper.setOnClickListener(R.id.llAnswerB, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adapter.getItem(helper.getPosition()).setSelect("B");
                                adapter.notifyDataSetChanged();
                            }
                        });
                        helper.setOnClickListener(R.id.llAnswerC, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adapter.getItem(helper.getPosition()).setSelect("C");
                                adapter.notifyDataSetChanged();
                            }
                        });
                        helper.setOnClickListener(R.id.llAnswerD, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                adapter.getItem(helper.getPosition()).setSelect("D");
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        };
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSubmit:
                if(tvSubmit.getText().toString().equals("返回")){
                    this.finish();
                    return;
                }
                if(isSubmit){
                    ToastUtils.custom("正在提交，请稍后");
                    //AnswerActivity.this.finish();
                    return;
                }else {
                    answerList.clear();
                    sb = new StringBuilder();
                    if(tempA1 == 1){
                        //answerList.add("A");
                        answerList.add(Questionitem.getRealAnswer("A"));
                    }
                    if(tempA2 == 1){
                        // answerList.add("B");
                        answerList.add(Questionitem.getRealAnswer("B"));
                    }
                    if(tempA3 == 1){
                        //answerList.add("C");
                        answerList.add(Questionitem.getRealAnswer("C"));
                    }
                    if(tempA4 == 1){
                        // answerList.add("D");
                        answerList.add(Questionitem.getRealAnswer("D"));
                    }
                    Collections.sort(answerList);
                    for (int i=0;i<answerList.size();i++){
                        sb.append(answerList.get(i)).append(",");
                    }
                    if(sb.toString().contains(",")){
                        sb.deleteCharAt(sb.length()-1);
                    }
                    if(TextUtils.isEmpty(sb.toString()) && !checkAnswer){
                        ToastUtils.custom("请选择答案");
                        return;
                    }

                    if (tvSubmit.getText().toString().equals("提交")) {
                        isSubmit=true;
                        if("verride".equals(isverrrde)){
                            submitData(String.valueOf(MyApplication.score));
                        }else {
                            if(Questionitem==null){
                                timetype="";
                                submitData(String.valueOf(MyApplication.score));
                            }else {
                                int rsult=Questionitem.getCurrentItemScore(sb.toString());
                                MyApplication.score= MyApplication.score+rsult;
                                timetype="";
                                submitData(String.valueOf(MyApplication.score));
                            }
                        }



                        return;
                    } else {
                        wholeQuestionMap.get(questionIndex).setSelect(sb.toString());
                        questionIndex++;
                        int rsult=Questionitem.getCurrentItemScore(sb.toString());
                        MyApplication.score= MyApplication.score+ rsult;
                        tempA1 = 0;
                        tempA2 = 0;
                        tempA3 = 0;
                        tempA4 = 0;
                        currentQuestion.clear();
                        currentQuestion.add(wholeQuestionMap.get(questionIndex));
                        adapter.setDatas(currentQuestion);

                    }
                    if (wholeQuestionMap.size() == questionIndex+1) {
                        if(checkAnswer){
                            tvSubmit.setText("返回");
                        }else {
                            tvSubmit.setText("提交");
                        }

                    } else {
                        tvSubmit.setText("下一题");
                    }
                }

                break;
        }
    }
    public void showDialogTime() {
        final MiddleView middleView = new MiddleView(AnswerActivity.this, R.layout.panel_answer);
        TextView Showtitl= (TextView) middleView.getView().findViewById(R.id.Showtitle);
        TextView tvScore = (TextView) middleView.getView().findViewById(R.id.tvScore);
        TextView tvCostTime = (TextView) middleView.getView().findViewById(R.id.tvCostTime);
        //long costTime = orginalTime - duringTime;
        int min = (int) (costTime / 60);
        int second = (int) (costTime % 60);
        String minStr = (min < 10) ? ("0" + min) : String.valueOf(min);
        String secondStr = (second < 10) ? ("0" + second) : String.valueOf(second);
        tvCostTime.setText(minStr + ":" + secondStr);
        Showtitl.setText("时间到系统自动提交");
        tvScore.setText(String.valueOf(MyApplication.score));
        middleView.getView().findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
                AnswerActivity.this.finish();
            }
        });
        middleView.getView().findViewById(R.id.ivGoOn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
                AnswerActivity.this.finish();
            }
        });
        middleView.showModdleView(false);
    }

    public void showDialog() {
        final MiddleView middleView = new MiddleView(AnswerActivity.this, R.layout.panel_answer);
        TextView tvScore = (TextView) middleView.getView().findViewById(R.id.tvScore);
        TextView tvCostTime = (TextView) middleView.getView().findViewById(R.id.tvCostTime);
        //costTime = orginalTime - duringTime;
        int min = (int) (costTime / 60);
        int second = (int) (costTime % 60);
        String minStr = (min < 10) ? ("0" + min) : String.valueOf(min);
        String secondStr = (second < 10) ? ("0" + second) : String.valueOf(second);
        tvCostTime.setText(minStr + ":" + secondStr);
        tvScore.setText(String.valueOf(MyApplication.score));
        middleView.getView().findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
                AnswerActivity.this.finish();
            }
        });
        middleView.getView().findViewById(R.id.ivGoOn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
                AnswerActivity.this.finish();
            }
        });
        middleView.showModdleView(false);
    }

    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
        getData(id);
    }

    public void getData(String id) {
        ProxyUtils.getHttpProxy().gettestpaper(AnswerActivity.this, id);
    }

    protected void getData(QuestionList questionList) {
        if(checkAnswer){
            adapter.setDatas(questionList.getData());
        }else {
            if (questionList.getData().size() > 0) {
                for (int i = 0; i < questionList.getData().size(); i++) {
                    wholeQuestionMap.put(i, questionList.getData().get(i));
                }
                if(questionList.getData().size() == 1){
                    tvSubmit.setText("提交");
                }else {
                    tvSubmit.setText("下一题");
                }
                currentQuestion.clear();
                currentQuestion.add(wholeQuestionMap.get(0));
                adapter.setDatas(currentQuestion);
            }
        }

    }


    public void submitData(String score) {
        costTime = orginalTime - duringTime;
        answerTime=costTime;
        if(isSubmit){

        }
        //优化网络请求
       // ProxyUtils.getHttpProxy().savescore(AnswerActivity.this, Integer.valueOf(PreferManager.getUserId()), Integer.valueOf(id), Integer.valueOf(score),costTime);
        savescore(PreferManager.getUserId(),id, score,String.valueOf(costTime));

    }

    protected void getResult() {
        //ToastUtils.custom("提交成功");
       // isSubmit=false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if("timeOver".equals(timetype)){
                    showDialogTime();
                }else {
                    showDialog();
                }


            }
        });

    }


    public class MyThread extends Thread {
        long timeStamp = 0;


        public MyThread(long timestamp) {
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
//                publishProgress(params[0]);
                    handler.sendMessage(handler.obtainMessage(0, timeStamp));
                }else {

                }


            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

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

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackClick() {
        isExit = true;
        if(MyApplication.score == 0){
            timetype="";
            submitData(String.valueOf(0));
        }else {
            timetype="";
            submitData(String.valueOf(MyApplication.score));
        }
    }

    @Override
    public void onBackPressed() {
        if(checkAnswer){
            super.onBackPressed();
        }
    }
    /**
     * 显示失败信息，默认显示吐司，子类有需要显示界面可自行重写
     */
    protected void showErrorMessage(String errorMessage) {
        ToastUtils.custom(errorMessage);
    }

    /**
     * 显示失败信息，默认显示吐司，子类有需要显示界面可自行重写
     */
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        ToastUtils.custom(errorMessage);
    }
}
