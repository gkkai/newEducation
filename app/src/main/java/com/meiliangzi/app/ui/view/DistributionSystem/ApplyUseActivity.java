package com.meiliangzi.app.ui.view.DistributionSystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.SendACarInfoArray;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.sendcar.AddSendacarActivity;
import com.meiliangzi.app.ui.view.sendcar.DriverListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

public class ApplyUseActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.auditor)
    TextView auditor;
    @BindView(R.id.startAtime)
    TextView startAtime;
    @BindView(R.id.endAtime)
    TextView endAtime;
    private TimePickerView pvTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_apply_use);
    }

    @Override
    protected void findWidgets() {
        auditor.setOnClickListener(this);
        startAtime.setOnClickListener(this);
        endAtime.setOnClickListener(this);
        initTimePicker();

    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.auditor:
                Intent intent=new Intent(this,SelectionAuditorsActivity.class);
                startActivityForResult(intent,102);

                break;
            case R.id.startAtime:
                pvTime.show(startAtime);
                break;
            case R.id.endAtime:
                pvTime.show(endAtime);
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode){
            case 101:

                break;
            case 102:

                break;
            case 103:
                break;
        }
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //ToastUtils.show(getTime(date));
                //TODO
                //     确定
                Log.i("pvTime", "onTimeSelect");
                ((TextView)pvTime.getClickView()).setText(getTime(date));

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        // TODO 选择
                        //((TextView)pvTime.getClickView()).setText(getTime(date));
                        pvTime.setTitleText(getTime(date));
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
