package com.meiliangzi.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.bean.FreebackBean;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.bean.BaseInfo;
import com.meiliangzi.app.widget.CircleImageView;
import com.zipow.videobox.FeedbackActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class FreeBackActivity  extends BaseActivity {


    @BindView(R.id.text_sumit)
    TextView text_sumit;
    @BindView(R.id.edit_wd_miaoshu)
    EditText edit_wd_miaoshu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_free_back2);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }
    @OnClick({ R.id.text_sumit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_sumit:
                try {
                    RuleCheckUtils.checkEmpty(edit_wd_miaoshu.getText().toString(), "请输入您的意见或者建议");
                    String ss=edit_wd_miaoshu.getText().toString();

                    //ProxyUtils.getHttpProxy().feedbackadd(this, Integer.valueOf(PreferManager.getUserId()), ss);
                    Map<String,String> maps=new HashMap<>();
                    maps.put("userId", NewPreferManager.getId());
                    maps.put("deliveryContent",ss);
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("deliveryContent",ss);
                    OkhttpUtils.getInstance(FreeBackActivity.this).postJson( NewPreferManager.getId(),jsonObject.toJSONString(),"academyService/feedBack/add",  new OkhttpUtils.onCallBack() {
                        @Override
                        public void onFaild(Exception e) {

                        }

                        @Override
                        public void onResponse(String json) {
                            Gson gson =new Gson();
                            //BaseInfo baseInfo =gson.fromJson(json, BaseInfo.class);
                            finish();



                        }
                    });
                }catch (Exception e){
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }


                break;

        }
    }
    protected  void  getfeedbackadd(FreebackBean bean){
        ToastUtils.show("提交完成");
        finish();

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }
}
