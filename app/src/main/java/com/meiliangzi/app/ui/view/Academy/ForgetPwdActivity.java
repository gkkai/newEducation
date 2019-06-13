package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.Validate;
import com.meiliangzi.app.tools.CountDownHandler;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.ResetPwdActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.bean.BaseInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Connection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.meiliangzi.app.config.Constant.ChanYeXY;

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.et_picturecode)
    EditText et_picturecode;

    @BindView(R.id.image_dentifyingcode)
    ImageView image_dentifyingcode;
    @BindView(R.id.et_messagecode)
    EditText et_messagecode;

    @BindView(R.id.tvValidate)
    TextView tvValidate;
    @BindView(R.id.et_newpwd)
    EditText et_newpwd;

    @BindView(R.id.tvsure)
    TextView tvsure;
    private CountDownHandler mHandler;
    String time= String.valueOf(System.currentTimeMillis());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_forget_pwd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvValidate:
                //短信验证码
                getValidate(etAccount.getText().toString(),et_picturecode.getText().toString());
                break;
            case R.id.image_dentifyingcode:
                captcha();
                break;
            case R.id.tvsure:
                //短信验证码
                forgetPasswd(etAccount.getText().toString(),et_messagecode.getText().toString(),et_newpwd.getText().toString());
                break;
        }

    }

    private void forgetPasswd(String phone,String code,String newPasswd) {
        Map<String,String> maps=new HashMap<>();
        //maps.put("userId", NewPreferManager.getId());
        maps.put("phone",phone);
        maps.put("code",code);
        maps.put("newPwd",md5(newPasswd));
        OkhttpUtils.getInstance(this).doPost("organizationService/userAccount/forgetPasswd", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final BaseInfo b=gson.fromJson(json,BaseInfo.class);
                if(0==b.getCode()){
                    finish();
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(b.getSuccess());
                        }
                    });

                }

            }
        });
    }

    public void getValidate(String phone,String code){
        Map<String,String> maps=new HashMap<>();
        maps.put("phone",phone);
        maps.put("code",code);
        maps.put("codeSign",time);
        OkhttpUtils.getInstance(this).getget("organizationService/user/sendMessage", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show(e.getMessage());
                    }
                });

            }
            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final BaseInfo b=gson.fromJson(json,BaseInfo.class);
                if(0==b.getCode()){
                    mHandler.setmCountDown(Constant.COUNT_NUM);
                    mHandler.sendEmptyMessage(CountDownHandler.MSG_COUNT_DOWN_FLAG);
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(b.getSuccess());
                        }
                    });

                }

            }
        });


    }
    protected void getValidate(Validate validate){
        ToastUtils.custom("验证码已发送，请注意查收");

        mHandler.setmCountDown(Constant.COUNT_NUM);
        mHandler.sendEmptyMessage(CountDownHandler.MSG_COUNT_DOWN_FLAG);
    }
    public void captcha(){
        time= String.valueOf(System.currentTimeMillis());
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
////获取当前时间
//        Date date = new Date();

        Map<String,String> maps=new HashMap<>();
        maps.put("codeSign",time);
//图片验证码
        setImageByUrl(image_dentifyingcode,ChanYeXY+"organizationService/captcCha/captcha?codeSign="+time,R.mipmap.imagecode,R.mipmap.imagecode);
    }
    /**
     * 为SmratImageView设置图片、加载中图片、加载失败图片
     *
     * @param view
     * @param url
     * @return
     */
    public void setImageByUrl(ImageView view, String url, Integer fallbackResource, Integer loadingResource) {
        // ImageView view = getView(viewID);

        ImageLoader.getInstance().displayImage(url, view, MyApplication.getSimpleOptions(fallbackResource, loadingResource));
    }

    @Override
    protected void findWidgets() {


        image_dentifyingcode.setOnClickListener(this);
        tvValidate.setOnClickListener(this);
        tvsure.setOnClickListener(this);
        captcha();

    }

    @Override
    protected void initComponent() {
        mHandler = new CountDownHandler(this, tvValidate);

    }
    @NonNull
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
