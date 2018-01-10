package com.meiliangzi.app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.ArticalList;
import com.meiliangzi.app.model.bean.GroupinfoBean;
import com.meiliangzi.app.model.bean.MessageListBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.view.imkit.ChatFridentActivity;
import com.meiliangzi.app.ui.view.imkit.ConfirmationActivity;
import com.meiliangzi.app.ui.view.imkit.MyConversationListBehaviorListener;
import com.meiliangzi.app.widget.MorePopWindow;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;

/**
 * @author xiaobo
 * @date   2017/8/16
 * @version  1.0
 * @description  消息
 *
 **/

public class MsgFragment extends BaseFragment implements View.OnClickListener, RongIM.UserInfoProvider, RongIM.GroupInfoProvider, RongIM.GroupUserInfoProvider {


    ImageView image;
    ImageView image2;
    ImageView image_suermessage;
    private BaseQuickAdapter<ArticalList.DataBean> listViewAdapter;
    private int height;
    TextView text_systemmessage;
    TextView text_studymessage;
    TextView text_suermessage;
    TextView text_systemmessageuread;
    TextView text_studymessageuread;
    TextView text_suermessageuread;
    FrameLayout frameLayout;
    @BindView(R.id.text_frident)
    TextView text_frident;
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.image_addfrident)
    ImageView image_addfrident;
    List<Conversation> conversation;
    private String sendid;
    RelativeLayout rl_system;
    RelativeLayout re_confirmation;
    RelativeLayout rl_study;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.text_group)
    TextView text_group;
    private Conversation.ConversationType[] mConversationsTypes = null;

    public MsgFragment() {
    }

    public static MsgFragment newInstance(String param1, String param2) {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return createView(inflater.inflate(R.layout.fragment_msg,null,false));
    }

    @Override
    protected void findWidgets() {

        final View headView = getActivity().getLayoutInflater().inflate(R.layout.headview_message, null, false);
         image= (ImageView) headView.findViewById(R.id.image);
        image2=(ImageView) headView.findViewById(R.id.image2);
        image_suermessage=(ImageView) headView.findViewById(R.id.image_suermessage);
        text_systemmessage=(TextView) headView.findViewById(R.id.text_systemmessage);
        text_studymessage=(TextView) headView.findViewById(R.id.text_studymessage);
        frameLayout= (FrameLayout) headView.findViewById(R.id.conversationlist);
        text_suermessage=(TextView) headView.findViewById(R.id.text_suermessage);
        text_systemmessageuread=(TextView) headView.findViewById(R.id.text_systemmessageuread);
        text_studymessageuread=(TextView) headView.findViewById(R.id.text_studymmessageuread);
        text_suermessageuread=(TextView) headView.findViewById(R.id.text_suermmessageuread);
        rl_system= (RelativeLayout) headView.findViewById(R.id.rl_system);
        re_confirmation= (RelativeLayout) headView.findViewById(R.id.re_confirmation);
        rl_study= (RelativeLayout) headView.findViewById(R.id.rl_study);

        text_frident.setOnClickListener( this);
        text_group.setOnClickListener(this);
        re_confirmation.setOnClickListener(this);
        image_addfrident.setOnClickListener(this);
        rl_system.setOnClickListener(this);
        
        rl_study.setOnClickListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        listViewAdapter = new BaseQuickAdapter<ArticalList.DataBean>(getActivity(), listView, R.layout.item_index) {
            @Override
            public void convert(BaseViewHolder helper, ArticalList.DataBean item) {

            }
        };
        listView.setAdapter(listViewAdapter);
        listView.addHeaderView(headView, null, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        isLogin();


    }
    private void isLogin() {
        if(TextUtils.isEmpty(PreferManager.getTokens())){
            tvEmpty.setText("请重新登录");
            tvEmpty.setVisibility(View.VISIBLE);
        }else {
            if (TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    tvEmpty.setText("请先登录");
                } else {
                    tvEmpty.setText("请完善个人信息");
                }
                tvEmpty.setVisibility(View.VISIBLE);

            }else {
                RongIM.getInstance().setCurrentUserInfo(new UserInfo(PreferManager.getUserId(),PreferManager.getUserName(),Uri.parse(PreferManager.getUserStar())));

                RongIM.getInstance().setMessageAttachedUserInfo(true);


                tvEmpty.setVisibility(View.GONE);
                ConversationListFragment fragment = new ConversationListFragment();
                Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM};

                fragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.conversationlist, fragment);
                transaction.commit();
/*
 *
 * 设置会话列表界面操作的监听器。
 */
                RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());
                //获取会话列表
                RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
                    @Override
                    public void onSuccess(List<Conversation> conversations) {

                        // ToastUtils.show("");
                        conversation=conversations;
                        if(conversation!=null){
                            int szie= conversation.size();
                            if(szie>0){

                                height=dip2px(getActivity(),szie*65);

                            }
                            for(int i=0;i<conversations.size();i++){
                                if(conversations.get(i).getConversationType().equals(Conversation.ConversationType.NONE)){
                                    if("清除会话".equals(conversations.get(i).getConversationTitle())){
                                        RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, conversations.get(i).getTargetId(), new RongIMClient.ResultCallback<Boolean>() {
                                            @Override
                                            public void onSuccess(Boolean aBoolean) {

                                            }

                                            @Override
                                            public void onError(RongIMClient.ErrorCode errorCode) {

                                            }
                                        });
                                    }else if("清除群组".equals(conversations.get(i).getConversationTitle())){
                                        RongIM.getInstance().removeConversation(Conversation.ConversationType.GROUP, conversations.get(i).getTargetId(), new RongIMClient.ResultCallback<Boolean>() {
                                            @Override
                                            public void onSuccess(Boolean aBoolean) {

                                            }

                                            @Override
                                            public void onError(RongIMClient.ErrorCode errorCode) {

                                            }
                                        });
                                    }
                                }
                            }
                            ViewGroup.LayoutParams lp= frameLayout.getLayoutParams();
                            lp.height=height;
                            frameLayout.setLayoutParams(lp);
                       /* ConversationListFragment fragment = new ConversationListFragment();
                        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                                .appendPath("conversationlist")
                                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                                .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                                .build();


                        fragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.conversationlist, fragment);
                        transaction.commit();*/

                        }else {

                        }


                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                },mConversationsTypes);


                ProxyUtils.getHttpProxy().messagelist(this, Integer.valueOf(PreferManager.getUserId()));

            }
        }

    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    protected void initComponent() {

        //获取系统消息接口

        //ProxyUtils.getHttpProxy().noticemessagelist(this, Integer.valueOf(PreferManager.getUserId()),1);


    }

    @Override
    protected void initListener() {
        super.initListener();
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyWithSingleParam(getActivity(), LoginActivity.class, "activity", "WholeFragment");
                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyWithSingleParam(getActivity(), PersonCenterActivity.class, "activity", "WholeFragment");
                }else {
                    IntentUtils.startAtyWithSingleParam(getActivity(), LoginActivity.class, "activity", "WholeFragment");

                }

            }
        });
    }

    private void getmessagelist(MessageListBean bean){
        //TODO  设置系统消息
        if(bean.getData().getNotice().getContent()==null){
            text_systemmessage.setText("暂无消息");
            text_systemmessageuread.setVisibility(View.GONE);
            setImageByUrl(image,bean.getData().getNotice().getImg(),R.mipmap.systemmessage,R.mipmap.systemmessage);

        }else {
            text_suermessage.setText(bean.getData().getNotice().getTitle());
            text_systemmessageuread.setVisibility(View.VISIBLE);
            setImageByUrl(image,bean.getData().getNotice().getImg(),R.mipmap.systemmessage,R.mipmap.systemmessage);
            text_systemmessage.setText(bean.getData().getNotice().getTitle());
        }
        //TODO  学习消息
        if(bean.getData().getCategory().getTitle()==null){
            text_studymessage.setText("暂无消息");
            text_studymessageuread.setVisibility(View.GONE);
            setImageByUrl(image2,bean.getData().getCategory().getImg(),R.mipmap.studymessage,R.mipmap.studymessage);

        }else {
            text_suermessage.setText(bean.getData().getCategory().getTitle());
            text_studymessageuread.setVisibility(View.VISIBLE);
            setImageByUrl(image2,bean.getData().getCategory().getImg(),R.mipmap.studymessage,R.mipmap.studymessage);

            text_studymessage.setText(bean.getData().getCategory().getTitle());
    }

       //TODO  验证消息
        if(bean.getData().getFriends().getNickName()==null){
            text_suermessage.setText("暂无消息");
            text_suermessageuread.setVisibility(View.GONE);
            setImageByUrl(image_suermessage,bean.getData().getFriends().getUserHead(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);

        }else {
            text_suermessageuread.setVisibility(View.VISIBLE);
            setImageByUrl(image_suermessage,bean.getData().getFriends().getUserHead(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);

            if(bean.getData().getFriends().getType()==2){
                text_suermessage.setText(bean.getData().getFriends().getNickName()+"邀请您加入"+bean.getData().getFriends().getGroupName()+"群");
            }else if(bean.getData().getFriends().getType()==3){
                text_suermessage.setText(bean.getData().getFriends().getNickName()+"申请加入"+bean.getData().getFriends().getGroupName());
            }else {
                text_suermessage.setText(bean.getData().getFriends().getNickName()+"请求添加您为好友");
            }


        }
        }

    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(PreferManager.getUserId())){
            ToastUtils.show("请先登陆");
        }else {
            switch (v.getId()){

                //TODO 跳转添加好友列表
                case R.id.image_addfrident:
                    final MorePopWindow morePopWindow = new MorePopWindow(getActivity());
                    morePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            // popupWindow隐藏时恢复屏幕正常透明度
                            morePopWindow.setBackgroundAlpha(1.0f);
                        }
                    });
                    morePopWindow.showPopupWindow(image_addfrident);
                    break;
                case R.id.re_confirmation:
                    //TODO 跳转验证消息列表
                    Intent intent=new Intent(getActivity(), ConfirmationActivity.class);
                    intent.putExtra("type","frident");
                    startActivity(intent);
                    break;
                case R.id.rl_study:
                    //TODO 跳转学习消息列表
                    Intent intentstudy=new Intent(getActivity(), ConfirmationActivity.class);
                    intentstudy.putExtra("type","study");
                    startActivity(intentstudy);
                    break;
                case R.id.rl_system:
                    //TODO 跳转系统消息列表
                    Intent intentsystem=new Intent(getActivity(), ConfirmationActivity.class);
                    intentsystem.putExtra("type","system");
                    startActivity(intentsystem);
                    break;
                case R.id.text_frident:
                    if(TextUtils.isEmpty(PreferManager.getUserId())){
                        ToastUtils.show("请先登陆");
                    }else {
                        Intent intentgroup=new Intent(getActivity(), ChatFridentActivity.class);
                        intentgroup.putExtra("type","frident");
                        startActivity(intentgroup);
                    }
                    break;
                case R.id.text_group:
                    //TODO 跳转好友消息列表
                    if(TextUtils.isEmpty(PreferManager.getUserId())){
                        ToastUtils.show("请先登陆");
                    }else {
                        Intent intentgroup=new Intent(getActivity(), ChatFridentActivity.class);
                        intentgroup.putExtra("type","group");
                        startActivity(intentgroup);
                    }

               /* Map map=new HashMap();
                map.put(Conversation.ConversationType.PRIVATE.getName(), false);
                RongIM.getInstance().startConversationList(getActivity(), map);
*/
                    break;


            }
        }


    }

    @Override
    public UserInfo getUserInfo(String s) {
        if(s.equals(PreferManager.getUserId())){
            return new UserInfo(PreferManager.getUserId(),PreferManager.getUserName(),Uri.parse(PreferManager.getUserStar()));

        }
        return null;

    }

    @Override
    public Group getGroupInfo(String s) {
        //TODO
        ProxyUtils.getHttpProxy().groupinfo(this, Integer.valueOf(s));

        return null;
    }
    private void getgroupinfo(GroupinfoBean data){
        RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(data.getData().getGroupId()),data.getData().getGroupName(),Uri.parse(String.valueOf(data.getData().getGroupUserId()))));

    }

    @Override
    public GroupUserInfo getGroupUserInfo(String s, String s1) {
        //TODO
        ProxyUtils.getHttpProxy().groupinfo(this, Integer.valueOf(s));

        return null;
    }

    /**
     * 自定义ListView适配器
     */
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return conversation.size();
        }

        @Override
        public Object getItem(int position) {
            return conversation.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                convertView = View.inflate(getActivity(), R.layout.item_chat_list, null);
                viewHolder = new ViewHolder();
                viewHolder.relativeLayout=(RelativeLayout) convertView.findViewById(R.id.rel_chat);
                viewHolder.text_username = (TextView) convertView.findViewById(R.id.text_username);
                viewHolder.txtv_delete = (TextView) convertView.findViewById(R.id.txtv_delete);
                viewHolder.consent = (TextView) convertView.findViewById(R.id.text_consent);
                viewHolder.txtv_top = (TextView) convertView.findViewById(R.id.txtv_top);
                viewHolder.text_mmessage = (TextView) convertView.findViewById(R.id.text_mmessage);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(Conversation.ConversationType.SYSTEM.equals(conversation.get(position).getConversationType())){


            }
            if(conversation.get(position).getObjectName().equals("RC:InfoNtf")){


                viewHolder.text_mmessage.setText(((InformationNotificationMessage)conversation.get(position).getLatestMessage()).getMessage());

            }else if(conversation.get(position).getObjectName().equals("RC:TxtMsg")){
                viewHolder.text_mmessage.setText(((TextMessage)conversation.get(position).getLatestMessage()).getContent());

            }else if(conversation.get(position).getObjectName().equals(" RC:VcMsg")){
               // viewHolder.text_mmessage.setText(((VoiceMessage)conversation.get(position).getLatestMessage()).getUri());

            }
            else if(conversation.get(position).getObjectName().equals(" RC:ImgMsg")){
                //viewHolder.text_mmessage.setText(((ImageMessage)conversation.get(position).getLatestMessage()).getMediaUrl());

            }

            viewHolder.text_username.setText(conversation.get(position).getSenderUserName());
            final int pos = position;
            viewHolder.txtv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), conversation.get(pos) + "被删除了",
                            Toast.LENGTH_SHORT).show();
                    conversation.remove(pos);
                    notifyDataSetChanged();
                }
            });
            viewHolder.txtv_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), conversation.get(pos) + "被删除了",
                            Toast.LENGTH_SHORT).show();
                    conversation.remove(pos);
                    notifyDataSetChanged();
                }
            });
            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.CHATROOM, conversation.get(position).getTargetId(), conversation.get(position).getSenderUserName());
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        public TextView text_username;
        public TextView txtv_delete;
        public ImageView image;
        public TextView consent;
        public TextView txtv_top;
        public TextView text_mmessage;
        public RelativeLayout  relativeLayout;
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
    private void getagreefriendapply(AgreeFriendapplyBean bean){
        TextMessage message=TextMessage.obtain("你们已经是好友了");

        Message myMessage = Message.obtain(String.valueOf(sendid), Conversation.ConversationType.PRIVATE, message);
/**
 * <p>发送消息。
 * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
 * 中的方法回调发送的消息状态及消息体。</p>
 *
 * @param message     将要发送的消息体。
 * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
 *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
 *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
 * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
 * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
 */
        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });



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
    protected void showErrorMessage(Integer errorCode) {
        super.showErrorMessage(errorCode);
    }

}

