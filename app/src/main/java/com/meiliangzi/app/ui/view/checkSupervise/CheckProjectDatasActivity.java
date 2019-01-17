package com.meiliangzi.app.ui.view.checkSupervise;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.CheckChildenProjectBean;
import com.meiliangzi.app.model.bean.CheckDepartmentsBean;
import com.meiliangzi.app.model.bean.CheckProjectBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.vote.VoteActivity;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.XListView;

import java.text.SimpleDateFormat;

import butterknife.BindView;

public class CheckProjectDatasActivity extends BaseActivity {
    /*@BindView(R.id.text_screen)
    TextView text_screen;*/
    @BindView(R.id.text_check_projecttitle)
    TextView text_check_projecttitle;
    @BindView(R.id.text_presion_cherge)
    TextView text_presion_cherge;
    @BindView(R.id.text_project_type)
    TextView text_project_type;
    @BindView(R.id.text_check_type)
    TextView text_check_type;
    @BindView(R.id.text_execute_time)
    TextView text_execute_time;
    @BindView(R.id.text_project_describe)
    TextView text_project_describe;
    @BindView(R.id.text_morerecord)
    TextView morerecord;
    @BindView(R.id.listView)
    MyGridView listView;
    @BindView(R.id.image_project_stats)
    ImageView image_project_stats;
    @BindView(R.id.text_project_auditing)
    TextView text_project_auditing;
    BaseVoteAdapter<CheckChildenProjectBean.DataBean> tiemAdapter;
    private int id;
    private int projecttype;

