package com.meiliangzi.app.ui.view.sendcar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.QueryuserBean;
import com.meiliangzi.app.model.bean.SendACarInfoArray;
import com.meiliangzi.app.model.bean.SendacarinfoBean;
import com.meiliangzi.app.receiver.CounterServer;
import com.meiliangzi.app.receiver.TagAliasOperatorHelper;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.SetttingActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.view.Academy.NewLoginActivity;
import com.meiliangzi.app.widget.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.meiliangzi.app.config.Constant.BASE_URL;

public class AddSendacarActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gradview)
    MyGridView gradview;
    @BindView(R.id.text_add_next)
    TextView text_add_next;
    @BindView(R.id.text_sure)
    TextView text_sure;
    @BindView(R.id.edit_add_send_department)
    TextView text_add_send_department;

    @BindView(R.id.text_add_send_name)
    TextView text_add_send_name;
    @BindView(R.id.text_titel)
    TextView text_titel;
    private  String userPhone;
    private List<SendACarInfoArray> data=new ArrayList<SendACarInfoArray>();
    private BaseQuickAdapter<SendACarInfoArray> adapter;
    String type="";
    @BindView(R.id.svid)
    ScrollView mScrollView;
    private int h;
    int width;
    private MyDialog myDialog;
    private TimePickerView pvTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_add_sendacar);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width= wm.getDefaultDisplay().getWidth();
        if( MyApplication.driverList!=null){
            MyApplication.driverList.clear();
        }


    }

    @Override
    protected void findWidgets() {
        myDialog=new MyDialog(this);
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


            }
        });
        myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.6f;
                getWindow().setAttributes(lp);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
        initTimePicker();
        type=getIntent().getStringExtra("type");
        if("updata".equals(type)){
            //TODO 修改用车申请
            text_titel.setText("车程修改");
            text_add_send_department.setFocusable(false);
            text_add_next.setVisibility(View.GONE);
            adapter=new BaseQuickAdapter<SendACarInfoArray>(this,R.layout.add_send) {


                @Override
                public void convert(BaseViewHolder helper, SendACarInfoArray item) {

                }

                @Override
                public void convert(final BaseViewHolder helper, SendACarInfoArray item, final int positino) {
                    //helper.getView(R.id.edit_add_send_user).setFocusable(false);
                    //helper.getView(R.id.edit_add_send_userPhone).setFocusable(false);

                    ((EditText)helper.getView(R.id.edit_add_send_user)).setText(item.getUser());
                    ((EditText)helper.getView(R.id.edit_add_send_userPhone)).setText(item.getUserPhone());
                    ((EditText)helper.getView(R.id.edit_add_send_start)).setText(item.getStart());
                    ((EditText)helper.getView(R.id.edit_add_send_end)).setText(item.getEnd());
                    ((TextView)helper.getView(R.id.text_add_send_startAt)).setText(item.getStartAt());
                    ((TextView)helper.getView(R.id.text_add_send_endAt)).setText(item.getEndAt());
                    ((TextView)helper.getView(R.id.text_add_send_driverName)).setText(item.getDriverName());
                    ((TextView)helper.getView(R.id.text_add_send_driverPhone)).setText(item.getDriverPhone());
                    ((TextView)helper.getView(R.id.text_add_send_plateNumber)).setText(item.getPlateNumber());

                    // TODO 监听驾驶员输入姓名

                    helper.getView(R.id.text_add_send_startAt).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pvTime.show(helper.getView(R.id.text_add_send_startAt));
                            //shownavigation((TextView) helper.getView(R.id.text_add_send_startAt),positino);

                        }
                    });
                    helper.getView(R.id.text_add_send_endAt).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pvTime.show( helper.getView(R.id.text_add_send_endAt));
                            //shownavigation((TextView)helper.getView(R.id.text_add_send_endAt),positino);

                        }
                    });
                    helper.getView(R.id.text_add_send_driverName).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(AddSendacarActivity.this,DriverUserLisActivity.class);
                            intent.putExtra("positino",positino);
                            intent.putExtra("type","updata");
                            startActivityForResult(intent,101);

                        }
                    });
                    helper.getView(R.id.text_add_send_plateNumber).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(AddSendacarActivity.this,DriverListActivity.class);
                            intent.putExtra("positino",positino);
                            startActivityForResult(intent,102);
                        }
                    });



                }
            };
            ProxyUtils.getHttpProxy().sendacarinfo(this,getIntent().getIntExtra("sendACarId",0));

        }else {
            //TODO 添加用车申请
            adapter=new BaseQuickAdapter<SendACarInfoArray>(this,R.layout.add_send) {


                @Override
                public void convert(BaseViewHolder helper, SendACarInfoArray item) {

                }

                @Override
                public void convert(final BaseViewHolder helper, SendACarInfoArray item, final int positino) {
                    // TODO 监听驾驶员输入姓名
                    if(positino==0){
                        helper.showOrHideView(R.id.tv_delete,false);
                    }else {
                        helper.showOrHideView(R.id.tv_delete,true);

                    }
                    helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.setMessage("确认是否删除");
                            myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                                @Override
                                public void onNoClick() {
                                    myDialog.dismiss();
                                }
                            });
                            myDialog.setYesOnclickListener("确认", new MyDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    adapter.getmDatas().remove(positino);
                                    adapter.notifyDataSetChanged();
                                    myDialog.dismiss();
                                }
                            });
                            myDialog.show();

                        }
                    });

                    helper.getView(R.id.text_add_send_startAt).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pvTime.show(helper.getView(R.id.text_add_send_startAt));
                            //shownavigation((TextView) helper.getView(R.id.text_add_send_startAt),positino);

                        }
                    });
                    helper.getView(R.id.text_add_send_endAt).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pvTime.show(helper.getView(R.id.text_add_send_endAt));
                            //shownavigation((TextView)helper.getView(R.id.text_add_send_endAt),positino);

                        }
                    });
                    helper.getView(R.id.text_add_send_driverName).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(AddSendacarActivity.this,DriverUserLisActivity.class);
                            intent.putExtra("positino",positino);
                            intent.putExtra("type","add");
                            startActivityForResult(intent,101);

                        }
                    });
                    //TODO 车牌号修改
                    helper.getView(R.id.text_add_send_plateNumber).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String s=((TextView)helper.getView(R.id.text_add_send_plateNumber)).getHint().toString().trim();
                            if(data.get(positino).getQueryuserDatalist().size()==0){
                                ToastUtils.show("请您先选择驾驶员");
                            }
                            else  {
                                Intent intent=new Intent(AddSendacarActivity.this,AddCardNumberActivity.class);
                                intent.putExtra("positino",positino);
                                startActivityForResult(intent,101);
                            }

                        }
                    });

                }
            };

        }

        data.add(new SendACarInfoArray());
        adapter.setDatas(data);
        gradview.setAdapter(adapter);
        text_add_next.setOnClickListener(this);
        text_sure.setOnClickListener(this);
        text_add_send_name.setOnClickListener(this);


    }
    protected void getsendacarinfo(SendacarinfoBean bean){
        userPhone=bean.getData().getProposerPhone();
        text_add_send_department.setText(bean.getData().getDepartmentName());
        text_add_send_name.setText(bean.getData().getProposer());
        data.get(0).setUser(bean.getData().getUser());
        data.get(0).setUserPhone(bean.getData().getUserPhone());
        data.get(0).setStart(bean.getData().getStart());
        data.get(0).setEnd(bean.getData().getEnd());
        data.get(0).setStart(bean.getData().getStart());
        data.get(0).setDriverName(bean.getData().getDriverName());
        data.get(0).setDriverPhone(bean.getData().getDriverPhone());
        data.get(0).setPlateNumber(bean.getData().getPlateNumber());
        data.get(0).setEndAt(bean.getData().getEndAt());
        data.get(0).setStartAt(bean.getData().getStartAt());
        data.get(0).setDriverUserid(bean.getData().getDriverUserId());
        adapter.setDatas(data);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (resultCode){
            case 101:
                if(intent.getIntExtra("type",3)==2){
                    data.get(intent.getIntExtra("positino",100)).getQueryuserDatalist().clear();
                    TextView   text_add_send_driverName=((TextView)getViewByPosition(intent.getIntExtra("positino",100),gradview).findViewById(R.id.text_add_send_driverName));
                    TextView   driverPhone=  ((TextView)getViewByPosition(intent.getIntExtra("positino",100),gradview).findViewById(R.id.text_add_send_driverPhone));
                    TextView   plateNumber= ((TextView)getViewByPosition(intent.getIntExtra("positino",100),gradview).findViewById(R.id.text_add_send_plateNumber));

                    StringBuilder driverName=new StringBuilder();
                    StringBuilder driverNumber=new StringBuilder();
                    StringBuilder drivephone=new StringBuilder();
                    for(int i=0;i< MyApplication.driverList.size();i++){
                        driverName.append((MyApplication.driverList.get(i)).getDriverName()+",");
                        driverNumber.append((MyApplication.driverList.get(i)).getPlateNumber()+",");
                        drivephone.append((MyApplication.driverList.get(i)).getDriverPhone()+",");
                        SendACarInfoArray.QueryuserData ss=new SendACarInfoArray.QueryuserData();
                        ss.setDriverName(MyApplication.driverList.get(i).getDriverName());
                        ss.setPlateNumber(MyApplication.driverList.get(i).getPlateNumber());
                        ss.setId(MyApplication.driverList.get(i).getId());
                        ss.setDriverPhone(MyApplication.driverList.get(i).getDriverPhone());

                        data.get(intent.getIntExtra("positino",100)).getQueryuserDatalist().add(i,ss);

                    }
                    if (driverName.length() > 0){
                        driverName.deleteCharAt(driverName.length() - 1);
                    }
                    if (drivephone.length() > 0){
                        drivephone.deleteCharAt(drivephone.length() - 1);
                    }
                    if (driverNumber.length() > 0){
                        driverNumber.deleteCharAt(driverNumber.length() - 1);
                    }
                    text_add_send_driverName.setText(driverName);
                    driverPhone.setText(drivephone);
                    plateNumber.setText(driverNumber);
                   // data.add(MyApplication.driverList);

                    //MyApplication.driverList.clear();

                    break;
                }
                if(intent.getIntExtra("type",3)==1){
                    ((TextView)getViewByPosition(intent.getIntExtra("positino",100),gradview).findViewById(R.id.text_add_send_driverName)).setText(intent.getStringExtra("driverName"));
                    ((TextView)getViewByPosition(intent.getIntExtra("positino",100),gradview).findViewById(R.id.text_add_send_driverPhone)).setText(intent.getStringExtra("driverPhone"));
                    ((TextView)getViewByPosition(intent.getIntExtra("positino",100),gradview).findViewById(R.id.text_add_send_plateNumber)).setText(intent.getStringExtra("plateNumber"));
                    data.get(intent.getIntExtra("positino",100)).setDriverUserid(String.valueOf(intent.getIntExtra("id",0)));
                    break;
                }

            case 102:

                ((TextView)getViewByPosition(intent.getIntExtra("positino",100),gradview).findViewById(R.id.text_add_send_plateNumber)).setText(intent.getStringExtra("plateNumber"));
               // data.get(intent.getIntExtra("positino",100)).setDriverUserid(String.valueOf(intent.getIntExtra("id",0)));
                break;
            case 103:
                text_add_send_department.setText(intent.getStringExtra("departmentName"));
                text_add_send_name.setText(intent.getStringExtra("nickName"));
                userPhone=intent.getStringExtra("userPhone");
                break;
        }
    }

    @Override
    protected void initComponent() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        int year=Integer.valueOf(nowyear);
