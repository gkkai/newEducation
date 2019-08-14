package com.meiliangzi.app.ui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.db.bean.MainBean;
import com.meiliangzi.app.db.bean.MessageBean;
import com.meiliangzi.app.db.manage.MessageManage;
import com.meiliangzi.app.model.bean.BannerBean;
import com.meiliangzi.app.model.bean.VersionUpdate;
import com.meiliangzi.app.receiver.CounterServer;
import com.meiliangzi.app.receiver.TagAliasOperatorHelper;
import com.meiliangzi.app.tools.FileUtil;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.AddFridentDialog;
import com.meiliangzi.app.ui.fragment.GroupFragment;
import com.meiliangzi.app.ui.fragment.IndexFragment;
import com.meiliangzi.app.ui.fragment.SheQunFragment;
import com.meiliangzi.app.ui.fragment.TrainFragment;
import com.meiliangzi.app.ui.listener.ClearCacheHandler;
import com.meiliangzi.app.ui.view.Academy.bean.UserInfoBean;
import com.meiliangzi.app.ui.view.Academy.fragment.AnswerFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.CIndexTFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.CVideoFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.ExaminationFragment;
import com.meiliangzi.app.ui.view.Academy.fragment.PersonalCenterFragment;
import com.meiliangzi.app.ui.view.MapNewActivity;
import com.meiliangzi.app.ui.view.ZoomActivity;
import com.meiliangzi.app.ui.view.checkSupervise.CheckSuperviseProjectListActivity;
import com.meiliangzi.app.ui.view.creativecommons.CommonsListActivity;
import com.meiliangzi.app.ui.view.sendcar.SendCarActivity;
import com.meiliangzi.app.ui.view.vote.VoteActivity;
import com.meiliangzi.app.widget.FragmentTabHost;
import com.meiliangzi.app.widget.ImageCycleView;
import com.meiliangzi.app.widget.MiddleView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import androidkun.com.versionupdatelibrary.entity.VersionUpdateConfig;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

import static com.meiliangzi.app.config.Constant.Examination;
import static com.meiliangzi.app.config.Constant.INDEX;
import static com.meiliangzi.app.config.Constant.TRAIN;
import static com.meiliangzi.app.config.Constant.VIDEO;
import static com.meiliangzi.app.receiver.TagAliasOperatorHelper.ACTION_DELETE;


/**
 * 主界面
 * @author xiaobo
 * @version 1.0
 * @date 2016/11/23 9:19
 */
