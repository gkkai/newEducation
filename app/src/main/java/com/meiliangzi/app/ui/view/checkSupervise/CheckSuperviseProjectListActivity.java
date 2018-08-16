package com.meiliangzi.app.ui.view.checkSupervise;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.meiliangzi.app.R;
import com.amap.api.maps2d.Projection;
import com.google.gson.Gson;
import com.meiliangzi.app.model.bean.CheckDepartmentsBean;
import com.meiliangzi.app.model.bean.CheckProjectBean;
import com.meiliangzi.app.model.bean.CountyListbean;
import com.meiliangzi.app.model.bean.VoteSubvotelistBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.vote.VoteActivity;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.utils.L;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckSuperviseProjectListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.dataEmpty)
    TextView dataEmpty;

    @BindView(R.id.text_screen)
    TextView text_screen;
    @BindView(R.id.listView)
    XListView listView;
    private View inflate;
    private Dialog dialog;
    private MyGridView myGridViewPartsecreen;
    private MyGridView myGridViewtiem;
    private MyGridView myGreiviewmouth;
    TextView tvReset;
    TextView tvDone;
    TextView text_project_nature_1;
    TextView text_project_nature_2;
    private  String endYear="-1";
    private  String endmouth="-1";
    private  int nature=-1;
    private int departId=-1;
    private int pos;
    private String  ischeck="";
    private int postime;
    private int posmouth;
    private String  ischecktime="";
    private String  ischecktmouth="";
    private List<String> tiems;
    private List<String> mouths;
    BaseVoteAdapter<String> tiemAdapter;
    BaseVoteAdapter<String> tiemmouthAdapter;
    BaseVoteAdapter<CheckProjectBean.DataBean> projectAdapter;
    BaseVoteAdapter<CheckDepartmentsBean.DataBean> departsreceenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_check_supervise_project_list);
    }

    @Override
    protected void findWidgets() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        endYear=sdf.format(new java.util.Date());
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        tiems=new ArrayList<>();

        tiems.add(0,"2017");
        tiems.add(1,"2018");
        tiems.add(2,"2019");
        tiems.add(3,"2020");
        mouths=new ArrayList<>();
        mouths.add(0,"全部");
        mouths.add(1,"1月");
        mouths.add(2,"2月");
        mouths.add(3,"3月");
        mouths.add(4,"4月");
        mouths.add(5,"5月");
        mouths.add(6,"6月");
        mouths.add(7,"7月");
        mouths.add(8,"8月");
        mouths.add(9,"9月");
        mouths.add(10,"10月");
        mouths.add(11,"11月");
        mouths.add(12,"12月");
        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.check_screen_dialog_layout, null);
        myGridViewPartsecreen  = (MyGridView) inflate.findViewById(R.id.myGreiviewpartsecreen);
        myGridViewtiem= (MyGridView) inflate.findViewById(R.id.myGridView_timesecreen);
        myGreiviewmouth=(MyGridView)inflate.findViewById(R.id.myGreiviewmouth);

        tvReset=(TextView) inflate.findViewById(R.id.tvReset);
        tvDone=(TextView) inflate.findViewById(R.id.tvDone);
        text_project_nature_1 = (TextView) inflate.findViewById(R.id.text_project_nature_1);
        text_project_nature_2=(TextView) inflate.findViewById(R.id.text_project_nature_2);
        tvReset.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        text_project_nature_1.setOnClickListener(this);
        text_project_nature_2.setOnClickListener(this);
        tiemAdapter=new BaseVoteAdapter<String>(CheckSuperviseProjectListActivity.this, R.layout.item_check_time_select) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                if(postime==getPosition()&&ischecktime.equals("ischeck")){
                    helper.setText(R.id.text_depart, item);
                    helper.getView(R.id.text_depart).setBackground(getResources().getDrawable(R.mipmap.checktimebackground));
                    ((TextView)helper.getView(R.id.text_depart)).setTextColor(getResources().getColor(R.color.white));

                }else {
                    ((TextView)helper.getView(R.id.text_depart)).setTextColor(getResources().getColor(R.color.black6));

                    helper.getView(R.id.text_depart).setBackground(getResources().getDrawable(R.drawable.shape_check_gray));

                    helper.setText(R.id.text_depart, item);
                }
            }
        };
        myGridViewtiem.setAdapter(tiemAdapter);
        myGridViewtiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                postime=position;
                ischecktime="ischeck";
                endYear=tiemAdapter.getItem(position);
                tiemAdapter.notifyDataSetChanged();
            }
        });
        tiemmouthAdapter=new BaseVoteAdapter<String>(CheckSuperviseProjectListActivity.this, R.layout.item_check_time_select) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                if(posmouth==getPosition()&&ischecktmouth.equals("ischeck")){
                    helper.setText(R.id.text_depart, item);
                    helper.getView(R.id.text_depart).setBackground(getResources().getDrawable(R.mipmap.checktimebackground));
                    ((TextView)helper.getView(R.id.text_depart)).setTextColor(getResources().getColor(R.color.white));

                }else {
                    ((TextView)helper.getView(R.id.text_depart)).setTextColor(getResources().getColor(R.color.black6));

                    helper.getView(R.id.text_depart).setBackground(getResources().getDrawable(R.drawable.shape_check_gray));

                    helper.setText(R.id.text_depart, item);
                }
            }
        };

        myGreiviewmouth.setAdapter(tiemmouthAdapter);
        myGreiviewmouth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posmouth=position;
                ischecktmouth="ischeck";
                if(position==0){
                    endmouth= "-1";
                } else if(position<10){
                    endmouth="0"+position;

                }else {
                    endmouth=  position+"";
                }
                //endmouth=tiemAdapter.getItem(position);
               // myGridViewtiem.notifyDataSetChanged();
                tiemmouthAdapter.notifyDataSetChanged();
            }
        });

        //部门列表
        departsreceenAdapter = new BaseVoteAdapter<CheckDepartmentsBean.DataBean>(CheckSuperviseProjectListActivity.this, R.layout.item_check_select) {
            @Override
            public void convert(final BaseViewHolder helper, CheckDepartmentsBean.DataBean item) {
                if(pos==getPosition()&&ischeck.equals("ischeck")){
                    helper.setText(R.id.text_depart, item.getName());
                    ((TextView)helper.getView(R.id.text_depart)).setTextColor(getResources().getColor(R.color.white));
                    helper.getView(R.id.text_depart).setBackground(getResources().getDrawable(R.mipmap.checkdepartbackground));

                }else {
                    ((TextView)helper.getView(R.id.text_depart)).setTextColor(getResources().getColor(R.color.black6));
                    helper.getView(R.id.text_depart).setBackground(getResources().getDrawable(R.drawable.shape_check_gray));

                    helper.setText(R.id.text_depart, item.getName());
                }




            }
        };
        myGridViewPartsecreen.setAdapter(departsreceenAdapter);
        myGridViewPartsecreen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                ischeck="ischeck";
                departId=((CheckDepartmentsBean.DataBean)departsreceenAdapter.getItem(position)).getId();
                departsreceenAdapter.notifyDataSetChanged();


            }
        });
        projectAdapter=new BaseVoteAdapter<CheckProjectBean.DataBean>(CheckSuperviseProjectListActivity.this, R.layout.item_checkproject_list) {
            @Override
            public void convert(BaseViewHolder helper, CheckProjectBean.DataBean item) {
                helper.setText(R.id.text_proName,item.getProName());
                helper.setText(R.id.text_departmentName,item.getDepartmentName());
                helper.setText(R.id.text_userName,item.getUserName());
                helper.setText(R.id.text_time,item.getStart_at()+"~"+item.getEnd_at());
                if(1==item.getProject_type()){
                    if(0==item.getProject_status()){
                        //TODO 白灯
                        //helper.setb(R.id.image_checkstate,null);
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.white));
                    }else if(1==item.getProject_status()){
                        //TODO 绿灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.greaden));

                    }else if(2==item.getProject_status()){
                        //TODO 黄灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.yellow));

                    }else if(3==item.getProject_status()){
                        //TODO 红灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.red));

                    }else if (4==item.getProject_status()){
                        //TODO 黑灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.black));

                    }
                }else {
                    if(0==item.getProject_status()){
                        //TODO 白灯
                        //helper.setb(R.id.image_checkstate,null);
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.purwhite));
                    }else if(1==item.getProject_status()){
                        //TODO 绿灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.purgreed));

                    }else if(2==item.getProject_status()){
                        //TODO 黄灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.puryellow));

                    }else if(3==item.getProject_status()){
                        //TODO 红灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.purred));

                    }else if (4==item.getProject_status()){
                        //TODO 黑灯
                        ((ImageView)helper.getView(R.id.image_checkstate)).setBackground(getResources().getDrawable(R.mipmap.purblack));

                    }
                }




            }
        };
        text_screen.setOnClickListener(this);
        listView.setAdapter(projectAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CheckSuperviseProjectListActivity.this,CheckProjectDatasActivity.class);
                intent.putExtra("id",projectAdapter.getItem(position-1).getId());
                intent.putExtra("projecttype",projectAdapter.getItem(position-1).getProject_type());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initComponent() {

    }
    private void shownavigation( ) {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        if (inflate != null) {
            ViewGroup parentViewGroup = (ViewGroup) inflate.getParent();
            if (parentViewGroup != null ) {
                parentViewGroup.removeView(inflate);
            }
        }
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.TOP);
        dialogWindow.setWindowAnimations(R.style.enter_exit_animate);  //添加动画
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 1500;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()){
            if(TextUtils.isEmpty(PreferManager.getUserId())){
                tvEmpty.setText("请先登录");
            }else if(!PreferManager.getIsComplete()){
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            //TODO 取得所有项目列表
            ProxyUtils.getHttpCheckProxy().projects(this,endYear,endmouth,departId,nature);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_screen:
                shownavigation( );
                break;

            case R.id.tvReset:
                endYear="-1";
                endmouth="-1";
                departId=-1;
                ischeck="";
                ischecktime="";
                ischecktmouth="";
                nature=-1;
                text_project_nature_2.setTextColor(getResources().getColor(R.color.ac_filter_nature));
                text_project_nature_2.setBackground(getResources().getDrawable(R.drawable.shape_check_gray));
                text_project_nature_1.setTextColor(getResources().getColor(R.color.ac_filter_nature));
                text_project_nature_1.setBackground(getResources().getDrawable(R.drawable.shape_check_gray));
                ProxyUtils.getHttpCheckProxy().projects(this,endYear,endmouth,departId,nature);
                dialog.dismiss();
                break;
            case R.id.tvDone:
                //TODO 取得所有项目列表
                if(ischecktime.equals("")){
                    //TODO 请选择时间
                    ToastUtils.show("请选择年份");
                }else if(ischecktmouth.equals("")){
                    //TODO 请选择部门
                    ToastUtils.show("请选择月份");

                } else if(ischeck.equals("")){
                    //TODO 请选择部门
                    ToastUtils.show("请选择部门");

                }else {
                    ProxyUtils.getHttpCheckProxy().projects(this,endYear,endmouth,departId,nature);
                    dialog.dismiss();
                }
                break;
            case R.id.text_project_nature_1:

                  nature=1;
                  text_project_nature_1.setTextColor(getResources().getColor(R.color.white));
                  text_project_nature_1.setBackground(getResources().getDrawable(R.mipmap.checkdepartbackground));
                  text_project_nature_2.setTextColor(getResources().getColor(R.color.ac_filter_nature));
                  text_project_nature_2.setBackground(getResources().getDrawable(R.drawable.shape_check_gray));


                break;
            case R.id.text_project_nature_2:

                    nature=2;
                    text_project_nature_2.setTextColor(getResources().getColor(R.color.white));
                    text_project_nature_2.setBackground(getResources().getDrawable(R.mipmap.checkdepartbackground));
                    text_project_nature_1.setTextColor(getResources().getColor(R.color.ac_filter_nature));
                    text_project_nature_1.setBackground(getResources().getDrawable(R.drawable.shape_check_gray));


                break;

        }

    }



    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
        tiemAdapter.setDatas(tiems);
        tiemmouthAdapter.setDatas(mouths);
        //TODO 取得所有部门列表
        ProxyUtils.getHttpCheckProxy().departments(this);

        //ProxyUtils.getHttpCheckProxy().projects(this);

    }
    private void getdepartments(CheckDepartmentsBean data){
        if(data!=null){
            departsreceenAdapter.setDatas(data.getData());
        }
    }
    private void getprojects(CheckProjectBean data){
        if(data!=null&&data.getData()!=null&&data.getData().size()!=0){
            dataEmpty.setVisibility(View.GONE);
            projectAdapter.setDatas(data.getData());
        }else {
            dataEmpty.setVisibility(View.VISIBLE);
            projectAdapter.setDatas(data.getData());
        }
    }
    @Override
    protected void initListener() {
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyWithSingleParam(CheckSuperviseProjectListActivity.this, LoginActivity.class, "activity", "WholeFragment");
                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyWithSingleParam(CheckSuperviseProjectListActivity.this, PersonCenterActivity.class, "activity", "WholeFragment");
                }
            }
        });
    }

}
