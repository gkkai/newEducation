package com.meiliangzi.app.ui.view;
import android.Manifest;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.CityListBean;
import com.meiliangzi.app.model.bean.CountyListbean;
import com.meiliangzi.app.model.bean.MapTypeListsBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.NativePlugin;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.widget.MyLearnLayout;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;


public class MapNewActivity extends BaseActivity implements XListView.IXListViewListener , View.OnClickListener{
    private  MyDialog myDialog;
    @BindView(R.id.wView_map)
    WebView webview;
    private String bdtype = "";
    private String autotype = "";
    @BindView(R.id.layout_maps)
    MyLearnLayout layout_maps;//展示地图类型列表
    private  int count=0;
    /*@BindView(R.id.llayout_citylist)
    LinearLayout llayout_citylist;*/
    @BindView(R.id.ll_view)
    RelativeLayout ll_view;
    /*@BindView(R.id.text_cncule)
    TextView text_cncule;*/
    @BindView(R.id.ellist_map)
    ExpandableListView explist;
    @BindView(R.id.spinner_city)
    Spinner spinner_city;
    //ListCityAdapter cityadapter;
    @BindView(R.id.image_ipuentname)
    ImageView inputname;
    @BindView(R.id.image_add)
    ImageView addname;
    @BindView(R.id.imag_up_down)
    ImageView imag_up_down;
    String autoPackage = "com.autonavi.minimap";
    String baiduPackage = "com.baidu.BaiduMap";
    private boolean isShowmap=false;
    private double gg_lon = 0, gg_lat = 0;
    private double bd_lon = 0, bd_lat = 0;
    private double tx_lon = 0, tx_lat = 0;
    private String name;
    private LocationListener mLocationListener;
    private LocationManager locMan;
    private NativePlugin nativePlugin;
    private int postiton=0;
    Gson gson;
    private Dialog dialog;
    private View inflate;
    private List<TextView> listviews=new ArrayList<>();//
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    public boolean show;
    public String listshow="down";
    private int height;
    private int cityId;
    private int classificationId;
    private List<CountyListbean.DataBean> countyBean=new ArrayList<>();//县目录下的列表
    private List<CityListBean.DataBean> cityBean=new ArrayList<>();//市目录下的县列表
    private LayoutInflater Inflater;
    private List<MapTypeListsBean.DataBean> typeBean=new ArrayList<>();
    private MyAdapter adapter;
    ArrayAdapter<CityListBean.DataBean> sinpadapter;
    private static final String[] cities =

