package com.meiliangzi.app.ui.view.Academy;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.bean.RuleListBean;
import com.meiliangzi.app.ui.view.Academy.bean.VideoBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zzhoujay.richtext.RichText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.jzvd.JZMediaInterface;
import cn.jzvd.JZMediaSystem;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import static com.meiliangzi.app.config.Constant.ChanYeXY;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN_CIRCLE;

public class VideoPlayActivity extends BaseActivity implements View.OnClickListener {
    private static final String LAST_PLAYED_TIME = "myJCVideoPlayerStandard";
    private String id;
    @BindView(R.id.video_Player)
    JZVideoPlayerStandard videoPlayer;
    @BindView(R.id.tx_title)
    TextView tx_title;
    @BindView(R.id.tv_departmentName)
    TextView tv_departmentName;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.image_back)
    ImageView image_back;
    @BindView(R.id.image_menu)
    ImageView image_menu;
    private int mLastPlayedTime;
    private Timer timer,timer1;
    long firsttime = System.currentTimeMillis();
    private String url;
    private String title;
    private Dialog dialog;
    private View inflate;
    private LinearLayout ll_weinxin;
    private LinearLayout ll_penyouquan;
    private TextView concle;
    private long time;
    private boolean isplay=false;
    private long timeFormat=0;
    private String WatchVideoId="402881e56a47e6c8016a47e9363c0002";
    private String  DurationWatchVideoId="402881e56a47e6c8016a47e9363c0004";
    private String videotime;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getStringExtra("id");
        videotime=getIntent().getStringExtra("videotime");
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

        onCreateView(R.layout.activity_video_play);
        if (this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            //界面横屏时需要进行的操作
            Log.i("界面","很平");
        }else {
            Log.i("界面","竖频");

        }


    }
    /**
     * 按16:9设置控件大小
     */
    private void setImageSize() {

        //todo 控件大小
        videoPlayer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) videoPlayer.getLayoutParams();
                //int width = ViewUtil.getScreenWidth(activity);// -DensityUtils.dp2px(context,14*2)    params.getMarginStart()
                Display display = getWindowManager().getDefaultDisplay();
                int width = display.getWidth();
                int h=(int)(width*9/16+0.5);//16:9
                params.height=h;
                videoPlayer.setLayoutParams(params);//将设置好的布局参数应用到控件中

                videoPlayer.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);//设置容器内播放器高


            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putInt(LAST_PLAYED_TIME, JCVideoPlayerStandard.getCurrentPositionWhenPlaying());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mLastPlayedTime = savedInstanceState.getInt(LAST_PLAYED_TIME);
    }

    @Override
    protected void findWidgets() {
          timer1=new Timer();
        timer=new Timer();
        String ruleslists= NewPreferManager.getRuleLists();
        image_back.setOnClickListener(this);
        image_menu.setOnClickListener(this);
        if(ruleslists.equals("")){
            time=30000;
        }else {
            Gson gson=new Gson();
            RuleListBean bean=   gson.fromJson(ruleslists,RuleListBean.class);
            for(int i=0;i<bean.getData().size();i++){
                if(WatchVideoId.equals(bean.getData().get(i).getId())){
                    time=Integer.valueOf(bean.getData().get(i).getConditions());
                }
            }
        }



    }

    @Override
    protected void initComponent() {
        url=ChanYeXY+"academyService/html/video.html?id="+id;
        Map<String,String> map=new HashMap<>();
        OkhttpUtils.getInstance(this).getList("academyService/video/findById/"+id, map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            public void onResponse(final String json) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson=new Gson();

                            VideoBean bean=gson.fromJson(json,VideoBean.class);
                            description=bean.getData().getContent();
                            title=bean.getData().getTitle();
                            initview(bean);



                        }catch (Exception e){
                            ToastUtils.show(e.getMessage());
                        }
                    }
                });



            }
        });





    }

    private void initview(VideoBean bean) {


        videoPlayer.setUp(bean.getData().getVideoPath()
                , JZVideoPlayerStandard.CURRENT_STATE_NORMAL,"");
        ImageLoader.getInstance().displayImage(bean.getData().getCoverImage(), videoPlayer.thumbImageView, MyApplication.getSimpleOptions(0, 0));

        videoPlayer.setJzUserAction(new MyUserActionStandard());
        videoPlayer.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        videoPlayer.setMediaInterface(new JZMediaSystem());
        videoPlayer.startButton.performClick();
        setImageSize();
        tx_title.setText(bean.getData().getTitle());
        tv_departmentName.setText(bean.getData().getDepartmentName());
        tv_time.setText(bean.getData().getCreateTime());
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        RichText.fromHtml(bean.getData().getContent()).into(tv_content);



    }

    @Override
    public void onDestroy() {
        //"activity销毁的时候释放资源，播放器停止播放";
        super.onDestroy();
        try {
            videoPlayer.releaseAllVideos();
        } catch (Exception e) {
        }
    }
    @Override
    public void onBackPressed() {
        if (videoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return false;
        }
        return false;
    }

    @Override
    public void onBackClick(View v) {
        super.onBackClick(v);
        onBack();
    }
