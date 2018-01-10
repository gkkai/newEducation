package com.meiliangzi.app.ui.view.imkit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.model.bean.FridentListBean;
import com.meiliangzi.app.model.bean.SearchGroupBean;
import com.meiliangzi.app.model.bean.SearchUserBean;
import com.meiliangzi.app.model.bean.UserAddGroupBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class SearchResultActivity extends BaseActivity {
    @BindView(R.id.gradview_group)
    GridView gradview_group;
    @BindView(R.id.edit_search)
    EditText edit_search;
    private BaseTrainAdapter<SearchUserBean.DataBean> adapter;
    private BaseTrainAdapter<SearchGroupBean.DataBean> groupadapter;
    private int Is_friends;
    private String name;
    private String type;
    InputMethodManager imm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_search_result);
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

    }

    @Override
    protected void findWidgets() {
        type=getIntent().getStringExtra("type");
        if("GROUP".equals(type)){
            edit_search.setVisibility(View.VISIBLE);
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
                             ProxyUtils.getHttpProxy().searchgroup(SearchResultActivity.this,edit_search.getText().toString(),Integer.valueOf(PreferManager.getUserId()));
                            //finish();
                        }

                        return true;
                    }
                    return false;
                }

            });
            groupadapter=new BaseTrainAdapter<SearchGroupBean.DataBean>(this,gradview_group, R.layout.item_addfrientchlied_lists) {
                @Override
                public void convert(BaseViewHolder helper, final SearchGroupBean.DataBean item) {
                    if(item.getIs_group()==2){
                        helper.setImageByUrl(R.id.ivImg_userheader,item.getImage(),R.mipmap.groupheader,R.mipmap.groupheader);
                        helper.setText(R.id.tv_username,item.getGroupName());
                        helper.getView(R.id.tv_navigation).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_navigation).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //addid= ((DepartmentuserNumberBean.DataBean.DepartmentUser) getChild(groupPosition, childPosition)).getId();
                                //TODO 申请加群
                                 ProxyUtils.getHttpProxy().useraddgroup(SearchResultActivity.this, item.getId() ,Integer.valueOf(PreferManager.getUserId()));
                            }

                        });
                        helper.showOrGoneView(R.id.tv_frident,false);
                        helper.showOrGoneView(R.id.tv_wait,false);
                    }else if(item.getIs_group()==1){
                        helper.setImageByUrl(R.id.ivImg_userheader,item.getImage(),R.mipmap.groupheader,R.mipmap.groupheader);
                        helper.setText(R.id.tv_username,item.getGroupName());
                        helper.showOrGoneView(R.id.tv_frident,true);
                        helper.showOrGoneView(R.id.tv_wait,false);
                        helper.showOrGoneView(R.id.tv_navigation,false);
                    }else if(item.getIs_group()==0){
                        helper.setImageByUrl(R.id.ivImg_userheader,item.getImage(),R.mipmap.groupheader,R.mipmap.groupheader);
                        helper.setText(R.id.tv_username,item.getGroupName());
                        helper.showOrGoneView(R.id.tv_frident,false);
                        helper.showOrGoneView(R.id.tv_wait,true);
                        helper.showOrGoneView(R.id.tv_navigation,false);

                    }



                }
            };
            gradview_group.setAdapter(groupadapter);
//TODO 群组列表
            ProxyUtils.getHttpProxy().grouplists(SearchResultActivity.this,Integer.valueOf(PreferManager.getUserId()));

        }else {
            name=getIntent().getStringExtra("name");
            adapter=new BaseTrainAdapter<SearchUserBean.DataBean>(this,gradview_group, R.layout.item_addfrientchlied_lists) {
                @Override
                public void convert(BaseViewHolder helper, final SearchUserBean.DataBean item) {
                    if(item.getIs_friends()==2){
                        helper.setImageByUrl(R.id.ivImg_userheader,item.getUser_head(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);
                        helper.setText(R.id.tv_username,item.getNickName());
                        helper.getView(R.id.tv_navigation).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_navigation).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //addid= ((DepartmentuserNumberBean.DataBean.DepartmentUser) getChild(groupPosition, childPosition)).getId();
                                //TODO 添加好友
                                ProxyUtils.getHttpProxy().addfriend(SearchResultActivity.this, Integer.valueOf(PreferManager.getUserId()), item.getId());
                            }

                        });
                    }else {
                        helper.setImageByUrl(R.id.ivImg_userheader,item.getUser_head(),R.mipmap.fridentmessage,R.mipmap.fridentmessage);
                        helper.setText(R.id.tv_username,item.getNickName());
                        helper.getView(R.id.tv_navigation).setVisibility(View.GONE);

                    }



                }
            };
            gradview_group.setAdapter(adapter);
            ProxyUtils.getHttpProxy().searchuser(this,name, Integer.valueOf(PreferManager.getUserId()));

        }


    }

    @Override
    protected void initComponent() {


    }
    private void getsearchuser(SearchUserBean bean){
        adapter.setDatas(bean.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        super.initListener();
        /*gradview_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("GROUP".equals(type)){
                    if(groupadapter.getItem(position).getIs_group()==2){

                    }else {
                        RongIM.getInstance().startConversation(SearchResultActivity.this, Conversation.ConversationType.GROUP,String.valueOf(groupadapter.getItem(position).getId()),groupadapter.getItem(position).getGroupName());

                    }
                }else {
                    if(adapter.getItem(position).getIs_friends()==2){

                    }else {
                        RongIM.getInstance().startConversation(SearchResultActivity.this, Conversation.ConversationType.PRIVATE,String.valueOf(adapter.getItem(position).getId()),adapter.getItem(position).getNickName());

                    }
                }

            }
        });*/
    }
    private void getaddfriend(AgreeFriendapplyBean bean){


    }
    //TODO 搜索结果展示
    private void getsearchgroup(SearchGroupBean bean){
        groupadapter.clear();
        groupadapter.setDatas(bean.getData());
        groupadapter.notifyDataSetChanged();

    }
    private void getuseraddgroup(UserAddGroupBean bean){
       /* ToastUtils.show("添加成功，等待管理员审核");
        finish();*/
       if(edit_search.getText().toString().equals("")){
           //TODO 群组列表
           ProxyUtils.getHttpProxy().grouplists(SearchResultActivity.this,Integer.valueOf(PreferManager.getUserId()));

       }else {
           //TODO 跳转到搜索结果界面
           ProxyUtils.getHttpProxy().searchgroup(SearchResultActivity.this,edit_search.getText().toString(),Integer.valueOf(PreferManager.getUserId()));

       }
         //finish();

    }
}