            { " 上海 " , " 北京 " , " 南京 " , " 哈尔滨 " , " 乌鲁木齐 " , " 符拉迪沃斯托克 " , " 圣弗朗西斯科 " };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        onCreateView(R.layout.activity_map);
        layout_maps.setActivity(this);
        explist.setIndicatorBounds(width-90, width-10);
        height=dip2px(this,400);
        setGestureListener();
        Inflater = LayoutInflater.from(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_ipuentname:
                //TODO 输入名字
                Intent intent =new Intent(this,MapListsActivity.class);
                startActivityForResult(intent,106);
                break;
            case R.id.image_add:
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("javascript:+androidGetAddress()");
                    }
                });*/
                Intent addintent=new Intent(this,AddMapActivity.class);
                startActivityForResult(addintent,101);
                //TODO 添加位置
                break;
            case R.id.text_bd_name:
                //TODO 选择导航
                if (isAvilible(this, baiduPackage)) {
                    statBaiduMap(baiduPackage);
                }else {
                   // ToastUtils.show("请安装百度地图客户端");
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
                //statqqoMap();
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            default: setType(view.getId());
        }


    }
    private void statBaiduMap(String maptype) {

        Intent intent=new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=name:"+name+"|latlng:"+bd_lat+","+bd_lon));
        intent.setPackage("com.baidu.BaiduMap");
        startActivity(intent); // 启动调用

    }
    private void statAutoMap(String maptype) {
        //Intent intent=new Intent();
        Intent intent = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=媒亮子"));
        intent.setPackage("com.autonavi.minimap");
        //intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords="+需要导航的地址));

        intent.setData(Uri.parse("amapuri://route/plan/?&sourceApplication=媒亮子&did=BGVIS2&dlat="+gg_lat+"&dlon="+gg_lon+"&dname=+"+name+"+&dev=0&t=0"));
        //intent.setData(Uri.parse("androidamap://poi?sourceApplication=媒亮子&poiname="+name+"&lat="+gg_lat+"&lon="+gg_lon+"&dev=1&style=2"));
        startActivity(intent); // 启动调用
    }
    private void setType(int id){
        layout_maps.removeAllViews();
        for (int i=0;i<listviews.size();i++){
            TextView textView=listviews.get(i);
            if(id==100+i){
                textView .setBackground(getResources().getDrawable(R.drawable.share_maptype));
                textView.setTextColor(Color.WHITE);
                //TODO 拿到县列表
                //cityId=1;//暂时默认榆林市
                postiton=i;
                classificationId=typeBean.get(i).getId();
                ProxyUtils.getHttpProxy().countylist(MapNewActivity.this,cityId,classificationId);
            }else {
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.GRAY);

            }
            layout_maps.addView(textView);
        }
    }
    @Override
    protected void findWidgets() {
        //image_city.setOnClickListener(this);
        gson=new Gson();
        locMan = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

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
        webview.loadUrl("http://www.meiliangzi.cn:8087/api/map");
        //webview.loadUrl("http://dev-2.meiliangzi.cn:8087/api/map");
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

        nativePlugin=   new NativePlugin(locMan,webview,this, this,mLocationListener);
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

                super.onGeolocationPermissionsShowPrompt(origin, callback);
                Log.i("TAG", "onGeolocationPermissionsShowPrompt");
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if(!isShowmap){
                    isShowmap=true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject js = new JSONObject();
                            try {
                                js.put("cityId", cityId);
                                js.put("countyId", "");
                                js.put("classificationId",classificationId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            webview.loadUrl("javascript:+queryAddress('" +js.toString()+ "')");
                        }
                    });
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
       /* cityadapter=new ListCityAdapter(this,cityBean);
        listView.setAdapter(cityadapter);*/
        adapter=new MyAdapter(this);
        //sinpadapter = new ArrayAdapter<String>( this , android.R.layout. simple_spinner_item , cities );
        explist.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        explist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }

        });
        explist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, final int i, long l) {
                //ToastUtils.show("explist.onGroupClick");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject js = new JSONObject();
                        try {
                            js.put("cityId", "");
                            js.put("countyId", ((CountyListbean.DataBean)adapter.getGroup(i)).getId());
                            js.put("classificationId",classificationId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        webview.loadUrl("javascript:+queryAddress('" +js.toString()+ "')");
                    }
                });
                return false;
            }
        });
        explist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, final int i, final int i1, long l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject js = new JSONObject();
                        try {
                            js.put("address", ((CountyListbean.DataBean.MapinfoBean)adapter.getChild(i,i1)).getAddress());
                            js.put("phone", ((CountyListbean.DataBean.MapinfoBean)adapter.getChild(i,i1)).getPhone());
                            js.put("name", ((CountyListbean.DataBean.MapinfoBean)adapter.getChild(i,i1)).getName());
                            js.put("image", ((CountyListbean.DataBean.MapinfoBean)adapter.getChild(i,i1)).getImage());
                            js.put("lat", ((CountyListbean.DataBean.MapinfoBean)adapter.getChild(i,i1)).getLatitude());
                            js.put("lng", ((CountyListbean.DataBean.MapinfoBean)adapter.getChild(i,i1)).getLongitude());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        webview.loadUrl("javascript:+queryCoordinate('" +js.toString()+ "')");
                    }
                });
                return false;
            }
        });
        // text_cncule.setOnClickListener(this);
        inputname.setOnClickListener(this);
        addname.setOnClickListener(this);
        super.initListener();
    }

    @Override
    public void onResume() {

        getpermission();
        super.onResume();
    }
    private void getpermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        //TODO 拿到市列表
        ProxyUtils.getHttpProxy().citylist(MapNewActivity.this);

    }
    /**
     * 返回县列表数据类型
     */
    protected  void  citylist(CityListBean data){
        cityBean=data.getData();
        //适配器
       /* //设置样式
        sinpadapter = new ArrayAdapter<CityListBean.DataBean>(this ,android.R.layout.simple_spinner_dropdown_item ,cityBean );
        //设置样式
        sinpadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        sinpadapter = new ArrayAdapter<CityListBean.DataBean>(this ,R.layout.spinner_item ,cityBean );
        //设置样式
        sinpadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(sinpadapter);
       /* spinner_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //cityId=cityBean.get(i).getId();//暂时默认榆林市
                cityId=sinpadapter.getItem(i).getId();
                //classificationId=typeBean.get(0).getId();
              //  ProxyUtils.getHttpProxy().countylist(MapNewActivity.this,cityId,classificationId);

            }
        });*/
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId=sinpadapter.getItem(i).getId();
                //classificationId=typeBean.get(0).getId();
                //TODO 查询数据类型
                ProxyUtils.getHttpProxy().classificationlist(MapNewActivity.this);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    /**
     * 返回数据类型
     */
    protected  void  classificationlist(MapTypeListsBean data){

        typeBean=data.getData();
        //cityId=cityBean.get(0).getId();//暂时默认榆林市
        classificationId=typeBean.get(postiton).getId();
        ProxyUtils.getHttpProxy().countylist(MapNewActivity.this,cityId,classificationId);
        addView1(data.getData());

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
    private void addView1(List<MapTypeListsBean.DataBean> dataBeans) {
        listviews.clear();
        layout_maps.removeAllViews();
        for (int i = 0; i < dataBeans.size(); i++) {
            View view = Inflater.inflate(R.layout.horizontalscrollview_item,// 找到布局文件
                    layout_maps, false);
            TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);

            if(i==postiton){
                txt.setBackground(getResources().getDrawable(R.drawable.share_maptype));
                txt.setTextColor(Color.WHITE);
            }
            txt.setText(dataBeans.get(i).getName());
            txt.setId(100+i);
            txt.setOnClickListener(this);
            ViewGroup parent = (ViewGroup) txt.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            listviews.add(txt);
            layout_maps.addView(txt);
        }
    }
    private View addView2(List<MapTypeListsBean.DataBean> dataBeans) {
        listviews.clear();
        layout_maps.removeAllViews();
        // TODO 动态添加布局(java方式)
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 75, 1.0f);
        vlp.gravity = Gravity.CENTER_VERTICAL;
        for (int i = 0; i < dataBeans.size(); i++) {
            //postiton=i;
            final TextView tv1 = new TextView(this);
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(14f);
            //tv1.setPadding(10, 10, 10, 10);
            //vlp.setMargins(55, 55, 55, 55);
            tv1.setLayoutParams(vlp);
            if(i==postiton){
                tv1.setBackground(getResources().getDrawable(R.drawable.share_maptype));
            }
            tv1.setText(dataBeans.get(i).getName());
            tv1.setId(100+i);
            tv1.setOnClickListener(this);
            listviews.add(tv1);
            layout_maps.addView(tv1);//将TextView 添加到子View 中
        }
        return layout_maps;
    }
    /**
     * 返回县列表数据类型
     */
    protected  void  countylist(CountyListbean data){
        countyBean.clear();
        countyBean=data.getData();

        adapter.notifyDataSetChanged();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject js = new JSONObject();
                try {
                    js.put("cityId", cityId);
                    js.put("countyId", "");
                    js.put("classificationId",classificationId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                webview.loadUrl("javascript:+queryAddress('" +js.toString()+ "')");
            }
        });

    }
    public class MyAdapter extends BaseExpandableListAdapter {
        private LayoutInflater mLayoutInflater;
        public MyAdapter(Context context) {
            this.context = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        Context context;

        //设置组视图的显示文字
        private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
        //子视图显示文字
        private String[][] generals = new String[][] {
                { "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
                { "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
                { "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" }

        };



        //自己定义一个获得textview的方法
        TextView getTextView() {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100);
            TextView textView = new TextView(context);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(36, 0, 0, 0);
            textView.setTextSize(15);
            textView.setTextColor(Color.BLACK);
            return textView;
        }


        @Override
        public int getGroupCount() {
            //return cityBean.size();
            return countyBean.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return countyBean.get(groupPosition).getMapinfo().size();
            // return 3;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return countyBean.get(groupPosition);
            // return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return countyBean.get(groupPosition).getMapinfo().get(childPosition);
            // return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
           /* LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            TextView textView = getTextView();
            textView.setText(((CountyListbean.DataBean)getGroup(groupPosition)).getName()+"  "+"("+(((CountyListbean.DataBean) getGroup(groupPosition)).getMapinfo().size())+")");
            ll.addView(textView);
            ll.setPadding(10, 10, 10, 10);
            return ll;*/
            ViewHolderGroup holderGroup=null;
            if(convertView==null){
                holderGroup=new ViewHolderGroup();
                convertView = Inflater.inflate(R.layout.item_mapgrounp_lists, parent, false);
                holderGroup.textView = (TextView) convertView.findViewById(R.id.tv_mapname);
                count++;
                convertView.setTag(holderGroup);
            }else {
                holderGroup=(ViewHolderGroup)convertView.getTag();
            }
            /*TextView textView = (TextView) convertView.findViewById(R.id.tv_mapname);*/
            holderGroup.textView.setText(((CountyListbean.DataBean)getGroup(groupPosition)).getName()+"  "+"("+(((CountyListbean.DataBean) getGroup(groupPosition)).getMapinfo().size())+")");

            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolderChild holderChild=null;
            if(convertView==null){
                holderChild=new ViewHolderChild();
                convertView = Inflater.inflate(R.layout.item_mapchlied_lists, parent, false);
                holderChild.tv_navigation=(TextView) convertView.findViewById(R.id.tv_navigation);
                holderChild.textView=(TextView) convertView.findViewById(R.id.tv_mapname);
                holderChild.imageView=(ImageView) convertView.findViewById(R.id.ivImg_map);
                convertView.setTag(holderChild);
            }else {
                holderChild= (ViewHolderChild) convertView.getTag();
            }
            /*TextView textView = (TextView) convertView.findViewById(R.id.tv_mapname);
            TextView tv_navigation = (TextView) convertView.findViewById(R.id.tv_navigation);

            textView.setText(((CountyListbean.DataBean.MapinfoBean)getChild(groupPosition, childPosition)).getName());
            ImageView imageView= (ImageView) convertView.findViewById(R.id.ivImg_map);*/
            holderChild.textView.setText(((CountyListbean.DataBean.MapinfoBean)getChild(groupPosition, childPosition)).getName());
            holderChild.tv_navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //弹出导航
                    name=((CountyListbean.DataBean.MapinfoBean)getChild(groupPosition, childPosition)).getName();
                    shownavigation((CountyListbean.DataBean.MapinfoBean)getChild(groupPosition, childPosition));
                    //ToastUtils.show("imageView.setOnClickListener"+groupPosition+"."+childPosition);
                }
            });
            setImageByUrl(holderChild.imageView,((CountyListbean.DataBean.MapinfoBean)getChild(groupPosition, childPosition)).getImage(), R.mipmap.defaule, R.mipmap.defaule);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        private class ViewHolderGroup{
            private TextView textView;

        }
        private class ViewHolderChild{
            private TextView textView;
            private TextView tv_navigation;
            private ImageView imageView;
        }

    }

    private void shownavigation(CountyListbean.DataBean.MapinfoBean item ) {
        bd_lat=Double.valueOf(item.getLatitude());
        bd_lon=Double.valueOf(item.getLongitude());
        bd_decrypt(bd_lat ,bd_lon);
        //map_bd2hx(bd_lat ,bd_lon);
        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_layout, null);
        TextView text_cncule = (TextView) inflate.findViewById(R.id.text_cncule);
        TextView bdtext = (TextView) inflate.findViewById(R.id.text_bd_name);
        bdtext.setVisibility(View.VISIBLE);
        bdtext.setOnClickListener(this);
        TextView autotext = (TextView) inflate.findViewById(R.id.text_auto_name);
        autotext.setVisibility(View.VISIBLE);
        autotext.setOnClickListener(this);
        text_cncule.setOnClickListener(this);
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
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
       /* if (isAvilible(this, baiduPackage)) {
            TextView bdtext = (TextView) inflate.findViewById(R.id.text_bd_name);
            bdtext.setVisibility(View.VISIBLE);
            bdtext.setOnClickListener(this);
            bdtype = "bd";
        }else {
            ToastUtils.show("请你安装百度地图客户端");
        }
        if (isAvilible(this, autoPackage)) {
            autotype = "auto";
            TextView autotext = (TextView) inflate.findViewById(R.id.text_auto_name);
            autotext.setVisibility(View.VISIBLE);
            autotext.setOnClickListener(this);
        } else {
            ToastUtils.show("请你安装高德地图客户端");
        }
        if ("".equals(bdtype) && "".equals(autotype)) {
            ToastUtils.show("请安装百度地图，或者高德地图客户端");
        } else {
            dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
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
    public void shownavigation(String lng,String lat,String name) {
        this.name=name;
        bd_lat=Double.valueOf(lat);
        bd_lon=Double.valueOf(lng);
        bd_decrypt(bd_lat ,bd_lon);
        //map_bd2hx(bd_lat ,bd_lon);
        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_layout, null);
        TextView text_cncule = (TextView) inflate.findViewById(R.id.text_cncule);
        TextView bdtext = (TextView) inflate.findViewById(R.id.text_bd_name);
        bdtext.setVisibility(View.VISIBLE);
        bdtext.setOnClickListener(this);
        TextView autotext = (TextView) inflate.findViewById(R.id.text_auto_name);
        autotext.setVisibility(View.VISIBLE);
        autotext.setOnClickListener(this);
        text_cncule.setOnClickListener(this);
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
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
       /* if (isAvilible(this, baiduPackage)) {
            TextView bdtext = (TextView) inflate.findViewById(R.id.text_bd_name);
            bdtext.setVisibility(View.VISIBLE);
            bdtext.setOnClickListener(this);
            bdtype = "bd";
        }else {
            ToastUtils.show("请你安装百度地图客户端");
        }
        if (isAvilible(this, autoPackage)) {
            autotype = "auto";
            TextView autotext = (TextView) inflate.findViewById(R.id.text_auto_name);
            autotext.setVisibility(View.VISIBLE);
            autotext.setOnClickListener(this);
        } else {
            ToastUtils.show("请你安装高德地图客户端");
        }
        if ("".equals(bdtype) && "".equals(autotype)) {
            ToastUtils.show("请安装百度地图，或者高德地图客户端");
        } else {
            dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
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

    void bd_decrypt(double bd_lat, double bd_lon)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = sqrt(x * x + y * y) - 0.00002 * sin(y * x_pi);
        double theta = atan2(y, x) - 0.000003 * cos(x * x_pi);
        gg_lon = z * cos(theta);
        gg_lat = z * sin(theta);
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
    /**
     * 设置上下滑动作监听器
     * @author jczmdeveloper
     */
    private void setGestureListener(){

       /* layout_maps.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY > 2
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            //向下滑動
                            performAnim2();
                        } else if (mCurPosY - mPosY < 2
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            //向上滑动
                            performAnim2();
                        }

                        break;
                }
                return true;
            }

        });*/
    }
    public void performAnim2(){
        //View是否显示的标志
        show = !show;
        //属性动画对象
        ValueAnimator va ;
        if(show){
            //升
            va = ValueAnimator.ofInt(dip2px(this,200),height);
            imag_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.down));
        }else{
            //降
            va = ValueAnimator.ofInt(height,dip2px(this,200));
            imag_up_down.setImageDrawable(getResources().getDrawable(R.mipmap.up));
        }
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前的height值
                int h =(Integer)valueAnimator.getAnimatedValue();
                //动态更新view的高度
                ll_view.getLayoutParams().height = h;
                ll_view.requestLayout();
            }
        });
        va.setDuration(100);
        //开始动画
        va.start();
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 为SmratImageView设置图片、加载中图片、加载失败图片
     *
     * @param view
     * @param url
     * @return
     */
    public void setImageByUrl(ImageView view, String url, Integer fallbackResource, Integer loadingResource) {


        ImageLoader.getInstance().displayImage(url, view, MyApplication.getSimpleOptions(fallbackResource, loadingResource));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode==106){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(data!=null){
                        JSONObject js = new JSONObject();
                        try {
                            js.put("phone", data.getStringExtra("phone"));
                            js.put("name", data.getStringExtra("name"));
                            js.put("image", data.getStringExtra("image"));
                            js.put("address", data.getStringExtra("address"));
                            js.put("lat", data.getStringExtra("lat"));
                            js.put("lng", data.getStringExtra("lng"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        webview.loadUrl("javascript:+queryCoordinate('" +js.toString()+ "')");
                    }
                }



            });
        }
        super.onActivityResult(requestCode, resultCode, data);
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

               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                    }
                });*/
                myDialog.dismiss();

            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myDialog.dismiss();
                    }
                });*/
                myDialog.dismiss();
            }
        });
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });*/
        myDialog.show();

    }
}
