package com.meiliangzi.app.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.meiliangzi.app.R;


/**
 * Created by kk on 2017/9/25.
 */

public class DelectGroupUserDialog extends Dialog {
    private TextView setadmin;//确定按钮
    private TextView delectuser;//取消按钮
    private View bround;//取消按钮
    //确定文本和取消文本的显示内容
    private String yesStr, noStr;
private Context context;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private String messageStr;
    private boolean isshwo;

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener( onNoOnclickListener onNoOnclickListener) {

        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener( onYesOnclickListener onYesOnclickListener) {

        this.yesOnclickListener = onYesOnclickListener;
    }

    public DelectGroupUserDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delect_groupuser_dialog_layout);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        setadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        delectuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
       
        if (setadmin != null) {
            setadmin.setText(messageStr);
        }
        if (setadmin != null) {
            if(isshwo){
                setadmin.setVisibility(View.GONE);
                bround.setVisibility(View.GONE);
            }

        }

    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        setadmin = (TextView) findViewById(R.id.text_setandmin);
        delectuser = (TextView) findViewById(R.id.text_delectuer);
        bround=findViewById(R.id.backgroud);
        //titleTv = (TextView) findViewById(R.id.title);
        //messageTv = (TextView) findViewById(R.id.message);
    }
    public void showsetadminview(boolean isshwo){
            this.isshwo=isshwo;
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = 0.5f;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = 1.0f;
        ((Activity) context).getWindow().setAttributes(lp);

    }

  
    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public void setMessage(String message) {
        messageStr = message;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
