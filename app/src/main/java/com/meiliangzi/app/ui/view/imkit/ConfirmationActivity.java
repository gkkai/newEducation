package com.meiliangzi.app.ui.view.imkit;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.ConfirmationBean;
import com.meiliangzi.app.model.bean.FridentListBean;
import com.meiliangzi.app.model.bean.NoticeMessagelistBean;
import com.meiliangzi.app.model.bean.StudyCenternBean;
import com.meiliangzi.app.model.bean.StudyMessageBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.ArticalDetailActivity;
import com.meiliangzi.app.ui.VideoDetailActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseLoctionsAdapter;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.DeleatDialog;
import com.meiliangzi.app.widget.SideslipListView;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

import static com.meiliangzi.app.R.id.conversation;


public class ConfirmationActivity extends BaseActivity  {
    @BindView(R.id.sideslistView)
    SideslipListView listView;
    @BindView(R.id.listView)
    XListView xlistView;
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_nodata)
    TextView text_nodata;
    @BindView(R.id.text_clear)
    TextView text_clear;
    String type;
    DeleatDialog deleatDialog;
    @BindView(R.id.backgroud)
    RelativeLayout backgroud;
    /**
     * 最多展示3行。
     */
    private static final int LINES = 3;
    private BaseQuickAdapter<ConfirmationBean.DataBean> adapter;
    private BaseLoctionsAdapter<NoticeMessagelistBean.DataBean> adaptersystem;
    private BaseLoctionsAdapter<StudyMessageBean.Databean> adapterstudy;;
    private ConfirmationBean.DataBean position;
    private StudyMessageBean.Databean positionstudy;
    private NoticeMessagelistBean.DataBean positionsystem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_confirmation);
    }

    @Override
    protected void findWidgets() {
        xlistView.setPullLoadEnable(false);
        xlistView.setPullRefreshEnable(false);
         type=   getIntent().getStringExtra("type");

        if("system".equals(type)){
            xlistView.setVisibility(View.VISIBLE);
            backgroud.setBackgroundColor(Color.parseColor("#f2f2f2"));
            text_title.setText("通知公告");
            text_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 清空
                    ProxyUtils.getHttpProxy().emptynoticemessage(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()));

                }
            });
            adaptersystem=new BaseLoctionsAdapter<NoticeMessagelistBean.DataBean>(this, R.layout.message_system_study) {
                @Override
                public void convert(final BaseViewHolder helper, NoticeMessagelistBean.DataBean item) {
                    helper.setText(R.id.text_messaecount,item.getContent());
                    helper.setText(R.id.text_time,item.getCreate_at());
                    helper.setText(R.id.text_title,item.getTitle());
                    helper.showOrGoneView(R.id.text_showall,false);
                    helper.getView(R.id.text_messaecount).post(new Runnable() {
                        @Override
                        public void run() {

                           if(((TextView)helper.getView(R.id.text_messaecount)).getLineCount()>=LINES){
                               ((TextView)helper.getView(R.id.text_messaecount)).setMaxLines(LINES);
                               helper.showOrGoneView(R.id.text_showall,true);
                               helper.setOnClickListener(R.id.text_showall, new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       //展示全部，按钮设置为点击收起。
                                       ((TextView)helper.getView(R.id.text_messaecount)).setMaxHeight(getResources().getDisplayMetrics().heightPixels);
                                       helper.showOrGoneView(R.id.text_showall,false);

                                   }
                               });
                                }
                           }
                    });
                }
            };

            xlistView.setAdapter(adaptersystem);
            xlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    deleatDialog =new DeleatDialog(ConfirmationActivity.this);
                    deleatDialog.setYesOnclickListener("确认", new DeleatDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            positionsystem=adaptersystem.getItem(position-1);
                            //TODO 长按删除
                            ProxyUtils.getHttpProxy().deletenoticemessage(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()),adaptersystem.getItem(position-1).getId());
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

                    return true;
                }
            });
        }else if("study".equals(type)){
            xlistView.setVisibility(View.VISIBLE);
            text_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 清空
                    ProxyUtils.getHttpProxy().emptynoticemessage(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()));

                }
            });
            text_title.setText("学习消息");
            adapterstudy=new BaseLoctionsAdapter<StudyMessageBean.Databean>(this, R.layout.item_index) {
                @Override
                public void convert(BaseViewHolder helper, StudyMessageBean.Databean item) {
                    helper.setText(R.id.tv_new_title, item.getTitle());
                    helper.setText(R.id.tv_new_time, item.getCreate_at());
                    //helper.setText(R.id.tv_new_collect_num, item.get);
                   /* if (item.getIs_praise().equals("1")) {
                        helper.setImageResource(R.id.iv_new_collect, R.mipmap.ic_support_unselected);
                    } else {
                        helper.setImageResource(R.id.iv_new_collect, R.mipmap.ic_support_selected);
                    }*/
                   helper.showOrGoneView(R.id.tv_new_collect_num,false);
                    helper.showOrGoneView(R.id.iv_new_collect,false);
                    helper.setImageByUrl(R.id.iv_news_img, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);

                    if (item.getType()==2) {
                        helper.getView(R.id.icPlay).setVisibility(View.GONE);
                    } else {
                        helper.getView(R.id.icPlay).setVisibility(View.VISIBLE);
                    }
                }


            };
            xlistView.setAdapter(adapterstudy);
            xlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapterstudy.getItem(position - 1).getType()==2) {
                        IntentUtils.startAtyWithSingleParam(ConfirmationActivity.this, ArticalDetailActivity.class, "id", String.valueOf(adapterstudy.getItem(position - 1).getCategory_id()));
                    } else if(adapterstudy.getItem(position - 1).getType()==1){
                        IntentUtils.startAtyWithSingleParam(ConfirmationActivity.this, VideoDetailActivity.class, "id", String.valueOf(adapterstudy.getItem(position - 1).getCategory_id()));
                    }
                }
            });
          /*  xlistView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //TODO 长按删除
                    ProxyUtils.getHttpProxy().useraddgroup(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()),adapterstudy.);

                    return false;
                }
            });*/
            xlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    deleatDialog =new DeleatDialog(ConfirmationActivity.this);
                    deleatDialog.setYesOnclickListener("确认", new DeleatDialog.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                            positionstudy=adapterstudy.getItem(position-1);
                            //TODO 长按删除
                            ProxyUtils.getHttpProxy().deletecategorymessage(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()),adapterstudy.getItem(position-1).getId());
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

                    return true;
                }
            });
        }else if("frident".equals(type)) {
            listView.setVisibility(View.VISIBLE);
            text_clear.setVisibility(View.GONE);
            text_title.setText("验证消息");
            adapter = new BaseQuickAdapter<ConfirmationBean.DataBean>(this, R.layout.item_confirmation_list) {
                @Override
                public void convert(final BaseViewHolder helper, final ConfirmationBean.DataBean item) {
                    if (item.getType() == 1) {
                        helper.setText(R.id.text_username, item.getNickName());
                        helper.setImageByUrl(R.id.image, item.getUserHead(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
                        helper.setText(R.id.text_mmessage,"请求添加您为好友");
                        if(item.getIs_refuse()==1){
                            helper.setText(R.id.tv_wait,"已通过");
                            helper.showOrGoneView(R.id.tv_wait,true);
                            helper.showOrGoneView(R.id.text_consent,false);
                            helper.showOrGoneView(R.id.text_refuse,false);
                        }else if(item.getIs_refuse()==2){
                            helper.setText(R.id.tv_wait,"未通过");
                            helper.showOrGoneView(R.id.tv_wait,true);
                            helper.showOrGoneView(R.id.text_consent,false);
                            helper.showOrGoneView(R.id.text_refuse,false);
                        }else {
                            helper.showOrGoneView(R.id.tv_wait,false);
                            helper.showOrGoneView(R.id.text_consent,true);
                            helper.showOrGoneView(R.id.text_refuse,true);
                        }
                        helper.getView(R.id.text_refuse).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position = item;
                                // MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteChannelById(item.getId());
                                int userid = item.getUserId();
                                int fridentid = item.getUserId();
                                //TODO 拒绝添加好友
                                ProxyUtils.getHttpProxy().deletefriend(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()), fridentid);


                            }
                        });
                        helper.getView(R.id.text_consent).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position = item;
                                int userid = item.getUserId();
                                int fridentid = item.getUserId();
                                //TODO 同意添加好友
                                ProxyUtils.getHttpProxy().agreefriendapply(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()), fridentid);


                            }
                        });
                        helper.getView(R.id.txtv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position = item;
                                // MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteChannelById(item.getId());
                                int userid = item.getUserId();
                                int fridentid = item.getUserId();
                                //TODO 拒绝添加好友
                                ProxyUtils.getHttpProxy().deletefriend(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()), fridentid);
