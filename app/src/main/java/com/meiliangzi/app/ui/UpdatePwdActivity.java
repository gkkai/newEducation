package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/18
 * @description 修改密码
 **/

public class UpdatePwdActivity extends BaseActivity {

    @BindView(R.id.etCurrentPwd)
    EditText etCurrentPwd;
    @BindView(R.id.cbIsVisable)
    CheckBox cbIsVisable;
    @BindView(R.id.etNewPww)
    EditText etNewPww;
    @BindView(R.id.isVisiable2)
    CheckBox isVisiable2;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.cbIsVisable3)
    CheckBox cbIsVisable3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_update_pwd);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        cbIsVisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbIsVisable.isChecked()){
                    etCurrentPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etCurrentPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        cbIsVisable3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbIsVisable3.isChecked()){
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        isVisiable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isVisiable2.isChecked()){
                    etNewPww.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etNewPww.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    @OnClick(R.id.tvSave)
    public void onClick() {
        try {
            RuleCheckUtils.checkEmpty(etCurrentPwd.getText().toString(),"请输入当前使用的登录密码");
            RuleCheckUtils.checkPwdLength(etCurrentPwd.getText().toString());
            RuleCheckUtils.checkEmpty(etNewPww.getText().toString(),"请输6-18位新密码");
            RuleCheckUtils.checkPwdLength(etNewPww.getText().toString());
            RuleCheckUtils.checkIsEqual(etNewPww.getText().toString(),etPwd.getText().toString());
            modifyPwd(PreferManager.getUserId(),etCurrentPwd.getText().toString(),etNewPww.getText().toString());
        } catch (Exception e) {
            ToastUtils.custom(e.getMessage());
            e.printStackTrace();
        }
    }

    public void modifyPwd(String userId,String oldPassword,String newPassword){
        ProxyUtils.getHttpProxy().modifypassword(UpdatePwdActivity.this,userId,oldPassword,newPassword);
    }


    protected void getResult(BaseBean baseBean){
        ToastUtils.custom("修改成功");
        UpdatePwdActivity.this.finish();
    }

}
