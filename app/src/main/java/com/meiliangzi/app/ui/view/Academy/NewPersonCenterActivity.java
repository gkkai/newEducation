package com.meiliangzi.app.ui.view.Academy;

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
import com.meiliangzi.app.model.bean.UserStar;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.tools.picompressor.Compressor;
import com.meiliangzi.app.ui.IsPartyMemberActivity;
import com.meiliangzi.app.ui.PartyBranchActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.SetCompanyActivity;
import com.meiliangzi.app.ui.SetNickNameActivity;
import com.meiliangzi.app.ui.SetPartmentActivity;
import com.meiliangzi.app.ui.UpdateWorkNumActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.DialogSelectPhoto;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class NewPersonCenterActivity extends BaseActivity implements PermissionListener, View.OnClickListener {

    @BindView(R.id.tvphone)
    TextView tvphone;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvIsPartybranch)
    TextView tvIsPartybranch;
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
    @BindView(R.id.llNickName)
    LinearLayout llNickName;
    @BindView(R.id.llCompany)
    LinearLayout llCompany;
    @BindView(R.id.llJob)
    LinearLayout llJob;
    @BindView(R.id.llWorkNum)
    LinearLayout llWorkNum;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.llIsPartment)
    LinearLayout llIsPartment;
    @BindView(R.id.llIsPartybranch)
    LinearLayout llIsPartybranch;

    @BindView(R.id.tv_done)
    TextView tv_done;
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private static final int REQUEST_CODE_SETTING = 101;
    private Company.DataBean partment;
    @BindView(R.id.ivStavr)
    CircleImageView ivStavr;
    private String userDepartment="";
    private String isPartment;
    private String workNum;
    private DialogSelectPhoto dialogSelect;
    private String isMember;
    private String path="";
    private int user_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_new_person_center);
    }

    @Override
    protected void findWidgets() {
        tv_done.setOnClickListener(this);
    }

    @Override
    protected void initComponent() {
        llUserStar.setOnClickListener(this);

        tvphone.setText(NewPreferManager.getPhone());
        if (NewPreferManager.getPhoto().startsWith("http")) {
            ImageLoader.getInstance().displayImage(NewPreferManager.getPhoto(), ivStavr, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        } else {
            ImageLoader.getInstance().displayImage("file:///" + NewPreferManager.getPhoto(), ivStavr, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        }
        tvUserName.setText(NewPreferManager.getUserName());

        tvCompany.setText("神南产业发展有限公司");
        tvWorkNum.setText(NewPreferManager.getWorkNumber());
        workNum = NewPreferManager.getWorkNumber();
        tvPartment.setText(NewPreferManager.getOrganizationName());
        if (!"".equals(NewPreferManager.getPartyMasses())) {
            tvIsParentment.setText("是");
            llIsPartybranch.setVisibility(View.VISIBLE);
            tvIsPartybranch.setText(NewPreferManager.getPartyName());
        } else {
            tvIsParentment.setText("否");
            llIsPartybranch.setVisibility(View.GONE);
        }
        dialogSelect = new DialogSelectPhoto();
        dialogSelect.setType(1);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llUserStar:
                AndPermission.with(this)
                        .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                        .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                AndPermission.rationaleDialog(NewPersonCenterActivity.this, rationale).show();
                            }
                        })
                        .send();

                break;
            case R.id.llNickName:
                IntentUtils.startAtyForResult(this, SetNickNameActivity.class, 1001);
                break;
            case R.id.llCompany:
                IntentUtils.startAtyForResult(this, SetCompanyActivity.class, 1002);
                break;
            case R.id.llJob:

                Intent intent = new Intent(this,SetPartmentActivity.class);
                intent.putExtra("object", partment);
                startActivityForResult(intent,1003);
                break;
            case R.id.llWorkNum:
                IntentUtils.startAtyForResult(this, UpdateWorkNumActivity.class, 1005);
                break;
            case R.id.llIsPartment:
                IntentUtils.startAtyForResult(this, IsPartyMemberActivity.class, 1004);
                break;

            case R.id.tv_done:
                if(!"".equals(path)){
                    try {
                        OkhttpUtils.getInstance(this).put("academyService/userInfo/updateImage", path,new OkhttpUtils.onCallBack() {
                            @Override
                            public void onFaild(Exception e) {

                            }

                            @Override
                            public void onResponse(String json) {

                                setResult(1);
                                finish();



                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {

                }



                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {


                default:

                    path = dialogSelect.onActivityResult(this, requestCode, resultCode, data);
                    if(path!=null){
                        ImageLoader.getInstance().displayImage("file:///" + path, ivStavr, MyApplication.getSimpleOptions(R.mipmap.test_user_star, R.mipmap.test_user_star));

                        //updatePersonInfo(new File(path));
                    }
                    break;
            }
        }

    }

    @Override
    public void onBackClick(View v) {
        super.onBackClick(v);

    }

    public void updatePersonInfo(File img) {
        ProxyUtils.getHttpProxy().updateuserinfo(this, NewPreferManager.getId(), img);
    }

    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
        dialogSelect.getSelectPhoto(this);
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(NewPersonCenterActivity.this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(NewPersonCenterActivity.this, REQUEST_CODE_SETTING).show();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    protected void getUserStar(UserStar userStar) {
        NewPreferManager.savePhoto(userStar.getData());
    }
}