/*判断应用是否在前台*/
    public static boolean isForeground(Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(!isForeground(this))
        {
            MyApplication.isActive = false;

            isplay=false;
        }
    }
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(!MyApplication.isActive)
        {
            MyApplication.isActive = true;
            isplay=false;
            /*一些处理，如弹出密码输入界面*/
        }
    }


    public void onBack() {
        timer.cancel();
        timer1.cancel();
        Map<String,String> maps=new HashMap<>();
        maps.put("userId",NewPreferManager.getId());
        maps.put("ruleId",DurationWatchVideoId);
        maps.put("time", timeFormat+"");
        maps.put("comprehensiveId",id);
        OkhttpUtils.getInstance(this).doPost("academyService/detail/videoLearningDurationScore", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(String json) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show("阅读视频"+timeFormat);
                    }
                });


            }
        });



        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_menu:
                //sharewein();
                shareurl();
                break;
            case R.id.image_back:
                onBack();
                break;
            case R.id.ll_weinxin:
                sharewein();
                dialog.dismiss();
                break;
            case R.id.ll_penyouquan:
                sharewcircle();
                dialog.dismiss();
                break;
            case R.id.concle:
                dialog.dismiss();
                break;
        }

    }
    public void shareurl(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
//填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.share_dialog, null);
        ll_weinxin=(LinearLayout) inflate.findViewById(R.id.ll_weinxin);
        ll_penyouquan=(LinearLayout) inflate.findViewById(R.id.ll_penyouquan);
        concle=(TextView) inflate.findViewById(R.id.concle);

        ll_weinxin.setOnClickListener(this);
        ll_penyouquan.setOnClickListener(this);
        concle.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框


    }
    private void  sharewein(){
        UMImage image = new UMImage(this, R.mipmap.log2);//资源文件

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(this)
                .setPlatform(WEIXIN)
                .withMedia(web)
                .share();

    }
    private void  sharewcircle(){
        UMImage image = new UMImage(this, R.mipmap.log2);//资源文件

        UMWeb web = new UMWeb(url);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(this)
                .setPlatform(WEIXIN_CIRCLE)
                .withMedia(web)
                .share();

    }
    class MyUserActionStandard implements JZUserAction {


        @Override
        public void onEvent(int type, Object url, int screen, Object... objects) {
            switch (type) {
                case JZUserAction.ON_CLICK_START_ICON:
                    //TODO 开始播放
                    isplay=true;
                    //TODO 视频逻辑处理

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Map<String,String> maps=new HashMap<>();
                            maps.put("userId", NewPreferManager.getId());

                            maps.put("ruleId",WatchVideoId);

                            maps.put("comprehensiveId",id);
                            OkhttpUtils.getInstance(getBaseContext()).doPost("academyService/detail/watchVideoScore", maps, new OkhttpUtils.onCallBack() {
                                @Override
                                public void onFaild(Exception e) {

                                }

                                @Override
                                public void onResponse(String json) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtils.show("阅读视频"+time);
                                            }
                                        });

                                }
                            });

                        }
                    },time*1000);
                    timer1.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (isplay){
                                if(timeFormat>=Integer.valueOf(videotime)){

                                }else {
                                    timeFormat++;
                                }
                                if(timeFormat==time){}

                                Log.i("VideoPlayActivity",timeFormat+"");

                            }


                        }
                    },0,1000);
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    //TODO  重播
                    isplay=true;
                    //timeFormat=0;
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_PAUSE:
                    //TODO 暂停
                    isplay=false;
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_CLICK_RESUME:
                    isplay=true;
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_AUTO_COMPLETE:
                    //TODO 结束播放
                    isplay=false;

                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_ENTER_FULLSCREEN:

                    videoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
                    //videoPlayer.autoFullscreen(1);
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_QUIT_FULLSCREEN:
                    //TODO 竖频

                    videoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JZUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }


    private class MyJZMediaSystem extends JZMediaInterface {
        @Override
        public void start() {
            Log.i("USER_EVENT", "1");
        }

        @Override
        public void prepare() {
            Log.i("USER_EVENT", "2");
        }

        @Override
        public void pause() {
            Log.i("USER_EVENT", "3");

        }

        @Override
        public boolean isPlaying() {
            Log.i("USER_EVENT", "4");
            return false;
        }

        @Override
        public void seekTo(long time) {
            Log.i("USER_EVENT", "5");

        }

        @Override
        public void release() {
            Log.i("USER_EVENT", "6");

        }

        @Override
        public long getCurrentPosition() {
            Log.i("USER_EVENT", "7");
            return 0;
        }

        @Override
        public long getDuration() {
            Log.i("USER_EVENT", "8");
            return 0;
        }

        @Override
        public void setSurface(Surface surface) {


        }

        @Override
        public void setVolume(float leftVolume, float rightVolume) {

        }
    }
}
