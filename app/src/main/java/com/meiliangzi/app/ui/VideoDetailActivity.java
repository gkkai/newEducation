package com.meiliangzi.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.db.bean.MessageBean;
import com.meiliangzi.app.db.manage.MessageManage;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.model.bean.CommonList;
import com.meiliangzi.app.model.bean.DetailDetail;
import com.meiliangzi.app.model.bean.QuestionList;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.ParamUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseBActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.dialog.RLAlertDialog;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import xiaobo.com.video.CustomView.MyVideoPlayerStandard;
import xiaobo.com.video.UserAction;
import xiaobo.com.video.UserActionStandard;
import xiaobo.com.video.VideoPlayer;
import xiaobo.com.video.VideoPlayerStandard;

import static com.meiliangzi.app.ui.MainActivity.KEY_MESSAGE;
import static com.meiliangzi.app.ui.MainActivity.MESSAGE_RECEIVED_ACTION;


/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/15
 * @description 视频详情
 **/

public class VideoDetailActivity extends BaseBActivity implements XListView.IXListViewListener{
    /* @BindView(R.id.ivPlay)
     ImageView ivPlay;
    @BindView(R.id.ivImg)
     ImageView ivImg;*/
    @BindView(R.id.llParise)
    LinearLayout llParise;
    @BindView(R.id.isSupport)
    CheckBox isSupport;
    private MessageReceiver mMessageReceiver;

    @BindView(R.id.tvCollect)
    TextView tvCollect;
    @BindView(R.id.tvAnswer)
    TextView tvAnswer;
    private String id;

    private String videoUrl;

    private int playState = 0;
    private DetailDetail detailDetail;
    private int value = 1;

    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.etContent)
    EditText etContent;

    private BaseQuickAdapter<CommonList.DataBean> adapter;

    @BindView(R.id.jc_video)
    MyVideoPlayerStandard myJCVideoPlayerStandard;
    private int pos = 0;
    private int page = 1;
    private MyDialog myDialog;
    private int parise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_video_detail);
    }

    @Override
    protected void findWidgets() {

        registerMessageReceiver();
    }

    @Override
    protected void initComponent() {
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        id = getIntent().getStringExtra("id");
        etContent.setInputType(InputType.TYPE_NULL);
        adapter = new BaseQuickAdapter<CommonList.DataBean>(VideoDetailActivity.this,listView,R.layout.item_common) {
            @Override
            public void convert(final BaseViewHolder helper, final CommonList.DataBean item) {
                CircleImageView ivImg = helper.getView(R.id.ivImg);
                helper.setImageByUrl(ivImg, item.getUserinfo().getAvatar(), R.mipmap.ic_default_star, R.mipmap.ic_default_star);
                helper.setText(R.id.tvName, item.getUserinfo().getNickname());
                helper.setText(R.id.tvTime, item.getCreate_time());
                helper.setText(R.id.tvContent, item.getContent());
                TextView tvParise = helper.getView(R.id.tvParise);
                if (item.getPraise() == 0) {
                    tvParise.setText("");
                } else {
                    tvParise.setVisibility(View.VISIBLE);
                    tvParise.setText("+" + item.getPraise());
                }
                if (item.getIs_praise() == 1) {
                    tvParise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_support_normal_), null, null, null);
                    tvParise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pos = helper.getPosition();
                            supportCommon(String.valueOf(item.getId()), String.valueOf(item.getUserinfo().getId()));
                        }
                    });
                    tvParise.setTextColor(Color.parseColor("#606060"));
                } else {
                    tvParise.setTextColor(getResources().getColor(R.color.colorRed));
                    tvParise.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_support_selected_), null, null, null);
                    tvParise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pos = helper.getPosition();
                            unSupportCommon(String.valueOf(item.getId()));
                        }
                    });
                }

            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        etContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = etContent.getInputType(); // backup the input type
                etContent.setInputType(InputType.TYPE_CLASS_TEXT); // disable soft input
                etContent.onTouchEvent(event); // call native handler
                etContent.setInputType(inType); // restore input type
                return true;
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
                    unSupportpraise(id);
                }
            }
        });

    }

    private void ispraise(String subjectId) {
        if (PreferManager.getUserId().isEmpty()){
            IntentUtils.startAty(VideoDetailActivity.this,LoginActivity.class);
            return;
        }
        ProxyUtils.getHttpProxy().ispraise(VideoDetailActivity.this, subjectId, PreferManager.getUserId());
    }
    protected void getParise(BaseBean baseBean) {
        ToastUtils.custom("点赞成功");
        detailDetail.getData().setPraise(detailDetail.getData().getPraise()+1);
        isSupport.setText("+" + detailDetail.getData().getPraise());
        isSupport.setChecked(true);
    }
    @Override
    protected void onPause() {
        MainActivity.isForeground = false;
        super.onPause();
        VideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }



    @Override
    protected void asyncRetrive() {

    }

    @Override
    protected void onResume() {
        MainActivity.isForeground = true;
        super.onResume();

        getCommon();
        getData(id);
    }

    private void getData(String id) {
        ProxyUtils.getHttpProxy().videoinfo(VideoDetailActivity.this, id, PreferManager.getUserId());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void getResult(DetailDetail detailDetail) {
        this.detailDetail = detailDetail;
        if (detailDetail.getData().getIs_study() == 0) {
            tvAnswer.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_answer_normal_), null, null, null);
            tvAnswer.setTextColor(Color.parseColor("#606060"));
        } else {
            tvAnswer.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_answer_selected_), null, null, null);
            tvAnswer.setTextColor(getResources().getColor(R.color.colorRed));
