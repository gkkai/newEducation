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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.MapLoctionsBean;
import com.meiliangzi.app.model.bean.QueryMapsBeans;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.CacheUtils;
import com.meiliangzi.app.ui.MainActivity;
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

    @BindView(R.id.edit_adddress)
    EditText edit_adddress;
    @BindView(R.id.ll_no)
    LinearLayout ll_no;
    private String name;
    private String lat,lng;
    List<String> loctsons;
    @BindView(R.id.tvhostirysocary)
    TextView tvhostirysocary;

    private Boolean isequals=false;
    private BaseLoctionsAdapter<QueryMapsBeans.DataBean> adapter;
    private List<QueryMapsBeans.DataBean> lists=new ArrayList<>();
    private double gg_lon = 0, gg_lat = 0;
    private double bd_lon = 0, bd_lat = 0;
    private double tx_lon = 0, tx_lat = 0;

    InputMethodManager imm;



    @BindView(R.id.ll_historicalrecords)
    LinearLayout ll_historicalrecords;

    @BindView(R.id.gradview_group)
    GridView gradview_group;
    private BaseLoctionsAdapter<String > historiadapter;
    Gson gson =new Gson();
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_map_lists);
        onCreateView(R.layout.activity_map_lists);
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);





    }



    @Override
    protected void findWidgets() {
        historiadapter=new BaseLoctionsAdapter<String>(this, R.layout.gradview_chex) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                ((TextView) helper.getView(R.id.ck)).setText(item);

            }
        };
        if(NewPreferManager.gethostirysocary().equals("")){
            List <String> list= new ArrayList<String>() ;
            historiadapter.setDatas(list);
        }else {
            List <String> list= gson.fromJson(NewPreferManager.gethostirysocary(),List.class) ;
            historiadapter.setDatas(list);

        }

        gradview_group.setAdapter(historiadapter);
        adapter = new BaseLoctionsAdapter<QueryMapsBeans.DataBean>(this, R.layout.item_loctions_lists) {

            @Override
            public void convert(BaseViewHolder helper, final QueryMapsBeans.DataBean item) {
                name=edit_adddress.getText().toString();
                helper.setText(R.id.tv_Loction_Name, item.getName());

            }
        };
        listView.setAdapter(adapter);
        gradview_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 查询数据类型
                ProxyUtils.getHttpProxy().querymaps(MapListsActivity.this,historiadapter.getItem(position));
            }
        });
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPreferManager.savehostirysocary("");
                historiadapter.setDatas(new ArrayList<String>());
                historiadapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    protected void initComponent() {

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
                        if(NewPreferManager.gethostirysocary().equals("")){
                            List <String> list= new ArrayList<String>() ;
                            list.add(result);
                            NewPreferManager.savehostirysocary(gson.toJson(list));
                        }else {
                            List <String> list= gson.fromJson(NewPreferManager.gethostirysocary(),List.class) ;
                            if(list.contains(result)){

                            }else {
                                list.add(result);
                                NewPreferManager.savehostirysocary(gson.toJson(list));
                            }



                        }


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
                Intent intent =new Intent(MapListsActivity.this,MapDetailsActivity.class);
                intent.putExtra("id",adapter.getItem(i-1).getId());
                intent.putExtra("id",adapter.getItem(i-1).getId());
                intent.putExtra("id",adapter.getItem(i-1).getId());
                startActivity(intent);

            }
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


        }
    }
    private void querymaps(QueryMapsBeans data) {
        if(data.getData()!=null&&data.getData().size()==0){
            ll_no.setVisibility(View.VISIBLE);
            ll_historicalrecords.setVisibility(View.GONE);
            adapter.setDatas(data.getData());
        }else {
            ll_historicalrecords.setVisibility(View.GONE);
            ll_no.setVisibility(View.GONE);
            adapter.setDatas(data.getData());
        }


    }


    @Override
    protected void showErrorMessage(String errorMessage) {


        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if(errorCode==1){
            ll_no.setVisibility(View.VISIBLE);
            ll_historicalrecords.setVisibility(View.GONE);

            adapter.setDatas(lists);
        }

        super.showErrorMessage(errorCode, errorMessage);
    }


}
