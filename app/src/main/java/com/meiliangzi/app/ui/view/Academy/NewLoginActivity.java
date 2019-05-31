package com.meiliangzi.app.ui.view.Academy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.receiver.TagAliasOperatorHelper;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.PreferUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.RegisterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.bean.NewBaseBean;
import com.meiliangzi.app.ui.view.Academy.bean.UserInfoBean;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;

import static com.meiliangzi.app.config.Constant.LOGINID;

public class NewLoginActivity  extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.cbLine1)
    CheckBox cbLine1;

    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.cbLine2)
    CheckBox cbLine2;

    @BindView(R.id.tvphone)
    TextView tvphone;
    @BindView(R.id.tvworknumber)
    TextView tvworknumber;

    @BindView(R.id.cbLine11)
    View cbLine11;
    @BindView(R.id.cbLine22)
    View cbLine22;
    @BindView(R.id.check_rem_pwd)
    CheckBox cbRemPwd;
    private String loginName="";
    private String pwd="";
    private IWXAPI api;
    private String type="1";

    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvForgetPwd)
    TextView tvForgetPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏


        onCreateView(R.layout.activity_new_login);
    }

    @Override
    protected void findWidgets() {
        cbRemPwd.setChecked(NewPreferManager.getIsRememberPwd().equals("1"));
        etAccount.setText(NewPreferManager.getPhone());
        if(NewPreferManager.getIsRememberPwd().equals("1")){

            etPwd.setText(NewPreferManager.getPasswd());
        }
        tvphone.setTextColor(getResources().getColor(R.color.zm_red));
        tvworknumber.setTextColor(getResources().getColor(R.color.group_list_gray));
        cbLine11.setBackgroundColor(getResources().getColor(R.color.de_draft_color));
        cbLine22.setBackgroundColor(getResources().getColor(R.color.gray1));
        tvphone.setOnClickListener(this);
        tvworknumber.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);

    }

    @Override
    protected void initComponent() {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvphone:
                tvphone.setTextColor(getResources().getColor(R.color.de_draft_color));
                tvworknumber.setTextColor(getResources().getColor(R.color.gray1));
                cbLine11.setBackgroundColor(getResources().getColor(R.color.de_draft_color));
                cbLine22.setBackgroundColor(getResources().getColor(R.color.gray1));
                etAccount.setHint("请输入手机号码");
                etAccount.setText("");
                etPwd.setText("");
                type="1";
                break;
            case R.id.tvworknumber:
                tvphone.setTextColor(getResources().getColor(R.color.gray1));
                tvworknumber.setTextColor(getResources().getColor(R.color.de_draft_color));
                cbLine11.setBackgroundColor(getResources().getColor(R.color.gray1));
                cbLine22.setBackgroundColor(getResources().getColor(R.color.de_draft_color));
                etAccount.setHint("请输入工号");
                etAccount.setText("");
                etPwd.setText("");
                type="2";
                break;
            case R.id.tvForgetPwd:
                IntentUtils.startAty(this,ForgetPwdActivity.class);
                break;
            case R.id.tvLogin:
                try {
                    loginName = etAccount.getText().toString();
                    pwd = etPwd.getText().toString();
                    RuleCheckUtils.checkEmpty(loginName,"请输入用户名");
                    RuleCheckUtils.checkPhone(loginName);
                    RuleCheckUtils.checkPwdLength(pwd);
                    login(loginName,pwd);
                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }

                break;
            case R.id.tvRegister:
                IntentUtils.startAty(this,RegisterActivity.class);
                break;

        }
    }
    public void login(String loginName,String password){
        if (cbRemPwd.isChecked()){
            NewPreferManager.saveIsRememberPwd("1");
        }else {
            NewPreferManager.saveIsRememberPwd("0");
        }
        NewPreferManager.savePasswd(password);
        Map<String,String> map=new HashMap();
        map.put("loginName",loginName);
        map.put("passwd",md5(password));
        map.put("type",type);
        OkhttpUtils.getInstance(this).dologin("organizationService/userAccount/userLogin", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final NewBaseBean bean=gson.fromJson(json,NewBaseBean.class);
                if("1".equals(bean.getCode())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(bean.getMessage());
                        }
                    });
                }else {
                    NewPreferManager.saveToken(bean.getData().getTokenVo().getToken());
                    NewPreferManager.saveOrgId(bean.getData().getOrgId());
                    NewPreferManager.saverefreshToken(bean.getData().getTokenVo().getRefreshToken());

                    userInfo(bean.getData().getUserId(),bean.getData().getOrgId());
                    getlsit(bean.getData().getUserId());
                    loginScore(bean.getData().getUserId());

                }
            }
        });
    }

    private void loginScore(String userId) {
        Map<String,String> maps=new HashMap<>();
        maps.put("userId", userId);
        maps.put("ruleId", LOGINID);

        OkhttpUtils.getInstance(this).doPost("academyService/detail/loginScore", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(final String json) {
               // NewPreferManager.saveRuleLists(json);

            }
        });
    }

    private void getlsit(String userId){
        Map<String,String> maps=new HashMap<>();
        maps.put("userId", userId);

        OkhttpUtils.getInstance(this).getList("academyService/rule/lists", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(final String json) {
                NewPreferManager.saveRuleLists(json);
            }
        });
    }
    private void userInfo(final String userId,String orgId){
        Map<String,String> map=new HashMap();
        map.put("userId",userId);
        map.put("orgId",orgId);
        OkhttpUtils.getInstance(this).getList("academyService/userInfo/findByUserInfo", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final UserInfoBean user=gson.fromJson(json,UserInfoBean.class);
                if("1".equals(user.getCode())){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(user.getMessage());
                        }
                    });
                }else {
                    NewPreferManager.saveUserSex(user.getData().getUserSex());
                    NewPreferManager.saveOrganizationName(user.getData().getOrganizationName());
                    NewPreferManager.saveOrganizationId(user.getData().getOrganizationId());
                    NewPreferManager.savePhoto(user.getData().getPhoto());
                    NewPreferManager.saveUserName(user.getData().getUserName());
                    NewPreferManager.saveBirthDate(user.getData().getBirthDate());
                    NewPreferManager.saveNativePlace(user.getData().getNativePlace());
                    NewPreferManager.savePartyMasses(user.getData().getPartyMasses());
                    NewPreferManager.savePartyName(user.getData().getPartyName());
                    NewPreferManager.savePartyPositionName(user.getData().getPartyPositionName());
                    NewPreferManager.savePhone(user.getData().getPhone());
                    NewPreferManager.saveUserCode(user.getData().getUserCode());
                    NewPreferManager.saveWorkNumber(user.getData().getWorkNumber());
                    NewPreferManager.savePositionName(user.getData().getPositionName());
                    NewPreferManager.savePositionId(user.getData().getPositionId());
                    NewPreferManager.saveId(user.getData().getId());
                    NewPreferManager.saveUserTotalScore(user.getData().getUserTotalScore());
                    //TODO 获取部门列表
                    IntentUtils.startAty(NewLoginActivity.this, MainActivity.class);
                    finish();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                            if(true){
                                tagAliasBean.alias = String.valueOf(user.getData().getId());
                            }else{
                            }
                            int sequence = 1;
                            sequence++;
                            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
                            tagAliasBean.isAliasAction=true;
                            TagAliasOperatorHelper.getInstance().handleAction(NewLoginActivity.this,sequence, tagAliasBean);

                        }
                    });



                }


            }
        });


    }
    /**
     * 32位MD5加密的大写字符串
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
