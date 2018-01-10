package com.meiliangzi.app.ui.view.imkit;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AddFrouppicBean;
import com.meiliangzi.app.model.bean.ExitGroupBean;
import com.meiliangzi.app.model.bean.GroupUserlistBean;
import com.meiliangzi.app.model.bean.QueryVideoCommentBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.ClearChatDialog;
import com.meiliangzi.app.widget.DialogSelectPhoto;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.XCRoundRectImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.AlterDialogFragment;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class MessageActivity extends BaseActivity implements View.OnClickListener , PermissionListener {
    private static final int REQUEST_CODE_PERMISSION_CAMERA_SD = 100;
    private static final int REQUEST_CODE_SETTING = 101;
    @BindView(R.id.gradview_group)
    MyGridView gradview_group;
    @BindView(R.id.image_group)
    XCRoundRectImageView image_group;
    @BindView(R.id.text_decectchat)
    TextView text_decectchat;
    @BindView(R.id.text_exit_group)
    TextView text_exit_group;
    boolean isadd=false;
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_groupId)
    TextView text_groupId;
    @BindView(R.id.text_showgroupuserlist)
    TextView text_showgroupuserlist;
    @BindView(R.id.re_groupheader)
    RelativeLayout re_groupheader;

    BaseTrainAdapter<GroupUserlistBean.DataBean.UserInfo> listadapter;
    String groupid;
    String groupname;
    String groupimage;
    String id;
    private String s = null;
    private DialogSelectPhoto dialogSelect;
    ClearChatDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_double_chat_room);

    }


    @Override
    protected void findWidgets() {
        dialogSelect = new DialogSelectPhoto();
        dialogSelect.setType(1);
        text_showgroupuserlist.setOnClickListener(this);
        re_groupheader.setOnClickListener(this);
        text_decectchat.setOnClickListener(this);
        text_exit_group.setOnClickListener(this);
        listadapter = new BaseTrainAdapter<GroupUserlistBean.DataBean.UserInfo>(this, gradview_group, R.layout.item_group_user) {

            @Override
            public void convert(BaseViewHolder helper, GroupUserlistBean.DataBean.UserInfo item) {
                int size = listadapter.getCount();
                int position = listadapter.getPosition();
                if (position == size - 1&&isadd) {
                    helper.setImageByUrl(R.id.image_group_userheader, item.getAvatar(), R.mipmap.groupadd1, R.mipmap.groupadd1);
                    helper.getView(R.id.image_group_userheader).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO 跳转所有人页面
                            Intent intent=new Intent(MessageActivity.this,NewBuildChatGroupActivity.class);
                            intent.putExtra("groupId",groupid);
                            startActivity(intent);
                           // ToastUtils.show("hhhhhhh");
                           // finish();
                        }
                    });
helper.getView(R.id.text_group_name).setVisibility(View.GONE);
                } else {
                    helper.setImageByUrl(R.id.image_group_userheader, item.getAvatar(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
                    helper.setText(R.id.text_group_name, item.getNickName());
                    helper.getView(R.id.text_group_name).setVisibility(View.VISIBLE);
                }

            }
        };
        gradview_group.setAdapter(listadapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        groupid = getIntent().getStringExtra("id");
        text_groupId.setText("(群号 ："+groupid+")");
//TODO
        ProxyUtils.getHttpProxy().groupuserlist(this, Integer.valueOf(groupid));

    }

    @Override
    protected void initComponent() {



    }

    private void getgroupuserlist(GroupUserlistBean data) {
        id = data.getData().getGroupInfo().getGroupUserId();
        if (id .equals(PreferManager.getUserId()) ){

        }else {
            text_exit_group.setText("退出群组");
        }
        groupname=data.getData().getGroupInfo().getGroupName();
        groupimage=data.getData().getGroupInfo().getImage();
        RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(groupid),groupname, Uri.parse(String.valueOf(groupimage))));

        List<Integer> adminIds=  data.getData().getGroupInfo().getGroupAdminId();
         isadd=false;
        if(adminIds!=null&&adminIds.size()!=0){
           /* for(int i=0;i<adminIds.size();i++){
            String userid=    PreferManager.getUserId();
                if(adminIds.get(i).equals()Integer.valueOf(PreferManager.getUserId())){
                    isadd=true;
                }
            }*/
           if(adminIds.contains(Integer.valueOf(PreferManager.getUserId()))){
               isadd=true;
           }
        }

        if(data.getData().getGroupInfo().getGroupUserId().equals(PreferManager.getUserId())){
            isadd=true;
        }
        /*if(isadd){
            data.getData().getUserInfo().add(new GroupUserlistBean.DataBean.UserInfo());

        }*/
         List<GroupUserlistBean.DataBean.UserInfo> datas = data.getData().getUserInfo();
        if (datas.size() >9) {
            if(isadd){
                datas=datas.subList(0, 9);
                datas.add((new GroupUserlistBean.DataBean.UserInfo()));
                listadapter.setDatas(datas);

            }else {
                listadapter.setDatas(datas.subList(0, 10));

            }

            listadapter.notifyDataSetChanged();
        } else {
            if(isadd){
                data.getData().getUserInfo().add(new GroupUserlistBean.DataBean.UserInfo());

            }
            listadapter.setDatas(data.getData().getUserInfo());
            listadapter.notifyDataSetChanged();
        }
