package com.meiliangzi.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;

import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.model.bean.GroupinfoBean;
import com.meiliangzi.app.tools.AppCrashUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.PreferUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.NativePlugin;
import com.meiliangzi.app.ui.NewsDetailActivity;
import com.meiliangzi.app.ui.view.train.ZipFileBena;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.NativeObject;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;

import static android.content.ContentValues.TAG;


/**
 * @author xiaobo
 * @ClassName: MyApplication
 * @Description: 程序应用配置
 * @date 2016年5月1日 上午10:26:14
 */
public class MyApplication extends MultiDexApplication implements RongIM.GroupInfoProvider, RongIM.GroupUserInfoProvider {
    private static Stack<Activity> atyStack = new Stack<Activity>();
    private static DisplayImageOptions options;
    private static DisplayImageOptions.Builder builder;
    public  static int score=0;
    //    private static UnionApplication mInstance = null;
    private static MyApplication _instance;
    public static int type=5;
    public static String path;
    private SQLHelper sqlHelper;
    public static Activity activity;
    public static NewsDetailActivity newsactivity;
    public static String getdata="102";
    public static boolean show=false;
    public static FragmentManager fragmentManager;
    public static NativePlugin plugin;
    public static List<ZipFileBena> zipfilelist=new ArrayList<>();
    public static Class clsaaname;
    public static String tokens;
    public static boolean ISShow=true;
    public static DepartmentuserNumberBean numberBean;
    /**
     * 集成融云应用的唯一标识, 前端后台都需要用到.
     */
    public static final String APP_KEY = "e5t4ouvpei9ca";

    /**
     * 务必妥善保存, 不能放在应用端, 否则可以被反编译获得.
     */
    public static final String APP_SECRET = "DYCgyXM4CEE";
    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        _instance=this;
        ToastUtils.init(this);
        AppCrashUtils.init(this);
        PreferUtils.openFile(this);
        initImageLoader(getApplicationContext());
        initSimpleOption();
        initPush();
        PlatformConfig.setWeixin("wxfcf0a2512f7f6031", "d66d4d41f647dae8c4e5ae46a1ff69d5");
        //submitTime();
        //RongIMClient.init(this, APP_KEY);
      if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))||"io.rong.push".equals(
                getCurProcessName(getApplicationContext()))) {
          RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");
          RongIM.init(this);


      }
          //RongIMClient.init(this);



    }


    protected void showErrorMessage(Integer errorCode, String errorMessage) {

    }


    public static void push(Activity aty) {
        atyStack.push(aty);
    }

    public static MyApplication getInstance() {
        return _instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        while (!atyStack.empty()) {
            atyStack.pop().finish();
        }
        Process.killProcess(Process.myPid());
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static void closeSeries(Class<? extends  Activity> cls) {
        if (atyStack.isEmpty()) {
            return;
        }
        for (int pos = findPos(cls); pos < atyStack.size() - 1; ) {
            atyStack.pop().finish();
        }
    }

    private static int findPos(Class<? extends  Activity> cls) {
        for (int pos = 0; pos < atyStack.size(); pos++) {
            if (atyStack.get(pos).getClass() == cls) {
                return pos;
            }
        }
        throw new IllegalStateException();
    }

    public static void pop(Activity aty) {
        atyStack.remove(aty);
    }

    private void initSimpleOption() {
        builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565);
    }


    public static DisplayImageOptions getRoundImage(Integer fallbackResource, Integer loadingResource) {
        builder.cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .resetViewBeforeLoading(true).displayer(new RoundedBitmapDisplayer(10)).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565);// ����ͼƬ�Ľ�������//
        options = builder.showImageOnLoading(loadingResource).
                showImageForEmptyUri(fallbackResource).build();
        return options;
    }

    @SuppressWarnings("deprecation")
    public void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "edu/image");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 480 * 800)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions getSimpleOptions(Integer fallbackResource, Integer loadingResource) {
        options = builder.showImageOnLoading(loadingResource).
                showImageForEmptyUri(fallbackResource).build();
        return options;
    }

    @Override
    public void onTrimMemory(int level) {
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {

            Log.i("GRAGE", "APP遁入后台");
            if (!PreferManager.getUserId().isEmpty())
            {
             long  start= Long.parseLong(PreferManager.getTimeStart());
                if (start==0){
                    return;
                }
                long minite=(System.currentTimeMillis()-start)/(60*1000);
                //submitTime();
            }
        }
    }

    public void submitTime(){
        if(!TextUtils.isEmpty(PreferManager.getUserId())){
            ProxyUtils.getHttpProxyNoDialog().queryuserloginlog(this, PreferManager.getUserId());
        }
    }

    protected void getStatus(JSONObject jsonObject){
        Log.i(TAG, "getStatus: "+jsonObject);
    }



    /**
     * 初始化推送
     */
    private void initPush() {
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }



    /**
     * 获取数据库Helper
     */

    public SQLHelper getSQLHelper() {

        if (sqlHelper == null)
            sqlHelper = new SQLHelper(_instance);
        return sqlHelper;
    }


            @Override
            public Group getGroupInfo(String s) {
                //TODO
                ProxyUtils.getHttpProxy().groupinfo(this, Integer.valueOf(s));

                return null;
            }


    @Override
    public GroupUserInfo getGroupUserInfo(String s, String s1) {
        //TODO
        ProxyUtils.getHttpProxy().groupinfo(this, Integer.valueOf(s));

        return null;
    }
    private void getgroupinfo(GroupinfoBean data){
        RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(data.getData().getGroupId()),data.getData().getGroupName(), Uri.parse(String.valueOf(data.getData().getGroupUserId()))));

    }
}
