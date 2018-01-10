package com.meiliangzi.app.ui.view.imkit;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.CreatGroupChatBean;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.model.bean.FridentListBean;
import com.meiliangzi.app.model.bean.GroupUserlistBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.CreatGroupInpuName;
import com.meiliangzi.app.ui.dialog.DeleatDialog;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

public class NewBuildChatGroupActivity extends BaseActivity implements View.OnClickListener {
   /* @BindView(R.id.ellist_chatlist)
    ExpandableListView grouplist;*/
   @BindView(R.id.gradview_group)
   GridView gradview_group;
    @BindView(R.id.text_sure)
    TextView text_sure;
    @BindView(R.id.text_title)
    TextView text_title;
private String name;
    //private MyAdapter adapter;
    private BaseQuickAdapter<FridentListBean.DataBean> adapter;
    private List<DepartmentuserNumberBean.DataBean> NumberBean=new ArrayList<>();//部门的列表
    private LayoutInflater Inflater;
    private List<String>  firdendId=new ArrayList<String>() {
    };
    private List<Integer>  groupfirdendId=new ArrayList<Integer>() {
    };
    private CreatGroupInpuName myDialog;
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = getWindowManager().getDefaultDisplay().getWidth();

        onCreateView(R.layout.activity_new_build_chat_group);
        //grouplist.setIndicatorBounds(width-90, width-10);