//            tvAnswer.setEnabled(false);
        }

        if (detailDetail.getData().getIs_collect() == 1) {
            value = 0;
            tvCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_collect_selected), null, null, null);
            tvCollect.setTextColor(getResources().getColor(R.color.colorRed));
        } else {
            value = 1;
            tvCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_collect_normal), null, null, null);
            tvCollect.setTextColor(Color.parseColor("#606060"));
        }

        videoUrl = detailDetail.getData().getContent();

        myJCVideoPlayerStandard.setUp(videoUrl
                , VideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        ImageLoader.getInstance().displayImage(detailDetail.getData().getImg(), myJCVideoPlayerStandard.thumbImageView, MyApplication.getSimpleOptions(0, 0));
        VideoPlayer.setJcUserAction(new MyUserActionStandard());
        parise = detailDetail.getData().getPraise();
        isSupport.setText("+" + parise);

        if (detailDetail.getData().getIs_praise() == 1) {
            isSupport.setChecked(false);
        } else {
            isSupport.setChecked(true);
        }
    }

    @OnClick({ R.id.tvCollect, R.id.tvAnswer,R.id.tvSend})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.ivPlay:
//                mnViderPlayer.playVideo(videoUrl,"");
//                break;
            case R.id.tvCollect:
                if (value == 0) {
                    value = 1;
                } else {
                    value = 0;
                }
                collect(id, String.valueOf(value));
                break;
            case R.id.tvAnswer:
                if (PreferManager.getUserId().isEmpty()){
                    IntentUtils.startAty(VideoDetailActivity.this,LoginActivity.class);
                    return;
                }

                if (detailDetail.getData().getIs_study()==0) {
                    showSureAnswer();
                    //ProxyUtils.getHttpProxyNoDialog().updatestudystatus(VideoDetailActivity.this,PreferManager.getUserId(),id);
                } else if (detailDetail.getData().getIs_study()==2){
                    showMiddletwo();
                }else if(detailDetail.getData().getIs_study()==3){
                    showMiddlethree();
                }else {
                    showMiddle();
                }
                break;
            case R.id.tvSend:
                try {
                    RuleCheckUtils.checkEmpty(etContent.getText().toString(),"请输入内容");
                    send(etContent.getText().toString());
                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }
                break;
        }
    }
    private  void showSureAnswer() {
        myDialog = new MyDialog(this);
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                //取得试卷信息
                ProxyUtils.getHttpProxy().gettestpaper(VideoDetailActivity.this, id);
                //ProxyUtils.getHttpProxyNoDialog().updatestudystatus(VideoDetailActivity.this, PreferManager.getUserId(),id);
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
        ProxyUtils.getHttpProxyNoDialog().updatestudystatus(VideoDetailActivity.this, PreferManager.getUserId(),id);
    }
    protected void getAnswerStatus(BaseBean baseBean){
        ParamUtils paramUtils = ParamUtils.build();
        paramUtils.put("answer_tiem", detailDetail.getData().getAnswer_tiem());
        paramUtils.put("id", id);
        goAnswer(paramUtils);
    }

    public void showMiddletwo() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("此课程为党员课程");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                //ProxyUtils.getHttpProxyNoDialog().updatestudystatus(VideoDetailActivity.this,PreferManager.getUserId(),id);
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
       /* final MiddleView middleView = new MiddleView(VideoDetailActivity.this, R.layout.panel_answer_two);
        TextView tvConfirm = (TextView) middleView.getView().findViewById(R.id.tvConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleView.dismissMiddleView();
            }
        });
        middleView.showModdleView(true);*/
    }

    private void goAnswer(ParamUtils paramUtils) {
        if (PreferManager.getCompany().isEmpty()|| PreferManager.getUserName().isEmpty()|| PreferManager.getWorkNum().isEmpty()){
            //完善资料
            new RLAlertDialog(VideoDetailActivity.this, "温馨提示",
                    "您还没有完善个人信息", "取消", "确认", new RLAlertDialog.Listener() {

                @Override
                public void onRightClick() {
                    IntentUtils.startAty(VideoDetailActivity.this, PersonCenterActivity.class);
                }

                @Override
                public void onLeftClick() {
                    finish();
                }
            }).show();

            return;
        }
        if (!PreferManager.isPartment()&&detailDetail.getData().getContent_type()==2){
            showPartmentMiddle();
            return;
        }
        IntentUtils.startAtyForResult(VideoDetailActivity.this, AnswerActivity.class, 1002, paramUtils.create());
    }


    public void collect(String subjectId, String value) {
        if (TextUtils.isEmpty(PreferManager.getUserId())) {
            IntentUtils.startAtyForResult(VideoDetailActivity.this, LoginActivity.class, 1001);
            return;
        }
        ProxyUtils.getHttpProxy().usercollect(VideoDetailActivity.this, PreferManager.getUserId(), subjectId, value);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void getResult(BaseBean baseBean) {
        if (value == 1) {
            ToastUtils.custom("取消收藏");
            tvCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_collect_normal), null, null, null);
            tvCollect.setTextColor(Color.parseColor("#606060"));

        } else {

            ToastUtils.custom("收藏成功");
            tvCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_collect_selected), null, null, null);
            tvCollect.setTextColor(getResources().getColor(R.color.colorRed));

        }
    }


    public void showMiddle() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("您已经答过该题");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                //ProxyUtils.getHttpProxyNoDialog().updatestudystatus(VideoDetailActivity.this,PreferManager.getUserId(),id);
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
    public void showMiddlethree() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("该课程不能答题");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                //ProxyUtils.getHttpProxyNoDialog().updatestudystatus(VideoDetailActivity.this,PreferManager.getUserId(),id);
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

    public void showPartmentMiddle() {
        myDialog = new MyDialog(this);
        myDialog.setMessage("该课程为党员课程");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                //ProxyUtils.getHttpProxyNoDialog().updatestudystatus(VideoDetailActivity.this,PreferManager.getUserId(),id);
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
       /* final MiddleView middleView = new MiddleView(VideoDetailActivity.this, R.layout.panel_partment);
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
    public void onRefresh() {
        page = 1;
        getCommon();
    }

    @Override
    public void onLoadMore() {
        page++;
        getCommon();
    }

    class MyUserActionStandard implements UserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case UserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case UserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case UserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    getData(id);
                    break;
                case 1002:
                    tvAnswer.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_answer_selected_), null, null, null);
                    tvAnswer.setTextColor(getResources().getColor(R.color.colorRed));
                    detailDetail.getData().setIs_study(1);
                    break;
            }
        } /*else {
            this.finish();
        }*/
    }


    public void getCommon() {
        ProxyUtils.getHttpProxy().getallcomment(VideoDetailActivity.this, id, String.valueOf(Constant.PAGESIZE), String.valueOf(page), PreferManager.getUserId());
    }

    protected void getData(CommonList commonList) {
        if(page == 1){
            adapter.pullRefresh(commonList.getData());
        }else {
            adapter.pullLoad(commonList.getData());
        }
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        if(page == 1){
            adapter.pullRefresh(new ArrayList<CommonList.DataBean>());
        }else {
            adapter.pullLoad(new ArrayList<CommonList.DataBean>());
        }
        ToastUtils.show(errorMessage);
    }

    public void send(String content) {
        if (TextUtils.isEmpty(PreferManager.getUserId())) {
            IntentUtils.startAtyForResult(VideoDetailActivity.this, LoginActivity.class, 1001);
            return;
        }
        ProxyUtils.getHttpProxy().publishcomment(VideoDetailActivity.this, PreferManager.getUserId(), id, "-1", content);
    }

    protected void getCommon(BaseBean baseBean) {
        getCommon();
        etContent.setText("");
    }


    public void supportCommon(String praiseId, String praise_userid) {
        if (TextUtils.isEmpty(PreferManager.getUserId())) {
            IntentUtils.startAtyForResult(VideoDetailActivity.this, LoginActivity.class, 1001);
            return;
        }
        ProxyUtils.getHttpProxy().ispraiselog(VideoDetailActivity.this, praiseId, PreferManager.getUserId(), praise_userid);
    }

    protected void getPariseResult(BaseBean baseBean) {
        adapter.getmDatas().get(pos).setIs_praise(0);
        adapter.getmDatas().get(pos).setPraise(adapter.getmDatas().get(pos).getPraise()+1);
        adapter.notifyDataSetChanged();
    }

    public void unSupportCommon(String praiseId) {
        if (TextUtils.isEmpty(PreferManager.getUserId())) {
            IntentUtils.startAtyForResult(VideoDetailActivity.this, LoginActivity.class, 1001);
            return;
        }
        ProxyUtils.getHttpProxy().undopraiselog(VideoDetailActivity.this,praiseId, PreferManager.getUserId());
    }
    public void unSupportpraise(String praiseId) {
        if (TextUtils.isEmpty(PreferManager.getUserId())) {
            IntentUtils.startAtyForResult(VideoDetailActivity.this, LoginActivity.class, 1001);
            return;
        }
        ProxyUtils.getHttpProxy().undopraise(VideoDetailActivity.this,praiseId, PreferManager.getUserId());
        // ProxyUtils.getHttpProxy().undopraiselog(VideoDetailActivity.this,praiseId,PreferManager.getUserId());
    }
    protected void getUnParise(BaseBean baseBean){
        ToastUtils.custom("取消点赞");
        detailDetail.getData().setPraise(detailDetail.getData().getPraise()-1);
        if(detailDetail.getData().getPraise()==0){
            isSupport.setText("");
        }else {
            isSupport.setText("+" + detailDetail.getData().getPraise());
        }
        isSupport.setChecked(false);
    }
    protected void unParise(BaseBean baseBean) {

        if(detailDetail.getData().getPraise()==0){
            isSupport.setText("");
        }else {
            isSupport.setText("+" + detailDetail.getData().getPraise());
        }
        isSupport.setChecked(false);
        adapter.getmDatas().get(pos).setIs_praise(1);
        adapter.getmDatas().get(pos).setPraise(adapter.getmDatas().get(pos).getPraise()-1);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(page == 1){
            adapter.pullRefresh(new ArrayList<CommonList.DataBean>());
        }else {
            adapter.pullLoad(new ArrayList<CommonList.DataBean>());
        }
        ToastUtils.show(errorMessage);
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

}