    private String startYear="2019";
    private int width;
    private String usercheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_check_project_datas);
    }

    @Override
    protected void findWidgets() {
        final WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
         width = wm.getDefaultDisplay().getWidth();
        width=width*3;
        width=width/4;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        startYear=sdf.format(new java.util.Date());
        id=getIntent().getIntExtra("id",111111);
        projecttype=getIntent().getIntExtra("projecttype",11);
        Drawable drawable1 = getResources().getDrawable(R.mipmap.morerecord);
        drawable1.setBounds(0, 0, 80, 80);
        morerecord.setCompoundDrawables(drawable1,null,null,null);
       // listView.setPullLoadEnable(false);
        //listView.setPullRefreshEnable(false);
        morerecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CheckProjectDatasActivity.this,ProjectDetalisRecordActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("usercheck",usercheck);
                startActivity(intent);
            }
        });
        tiemAdapter=new BaseVoteAdapter<CheckChildenProjectBean.DataBean>(CheckProjectDatasActivity.this, R.layout.item_check_chilproject_list) {
            @Override
            public void convert(BaseViewHolder helper, CheckChildenProjectBean.DataBean item) {
                helper.setText(R.id.text_depart,item.getName());
                //helper.setText(R.id.text_check_projecttitle,item.getName());
                    helper.getView(R.id.text_depart).post(new Runnable() {
                        @Override
                        public void run() {
                            Layout l = text_check_projecttitle.getLayout();
                            if (l != null) {
                                int lines = l.getLineCount();
                                if (lines > 0) {
                                    if (l.getEllipsisCount(lines - 1) > 0) {
                                        //TODO 截取宽度
                                        ViewGroup.LayoutParams ls=    text_check_projecttitle.getLayoutParams();
                                        ls.width=width;
                                        text_check_projecttitle.setLayoutParams(ls);
                                    }
                                }
                            } else {
                                ViewGroup.LayoutParams ls=    text_check_projecttitle.getLayoutParams();
                                ls.width= ViewGroup.LayoutParams.WRAP_CONTENT;
                                text_check_projecttitle.setLayoutParams(ls);
                            }
                        }
                    });

                helper.setText(R.id.text_name,item.getUserName());
                helper.setText(R.id.text_time,item.getStart_at()+"~"+item.getEnd_at());
                if(1==item.getSchedule()){
                    helper.setText(R.id.text_status,"处理中");
                }else if(2==item.getSchedule()){
                    helper.setText(R.id.text_status,"验收中");
                }else {
                    helper.setText(R.id.text_status,"已完成");
                }
            if(1==projecttype){
                if(0==item.getProject_status_red()){
                    helper.showOrGoneView(R.id.image_checkstate2,false);
                }else {
                    helper.showOrGoneView(R.id.image_checkstate2,true);
                    ((ImageView)helper.getView(R.id.image_checkstate2)).setBackground(getResources().getDrawable(R.mipmap.red));

                }
                //TODO 预算项目
                if(0==item.getProject_status()){

                    //TODO 白灯
                    //helper.setb(R.id.image_checkstate,null);
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.white));
                }else if(1==item.getProject_status()){
                    //TODO 绿灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.greaden));

                }else if(2==item.getProject_status()){
                    //TODO 黄灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.yellow));

                }else if(3==item.getProject_status()){
                    //TODO 红灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.red));

                }else if (4==item.getProject_status()){
                    //TODO 黑灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.black));

                }
            }else {
                if(0==item.getProject_status_red()){
                    helper.showOrGoneView(R.id.image_checkstate2,false);
                }else {
                    helper.showOrGoneView(R.id.image_checkstate2,true);
                    ((ImageView)helper.getView(R.id.image_checkstate2)).setBackground(getResources().getDrawable(R.mipmap.purred));

                }
                //TODO 临时项目
                if(0==item.getProject_status()){
                    //TODO 白灯
                    //helper.setb(R.id.image_checkstate,null);
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.purwhite));
                }else if(1==item.getProject_status()){
                    //TODO 绿灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.purgreed));

                }else if(2==item.getProject_status()){
                    //TODO 黄灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.puryellow));

                }else if(3==item.getProject_status()){
                    //TODO 红灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.purred));

                }else if (4==item.getProject_status()){
                    //TODO 黑灯
                    ((ImageView)helper.getView(R.id.image_checkstate1)).setBackground(getResources().getDrawable(R.mipmap.purblack));

                }
            }

            }
        };
        listView.setAdapter(tiemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(CheckProjectDatasActivity.this,CheckTaskDetailActivity.class);
                intent.putExtra("taskid",tiemAdapter.getItem(position).getId()+"");
                intent.putExtra("projecttype",projecttype);
                intent.putExtra("statusred",tiemAdapter.getItem(position).getProject_status_red());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
        //TODO 取得所有项目列表
        ProxyUtils.getHttpCheckProxy().projects(this,startYear,"-1",-1,-1,-1,-1);
        //ProxyUtils.getHttpCheckProxy().projects(this);
        //TODO 取得子项目列表
        ProxyUtils.getHttpCheckProxy().subpros(this,id);

    }
    private void getsubpros(CheckChildenProjectBean data){
    if(data!=null){
            tiemAdapter.setDatas(data.getData());
        //tiemAdapter.notifyDataSetChanged();
        }

    }
    private void getprojects(CheckProjectBean data){
        if(data!=null&&data.getData()!=null){
            for (int i=0;i<data.getData().size();i++){
                if(id==data.getData().get(i).getId()){
                    text_check_projecttitle.setText(data.getData().get(i).getProName());
                    //projecttype=data.getData().get(i).getProject_type();
                    text_presion_cherge.setText(data.getData().get(i).getExamine_userName());

                    if(1==data.getData().get(i).getProject_type()){
                        text_project_type.setText("预算项目");
                    }else {
                        text_project_type.setText("临时项目");
                    }
                    if(1==data.getData().get(i).getNature()){
                        text_check_type.setText("生产型");
                    }else {
                        text_check_type.setText("非生产型");
                    }
                    text_execute_time.setText("执行时间 ："+data.getData().get(i).getStart_at()+"~"+data.getData().get(i).getEnd_at());
                    text_project_describe.setText(data.getData().get(i).getProDesc());
                    usercheck=data.getData().get(i).getExamine_userName();
                    text_project_auditing.setText("审核人 ："+data.getData().get(i).getExamine_userName());
                    if(1==projecttype){
                        if(0==data.getData().get(i).getProject_status()){
                            //TODO 白灯

                            //TODO 白灯
                            //helper.setb(R.id.image_checkstate,null);
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.white));
                        }else if(1==data.getData().get(i).getProject_status()){
                            //TODO 绿灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.greaden));

                        }else if(2==data.getData().get(i).getProject_status()){
                            //TODO 黄灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.yellow));

                        }else if(3==data.getData().get(i).getProject_status()){
                            //TODO 红灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.red));

                        }else if (4==data.getData().get(i).getProject_status()){
                            //TODO 黑灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.black));

                        }
                    }else {
                        if(0==data.getData().get(i).getProject_status()){
                            //TODO 白灯

                            //TODO 白灯
                            //helper.setb(R.id.image_checkstate,null);
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purwhite));
                        }else if(1==data.getData().get(i).getProject_status()){
                            //TODO 绿灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purgreed));

                        }else if(2==data.getData().get(i).getProject_status()){
                            //TODO 黄灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.puryellow));

                        }else if(3==data.getData().get(i).getProject_status()){
                            //TODO 红灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purred));

                        }else if (4==data.getData().get(i).getProject_status()){
                            //TODO 黑灯
                            image_project_stats.setBackground(getResources().getDrawable(R.mipmap.purblack));

                        }
                    }
                    ViewGroup.LayoutParams ls=    text_check_projecttitle.getLayoutParams();


                        text_check_projecttitle.post(new Runnable() {
                            @Override
                            public void run() {
                                Layout l = text_check_projecttitle.getLayout();
                                if (l != null) {
                                    int lines = l.getLineCount();
                                    if (lines > 0) {
                                        if (l.getEllipsisCount(lines - 1) > 0) {
                                            //TODO 截取宽度
                                            ViewGroup.LayoutParams ls=    text_check_projecttitle.getLayoutParams();
                                            ls.width=width;
                                            text_check_projecttitle.setLayoutParams(ls);
                                        }
                                    }
                                } else {
                                }
                            }
                        });
                    }


                }

            }
        }
    }