        Inflater = LayoutInflater.from(this);

    }
    @Override
    protected void findWidgets() {
        groupId=getIntent().getStringExtra("groupId");
        text_sure.setOnClickListener(this);
        adapter=new BaseQuickAdapter<FridentListBean.DataBean>(this,R.layout.item_newchatgroupchlied_lists) {
            @Override
            public void convert(BaseViewHolder helper, final FridentListBean.DataBean item) {
                helper.setImageByUrl(R.id.ivImg_userheader,item.getUserHead(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);
                helper.setText(R.id.tv_username,item.getNickName());
                /*helper.setText(R.id.tv_navigation,"删除");
                helper.setOnClickListener(R.id.tv_navigation, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            }
                        });*/
                ((CheckBox)helper.getView(R.id.ckContent)).setChecked(false);
                final int id=item.getId();
                if(firdendId!=null&&firdendId.size()!=0){
                    for (int i=0;i<firdendId.size();i++){
                        if(firdendId.get(i).equals(String.valueOf(id))) {
                            ((CheckBox)helper.getView(R.id.ckContent)).setChecked(true);
                        }
                    }
                }
                helper.getView(R.id.ckContent).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isadd=false;
                        int position=0;
                        if(firdendId!=null&&firdendId.size()!=0){
                            for (int i=0;i<firdendId.size();i++){
                                if(firdendId.get(i).equals(String.valueOf(item.getId()))) {
                                    position=i;
                                    isadd=true;

                                }
                            }
                        }
                        if(isadd){
                            firdendId.remove(position);
                            text_sure.setText("确认"+"（"+firdendId.size()+"）");
                        }else {
                            if(groupfirdendId.contains(item.getId())){

                            }else {

                                firdendId.add(String.valueOf(item.getId()));
                                text_sure.setText("确认"+"（"+firdendId.size()+"）");
                            }


                        }

                    }
                });
                    }


        };
        gradview_group.setAdapter(adapter);

    }

    @Override
    protected void initComponent() {
        //TODO 获取部门列表
        //ProxyUtils.getHttpProxy().querydepartmentusernumber(this, Integer.valueOf(PreferManager.getUserId()));


    }

    @Override
    protected void onResume() {
        if(MyApplication.numberBean!=null){
            NumberBean=MyApplication.numberBean.getData();
            //adapter=new MyAdapter(this);
            //grouplist.setAdapter(adapter);
        }
        if(getIntent().getStringExtra("groupId")!=null){
            text_title.setText("请选择添加人员");
        }
        if(groupId==null){
            ProxyUtils.getHttpProxy().friendlist(this, Integer.valueOf(PreferManager.getUserId()));

        }else {
            //获取好友列表
            ProxyUtils.getHttpProxy().groupuserlist(this, Integer.valueOf(groupId));

        }

        super.onResume();
    }

    private void getgroupuserlist(GroupUserlistBean data) {
        groupfirdendId.clear();
        if(data!=null){
            for (int i=0;i<data.getData().getUserInfo().size();i++) {
                groupfirdendId.add(data.getData().getUserInfo().get(i).getId());
            }
        }


        ProxyUtils.getHttpProxy().friendlist(this, Integer.valueOf(PreferManager.getUserId()));
    }
    private void getmentusernumber(DepartmentuserNumberBean data){
        NumberBean.clear();
        NumberBean=data.getData();

        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_sure:
                //ToastUtils.show("请您先标记位置");
                if(groupId!=null){
                    ProxyUtils.getHttpProxy().personaljoingroup(NewBuildChatGroupActivity.this, Integer.valueOf(PreferManager.getUserId()),Integer.valueOf(groupId),firdendId.toString());


                }else {
                    myDialog = new CreatGroupInpuName(this);
                    myDialog.setYesOnclickListener("确定", new CreatGroupInpuName.onYesOnclickListener() {
                        @Override
                        public void onYesClick() {
                             name= myDialog.name.getText().toString();
                            if(null!=name&&!"".equals(name)){
                                ProxyUtils.getHttpProxy().groupaddinvitation(NewBuildChatGroupActivity.this, Integer.valueOf(PreferManager.getUserId()),firdendId.toString(),name);
                                myDialog.dismiss();
                            }else {
                                ToastUtils.show("请输入群名称");

                            }



                        }
                    });
                    myDialog.setNoOnclickListener("取消", new CreatGroupInpuName.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            // Toast.makeText(this,"点击了--取消--按钮",Toast.LENGTH_LONG).show();
                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();


                }

                break;
        }
    }
    private void getgroupaddinvitation(CreatGroupChatBean bean){
        ToastUtils.show("操作成功");
        RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(bean.getData()),name,null));
        RongIM.getInstance().startConversation(NewBuildChatGroupActivity.this, Conversation.ConversationType.GROUP,String.valueOf(bean.getData().getGroupId()),bean.getData().getGroupName());


        finish();

    }
    private void getpersonaljoingroup(AgreeFriendapplyBean bean){
        ToastUtils.show("操作成功");
       /* RongIM.getInstance().refreshGroupInfoCache(new Group(String.valueOf(bean.getData()),name,null));
        RongIM.getInstance().startConversation(NewBuildChatGroupActivity.this, Conversation.ConversationType.GROUP,String.valueOf(bean.getData().getGroupId()),bean.getData().getGroupName());
*/

        finish();

    }

    public class MyAdapter extends BaseExpandableListAdapter {
        private LayoutInflater mLayoutInflater;
        public MyAdapter(Context context) {
            this.context = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        Context context;

        //设置组视图的显示文字
        private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
        //子视图显示文字
        private String[][] generals = new String[][] {
                { "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
                { "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
                { "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" }

        };



        //自己定义一个获得textview的方法
        TextView getTextView() {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100);
            TextView textView = new TextView(context);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(36, 0, 0, 0);
            textView.setTextSize(15);
            textView.setTextColor(Color.BLACK);
            return textView;
        }


        @Override
        public int getGroupCount() {
            //return cityBean.size();
            return NumberBean.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return NumberBean.get(groupPosition).getDepartmentUser().size();
            // return 3;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return NumberBean.get(groupPosition);
            // return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return NumberBean.get(groupPosition).getDepartmentUser().get(childPosition);
            // return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            ViewHolderGroup holderGroup=null;
            if(convertView==null){
                holderGroup=new ViewHolderGroup();
                convertView = Inflater.inflate(R.layout.item_newchat_grounp_lists, parent, false);
                holderGroup.textView = (TextView) convertView.findViewById(R.id.tv_gtoupname);
                //holderGroup.checkBox= (CheckBox) convertView.findViewById(R.id.ckContent);
                convertView.setTag(holderGroup);
            }else {
                holderGroup=(ViewHolderGroup)convertView.getTag();
            }
            /*holderGroup.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });*/
            /*TextView textView = (TextView) convertView.findViewById(R.id.tv_mapname);*/
            holderGroup.textView.setText(((DepartmentuserNumberBean.DataBean)getGroup(groupPosition)).getDepartmentName()+"  "+"("+(((DepartmentuserNumberBean.DataBean) getGroup(groupPosition)).getDepartmentUser().size())+")");

            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolderChild holderChild=null;
            if(convertView==null){
                holderChild=new ViewHolderChild();
                convertView = Inflater.inflate(R.layout.item_newchatgroupchlied_lists, parent, false);
                holderChild.checkBox= (CheckBox) convertView.findViewById(R.id.ckContent);

                holderChild.textView=(TextView) convertView.findViewById(R.id.tv_username);
                holderChild.imageView=(ImageView) convertView.findViewById(R.id.ivImg_userheader);
                convertView.setTag(holderChild);
            }else {
                holderChild= (ViewHolderChild) convertView.getTag();
            }
            if(((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getId()== Integer.valueOf(PreferManager.getUserId())){
                holderChild.checkBox.setVisibility(View.GONE);
            }else {
                holderChild.checkBox.setVisibility(View.VISIBLE);
            }
            holderChild.checkBox.setChecked(false);
            final int id=((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getId();
            if(firdendId!=null&&firdendId.size()!=0){
                for (int i=0;i<firdendId.size();i++){
                    if(firdendId.get(i).equals(String.valueOf(id))) {
                        holderChild.checkBox.setChecked(true);
                    }
                }
            }
holderChild.checkBox.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        boolean isadd=false;
        int position=0;
        if(firdendId!=null&&firdendId.size()!=0){
            for (int i=0;i<firdendId.size();i++){
                if(firdendId.get(i).equals(String.valueOf(id))) {
                    position=i;
                   isadd=true;

                }
            }
        }
        if(isadd){
            firdendId.remove(position);
        }else {
            firdendId.add(String.valueOf((((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getId())));

        }

    }
});
            holderChild.textView.setText(((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getNickName());

            setImageByUrl(holderChild.imageView,((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getUserHead(), R.mipmap.defaule, R.mipmap.defaule);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        private class ViewHolderGroup{
            private TextView textView;
            private CheckBox checkBox;

        }
        private class ViewHolderChild{
            CheckBox checkBox;
            private TextView textView;
            private ImageView imageView;
        }

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
    private void getaddfriend(AgreeFriendapplyBean bean){

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }
    private void getfriendlist(FridentListBean bean){

        adapter.setDatas(bean.getData());
        adapter.notifyDataSetChanged();

    }
}
