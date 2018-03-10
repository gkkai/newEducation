package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.model.bean.BindPhoneBean;
import com.meiliangzi.app.model.bean.ImageCodeBean;
import com.meiliangzi.app.model.bean.Validate;
import com.meiliangzi.app.tools.CountDownHandler;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.widget.MiddleView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/15
 * @description 重置密码
 **/

public class ResetPwdActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etValidate)
    EditText etValidate;
    @BindView(R.id.tvResetOk)
    TextView tvResetOrBind;
    @BindView(R.id.tvValidate)
    TextView tvValidate;

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.etNewPww)
    EditText etNewPww;
    @BindView(R.id.cbIsVisable)
    CheckBox cbIsVisable;
    @BindView(R.id.isVisiable2)
    CheckBox isVisiable2;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.ll_identifyingcode)
    LinearLayout ll_identifyingcode;
    @BindView(R.id.image_dentifyingcode)
    ImageView image_dentifyingcode;
    @BindView(R.id.et_identifyingcode)
    EditText et_identifyingcode;
    ImageCodeBean data;
    private CountDownHandler mHandler;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_reset_pwd);
    }

    @Override
    protected void findWidgets() {
        tvEmpty.setOnClickListener(this);
        type=getIntent().getIntExtra("BindPhone",100);
        if(getIntent().getIntExtra("BindPhone",100)==101) {
            tvResetOrBind.setText("确认绑定");
            title.setText("绑定手机");
        }
    }

    @Override
    protected void initComponent() {
        mHandler = new CountDownHandler(ResetPwdActivity.this, tvValidate);
    }

    public void showDialog() {
        final MiddleView middleView = new MiddleView(ResetPwdActivity.this, R.layout.panel_reset);
        middleView.getView().findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
                ToastUtils.custom("重置成功");
                ResetPwdActivity.this.finish();
            }
        });
        middleView.showModdleView(true);
    }

    @OnClick({R.id.tvValidate, R.id.tvResetOk,R.id.image_dentifyingcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvValidate:
                //TODO 图文验证

                try {
                    RuleCheckUtils.checkPhones(etAccount.getText().toString());
                    RuleCheckUtils.checkEmpty(et_identifyingcode.getText().toString(),"请输入图文验证");
                    //getValidate(etAccount.getText().toString());
                   // ProxyUtils.getHttpProxy().imagecode(ResetPwdActivity.this);

                    getValidate(etAccount.getText().toString(),et_identifyingcode.getText().toString(),data.getData().getAt());

                }  catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.custom(e.getMessage());
                }
                break;
            case R.id.image_dentifyingcode:
                //TODO 图文验证刷新
                //ProxyUtils.getHttpProxy().imagecode(ResetPwdActivity.this);
                ProxyUtils.getHttpProxy().imagecode(ResetPwdActivity.this);
                break;

            case R.id.tvResetOk:

                    try {
                        RuleCheckUtils.checkPhones(etAccount.getText().toString());
                        RuleCheckUtils.checkEmpty(etValidate.getText().toString(),"请输入验证码");
                        RuleCheckUtils.checkEmpty(etNewPww.getText().toString(),"请输入密码");
                        RuleCheckUtils.checkPwdLength(etNewPww.getText().toString());
                        RuleCheckUtils.checkIsEqual(etNewPww.getText().toString(),etPwd.getText().toString());
                        if(getIntent().getIntExtra("BindPhone",100)==101){
                            BindPhoneBean( PreferManager.getUserId(),etAccount.getText().toString(), etValidate.getText().toString(), etNewPww.getText().toString());
                        }else {
                            findPwd(etAccount.getText().toString(), etValidate.getText().toString(), etNewPww.getText().toString());
                        }
                    }  catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.custom(e.getMessage());

                }

                break;
        }
    }

    private void BindPhoneBean(String userId, String phone, String sms, String pwd) {
        String p=userId+","+phone+","+sms+","+pwd;
        ProxyUtils.getHttpProxy().addphone(this,userId,phone,sms,pwd);
    }
    public void getimagecode(ImageCodeBean data){
        //ProxyUtils.getHttpProxy().sms(ResetPwdActivity.this,phone);
        this.data=data;
        setImageByUrl(image_dentifyingcode,data.getData().getImage(),R.mipmap.imagecode,R.mipmap.imagecode);

        //getValidate(etAccount.getText().toString(),et_identifyingcode.getText().toString());
    }
    public void getValidate(String phone,String text,int code){
        ProxyUtils.getHttpProxy().sms(ResetPwdActivity.this,phone,code,text);
    }

    protected void getValidate(Validate validate){
        ToastUtils.custom("验证码已发送，请注意查收");

        mHandler.setmCountDown(Constant.COUNT_NUM);
        mHandler.sendEmptyMessage(CountDownHandler.MSG_COUNT_DOWN_FLAG);
    }
    protected void addphone(BindPhoneBean validate){
        if("0".equals(validate.getStatus())){
            ToastUtils.custom("绑定成功");
            PreferManager.saveUserPhone(etAccount.getText().toString());
            setResult(103);
            finish();
        }else {
            ToastUtils.custom("绑定失败，"+validate.getMsg());
        }
        //ToastUtils.custom("验证码已发送，请注意查收");
       /* mHandler.setmCountDown(Constant.COUNT_NUM);
        mHandler.sendEmptyMessage(CountDownHandler.MSG_COUNT_DOWN_FLAG);*/
    }

    @Override
    protected void initListener() {
        cbIsVisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbIsVisable.isChecked()){
                    etNewPww.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etNewPww.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        isVisiable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisiable2.isChecked()){
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void findPwd(String phone,String authcode,String newPassword){
        ProxyUtils.getHttpProxy().forgetpassword(ResetPwdActivity.this,phone,authcode,newPassword);
    }

    protected void getResult(BaseBean baseBean){
        ToastUtils.custom("找回密码成功");
        ResetPwdActivity.this.finish();
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
        ToastUtils.custom(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
        ToastUtils.custom(errorMessage);
    }

    @Override
    public void onClick(View v) {
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyWithSingleParam(ResetPwdActivity.this, LoginActivity.class, "activity", "WholeFragment");
                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyWithSingleParam(ResetPwdActivity.this, PersonCenterActivity.class, "activity", "WholeFragment");
                }
            }
        });
    }

    /*@Override
    public void onBackClick(View v) {
        super.onBackClick(v);
        setResult(RESULT_OK);
    }*/

    /*@Override
    public void onBackClick() {
        super.onBackClick();
        Intent intent=new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if(type==101){
            if (TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    tvEmpty.setText("请先登录");
                } else {
                    tvEmpty.setText("请完善个人信息");
                }
                tvEmpty.setVisibility(View.VISIBLE);

            }else

            {
                tvEmpty.setVisibility(View.GONE);
            }
        }

        ProxyUtils.getHttpProxy().imagecode(ResetPwdActivity.this);

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
}
