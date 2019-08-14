package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;

public class ErrorReportActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_currentAnswerRightNumber)
    TextView tv_currentAnswerRightNumber;
    @BindView(R.id.tv_lastWrongNumber)
    TextView tv_lastWrongNumber;
    @BindView(R.id.tv_pass)
    TextView tv_pass;
    @BindView(R.id.rl_pass)
    RelativeLayout rl_pass;
    private int currentAnswerWrongNumber;
    private int currentAnswerRightNumber;
    private int lastWrongNumber;

    @BindView(R.id.tv_answer)
    TextView tv_answer;
    @BindView(R.id.tv_finish)
    TextView tv_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentAnswerWrongNumber= getIntent().getIntExtra("currentAnswerWrongNumber",1);
        currentAnswerRightNumber= getIntent().getIntExtra("currentAnswerRightNumber",1);
        lastWrongNumber= getIntent().getIntExtra("lastWrongNumber",1);
        onCreateView(R.layout.activity_error_report);
    }

    @Override
    protected void findWidgets() {
        tv_lastWrongNumber.setText("剩"+lastWrongNumber+"题");
        tv_currentAnswerRightNumber.setText("答对"+currentAnswerRightNumber+"题");
        int totle=(lastWrongNumber+currentAnswerRightNumber)/2;
        if(currentAnswerRightNumber>=totle){

            tv_pass.setText("您已经完成此次答题");
        }else {
            rl_pass.setBackground(getResources().getDrawable(R.mipmap.star));
            tv_pass.setText("一定要继续努力");
        }
        tv_answer.setOnClickListener(this);
        tv_finish.setOnClickListener(this);

    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_answer:
                //TODO 继续答题

                IntentUtils.startAty(this, ErrorBankActivity.class);
                finish();
                break;
            case R.id.tv_finish:
                //TODO 返回主页

                finish();
                break;
        }
    }
}
