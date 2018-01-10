package com.meiliangzi.app.ui.view.train;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.bean.MessageBean;
import com.meiliangzi.app.db.manage.MessageManage;
import com.meiliangzi.app.model.bean.QualityVideoCommentBean;
import com.meiliangzi.app.model.bean.QueryVideoCommentBean;
import com.meiliangzi.app.model.bean.VideoInfoBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.VideoDetailActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import butterknife.BindView;
import xiaobo.com.video.CustomView.MyVideoPlayerStandard;
import xiaobo.com.video.UserAction;
import xiaobo.com.video.UserActionStandard;
import xiaobo.com.video.VideoPlayer;
import xiaobo.com.video.VideoPlayerStandard;

import static com.meiliangzi.app.ui.MainActivity.KEY_MESSAGE;
import static com.meiliangzi.app.ui.MainActivity.MESSAGE_RECEIVED_ACTION;

/**
 * 课程学习
 */

public class TrainClassStudyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.listView_comment)
    MyGridView listView_comment;
    @BindView(R.id.myGridViewType)
    MyGridView gridView;
    private int videoId;
    private MessageReceiver mMessageReceiver;
    @BindView(R.id.jc_video)
    MyVideoPlayerStandard play;//视频播放控件
    TextView tx_video_name,tx_video_teacher,tx_video_learning_num,tx_video_use_person;
    BaseTrainAdapter<QueryVideoCommentBean.CommentDataBean> listadapter;
    BaseTrainAdapter<VideoInfoBean.DataBean.ChapterDataInfo> gradadapter;
    @BindView(R.id.rel_class_study)
    RelativeLayout rel_class_study;
    @BindView(R.id.id_view)
    View id_view;
    @BindView(R.id.tvSend)
    TextView tvSend;
    @BindView(R.id.etContent)
    EditText etContent;
    private int currentPage =1;
    private int pageSize=10;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        onCreateView(R.layout.activity_train_class_study);
    }


    @Override
    protected void findWidgets() {
        registerMessageReceiver();
        listadapter =new BaseTrainAdapter<QueryVideoCommentBean.CommentDataBean>(this,listView_comment,R.layout.item_comment_list) {

            @Override
            public void convert(BaseViewHolder helper, QueryVideoCommentBean.CommentDataBean item) {
                //helper.setImageByUrl(R.id.image_comment_query,item.get);
                helper.setText(R.id.tx_comment_person,item.getComment_person());
                helper.setText(R.id.tx_comment_time,item.getComment_time());
                helper.setText(R.id.tx_comment_data,item.getComment());

            }
        };
        listView_comment.setAdapter(listadapter);
        gradadapter=new BaseTrainAdapter<VideoInfoBean.DataBean.ChapterDataInfo>(this,gridView,R.layout.item_train_video_chapter) {
            @Override
            public void convert(BaseViewHolder helper, VideoInfoBean.DataBean.ChapterDataInfo item) {
                helper.setImageByUrl(R.id.video_chapter_image,item.getChapter_pic(),R.mipmap.defaule,R.mipmap.defaule);
                helper.setText(R.id.video_chapter_name,item.getChapter_name());


            }
        };
        View headView =getLayoutInflater().inflate(R.layout.train_class_study, null, false);
        tx_video_name= (TextView) findViewById(R.id.tx_video_name);
        tx_video_teacher= (TextView) findViewById(R.id.tx_video_teacher);
        tx_video_learning_num= (TextView) findViewById(R.id.tx_video_learning_num);
        tx_video_use_person= (TextView) findViewById(R.id.tx_video_use_person);
        tvSend.setOnClickListener(this);

        gridView.setAdapter(gradadapter);
        //listView.addHeaderView(headView);


    }

    @Override
    protected void initComponent() {

    }
    @Override
    protected void onPause() {
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
    protected void onResume() {
        if(getIntent()!=null){
            videoId=getIntent().getIntExtra("videoId",0);
        }

        //TODO 视频学习详情
        ProxyUtils.getHttpProxy().qualityvideoinfo(this,videoId);

        super.onResume();


    }

    @Override
    protected void asyncRetrive() {
       /* if(getIntent()!=null){
            videoId=getIntent().getIntExtra("videoId",0);
        }

       //TODO 视频学习详情
        ProxyUtils.getHttpProxy().qualityvideoinfo(this,videoId);


        super.asyncRetrive();*/

    }
    //TODO 返回视频学习详情
    protected void  getqualityvideoinfo(VideoInfoBean data){
        if(data.getData().getVideo_chapter()!=null&&data.getData().getVideo_chapter().size()!=0){
            gradadapter.setDatas(data.getData().getVideo_chapter());
        }else {
            rel_class_study.setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) id_view.getLayoutParams();
            lp.addRule(RelativeLayout.BELOW,R.id.view);
            id_view.setLayoutParams(lp);

        }

        VideoInfoBean.DataBean   databean=(VideoInfoBean.DataBean)data.getData();
        play.setUp(databean.getVideo_address()
                , VideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        ImageLoader.getInstance().displayImage(databean.getImage(), play.thumbImageView, MyApplication.getSimpleOptions(0, 0));
        VideoPlayer.setJcUserAction(new MyUserActionStandard());
        tx_video_name.setText(databean.getVideo_name());
        tx_video_teacher.setText("老师："+databean.getVideo_teacher());
        tx_video_learning_num.setText("视频学习人数"+databean.getLearning_num());
        tx_video_use_person.setText("适用人员："+databean.getVideo_use_person());
        id=data.getData().getId();

//TODO 查询视频评论
        ProxyUtils.getHttpProxy().queryvideocomment(this,data.getData().getId(),currentPage,pageSize);

    }
    //TODO 返回视频评论列表
    protected void  getqueryvideocomment(final QueryVideoCommentBean data){
      listadapter.setDatas(data.getData());


    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSend:
                try {
                    RuleCheckUtils.checkEmpty(etContent.getText().toString(), "请先输入内容");
                 String   commentPerson= PreferManager.getUserName();
                    String   commentContent=etContent.getText().toString();
//TODO 发表评论

                    ProxyUtils.getHttpProxy().qualityvideocomment(this,videoId,commentPerson,commentContent);
                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }

                break;
        }

    }
    private void getqualityvideocomment(QualityVideoCommentBean bean){
        ToastUtils.show("评论发表成功");
        if(getIntent()!=null){
            videoId=getIntent().getIntExtra("videoId",0);
        }

        //TODO 视频学习详情
        ProxyUtils.getHttpProxy().qualityvideoinfo(this,videoId);



    }


    public  class  MyUserActionStandard implements UserActionStandard {

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