////TODO
//        for(int i=0;i<20;i++){
//            listyear.add(i,String.valueOf(year+i));
//        }
//        for(int i=0;i<12;i++){
//            listmouth.add(String.valueOf(i+1));
//        }
//
//        for(int i=0;i<getMonthOfDay(year,3);i++){
//            listdays.add(String.valueOf(i+1));
//        }
//        for(int i=0;i<24;i++){
//            listtime.add(i+"");
//        }
//        for(int i=0;i<60;i++){
//            listmin.add(i+"");
//        }
    }
    //计算天数
//    public static int getMonthOfDay(int year,int month){
//        int day = 0;
//        if(year%4==0&&year%100!=0||year%400==0){
//            day = 29;
//        }else{
//            day = 28;
//        }
//        switch (month){
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 12:
//                return 31;
//            case 4:
//            case 6:
//            case 9:
//            case 11:
//                return 30;
//            case 2:
//                return day;
//
//        }
//
//        return 0;
//    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.text_add_send_name:
               Intent intent=new Intent(this,AddUserSendCarActivity.class);
                startActivityForResult(intent,103);

                break;
            case R.id.text_add_next:
                data.add(new SendACarInfoArray());
                adapter.setDatas(data);

                mScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        //mScrollView.scrollTo(0, width+h);
                        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

                    }
                });
                break;
            case R.id.text_sure:
                if("updata".equals(type)){
                    for(int i=0;i<data.size();i++){
                        String user=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_user)).getText().toString();
                        data.get(i).setUser(user);
                        String userPhone=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_userPhone)).getText().toString();
                        data.get(i).setUserPhone(userPhone);
                        String start=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_start)).getText().toString();
                        data.get(i).setStart(start);
                        String end=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_end)).getText().toString();
                        data.get(i).setEnd(end);
                        String startAt=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_startAt)).getText().toString();
                        data.get(i).setStartAt(startAt);
                        String endAt=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_endAt)).getText().toString();
                        data.get(i).setEndAt(endAt);
                        String driverName=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_driverName)).getText().toString();
                        data.get(i).setDriverName(driverName);
                        String driverPhone=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_driverPhone)).getText().toString();
                        data.get(i).setDriverPhone(driverPhone);
                        String plateNumber=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_plateNumber)).getText().toString();
                        data.get(i).setPlateNumber(plateNumber);
                    }
                }else {
                    for(int i=0;i<data.size();i++){
                        String user=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_user)).getText().toString();
                        data.get(i).setUser(user);
                        String userPhone=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_userPhone)).getText().toString();
                        data.get(i).setUserPhone(userPhone);
                        String start=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_start)).getText().toString();
                        data.get(i).setStart(start);
                        String end=((EditText)getViewByPosition(i,gradview).findViewById(R.id.edit_add_send_end)).getText().toString();
                        data.get(i).setEnd(end);
                        String startAt=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_startAt)).getText().toString();
                        data.get(i).setStartAt(startAt);
                        String endAt=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_endAt)).getText().toString();
                        data.get(i).setEndAt(endAt);

