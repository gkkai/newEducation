package com.meiliangzi.app.ui.view.imkit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.GroupinfoBean;
import com.meiliangzi.app.model.bean.SearchGroupBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.widget.XListView;


import java.util.Locale;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

public class ConversationActivity extends FragmentActivity implements View.OnClickListener {
    private String mTargetId; //目标 Id
    private Conversation.ConversationType mConversationType; //会话类型
    TextView text_title;
    ImageView image_left,image_right;
    String type=null;
    TextView showmessage;
    ImageView test_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversationactivity);
        text_title= (TextView) findViewById(R.id.text_title);
        image_left= (ImageView) findViewById(R.id.image_left);
        image_right=(ImageView) findViewById(R.id.image_right);
        test_right=(ImageView) findViewById(R.id.test_right);

        showmessage= (TextView) findViewById(R.id.text_addfrident);
        Intent intent = getIntent();

            RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
                @Override
                public void getGroupMembers(String groupId, RongIM.IGroupMemberCallback callback) {
                    //获取群组成员信息列表
                    //callback.onGetGroupMembersResult(list); // 调用 callback 的 onGetGroupMembersResult 回传群组信息
                }
            });
            mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.US));
        mTargetId=intent.getData().getQueryParameter("targetId");
        text_title.setText(intent.getData().getQueryParameter("title"));

        if(Conversation.ConversationType.GROUP.equals(mConversationType)){
                test_right.setVisibility(View.VISIBLE);
                type="GROUP";
            //TODO
            ProxyUtils.getHttpProxy().groupinfo(this, Integer.valueOf(mTargetId));


        }
            if(Conversation.ConversationType.PRIVATE.equals(mConversationType)){
                //image_right.setVisibility(View.VISIBLE);
                type="PRIVATE";
                ProxyUtils.getHttpProxy().isfriend(this, Integer.valueOf(PreferManager.getUserId()),Integer.valueOf(mTargetId));

            }

        image_left.setOnClickListener(this);
            test_right.setOnClickListener(this);
            image_right.setOnClickListener(this);
        }


    private void getisfriend(AgreeFriendapplyBean bean){
        if("1".equals(bean.getData())){
        }else {
            showmessage.setVisibility(View.VISIBLE);
            ToastUtils.show("请先添加好友");
        }
    }
    private void getgroupinfo(GroupinfoBean data){
        if(data.getData()!=null){
        }else {
            showmessage.setVisibility(View.VISIBLE);
            ToastUtils.show("该群已经被解散");
        }
    }
    /**
     * 后退按钮响应事件
     */
    public void onBackClick(View v) {
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //TODO 群信息
            case R.id.image_right:
                Intent intentfridnt=new Intent(this,MessageActivity.class);

                startActivity(intentfridnt);
                break;
            //TODO 好友信息
            case R.id.test_right:
                Intent intentgroup=new Intent(this,MessageActivity.class);
                intentgroup.putExtra("type",type);
                intentgroup.putExtra("id",mTargetId);
                startActivityForResult(intentgroup,10010);
                //finish();
                break;
            //TODO 返回
            case R.id.image_left:
                finish();
                break;
        }

    }
    protected void showErrorMessage(String errorMessage) {
        showmessage.setVisibility(View.VISIBLE);
        test_right.setVisibility(View.GONE);
        showmessage.setText("该群不存在");
        ToastUtils.show(errorMessage);

    }

    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        showmessage.setVisibility(View.VISIBLE);
        test_right.setVisibility(View.GONE);
        showmessage.setText("该群不存在");
        ToastUtils.show(errorMessage);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==10){
            switch (requestCode){
                case 10010:
                    finish();
                    break;

            }

        }
    }
}
