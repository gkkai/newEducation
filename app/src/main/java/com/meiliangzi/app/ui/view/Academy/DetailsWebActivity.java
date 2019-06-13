package com.meiliangzi.app.ui.view.Academy;



import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.NativePlugin;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.bean.RuleListBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

import static com.meiliangzi.app.config.Constant.ChanYeXY;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN_CIRCLE;

public class DetailsWebActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.xWebView)
    WebView webview;
    private Dialog dialog;
    private View inflate;
    private LinearLayout ll_weinxin;
    private LinearLayout ll_penyouquan;
    private TextView concle;
    private boolean mShouldOverrideUrlLoading;
    private NativePlugin nativePlugin;
    private Uri imageUri;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private String ArticlereadingId="402881e56a47e6c8016a47e8ffda0001";
    private String  DurationarticlesId="402881e56a47e6c8016a47e9363c0003";

    private String type;

    @BindView(R.id.imag_share)
    ImageView imag_share;
    private Timer timer,timer1;
    long firsttime = System.currentTimeMillis();
    private String url;
    private String id;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private String title;

    private long time;
    private boolean isplay=false;
    private long timeFormat=0;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO  0是文章 1是视屏
        type=getIntent().getStringExtra("type");
        url=getIntent().getStringExtra("url");
        title=getIntent().getStringExtra("title");
        description=getIntent().getStringExtra("description");
        id=getIntent().getStringExtra("id");
        onCreateView(R.layout.activity_details);
    }

    @Override
    protected void findWidgets() {
       // tv_title.setText(title);

        if("0".equals(type)){
            imag_share.setVisibility(View.VISIBLE);
            timer1=new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    timeFormat++;


                }
            },0,1000);
            tv_title.setText("文章详情");
            String ruleslists= NewPreferManager.getRuleLists();
            if(ruleslists.equals("")){
                time=30000;
            }else {
                Gson gson=new Gson();
                RuleListBean bean=   gson.fromJson(ruleslists,RuleListBean.class);
                for(int i=0;i<bean.getData().size();i++){
                    if(ArticlereadingId.equals(bean.getData().get(i).getId())){
                        time=Integer.valueOf(bean.getData().get(i).getConditions());
                    }
                }
            }
            //TODO 文章逻辑处理
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Map<String,String> maps=new HashMap<>();
                    maps.put("userId", NewPreferManager.getId());
                    if("0".equals(type)){
                        maps.put("ruleId",ArticlereadingId);
                    }
                    maps.put("comprehensiveId",id);
                    OkhttpUtils.getInstance(DetailsWebActivity.this).doPost("academyService/detail/readArticlesScore", maps, new OkhttpUtils.onCallBack() {
                        @Override
                        public void onFaild(Exception e) {

                        }

                        @Override
                        public void onResponse(String json) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.show("文章结束"+time*1000);
                                }
                            });


                        }
                    });

                }
            },time*1000);

        }else {
            imag_share.setVisibility(View.GONE);
            tv_title.setText(title);
        }

        imag_share.setOnClickListener(this);
        webview.loadUrl(ChanYeXY+url);
       // webview.loadUrl("file:///android_asset/video.html");
        WebSettings webSettings = webview.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 设置与Js交互的权限
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationDatabasePath(this.getFilesDir().getPath());

        nativePlugin=   new NativePlugin(this, webview, new NativePlugin.JsCallback() {
            @Override
            public void callback(String type) {

                if("paly".equals(type)){

                    isplay=true;
                }else if("puse".equals(type)){
                    isplay=false;
                }

            }
        });

        webview.addJavascriptInterface(nativePlugin, "NativePlugin");//AndroidtoJS类对象映射到js的test对象
        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
                Log.i("TAG", "onGeolocationPermissionsHidePrompt");
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin,
                                                           final GeolocationPermissions.Callback callback) {

                super.onGeolocationPermissionsShowPrompt(origin, callback);
                Log.i("TAG", "onGeolocationPermissionsShowPrompt");
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

    }

    @Override
    protected void initComponent() {

    }
    private void takePhoto() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imag_share:
                //sharewein();
                shareurl();
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
    /**\
     * 分享链接、title、内容
     */
    String linkHref = "分享链接";
    String content = "分享内容";
    //分享图片
    private UMImage image = null;
    private void  sharewein(){
        UMImage image = new UMImage(this, R.mipmap.log2);//资源文件

        UMWeb web = new UMWeb(ChanYeXY+url);
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

        UMWeb web = new UMWeb(ChanYeXY+url);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(description);//描述
        new ShareAction(this)
                .setPlatform(WEIXIN_CIRCLE)
                .withMedia(web)
                .share();

    }
    //分享回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {


        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            ToastUtils.show("分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.show("分享失败");
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.show("分享取消");
        }
    };

    @Override
    public void onBackClick(View v) {
        if("0".equals(type)){
            timer.cancel();
            timer1.cancel();
            Map<String,String> maps=new HashMap<>();
            maps.put("userId",NewPreferManager.getId());
            if("0".equals(type)){

                maps.put("ruleId",DurationarticlesId);
            }
            long nowTime = System.currentTimeMillis();
            long time=(nowTime-firsttime)/1000;
            maps.put("time", String.valueOf(timeFormat));
            maps.put("comprehensiveId",id);
            OkhttpUtils.getInstance(DetailsWebActivity.this).doPost("academyService/detail/essayLearningDurationScore", maps, new OkhttpUtils.onCallBack() {
                @Override
                public void onFaild(Exception e) {

                }

                @Override
                public void onResponse(String json) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show("timeFormat");
                        }
                    });

                }
            });
        }


        finish();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return false;
    }




}
