package com.meiliangzi.app.ui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.db.bean.MessageBean;
import com.meiliangzi.app.db.manage.MessageManage;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.model.bean.GroupinfoBean;
import com.meiliangzi.app.model.bean.QueryUserInfoBean;
import com.meiliangzi.app.model.bean.VersionUpdate;
import com.meiliangzi.app.receiver.CounterServer;
import com.meiliangzi.app.receiver.TagAliasOperatorHelper;
import com.meiliangzi.app.tools.FileUtil;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.PreferUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.AddFridentDialog;
import com.meiliangzi.app.ui.fragment.GroupFragment;
import com.meiliangzi.app.ui.fragment.IndexFragment;
import com.meiliangzi.app.ui.fragment.MineFragment;
import com.meiliangzi.app.ui.fragment.MsgFragment;
import com.meiliangzi.app.ui.fragment.SheQunFragment;
import com.meiliangzi.app.ui.fragment.TrainFragment;
import com.meiliangzi.app.ui.listener.ClearCacheHandler;
import com.meiliangzi.app.widget.FragmentTabHost;
import com.meiliangzi.app.widget.MiddleView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import androidkun.com.versionupdatelibrary.entity.VersionUpdateConfig;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

import static com.meiliangzi.app.config.Constant.INDEX;
import static com.meiliangzi.app.config.Constant.MSG;
import static com.meiliangzi.app.config.Constant.TRAIN;
import static com.meiliangzi.app.config.Constant.VIDEO;
import static com.meiliangzi.app.receiver.TagAliasOperatorHelper.ACTION_DELETE;


/**
 * 主界面
 *
 * @author xiaobo
 * @version 1.0
 * @date 2016/11/23 9:19
 */
public class MainActivity extends BaseActivity implements PermissionListener, RongIM.UserInfoProvider, RongIM.GroupInfoProvider, RongIM.GroupUserInfoProvider {
    private SQLHelper helper = null;
    private ClearCacheHandler clearCacheHanler;
    private FragmentTabHost mTabHost;
    private long exitTime = 0;
    //for receive customer msg from jpush server
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
    private String hauweipag="com.huawei.appmarket";
    private String isPhone;
    SQLiteDatabase database = null;

String groupid=null;
    private AddFridentDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.activity=this;

