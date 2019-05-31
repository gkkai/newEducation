package com.meiliangzi.app.ui.view.imkit;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.CreatGroupChatBean;
import com.meiliangzi.app.model.bean.FridentListBean;
import com.meiliangzi.app.model.bean.GroupListBean;
import com.meiliangzi.app.model.bean.NoticeMessagelistBean;
import com.meiliangzi.app.model.bean.QueryMapsBeans;
import com.meiliangzi.app.model.bean.StudyMessageBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseLoctionsAdapter;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.DeleatDialog;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.SideslipListView;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;

public class ChatFridentActivity extends BaseActivity {
    /*@BindView(R.id.sideslistView)
    SideslipListView listView;*/
    @BindView(R.id.gradview_group)
    GridView gradview_group;

    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.add)
    ImageView imageView_add;
    private BaseQuickAdapter<FridentListBean.DataBean> adapter;
    private BaseTrainAdapter<GroupListBean.DataBean> adapterGroup;
    private List<GroupListBean.DataBean> data=new ArrayList<>();
    String type;
    private FridentListBean.DataBean position;
    private DeleatDialog deleatDialog;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_chat_frident);

    }

    @Override
    protected void findWidgets() {
        type=getIntent().getStringExtra("type");
        /*listViewFrident.setPullLoadEnable(true);
        listViewFrident.setPullRefreshEnable(true);*/

if(type!=null&&type.equals("frident")){
    text_title.setText("好友");

    adapter=new BaseQuickAdapter<FridentListBean.DataBean>(this,R.layout.item_addfrientchlied_lists) {
        @Override
        public void convert(BaseViewHolder helper, final FridentListBean.DataBean item) {
            helper.setImageByUrl(R.id.ivImg_userheader,item.getUserHead(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);
            helper.setText(R.id.tv_username,item.getNickName());
            helper.setText(R.id.tv_navigation,"删除");
            helper.setOnClickListener(R.id.tv_navigation, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleatDialog =new DeleatDialog(ChatFridentActivity.this);
                    deleatDialog.setTitle("提示");
                    deleatDialog.setMessage("是否删除");
                    deleatDialog.setYesOnclickListener("确认", new DeleatDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            position=item;
                            id=String.valueOf(item.getId());
                            //TODO 删除好友
                            ProxyUtils.getHttpProxy().deletefriend(ChatFridentActivity.this, Integer.valueOf(PreferManager.getUserId()), item.getId());
                            deleatDialog.dismiss();
                        }
                    });
                    deleatDialog.setNoOnclickListener("取消", new DeleatDialog.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            deleatDialog.dismiss();

                        }
                    });
                    deleatDialog.show();
                    deleatDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.alpha = 1.0f;
                            getWindow().setAttributes(lp);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


                        }
                    });
                    deleatDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.alpha = 0.6f;
                            getWindow().setAttributes(lp);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);


                        }
                    });
                }
            });
            RongIM.getInstance().refreshUserInfoCache(new UserInfo(String.valueOf(item.getId()),item.getNickName(), Uri.parse(item.getUserHead())));


        }
    };
    gradview_group.setAdapter(adapter);
}else if(type!=null&&type.equals("group")){
    text_title.setText("群组");
    imageView_add.setVisibility(View.VISIBLE);
    imageView_add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(ChatFridentActivity.this,SearchResultActivity.class);
            intent.putExtra("type","GROUP");
            startActivity(intent);
        }
    });
    adapterGroup=new BaseTrainAdapter<GroupListBean.DataBean>(this,gradview_group, R.layout.item_addfrientchlied_lists) {
        @Override
        public void convert(BaseViewHolder helper, final GroupListBean.DataBean item) {
            helper.setImageByUrl(R.id.ivImg_userheader,item.getImage(),R.mipmap.groupheader,R.mipmap.groupheader);

            helper.setText(R.id.tv_username,item.getGroupName());
            helper.getView(R.id.tv_navigation).setVisibility(View.GONE);


        }
    };
    gradview_group.setAdapter(adapterGroup);
}


    }
    private void getagreefriendapply(AgreeFriendapplyBean bean){
        RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, id, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
        adapter.getmDatas().remove(position);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void initComponent() {





    }


    private void getfriendlist(FridentListBean bean){

        adapter.setDatas(bean.getData());
        adapter.notifyDataSetChanged();

    }
    private void getusergrouplist(GroupListBean bean){
        data.addAll(bean.getData());
        adapterGroup.setDatas(data);
        adapterGroup.notifyDataSetChanged();

    }

    @Override
    protected void initListener() {
        super.initListener();
        gradview_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type!=null&&type.equals("frident")){
                    RongIM.getInstance().startConversation(ChatFridentActivity.this, Conversation.ConversationType.PRIVATE,String.valueOf(adapter.getItem(position).getId()),adapter.getItem(position).getNickName());
                    finish();

                }else if(type!=null&&type.equals("group")){
                    RongIM.getInstance().startConversation(ChatFridentActivity.this, Conversation.ConversationType.GROUP,String.valueOf(adapterGroup.getItem(position).getId()),adapterGroup.getItem(position).getGroupName());
                    finish();
                }
                  }
        });

    }

    @Override
    protected void onResume() {
        data.clear();
        if(type!=null&&type.equals("frident")){
            //获取好友列表

            ProxyUtils.getHttpProxy().friendlist(this, Integer.valueOf(PreferManager.getUserId()));

        }else if(type!=null&&type.equals("group")){
            //获取我的群组列表

            ProxyUtils.getHttpProxy().usergrouplist(this, Integer.valueOf(PreferManager.getUserId()));
            //TODO 获取我参与的群组
            ProxyUtils.getHttpProxy().grouplist(this, Integer.valueOf(PreferManager.getUserId()));

        }

        super.onResume();
    }


    @Override
    protected void showErrorMessage(String errorMessage) {
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
    }
}
