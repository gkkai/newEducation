package com.meiliangzi.app.ui.view.train;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.CountyListbean;
import com.meiliangzi.app.model.bean.QueryVideoCommentBean;
import com.meiliangzi.app.model.bean.TrainSignerUpBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.view.AddMapLoctionActivity;
import com.meiliangzi.app.ui.view.MapNewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;

public class PersonalSingUpActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_train_persong_name)
    EditText et_train_persong_name;
    @BindView(R.id.image_train_sex)
    CheckBox image_train_sex;
    @BindView(R.id.et_train_ID_number)
    EditText et_train_ID_number;
    @BindView(R.id.et_train_phane)
    EditText et_train_phane;
    @BindView(R.id.et_train_record)
    TextView et_train_record;
    @BindView(R.id.et_train_major)
    EditText et_train_major;
    @BindView(R.id.et_train_company)
    EditText et_train_company;
    @BindView(R.id.et_train_properties)
    TextView et_train_properties;
    @BindView(R.id.et_train_department)
    EditText et_train_department;
    @BindView(R.id.et_train_job)
    EditText et_train_job;
    @BindView(R.id.et_train_qq)
    EditText et_train_qq;
    @BindView(R.id.et_train_traintype)
    TextView et_train_traintype;
    @BindView(R.id.image_singup)
    ImageView image_singup;
    private View inflate;
    private Dialog dialog;
    private int train_id;
    private MyDialog myDialog;
    private String sex = "男";

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.et_train_properties:
                //TODO 输入姓名单位性质
                showdialog("properties");
                break;
            case R.id.text_properties_one:
                et_train_properties.setText("国有重点");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.text_properties_tow:
                et_train_properties.setText("地方国有");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.text_properties_there:
                et_train_properties.setText("乡镇煤矿");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.et_train_record:
                //TODO 输入选则学历
                showdialog("record");
                break;
            case R.id.text_record_juniorschool:
                et_train_record.setText("小学");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.text_record_juniorhighschool:
                et_train_record.setText("初中");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;


            case R.id.text_record_highschoole:
                et_train_record.setText("高中");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.text_record_juniorcollege:
                et_train_record.setText("大专");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.text_record_regularcourse:
                et_train_record.setText("本科");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.text_record_graduatestudent:
                et_train_record.setText("研究生");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.et_train_traintype:
                showdialog("traintype");
                //TODO 输入选择培训类型
                break;
            case R.id.text_record_train:
                et_train_traintype.setText("培训");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.text_record_refreshertraining:
                et_train_traintype.setText("复训");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                break;
            case R.id.image_train_sex:
                //image_train_sex.setImageDrawable(getApplication().getDrawable(R.mipmap.woman));
                //TODO 输入姓性别
                if("男".equals(sex)){
                    sex="女";
                    //setImageByUrl(image_train_sex,null,R.mipmap.woman,R.mipmap.woman);
                    image_train_sex.setChecked(false);


                }else {
                    sex="男";
                    //setImageByUrl(image_train_sex,null,R.mipmap.man,R.mipmap.man);
                    image_train_sex.setChecked(true);
                }
                break;
            case R.id.image_singup:
                try {
                    RuleCheckUtils.checkEmpty(et_train_persong_name.getText().toString(), "请设置输入姓名");
                    RuleCheckUtils.checkEmpty(et_train_ID_number.getText().toString(), "请输入身份证号码");
                    RuleCheckUtils.checkEmpty(et_train_phane.getText().toString(), "请设置电话号码");
                    RuleCheckUtils.checkEmpty(et_train_record.getText().toString(), "请选择学历");

                    RuleCheckUtils.checkEmpty(et_train_major.getText().toString(), "请输入专业名称");
                    RuleCheckUtils.checkEmpty(et_train_company.getText().toString(), "请输入工作单位");

                    RuleCheckUtils.checkEmpty(et_train_properties.getText().toString(), "请选择公司类型");
                    RuleCheckUtils.checkEmpty(et_train_department.getText().toString(), "请输入部门");
                    RuleCheckUtils.checkEmpty(et_train_job.getText().toString(), "请输入职位");
                    RuleCheckUtils.checkEmpty(et_train_qq.getText().toString(), "请输入qq");
                    RuleCheckUtils.checkEmpty(et_train_traintype.getText().toString(), "选择培训类型");

                    //TODO 开始报名
                    int userId = Integer.valueOf(PreferManager.getUserId());
                    String name = et_train_persong_name.getText().toString().trim();
                    String idCard = et_train_ID_number.getText().toString().trim();

                    String phone = et_train_phane.getText().toString().trim();
                    String education = et_train_record.getText().toString().trim();
                    String major = et_train_major.getText().toString().trim();
                    String company = et_train_company.getText().toString().trim();
                    String companyType = et_train_properties.getText().toString().trim();
                    String department = et_train_department.getText().toString().trim();
                    String position = et_train_job.getText().toString().trim();
                    String qq = et_train_qq.getText().toString().trim();
                    String remarks = et_train_traintype.getText().toString().trim();
                        ProxyUtils.getHttpProxy().cultivatetrainbaoming(this, train_id, userId, name, idCard, sex, phone, education, major, company, companyType, department, position, qq, remarks);

                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }

                break;

        }
    }
        //TODO 返回视频评论列表
    protected void  getcultivatetrainbaoming(TrainSignerUpBean data){
        //listadapter.setDatas(data.getData());

        myDialog = new MyDialog(this);
        myDialog.setMessage("报名成功");
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_personal_sing_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        onCreateView(R.layout.activity_personal_sing_up);
        train_id=getIntent().getIntExtra("type",0);
    }

    @Override
    protected void findWidgets() {
        image_train_sex.setChecked(true);
        image_train_sex.setOnClickListener(this);
        et_train_record.setOnClickListener(this);
        et_train_properties.setOnClickListener(this);
        et_train_traintype.setOnClickListener(this);
        image_singup.setOnClickListener(this);

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }
    private void showdialog(String type) {
        if("properties".equals(type)){
            inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_train_singerup_properties, null);
            TextView text_properties_one = (TextView) inflate.findViewById(R.id.text_properties_one);
            TextView text_properties_tow = (TextView) inflate.findViewById(R.id.text_properties_tow);
            text_properties_one.setOnClickListener(this);
            TextView text_properties_there = (TextView) inflate.findViewById(R.id.text_properties_there);
            text_properties_tow.setOnClickListener(this);
            text_properties_there.setOnClickListener(this);
        }else if("record".equals(type)){
            inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_train_singerup_record, null);
            TextView text_record_juniorschool = (TextView) inflate.findViewById(R.id.text_record_juniorschool);
            TextView text_record_juniorhighschool = (TextView) inflate.findViewById(R.id.text_record_juniorhighschool);
            text_record_juniorhighschool.setOnClickListener(this);
            TextView text_record_highschoole = (TextView) inflate.findViewById(R.id.text_record_highschoole);
            text_record_highschoole.setOnClickListener(this);
            text_record_juniorschool.setOnClickListener(this);
            TextView text_record_juniorcollege = (TextView) inflate.findViewById(R.id.text_record_juniorcollege);
            TextView text_record_regularcourse = (TextView) inflate.findViewById(R.id.text_record_regularcourse);
            text_record_juniorcollege.setOnClickListener(this);
            TextView text_record_graduatestudent = (TextView) inflate.findViewById(R.id.text_record_graduatestudent);
            text_record_regularcourse.setOnClickListener(this);
            text_record_juniorschool.setOnClickListener(this);
            text_record_graduatestudent.setOnClickListener(this);

        }else if("traintype".equals(type)){
            inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_train_singerup_traintype, null);
            TextView text_record_train = (TextView) inflate.findViewById(R.id.text_record_train);
            TextView text_record_refreshertraining = (TextView) inflate.findViewById(R.id.text_record_refreshertraining);
            text_record_refreshertraining.setOnClickListener(this);
            text_record_train.setOnClickListener(this);

        }

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

}