//                        String driverName=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_driverName)).getText().toString();
//                        data.get(i).setDriverName(driverName);
//                        String driverPhone=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_driverPhone)).getText().toString();
//                        data.get(i).setDriverPhone(driverPhone);
//                        String plateNumber=((TextView)getViewByPosition(i,gradview).findViewById(R.id.text_add_send_plateNumber)).getText().toString();
//                        data.get(i).setPlateNumber(plateNumber);
                    }
                }


                try {
                    RuleCheckUtils.checkEmpty(text_add_send_department.getText().toString(), "请选择申请人");
                    RuleCheckUtils.checkEmpty(text_add_send_name.getText().toString(), "请选择申请人");
                    JSONObject js=new JSONObject();
                    JSONArray jsonArry=new JSONArray();
                    js.put("departmentName",text_add_send_department.getText().toString());
                    js.put("proposer",text_add_send_name.getText().toString());
                    js.put("proposerPhone",userPhone);
                    if("updata".equals(type)){
                        //TODO 修改
                        sendacarupdata(js);
                    }else {
                        //TODO 新增
                        for (int i = 0; i < data.size(); i++) {
                            JSONObject jsobject=new JSONObject();
                            JSONArray jsonArry1=new JSONArray();
                            jsobject.put("user", data.get(i).getUser());
                            jsobject.put("userPhone", data.get(i).getUserPhone());
                            jsobject.put("start", data.get(i).getStart());
                            jsobject.put("end", data.get(i).getEnd());
                            jsobject.put("startAt", data.get(i).getStartAt());
                            jsobject.put("endAt", data.get(i).getEndAt());
                            for(int j = 0; j < data.get(i).getQueryuserDatalist().size(); j++){
                                JSONObject jsobject1=new JSONObject();
                                jsobject1.put("driverName", data.get(i).getQueryuserDatalist().get(j).getDriverName());
                                jsobject1.put("driverPhone", data.get(i).getQueryuserDatalist().get(j).getDriverPhone());
                                jsobject1.put("plateNumber", data.get(i).getQueryuserDatalist().get(j).getPlateNumber());
                                jsobject1.put("driverUserid", data.get(i).getQueryuserDatalist().get(j).getId());
                                jsonArry1.put(jsobject1);
                            }
                            jsobject.put("driverInfoArray",jsonArry1);
                            jsonArry.put(jsobject);
                        }
                        js.put("SendACarInfoArray",jsonArry);
                        sendacaradd(js);
                    }


                }catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }

                break;


        }

    }