         dialog=new AddFridentDialog(this);
        dialog.setMessage("您好，请先绑定手机号码");
        //dialog.setCancelable(false);
        dialog.setYesOnclickListener("确定", new AddFridentDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Intent intent=new Intent(MainActivity.this,ResetPwdActivity.class);
                isPhone="Yes";
                intent.putExtra("BindPhone",101);
                startActivityForResult(intent,1009);
                dialog.dismiss();
            }
        });
        dialog.setNoOnclickListener("取消", new AddFridentDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                dialog.dismiss();
            }
        });
        super.onCreateView(R.layout.activity_main);
        /*//TODO 启动拿到当前时间
        ProxyUtils.getHttpProxyNoDialog().studyinfo(this, PreferManager.getUserId());*/
        //requestReadContactsPermission();
        if(helper ==null){
            helper = new SQLHelper(this);
        }
        ImLibinit(PreferManager.getTokens());



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
       // outState.putInt("variable", mCustomVariable);
    }
    @Override
    protected void findWidgets() {
//clearCacheHanler = new ClearCacheHandler(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        registerMessageReceiver();  // used for receive msg
        init();
//        MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteAllChannel();

    }


    @Override
    protected void initListener() {
//        Intent intent=getIntent();
//        if (intent!=null){
//            Bundle bundle= intent.getExtras();
//            Log.i("grage",bundle.get("cn.jpush.android.EXTRA").toString());
//            Log.i("grage",bundle.get("cn.jpush.android.ALERT").toString());
//            if (bundle!=null){
//                MessageBean bean = new MessageBean();
//                bean.setKey(bundle.getString("key"));
//                bean.setTitle(bundle.getString("title"));
//                bean.setContent("cn.jpush.android.ALERT");
//                bean.setImage(bundle.getString("image"));
//                bean.setId(bundle.getString("id")+"");
//                if (MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).addChannel(bean)) {
//                    return;
//                }
//            }
//
//        }

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
        //checkVersion();
        initTabHost();
        //submitTime();
        if(!"".equals(PreferManager.getUserId())){
            //TODO 获取部门列表
           // ProxyUtils.getHttpProxy().querydepartmentusernumber(this, Integer.valueOf(PreferManager.getUserId()));

        }
        checkVersion();


    }


    private void checkVersion() {

        ProxyUtils.getHttpProxyNoDialog().queryversionnumber(MainActivity.this);

    }

    protected void getVersion(VersionUpdate versionUpdate) {
        this.versionUpdate = versionUpdate;
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
        mTabHost.addTab(mTabHost.newTabSpec(INDEX).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_index, R.string.stringIndex)), IndexFragment.class, new Bundle());
        mTabHost.addTab(mTabHost.newTabSpec(VIDEO).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_video, R.string.stringVideo)), SheQunFragment.class, new Bundle());
        mTabHost.addTab(mTabHost.newTabSpec(MSG).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_msg, R.string.stringMsg)), MsgFragment.class, new Bundle());
        // TODO 培训
        //mTabHost.addTab(mTabHost.newTabSpec(Constant.TRAIN).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_msg, R.string.stringTRAIN)), TrainFragment.class, new Bundle());
        mTabHost.addTab(mTabHost.newTabSpec(Constant.MINE).setIndicator(createIndicatorView(R.drawable.selector_maintab_nav_nime, R.string.stringMine)), MineFragment.class, new Bundle());
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }


    public FragmentTabHost getTabHost() {
        return mTabHost;
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
        mTabHost.setCurrentTab(pos);
        //mTabHost.setCurrentTab(pos);

    }

    private void setTab() {
        Intent intent = getIntent();
        int pos = intent.getIntExtra("jPfrom", 0);
        if(pos==3){
            getIntent().putExtra("jPfrom",0);
        }
        mTabHost.setCurrentTab(pos);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                if (TextUtils.equals(mTabHost.getCurrentTabTag(), INDEX)) {
                    exitApp();
                } else {
                    mTabHost.setCurrentTabByTag(INDEX);
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
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
        if(!TextUtils.isEmpty(PreferManager.getUserId())){
            ProxyUtils.getHttpProxyNoDialog().queryuserloginlog(this, PreferManager.getUserId());
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
                if("".equals(PreferManager.getUserPhone())){
                    if(TextUtils.isEmpty(PreferManager.getUserId())){

                    }else {
                        dialog.show();
                    }

                }
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
        if(TextUtils.isEmpty(PreferManager.getUserId())){

        }else {
            if("".equals(PreferManager.getUserPhone())&&MyApplication.ISShow){


                dialog.show();
            }
        }
       /* if("".equals(PreferManager.getUserPhone())) {
            if (TextUtils.isEmpty(PreferManager.getUserId())) {
                dialog.show();
            }
        }*/
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
        RongIM.getInstance().disconnect();
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
                    playClearAnimIfNeeded();
                    MyApplication.ISShow=false;
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                   VersionUpdateConfig.getInstance()//获取配置实例
                            .setContext(MainActivity.this)//设置上下文
                            .setDownLoadURL(versionUpdate.getData().getAndroid_address())//设置文件下载链接
//                    .setFileSavePath(savePath)//设置文件保存路径（可不设置）
                            .setNotificationIconRes(R.mipmap.logo)//设置通知图标
                            .setNotificationSmallIconRes(R.mipmap.logo)//设置通知小图标
                            .setNotificationTitle("煤亮子")//设置通知标题
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




// [MyReceiver] onReceive - cn.jpush.android.intent.NOTIFICATION_OPENED, extras:
//    key:cn.jpush.android.NOTIFICATION_TYPE, value:0
//    key:app, value:com.meiliangzi.app
//    key:cn.jpush.android.ALERT, value:逍遥您有一个新课程未学习。
//    key:cn.jpush.android.EXTRA, value: [image - http://o7tb2rscn.bkt.clouddn.com/attach/7716313e949411e79aeb00163e004505.png]
//    key:cn.jpush.android.EXTRA, value: [id - 73]
//    key:cn.jpush.android.EXTRA, value: [key - essay]
//    key:cn.jpush.android.EXTRA, value: [title - 是对方的说法]
//    key:cn.jpush.android.NOTIFICATION_ID, value:532153223
//    key:cn.jpush.android.ALERT_TYPE, value:-1
//    key:cn.jpush.android.NOTIFICATION_CONTENT_TITLE, value:煤亮子
//    key:cn.jpush.android.MSG_ID, value:42784197147745772
//    key:sdktype, value:JPUSH

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
            tagAliasBean.alias = String.valueOf(PreferManager.getUserId());
        }else{
//            tagAliasBean.tags = tags;
        }
        tagAliasBean.isAliasAction = true;
        int sequence = 1;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
        PreferManager.saveUserId("");
//                if (!PreferManager.getUserId().isEmpty())
//                {
//                    long  start= Long.parseLong(PreferManager.getTimeStart());
//                    if (start==0){
//                        return;
//                    }
//                    long minite=(System.currentTimeMillis()-start)/(60*1000);
//                    doSubmit(String.valueOf(minite));
//                }
        PreferManager.saveIsCompleteInfo(false);
        //PreferUtils.deletefile();
       /* TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_DELETE;
        if(true){
            tagAliasBean.alias = String.valueOf(PreferManager.getUserId());
        }else{
//            tagAliasBean.tags = tags;
        }
        tagAliasBean.isAliasAction = true;
        int sequence = 1;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
        PreferManager.saveUserId("");*/
        //PreferManager.saveUserId(null);
       // PreferUtils.remove("userId");
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

   //TODO 链接融云服务
    private void ImLibinit(final String tokens) {
        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(tokens, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {
                   /* Map map=new HashMap();
                    map.put(Conversation.ConversationType.PRIVATE.getName(), false);
                    RongIM.getInstance().startConversationList(getBaseContext(), map);
*/
                  // RongIM.getInstance().setUserInfoProvider(MainActivity.this, false);
                   RongIM.getInstance().setGroupInfoProvider(MainActivity.this,false);
                   // RongIM.getInstance().setGroupUserInfoProvider(MainActivity.this,false);

                    //RongIM.setGroupInfoProvider(MainActivity.this,false);
                    //RongIM.setGroupUserInfoProvider(MainActivity.this,false);


                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
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
                //helper.onUpgrade(database,1,2);
                database.close();
            }
        }
        if(name!=null&&image!=null){
            return new UserInfo(s,name,Uri.parse(image));
        }*/
        //TODO 查看用户个人信息
        //ProxyUtils.getHttpProxy().queryuserinfo(MainActivity.this, Integer.valueOf(s));

if(!TextUtils.isEmpty(PreferManager.getUserId())&&s.equals(PreferManager.getUserId())){
    return new UserInfo(PreferManager.getUserId(),PreferManager.getUserName(),Uri.parse(PreferManager.getUserStar()));

}

return null;
    }
    @Override
    public Group getGroupInfo(String s) {

        //TODO
        ProxyUtils.getHttpProxy().groupinfo(MainActivity.this, Integer.valueOf(s));

        return null;
    }
    @Override
    public GroupUserInfo getGroupUserInfo(String s, String s1) {
        //TODO
        ProxyUtils.getHttpProxy().groupinfo(MainActivity.this, Integer.valueOf(s));

        return null;
    }
    private void getmentusernumber(DepartmentuserNumberBean data){
        MyApplication.numberBean=data;

    }
    private void getqueryuserinfo(QueryUserInfoBean data){
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(String.valueOf(data.getData().getUserId()),data.getData().getUserName(),Uri.parse(data.getData().getImage())));


    }
    private void getgroupinfo(GroupinfoBean data){
        RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(data.getData().getGroupId()),data.getData().getGroupName(), Uri.parse(String.valueOf(data.getData().getGroupImage()))));

    }

    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();


        if(getIntent()!=null){
            int pos = getIntent().getIntExtra("from", 0);
            mTabHost.setCurrentTab(pos);
        }

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        if(errorMessage.equals("无此群组信息")){

        }else {
            super.showErrorMessage(errorMessage);
        }

    }
}

