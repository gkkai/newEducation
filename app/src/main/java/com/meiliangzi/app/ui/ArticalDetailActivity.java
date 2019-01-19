package com.meiliangzi.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.bean.MessageBean;
import com.meiliangzi.app.db.manage.MessageManage;
import com.meiliangzi.app.model.bean.ArticalDetail;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.model.bean.QuestionList;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.ParamUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.dialog.RLAlertDialog;


import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.meiliangzi.app.ui.MainActivity.KEY_MESSAGE;
import static com.meiliangzi.app.ui.MainActivity.MESSAGE_RECEIVED_ACTION;


/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/15
 * @description 文章详情
 **/


public class ArticalDetailActivity extends BaseActivity {

    private MessageReceiver mMessageReceiver;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvSub)
    TextView tvSub;
    @BindView(R.id.ivImg)
    ImageView ivImg;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.isSupport)
    CheckBox isSupport;
    @BindView(R.id.cbCollecct)
    CheckBox cbCollecct;
    @BindView(R.id.cbAnswer)
    CheckBox cbAnswer;
    @BindView(R.id.llCollect)
    LinearLayout llCollect;
    @BindView(R.id.llParise)
    LinearLayout llParise;
    private String id;


    private String value = "0";
    private String parise;
    private int answer_tiem;
    private ArticalDetail articalDetail;
    private String from="";
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_artical_detail);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        id = getIntent().getStringExtra("id");
        from = getIntent().getStringExtra("from");

    }

    @OnClick({R.id.llCommon, R.id.llAnswer})
    public void onClilck(View view) {
        switch (view.getId()) {
            case R.id.llCommon:
                if (id != null) {
                    IntentUtils.startAtyWithSingleParam(ArticalDetailActivity.this, CommentListActivity.class, "id", id);
                }
                break;
            case R.id.llAnswer:
                if (PreferManager.getUserId().isEmpty()){
                    ToastUtils.custom("请先登录");
                    IntentUtils.startAty(ArticalDetailActivity.this,LoginActivity.class);
                    return;
                }
                if (id != null) {
                    if (articalDetail.getData().getIs_study()==0) {
                        showSureAnswer();
                    }else if (articalDetail.getData().getIs_study()==2){
                        showMiddletwo();
                    }else if(articalDetail.getData().getIs_study()==3){
                        showMiddlethree();
                    } else {
                        showMiddle();
                    }

                }

                break;
        }
    }

    private void showSureAnswer() {
        myDialog = new MyDialog(this);
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                //取得试卷信息
                ProxyUtils.getHttpProxy().gettestpaper(ArticalDetailActivity.this, id);

                //ProxyUtils.getHttpProxyNoDialog().updatestudystatus(ArticalDetailActivity.this, PreferManager.getUserId(),id);
                myDialog.dismiss();
            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
               // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    protected void getData(QuestionList questionList ){
        ProxyUtils.getHttpProxyNoDialog().updatestudystatus(ArticalDetailActivity.this, PreferManager.getUserId(),id);
    }
    protected void getAnswerStatus(BaseBean baseBean){
        articalDetail.getData().setIs_study(2);
        cbAnswer.setChecked(true);
        ParamUtils paramUtils = ParamUtils.build();
        paramUtils.put("answer_tiem", answer_tiem);
        paramUtils.put("id", id);
        goAnswer(paramUtils);
    }

    private void goAnswer(ParamUtils paramUtils) {
        if (PreferManager.getCompany().isEmpty()|| PreferManager.getUserName().isEmpty()|| PreferManager.getWorkNum().isEmpty()){
            //完善资料
            new RLAlertDialog(ArticalDetailActivity.this, "温馨提示",
                    "您还没有完善个人信息", "取消", "确认", new RLAlertDialog.Listener() {

                @Override
                public void onRightClick() {
                    IntentUtils.startAty(ArticalDetailActivity.this, PersonCenterActivity.class);
                }

                @Override
                public void onLeftClick() {
                    finish();
                }
            }).show();

            return;
        }
        if (!PreferManager.isPartment()&&articalDetail.getData().getContent_type()==2){
            showPartmentMiddle();
            return;
        }
        paramUtils.put("checkAnswer",false);
        IntentUtils.startAtyForResult(ArticalDetailActivity.this, AnswerActivity.class, 1002, paramUtils.create());
    }

    public void showPartmentMiddle() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("该课程为党员课程");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                // ProxyUtils.getHttpProxyNoDialog().updatestudystatus(ArticalDetailActivity.this,PreferManager.getUserId(),id);
                myDialog.dismiss();
            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                myDialog.dismiss();
            }
        });
        myDialog.show();
       /* final MiddleView middleView = new MiddleView(ArticalDetailActivity.this, R.layout.panel_partment);
        TextView tvConfirm = (TextView) middleView.getView().findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
            }
        });
        middleView.showModdleView(true);*/
    }


    @Override
    protected void initListener() {
        llCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbCollecct.isChecked()) {
                    value = "1";
                } else {
                    value = "0";
                }
                if (id != null) {
                    collect(id, value);
                }
            }
        });
        llParise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSupport.isChecked()) {
                    if (id != null) {
                        ispraise(id);
                    }
                }else {
                    unSupportCommon(id);
                }
            }
        });

    }

    @Override
    protected void asyncRetrive() {
//        if (TextUtils.isEmpty(PreferManager.getUserId())) {
//            IntentUtils.startAtyForResult(ArticalDetailActivity.this, LoginActivity.class, 1001);
//            return;
//        }

    }

    @Override
    protected void onResume() {
        MainActivity.isForeground = true;
        super.onResume();
        getData(id);
    }

    public void getData(String id) {
        if (id != null) {
            ProxyUtils.getHttpProxy().articleinfo(ArticalDetailActivity.this, PreferManager.getUserId(), id);
        } else {
            cbCollecct.setEnabled(false);
            cbAnswer.setEnabled(false);
            ToastUtils.custom("暂无详情");
        }
    }


    protected void getData(ArticalDetail articalDetail) {
        this.articalDetail = articalDetail;
        answer_tiem = articalDetail.getData().getAnswer_tiem();
        tvTitle.setText(articalDetail.getData().getName());
        tvTime.setText(articalDetail.getData().getCreateTime());
        if (TextUtils.isEmpty(articalDetail.getData().getImg())) {
            ivImg.setVisibility(View.GONE);
        } else {
            ImageLoader.getInstance().displayImage(articalDetail.getData().getImg(), ivImg, MyApplication.getSimpleOptions(0, 0));
        }
        tvContent.setText(articalDetail.getData().getContent());
        parise = articalDetail.getData().getPraise();
        isSupport.setText("+" + parise);
        if (articalDetail.getData().getIs_collect() == 1) {
            cbCollecct.setChecked(true);
        } else {
            cbCollecct.setChecked(false);
        }
        if (articalDetail.getData().getIs_praise() == 1) {
            isSupport.setChecked(false);
        } else {
            isSupport.setChecked(true);
        }
        if (articalDetail.getData().getContent_type()==1){
            tvSub.setText("陕煤神南产业-基础教程");
        }else {
            tvSub.setText("陕煤神南产业-党员教育");
        }

        cbAnswer.setChecked(articalDetail.getData().getIs_study()==1);



    }

    public void collect(String subjectId, String value) {
        if (PreferManager.getUserId().isEmpty()){
            IntentUtils.startAty(ArticalDetailActivity.this,LoginActivity.class);
            return;
        }
        ProxyUtils.getHttpProxy().usercollect(ArticalDetailActivity.this, PreferManager.getUserId(), subjectId, value);
    }

    protected void getResult(BaseBean baseBean) {
        if (value.equals("0")) {
            cbCollecct.setChecked(true);
            ToastUtils.custom("收藏成功");
        } else {
            cbCollecct.setChecked(false);
            ToastUtils.custom("取消收藏成功");
        }
    }


    public void ispraise(String subjectId) {
        if (PreferManager.getUserId().isEmpty()){
            IntentUtils.startAty(ArticalDetailActivity.this,LoginActivity.class);
            return;
        }
        ProxyUtils.getHttpProxy().ispraise(ArticalDetailActivity.this, subjectId, PreferManager.getUserId());
    }

    protected void getParise(BaseBean baseBean) {
        ToastUtils.custom("点赞成功");
        articalDetail.getData().setPraise(String.valueOf(Integer.parseInt(articalDetail.getData().getPraise())+1));
        isSupport.setText("+" + articalDetail.getData().getPraise());
        isSupport.setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    getData(id);
                    break;
                case 1002:
                    cbAnswer.setChecked(true);
                    break;
            }
        }
    }

    public void showMiddle() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("您已经答过该题");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
               // ProxyUtils.getHttpProxyNoDialog().updatestudystatus(ArticalDetailActivity.this,PreferManager.getUserId(),id);
                myDialog.dismiss();
            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                //TODO 取得试卷信息
                ProxyUtils.getHttpProxy().gettestpaper(ArticalDetailActivity.this, id);

                myDialog.dismiss();
            }
        });
        myDialog.show();
       /* final MiddleView middleView = new MiddleView(ArticalDetailActivity.this, R.layout.panel_answer_);
        TextView tvConfirm = (TextView) middleView.getView().findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
            }
        });
        middleView.showModdleView(true);*/
    }
    public void showMiddlethree() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("该课程不能答题");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                // ProxyUtils.getHttpProxyNoDialog().updatestudystatus(ArticalDetailActivity.this,PreferManager.getUserId(),id);
                myDialog.dismiss();
            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    public void showMiddletwo() {
       /* final MiddleView middleView = new MiddleView(ArticalDetailActivity.this, R.layout.panel_answer_two);
        TextView tvConfirm = (TextView) middleView.getView().findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
            }
        });
        middleView.showModdleView(true);*/
        myDialog = new MyDialog(this);
        myDialog.setMessage("此课程为党员课程");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                // ProxyUtils.getHttpProxyNoDialog().updatestudystatus(ArticalDetailActivity.this,PreferManager.getUserId(),id);
                finish();
                myDialog.dismiss();
            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }


    public void unSupportCommon(String categoryId) {
        if (PreferManager.getUserId().isEmpty()){
            IntentUtils.startAty(ArticalDetailActivity.this,LoginActivity.class);
            return;
        }
        ProxyUtils.getHttpProxy().undopraise(ArticalDetailActivity.this,categoryId, PreferManager.getUserId());
    }

    protected void getUnParise(BaseBean baseBean){
        ToastUtils.custom("取消点赞");
        articalDetail.getData().setPraise(String.valueOf(Integer.parseInt(articalDetail.getData().getPraise())-1));
        if(articalDetail.getData().getPraise().equals("0")){
            isSupport.setText("");
        }else {
         isSupport.setText("+" + articalDetail.getData().getPraise());
        }
        isSupport.setChecked(false);
    }

    @Override
    protected void onPause() {
        MainActivity.isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(MainActivity.KEY_EXTRAS);
                    JSONObject extraJson = new JSONObject(extras);
                    Log.i("grage",messge);
                    Log.i("grage",extras);
                    MessageBean bean = new MessageBean();
                    bean.setKey(extraJson.getString("key"));
                    bean.setTitle(extraJson.getString("title"));
                    bean.setContent(messge);
                    bean.setImage(extraJson.getString("image"));
                    bean.setId(extraJson.getString("id")+"");
                    if (MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).addChannel(bean)) {
                        return;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
