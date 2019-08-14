package com.meiliangzi.app.ui.view.sendcar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.Partment;
import com.meiliangzi.app.model.bean.SendCarUserBean;
import com.meiliangzi.app.model.bean.departmentuserlistBean;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;

public class SendCarAddUserActivity extends BaseActivity {
    @BindView(R.id.ellist_sendcar)
    ExpandableListView ellist_sendcar;
    private SendCarUserBean NumberBean=new SendCarUserBean();//部门的列表
    private MyAdapter adapter;
    private  int departmentId;
    private  int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        onCreateView(R.layout.activity_send_car_add_user);
        ellist_sendcar.setIndicatorBounds(width-90, width-10);
    }

    @Override
    protected void findWidgets() {
        adapter=new MyAdapter(this);
        ellist_sendcar.setAdapter(adapter);
        ellist_sendcar.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, final int i, long l) {
                departmentId=((SendCarUserBean.PartmentBean)adapter.getGroup(i)).getId();
                position=i;
                ProxyUtils.getHttpProxy().departmentuserlist(SendCarAddUserActivity.this,departmentId);

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        ProxyUtils.getHttpProxy().querydepartment(this,NewPreferManager.getoldUseId());

        super.onResume();
    }

    public void getData(Partment partment) {
        for(int i=0;i<partment.getData().size();i++){
            SendCarUserBean.PartmentBean partmentBean=new SendCarUserBean.PartmentBean();
            partmentBean.setId(partment.getData().get(i).getId());
            partmentBean.setName(partment.getData().get(i).getName());
            NumberBean.getData().add(partmentBean);
        }

        adapter.notifyDataSetChanged();
    }
    public void getdepartmentuserlist(departmentuserlistBean partment) {
        if(NumberBean.getData().get(position).getDepartmentuserlist()!=null){

        }else {
            for(int i=0;i<partment.getData().size();i++){
                SendCarUserBean.PartmentBean.DepartmentUserlist PartmentBean=new SendCarUserBean.PartmentBean.DepartmentUserlist();
                PartmentBean.setUserPhone(partment.getData().get(i).getUserPhone());
                PartmentBean.setNickName(partment.getData().get(i).getNickName());
                PartmentBean.setUserPhone(partment.getData().get(i).getUserPhone());
                NumberBean.getData().get(position).getDepartmentuserlist().add(PartmentBean);
            }
        }


        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initComponent() {

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
            if(NumberBean.getData()!=null){
                return NumberBean.getData().size();
            }else {
                return 0;
            }

        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if(NumberBean.getData().get(groupPosition).getDepartmentuserlist()!=null){
                return NumberBean.getData().get(groupPosition).getDepartmentuserlist().size();
            }else {
                return 0;
            }

            // return 3;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return NumberBean.getData().get(groupPosition);
            // return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return NumberBean.getData().get(groupPosition).getDepartmentuserlist().get(childPosition);
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

            MyAdapter.ViewHolderGroup holderGroup=null;
            if(convertView==null){
                holderGroup=new MyAdapter.ViewHolderGroup();
                convertView = mLayoutInflater.inflate(R.layout.item_addfridentgrounp_lists, parent, false);
                holderGroup.textView = (TextView) convertView.findViewById(R.id.tv_gtoupname);
                convertView.setTag(holderGroup);
            }else {
                holderGroup=(MyAdapter.ViewHolderGroup)convertView.getTag();
            }
            /*TextView textView = (TextView) convertView.findViewById(R.id.tv_mapname);*/
            holderGroup.textView.setText(((SendCarUserBean.PartmentBean)getGroup(groupPosition)).getName());

            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            MyAdapter.ViewHolderChild holderChild=null;
            if(convertView==null){
                holderChild=new MyAdapter.ViewHolderChild();
                convertView = mLayoutInflater.inflate(R.layout.item_sendcaruser_lists, parent, false);
                holderChild.tv_nickName=(TextView) convertView.findViewById(R.id.tv_nickName);
                holderChild.tv_userphone=(TextView) convertView.findViewById(R.id.tv_userphone);
                convertView.setTag(holderChild);
            }else {
                holderChild= (MyAdapter.ViewHolderChild) convertView.getTag();
            }
            holderChild.tv_userphone.setText(((SendCarUserBean.PartmentBean.DepartmentUserlist)getChild(groupPosition, childPosition)).getUserPhone());

            holderChild.tv_nickName.setText(((SendCarUserBean.PartmentBean.DepartmentUserlist)getChild(groupPosition, childPosition)).getNickName());
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
            private TextView tv_nickName;
            private TextView tv_userphone;
        }

    }
}
