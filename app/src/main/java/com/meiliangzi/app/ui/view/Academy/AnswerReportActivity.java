package com.meiliangzi.app.ui.view.Academy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;



public class AnswerReportActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_pass)
    TextView tv_pass;
    @BindView(R.id.tv_answeranalysis)
    TextView tv_answeranalysis;
    @BindView(R.id.tv_finish)
    TextView tv_finish;
    @BindView(R.id.rl_pass)
    RelativeLayout rl_pass;

    @BindView(R.id.tv_integral)
    TextView tv_integral;
    @BindView(R.id.tv_integral_week)
    TextView tv_integral_week;
    String paperId;
    String title;
    private String score;
    private String pass;
    private String type;
    @BindView(R.id.ll_kaosho)
    LinearLayout ll_kaosho;
    @BindView(R.id.ll_week)
    LinearLayout ll_week;
    private String pagetitle;
    private String totalNumber;
    private String countDown;
    private String time;
    private String finishStatus;
    private String answerTime;
    private String repeatAnswer;
    private String userId;
    private String mode;

    private String createType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paperId=getIntent().getStringExtra("paperId");
        score=getIntent().getStringExtra("score");
        pass=getIntent().getStringExtra("pass");
        type=getIntent().getStringExtra("type");
        paperId=getIntent().getStringExtra("paperId");
        type=getIntent().getStringExtra("type");
        title=getIntent().getStringExtra("title");
        pagetitle=getIntent().getStringExtra("pagetitle");
        totalNumber=getIntent().getStringExtra("totalNumber");
        countDown=getIntent().getStringExtra("countDown");
        time = getIntent().getStringExtra("time");
        userId=getIntent().getStringExtra("userId");
        //TODO
        finishStatus=getIntent().getStringExtra("finishStatus");
        answerTime=getIntent().getStringExtra("answerTime");
        repeatAnswer=getIntent().getStringExtra("repeatAnswer");

        createType=getIntent().getStringExtra("createType");
        mode=getIntent().getStringExtra("mode");
        onCreateView(R.layout.activity_answer_report);
    }

    @Override
    protected void findWidgets() {

        if("0".equals(type)||"1".equals(type)){
            //TODO 每周一答
            ll_week.setVisibility(View.VISIBLE);
            ll_kaosho.setVisibility(View.GONE);
            tv_pass.setText("您已经完成此次答题");
            tv_answeranalysis.setText("答案解析");
            tv_finish.setText("重新答题");
        }else {
            //TODO 专题考试
            if("0".equals(pass)){
                //TODO 未通过
                tv_pass.setText("还需再接再厉！");
                rl_pass.setBackground(getResources().getDrawable(R.mipmap.yellowpass));

            }else {
                //TODO 已通国
            }
            tv_answeranalysis.setText("答案解析");
            tv_finish.setText("返回主页");
            ll_week.setVisibility(View.GONE);
            ll_kaosho.setVisibility(View.VISIBLE);
        }
        tv_integral.setText(score);
        tv_integral_week.setText(score);
        tv_finish.setOnClickListener(this);
        tv_answeranalysis.setOnClickListener(this);

    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_finish:
                if(tv_finish.getText().equals("返回主页")){
                    IntentUtils.startAty(this, MainActivity.class);
                    finish();
                }else {
                    //TODO 重新答题
                    Intent intent=new Intent(this,WeekExaminationActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("userId", NewPreferManager.getId());
                    intent.putExtra("paperId",paperId);
                    intent.putExtra("paperId",paperId);
                    intent.putExtra("type",type);
                    intent.putExtra("userId",NewPreferManager.getId());
                    intent.putExtra("pagetitle",pagetitle);
                    intent.putExtra("time",time);
                    intent.putExtra("finishStatus",finishStatus);
                    intent.putExtra("answerTime",answerTime);
                    intent.putExtra("repeatAnswer",finishStatus);
                    intent.putExtra("totalNumber",totalNumber);
                    intent.putExtra("title",title);
                    intent.putExtra("mode",mode);
                    intent.putExtra("createType",createType);
                    startActivity(intent);
                    finish();
                }


                break;
            case R.id.tv_answeranalysis:
                //TODO 查看解析
                Intent intent=new Intent(this,AnalysisActivity.class);
                //TODO


                intent.putExtra("userId",NewPreferManager.getId());
                intent.putExtra("paperId",paperId);
                intent.putExtra("paperId",paperId);
                intent.putExtra("type",type);
                intent.putExtra("userId",NewPreferManager.getId());
                intent.putExtra("pagetitle",pagetitle);
                intent.putExtra("time",time);
                intent.putExtra("finishStatus",finishStatus);
                intent.putExtra("answerTime",answerTime);
                intent.putExtra("repeatAnswer",finishStatus);
                intent.putExtra("totalNumber",totalNumber);
                intent.putExtra("title",title);
                intent.putExtra("createType",createType);
                startActivity(intent);
                finish();
                break;

        }
    }
}
