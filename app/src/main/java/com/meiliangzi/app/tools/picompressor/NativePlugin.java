package com.meiliangzi.app.tools.picompressor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.meiliangzi.app.model.bean.MapLoctionsBean;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.view.AddMapActivity;
import com.meiliangzi.app.ui.view.AddMapLoctionActivity;
import com.meiliangzi.app.ui.view.MapActivity;
import com.meiliangzi.app.ui.view.MapNewActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kk on 2017/10/1.
 */

public class NativePlugin {
    private WebView webView;
    private Activity fragment;
    private Context context;
    private Gson gson;
    private double lat;
    private double lng;
    LocationListener mLocationListener;
    LocationManager locMan;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private Location location,floction;

    private double latitude, longitude;
    private MyDialog myDialog;

    public NativePlugin(LocationManager locMan,WebView webView ,Activity fragment, Context context, LocationListener mLocationListener) {
        this.webView = webView;
        this.fragment = fragment;
        this.context = context;


        this.mLocationListener = mLocationListener;
        this.locMan = locMan;
        this.gson = new Gson();
    }

    public Location getfloction() {
        return floction;
    }

    public void setfLoction(Location floction) {
        this.floction = floction;
    }

    /**
     * 加载地址
     */

    @JavascriptInterface
    public void callBackData(String callback) {
        //viewActivity.showselectLists(callback);
        MapLoctionsBean bean = gson.fromJson(callback, MapLoctionsBean.class);
        //((MapActivity) fragment).showLoctions(bean.getData());

    }
    /**
     * 加载地址坐标
     */

    @JavascriptInterface
    public void getloctionformHtml(String lng,String lat) {

       if("".equals(lng)&&"".equals(lat)){
            fragment.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //ToastUtils.show("请您先标记位置");
                    myDialog = new MyDialog(fragment);
                    myDialog.setMessage("请您先标记位置");
                    myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            //fragment.finish();
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
            });
        }else {
            Intent intent=new Intent(fragment,AddMapLoctionActivity.class);
            intent.putExtra("lng",lng);
            intent.putExtra("lat",lat);
            fragment.startActivityForResult(intent,101);
           fragment.finish();
        }

    }

    /**
     * 点击定位
     */

    @JavascriptInterface
    public void callBackLoction() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, mLocationListener);
        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, mLocationListener);
        if (getfloction() != null) {

                lat = getfloction().getLatitude();
                lng = getfloction().getLongitude();
                final JSONObject js = new JSONObject();

                try {
                    js.put("lat", lat);
                    js.put("lng", lng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:+callLoction('" + js.toString() + "')");
                    }
                });


        } else {

            Location loc = getLocation();

            if (loc != null) {
                Long time = loc.getTime();
                Long timenow = System.currentTimeMillis();
                if (timenow - time > 100000) {
                    fragment.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ToastUtils.show("GPS信号弱，定位失败，请重试");
                        }
                    });
                } else {
                    lat = loc.getLatitude();
                    lng = loc.getLongitude();
                    final JSONObject js = new JSONObject();

                    try {
                        js.put("lat", lat);
                        js.put("lng", lng);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    fragment.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            webView.loadUrl("javascript:+callLoction('" + js.toString() + "')");
                        }
                    });
                }
            }else {
                fragment.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ToastUtils.show("GPS信号弱，定位失败，请重试");
                    }
                });
            }

        }
    }

    public Location getLocation() {
        try {


            // getting GPS status
            isGPSEnabled = locMan
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locMan
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return null;
                        }
                        locMan.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1,
                                1, mLocationListener);
                        Log.d("GPS", "GPS Enabled");
                        if (locMan != null) {
                            location = locMan
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }else  if (isNetworkEnabled) {
                    locMan.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1, 1,
                            mLocationListener);
                    Log.d("Network", "Network Enabled");
                    if (locMan != null) {
                        location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    /**
     * 点击搜索
     */

    @JavascriptInterface
    public void androidCoordinate(final String lng, final String lat, final String name) {
        //显示隐藏列表
        //((MapActivity) fragment).showlllistview();

        fragment.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((MapNewActivity) fragment).shownavigation(lng,lat,name);
            }
        });


    }
    @JavascriptInterface
    public void callphone(String phoneNumber ) {
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));//跳转到拨号界面，同时传递电话号码
        fragment.startActivity(dialIntent);
        //fragment.finish();

    }
  /*  1 Android直接拨打电话
    Intent dialIntent =  new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phoneNumber));//直接拨打电话
    startActivity(dialIntent);
2 Android跳转到拨号界面
    Intent dialIntent =  new Intent(Intent.ACTION_CALL_BUTTON);//跳转到拨号界面
    startActivity(dialIntent);
3 Android跳转到拨号界面，同时传递电话号码
    Intent dialIntent =  new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));//跳转到拨号界面，同时传递电话号码
    startActivity(dialIntent);*/
}
