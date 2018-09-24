package com.meiliangzi.app.ui.view.sendcar;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.SendacardeleteBean;
import com.meiliangzi.app.model.bean.SendacarinfoBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class FkDataActivity extends BaseActivity {
    @BindView(R.id.text_fk_partment)
    TextView text_fk_partment;
    @BindView(R.id.text_fk_username)
    TextView text_fk_username;
    @BindView(R.id.text_fk_user)
    TextView text_fk_user;
    @BindView(R.id.text_fk_userphone)
    TextView text_fk_userphone;
    @BindView(R.id.text_fk_satrt)
    TextView text_fk_satrt;
    @BindView(R.id.text_fk_end)
    TextView text_fk_end;
    @BindView(R.id.text_fk_statttime)
    TextView text_fk_statttime;
    @BindView(R.id.text_fk_endtime)
    TextView text_fk_endtime;
    @BindView(R.id.text_fk_drivemUser)
    TextView text_fk_drivemUser;
    @BindView(R.id.text_fk_drivemPhone)
    TextView text_fk_drivemPhone;
    @BindView(R.id.text_fk_number)
    TextView text_fk_number;
    @BindView(R.id.text_fk_tiem)
    TextView text_fk_tiem;
    @BindView(R.id.text_fk_startmileage)
    EditText text_fk_startmileage;
    @BindView(R.id.text_fk_endmileage)
    EditText text_fk_endmileage;
    @BindView(R.id.text_fk_mileage)
    EditText text_fk_mileage;
    @BindView(R.id.edit_fk_remarks)
    EditText edit_fk_remarks;

    @BindView(R.id.text_sure)
    TextView text_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_fk_data);
    }

    @Override
    protected void findWidgets() {
        text_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String fktiem=   text_fk_tiem.getText().toString().trim();
                String startmileage=  text_fk_startmileage.getText().toString().trim();
                String endmileage=text_fk_endmileage.getText().toString().trim();
                String mileage= text_fk_mileage.getText().toString().trim();
                String remarks=edit_fk_remarks.getText().toString().trim();
                    if("updata".equals(getIntent().getStringExtra("type"))){
                       int i= getIntent().getIntExtra("sendACarId",0);
                        int i1= getIntent().getIntExtra("sendACarInfoId",0);
                        ProxyUtils.getHttpProxy().sendacarinfoupdate(FkDataActivity.this,getIntent().getIntExtra("sendACarInfoId",0),fktiem,
                                startmileage,endmileage,mileage,remarks);


                    }else {
                        ProxyUtils.getHttpProxy().sendacarinfoadd(FkDataActivity.this,getIntent().getIntExtra("sendACarId",0),fktiem,
                                startmileage,endmileage,mileage,remarks);

                    }


            }
        });
        text_fk_tiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shownavigation(text_fk_tiem);
            }
        });
    }

    @Override
    protected void initComponent() {
        ProxyUtils.getHttpProxy().sendacarinfo(this,getIntent().getIntExtra("sendACarId",0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year=Integer.valueOf(nowyear);
//TODO
        for(int i=0;i<20;i++){
            listyear.add(i,String.valueOf(year+i));
        }
        for(int i=0;i<12;i++){
            listmouth.add(String.valueOf(i+1));
        }

        for(int i=0;i<getMonthOfDay(year,3);i++){
            listdays.add(String.valueOf(i+1));
        }
        for(int i=0;i<24;i++){
            listtime.add(i+"");
        }
        for(int i=0;i<60;i++){
            listmin.add(i+"");
        }

    }
    //计算天数
    public static int getMonthOfDay(int year,int month){
        int day = 0;
        if(year%4==0&&year%100!=0||year%400==0){
            day = 29;
        }else{
            day = 28;
        }
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return day;

        }

        return 0;
    }
    protected void getsendacarinfoadd(SendacardeleteBean bean){
        finish();


    }
    protected void getsendacarinfo(SendacarinfoBean bean){
        text_fk_partment.setText(bean.getData().getDepartmentName());
        text_fk_username.setText(bean.getData().getProposer());
        text_fk_user.setText(bean.getData().getUser());
        text_fk_userphone.setText(bean.getData().getUserPhone());
        text_fk_satrt.setText(bean.getData().getStart());
        text_fk_end.setText(bean.getData().getEnd());
        text_fk_statttime.setText(bean.getData().getStartAt());
        text_fk_endtime.setText(bean.getData().getEndAt());
        text_fk_drivemUser.setText(bean.getData().getDriverName());
        text_fk_drivemPhone.setText(bean.getData().getDriverPhone());
        text_fk_number.setText(bean.getData().getPlateNumber());
        text_fk_tiem.setText(bean.getData().getReturnTime());
        text_fk_startmileage.setText(bean.getData().getInitialMileage());
        text_fk_endmileage.setText(bean.getData().getReturnMileage());
        text_fk_mileage.setText(bean.getData().getMileage());
        edit_fk_remarks.setText(bean.getData().getRemarks());

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }
    ArrayWheelAdapter dayadapter=new ArrayWheelAdapter(this);
    private Dialog dialog;
    private View inflate;
    private com.wx.wheelview.widget.WheelView year;
    private com.wx.wheelview.widget.WheelView mouth;
    private com.wx.wheelview.widget.WheelView days;
    private com.wx.wheelview.widget.WheelView time;
    private com.wx.wheelview.widget.WheelView min;
    List<String> listyear=new ArrayList<String>();
    List<String> listmouth=new ArrayList<String>();
    List<String> listdays=new ArrayList<String>();
    List<String> listtime=new ArrayList<String>();
    List<String> listmin=new ArrayList<String>();
    //TODO
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
    private String nowyear=sdf.format(new java.util.Date());
    private String selectyear;
    private  String selectmouth;
    private String selectdata;
    private String selecttime;
    private String selectmin;
    public void shownavigation(final TextView text) {
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_send_select_time, null);
        TextView text_sure=(TextView) inflate.findViewById(R.id.text_sure);
        TextView text_quxiao=(TextView) inflate.findViewById(R.id.text_quxiao);
        final TextView text_time=(TextView) inflate.findViewById(R.id.text_time);

        text_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text_time.getText());
                dialog.dismiss();
            }
        });
        text_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
                dialog.dismiss();

            }
        });

        year = (WheelView) inflate.findViewById(R.id.year);
        mouth = (WheelView) inflate.findViewById(R.id.mouth);
        days = (WheelView) inflate.findViewById(R.id.days);
        time = (WheelView) inflate.findViewById(R.id.time);
        min = (WheelView) inflate.findViewById(R.id.min);
        com.wx.wheelview.widget.WheelView.WheelViewStyle style = new com.wx.wheelview.widget.WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        year.setWheelAdapter(new ArrayWheelAdapter(this));
        year.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        year.setWheelSize(5);
        year.setWheelData(listyear);
        year.setStyle(style);
        year.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        mouth.setWheelAdapter(new ArrayWheelAdapter(this));
        mouth.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        mouth.setWheelSize(5);
        mouth.setSelection(3);
        mouth.setWheelData(listmouth);
        mouth.setStyle(style);
        mouth.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        days.setWheelAdapter(dayadapter);
        days.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        days.setWheelSize(5);
        days.setSelection(3);
        days.setWheelData(listdays);
        days.setStyle(style);
        days.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        time.setWheelAdapter(new ArrayWheelAdapter(this));
        time.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        time.setWheelSize(5);
        time.setSelection(3);
        time.setWheelData(listtime);
        time.setStyle(style);
        time.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        min.setWheelAdapter(new ArrayWheelAdapter(this));
        min.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        min.setWheelSize(5);
        min.setSelection(3);
        min.setWheelData(listmin);
        min.setStyle(style);
        min.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
        year.setOnWheelItemSelectedListener(new com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                selectyear=listyear.get(position);
                if(selectyear!=null&&selectmouth!=null){
                    listdays.clear();
                    for(int i=0;i<getMonthOfDay(Integer.valueOf(selectyear),Integer.valueOf(selectmouth));i++){
                        listdays.add(String.valueOf(i+1));
                    }
                    dayadapter.setData(listdays);
                }
                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);

            }
        });
        mouth.setOnWheelItemSelectedListener(new com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                selectmouth=listmouth.get(position);
                if(selectyear!=null&&selectmouth!=null){
                    listdays.clear();
                    for(int i=0;i<getMonthOfDay(Integer.valueOf(selectyear),Integer.valueOf(selectmouth));i++){
                        listdays.add(String.valueOf(i+1));
                    }
                    dayadapter.setData(listdays);
                }
                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);
            }
        });

        days.setOnWheelItemSelectedListener(new com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                if(listdays.size()!=0){
                    selectdata=listdays.get(position);
                }
                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);

            }

        });
        time.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                selecttime=listtime.get(position);
                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);

            }
        });
        min.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                selectmin=listmin.get(position);
                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);

            }
        });

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
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();//显示对话框

    }
}
