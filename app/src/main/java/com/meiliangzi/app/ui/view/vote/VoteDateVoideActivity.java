package com.meiliangzi.app.ui.view.vote;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.VoteSubvotelistBean;
import com.meiliangzi.app.model.bean.VoteUsersubvoteBean;
import com.meiliangzi.app.model.bean.VpteSubvoteinfoBean;
import com.meiliangzi.app.tools.MyPlayUtil;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.train.TrainClassStudyActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import xiaobo.com.video.CustomView.MyVideoPlayerStandard;
import xiaobo.com.video.UserAction;
import xiaobo.com.video.UserActionStandard;
import xiaobo.com.video.VideoPlayer;
import xiaobo.com.video.VideoPlayerStandard;

public class VoteDateVoideActivity extends BaseActivity {
    Bitmap bitmap = null;
    @BindView(R.id.jc_video)
    MyPlayUtil play;
    @BindView(R.id.gradview)
    GridView gradview;
    @BindView(R.id.rl_blow)
    RelativeLayout rl_blow;
    @BindView(R.id.rl_voidlist)
    RelativeLayout rl_voidlist;
    @BindView(R.id.text_vote_num)
    TextView text_vote_num;
    @BindView(R.id.text_piao)
    TextView text_piao;
    @BindView(R.id.text_vote)
    TextView text_vote;
    @BindView(R.id.text_blow_num)
    TextView text_blow_num;
    @BindView(R.id.text_blow_vote)
    TextView text_blow_vote;
    @BindView(R.id.text_vote_title)
    TextView text_vote_title;
    BaseVoteAdapter<String> voteAdapter;
    //List<String> urls=new ArrayList<>();
    private Handler handler;
private int id;
    private int isvote;
    static MediaMetadataRetriever mmr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_vote_date_voide);
    }

    @Override
    protected void findWidgets() {
//        mmr = new MediaMetadataRetriever();
//
        //play.thumbImageView.setScaleType(ImageView.ScaleType.CENTER);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        play.thumbImageView.setImageBitmap(bitmap);


                }

            }
        };
        isvote=getIntent().getIntExtra("isvote",0000000);
        id=getIntent().getIntExtra("id",0000000);
        voteAdapter = new BaseVoteAdapter<String>(this, gradview, R.layout.item_vote_vote_list) {
            @Override
            public void convert(BaseViewHolder helper, final String item) {

                helper.setImageByUrl(R.id.image_, item, R.mipmap.votebackgroud, R.mipmap.votebackgroud);

            }

        };
        gradview.setAdapter(voteAdapter);
        gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoPlayer.releaseAllVideos();
                if (VideoPlayer.backPress()) {
                    //return;
                }
                if(voteAdapter.getmDatas().size()>=2){
                    play.setUp(voteAdapter.getmDatas().get(position)
                            , VideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

                    //ImageLoader.getInstance().displayImage(urls.get(position), play.thumbImageView, MyApplication.getSimpleOptions(0, 0));
                    /*mmr.setDataSource(getApplicationContext(), Uri.parse(urls.get(position-1)));
                    Bitmap bitmap = mmr.getFrameAtTime();//获取第一帧图片
                    play.thumbImageView.setImageBitmap(bitmap);*/
                    //ImageLoader.getInstance().displayImage(urls.get(position), play.thumbImageView, MyApplication.getSimpleOptions(0, 0));
                    //play.thumbImageView.setImageBitmap(getBitmapFormUrl(voteAdapter.getmDatas().get(position)));
                    getBitmapFormUrl(voteAdapter.getmDatas().get(position));
                    VideoPlayer.setJcUserAction(new MyUserActionStandard());
                }


            }
        });


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
       // mmr.release();//释放资源
        super.onBackPressed();
    }
    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
        //ProxyUtils.getHttpProxy().subvoteinfo(this,id,Integer.valueOf(PreferManager.getUserId()));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mmr.release();//释放资源
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProxyUtils.getHttpProxy().subvoteinfo(this,id,Integer.valueOf(PreferManager.getUserId()));

    }

    private void getsubvoteinfo(VpteSubvoteinfoBean data){
        //text_blow_vote.setEnabled(true);
        //urls.clear();
        text_blow_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_blow_vote.setEnabled(false);
                ProxyUtils.getHttpProxy().usersubvote(VoteDateVoideActivity.this,id,Integer.valueOf(PreferManager.getUserId()));

            }
        });
        text_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_blow_vote.setEnabled(false);
                ProxyUtils.getHttpProxy().usersubvote(VoteDateVoideActivity.this,id,Integer.valueOf(PreferManager.getUserId()));

            }
        });
        if(data.getData()!=null){
            text_vote_title.setText(data.getData().getTitle());

            /*if(data.getData().getVideoAddress().size()>=2){
                isShwoView(false);
                rl_voidlist.setVisibility(View.VISIBLE);
                rl_blow.setVisibility(View.VISIBLE);
                //urls=data.getData().getVideoAddress();
                voteAdapter.setDatas(data.getData().getVideoAddress());
                play.setUp(data.getData().getVideoAddress().get(0)
                        , VideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
                getBitmapFormUrl(data.getData().getVideoAddress().get(0));
                //play.thumbImageView.setImageBitmap(getBitmapFormUrl(data.getData().getVideoAddress().get(0)));
                //ImageLoader.getInstance().displayImage(data.getData().getVideoAddress().get(0), play.thumbImageView, MyApplication.getSimpleOptions(0, 0));
                VideoPlayer.setJcUserAction(new MyUserActionStandard());

            }else {
                isShwoView(true);
                rl_voidlist.setVisibility(View.GONE);
                rl_blow.setVisibility(View.GONE);
            }*/
            isShwoView(false);
            rl_voidlist.setVisibility(View.VISIBLE);
            rl_blow.setVisibility(View.VISIBLE);
            //urls=data.getData().getVideoAddress();
            voteAdapter.setDatas(data.getData().getVideoAddress());
            play.setUp(data.getData().getVideoAddress().get(0)
                    , VideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
            getBitmapFormUrl(data.getData().getVideoAddress().get(0));
            //play.thumbImageView.setImageBitmap(getBitmapFormUrl(data.getData().getVideoAddress().get(0)));
            //ImageLoader.getInstance().displayImage(data.getData().getVideoAddress().get(0), play.thumbImageView, MyApplication.getSimpleOptions(0, 0));
            VideoPlayer.setJcUserAction(new MyUserActionStandard());
            if(2==isvote){
                if(data.getData().getIsVote()==2){
                    //TODO 能投票
                    text_blow_vote.setText("投票");
                    text_blow_vote.setBackground(getResources().getDrawable(R.mipmap.votestartlong));
                    text_blow_vote.setEnabled(true);
                    text_vote.setText("投票");
                    text_vote.setBackground(getResources().getDrawable(R.mipmap.votestart));
                    text_vote.setEnabled(true);

                }else {
                    // TODO 不能投票
                    text_blow_vote.setText("已投");
                    text_blow_vote.setBackground(getResources().getDrawable(R.mipmap.voterendlong));
                    text_blow_vote.setEnabled(false);

                    text_vote.setText("已投");
                    text_vote.setBackground(getResources().getDrawable(R.mipmap.voteend));
                    text_vote.setEnabled(false);
                }
            }else {
                text_vote.setText("投票");
                text_vote.setBackground(getResources().getDrawable(R.mipmap.voteend));
                text_vote.setEnabled(false);
                // TODO 不能投票
                text_blow_vote.setText("投票");
                text_blow_vote.setBackground(getResources().getDrawable(R.mipmap.voterendlong));
                text_blow_vote.setEnabled(false);

            }

            text_blow_num.setText(data.getData().getUserVoteLogNumber()+"");
            text_vote_num.setText(data.getData().getUserVoteLogNumber()+"");
            }
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
    public void isShwoView(boolean isshow){
        if(isshow){

            text_vote.setVisibility(View.VISIBLE);
            text_piao.setVisibility(View.VISIBLE);
            text_vote_num.setVisibility(View.VISIBLE);
        }else {
            text_vote.setVisibility(View.GONE);
            text_piao.setVisibility(View.GONE);
            text_vote_num.setVisibility(View.GONE);
        }

    }
    private void getusersubvote(VoteUsersubvoteBean data){
        ProxyUtils.getHttpProxy().subvoteinfo(this,id,Integer.valueOf(PreferManager.getUserId()));

    }
    public  void getBitmapFormUrl(final String url) {
        //Bitmap bitmap = null;
        final MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Bitmap bitmap = null;
                try {
                    if (Build.VERSION.SDK_INT >= 14) {
                        retriever.setDataSource(url, new HashMap<String, String>());
                    } else {
                        retriever.setDataSource(url);
                    }
        /*getFrameAtTime()--->在setDataSource()之后调用此方法。 如果可能，该方法在任何时间位置找到代表性的帧，
         并将其作为位图返回。这对于生成输入数据源的缩略图很有用。**/
                    bitmap = retriever.getFrameAtTime();
                    Message message = new Message();

                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        retriever.release();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        //mmr.release();
       // return bitmap;
    }
}