//    private Dialog dialog;
//    private View inflate;
//    private com.wx.wheelview.widget.WheelView year;
//    private com.wx.wheelview.widget.WheelView mouth;
//    private com.wx.wheelview.widget.WheelView days;
//    private com.wx.wheelview.widget.WheelView time;
//    private com.wx.wheelview.widget.WheelView min;
//    List<String> listyear=new ArrayList<String>();
//    List<String> listmouth=new ArrayList<String>();
//    List<String> listdays=new ArrayList<String>();
//    List<String> listtime=new ArrayList<String>();
//    List<String> listmin=new ArrayList<String>();
//    //TODO
//    SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
//    private String nowyear=sdf.format(new java.util.Date());
//    private String selectyear;
//    private  String selectmouth;
//    private String selectdata;
//    private String selecttime;
//    private String selectmin;
//    public void shownavigation(final TextView text, final int Position) {
//        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_send_select_time, null);
//        TextView text_sure=(TextView) inflate.findViewById(R.id.text_sure);
//        TextView text_quxiao=(TextView) inflate.findViewById(R.id.text_quxiao);
//        final TextView text_time=(TextView) inflate.findViewById(R.id.text_time);
//
//        text_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                text.setText(text_time.getText());
//                dialog.dismiss();
//            }
//        });
//        text_quxiao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                text.setText("");
//                dialog.dismiss();
//
//            }
//        });
//
//        year = (WheelView) inflate.findViewById(R.id.year);
//        mouth = (WheelView) inflate.findViewById(R.id.mouth);
//        days = (WheelView) inflate.findViewById(R.id.days);
//        time = (WheelView) inflate.findViewById(R.id.time);
//        min = (WheelView) inflate.findViewById(R.id.min);
//        com.wx.wheelview.widget.WheelView.WheelViewStyle style = new com.wx.wheelview.widget.WheelView.WheelViewStyle();
//        style.selectedTextColor = Color.parseColor("#0288ce");
//        style.textColor = Color.GRAY;
//        style.selectedTextSize = 20;
//        year.setWheelAdapter(new ArrayWheelAdapter(this));
//        year.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
//        year.setWheelSize(5);
//        year.setWheelData(listyear);
//        year.setStyle(style);
//        year.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
//        mouth.setWheelAdapter(new ArrayWheelAdapter(this));
//        mouth.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
//        mouth.setWheelSize(5);
//        mouth.setSelection(3);
//        mouth.setWheelData(listmouth);
//        mouth.setStyle(style);
//        mouth.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
//        days.setWheelAdapter(dayadapter);
//        days.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
//        days.setWheelSize(5);
//        days.setSelection(3);
//        days.setWheelData(listdays);
//        days.setStyle(style);
//        days.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
//        time.setWheelAdapter(new ArrayWheelAdapter(this));
//        time.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
//        time.setWheelSize(5);
//        time.setSelection(3);
//        time.setWheelData(listtime);
//        time.setStyle(style);
//        time.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
//        min.setWheelAdapter(new ArrayWheelAdapter(this));
//        min.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
//        min.setWheelSize(5);
//        min.setSelection(3);
//        min.setWheelData(listmin);
//        min.setStyle(style);
//        min.setExtraText("", Color.parseColor("#0288ce"), 40, 70);
//        year.setOnWheelItemSelectedListener(new com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, Object o) {
//                selectyear=listyear.get(position);
//                if(selectyear!=null&&selectmouth!=null){
//                    listdays.clear();
//                    for(int i=0;i<getMonthOfDay(Integer.valueOf(selectyear),Integer.valueOf(selectmouth));i++){
//                        listdays.add(String.valueOf(i+1));
//                    }
//                    dayadapter.setData(listdays);
//                }
//                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);
//
//            }
//        });
//        mouth.setOnWheelItemSelectedListener(new com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, Object o) {
//                selectmouth=listmouth.get(position);
//                if(selectyear!=null&&selectmouth!=null){
//                    listdays.clear();
//                    for(int i=0;i<getMonthOfDay(Integer.valueOf(selectyear),Integer.valueOf(selectmouth));i++){
//                        listdays.add(String.valueOf(i+1));
//                    }
//                    dayadapter.setData(listdays);
//                }
//                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);
//            }
//        });
//
//        days.setOnWheelItemSelectedListener(new com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, Object o) {
//                if(listdays.size()!=0){
//                    selectdata=listdays.get(position);
//                }
//                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);
//
//            }
//
//        });
//        time.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, Object o) {
//               selecttime=listtime.get(position);
//                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);
//
//            }
//        });
//        min.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, Object o) {
//                selectmin=listmin.get(position);
//                text_time.setText(selectyear+"-"+selectmouth+"-"+selectdata+" "+selecttime+":"+selectmin);
//
//            }
//        });
//
//        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
////填充对话框的布局
//
//        //将布局设置给Dialog
//        dialog.setContentView(inflate);
//        //获取当前Activity所在的窗体
//        Window dialogWindow = dialog.getWindow();
//        //设置Dialog从窗体底部弹出
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        //获得窗体的属性
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 0;//设置Dialog距离底部的距离
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
////       将属性设置给窗体
//        dialogWindow.setAttributes(lp);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();//显示对话框
//
//    }
    private void sendacarupdata(JSONObject json) {
        String url = BASE_URL+"sendacarupdate";
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            builder.addFormDataPart("departmentName",json.getString("departmentName"));
            builder.addFormDataPart("proposer",json.getString("proposer"));
            builder.addFormDataPart("proposerPhone",json.getString("proposerPhone"));
            builder.addFormDataPart("user",data.get(0).getUser());
            builder.addFormDataPart("userPhone",data.get(0).getUserPhone());
            builder.addFormDataPart("start",data.get(0).getStart());
            builder.addFormDataPart("end",data.get(0).getEnd());
            builder.addFormDataPart("startAt",data.get(0).getStartAt());
            builder.addFormDataPart("endAt",data.get(0).getEndAt());
            builder.addFormDataPart("driverName",data.get(0).getDriverName());
            builder.addFormDataPart("driverPhone",data.get(0).getDriverPhone());
            builder.addFormDataPart("driverUserId",data.get(0).getDriverUserid());
            builder.addFormDataPart("plateNumber",data.get(0).getPlateNumber());
            builder.addFormDataPart("sendACarId",String.valueOf(getIntent().getIntExtra("sendACarId",0)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MultipartBody body = builder.build();
        final Request request = new Request.Builder()
                .addHeader("content-type", "application/json;charset:utf-8")
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.custom("提交失败，请重新提交");
                            }
                        });


                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.custom("行程创建成功");
                                finish();
                            }
                        });


                    }
                });

            }
        });

    }
    private void sendacaradd(JSONObject json) {
        String url = BASE_URL+"sendacaradd";
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            builder.addFormDataPart("departmentName",json.getString("departmentName"));
            builder.addFormDataPart("proposer",json.getString("proposer"));
            builder.addFormDataPart("proposerUserid", NewPreferManager.getoldUseId()+"");
            builder.addFormDataPart("proposerPhone",json.getString("proposerPhone"));
            builder.addFormDataPart("SendACarInfoArray", json.getJSONArray("SendACarInfoArray").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MultipartBody body = builder.build();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json;charset:utf-8")
                .url(url)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.custom("提交失败，请重新提交");
                            }
                        });


                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.custom("行程创建成功");
                                finish();
                            }
                        });


                    }
                });

            }
        });

    }
    public View getViewByPosition(int pos, MyGridView mgview ) {
        final int firstListItemPosition = mgview.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + mgview.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return mgview.getAdapter().getView(pos, null, mgview);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return mgview.getChildAt(childIndex);
        }
    }
    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //ToastUtils.show(getTime(date));
                //TODO
                //     确定
                Log.i("pvTime", "onTimeSelect");
                ((TextView)pvTime.getClickView()).setText(getTime(date));

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                       // TODO 选择
                        //((TextView)pvTime.getClickView()).setText(getTime(date));
                        pvTime.setTitleText(getTime(date));
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

}