listView.turnNormal();

                            }
                        });
                    } else if (item.getType() == 2) {
                        helper.setText(R.id.text_username, item.getGroupName());
                        helper.setImageByUrl(R.id.image, item.getUserHead(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
                        helper.setText(R.id.text_mmessage,"邀请您"+item.getGroupName());
                        if(item.getIs_refuse()==1){
                            helper.setText(R.id.tv_wait,"已通过");
                            helper.showOrGoneView(R.id.tv_wait,true);
                            helper.showOrGoneView(R.id.text_consent,false);
                            helper.showOrGoneView(R.id.text_refuse,false);
                        }else if(item.getIs_refuse()==2){
                            helper.setText(R.id.tv_wait,"未通过");
                            helper.showOrGoneView(R.id.tv_wait,true);
                            helper.showOrGoneView(R.id.text_consent,false);
                            helper.showOrGoneView(R.id.text_refuse,false);
                        }else {
                            helper.showOrGoneView(R.id.tv_wait,false);
                            helper.showOrGoneView(R.id.text_consent,true);
                            helper.showOrGoneView(R.id.text_refuse,true);
                        }
                        helper.getView(R.id.text_refuse).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position = item;
                                // MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteChannelById(item.getId());
                                int userid = item.getUserId();
                                int fridentid = item.getGroupId();
                                //TODO 拒绝添加好友
                                ProxyUtils.getHttpProxy().refusegroupapply(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()), fridentid,item.getGroupName());


                            }
                        });
                        helper.getView(R.id.text_consent).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position = item;
                                int userid = item.getUserId();
                                int fridentid = item.getGroupId();
                                //TODO 同意添加好友
                                ProxyUtils.getHttpProxy().agreegroupapply(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()), fridentid,item.getGroupName());


                            }
                        });
                        helper.getView(R.id.txtv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position = item;
                                // MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteChannelById(item.getId());
                                int userid = item.getUserId();
                                int fridentid = item.getGroupId();
                                //TODO 拒绝添加好友
                                ProxyUtils.getHttpProxy().refusegroupapply(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()), fridentid,item.getGroupName());
                                listView.turnNormal();

                            }
                        });

                    }else if(item.getType() == 3){
                        helper.setText(R.id.text_username, item.getNickName());
                        helper.setImageByUrl(R.id.image, item.getUserHead(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
                        helper.setText(R.id.text_mmessage,"申请加入"+item.getGroupName());
                        if(item.getIs_refuse()==1){
                            helper.setText(R.id.tv_wait,"已通过");
                            helper.showOrGoneView(R.id.tv_wait,true);
                            helper.showOrGoneView(R.id.text_consent,false);
                            helper.showOrGoneView(R.id.text_refuse,false);
                        }else if(item.getIs_refuse()==2){
                            helper.setText(R.id.tv_wait,"未通过");
                            helper.showOrGoneView(R.id.tv_wait,true);
                            helper.showOrGoneView(R.id.text_consent,false);
                            helper.showOrGoneView(R.id.text_refuse,false);
                        }else {
                            helper.showOrGoneView(R.id.tv_wait,false);
                            helper.showOrGoneView(R.id.text_consent,true);
                            helper.showOrGoneView(R.id.text_refuse,true);
                        }

                            /*helper.showOrGoneView(R.id.text_consent,true);
                            helper.showOrGoneView(R.id.text_refuse,true);*/
                            helper.getView(R.id.text_consent).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    position = null;
                                    // MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteChannelById(item.getId());
                                    int userid = item.getUserId();
                                    int fridentid = item.getGroupId();
                                    int id=item.getId();
                                    item.setIs_refuse(1);
                                    //TODO 管理员同意加入群组
                                    ProxyUtils.getHttpProxy().agreeaddgroup(ConfirmationActivity.this, userid, fridentid,id);

                                    helper.setText(R.id.tv_wait,"已通过");
                                    helper.showOrGoneView(R.id.tv_wait,true);
                                    helper.showOrGoneView(R.id.text_consent,false);
                                    helper.showOrGoneView(R.id.text_refuse,false);
                                }
                            });
                            helper.getView(R.id.text_refuse).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    position = null;
                                    int userid = item.getUserId();
                                    int fridentid = item.getGroupId();
                                    int id=item.getId();
                                    helper.setText(R.id.tv_wait,"未通过");
                                    helper.showOrGoneView(R.id.tv_wait,true);
                                    helper.showOrGoneView(R.id.text_consent,false);
                                    helper.showOrGoneView(R.id.text_refuse,false);
                                    item.setIs_refuse(2);
                                    //TODO 管理员拒绝加入群组
                                    ProxyUtils.getHttpProxy().refuseaddgroup(ConfirmationActivity.this, userid, fridentid,id);


                                }
                            });
                        helper.getView(R.id.txtv_delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                position = item;
                                // MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteChannelById(item.getId());
                                int userid = item.getUserId();
                                int fridentid = item.getGroupId();
                                int id=item.getId();

                                //TODO 管理员删除信息
                                ProxyUtils.getHttpProxy().deleteaddgroupmessage(ConfirmationActivity.this, Integer.valueOf(PreferManager.getUserId()), fridentid,id);
                                listView.turnNormal();

                            }
                        });
                        }





                }

            };
            listView.setAdapter(adapter);

            }

    }

    @Override
    protected void initComponent() {
        if("system".equals(type)){
            ///获取系统消息接口

            ProxyUtils.getHttpProxy().noticemessagelist(this, Integer.valueOf(PreferManager.getUserId()),1);

        }else if("study".equals(type)){

//获取学习消息接口

            ProxyUtils.getHttpProxy().categorymessagelist(this, Integer.valueOf(PreferManager.getUserId()),1);

        }else if("frident".equals(type)){
            //TODO 添加好友
            ProxyUtils.getHttpProxy().friendapplymessage(this, Integer.valueOf(PreferManager.getUserId()));


        }




    }
    private void getnoticemessagelist(NoticeMessagelistBean bean){
        //setImageByUrl(image,bean.getData());
        adaptersystem.setDatas(bean.getData());
        adaptersystem.notifyDataSetChanged();
    }
    private void getcategorymessagelist(StudyMessageBean bean){
        //setImageByUrl(image,bean.getData());
        adapterstudy.setDatas(bean.getData());
        adapterstudy.notifyDataSetChanged();
    }
    private void getagreefriendapply(AgreeFriendapplyBean bean){
        //ProxyUtils.getHttpProxy().friendapplymessage(this, Integer.valueOf(PreferManager.getUserId()));
if(position==null){

}else {
    adapter.getmDatas().remove(position);
    adapter.notifyDataSetChanged();
}}
    private void getdeleteaddgroupmessage(AgreeFriendapplyBean bean){
       // ProxyUtils.getHttpProxy().friendapplymessage(this, Integer.valueOf(PreferManager.getUserId()));
        if(position==null){

        }else {
            adapter.getmDatas().remove(position);
            adapter.notifyDataSetChanged();
        }

    }
    private void getdeletecategorymessage(AgreeFriendapplyBean bean){
        adapterstudy.getmDatas().remove(positionstudy);
        adapterstudy.notifyDataSetChanged();

    }
    private void getdeletenoticemessage(AgreeFriendapplyBean bean){
        adaptersystem.getmDatas().remove(positionsystem);
        adaptersystem.notifyDataSetChanged();

    }
    private void getfriendapplymessage(ConfirmationBean bean){
        adapter.setDatas(bean.getData());
        adapter.notifyDataSetChanged();
    }
    private void getemptynoticemessage(AgreeFriendapplyBean bean){
        if("system".equals(type)){
            ///获取系统消息接口
            adaptersystem.clear();
            adaptersystem.notifyDataSetChanged();

        }else if("study".equals(type)){

//获取学习消息接口
            adapterstudy.clear();
            adapterstudy.notifyDataSetChanged();

        }

    }

    @Override
    public void onBackClick(View v) {

        super.onBackClick(v);
    }

    @Override
    protected void onCreateView(int layoutResID) {
        super.onCreateView(layoutResID);
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {

        if(errorCode==1&&errorMessage.equals("暂无数据")){
            text_nodata.setVisibility(View.VISIBLE);
        }else {
            text_nodata.setVisibility(View.GONE);
            super.showErrorMessage(errorCode, errorMessage);
        }
    }
    /**
     * 自定义ListView适配器
     *//*
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
                convertView = View.inflate(ConfirmationActivity.this, R.layout.item_chat_list, null);
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
        public TextView consent;
        public TextView txtv_top;
        public TextView text_mmessage;
        public RelativeLayout  relativeLayout;
    }*/
}
