package com.meiliangzi.app.ui.view.sendcar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.IndexSendacarBean;
import com.meiliangzi.app.model.bean.ProposerUserlistBean;
import com.meiliangzi.app.model.bean.SendacardeleteBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.SendDeleatDialog;
import com.meiliangzi.app.widget.MyGridView;
import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * 派车首页
 *
 */

public class SendCarActivity extends BaseActivity implements View.OnClickListener {
    public int plateStatus;
    public int currentPage=1;
    public int pageSize=30;
    @BindView(R.id.gradview)
    MyGridView gradview;
    @BindView(R.id.text_JCZ)
    TextView text_JCZ;
    @BindView(R.id.text_YWC)
    TextView text_YWC;
    @BindView(R.id.text_DFK)
    TextView text_DFK;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.image_ongoing)
    ImageView image_ongoing;
    @BindView(R.id.image_YWC)
    ImageView image_YWC;

    @BindView(R.id.image_pending_feedback)
    ImageView image_pending_feedback;
    @BindView(R.id.text_login)
    TextView text_login;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private SendDeleatDialog deleatDialog;
    private BaseQuickAdapter<IndexSendacarBean.IndexSendacarData> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_send_car);
    }

    @Override
    protected void findWidgets() {
        deleatDialog=new SendDeleatDialog(this);
        adapter=new BaseQuickAdapter<IndexSendacarBean.IndexSendacarData>(this, R.layout.item_wait_sendcar){

            @Override
            public void convert(BaseViewHolder helper, final IndexSendacarBean.IndexSendacarData item) {
                //TODO  数据展示
                helper.setText(R.id.text_sendcar_departmentName, item.getDepartmentName());
                helper.setText(R.id.text_sendcar_plateNumber, item.getPlateNumber());
                helper.setText(R.id.text_sendcar_start, item.getStart());
                helper.setText(R.id.text_sendcar_end, item.getEnd());
                //TODO 显示权限
                String start = item.getStartAt().substring(item.getStartAt().lastIndexOf(" "));
                String end = item.getEndAt().substring(item.getEndAt().lastIndexOf(" "));
                helper.setText(R.id.text_sendcar_startAt_endAt, start+"~"+end);
                helper.getView(R.id.text_quxiao_sendcar).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(item.getProposerUserid()== Integer.valueOf(NewPreferManager.getId())){
                            deleatDialog.setYesOnclickListener("确定", new SendDeleatDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {

                                    ProxyUtils.getHttpProxy().sendacardelete(SendCarActivity.this,item.getId());
                                    deleatDialog.dismiss();
                                }
                            });
                            deleatDialog.setNoOnclickListener("取消", new SendDeleatDialog.onNoOnclickListener() {
                                @Override
                                public void onNoClick() {

                                    // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                                    deleatDialog.dismiss();
                                }
                            });
                            deleatDialog.show();
                        }else {
                           ToastUtils.show("您没有权限进行此操作");
                        }

                    }
                });

                helper.setText(R.id.text_sendcar_time, tiem(item.getStartAt()));

            }
        };
        add.setOnClickListener(this);
        gradview.setAdapter(adapter);
        image_ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SendCarActivity.this,OngoingActivity.class);
                startActivity(i);

            }
        });
        image_pending_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SendCarActivity.this,FreeBackActivity.class);
                startActivity(i);
            }
        });
        image_YWC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SendCarActivity.this,SendFinishActivity.class);
                startActivity(i);
            }
        });
        gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SendCarActivity.this,SendaCarinfoActivity.class);
                intent.putExtra("sendACarId",adapter.getItem(position).getId());
                intent.putExtra("type",1);
                intent.putExtra("proposerUserid",adapter.getItem(position).getProposerUserid());

                startActivity(intent);
            }
        });
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(NewPreferManager.getId())) {
                    IntentUtils.startAtyForResult(SendCarActivity.this, LoginActivity.class, 1003, "activity", "index");
                }
            }
        });

    }



    @Override
    protected void initComponent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProxyUtils.getHttpProxy().indexsendacar(this,1,currentPage,pageSize);
        isLogin();
    }
    protected void getindexsendacar(IndexSendacarBean bean){
        if(bean.getData().size()==0){
            if(tvEmpty.getVisibility()==View.GONE){
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }else {
            if(tvEmpty.getVisibility()==View.VISIBLE){
                tvEmpty.setVisibility(View.GONE);
            }
        }

        adapter.setDatas(bean.getData());
        text_JCZ.setText(bean.getJCZ()+"");
        text_DFK.setText(bean.getDFK()+"");
        text_YWC.setText(bean.getYWC()+"");

    }
    protected void getsendacardelete(SendacardeleteBean bean){
        ProxyUtils.getHttpProxy().indexsendacar(this,1,currentPage,pageSize);

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                //TODO 检查是否权限
                ProxyUtils.getHttpProxy().proposeruserlist(this);

                break;
        }
    }
    protected void getproposeruserlist(ProposerUserlistBean bean){
        if(bean!=null&&bean.getData().size()!=0){
            //TODO
        if(iscontains(bean)){
            Intent intent=new Intent(this,AddSendacarActivity.class);
            startActivity(intent);
        }else {
           //TODO 测试用的
//           Intent intent=new Intent(this,AddSendacarActivity.class);
//            startActivity(intent);
            ToastUtils.show("您没有权限进行此次操作");
        }

        }

    }
    private boolean iscontains(ProposerUserlistBean bean){
        boolean iscontains=false;
        for (int i=0;i<bean.getData().size();i++){
                if(bean.getData().get(i).getId()==Integer.valueOf(NewPreferManager.getId())){

                    iscontains= true;
                }
            }
        return iscontains;
    }
    private String tiem(String start) {
        String time=date2TimeStamp(start+":00","yyyy/MM/dd HH:mm:ss");
        String nowtime=timeStamp();
        String nowtime1="1538357061";

        SimpleDateFormat sdf=new SimpleDateFormat("dd HH:mm:ss");//这个是你要转成后的时间的格式
        return secondToTime(Integer.valueOf(time)-Integer.valueOf(nowtime));
    }
    /**
     22      * 日期格式字符串转换成时间戳
     23      * @param date 字符串日期
     24      * @param format 如：yyyy-MM-dd HH:mm:ss
     25      * @return
     26      */
    public static String date2TimeStamp(String date_str,String format){
               try {
                       SimpleDateFormat sdf = new SimpleDateFormat(format);
                         return String.valueOf(sdf.parse(date_str).getTime()/1000);
                     } catch (Exception e) {
                        e.printStackTrace();
               }
                return "";
            }
            /**
 38      * 取得当前时间戳（精确到秒）
 39      * @return
 40      */
           public static String timeStamp(){
                long time = System.currentTimeMillis();
                 String t = String.valueOf(time/1000);
                return t;
             }
    /**
     * 返回日时分秒
     * @param second
     * @return
     */
    private String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days){
            return days +"天"+hours+"小时"+minutes+"分";
        }else {
            return hours+"小时"+minutes+"分";
        }
    }
    private void isLogin() {
        if (TextUtils.isEmpty(NewPreferManager.getId()) ) {
//            if (TextUtils.isEmpty(NewPreferManager.getId())) {
//                text_login.setText("请先登录");
//            } else {
//                text_login.setText("请完善个人信息");
//            }
            text_login.setText("请先登录");
            text_login.setVisibility(View.VISIBLE);
            add.setFocusable(false);
        }else {
            text_login.setVisibility(View.GONE);
            add.setFocusable(true);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isLogin();
        }
    }

}


