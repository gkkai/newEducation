package com.meiliangzi.app.ui.view;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.bean.MapInfoBean;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.MyDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;


public class MapDetailsActivity extends BaseActivity implements View.OnClickListener {
    private int id;
    private MyDialog myDialog;
    Gson gson=new Gson();
    @BindView(R.id.tv_describe)
    TextView tv_describe;
    @BindView(R.id.tv_category)
    TextView tv_category;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_cityName)
    TextView tv_cityName;
    @BindView(R.id.tv_daohang)
    TextView tv_daohang;
    private String name;
    private String lat,lng;
    private double gg_lon = 0, gg_lat = 0;
    private double bd_lon = 0, bd_lat = 0;
    private double tx_lon = 0, tx_lat = 0;
    private Dialog dialog;
    private View inflate;
    private String bdtype = "";
    private String autotype = "";
    String autoPackage = "com.autonavi.minimap";
    String baiduPackage = "com.baidu.BaiduMap";

    @BindView(R.id.image_edit)
    ImageView image_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getIntExtra("id",1);
        onCreateView(R.layout.activity_map_details);


    }

    @Override
    protected void findWidgets() {

//TODO 查询数据类型
        ProxyUtils.gethttp().mapInfo(this,id);


    }
    private void  mapInfo(final MapInfoBean bean){

        tv_describe.setText(bean.getData().getDescribe());
                            tv_category.setText(bean.getData().getClassIfIcationName());
                            tv_phone.setText(bean.getData().getPhone());
                            tv_cityName.setText(bean.getData().getCityName()+bean.getData().getCountyName());
                            tv_daohang.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    daohang(bean.getData());

                                }
                            });
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapDetailsActivity.this,AddMapLoctionActivity.class);
                intent.putExtra("type","111");
                intent.putExtra("cityname",bean.getData().getCityName());
                intent.putExtra("countyname",bean.getData().getCountyName());
                intent.putExtra("phone",bean.getData().getPhone());
                intent.putExtra("describe",bean.getData().getDescribe());
                intent.putExtra("county_id",bean.getData().getCounty_id());
                intent.putExtra("county_id",bean.getData().getId());
                intent.putExtra("classification_id",bean.getData().getClassification_id());
                intent.putExtra("cityid",bean.getData().getCityName());
                intent.putExtra("name",bean.getData().getName());
                intent.putExtra("image",bean.getData().getImage());
                intent.putExtra("lng",bean.getData().getLongitude());
                intent.putExtra("lat",bean.getData().getLatitude());
                startActivity(intent);
                finish();
            }
        });



       }

    private void daohang(MapInfoBean.Data item) {
        name=item.getName();
        bd_lat=Double.valueOf(item.getLatitude());
        bd_lon=Double.valueOf(item.getLongitude());
        bd_decrypt(bd_lat ,bd_lon);
        //map_bd2hx(bd_lat ,bd_lon);
        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_layout, null);
        TextView text_cncule = (TextView) inflate.findViewById(R.id.text_cncule);
        text_cncule.setOnClickListener(MapDetailsActivity.this);
        TextView bdtext = (TextView) inflate.findViewById(R.id.text_bd_name);
        bdtext.setVisibility(View.VISIBLE);
        bdtext.setOnClickListener(MapDetailsActivity.this);
        bdtype = "bd";
        TextView autotext = (TextView) inflate.findViewById(R.id.text_auto_name);
        autotext.setVisibility(View.VISIBLE);
        autotext.setOnClickListener(MapDetailsActivity.this);
        dialog = new Dialog(MapDetailsActivity.this, R.style.ActionSheetDialogStyle);
//填充对话框的布局

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
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

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_bd_name:
                //TODO 选择导航
                if (isAvilible(this, baiduPackage)) {
                    statBaiduMap(baiduPackage);
                } else {
                    //ToastUtils.show("请安装百度地图客户端");
                    goToMarket(this, baiduPackage, "bd");
                }

                break;

            case R.id.text_auto_name:
                //TODO 选择导航
                if (isAvilible(this, autoPackage)) {
                    statAutoMap(autoPackage);
                } else {
                    //ToastUtils.show("请安装高德地图客户端");
                    goToMarket(this, autoPackage, "auto");
                }

                break;
            case R.id.text_cncule:
                //TODO 取消
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                break;
//            case R.id.image_edit:
//
//                break;

        }
    }
    /**检查手机是否安装应用包名
     * @param context
     * @param packageName
     * @return
     */
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
    private void statBaiduMap(String maptype) {

        Intent intent=new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=name:"+name+"|latlng:"+bd_lat+","+bd_lon));
        intent.setPackage("com.baidu.BaiduMap");
        startActivity(intent); // 启动调用
        //getApplicationContext().startActivity(intent); // 启动调用

    }
    private void statAutoMap(String maptype) {
        Intent intent = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=媒亮子"));
        intent.setPackage("com.autonavi.minimap");

        intent.setData(Uri.parse("amapuri://route/plan/?&sourceApplication=媒亮子&did=BGVIS2&dlat="+gg_lat+"&dlon="+gg_lon+"&dname=+"+name+"+&dev=0&t=0"));

        //intent.setData(Uri.parse("androidamap://navi?sourceApplication=媒亮子&poiname="+name+"&lat="+gg_lat+"&lon="+gg_lon+"&dev=1&style=2"));
        startActivity(intent); // 启动调用
    }
    public  void goToMarket(final Context context, final String packageName,String  type) {
        myDialog = new MyDialog(this);
        if("bd".equals(type)){
            myDialog.setMessage("您手机未安装百度地图客户端，请确认安装");
        }else {
            myDialog.setMessage("您手机未安装，请确认安装");
        }

        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    context.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }

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

}
