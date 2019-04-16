package com.meiliangzi.app.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.CheckProjectBean;
import com.meiliangzi.app.model.bean.CheckProjectDetBean;
import com.meiliangzi.app.model.bean.VoteBaseBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CheckFragment extends BaseFragment {
    private final String usercheck;
    private int id;
    private int pos;
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.dataEmpty)
    TextView dataEmpty;
    BaseVoteAdapter<CheckProjectDetBean.DataBean.CreateLogs> CreateAdapter;
    BaseVoteAdapter<CheckProjectDetBean.DataBean.ChangePos> ChangeAdapter;
    BaseVoteAdapter<CheckProjectDetBean.DataBean.ProjectChecks> ChecksAdapter;
    public CheckFragment(int pos,int id,String usercheck) {
        this.id=id;
        this.pos=pos;
        this.usercheck=usercheck;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = createView(inflater.inflate(R.layout.fragment_check, container, false));
        return view;
    }


    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        if(pos==0){
            //TODO 创建记录
            CreateAdapter=new BaseVoteAdapter<CheckProjectDetBean.DataBean.CreateLogs>(getContext(),R.layout.item_check_create_record) {
                @Override
                public void convert(BaseViewHolder helper, CheckProjectDetBean.DataBean.CreateLogs item) {
                    int pos=getPosition();
                    helper.setText(R.id.text_,"第"+(getmDatas().size()-pos)+"次提交");
                    helper.setText(R.id.text_pos,""+(getmDatas().size()-pos));
                    helper.setText(R.id.text_receive_at,item.getReceive_at());
                    if(0==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"审核中");
                    }else if(1==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"通过");
                    }
                    else if(2==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"不通过");
                    }

                    helper.setText(R.id.text_audit_opinion,item.getAudit_opinion());
                    helper.setText(R.id.text_userchecke,usercheck);

                }
            };
            listView.setAdapter(CreateAdapter);
        }else if(pos==1){
            //TODO 成果书记录
            ChecksAdapter=new BaseVoteAdapter<CheckProjectDetBean.DataBean.ProjectChecks>(getContext(),R.layout.item_check_check_record) {
                @Override
                public void convert(BaseViewHolder helper, CheckProjectDetBean.DataBean.ProjectChecks item) {
                    int pos=getPosition();
                    helper.setText(R.id.text_,"第"+(getmDatas().size()-pos)+"次提交");
                    helper.setText(R.id.text_pos,""+(getmDatas().size()-pos));
                    helper.setText(R.id.text_completion_desc,item.getCompletion_desc());
                    helper.setText(R.id.text_problem,item.getProblem());
                    helper.setText(R.id.text_solution,item.getSolution());
                    helper.setText(R.id.text_proposal,item.getProposal());
                    helper.setText(R.id.text_submit_time,item.getSubmit_time());

                    if(0==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"待提交");
                    }else if(1==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"审核中");
                    }
                    else if(2==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"通过");
                    }else if(3==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"不通过");
                    }

                    helper.setText(R.id.text_audit_opinion,item.getAudit_opinion());
                }
            };
            listView.setAdapter(ChecksAdapter);
        }else if(pos==2){
            //TODO 变更记录
            ChangeAdapter=new BaseVoteAdapter<CheckProjectDetBean.DataBean.ChangePos>(getContext(),R.layout.item_check_change_record) {
                @Override
                public void convert(BaseViewHolder helper, CheckProjectDetBean.DataBean.ChangePos item) {
                    int pos=getPosition();
                    helper.setText(R.id.text_,"第"+(getmDatas().size()-pos)+"次提交");
                    helper.setText(R.id.text_pos,""+(getmDatas().size()-pos));
                    if(1==item.getType()){
                        helper.setText(R.id.text_change_type,"延时");
                    }else if(2==item.getType()){
                        helper.setText(R.id.text_change_type,"取消");
                    }

                    helper.setText(R.id.text_changestart_at,item.getChangestart_at());

                    helper.setText(R.id.text_changeend_at,item.getChangeend_at());
                    helper.setText(R.id.text_application_reasons,item.getApplication_reasons());
                    helper.setText(R.id.text_audit_time,item.getCreate_at());
                    if(1==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"审核中");
                    }else if(2==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"通过");
                    }
                    else if(3==item.getIs_adopt()){
                        helper.setText(R.id.text_is_adopt,"不通过");
                    }
                    helper.setText(R.id.text_audit_opinion,item.getAudit_opinion());



                }
            };
            listView.setAdapter(ChangeAdapter);
        }

    }

    @Override
    protected void initComponent() {

    }
    @Override
    public void onResume() {
        super.onResume();
       // getData("0");
        //TODO 取得子项目列表
        ProxyUtils.getHttpCheckProxy().det(this,id);
    }
    private void getdet(CheckProjectDetBean data){
        if(pos==0){
            if(data.getData().getCreateLogs()!=null&&data.getData().getCreateLogs().size()!=0){
                //TODO 创建记录
                CreateAdapter.setDatas(data.getData().getCreateLogs());
                dataEmpty.setVisibility(View.GONE);
            }else {
                dataEmpty.setVisibility(View.VISIBLE);
            }

        }else if(pos==1){
            if(data.getData().getProjectChecks()!=null&&data.getData().getProjectChecks().size()!=0){
                //TODO 成果书记录
                ChecksAdapter.setDatas(data.getData().getProjectChecks());
                dataEmpty.setVisibility(View.GONE);
            }else {
                dataEmpty.setVisibility(View.VISIBLE);
            }

        }else if(pos==2){
            if(data.getData().getChangePos()!=null&&data.getData().getChangePos().size()!=0){
                //TODO 变更记录
                ChangeAdapter.setDatas(data.getData().getChangePos());
                dataEmpty.setVisibility(View.GONE);
            }else {
                dataEmpty.setVisibility(View.VISIBLE);
            }

        }

    }

}
