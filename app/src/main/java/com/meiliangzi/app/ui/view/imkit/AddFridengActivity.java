package com.meiliangzi.app.ui.view.imkit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.CountyListbean;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.model.bean.SearchUserBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.AddFridentDialog;
import com.meiliangzi.app.ui.dialog.ClearChatDialog;
import com.meiliangzi.app.ui.view.MapNewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.InformationNotificationMessage;

public class AddFridengActivity extends BaseActivity {
    /*@BindView(R.id.ellist_chatlist)
    ExpandableListView grouplist;*/
    @BindView(R.id.gradview_group)
    GridView gradview_group;
    @BindView(R.id.edit_search)
    EditText edit_search;
    //private MyAdapter adapter;
    private BaseTrainAdapter<SearchUserBean.DataBean> adapter;
    private List<DepartmentuserNumberBean.DataBean> NumberBean=new ArrayList<>();//部门的列表
    private LayoutInflater Inflater;
    private  int addid;
    InputMethodManager imm;
    private AddFridentDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_add_frideng);
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

       // grouplist.setIndicatorBounds(width-90, width-10);

        Inflater = LayoutInflater.from(this);
    }

    @Override
    protected void findWidgets() {
        adapter=new BaseTrainAdapter<SearchUserBean.DataBean>(this,gradview_group, R.layout.item_addfrientchlied_lists) {
            @Override
            public void convert(final BaseViewHolder helper, final SearchUserBean.DataBean item) {
                if(item.getIs_friends()==2){
                    helper.setImageByUrl(R.id.ivImg_userheader,item.getUser_head(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);
                    helper.setText(R.id.tv_username,item.getNickName());
                    helper.getView(R.id.tv_navigation).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_navigation).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //addid= ((DepartmentuserNumberBean.DataBean.DepartmentUser) getChild(groupPosition, childPosition)).getId();
                            //TODO 添加好友
                            ProxyUtils.getHttpProxy().addfriend(AddFridengActivity.this, Integer.valueOf(PreferManager.getUserId()), item.getId());
                           /* helper.showOrGoneView(R.id.tv_frident,false);
                            helper.showOrGoneView(R.id.tv_wait,true);
                            helper.showOrGoneView(R.id.tv_navigation,false);*/
                        }

                    });
                    helper.showOrGoneView(R.id.tv_frident,false);
                    helper.showOrGoneView(R.id.tv_wait,false);
                }else if(item.getIs_friends()==1){
                    helper.setImageByUrl(R.id.ivImg_userheader,item.getUser_head(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);
                    helper.setText(R.id.tv_username,item.getNickName());
                    helper.showOrGoneView(R.id.tv_frident,true);
                    helper.showOrGoneView(R.id.tv_wait,false);
                    helper.showOrGoneView(R.id.tv_navigation,false);
                }else if(item.getIs_friends()==0){
                    helper.setImageByUrl(R.id.ivImg_userheader,item.getUser_head(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);
                    helper.setText(R.id.tv_username,item.getNickName());
                    helper.showOrGoneView(R.id.tv_frident,false);
                    helper.showOrGoneView(R.id.tv_wait,true);
                    helper.showOrGoneView(R.id.tv_navigation,false);

                }



            }
        };
        gradview_group.setAdapter(adapter);

        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
//do something;
                    if("".equals(edit_search.getText().toString())){
                        ToastUtils.show("您输入的内容为空");
                    }else {
                        String result= edit_search.getText().toString();
                        //TODO 跳转到搜索结果界面
                       // ProxyUtils.getHttpProxy().querymaps(MapListsActivity.this,edit_adddress.getText().toString());
                       /* Intent intent=new Intent(AddFridengActivity.this,SearchResultActivity.class);
                        intent.putExtra("name",edit_search.getText().toString());
                        startActivity(intent);
                        finish();*/
                        ProxyUtils.getHttpProxy().searchuser(AddFridengActivity.this,edit_search.getText().toString(), Integer.valueOf(PreferManager.getUserId()));

                    }

                    return true;
                }
                return false;
            }

        });
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

        super.onResume();
    }
   /* private void getaddfriend(AgreeFriendapplyBean bean){


    }*/
    private void getmentusernumber(DepartmentuserNumberBean data){
        NumberBean.clear();
        NumberBean=data.getData();

        //adapter.notifyDataSetChanged();
    }
    private void getsearchuser(SearchUserBean bean){
        adapter.setDatas(bean.getData());
        adapter.notifyDataSetChanged();
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
        public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {

            ViewHolderGroup holderGroup=null;
            if(convertView==null){
                holderGroup=new ViewHolderGroup();
                convertView = Inflater.inflate(R.layout.item_addfridentgrounp_lists, parent, false);
                holderGroup.textView = (TextView) convertView.findViewById(R.id.tv_gtoupname);
                convertView.setTag(holderGroup);
            }else {
                holderGroup=(ViewHolderGroup)convertView.getTag();
            }
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
                convertView = Inflater.inflate(R.layout.item_addfrientchlied_lists, parent, false);
                holderChild.tv_navigation=(TextView) convertView.findViewById(R.id.tv_navigation);
                holderChild.textView=(TextView) convertView.findViewById(R.id.tv_username);
                holderChild.imageView=(ImageView) convertView.findViewById(R.id.ivImg_userheader);
                convertView.setTag(holderChild);
            }else {
                holderChild= (ViewHolderChild) convertView.getTag();
            }
            if(((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getIsFriends()==1||((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getId()== Integer.valueOf(PreferManager.getUserId())){
                holderChild.tv_navigation.setVisibility(View.GONE);
            }else {
                holderChild.tv_navigation.setVisibility(View.VISIBLE);
            }
            holderChild.textView.setText(((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getNickName());
            holderChild.tv_navigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 addid= ((DepartmentuserNumberBean.DataBean.DepartmentUser) getChild(groupPosition, childPosition)).getId();
                    //TODO 添加好友
                    ProxyUtils.getHttpProxy().addfriend(AddFridengActivity.this, Integer.valueOf(PreferManager.getUserId()), ((DepartmentuserNumberBean.DataBean.DepartmentUser) getChild(groupPosition, childPosition)).getId());

                }

            });
            setImageByUrl(holderChild.imageView,((DepartmentuserNumberBean.DataBean.DepartmentUser)getChild(groupPosition, childPosition)).getUserHead(), R.mipmap.fridentmessage, R.mipmap.fridentmessage);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        private class ViewHolderGroup{
            private TextView textView;

        }
        private class ViewHolderChild{
            private TextView textView;
            private TextView tv_navigation;
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
        /*dialog=new AddFridentDialog(this);
        dialog.setMessage("添加成功，等待对方通过");
        dialog.setYesOnclickListener("确认", new AddFridentDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });*/
        ProxyUtils.getHttpProxy().searchuser(AddFridengActivity.this,edit_search.getText().toString(), Integer.valueOf(PreferManager.getUserId()));

        //ToastUtils.show("添加成功，等待对方通过");

    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
      /*  dialog=new AddFridentDialog(this);
        dialog.setMessage(errorMessage);
        dialog.setYesOnclickListener("确认", new AddFridentDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });*/
      ToastUtils.show(errorMessage);

    }
}

