package com.meiliangzi.app.ui.view.train;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.RecentClassInfobean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;

public class TrainClassDataelsActivity extends BaseActivity {
    @BindView(R.id.image_classinfo)
    ImageView  image_classinfo;
    @BindView(R.id.tx_classinfo_title)
    TextView tx_classinfo_title;
    @BindView(R.id.tx_classinfo_certificate)
    TextView tx_classinfo_certificate;
    @BindView(R.id.tx_classinfo_start_at)
    TextView tx_classinfo_start_at;
    @BindView(R.id.tx_classinfo_linkmanname)
    TextView tx_classinfo_linkmanname;
    @BindView(R.id.tx_classinfo_linkmanqq)
    TextView tx_classinfo_linkmanqq;
    @BindView(R.id.tx_classinfo_linkmanphone)
    TextView tx_classinfo_linkmanphone;
    @BindView(R.id.tv_classinfo_singup)
    TextView tv_classinfo_singup;
    @BindView(R.id.tv_classinfo_html)
    TextView tv_classinfo_html;
    @BindView(R.id.myGridViewType)
    MyGridView listView;
    @BindView(R.id.id_view)
    View id_view;
    private int trainingId;
    private int userId;
    @BindView(R.id.rel_class_study)
    RelativeLayout rel_class_study;
    BaseTrainAdapter<RecentClassInfobean.Databean.TeacherGroup> adapter;
    private int study_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_class_data);
        onCreateView(R.layout.activity_class_data);
    }

    @Override
    protected void findWidgets() {

        adapter = new BaseTrainAdapter<RecentClassInfobean.Databean.TeacherGroup>(this, listView, R.layout.train_classinfo_teacher) {
            @Override
            public void convert(BaseViewHolder helper, final RecentClassInfobean.Databean.TeacherGroup item) {
                helper.setImageByUrl(R.id.ivImg,item.getAvatar(),R.mipmap.defaule,R.mipmap.defaule);
                helper.setText(R.id.tv_teacher_name,item.getName());
                helper.setText(R.id.tv_tecaher_positions,item.getPositions());

            }
        };
        listView.setAdapter(adapter);
        tv_classinfo_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(study_type==1){
                    //TODO 提示已经报名
                    ToastUtils.show("您已经报名");

                }else {
                    //TODO 跳转报名界面
                    Intent intent =new Intent(TrainClassDataelsActivity.this,PersonalSingUpActivity.class);
                    intent.putExtra("type",trainingId);
                    startActivity(intent);
                    //finish();
                }


            }
        });
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void onResume() {
        if(getIntent()!=null){
            trainingId=getIntent().getIntExtra("trainingId",0);

        }
        userId= Integer.valueOf(PreferManager.getUserId());
        //TODO 开班详情
        ProxyUtils.getHttpProxy().recentclassinfo(this,trainingId,userId);
        super.onResume();
    }

    @Override
    protected void asyncRetrive() {
        if(getIntent()!=null){
            trainingId=getIntent().getIntExtra("trainingId",0);

        }
        userId= Integer.valueOf(PreferManager.getUserId());
        //TODO 开班详情
        ProxyUtils.getHttpProxy().recentclassinfo(this,trainingId,userId);
        //super.asyncRetrive();
    }
    protected void getrecentclassinfo(final RecentClassInfobean bean){
        if(bean.getData().getTeacher_group()!=null&&bean.getData().getTeacher_group().size()!=0){
            adapter.setDatas(bean.getData().getTeacher_group());
        }else {
            rel_class_study.setVisibility(View.GONE);
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) id_view.getLayoutParams();
            lp.addRule(RelativeLayout.BELOW,R.id.view);
            id_view.setLayoutParams(lp);

        }
        RecentClassInfobean.Databean.ClassInfo  info= bean.getData().getInfo();


        setImageByUrl(image_classinfo,info.getImage(),R.mipmap.defaule,R.mipmap.defaule);
        tx_classinfo_title.setText("班级名称："+info.getTitle());
        tx_classinfo_certificate.setText("证书名称："+info.getCertificate());
        tx_classinfo_start_at.setText("开班时间："+info.getStart_at());
        tx_classinfo_linkmanname.setText("联系人："+bean.getData().getLinkman_data().getName());
        tx_classinfo_linkmanqq.setText("QQ:"+bean.getData().getLinkman_data().getQq());

        tx_classinfo_linkmanphone.setText("联系人电话："+bean.getData().getLinkman_data().getPhone());
        //webview.loadUrl();

        tv_classinfo_html.setText( analyzehtmp(info.getContent()));
       study_type=bean.getData().getStudy_type();


    }


    private String analyzehtmp(String data) {
        Document doc= Jsoup.parse(data);
        Element content = doc.getElementById("content");
        Elements links = doc.getElementsByTag("p");
        //实例化stringbuffer
        StringBuffer buffer =new StringBuffer();
        for (Element link : links) {
            //将文本提前出来
            buffer.append(link.text());
            buffer.append("\n");
        }
        return buffer.toString();
    }
    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
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

}
