package com.meiliangzi.app.ui.view.imkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.ExitGroupBean;
import com.meiliangzi.app.model.bean.GroupUserlistBean;
import com.meiliangzi.app.model.bean.SetAdminBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.DelectGroupUserDialog;
import com.meiliangzi.app.widget.XListView;

import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class ShowGroupUserListActivity extends BaseActivity {
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.listViewUser)
    XListView listViewUser;
    BaseTrainAdapter<GroupUserlistBean.DataBean.UserInfo> listadapter;
    private String groupid;
    private String userid;
    private String type;
    private List<Integer> adminIds;
private DelectGroupUserDialog  dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_show_group_user_list);

    }

    @Override
    protected void findWidgets() {


        listViewUser.setPullLoadEnable(false);
        listViewUser.setPullRefreshEnable(false);
        listadapter = new BaseTrainAdapter<GroupUserlistBean.DataBean.UserInfo>(this, listViewUser, R.layout.item_setgroup_admin) {

            @Override
            public void convert(BaseViewHolder helper, final GroupUserlistBean.DataBean.UserInfo item) {
                if(type.equals("adminuser")){


     if(adminIds.contains(item.getId())){
    //TODO 是管理员
    helper.setImageByUrl(R.id.image_group_userheader, item.getAvatar(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
    helper.getView(R.id.text_group_admin).setVisibility(View.VISIBLE);
    ((TextView)helper.getView(R.id.text_cenele_admin)).setText("取消管理员");

    helper.setText(R.id.text_group_name, item.getNickName());
    helper.getView(R.id.text_cenele_admin).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO 取消管理员
            ProxyUtils.getHttpProxy().removeadmin(ShowGroupUserListActivity.this,Integer.valueOf(item.getId()),Integer.valueOf(groupid));

        }
    });

}else {
    //TODO 不是管理员
    helper.setImageByUrl(R.id.image_group_userheader, item.getAvatar(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
    helper.getView(R.id.text_group_admin).setVisibility(View.GONE);
    if(Integer.valueOf(userid)==(item.getId())){
        helper.getView(R.id.text_cenele_admin).setVisibility(View.GONE);
    }else {
        helper.getView(R.id.text_cenele_admin).setVisibility(View.GONE);
        ((TextView)helper.getView(R.id.text_cenele_admin)).setText("设置管理员");


    }

    helper.setText(R.id.text_group_name, item.getNickName());
    helper.getView(R.id.text_cenele_admin).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO 设置管理员
            ProxyUtils.getHttpProxy().addadmin(ShowGroupUserListActivity.this,Integer.valueOf(item.getId()),Integer.valueOf(groupid));

        }
    });

}


            }else if(type.equals("user")){

                    if(adminIds.contains(item.getId())){
                        //TODO 是管理员
                        helper.setImageByUrl(R.id.image_group_userheader, item.getAvatar(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
                        helper.getView(R.id.text_group_admin).setVisibility(View.VISIBLE);
                        helper.setText(R.id.text_group_name, item.getNickName());
                        helper.getView(R.id.text_cenele_admin).setVisibility(View.GONE);

                    }else {
                        //TODO 不是管理员
                        helper.setImageByUrl(R.id.image_group_userheader, item.getAvatar(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
                        helper.getView(R.id.text_group_admin).setVisibility(View.GONE);
                        helper.setText(R.id.text_group_name, item.getNickName());
                        helper.getView(R.id.text_cenele_admin).setVisibility(View.GONE);

                    }

                }
            }
        };

        listViewUser.setAdapter(listadapter);


    }

    @Override
    protected void initListener() {
        super.initListener();
        listViewUser.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                dialog=new DelectGroupUserDialog(ShowGroupUserListActivity.this);
                if(!String.valueOf(listadapter.getItem(position-1).getId()).equals(userid)){
                    if(PreferManager.getUserId().equals(userid)){
                        if(adminIds.contains(listadapter.getItem(position-1).getId())){
                            dialog.setMessage("取消管理员");
                            dialog.setYesOnclickListener(new DelectGroupUserDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    //TODO 取消管理员
                                    ProxyUtils.getHttpProxy().removeadmin(ShowGroupUserListActivity.this,Integer.valueOf(listadapter.getItem(position-1).getId()),Integer.valueOf(groupid));
                                    dialog.dismiss();


                                }
                            });
                        }else {
                            dialog.setMessage("设置管理员");
                            dialog.setYesOnclickListener(new DelectGroupUserDialog.onYesOnclickListener() {
                                @Override
                                public void onYesClick() {
                                    //TODO 设置管理员
                                    ProxyUtils.getHttpProxy().addadmin(ShowGroupUserListActivity.this,Integer.valueOf(listadapter.getItem(position-1).getId()),Integer.valueOf(groupid));

                                    dialog.dismiss();
                                }
                            });
                        }

                        dialog.setNoOnclickListener(new DelectGroupUserDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {

                                //TODO 退出群组
                                ProxyUtils.getHttpProxy().exitgroup(ShowGroupUserListActivity.this, Integer.valueOf(listadapter.getItem(position-1).getId()), Integer.valueOf(groupid));
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else if(adminIds.contains(Integer.valueOf(PreferManager.getUserId()))){
                        dialog.showsetadminview(true);
                        //TODO 是管理员
                        dialog.setNoOnclickListener(new DelectGroupUserDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                if(String.valueOf(listadapter.getItem(position-1).getId()).equals(userid)||adminIds.contains(listadapter.getItem(position-1).getId())){
                                    ToastUtils.show("您没有权限移除管理员");
                                    dialog.dismiss();

                                }else {
                                    //TODO 退出群组
                                    ProxyUtils.getHttpProxy().exitgroup(ShowGroupUserListActivity.this, Integer.valueOf(listadapter.getItem(position-1).getId()), Integer.valueOf(groupid));
                                    dialog.dismiss();
                                }

                            }
                        });
                        dialog.show();
                    }

                }else {

                }


                return false;
            }
        });
    }

    private void getsetadmin(SetAdminBean bean){
    ProxyUtils.getHttpProxy().groupuserlist(this, Integer.valueOf(groupid));


}
    @Override
    protected void initComponent() {
        groupid = getIntent().getStringExtra("groupid");
        type= getIntent().getStringExtra("type");
        userid= getIntent().getStringExtra("userid");
//TODO
        ProxyUtils.getHttpProxy().groupuserlist(this, Integer.valueOf(groupid));

    }
    private void getgroupuserlist(GroupUserlistBean data) {
       adminIds = data.getData().getGroupInfo().getGroupAdminId();
        listadapter.setDatas(data.getData().getUserInfo());
    }
    private void getexitgroup(ExitGroupBean data) {
        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, groupid, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {

            }


            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        ProxyUtils.getHttpProxy().groupuserlist(this, Integer.valueOf(groupid));

    }

       /* setResult(10);

        finish();*/
    }

