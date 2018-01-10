package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.receiver.CounterServer;
import com.meiliangzi.app.receiver.TagAliasOperatorHelper;
import com.meiliangzi.app.tools.FileUtil;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.listener.ClearCacheHandler;
import com.meiliangzi.app.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;

import static com.meiliangzi.app.receiver.TagAliasOperatorHelper.ACTION_DELETE;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 设置
 **/

public class SetttingActivity extends BaseActivity {

    @BindView(R.id.ivImg)
    CircleImageView ivImg;

    @BindView(R.id.tv_username)
    TextView tvUserName;
    @BindView(R.id.tvUpDatePwd)
    TextView tvUpDatePwd;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    private TextView cacheTxt;
    private TextView bundPhone;
    private TextView mClearCacheTxt;
    private RelativeLayout mClear;
    private ViewSwitcher mClearCacheViewSwitch;
    private ClearCacheHandler clearCacheHanler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_settting);
    }

    @Override
    protected void findWidgets() {
        clearCacheHanler = new ClearCacheHandler(this);
        // 清除缓存
        mClear = findView(R.id.layout_setting_clear_cache);
        mClearCacheTxt = findView(R.id.tv_settings_clear_cache);
        mClearCacheViewSwitch = findView(R.id.vs_settings_clear_cache);
        // 缓存容量显示
        cacheTxt =findView(R.id.tv_setting_cache);
        bundPhone= (TextView) findViewById(R.id.tvbindPhone);
        String r = PreferManager.getUserPhone();
        if(!"".equals(PreferManager.getUserPhone())){
            bundPhone.setVisibility(View.GONE);
            tvUpDatePwd.setVisibility(View.VISIBLE);

        }
        mClearCacheViewSwitch.setInAnimation(this, android.R.anim.fade_in);
        mClearCacheViewSwitch.setOutAnimation(this, android.R.anim.fade_out);
        if (PreferManager.getUserStar().startsWith("http")) {
            ImageLoader.getInstance().displayImage(PreferManager.getUserStar(), ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        } else {
            ImageLoader.getInstance().displayImage("file:///" + PreferManager.getUserStar(), ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        }
        tvUserName.setText(PreferManager.getUserName());
        tvUserPhone.setText(PreferManager.getUserPhone());
    }

    @Override
    protected void initComponent() {

    }

    @OnClick({R.id.llPersonInfo, R.id.tvUpDatePwd, R.id.tvAboutUs, R.id.layout_setting_clear_cache,R.id.tvLoginOut,R.id.tvbindPhone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llPersonInfo:
                IntentUtils.startAtyWithSingleParam(this, PersonCenterActivity.class, "activity", "PersonCenterActivity");
                break;
            case R.id.tvUpDatePwd:
                IntentUtils.startAty(SetttingActivity.this, UpdatePwdActivity.class);
                break;
            case R.id.tvAboutUs:
                IntentUtils.startAty(SetttingActivity.this, AboutUsActivity.class);
                break;
            case R.id.layout_setting_clear_cache:
                playClearAnimIfNeeded();
                break;
            case R.id.tvbindPhone:
                bindPhone();
                break;
            case R.id.tvLoginOut:
                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                tagAliasBean.action = ACTION_DELETE;
                if(true){
                    tagAliasBean.alias = String.valueOf(PreferManager.getUserId());
                }else{
//            tagAliasBean.tags = tags;
                }
                tagAliasBean.isAliasAction = true;
                int sequence = 1;
                TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
                PreferManager.saveUserId("");
//                if (!PreferManager.getUserId().isEmpty())
//                {
//                    long  start= Long.parseLong(PreferManager.getTimeStart());
//                    if (start==0){
//                        return;
//                    }
//                    long minite=(System.currentTimeMillis()-start)/(60*1000);
//                    doSubmit(String.valueOf(minite));
//                }
                Intent intent = new Intent(SetttingActivity.this, CounterServer.class);
                stopService(intent);
                PreferManager.saveIsCompleteInfo(false);
                SetttingActivity.this.finish();
                break;
        }
    }

    private void bindPhone() {
        IntentUtils.startAtyForResult(this,ResetPwdActivity.class,104,"BindPhone",101);
    }


    private String getSizeString(double size, int level) {
        if (size < 1.0) {
            return null;
        }
        String str = getSizeString(size / 1024, level + 1);
        if (str == null) {
            String ret = Double.toString(getSimpleDouble(size));
            switch (level) {
                case 0: // bytes
                    ret += " bytes";
                    break;
                case 1: // Kb
                    ret += " K";
                    break;
                case 2: // Mb
                    ret += " M";
                    break;
            }
            return ret;
        } else {
            return str;
        }
    }

    private double getSimpleDouble(double num) {
        BigDecimal bigDecimal = new BigDecimal(num);
        BigDecimal temp = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return temp.doubleValue();
    }

    private void clearCache(List<File> cacheFiles) {
        mClear.setClickable(false);
        mClearCacheViewSwitch.showNext();
        mClearCacheTxt.setText(R.string.setting_clearing);
        startNewThreadClearCache(cacheFiles);
    }

    public void onClearCacheOver() {
        mClearCacheViewSwitch.showPrevious();
        mClearCacheTxt.setText(R.string.setting_clear);
        mClear.setClickable(true);
    }

    private void startNewThreadClearCache(final List<File> files) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                clearCachFile(files);
                sendClearSuccesMessage();
            }
        });
    }

    private void playClearAnimIfNeeded() {
        List<File> cacheFiles = new ArrayList<File>();
        FileUtil.recursionFile(getCacheDir(), cacheFiles);
        if (cacheFiles.isEmpty()) {
            ToastUtils.custom("没有缓存文件需要清理");
        } else {
            clearCache(cacheFiles);
        }
    }

    private void clearCachFile(final List<File> files) {
        for (int pos = 0; pos < files.size(); pos++) {
            try {
                files.get(pos).delete();
                // 友好性交互，避免文件太少从而删除过快，一闪而逝的现象
                //Thread.sleep(150);
                clearCacheHanler.sendMessage(createHandlerMessage(pos));
            } catch (Exception e) {
//                Print.e(TAG, e);
                e.printStackTrace();
            }
        }
    }

    public void refreshCacheSize() {
        List<File> files = new ArrayList<File>();
        FileUtil.recursionFile(getCacheDir(), files);
        long total = 0;
        for (File file : files) {
            total += file.length();
        }
        if (cacheTxt == null) {
            return;
        }
        if (total == 0) {
            cacheTxt.setText("0.0 M");
        } else {
            cacheTxt.setText(getSizeString(total, 0));
        }
    }

    private void sendClearSuccesMessage() {
        clearCacheHanler.sendMessage(createHandlerMessage(Integer.MAX_VALUE));
    }

    public static Message createHandlerMessage(int value) {
        Message msg = new Message();
        msg.obj = value;
        return msg;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==103){
            bundPhone.setVisibility(View.GONE);
            tvUpDatePwd.setVisibility(View.VISIBLE);
        }else {

        }
    }
}
