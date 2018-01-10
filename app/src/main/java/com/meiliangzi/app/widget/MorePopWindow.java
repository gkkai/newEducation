package com.meiliangzi.app.widget;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.view.imkit.AddFridengActivity;
import com.meiliangzi.app.ui.view.imkit.ChatFridentActivity;
import com.meiliangzi.app.ui.view.imkit.NewBuildChatGroupActivity;


public class MorePopWindow extends PopupWindow {
    Context context;
    @SuppressLint("InflateParams")
    public MorePopWindow(final Activity context) {
        this.context=context;
        LayoutInflater inflater = (LayoutInflater) context
                                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.popupwindow_add, null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(content);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        setBackgroundAlpha(0.5f);//设置屏幕透明度

        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);


        RelativeLayout re_addfriends = (RelativeLayout) content.findViewById(R.id.re_addfriends);
        RelativeLayout re_chatroom = (RelativeLayout) content.findViewById(R.id.re_chatroom);
        RelativeLayout re_scanner = (RelativeLayout) content.findViewById(R.id.re_scanner);
        re_addfriends.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO 新建群聊
               context.startActivity(new Intent(context, NewBuildChatGroupActivity.class));
                MorePopWindow.this.dismiss();
               /*Intent intent=new Intent(context, ChatFridentActivity.class);
                context.startActivity(intent);
                MorePopWindow.this.dismiss();*/

            }

        });
        re_chatroom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO 新建群聊
                /*context.startActivity(new Intent(context, NewBuildChatGroupActivity.class));
                MorePopWindow.this.dismiss();*/
                /*Intent intent=new Intent(context, ChatFridentActivity.class);
                context.startActivity(intent);
                MorePopWindow.this.dismiss();*/

            }

        });
        re_scanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddFridengActivity.class));
                MorePopWindow.this.dismiss();
            }
        });


    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
            setBackgroundAlpha(1.0f);
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }
}
