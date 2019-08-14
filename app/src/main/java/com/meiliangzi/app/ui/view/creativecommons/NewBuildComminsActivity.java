package com.meiliangzi.app.ui.view.creativecommons;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.ImageBean;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.SelectPhoto;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewBuildComminsActivity extends BaseActivity implements PermissionListener, View.OnClickListener {
    @BindView(R.id.gradview_group)
    MyGridView gradview_group;

    @BindView(R.id.edit_neirong)
    EditText edit_neirong;
    @BindView(R.id.edit_title)
    EditText edit_title;
    BaseTrainAdapter<ImageBean> listadapter;
    boolean isadd=false;
    private final OkHttpClient client = new OkHttpClient();

    @BindView(R.id.text_sure)
    TextView text_sure;
    private boolean issure=true;
    /**
     * 选中的图片集合
     */
    private List<ImageBean> mSelectImages = new ArrayList<>();
    private SelectPhoto dialogSelect;
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private static final int REQUEST_CODE_SETTING = 101;
    private int mScreenWidth;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取屏幕宽度
        onCreateView(R.layout.activity_new_build_commins);
    }
    @Override
    protected void findWidgets() {
        text_sure.setOnClickListener(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        listadapter = new BaseTrainAdapter<ImageBean>(this, gradview_group, R.layout.showselectphone) {

            @Override
            public void convert(BaseViewHolder helper, ImageBean item) {
                int size = listadapter.getCount();
                final int position = listadapter.getPosition();

                //适配imageView，正方形，宽和高都是屏幕宽度的1/3
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) helper.getView(R.id.image_group_userheader).getLayoutParams();
                params.width =  mScreenWidth / 3 - params.rightMargin - params.leftMargin;
                params.height =  (mScreenWidth / 3 - params.rightMargin - params.leftMargin)-20;
                helper.getView(R.id.image_group_userheader).setLayoutParams(params);

                    if (item.getPath() == null) {
                        // ((ImageView)helper.getView(R.id.image_group_userheader)).setImageBitmap(null);
                        helper.getView(R.id.image_group_userheader).setEnabled(true);
                        //helper.setImageByUrl(R.id.image_group_userheader, null, R.mipmap.groupadd1, R.mipmap.groupadd1);
                        helper.showOrGoneView(R.id.image_delete,false);
                        Glide.with(NewBuildComminsActivity.this).load(item.getPath()).error(R.mipmap.addgroup).into((ImageView) helper.getView(R.id.image_group_userheader));

                        helper.getView(R.id.image_group_userheader).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AndPermission.with(NewBuildComminsActivity.this)
                                        .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                                        .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                                        .rationale(new RationaleListener() {
                                            @Override
                                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                                AndPermission.rationaleDialog(NewBuildComminsActivity.this, rationale).show();
                                            }
                                        })
                                        .send();
                            }
                        });

                    } else {
                        helper.showOrGoneView(R.id.image_delete,true);
                        helper.getView(R.id.image_group_userheader).setEnabled(false);
                        Glide.with(NewBuildComminsActivity.this).load(item.getPath()).into((ImageView) helper.getView(R.id.image_group_userheader));
                        helper.getView(R.id.image_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mSelectImages.remove(position);
                                if(mSelectImages.get(mSelectImages.size()-1).getPath()==null){

                                }else {
                                    mSelectImages.add(new ImageBean());
                                }

                                listadapter.setDatas(mSelectImages);

                            }
                        });
                    }

                }




        };
        mSelectImages.add(mSelectImages.size(),new ImageBean());
        listadapter.setDatas(mSelectImages);
        gradview_group.setAdapter(listadapter);
        dialogSelect = new SelectPhoto();


    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        dialogSelect.getSelectPhoto(NewBuildComminsActivity.this);


    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(NewBuildComminsActivity.this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(NewBuildComminsActivity.this, REQUEST_CODE_SETTING).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                //多图
                case 0:
                    Bundle bundle = data.getExtras();
                    mSelectImages.remove(mSelectImages.size()-1);
                    mSelectImages.addAll(bundle.<ImageBean>getParcelableArrayList("selectImages"));
                    mSelectImages.add(new ImageBean());
                    removeDuplicateWithOrder(mSelectImages);
                    if(mSelectImages.size()>=9){
                        mSelectImages=  mSelectImages.subList(0,9);
                    }
                    listadapter.setDatas(mSelectImages);
                    break;
                //头像
                case 1:
                    mSelectImages.remove(mSelectImages.size()-1);
                        ImageBean image=new ImageBean();
                        image.setPath(MyApplication.path);
                        mSelectImages.add(image);
                    mSelectImages.add(new ImageBean());
                    removeDuplicateWithOrder(mSelectImages);
                    if(mSelectImages.size()>=9){
                        mSelectImages=  mSelectImages.subList(0,9);
                    }
                    listadapter.setDatas(mSelectImages);
                    break;
            }
        }

    }
    private void uploadImg(String title,String content,String userId,String userName) {
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("title",title);
        builder.addFormDataPart("content",content);
        builder.addFormDataPart("userId", userId);
        builder.addFormDataPart("userName", userName);
        for (int i = 0; i < mSelectImages.size(); i++) {
            if(mSelectImages.get(i).getPath()!=null){
                File f = new File(mSelectImages.get(i).getPath());
                if (f != null) {
                    builder.addFormDataPart("image", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
                }
            }
            }


        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()

                .addHeader("content-type", "application/json;charset:utf-8")
                .addHeader("Content-Type", "text/html; charset=UTF-8")// 自定义的header
                .url(Constant.BASE_URL+"repositoryadd")//地址
                .post(requestBody)//添加请求体
                .build();
       String s=request.body().toString();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                issure=true;
                ToastUtils.show("上传失败");
                //System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                issure=true;
                finish();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_sure:
                try {
                    RuleCheckUtils.checkEmpty(edit_title.getText().toString(), "请输入标题");
                    RuleCheckUtils.checkEmpty(edit_neirong.getText().toString(), "请输入内容");
                    if(issure){
                        issure=false;
                        uploadImg(edit_title.getText().toString().trim(),edit_neirong.getText().toString().trim(), NewPreferManager.getoldUseId()+"",NewPreferManager.getUserName());

                    }

                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }

                break;
        }
    }
    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }
    // 删除ArrayList中重复元素，保持顺序
    public static void removeDuplicateWithOrder(List list) {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   2 ; j  >  i; j -- )  {
                if  (((ImageBean)list.get(j)).getPath().equals(((ImageBean)list.get(i)).getPath()))  {
                    list.remove(j);
                }
            }
        }
    }

}
