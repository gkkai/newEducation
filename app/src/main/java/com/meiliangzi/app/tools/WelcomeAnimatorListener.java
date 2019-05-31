package com.meiliangzi.app.tools;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.google.gson.Gson;
import com.meiliangzi.app.receiver.TagAliasOperatorHelper;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.WelcomeActivity;
import com.meiliangzi.app.ui.view.Academy.NewLoginActivity;
import com.meiliangzi.app.ui.view.Academy.bean.NewBaseBean;
import com.meiliangzi.app.ui.view.Academy.bean.UserInfoBean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.meiliangzi.app.config.Constant.LOGINID;


public class WelcomeAnimatorListener implements AnimationListener {
    private WelcomeActivity welcomeActivity;

    public WelcomeAnimatorListener(WelcomeActivity welcomeActivity) {
        this.welcomeActivity = welcomeActivity;
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(TextUtils.isEmpty(NewPreferManager.getPasswd()) ){

                //TODO 进行登录
                IntentUtils.startAty(welcomeActivity, NewLoginActivity.class);
                welcomeActivity.finish();


        }else {


            if(!TextUtils.isEmpty(NewPreferManager.getWorkNumber())){

            //Todo 工号登录
                login(NewPreferManager.getWorkNumber(),NewPreferManager.getPasswd(),"2");

            }else if(!TextUtils.isEmpty(NewPreferManager.getPhone())){
               //TODO
                login(NewPreferManager.getPhone(),NewPreferManager.getPasswd(),"1");

            }else {
                //TODO 进行登录
                IntentUtils.startAty(welcomeActivity, NewLoginActivity.class);
                welcomeActivity.finish();

            }

        }


    }
    public void login(String loginName,String password,String type){

        NewPreferManager.savePasswd(password);
        Map<String,String> map=new HashMap();
        map.put("loginName",loginName);
        map.put("passwd",md5(password));
        map.put("type",type);
        OkhttpUtils.getInstance(welcomeActivity).dologin("organizationService/userAccount/userLogin", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {
                IntentUtils.startAty(welcomeActivity,NewLoginActivity.class);
                welcomeActivity.finish();

            }
            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final NewBaseBean bean=gson.fromJson(json,NewBaseBean.class);
                if("1".equals(bean.getCode())){
                    welcomeActivity.runOnUiThread(new Runnable() {
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

        OkhttpUtils.getInstance(welcomeActivity).doPost("academyService/detail/loginScore", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(final String json) {

            }
        });
    }

    private void getlsit(String userId){
        Map<String,String> maps=new HashMap<>();
        maps.put("userId", userId);

        OkhttpUtils.getInstance(welcomeActivity).getList("academyService/rule/lists", maps, new OkhttpUtils.onCallBack() {
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
        OkhttpUtils.getInstance(welcomeActivity).getList("academyService/userInfo/findByUserInfo", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            IntentUtils.startAty(welcomeActivity,NewLoginActivity.class);
                welcomeActivity.finish();
            }
            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final UserInfoBean user=gson.fromJson(json,UserInfoBean.class);
                if("1".equals(user.getCode())){
                    welcomeActivity. runOnUiThread(new Runnable() {
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
                    IntentUtils.startAty(welcomeActivity, MainActivity.class);
                    welcomeActivity.finish();
                    welcomeActivity.runOnUiThread(new Runnable() {
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
                            TagAliasOperatorHelper.getInstance().handleAction(welcomeActivity,sequence, tagAliasBean);

                        }
                    });



                }


            }
        });


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


    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
