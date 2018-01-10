package com.meiliangzi.app.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.Company;
import com.meiliangzi.app.model.bean.Updateuserinfo;
import com.meiliangzi.app.model.bean.UserStar;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.Compressor;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.DialogSelectPhoto;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 个人中心
 **/

public class PersonCenterActivity extends BaseActivity implements PermissionListener {


    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private static final int REQUEST_CODE_SETTING = 101;

    @BindView(R.id.tvAccount)
    TextView tvAccount;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvIsPartybranch)
    TextView tvIsPartybranch;
    @BindView(R.id.tvGender)
    TextView tvGender;
    @BindView(R.id.tvCompany)
    TextView tvCompany;
    @BindView(R.id.tvWorkNum)
    TextView tvWorkNum;
    @BindView(R.id.tvPartment)
    TextView tvPartment;
    @BindView(R.id.tvIsParentment)
    TextView tvIsParentment;
    @BindView(R.id.llUserStar)
    LinearLayout llUserStar;

    @BindView(R.id.llIsPartybranch)
    LinearLayout llIsPartybranch;
    @BindView(R.id.llNickName)
    LinearLayout llNickName;
    @BindView(R.id.llGender)
    LinearLayout llGender;
    @BindView(R.id.llCompany)
    LinearLayout llCompany;
    @BindView(R.id.llWorkNum)
    LinearLayout llWorkNum;
    @BindView(R.id.llJob)
    LinearLayout llJob;
    @BindView(R.id.llIsPartment)
    LinearLayout llIsPartment;
    @BindView(R.id.tvDone)
    TextView tvDone;

    private DialogSelectPhoto dialogSelect;

    @BindView(R.id.ivStavr)
    CircleImageView ivStavr;
    private String s="";
    private int companyId=0;
    private int departmentId=0;
    private String userDepartment="";
    private String isPartment;
    private String workNum;
    private int isPartyMember=0;
    private int partyBranchId=0;
    private int partyBranchsId=0;
    private String isMember;
    private String isCompany="";
    private Company.DataBean partment;
    private int user_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_person_center);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void initComponent() {
        if(PreferManager.getIsComplete()){
            tvDone.setVisibility(View.GONE);
        }
        dialogSelect = new DialogSelectPhoto();
        dialogSelect.setType(1);
        tvAccount.setText(PreferManager.getUserPhone());
        if (PreferManager.getUserStar().startsWith("http")) {
            ImageLoader.getInstance().displayImage(PreferManager.getUserStar(), ivStavr, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        } else {
            ImageLoader.getInstance().displayImage("file:///" + PreferManager.getUserStar(), ivStavr, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        }
        tvUserName.setText(PreferManager.getUserName());
        tvGender.setVisibility(View.GONE);
        companyId = Integer.valueOf(PreferManager.getCompanId());
        departmentId = Integer.valueOf(PreferManager.getDepartmentId());
        tvCompany.setText(PreferManager.getCompany());
        tvWorkNum.setText(PreferManager.getWorkNum());
        workNum = PreferManager.getWorkNum();
        tvPartment.setText(PreferManager.getDepartment());

        if (PreferManager.isPartment()) {
            tvIsParentment.setText("是");
            isMember = "1";
            isPartment = "是";
            llIsPartybranch.setVisibility(View.VISIBLE);
            tvIsPartybranch.setText(PreferManager.getpartyBranName("partyBranName"));
            llIsPartybranch.setEnabled(false);
        } else {
            tvIsParentment.setText("否");
            isMember = "0";
            isPartment = "否";
            llIsPartybranch.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(tvCompany.getText().toString())){
            tvCompany.setCompoundDrawables(null,null,null,null);
            llCompany.setEnabled(false);
            PreferManager.saveIsCompleteInfo(true);
        }

        if(!TextUtils.isEmpty(tvPartment.getText().toString())){
            tvPartment.setCompoundDrawables(null,null,null,null);
            llJob.setEnabled(false);
        }
        if(!TextUtils.isEmpty(tvCompany.getText().toString()) && !TextUtils.isEmpty(tvPartment.getText().toString())){
            tvIsParentment.setCompoundDrawables(null,null,null,null);
            llIsPartment.setEnabled(false);
            tvUserName.setCompoundDrawables(null,null,null,null);
            llNickName.setEnabled(false);
        }
        if(!tvWorkNum.getText().toString().equals("0")){
            tvWorkNum.setCompoundDrawables(null,null,null,null);
            llWorkNum.setClickable(false);
        }

        if(TextUtils.isEmpty(tvCompany.getText().toString())){
            llJob.setVisibility(View.GONE);
        }else {
            llJob.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.llUserStar, R.id.llNickName, R.id.llGender, R.id.llCompany, R.id.llWorkNum, R.id.llJob, R.id.llIsPartment,R.id.llIsPartybranch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llUserStar:
                AndPermission.with(PersonCenterActivity.this)
                        .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                        .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                AndPermission.rationaleDialog(PersonCenterActivity.this, rationale).show();
                            }
                        })
                        .send();

                break;
            case R.id.llNickName:
                IntentUtils.startAtyForResult(PersonCenterActivity.this, SetNickNameActivity.class, 1001);
                break;
            case R.id.llGender:
                IntentUtils.startAtyForResult(PersonCenterActivity.this, UpdateGenderActivity.class, 1006);
                break;
            case R.id.llCompany:
                IntentUtils.startAtyForResult(PersonCenterActivity.this, SetCompanyActivity.class, 1002);
                break;
            case R.id.llWorkNum:
                IntentUtils.startAtyForResult(PersonCenterActivity.this, UpdateWorkNumActivity.class, 1005);
                break;
            case R.id.llJob:

                Intent intent = new Intent(PersonCenterActivity.this,SetPartmentActivity.class);
                intent.putExtra("object", partment);
                startActivityForResult(intent,1003);
                break;
            case R.id.llIsPartybranch:
                IntentUtils.startAtyForResult(PersonCenterActivity.this, PartyBranchActivity.class, 1008,"companyId",companyId);
                break;
            case R.id.llIsPartment:
                IntentUtils.startAtyForResult(PersonCenterActivity.this, IsPartyMemberActivity.class, 1004);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    String content = data.getStringExtra("content");
                    tvUserName.setText(content);
                    break;
                case 1002:
                    String company = data.getStringExtra("content");
                    companyId = Integer.valueOf(data.getStringExtra("companyId"));
                     partment = (Company.DataBean) data.getSerializableExtra("object");
                    tvCompany.setText(company);
                    tvPartment.setText("");
                    llJob.setVisibility(View.VISIBLE);
                    break;
                case 1003:
                    String partment = data.getStringExtra("content");
                    departmentId = Integer.valueOf(data.getStringExtra("departmentId"));
                    tvPartment.setText(partment);
                    break;
                case 1004:
                    isPartment = data.getStringExtra("content");
                    if (isPartment.equals("是")) {
                        isMember = "1";
                    } else {
                        isMember = "0";
                    }
                    tvIsParentment.setText(isPartment);
                    break;
                case 1005:
                    workNum = data.getStringExtra("content");
                    tvWorkNum.setText(workNum);
                    break;
                case 1006:
                    String gender = data.getStringExtra("content");
                    tvGender.setText(gender);
                    break;
                case 1008:
                    String dataStringExtra = data.getStringExtra("name");

                    partyBranchId=Integer.valueOf(PreferManager.getpartyBranchId("partyBranchId"));
                    partyBranchsId=Integer.valueOf(PreferManager.getpartyBranchsId("partyBranchsId"));
                    tvIsPartybranch.setText(dataStringExtra);
                    break;
                default:

                    s = dialogSelect.onActivityResult(PersonCenterActivity.this, requestCode, resultCode, data);
                    if(s!=null){
                        ImageLoader.getInstance().displayImage("file:///" + s, ivStavr, MyApplication.getSimpleOptions(R.mipmap.test_user_star, R.mipmap.test_user_star));

                        updatePersonInfo(new File(s));
                    }
                    break;
            }
        }
        if( "1".equals(isMember)&&companyId!=0){
            if(llIsPartybranch.getVisibility()==View.GONE){
                llIsPartybranch.setVisibility(View.VISIBLE);
            }

        }else {
            if(llIsPartybranch.getVisibility()==View.VISIBLE){
                llIsPartybranch.setVisibility(View.GONE);
            }
        }

    }

    @OnClick(R.id.tvDone)
    public void onClick() {
        try {
            RuleCheckUtils.checkEmpty(tvUserName.getText().toString(), "请设置用户真实姓名");
            RuleCheckUtils.checkEmpty(tvCompany.getText().toString(), "请设置所属公司");
            RuleCheckUtils.checkEmpty(tvWorkNum.getText().toString(), "请设置工号");
            RuleCheckUtils.checkEmpty(tvPartment.getText().toString(), "请设置所属部门");
            RuleCheckUtils.checkEmpty(tvIsParentment.getText().toString(), "请设置是否党员");
            user_code=Integer.valueOf(tvWorkNum.getText().toString());
            if (null==s){
                s="";
            }
            if(isPartment.equals("是")){
                if(partyBranchId==0&&partyBranchsId==0){
                    ToastUtils.custom("请选择党支部");
                    return;
                }
            }

            if (TextUtils.isEmpty(s)) {
                updatePersonInfo(null, tvUserName.getText().toString(), companyId, departmentId,isMember,userDepartment,user_code,partyBranchId,partyBranchsId);
            } else {
             File   file1 = Compressor.getDefault(PersonCenterActivity.this).compressToFile(new File(s));
                updatePersonInfo(file1, tvUserName.getText().toString(), companyId, departmentId,isMember,userDepartment,user_code,partyBranchId,partyBranchsId);
            }
        } catch (Exception e) {
            ToastUtils.custom(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updatePersonInfo(File img, String userName, int companyId, int departmentId, String isPartMember,String userDepartment, int user_code,int partyBranchId,int partyBranchsId) {
        String result=userName+","+companyId+","+departmentId+","+isPartMember+","+userDepartment+","+partyBranchId+","+partyBranchsId+","+Integer.valueOf(PreferManager.getUserId());
        ProxyUtils.getHttpProxy().updateuserinfo(PersonCenterActivity.this, Integer.valueOf(PreferManager.getUserId()), img, userName, companyId, departmentId, isPartMember,userDepartment,user_code, partyBranchId,partyBranchsId);
    }

    public void updatePersonInfo(File img) {
        ProxyUtils.getHttpProxy().updateuserinfo(PersonCenterActivity.this, PreferManager.getUserId(), img);
    }

    protected void getUserStar(UserStar userStar) {
        PreferManager.saveUserStar(userStar.getData());
    }

    protected void getResult(Updateuserinfo baseBean) {
        ToastUtils.custom("修改成功");
        PreferManager.saveUserName(tvUserName.getText().toString());
        PreferManager.saveCompany(tvCompany.getText().toString());
        PreferManager.saveDepartment(tvPartment.getText().toString());
        PreferManager.saveCompanyId(String.valueOf(companyId));
        PreferManager.saveWorkNum(tvWorkNum.getText().toString());
        PreferManager.savePartmentId(String.valueOf(departmentId));
        if (tvIsParentment.equals("是")) {
            PreferManager.saveIsPartment(true);
        } else {
            PreferManager.saveIsPartment(false);
        }
        tvDone.setVisibility(View.GONE);
        if (isPartment.equals("是")) {
            isMember = "1";
            PreferManager.saveIsPartment(true);
        } else {
            isMember = "0";
            PreferManager.saveIsPartment(false);
        }
        PreferManager.saveIsCompleteInfo(true);
        tvUserName.setCompoundDrawables(null,null,null,null);
        tvCompany.setCompoundDrawables(null,null,null,null);
        tvPartment.setCompoundDrawables(null,null,null,null);
        tvWorkNum.setCompoundDrawables(null,null,null,null);
        tvIsParentment.setCompoundDrawables(null,null,null,null);
        if(getIntent()!=null && getIntent().getStringExtra("activity")!=null){
            setResult(RESULT_OK);
            this.finish();
        }
    }


    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
        dialogSelect.getSelectPhoto(PersonCenterActivity.this);
    }

    @Override
    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(PersonCenterActivity.this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(PersonCenterActivity.this, REQUEST_CODE_SETTING).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
