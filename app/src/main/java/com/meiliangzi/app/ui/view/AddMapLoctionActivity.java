package com.meiliangzi.app.ui.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AddMapBean;
import com.meiliangzi.app.model.bean.MapTypeListsBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.MyDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddMapLoctionActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iput_cityname)
    TextView iput_cityname;
    @BindView(R.id.iput_countyname)
    TextView iput_countyname;
    @BindView(R.id.loction_name)
    EditText loction_name;

    @BindView(R.id.phene_number)
    EditText phene_number;
    @BindView(R.id.lat_id)
    EditText lat_id;
    @BindView(R.id.lng_id)
    EditText lng_id;
    @BindView(R.id.sure)
    TextView sure;
    @BindView(R.id.image_citylist)
    ImageView image_citylist;
    @BindView(R.id.image_countylist)
    ImageView image_countylist;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private int cityid=0;
    private int county_id;
    @BindView(R.id.ll_map_type)
    LinearLayout ll_map_type;//展示地图类型列表
    private int postiton=0;
    private String lat;
    private String lng;
    private List<TextView> listviews=new ArrayList<>();//
    private List<MapTypeListsBean.DataBean> typeBean=new ArrayList<>();
    private int classification_id;
    private LayoutInflater Inflater;
    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_add_map_loction);
        Inflater = LayoutInflater.from(this);
    }

    @Override
    protected void findWidgets() {
        tvEmpty.setOnClickListener(this);
        lat =getIntent().getStringExtra("lat");
        lng =getIntent().getStringExtra("lng");
        lat_id.setText(lat);
        lng_id.setText(lng);
        String lng=lng_id.getText().toString().trim();
        String lat=lat_id.getText().toString().trim();
    }

    @Override
    protected void initComponent() {
        if (TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()) {
            if (TextUtils.isEmpty(PreferManager.getUserId())) {
                tvEmpty.setText("请先登录");
            } else {
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);

        }
//TODO 查询数据类型
        ProxyUtils.getHttpProxy().classificationlist(AddMapLoctionActivity.this);
    }
    /**
     * 返回数据类型
     */
    protected  void  classificationlist(MapTypeListsBean data){
        typeBean=data.getData();
        addView1(data.getData());

    }
    private void addView1(List<MapTypeListsBean.DataBean> dataBeans) {
        listviews.clear();
        ll_map_type.removeAllViews();
        for (int i = 0; i < dataBeans.size(); i++) {
            View view = Inflater.inflate(R.layout.horizontalscrollview_add_map_item,// 找到布局文件
                    ll_map_type, false);
            TextView txt = (TextView) view
                    .findViewById(R.id.id_index_gallery_item_text);

            if(i==postiton){
                txt.setBackground(getResources().getDrawable(R.drawable.shape_type));
                txt.setTextColor(Color.WHITE);
                classification_id=typeBean.get(i).getId();
            }

            txt.setText(dataBeans.get(i).getName());
            txt.setId(100+i);
            txt.setOnClickListener(this);
            ViewGroup parent = (ViewGroup) txt.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            listviews.add(txt);
            ll_map_type.addView(txt);
        }
    }
    private View addView2(List<MapTypeListsBean.DataBean> dataBeans) {


        // TODO 动态添加布局(java方式)
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(120, 65, 1.0f);
        vlp.gravity = Gravity.CENTER_VERTICAL;
        for (int i = 0; i < dataBeans.size(); i++) {
            postiton=i;
            final TextView tv1 = new TextView(this);
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(14f);
            //tv1.setPadding(10, 10, 10, 10);
            vlp.setMargins(50, 50, 50, 50);
            tv1.setLayoutParams(vlp);
            if(i==0){
                tv1.setBackground(getResources().getDrawable(R.drawable.shape_addname_textview));
                //TODO 返回类型
                classification_id=typeBean.get(i).getId();
            }
            tv1.setText(dataBeans.get(i).getName());
            tv1.setId(100+i);
            tv1.setOnClickListener(this);
            listviews.add(tv1);
            ll_map_type.addView(tv1);//将TextView 添加到子View 中
        }
        return ll_map_type;
    }

    @Override
    protected void initListener() {
        image_citylist.setOnClickListener(this);
        image_countylist.setOnClickListener(this);
        iput_cityname.setOnClickListener(this);
        iput_countyname.setOnClickListener(this);
        sure.setOnClickListener(this);
        super.initListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_citylist:
                Intent cityintent=new Intent(this,AddCityListsActivity.class);
                cityintent.putExtra("type","city");
                startActivityForResult(cityintent,103);
                break;
            case R.id.iput_cityname:
                Intent cityname=new Intent(this,AddCityListsActivity.class);
                cityname.putExtra("type","city");
                startActivityForResult(cityname,103);
                break;
            case R.id.image_countylist:
                try {
                    RuleCheckUtils.checkEmpty(iput_cityname.getText().toString(), "请选择所在市");
                    Intent countyintent = new Intent(this, AddCityListsActivity.class);
                    countyintent.putExtra("type", "county");
                    countyintent.putExtra("cityid", cityid);
                    startActivityForResult(countyintent, 104);
                } catch (Exception e) {
                ToastUtils.custom(e.getMessage());
                e.printStackTrace();
            }
                break;
            case R.id.iput_countyname:
                try {
                    RuleCheckUtils.checkEmpty(iput_cityname.getText().toString(), "请选择所在市");
                    Intent countyintent = new Intent(this, AddCityListsActivity.class);
                    countyintent.putExtra("type", "county");
                    countyintent.putExtra("cityid", cityid);
                    startActivityForResult(countyintent, 104);
                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case R.id.sure:
                //TODO 上传后台
                try {
                    RuleCheckUtils.checkEmpty(iput_cityname.getText().toString(), "请选择所在市");

                    RuleCheckUtils.checkEmpty(iput_countyname.getText().toString(), "请选择所在县");
                    RuleCheckUtils.checkEmpty(loction_name.getText().toString(), "请输入地址名称");
                    //TODO 地理位置添加
                    String name=loction_name.getText().toString();
                    //TODO 手机号码
                    String phone=phene_number.getText().toString();
                    String lng=lng_id.getText().toString().trim();
                    String lat=lat_id.getText().toString().trim();
                    ProxyUtils.getHttpProxy().addmapss(AddMapLoctionActivity.this,Integer.valueOf(PreferManager.getUserId()),name,cityid,county_id,classification_id,phone,lng,lat);
                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case R.id.tvEmpty:
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else if (!PreferManager.getIsComplete()) {
                    Intent intent=new Intent(this,PersonCenterActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
            default: setType(view.getId());
        }

    }
    private void setType(int id){
            ll_map_type.removeAllViews();
            for (int i=0;i<listviews.size();i++){
                TextView textView=listviews.get(i);
                if(id==100+i){
                    textView .setBackground(getResources().getDrawable(R.drawable.shape_type));
                    //TODO 返回类型
                    classification_id=typeBean.get(i).getId();
                    textView.setTextColor(Color.WHITE);
                }else {
                    textView.setBackground(getResources().getDrawable(R.drawable.shape_gray));
                    textView.setTextColor(Color.GRAY);
                }
                ll_map_type.addView(textView);
            }
    }
    /**
     * 返回数据类型
     */
    protected  void  addmaps(AddMapBean data){
        if(data.getStatus()==0){
            {
                //ToastUtils.show("请您先标记位置");
                myDialog = new MyDialog(this);
                myDialog.setMessage("地址添加完成，请等待后台审核");
                myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        Intent intent= new Intent(AddMapLoctionActivity.this,MapNewActivity.class);
                        startActivity(intent);
                        finish();
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

        }else {
            ToastUtils.show(data.getMsg());
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==105){

        }else {
            if(requestCode==103){
                cityid=data.getIntExtra("cityid",0);
                String string=    data.getStringExtra("cityname");
                iput_cityname.setText(data.getStringExtra("cityname"));
            }else if(requestCode==104){
                iput_countyname.setText(data.getStringExtra("countyname"));
                county_id=data.getIntExtra("county_id",0);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackClick(View v) {
        Intent intent =new Intent(this,MapNewActivity.class);
        startActivity(intent);
        finish();
      //  super.onBackClick();
    }
}