public class MainActivity extends BaseActivity implements PermissionListener, View.OnClickListener {
    private SQLHelper helper = null;
    private ClearCacheHandler clearCacheHanler;
    private long exitTime = 0;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;
    private int versionCode = -1;
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private static final int REQUEST_CODE_SETTING = 101;
    private VersionUpdate versionUpdate;
    private String isPhone;
    SQLiteDatabase database = null;
    private ImageCycleView icvView;
    public  FragmentTabHost mTabHost;
    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.activity=this;

//        dialog=new AddFridentDialog(this);
//        dialog.setMessage("您好，请先绑定手机号码");
//        //dialog.setCancelable(false);
//        dialog.setYesOnclickListener("确定", new AddFridentDialog.onYesOnclickListener() {
//            @Override
//            public void onYesClick() {
//                Intent intent=new Intent(MainActivity.this,ResetPwdActivity.class);
//                isPhone="Yes";
//                intent.putExtra("BindPhone",101);
//                startActivityForResult(intent,1009);
//                dialog.dismiss();
//            }
//        });
//        dialog.setNoOnclickListener("取消", new AddFridentDialog.onNoOnclickListener() {
//            @Override
//            public void onNoClick() {
//
//                dialog.dismiss();
//            }
//        });
        initWindow();
        if(helper ==null){
            helper = new SQLHelper(this);
        }
        int[] id={R.mipmap.sendcar3,R.mipmap.check,R.mipmap.zoommeet1,R.mipmap.shequn_map1
        ,R.mipmap.knowledgeku,R.mipmap.votelog};
        String[] name={"派车系统","考核督办","视频会议","地图服务","知识库","投票管理"};
        for (int i=0;i<6;i++){
            MainBean mainBean =new MainBean();
            mainBean.setImgid(id[i]);
            mainBean.setName(name[i]);
           // mainBeanList.add(mainBean);

        }
        super.onCreateView(R.layout.activity_main);
        pos=getIntent().getIntExtra("pos",10);
        if(pos!=10){
            mTabHost.setCurrentTab(pos);
        }


    }
    public FragmentTabHost getmTabHost(){
        return  mTabHost;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }
    @Override
    protected void findWidgets() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

       // ProxyUtils.getHttpProxyNoDialog().indexslideshow(this);
        registerMessageReceiver();  // used for receive msg
        init();
        icvView = (ImageCycleView)findViewById(R.id.icvView);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_shequn_map:
                // TODO  查看更多
                Intent intentmap=new Intent(this, MapNewActivity.class);
                startActivity(intentmap);
                break;
            case R.id.rl_shequn_zoommeet:
                // TODO  视频会议
                Intent intentZoom=new Intent(this, ZoomActivity.class);
                startActivity(intentZoom);
                break;

            case R.id.rl_shequn_vote:
                // TODO 投票管理
                Intent intentvote=new Intent(this, VoteActivity.class);
                startActivity(intentvote);
                break;
            case R.id.rl_shequn_check:
                // TODO  考核督办
                Intent intentCheck=new Intent(this, CheckSuperviseProjectListActivity.class);
                startActivity(intentCheck);
                break;
            case R.id.rl_shequn_commmons:
                // TODO  知识共享
                Intent commmons=new Intent(this, CommonsListActivity.class);
                startActivity(commmons);
                break;
            case R.id.rl_shequn_sendcar:
                // TODO  知识共享
                Intent sendcar=new Intent(this, SendCarActivity.class);
               startActivity(sendcar);
                break;
        }

    }
    protected void indexslideshow(BannerBean bean) {
        List<BannerBean.DataBean> beans=new ArrayList<>();
        for (int i=0;i<2;i++){
            BannerBean.DataBean bean1=new BannerBean.DataBean();
            bean1.setImage(""+i);
            beans.add(bean1);
        }



        icvView.setImageResources(beans, imageCycleListener);
        ///loadRoundImage(this,5,);

    }


    ImageCycleView.ImageCycleViewListener imageCycleListener = new ImageCycleView.ImageCycleViewListener() {
        int id = 0;

        @Override
        public void displayImage(final String imageURL, ImageView imageView) {
            if(imageURL.equals("0")){
                DisplayImageOptions options;
                //显示图象选项
                options = new DisplayImageOptions.Builder()
                        // 设置图片下载期间显示的图片
                        .showStubImage(R.mipmap.index).showImageForEmptyUri(R.mipmap.index)// 设置图片Uri为空或是错误的时候显示的图片    
                        .showImageOnFail(R.mipmap.index)// 设置图片加载或解码过程中发生错误显示的图片   
                        .cacheInMemory() // 设置下载的图片是否缓存在内存中
                        .cacheOnDisc() // 设置下载的图片是否缓存在SD卡中    
                        .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片  
                        .build();


                ImageLoader.getInstance().displayImage("999", imageView,options);
            }else {
                DisplayImageOptions options;
                //显示图象选项
                options = new DisplayImageOptions.Builder()
                        // 设置图片下载期间显示的图片
                        .showStubImage(R.mipmap.index2).showImageForEmptyUri(R.mipmap.index2)// 设置图片Uri为空或是错误的时候显示的图片    
                        .showImageOnFail(R.mipmap.index2)// 设置图片加载或解码过程中发生错误显示的图片   
                        .cacheInMemory() // 设置下载的图片是否缓存在内存中
                        .cacheOnDisc() // 设置下载的图片是否缓存在SD卡中    
                        .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片  
                        .build();


                ImageLoader.getInstance().displayImage("999", imageView,options);
            }



//            imageView.setImageResource(R.mipmap.test_index_pic);
        }

        @Override
        public void onImageClick(int position, View imageView) {


        }
    };
    @Override
    protected void initListener() {


    }

    @Override
    protected void initComponent() {
        try {
            Intent intent = new Intent(MainActivity.this,CounterServer.class);
            startService(intent);
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        initTabHost();

        checkVersion();


    }


    private void checkVersion() {

        ProxyUtils.getHttpProxyNoDialog().queryversionnumber(MainActivity.this);

    }

    protected void getVersion(VersionUpdate versionUpdate) {
        this.versionUpdate = versionUpdate;
//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE};
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                    return;
//                }
//            }

        AndPermission.with(MainActivity.this)
                .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                .permission(Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(MainActivity.this, rationale).show();
                    }
                })
                .send();

    }


    private void initTabHost() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(INDEX).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_index, R.string.stringIndex)), CIndexTFragment.class, new Bundle());
        // TODO 培训
        mTabHost.addTab(mTabHost.newTabSpec(Constant.TRAIN).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_msg, R.string.stringTRAIN)), CVideoFragment.class, new Bundle());

        mTabHost.addTab(mTabHost.newTabSpec(Examination).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_video, R.string.stringAnswer)), AnswerFragment.class, new Bundle());

        mTabHost.addTab(mTabHost.newTabSpec(Constant.MINE).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_nime, R.string.stringMine)), PersonalCenterFragment.class, new Bundle());
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }




    private View createIndicatorView(int selectorRes, int finddesigner) {

        View view = getLayoutInflater().inflate(R.layout.item_maintab_navigation, null);
        ImageView iv = (ImageView) view.findViewById(R.id.ivTab);
        iv.setImageResource(selectorRes);
        TextView tvTabText = (TextView) view.findViewById(R.id.tvTabText);
        tvTabText.setText(finddesigner);
        return view;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
       /* setIntent(intent);
        setTab();*/
        int pos = intent.getIntExtra("from", 0);
    }

    private void setTab() {
        Intent intent = getIntent();
        int pos = intent.getIntExtra("jPfrom", 0);
        if(pos==3){
            getIntent().putExtra("jPfrom",0);
        }
    }

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            exitApp();
        }
    return false;
}

    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.custom("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            getApplication().onTerminate();
        }
    }
    public void submitTime(){
        if(!TextUtils.isEmpty(NewPreferManager.getId())){
            ProxyUtils.getHttpProxyNoDialog().queryuserloginlog(this, NewPreferManager.getId());
        }
    }
    protected void getStatus(JSONObject jsonObject){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1002:
                    GroupFragment firstFragment = (GroupFragment) getSupportFragmentManager().findFragmentByTag(VIDEO);
                    firstFragment.onActivityResult(requestCode, resultCode, data);
                    break;
                case 1003:
                    IndexFragment indexFragment = (IndexFragment) getSupportFragmentManager().findFragmentByTag(INDEX);
                    indexFragment.onActivityResult(requestCode, resultCode, data);
                    break;
                case 1004:
                    TrainFragment trainFragment = (TrainFragment) getSupportFragmentManager().findFragmentByTag(TRAIN);
                    trainFragment.onActivityResult(requestCode, resultCode, data);
                    break;

            }
        }else {
            if(requestCode==1009){
//                if("".equals(NewPreferManager.getPhone())){
//                    if(TextUtils.isEmpty(NewPreferManager.getId())){
//
//                    }else {
//                        dialog.show();
//                    }
//
//                }
            }



        }

    }
    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        isForeground = true;



