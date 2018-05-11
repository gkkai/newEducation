package com.meiliangzi.app.ui.view.checkSupervise;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.CheckProjectDetBean;
import com.meiliangzi.app.model.bean.CheckProjectTaskDetBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

import static android.R.attr.width;

public class CheckTaskDetailActivity extends BaseActivity implements View.OnClickListener {
    private String taskid;
    @BindView(R.id.listView)
    MyGridView listView;
    @BindView(R.id.text_check_projecttitle)
    TextView text_check_projecttitle;
    @BindView(R.id.image_project_stats)
    ImageView image_project_stats;
    @BindView(R.id.image_project_stats2)
    ImageView image_project_stats2;
    @BindView(R.id.text_username)
    TextView text_username;
    @BindView(R.id.text_assisting_departmentname)
    TextView text_assisting_departmentname;
    @BindView(R.id.text_start_at)
    TextView text_start_at;
    @BindView(R.id.text_standard)
    TextView text_standard;
    @BindView(R.id.text_measures)
    TextView text_measures;
    @BindView(R.id.text_examines_userName)
    TextView text_examines_userName;
    @BindView(R.id.text_checkcord)
    TextView text_checkcord;
    @BindView(R.id.text_changecord)
    TextView text_changecord;
    private CheckProjectTaskDetBean data;
    BaseVoteAdapter<CheckProjectTaskDetBean.DataBean.ChangePos> ChangeAdapter;
    BaseVoteAdapter<CheckProjectTaskDetBean.DataBean.TaskCheckLogs> ChecksAdapter;
    private int projecttype;
    private int width;
    private int statusred;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_check_task_detail);
    }

    @Override
    protected void findWidgets() {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        width=width*3;
        width=width/4;
        taskid=getIntent().getStringExtra("taskid");
        text_changecord.setOnClickListener(this);
        text_checkcord.setOnClickListener(this);
        projecttype=getIntent().getIntExtra("projecttype",000);
        statusred=getIntent().getIntExtra("statusred",000);

        ChangeAdapter=new BaseVoteAdapter<CheckProjectTaskDetBean.DataBean.ChangePos>(this,R.layout.item_check_change_record) {
            @Override
            public void convert(BaseViewHolder helper, CheckProjectTaskDetBean.DataBean.ChangePos item) {
                int pos =getPosition();
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
        ChecksAdapter=new BaseVoteAdapter<CheckProjectTaskDetBean.DataBean.TaskCheckLogs>(this,R.layout.item_check_check_record) {
            @Override
            public void convert(BaseViewHolder helper, CheckProjectTaskDetBean.DataBean.TaskCheckLogs item) {

                int pos =getPosition();
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
    }

    @Override
    protected void initComponent() {
        //TODO 取得子项目列表
        ProxyUtils.GetHttpCheckProxy().det(this,taskid);
    }
    private void getdet(CheckProjectTaskDetBean data){
        if(data!=null){
            this.data=data;
            text_check_projecttitle.setText(data.getData().getProjectPo().getName());
            text_username.setText(data.getData().getProjectPo().getUserName());
            if(1==projecttype){
                if(0==statusred){
                    //helper.showOrGoneView(R.id.image_checkstate2,false);
                    image_project_stats2.setVisibility(View.GONE);
                }else {
                    image_project_stats2.setVisibility(View.VISIBLE);
                    image_project_stats2.setBackground(getResources().getDrawable(R.mipmap.red));

                }
                if(0==data.getData().getProjectPo().getProject_status()){
                    //TODO 白灯

                    //TODO 白灯
                    //helper.setb(R.id.image_checkstate,null);
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.white));
                }else if(1==data.getData().getProjectPo().getProject_status()){
                    //TODO 绿灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.greaden));

                }else if(2==data.getData().getProjectPo().getProject_status()){
                    //TODO 黄灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.yellow));

                }else if(3==data.getData().getProjectPo().getProject_status()){
                    //TODO 红灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.red));

                }else if (4==data.getData().getProjectPo().getProject_status()){
                    //TODO 黑灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.black));

                }
            }else {
                if(0==statusred){
                    //helper.showOrGoneView(R.id.image_checkstate2,false);
                    image_project_stats2.setVisibility(View.GONE);
                }else {
                    image_project_stats2.setVisibility(View.VISIBLE);
                    image_project_stats2.setBackground(getResources().getDrawable(R.mipmap.purred));

                }
                if(0==data.getData().getProjectPo().getProject_status()){
                    //TODO 白灯

                    //TODO 白灯
                    //helper.setb(R.id.image_checkstate,null);
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purwhite));
                }else if(1==data.getData().getProjectPo().getProject_status()){
                    //TODO 绿灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purgreed));

                }else if(2==data.getData().getProjectPo().getProject_status()){
                    //TODO 黄灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.puryellow));

                }else if(3==data.getData().getProjectPo().getProject_status()){
                    //TODO 红灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purred));

                }else if (4==data.getData().getProjectPo().getProject_status()){
                    //TODO 黑灯
                    image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purblack));

                }
            }

            text_start_at.setText(data.getData().getProjectPo().getStart_at()+"~"+data.getData().getProjectPo().getEnd_at());
            text_assisting_departmentname.setText(data.getData().getProjectPo().getAssisting_departmentname());
            text_standard.setText(data.getData().getProjectPo().getStandard());
            text_measures.setText(data.getData().getProjectPo().getMeasures());
            text_examines_userName.setText(data.getData().getProjectPo().getExamines_userName());
            final ViewGroup.LayoutParams ls=    text_check_projecttitle.getLayoutParams();
            if(ls.width>width){

                ls.width=width;
                text_check_projecttitle.setLayoutParams(ls);
            }
                /*text_check_projecttitle.post(new Runnable() {
                    @Override
                    public void run() {
                        Layout l = text_check_projecttitle.getLayout();
                        if (l != null) {
                            int lines = l.getLineCount();
                            if (lines > 0) {
                                if (l.getEllipsisCount(lines - 1) > 0) {
                                    //TODO 截取宽度

                                    ls.width=width;
                                    text_check_projecttitle.setLayoutParams(ls);
                                }
                            }
                        } else {
                        }
                    }
                });*/


        }
        listView.setAdapter(ChecksAdapter);
        ChecksAdapter.setDatas(data.getData().getProjectChecks());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.text_checkcord:
                listView.setAdapter(ChecksAdapter);
                ChecksAdapter.setDatas(data.getData().getProjectChecks());
                text_checkcord.setTextColor(getResources().getColor(R.color.ac_filter_string_color));
                text_changecord.setTextColor(getResources().getColor(R.color.colorText));
                break;
            case R.id.text_changecord:
                listView.setAdapter(ChangeAdapter);
                ChangeAdapter.setDatas(data.getData().getChangePos());
                text_changecord.setTextColor(getResources().getColor(R.color.ac_filter_string_color));
                text_checkcord.setTextColor(getResources().getColor(R.color.colorText));

                break;
        }

    }
}
