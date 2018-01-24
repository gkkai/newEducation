package com.meiliangzi.app.ui.view;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.MapLoctionsBean;
import com.meiliangzi.app.model.bean.QueryMapsBeans;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.CacheUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseLoctionsAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.widget.XListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class MapListsActivity extends BaseActivity implements XListView.IXListViewListener , View.OnClickListener {
    @BindView(R.id.listView)
    XListView listView;
    /*@BindView(R.id.ll_listview)
    LinearLayout ll_listview;*/
    @BindView(R.id.edit_adddress)
    EditText edit_adddress;
    @BindView(R.id.ll_no)
    LinearLayout ll_no;
    Gson gson;
    private String name;
    private String lat,lng;
    List<String> loctsons;
    @BindView(R.id.tvhostirysocary)
    TextView tvhostirysocary;
    private Dialog dialog;
    private View inflate;
    String autoPackage = "com.autonavi.minimap";
    String baiduPackage = "com.baidu.BaiduMap";
    private Boolean isequals=false;
    private BaseLoctionsAdapter<QueryMapsBeans.DataBean> adapter;
    private List<QueryMapsBeans.DataBean> lists=new ArrayList<>();
    private double gg_lon = 0, gg_lat = 0;
    private double bd_lon = 0, bd_lat = 0;
    private double tx_lon = 0, tx_lat = 0;
    private String bdtype = "";
    private String autotype = "";
    InputMethodManager imm;
    private MyDialog myDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_map_lists);
        onCreateView(R.layout.activity_map_lists);
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);





    }



    @Override
    protected void findWidgets() {

        adapter = new BaseLoctionsAdapter<QueryMapsBeans.DataBean>(this, R.layout.item_loctions_lists) {

            @Override
            public void convert(BaseViewHolder helper, final QueryMapsBeans.DataBean item) {
                name=edit_adddress.getText().toString();
                helper.setText(R.id.tv_Loction_Name, item.getName());
                helper.getView(R.id.tv_navigation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name=item.getName();
                        bd_lat=Double.valueOf(item.getLatitude());
                        bd_lon=Double.valueOf(item.getLongitude());
                        bd_decrypt(bd_lat ,bd_lon);
                        //map_bd2hx(bd_lat ,bd_lon);
                        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_layout, null);
                        TextView text_cncule = (TextView) inflate.findViewById(R.id.text_cncule);
                        text_cncule.setOnClickListener(MapListsActivity.this);
                        TextView bdtext = (TextView) inflate.findViewById(R.id.text_bd_name);
                        bdtext.setVisibility(View.VISIBLE);
                        bdtext.setOnClickListener(MapListsActivity.this);
                        bdtype = "bd";
                        TextView autotext = (TextView) inflate.findViewById(R.id.text_auto_name);
                        autotext.setVisibility(View.VISIBLE);
                        autotext.setOnClickListener(MapListsActivity.this);
                        dialog = new Dialog(MapListsActivity.this, R.style.ActionSheetDialogStyle);
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
                       /* if (isAvilible(MapListsActivity.this, baiduPackage)) {
                            TextView bdtext = (TextView) inflate.findViewById(R.id.text_bd_name);
                            bdtext.setVisibility(View.VISIBLE);
                            bdtext.setOnClickListener(MapListsActivity.this);
                            bdtype = "bd";
                        }
                        if (isAvilible(MapListsActivity.this, autoPackage)) {
                            autotype = "auto";
                            TextView autotext = (TextView) inflate.findViewById(R.id.text_auto_name);
                            autotext.setVisibility(View.VISIBLE);
                            autotext.setOnClickListener(MapListsActivity.this);
                        }
                        if ("".equals(bdtype) && "".equals(autotype)) {
                            ToastUtils.show("请安装百度地图，或者高德地图客户端");
                        } else {
                            dialog = new Dialog(MapListsActivity.this, R.style.ActionSheetDialogStyle);
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
                        }*/
                    }
                });
            }
        };
        listView.setAdapter(adapter);
    }
    @Override
    protected void initComponent() {
        // edit_adddress.setOnClickListener(this);
       /* edit_adddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
       /* edit_adddress.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i == KeyEvent.KEYCODE_NUMPAD_ENTER&&keyEvent.getAction() == KeyEvent.ACTION_UP) {

                    //TODO:回车键按下时要执行的操作

                }
                return false;
            }
        });*/
        edit_adddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
//do something;
                    if("".equals(edit_adddress.getText().toString())){
                        ToastUtils.show("您输入的内容为空");
                    }else {
                        String result= edit_adddress.getText().toString();
                        //TODO 查询数据类型
                        ProxyUtils.getHttpProxy().querymaps(MapListsActivity.this,edit_adddress.getText().toString());
                    }

                    return true;
                }
                return false;
            }

        });
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        listView.setXListViewListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent();
                intent.putExtra("address",adapter.getItem(i-1).getAddress());
                intent.putExtra("name",adapter.getItem(i-1).getName());
                intent.putExtra("image",adapter.getItem(i-1).getImage());
                intent.putExtra("lat",adapter.getItem(i-1).getLatitude());
                intent.putExtra("lng",adapter.getItem(i-1).getLongitude());
                intent.putExtra("phone",adapter.getItem(i-1 ).getPhone());
                setResult(106,intent);
                finish();

            }/*{
                bd_lat=Double.valueOf(adapter.getItem(i-1).getLatitude());
                bd_lon=Double.valueOf(adapter.getItem(i-1).getLongitude());
                bd_decrypt(bd_lat ,bd_lon);
                map_bd2hx(bd_lat ,bd_lon);


                //TODO 保存到本地缓存
                String loction= gson.toJson(adapter.getItem(i-1));
                if(lists!=null&&lists.size()!=0){
                    for(int j=0;j<loctsons.size();j++){
                        String neme= lists.get(j).getName();
                        String nemes= adapter.getItem(i-1).getName();
                        if(nemes.equals(neme)){
                            isequals=true;
                            break;
                        }else {
                        }
                    }

                }
                if(!isequals){
                    isequals=false;
                    CacheUtils.writeJson(MapListsActivity.this,loction,"CacheLoction",true);
                    lists.add(adapter.getItem(i-1));
                }else {
                    isequals=false;
                }

                //TODO 回掉地图显示
                showHtmlMap(adapter.getItem(i-1));
            }*/
        });

    }
    //TODO 显示手机上
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
                //webview.loadUrl("javascript:+callLoction('" + js.toString() + "')");
            }
        });


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
    /**
     * 坐标转换，百度地图坐标转换成腾讯地图坐标
     * @param lat  百度坐标纬度
     * @param lon  百度坐标经度
     * @return 返回结果：纬度,经度
     */
    public  double[] map_bd2hx(double lat, double lon){

        double x_pi=3.14159265358979324;
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        tx_lon = z * Math.cos(theta);
        tx_lat = z * Math.sin(theta);

        double[] doubles = new double[]{tx_lat,tx_lon};
        return doubles;
    }
    public void showLoctions(final List<QueryMapsBeans.DataBean> dataBean){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                adapter.setDatas(dataBean);
            }
        });
    }
    /* //TODO 展示历史记录
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
                 adapter.setDatas(lists);
             }
         });
     }*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_adddress:
                //TODO 输入地名
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result=edit_adddress.getText().toString();
                        // webview.loadUrl("javascript:+searchs('" + result + "')");
                    }
                });
                break;
            case R.id.text_bd_name:
                //TODO 选择导航
                if (isAvilible(this, baiduPackage)) {
                    statBaiduMap(baiduPackage);
                }else {
                    //ToastUtils.show("请安装百度地图客户端");
                    goToMarket(this,baiduPackage,"bd");
                }

                break;

            case R.id.text_auto_name:
                //TODO 选择导航
                if (isAvilible(this, autoPackage)) {
                    statAutoMap(autoPackage);
                }else {
                    //ToastUtils.show("请安装高德地图客户端");
                    goToMarket(this,autoPackage,"auto");
                }

                break;
            case R.id.text_cncule:
                //TODO 取消
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;

        }
    }
    private void querymaps(QueryMapsBeans data) {
        if(data.getData()!=null&&data.getData().size()==0){
            ll_no.setVisibility(View.VISIBLE);
            adapter.setDatas(data.getData());
        }else {
            ll_no.setVisibility(View.GONE);
            adapter.setDatas(data.getData());
        }


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

    @Override
    protected void showErrorMessage(String errorMessage) {


        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(errorCode==1){
            ll_no.setVisibility(View.VISIBLE);
            adapter.setDatas(lists);
        }

        super.showErrorMessage(errorCode, errorMessage);
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