//        if(TextUtils.isEmpty(PreferManager.getUserId())){
//
//        }else {
//            if("".equals(PreferManager.getUserPhone())&&MyApplication.ISShow){
//
//
//                dialog.show();
//            }
//        }
//        if(TextUtils.isEmpty(PreferManager.getUserId()) ){
//            if(TextUtils.isEmpty(PreferManager.getUserId())){
//                //TODO 进行登录
//              //  IntentUtils.startAty(welcomeActivity, LoginActivity.class);
//            }else if(!PreferManager.getIsComplete()){
//                //TODO 完善个人信息
//                IntentUtils.startAty(this, PersonCenterActivity.class);
//            }
//
//        }




        super.onResume();
    }



    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        //RongIM.getInstance().disconnect();
        super.onDestroy();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        if (Integer.parseInt(versionUpdate.getData().getAndroid_versionnumber()) > versionCode && versionCode != -1) {
            final MiddleView middleView = new MiddleView(MainActivity.this, R.layout.panel_update);
            TextView tvVersionName = (TextView) middleView.getView().findViewById(R.id.tvVersionName);
            TextView tvVersionDesc = (TextView) middleView.getView().findViewById(R.id.tvVersionDesc);
            TextView tvConfirm = (TextView) middleView.getView().findViewById(R.id.tvConfirm);
            TextView tvCancel = (TextView) middleView.getView().findViewById(R.id.tvCancel);
            tvVersionName.setText("版本："+versionUpdate.getData().getAndroid_versionname());
            tvVersionDesc.setText(versionUpdate.getData().getAndroid_versiondesc());
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //playClearAnimIfNeeded();
                    MyApplication.ISShow=false;
//                    if(dialog.isShowing()){
//                        dialog.dismiss();
//                    }
                   VersionUpdateConfig.getInstance()//获取配置实例
                            .setContext(MainActivity.this)//设置上下文
                            .setDownLoadURL(versionUpdate.getData().getAndroid_address())//设置文件下载链接
//                    .setFileSavePath(savePath)//设置文件保存路径（可不设置）
                            .setNotificationIconRes(R.mipmap.logo)//设置通知图标
                            .setNotificationSmallIconRes(R.mipmap.logo)//设置通知小图标
                            .setNotificationTitle("产业通")//设置通知标题
                            .startDownLoad();

                   /* if(isAvilible(MainActivity.this,hauweipag)){
                        //TODO 跳转华为应用商城
                        goToMarket(MainActivity.this,"com.meiliangzi.app");

                    }else {
                        //TODO 跳转网页进行下载
                        Uri uri = Uri.parse("http://app.hicloud.com/search/%25E5%258D%258E%25E4%25B8%25BA%25E5%25BA%2594%25E7%2594%25A8%25E5%25B8%2582%25E5%259C%25BA");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }*/
                    middleView.dismissMiddleView();
                }
            });
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    middleView.dismissMiddleView();
                }
            });
            middleView.showModdleView(true);
        }else {

        }
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(MainActivity.this, REQUEST_CODE_SETTING).show();
        }
    }






    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(MainActivity.KEY_EXTRAS);
                    JSONObject extraJson = new JSONObject(extras);
                    Log.i("grage", messge);
                    Log.i("grage", extras);
                    MessageBean bean = new MessageBean();
                    bean.setKey(extraJson.getString("key"));
                    bean.setTitle(extraJson.getString("title"));
                    bean.setContent(messge);
                    bean.setImage(extraJson.getString("image"));
                    bean.setId(extraJson.getString("id") + "");
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(errorMessage.equals("无此群组信息")){

        }else {
            super.showErrorMessage(errorMessage);
        }
    }
    private void requestReadContactsPermission() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                    },
                    101);
            return;
        }
    }
    private void playClearAnimIfNeeded() {
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_DELETE;
        if(true){
            tagAliasBean.alias = String.valueOf(NewPreferManager.getId());
        }else{
//            tagAliasBean.tags = tags;
        }
        tagAliasBean.isAliasAction = true;
        int sequence = 1;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
        NewPreferManager.saveId("");

       // NewPreferManager.saveIsCompleteInfo(false);

        List<File> cacheFiles = new ArrayList<File>();
        FileUtil.recursionFile(getCacheDir(), cacheFiles);
        if (cacheFiles.isEmpty()) {
            //ToastUtils.custom("没有缓存文件需要清理");
        } else {
            clearCache(cacheFiles);
        }
    }
    private void clearCache(List<File> cacheFiles) {
        startNewThreadClearCache(cacheFiles);
    }
    private void startNewThreadClearCache(final List<File> files) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                clearCachFile(files);
            }
        });
    }

    private void clearCachFile(final List<File> files) {
        for (int pos = 0; pos < files.size(); pos++) {
            try {
                files.get(pos).delete();
                // 友好性交互，避免文件太少从而删除过快，一闪而逝的现象
                //Thread.sleep(150);
            } catch (Exception e) {
//                Print.e(TAG, e);
                e.printStackTrace();
            }
        }
    }
    /**检查手机是否安装应用包名
     * @param context
     * @param packageName
     * @return
     */
    public  boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);


    }
    public  void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }







    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();



    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        if(errorMessage.equals("无此群组信息")){

        }else {
            super.showErrorMessage(errorMessage);
        }

    }


    private void initWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(Color.parseColor("#3399FE"));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    public static void loadRoundImage(final Context context, final int cornerRadius, String url,int resId,final ImageView imageView){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .placeholder(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(cornerRadius); //设置圆角弧度
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

}

