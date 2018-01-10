package com.meiliangzi.app.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.meiliangzi.app.R;


/**
 * @version 创建时间：2014年5月22日 下午2:11:48 类说明
 * @class 类名
 */
public class CountDownHandler extends Handler {
    public static final int MSG_COUNT_DOWN_FLAG = 1;
    private int mCountDown;
    private TextView mBtnCaptch;
    private Context context;

    public CountDownHandler(Context context, TextView mBtnCaptch) {
        this.context = context;
        this.mBtnCaptch = mBtnCaptch;
    }

    public void setmCountDown(int mCountDown) {
        this.mCountDown = mCountDown;
    }

    public int getmCountDown() {
        return mCountDown;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_COUNT_DOWN_FLAG:
                if (mCountDown > 0) {
                    mCountDown--;
                    mBtnCaptch.setText(Integer.toString(mCountDown) + context.getString(R.string.resend_peroid_nofity));
                    mBtnCaptch.setEnabled(false);
                    this.sendEmptyMessageDelayed(MSG_COUNT_DOWN_FLAG, 1000);
                } else {
                    mBtnCaptch.setEnabled(true);
                    mBtnCaptch.setText(context.getString(R.string.register_acode));
                }
                break;
        }
        super.handleMessage(msg);
    }
}
