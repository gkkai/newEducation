package com.meiliangzi.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.meiliangzi.app.R;


public class MiddleView {
    private View convertView;
    private Context context;
    private int theme;
    private Dialog bv;
    private int animationStyle;
    private boolean isTop = false;

    public MiddleView(Context c, int layoutRes) {
        this.context = c;
        this.theme = R.style.BottomViewTheme_Defalut;
        this.convertView = View.inflate(c, layoutRes, null);
//		this.setAnimation(R.style.BottomToTopAnim);
        this.bv = new Dialog(this.context, this.theme);

        this.bv.setContentView(this.convertView);
        this.bv .setCancelable(false);
        this.bv.setCanceledOnTouchOutside(false);
    }

    @SuppressWarnings("deprecation")
    public void showModdleView(boolean CanceledOnTouchOutside) {

        // this.bv.getWindow().requestFeature(1);
        Window wm = this.bv.getWindow();
        WindowManager m = wm.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = wm.getAttributes();
        p.width = (int) (d.getWidth() * 0.8);
        p.gravity = Gravity.CENTER;
        if (this.animationStyle != 0) {
            wm.setWindowAnimations(this.animationStyle);
        }
        this.bv.setCanceledOnTouchOutside(CanceledOnTouchOutside);
        wm.setAttributes(p);
        this.bv.show();
    }

    public Dialog getBv() {
        return bv;
    }

    public void setTopIfNecessary() {
        this.isTop = true;
    }

    public void setAnimation(int animationStyle) {
        this.animationStyle = animationStyle;
    }

    public View getView() {
        return this.convertView;

    }

    public void dismissMiddleView() {
        if (this.bv != null)
            this.bv.dismiss();
    }
}