setImageByUrl(image_group,data.getData().getGroupInfo().getImage(),R.mipmap.groupheader,R.mipmap.groupheader);
        text_title.setText(data.getData().getGroupInfo().getGroupName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_decectchat:
                //TODO 清空聊天记录
                dialog = new ClearChatDialog(this);
                dialog.setYesOnclickListener("清除", new ClearChatDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP, groupid, new RongIMClient.ResultCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean aBoolean) {
                                dialog.dismiss();
                                finish();
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                dialog.dismiss();
                                ToastUtils.show(errorCode.getMessage());

                            }
                        });

                    }
                });
                dialog.setNoOnclickListener("取消", new ClearChatDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogs) {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getWindow().setAttributes(lp);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


                    }
                });
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 0.6f;
                        getWindow().setAttributes(lp);
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


                    }
                });
                dialog.show();
                break;
            case R.id.re_groupheader:
                //TODO 修改群头像
                if(isadd){
                    AndPermission.with(MessageActivity.this)
                            .requestCode(REQUEST_CODE_PERMISSION_CAMERA_SD)
                            .permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                            // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                            .rationale(new RationaleListener() {
                                @Override
                                public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                    AndPermission.rationaleDialog(MessageActivity.this, rationale).show();
                                }
                            })
                            .send();
                }else {
                    ToastUtils.show("抱歉，您不能修改群头像，请联系管理员");
                }

                break;
            case R.id.text_exit_group:
                //TODO 删除并且退出
                dialog = new ClearChatDialog(this);
                if (id .equals(PreferManager.getUserId()) ){
                    dialog.setTitle("确认解散群组？");
                }else {
                    dialog.setTitle("确认退出该群？");
                }

                dialog.setYesOnclickListener("确认", new ClearChatDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        if (id .equals(PreferManager.getUserId()) ){
                            //TODO
                            ProxyUtils.getHttpProxy().dissolutiongroup(MessageActivity.this, Integer.valueOf(PreferManager.getUserId()), Integer.valueOf(groupid));


                        } else {
                            //TODO
                            ProxyUtils.getHttpProxy().exitgroup(MessageActivity.this, Integer.valueOf(PreferManager.getUserId()), Integer.valueOf(groupid));

                        }
                        dialog.dismiss();

                    }
                });
                dialog.setNoOnclickListener("取消", new ClearChatDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.text_showgroupuserlist:
                Intent intent=new Intent(this,ShowGroupUserListActivity.class);
                //TODO 显示群成员
                if(id.equals(PreferManager.getUserId())){
                    intent.putExtra("type","adminuser");
                }else {
                    intent.putExtra("type","user");
                }
                intent.putExtra("groupid",groupid);
                intent.putExtra("userid",id);
                startActivity(intent);
                break;

        }

    }

    private void getexitgroup(ExitGroupBean data) {
        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, groupid, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean)
            {

            }



            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        setResult(10);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                default:

                    s = dialogSelect.onActivityResult(MessageActivity.this, requestCode, resultCode, data);
                    //ImageLoader.getInstance().displayImage("file:///" + s, image_group, MyApplication.getSimpleOptions(R.mipmap.test_user_star, R.mipmap.test_user_star));
                    if(s!=null){
                        updatePersonInfo(new File(s));
                    }

                    break;
            }


        }
    }
    public void updatePersonInfo(File img) {
        ProxyUtils.getHttpProxy().addgrouppic(MessageActivity.this, Integer.valueOf(groupid), img);
    }
    private void getaddgrouppic(AddFrouppicBean baan){
        setImageByUrl(image_group,baan.getData(),R.mipmap.groupheader,R.mipmap.groupheader);

        RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(groupid),groupname, Uri.parse(String.valueOf(baan.getData()))));


    }
    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
        dialogSelect.getSelectPhoto(MessageActivity.this);
    }

    @Override
    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(MessageActivity.this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(MessageActivity.this, REQUEST_CODE_SETTING).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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