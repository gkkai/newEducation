package com.meiliangzi.app.ui.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.MapLoctionsBean;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.CacheUtils;
import com.meiliangzi.app.tools.picompressor.NativePlugin;
import com.meiliangzi.app.ui.WebViewActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseLoctionsAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.fragment.MapFragment;
import com.meiliangzi.app.ui.fragment.NewsFragment;
import com.meiliangzi.app.widget.PagerSlidingTabStrip;
import com.meiliangzi.app.widget.XListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MapActivity extends BaseActivity implements XListView.IXListViewListener , View.OnClickListener{
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

   /* List<TextView> listviews;

    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.ll_listview)
    LinearLayout ll_listview;
    @BindView(R.id.rl_bottom)
    RelativeLayout ll_bottom;
    @BindView(R.id.edit_serach)
    EditText etsearch;
    @BindView(R.id.tx_serach)
    TextView txsearch;
    @BindView(R.id.tvhostirysocary)
    TextView tvhostirysocary;
    @BindView(R.id.tx_cancel)
    TextView txcancel;
    @BindView(R.id.text_name)
    TextView text_name;
    @BindView(R.id.image_map)
    ImageView image_sure;
    String autoPackage = "com.autonavi.minimap";
    String baiduPackage = "com.baidu.BaiduMap";
    @BindView(R.id.wView_map)
    WebView webview;

    private String bdtype = "";
    private String autotype = "";
    private double gg_lon = 0, gg_lat = 0;
    private double bd_lon = 0, bd_lat = 0;
    private double tx_lon = 0, tx_lat = 0;
    private String name;
    private LocationListener mLocationListener;
    private LocationManager locMan;
    private NativePlugin nativePlugin;
    Gson gson;
    private Dialog dialog;
    private View inflate;
    private List<MapLoctionsBean.DataBean> lists=new ArrayList<>();
    private String lat,lng;
    private Boolean isequals=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_map_);

    }

    @Override
    protected void findWidgets() {
        gson=new Gson();
        locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                nativePlugin.setfLoction(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        webview.loadUrl("http://123.57.215.206:8087/api/map");
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

        nativePlugin=   new NativePlugin(locMan,webview, this, this,mLocationListener);
        //webview.loadUrl("file:///android_asset/map.html");
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
                //super.onGeolocationPermissionsShowPrompt(origin, callback);
                final boolean remember = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                builder.setTitle("位置信息");
                builder.setMessage(origin + "允许获取您的地理位置信息吗？").setCancelable(true).setPositiveButton("允许",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                callback.invoke(origin, true, remember);
                            }
                        })
                        .setNegativeButton("不允许",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        callback.invoke(origin, false, remember);
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
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
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
    }
    @Override
    public void onResume() {

        webview.loadUrl("http://123.57.215.206:8087/api/map");
        getpermission();
        super.onResume();
    }
    private void getpermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                    },
                    101);


            return;
        }
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, mLocationListener);
        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, mLocationListener);
    }
    @Override
    protected void initComponent() {
        final View headView = getLayoutInflater().inflate(R.layout.headview_index_, null, false);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        listView.setXListViewListener(this);
        txsearch.setOnClickListener(this);
        txcancel.setOnClickListener(this);
        image_sure.setOnClickListener(this);
    }
    @Override
    protected void initListener() {
        *//**//*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bd_lat=Double.valueOf(adapter.getItem(i-1).getLatitude());
                bd_lon=Double.valueOf(adapter.getItem(i-1).getLongitude());
                bd_decrypt(bd_lat ,bd_lon);
                map_bd2hx(bd_lat ,bd_lon);
                if(ll_listview.getVisibility()==View.VISIBLE){
                    ll_listview.setVisibility(View.GONE);
                }
                if(ll_bottom.getVisibility()==View.GONE){
                    ll_bottom.setVisibility(View.VISIBLE);
                    text_name.setText(adapter.getItem(i-1).getName());
                }
                //TODO 回掉地图显示
                showHtmlMap(adapter.getItem(i-1));
                //TODO 保存到本地缓存
                String loction= gson.toJson(adapter.getItem(i-1));

                if(!isequals){
                    isequals=false;
                    CacheUtils.writeJson(MapActivity.this,loction,"CacheLoction",true);
                    lists.add(adapter.getItem(i-1));
                }else {
                    isequals=false;
                }

            }
        });*//**//*

    }
    private void showHtmlMap(MapLoctionsBean.DataBean data) {

        lat = data.getLatitude();
        lng = data.getLongitude();
        final JSONObject js = new JSONObject();

        try {
            js.put("lat", lat);
            js.put("lng", lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl("javascript:+callLoction('" + js.toString() + "')");
            }
        });


    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void onDestroy() {
        locMan.removeUpdates(mLocationListener);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_bd_name:
                //TODO 选择导航
                statBaiduMap(baiduPackage);
                break;

            case R.id.text_auto_name:
                //TODO 选择导航
                statAutoMap(autoPackage);
                break;
            case R.id.text_qqmap:
                //TODO 选择导航
                statqqoMap();
                break;
            case R.id.image_map:
                inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_layout, null);
                TextView qqtext= (TextView) inflate.findViewById(R.id.text_qqmap);
                qqtext.setOnClickListener(this);
                if (isAvilible(MapActivity.this,baiduPackage)) {
                    TextView bdtext= (TextView) inflate.findViewById(R.id.text_bd_name);
                    bdtext.setVisibility(View.VISIBLE);
                    bdtext.setOnClickListener(this);
                    bdtype="bd";
                }
                if (isAvilible(MapActivity.this,autoPackage)) {
                    autotype="auto";
                    TextView autotext= (TextView) inflate.findViewById(R.id.text_auto_name);
                    autotext.setVisibility(View.VISIBLE);
                    autotext.setOnClickListener(this);
                }
                if("".equals(bdtype)&&"".equals(autotype)){
                    ToastUtils.show("请安装百度地图，或者高德地图客户端");
                }else {
                    dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
//填充对话框的布局

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
//       将属性设置给窗体
                    dialogWindow.setAttributes(lp);
                    dialog.show();//显示对话框
                }


                break;
            case R.id.tx_serach:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result=etsearch.getText().toString();
                        webview.loadUrl("javascript:+searchs('" + result + "')");
                    }
                });

                break;
            case R.id.tx_cancel:
                if(ll_listview.getVisibility()==View.VISIBLE){
                    ll_listview.setVisibility(View.GONE);
                }
                if(ll_bottom.getVisibility()==View.VISIBLE){
                    ll_bottom.setVisibility(View.GONE);
                }
                break;
        }
    }



    private void statqqoMap() {

        String uri="http://apis.map.qq.com/uri/v1/routeplan?type=bus&to="+name+"&tocoord="+tx_lat+","+tx_lon+"&policy=1&referer=myapp";
        webview.loadUrl(uri);
    }

    public void showLoctions(final List<MapLoctionsBean.DataBean> dataBean){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(ll_listview.getVisibility()==View.GONE){
                    ll_listview.setVisibility(View.VISIBLE);

                        tvhostirysocary.setVisibility(View.GONE);

                }
                etsearch.setText("");
                ll_bottom.setVisibility(View.GONE);
                //adapter.setDatas(dataBean);
            }
        });
    }
    //TODO 展示历史记录
    public void showlllistview(){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(ll_listview.getVisibility()==View.GONE){
                    ll_listview.setVisibility(View.VISIBLE);
                    if(lists.size()==0){
                        tvhostirysocary.setVisibility(View.VISIBLE);
                    }else {
                        tvhostirysocary.setVisibility(View.GONE);
                    }
                }
                if(ll_bottom.getVisibility()==View.VISIBLE){
                    ll_bottom.setVisibility(View.GONE);
                }
                //adapter.setDatas(lists);
            }
        });
    }

    private void statBaiduMap(String maptype) {
        name=text_name.getText().toString();
        Intent intent=new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=name:"+name+"|latlng:"+bd_lat+","+bd_lon));
        //intent.setPackage("com.autonavi.minimap");
        startActivity(intent); // 启动调用

    }
    *//**检查手机是否安装应用包名
     * @param context
     * @param packageName
     * @return
     *//*
    public static boolean isAvilible(Context context, String packageName) {
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
    private void statAutoMap(String maptype) {
        Intent intent=new Intent();
        intent.setData(Uri.parse("androidamap://navi?sourceApplication=媒亮子&poiname="+name+"&lat="+gg_lat+"&lon="+gg_lon+"&dev=1&style=2"));
    }
    void bd_decrypt(double bd_lat, double bd_lon)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
        gg_lon = z * cos(theta);
        gg_lat = z * sin(theta);
    }
    *//**//**
     * 坐标转换，百度地图坐标转换成腾讯地图坐标
     * @param lat  百度坐标纬度
     * @param lon  百度坐标经度
     * @return 返回结果：纬度,经度
     *//**//*
    public  double[] map_bd2hx(double lat, double lon){

        double x_pi=3.14159265358979324;
        double x=lon-0.0065,y=lat-0.006;
        double z=Math.sqrt(x*x+y*y)-0.00002*Math.sin(y*x_pi);
        double theta=Math.atan2(y,x)-0.000003*Math.cos(x*x_pi);
        tx_lon=z*Math.cos(theta);
        tx_lat=z*Math.sin(theta);

        double[]doubles=new double[]{tx_lat,tx_lon};
        return doubles;
        }

*/
}
