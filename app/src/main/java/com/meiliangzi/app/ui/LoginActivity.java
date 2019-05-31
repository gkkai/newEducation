package com.meiliangzi.app.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.model.bean.GroupinfoBean;
import com.meiliangzi.app.model.bean.QueryUserInfoBean;
import com.meiliangzi.app.model.bean.User;
import com.meiliangzi.app.receiver.TagAliasOperatorHelper;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.AddFridentDialog;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;


public class LoginActivity extends BaseActivity implements RongIM.UserInfoProvider, RongIM.GroupInfoProvider, RongIM.GroupUserInfoProvider {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.cbLine1)
    CheckBox cbLine1;
    @BindView(R.id.check_rem_pwd)
    CheckBox cbRemPwd;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.cbLine2)
    CheckBox cbLine2;
    @BindView(R.id.checkBox)
    CheckBox chFindPwd;
    private AddFridentDialog dialog;
    private String loginName="";
    private String pwd="";
    private IWXAPI api;
    SQLiteDatabase database = null;
    private SQLHelper helper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) ;//隐藏状态栏
        onCreateView(R.layout.activity_login);
        if(helper ==null){
            helper = new SQLHelper(this);
        }
    }

    @Override
    protected void findWidgets() {
        cbRemPwd.setChecked(PreferManager.getIsRememberPwd().equals("1"));
        etAccount.setText(PreferManager.getUserPhone());
        if (PreferManager.getIsRememberPwd().equals("1")){
            etPwd.setText(PreferManager.getLoginPwd());
        }
    }

    @Override
    protected void initComponent() {
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
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
        chFindPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }



    @OnClick({R.id.ivBack, R.id.tvForgetPwd, R.id.tvLogin, R.id.tvRegister,R.id.tvWeiChatLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                LoginActivity.this.finish();
                break;
            case R.id.tvForgetPwd:
                IntentUtils.startAty(LoginActivity.this,ResetPwdActivity.class);
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
                IntentUtils.startAty(LoginActivity.this,RegisterActivity.class);
                break;
            case R.id.tvWeiChatLogin:
                UMShareAPI.get(this).deleteOauth(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    public void login(String loginName,String password){
        ProxyUtils.getHttpProxy().login(LoginActivity.this,loginName,password);


    }
    private void getmentusernumber(DepartmentuserNumberBean data){
        MyApplication.numberBean=data;
    }

    protected void getResult(User user){
      
        PreferManager.saveUserId(String.valueOf(user.getData().getUserId()));
        PreferManager.saveTokens(user.getData().getTokens());
        PreferManager.saveUserStar(user.getData().getImage());
        PreferManager.saveUserPhone(user.getData().getPhone());
        PreferManager.saveUserName(user.getData().getUserName());
        PreferManager.saveCompany(user.getData().getUserCompany().getCompany_name());
        PreferManager.saveCompanyId(user.getData().getUserCompany().getId());
        PreferManager.saveWorkNum(String.valueOf(user.getData().getUser_code()));
        PreferManager.saveDepartment(user.getData().getDepartment().getDepartment_name());
        PreferManager.savePartmentId(user.getData().getDepartment().getId());
        PreferManager.saveIsPartment(user.getData().isIsPartymember());
        PreferManager.partyBranName(user.getData().getPartybranch().getPartybranch_name());
        PreferManager.saveTimeStart(System.currentTimeMillis()+"");
        PreferManager.saveIsAuthorization(user.getData().getIsAuthorization());
        if(TextUtils.isEmpty(user.getData().getUserCompany().getCompany_name())){
            PreferManager.saveIsCompleteInfo(false);
        }else {
            PreferManager.saveIsCompleteInfo(true);
        }
        if (cbRemPwd.isChecked()){
            PreferManager.saveIsRememberPwd("1");
            PreferManager.saveLoginPwd(pwd);
        }else {
            PreferManager.saveIsRememberPwd("0");
            PreferManager.saveLoginPwd("");
        }
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        if(true){
            tagAliasBean.alias = String.valueOf(user.getData().getUserId());
        }else{
//            tagAliasBean.tags = tags;
        }
        int sequence = 1;
        sequence++;
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        tagAliasBean.isAliasAction=true;
        TagAliasOperatorHelper.getInstance().handleAction(LoginActivity.this,sequence, tagAliasBean);
       //TODO 获取部门列表
       // ProxyUtils.getHttpProxy().querydepartmentusernumber(this, Integer.valueOf(PreferManager.getUserId()));

        IntentUtils.startAty(this, MainActivity.class);
        this.finish();
    }


    private void WeiLogin(String unionid,String nickName,String headUrl){
        ProxyUtils.getHttpProxy().login(LoginActivity.this,unionid,nickName,headUrl);
    }

    protected void getResult(JSONObject jsonObject){
//        ToastUtils.custom("123");
    }


    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener1);

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };


    UMAuthListener authListener1 = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if(data!=null){
                String  unionid = data.get("unionid");
                String  nickName = data.get("screen_name");
                String  userHead = data.get("iconurl");
                WeiLogin(unionid,nickName,userHead);
            }


        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(mContext, "失败：" + t.getMessage(),                                  Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
        /*if(requestCode==1009){
            if("".equals(PreferManager.getUserPhone())){
                dialog=new AddFridentDialog(this);
                dialog.setMessage("请绑定手机号码");
                //dialog.setCancelable(false);
                dialog.setYesOnclickListener("确定", new AddFridentDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Intent intent=new Intent(LoginActivity.this,ResetPwdActivity.class);

                        intent.putExtra("BindPhone",101);
                        startActivityForResult(intent,1009);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }*/

    }


    public void submitTime(){
        if(!TextUtils.isEmpty(PreferManager.getUserId())){
            ProxyUtils.getHttpProxyNoDialog().queryuserloginlog(this, PreferManager.getUserId());
        }
    }

    protected void getStatus(JSONObject jsonObject){

    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(errorMessage.equals("无此群组信息")){

        }else {
            super.showErrorMessage(errorMessage);
        }

    }


    @Override
    public UserInfo getUserInfo(String s) {

       /* Cursor cursor = null;
        String name=null;
        String image = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(true, SQLHelper.TABLE_NAME, new String[]{SQLHelper.NAME,SQLHelper.IMAGE}, "id=?",
                    new String []{s}, null, null, null, null);
            while (cursor.moveToNext()) {
                name=cursor.getString(cursor.getColumnIndex(SQLHelper.NAME));
                image=cursor.getString(cursor.getColumnIndex(SQLHelper.IMAGE));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        if(name!=null&&image!=null){
            return new UserInfo(s,name,Uri.parse(image));
        }

*/
        if(!TextUtils.isEmpty(PreferManager.getUserId())&&s.equals(PreferManager.getUserId())){
            return new UserInfo(PreferManager.getUserId(),PreferManager.getUserName(),Uri.parse(PreferManager.getUserStar()));

        }

        return null;
    }

    @Override
    public Group getGroupInfo(String s) {
        //TODO
        ProxyUtils.getHttpProxy().groupinfo(LoginActivity.this, Integer.valueOf(s));

        return null;
    }
    private void getqueryuserinfo(QueryUserInfoBean data){
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(String.valueOf(data.getData().getUserId()),data.getData().getUserName(),Uri.parse(data.getData().getImage())));


    }
    private void getgroupinfo(GroupinfoBean data){
        RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(data.getData().getGroupId()),data.getData().getGroupName(),Uri.parse(data.getData().getGroupImage())));

    }

    @Override
    public GroupUserInfo getGroupUserInfo(String s, String s1) {
        //TODO
        ProxyUtils.getHttpProxy().groupinfo(LoginActivity.this, Integer.valueOf(s));

        return null;
    }
}
