package com.meiliangzi.app.ui.view.sendcar;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.SendacardeleteBean;
import com.meiliangzi.app.model.bean.SendacarinfoBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;

public class SendaCarinfoActivity extends BaseActivity implements View.OnClickListener {
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
    @BindView(R.id.text_fk_entttime)
    TextView text_fk_entttime;
    @BindView(R.id.text_fk_drivemUser)
    TextView text_fk_drivemUser;
    @BindView(R.id.text_fk_drivemPhone)
    TextView text_fk_drivemPhone;
    @BindView(R.id.text_fk_number)
    TextView text_fk_number;
    @BindView(R.id.text_fk_tiem)
    TextView text_fk_tiem;
    @BindView(R.id.text_fk_startmileage)
    TextView text_fk_startmileage;
    @BindView(R.id.text_fk_endmileage)
    TextView text_fk_endmileage;
    @BindView(R.id.text_fk_mileage)
    TextView text_fk_mileage;
    @BindView(R.id.text_fk_remarks)
    TextView edit_fk_remarks;
    @BindView(R.id.image_updata)
    ImageView image_updata;
    int id;
    int sendACarInfoId;
    int proposerUserid;
    int driverUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_senda_carinfo);
    }

    @Override
    protected void findWidgets() {
        proposerUserid=getIntent().getIntExtra("proposerUserid",0000);
        image_updata.setOnClickListener(this);

    }

    @Override
    protected void initComponent() {
        if(getIntent().getIntExtra("type",101)==0){
            image_updata.setVisibility(View.GONE);
            ProxyUtils.getHttpProxy().sendacarinfo(this,getIntent().getIntExtra("sendACarId",0));

        }else {
            ProxyUtils.getHttpProxy().sendacarinfo(this,getIntent().getIntExtra("sendACarId",0));

        }

    }
    protected void getsendacarinfo(SendacarinfoBean bean){
        id=bean.getData().getId();
        driverUserId=Integer.valueOf(bean.getData().getDriverUserId());
        sendACarInfoId=Integer.valueOf(bean.getData().getSendACarInfoId());
        text_fk_partment.setText(bean.getData().getDepartmentName());
        text_fk_username.setText(bean.getData().getProposer());
        text_fk_user.setText(bean.getData().getUser());
        text_fk_userphone.setText(bean.getData().getUserPhone());
        text_fk_satrt.setText(bean.getData().getStart());
        text_fk_end.setText(bean.getData().getEnd());
        text_fk_statttime.setText(bean.getData().getStartAt());
        text_fk_entttime.setText(bean.getData().getEndAt());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.image_updata:
                shownavigation();
                break;
            case R.id.text_xiugai:


                    //TODO 修改申请
                    if(getIntent().getIntExtra("type",101)==1){
                        //TOOD
                        if(proposerUserid== Integer.valueOf(PreferManager.getUserId())) {
                            Intent intent = new Intent(this, AddSendacarActivity.class);
                            intent.putExtra("type", "updata");
                            intent.putExtra("sendACarId", id);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtils.show("您没有权限进行此操作");
                        }
                    }else {
                        if(driverUserId==Integer.valueOf(PreferManager.getUserId())){
                            //TODO 修改反馈
                            Intent intent=new Intent(this,FkDataActivity.class);
                            intent.putExtra("type","updata");
                            intent.putExtra("sendACarId",id);
                            intent.putExtra("sendACarInfoId",sendACarInfoId);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtils.show("您没有权限进行此操作");
                        }



                }


                break;
            case R.id.text_delete:
                //TODO 删除
                if(proposerUserid== Integer.valueOf(PreferManager.getUserId())){
                    ProxyUtils.getHttpProxy().sendacardelete(this,id);

                }else {
                    ToastUtils.show("您没有权限进行修改");
                }
                dialog.dismiss();


                break;
            case R.id.text_quxiao:
                dialog.dismiss();
                break;
        }
    }
    private Dialog dialog;
    private View inflate;
    public void shownavigation() {
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_updata, null);
        TextView xiugai=(TextView)inflate.findViewById(R.id.text_xiugai);
        TextView delete=(TextView)inflate.findViewById(R.id.text_delete);
        TextView quxiao=(TextView)inflate.findViewById(R.id.text_quxiao);
        xiugai.setOnClickListener(this);
        delete.setOnClickListener(this);
        quxiao.setOnClickListener(this);
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
        lp.y = 30;//设置Dialog距离底部的距离
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();//显示对话框
    }
    protected void getsendacardelete(SendacardeleteBean bean){
        finish();

    }
}
