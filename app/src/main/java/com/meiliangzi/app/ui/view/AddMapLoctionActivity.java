package com.meiliangzi.app.ui.view;

import android.Manifest;
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

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AddMapBean;
import com.meiliangzi.app.model.bean.MapTypeListsBean;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.widget.DialogSelectPhoto;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddMapLoctionActivity extends BaseActivity implements View.OnClickListener , PermissionListener {
    @BindView(R.id.iput_cityname)
    TextView iput_cityname;
    @BindView(R.id.iput_countyname)
    TextView iput_countyname;
    @BindView(R.id.loction_name)
    EditText loction_name;
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private static final int REQUEST_CODE_SETTING = 101;
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

    @BindView(R.id.image_add_image)
    ImageView image_add_image;
    private DialogSelectPhoto dialogSelect;
    private String path;

    @BindView(R.id.edit_describe)
     EditText edit_describe;
    @BindView(R.id.tv_dadian)
     TextView tv_dadian;
    private String cityname;
    private String countyname;

    private String phone;
    private String describe;
    private String image;
    private String name;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_add_map_loction);

        Inflater = LayoutInflater.from(this);
    }

    @Override
    protected void findWidgets() {
        tvEmpty.setOnClickListener(this);
        image_add_image.setOnClickListener(this);
        lat =getIntent().getStringExtra("lat");
        lng =getIntent().getStringExtra("lng");

        tv_dadian.setOnClickListener(this);

        if("111".equals(getIntent().getStringExtra("type"))){
            id=String.valueOf(getIntent().getIntExtra("id",1));
            cityname =getIntent().getStringExtra("cityname");
            countyname =getIntent().getStringExtra("countyname");
            phone =getIntent().getStringExtra("phone");
            image =getIntent().getStringExtra("image");
            describe=getIntent().getStringExtra("describe");
            name=getIntent().getStringExtra("name");
            classification_id=getIntent().getIntExtra("classification_id",0);
            cityid=getIntent().getIntExtra("cityid",0);
            county_id=getIntent().getIntExtra("county_id",0);
            lat_id.setText(lat);
            lng_id.setText(lng);
            iput_cityname.setText(cityname);
            iput_countyname.setText(countyname);
            phene_number.setText(phone);
            loction_name.setText(name);
            ImageLoader.getInstance().displayImage(image, image_add_image, MyApplication.getSimpleOptions(R.mipmap.test_user_star, R.mipmap.test_user_star));

            edit_describe.setText(describe);
        }
        if(lat!=null&&lng!=null){
            tv_dadian.setText("已打点");
        }else {
            tv_dadian.setText("未打点");
        }


    }

    @Override
    protected void initComponent() {
        dialogSelect = new DialogSelectPhoto();
        dialogSelect.setType(1);
        if (TextUtils.isEmpty(NewPreferManager.getId())) {
//            if (TextUtils.isEmpty(PreferManager.getUserId())) {
//                tvEmpty.setText("请先登录");
//            } else {
//                tvEmpty.setText("请完善个人信息");
//            }
            tvEmpty.setText("请先登录");
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
            case R.id.image_add_image:
                AndPermission.with(this)
                        .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                    .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(AddMapLoctionActivity.this, rationale).show();
                        }
                    })
                    .send();
                break;
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
//                    String lng=lng_id.getText().toString().trim();
//                    String lat=lat_id.getText().toString().trim();

                    String describe=edit_describe.getText().toString().trim();
                    if("111".equals(getIntent().getStringExtra("type"))){
                        ProxyUtils.getHttpProxy().updateMap(AddMapLoctionActivity.this,NewPreferManager.getId(),name,cityid,county_id,classification_id,lng,lat,image,describe,id);

                    }else {
                        ProxyUtils.getHttpProxy().addmaps(AddMapLoctionActivity.this,NewPreferManager.getId(),name,cityid,county_id,classification_id,phone,lng,lat,path,describe);

                    }
                   } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case R.id.tvEmpty:
                if (TextUtils.isEmpty(NewPreferManager.getId())) {
                    Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

                break;
            case R.id.tv_dadian:
                Intent addintent=new Intent(this,AddMapActivity.class);
                startActivityForResult(addintent,101);

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

            switch (requestCode) {
                case 103:
                    if(data!=null){
                        cityid=data.getIntExtra("cityid",0);
                        String string=    data.getStringExtra("cityname");
                        iput_cityname.setText(data.getStringExtra("cityname"));
                    }

                    break;
                case 104:
                    if(data!=null){
                        iput_countyname.setText(data.getStringExtra("countyname"));
                        county_id=data.getIntExtra("county_id",0);
                    }

                    break;
                case 101:
                    if(data!=null){
                        lng= data.getStringExtra("lng");
                        lat=data.getStringExtra("lat");
                        if(lat!=null&&lng!=null){
                            tv_dadian.setText("已打点");
                        }else {
                            tv_dadian.setText("未打点");
                        }
                    }

                    break;

                default:

                    path = dialogSelect.onActivityResult(this, requestCode, resultCode, data);
                    if(path!=null){
                        ImageLoader.getInstance().displayImage("file:///" + path, image_add_image, MyApplication.getSimpleOptions(R.mipmap.test_user_star, R.mipmap.test_user_star));

                        updatePersonInfo(new File(path));
                    }
                    break;

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    public void updatePersonInfo(File img) {
        ProxyUtils.getHttpProxy().imageupload(this,img);
    }
    private void imageupload(AddMapBean bean){
        path=bean.getData();

    }
    @Override
    public void onBackClick(View v) {
        Intent intent =new Intent(this,MapNewActivity.class);
        startActivity(intent);
        finish();
      //  super.onBackClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        dialogSelect.getSelectPhoto(this);
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING).show();
        }
    }
}
