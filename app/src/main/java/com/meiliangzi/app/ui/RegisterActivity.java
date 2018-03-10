package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.ImageCodeBean;
import com.meiliangzi.app.model.bean.Register;
import com.meiliangzi.app.model.bean.Validate;
import com.meiliangzi.app.tools.CountDownHandler;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/15
 * @description 注册
 **/

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.cbLine1)
    CheckBox cbLine1;
    @BindView(R.id.cbLine2)
    CheckBox cbLine2;
    @BindView(R.id.etNickName)
    EditText etNickName;
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.etValidate)
    EditText etValidate;
    @BindView(R.id.tvValidate)
    TextView tvValidate;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.cbIsVisable)
    CheckBox cbIsVisable;
    @BindView(R.id.tvRegisterAgreement)
    TextView tvRegisterAgreement;
    @BindView(R.id.image_dentifyingcode)
    ImageView image_dentifyingcode;
    @BindView(R.id.et_identifyingcode)
    EditText et_identifyingcode;
    ImageCodeBean data;
    private CountDownHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_register);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        mHandler = new CountDownHandler(RegisterActivity.this, tvValidate);
    }

    @Override
    protected void initListener() {
        etAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbLine1.setChecked(true);
                } else {
                    cbLine1.setChecked(false);
                }
            }
        });
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    cbLine2.setChecked(true);
                } else {
                    cbLine2.setChecked(false);
                }
            }
        });

        cbIsVisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbIsVisable.isChecked()){
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @OnClick({R.id.tvRegister,R.id.tvValidate,R.id.tvRegisterAgreement,R.id.image_dentifyingcode})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvRegister:
                try {
                    RuleCheckUtils.checkEmpty(etNickName.getText().toString(),"请输入真实姓名");
                    RuleCheckUtils.checkPhone(etAccount.getText().toString());
                    RuleCheckUtils.checkEmpty(etValidate.getText().toString(),"请输入验证码");
                    RuleCheckUtils.checkPwdLength(etPwd.getText().toString());
                    if(checkBox.isChecked()){
                       register(etAccount.getText().toString(),etPwd.getText().toString(),etNickName.getText().toString(),etValidate.getText().toString());
                    }else {
                        ToastUtils.custom("请阅读注册协议");
                    }
                }  catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.custom(e.getMessage());
                }
                break;
            case R.id.tvValidate:
                try {
                    RuleCheckUtils.checkPhones(etAccount.getText().toString());
                    RuleCheckUtils.checkEmpty(et_identifyingcode.getText().toString(),"请输入图文验证");
                    getValidate(etAccount.getText().toString(),et_identifyingcode.getText().toString(),data.getData().getAt());

                    //getValidate(phone);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.custom(e.getMessage());
                }
                break;
            case R.id.tvRegisterAgreement:
                IntentUtils.startAty(RegisterActivity.this,RegisterAgreementActivity.class);
                break;
            case R.id.image_dentifyingcode:
                //TODO 图文验证刷新
                //ProxyUtils.getHttpProxy().imagecode(ResetPwdActivity.this);
                ProxyUtils.getHttpProxy().imagecode(RegisterActivity.this);
                break;
        }
    }

    public void getValidate(String phone,String text,int code){
        ProxyUtils.getHttpProxy().sms(RegisterActivity.this,phone,code,text);
    }

    protected void getValidate(Validate validate){
        ToastUtils.custom("验证码已发送，请注意查收");
        mHandler.setmCountDown(Constant.COUNT_NUM);
        mHandler.sendEmptyMessage(CountDownHandler.MSG_COUNT_DOWN_FLAG);
    }

    public void register(String loginName,String password,String userName,String code){
        ProxyUtils.getHttpProxy().reg(RegisterActivity.this,loginName,password,userName,code);
    }

    protected void getResult(Register register){
        ToastUtils.custom("注册成功");
        RegisterActivity.this.finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ProxyUtils.getHttpProxy().imagecode(this);
    }
    public void getimagecode(ImageCodeBean data){
        //ProxyUtils.getHttpProxy().sms(ResetPwdActivity.this,phone);
        this.data=data;
        setImageByUrl(image_dentifyingcode,data.getData().getImage(),R.mipmap.imagecode,R.mipmap.imagecode);

        //getValidate(etAccount.getText().toString(),et_identifyingcode.getText().toString());
    }
    public void setImageByUrl(ImageView view, String url, Integer fallbackResource, Integer loadingResource) {
        // ImageView view = getView(viewID);

        ImageLoader.getInstance().displayImage(url, view, MyApplication.getSimpleOptions(fallbackResource, loadingResource));
    }
}
