package com.meiliangzi.app.ui.view.Academy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.MainActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.BaseInfo;
import com.meiliangzi.app.ui.view.Academy.bean.FindByPaperIdBean;
import com.meiliangzi.app.ui.view.Academy.bean.RuleListBean;
import com.meiliangzi.app.widget.MyGridView;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.meiliangzi.app.config.Constant.ArticlereadingId;
import static com.meiliangzi.app.config.Constant.DurationWatchVideoId;
import static com.meiliangzi.app.config.Constant.DurationarticlesId;
import static com.meiliangzi.app.config.Constant.Errorcorrectionoftestquestions;
import static com.meiliangzi.app.config.Constant.LOGINID;
import static com.meiliangzi.app.config.Constant.Orientationexamination;
import static com.meiliangzi.app.config.Constant.Professionalknowledgeanswers;
import static com.meiliangzi.app.config.Constant.Professionalknowledgeweekly;
import static com.meiliangzi.app.config.Constant.PublicIntelligenceAnswers;
import static com.meiliangzi.app.config.Constant.PublicKnowledgeWeekly;
import static com.meiliangzi.app.config.Constant.WatchVideoId;

public class TotalscoreActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gradview)
    MyGridView gradview;
    @BindView(R.id.tv_scoredetailed)
    TextView tv_scoredetailed;

    @BindView(R.id.tv_scoredescription)
    TextView tv_scoredescription;
    BaseVoteAdapter<RuleListBean.Data> Adapter;

    @BindView(R.id.im_black)
    ImageView im_black;

    @BindView(R.id.tv_totle_code)
    TextView tv_totle_code;
    private String content;

    @BindView(R.id.tv_today_scoredes)
    TextView tv_today_scoredes;
    Gson gson=new Gson();
    int  todayscoredes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

        onCreateView(R.layout.activity_totalscore);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.de_draft_color));
    }

    @Override
    protected void findWidgets() {
        im_black.setOnClickListener(this);
        gradview.setFocusable(false);
        Adapter=new BaseVoteAdapter<RuleListBean.Data>(this,gradview,R.layout.integralrule_item) {
            @Override
            public void convert(BaseViewHolder helper, final RuleListBean.Data item) {
                if(item.getDayScore()!=null){


                if(0==Integer.valueOf(item.getCycle())){
                    if(Integer.valueOf(item.getLimits())<=Integer.valueOf(item.getDayScore())){
                        //TODO 积分已满
                        ((TextView)helper.getView(R.id.tv_finish)).setText("已完成");
                        ((TextView)helper.getView(R.id.tv_finish)).setTextColor(getResources().getColor(R.color.black2));
                        helper.getView(R.id.tv_finish).setBackground(getResources().getDrawable(R.drawable.shape_huise));

                    }else {
                        //TODO 积分未满
                        ((TextView)helper.getView(R.id.tv_finish)).setText("去看看");
                        ((TextView)helper.getView(R.id.tv_finish)).setTextColor(getResources().getColor(R.color.white));
                        helper.getView(R.id.tv_finish).setBackground(getResources().getDrawable(R.drawable.shape_roid_red));
                        ((TextView)helper.getView(R.id.tv_finish)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (item.getId()) {
                                    case LOGINID:
                                        loginScore(NewPreferManager.getId());
                                        //TODO 阅读文章
//                                        Intent intent0 =new Intent(TotalscoreActivity.this, NewLoginActivity.class);
//                                        startActivity(intent0);
                                        break;
                                    case ArticlereadingId:
                                        //TODO 阅读文章
                                        Intent intent1 = new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent1.putExtra("pos", 0);
                                        startActivity(intent1);
                                        break;
                                    case WatchVideoId:
                                        //TODO  观看视屏
                                        Intent intent2 = new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent2.putExtra("pos", 1);
                                        startActivity(intent2);
                                        break;
                                    case DurationarticlesId:
                                        //TODO 文章学习时长
                                        Intent intent3 = new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent3.putExtra("pos", 0);
                                        startActivity(intent3);
                                        break;
                                    case DurationWatchVideoId:
                                        //TODO 视屏学习时长
                                        Intent intent4 = new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent4.putExtra("pos", 1);
                                        startActivity(intent4);
                                        break;
                                    case PublicIntelligenceAnswers:
                                        //TODO 公共智能答题
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()){

                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map <String,String> map=new HashMap<String, String>();
                                                    map.put("paperTypeId",item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean bean=   gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent =new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId",bean.getData().getId());
                                                                Examinationintent.putExtra("title",item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle",bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber",bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time",bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode",bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown",bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer",bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType",bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            }catch (final Exception e){
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });

                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent =new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid",item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }

                                        }
//                                        Intent intent5 = new Intent(TotalscoreActivity.this, WeekAnswerActivity.class);
//                                        intent5.putExtra("type", "0");
//                                        startActivity(intent5);
                                        break;
                                    case Professionalknowledgeanswers:
                                        //TODO 专业知识答题
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()){

                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map <String,String> map=new HashMap<String, String>();
                                                    map.put("paperTypeId",item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean   bean=   gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent =new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId",bean.getData().getId());
                                                                Examinationintent.putExtra("title",item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle",bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber",bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time",bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode",bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown",bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer",bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType",bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            }catch (final Exception e){
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });

                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent =new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid",item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }

                                        }
//                                        Intent intent6 =new Intent(TotalscoreActivity.this, WeekAnswerActivity.class);
//                                        intent6.putExtra("type","0");
//                                        startActivity(intent6);

                                        break;
                                    case PublicKnowledgeWeekly:
                                        //TODO 公共知识每周一答
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()) {

                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map<String, String> map = new HashMap<String, String>();
                                                    map.put("paperTypeId", item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean  bean = gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent = new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId", bean.getData().getId());
                                                                Examinationintent.putExtra("title", item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle", bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber", bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time", bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode", bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown", bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer", bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType", bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            } catch (final Exception e) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });

                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent = new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid", item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }
                                        }
//                                            Intent intent7 =new Intent(TotalscoreActivity.this, WeekAnswerActivity.class);
//                                        intent7.putExtra("type","1");
//                                        startActivity(intent7);
                                        break;
                                    case Professionalknowledgeweekly:
                                        //TODO 专业知识每周一答
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()) {

                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map<String, String> map = new HashMap<String, String>();
                                                    map.put("paperTypeId", item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean   bean = gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent = new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId", bean.getData().getId());
                                                                Examinationintent.putExtra("title", item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle", bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber", bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time", bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode", bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown", bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer", bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType", bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            } catch (final Exception e) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });

                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent = new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid", item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }
                                        }

//                                            Intent intent8 =new Intent(TotalscoreActivity.this, WeekAnswerActivity.class);
//                                        intent8.putExtra("type","1");
//                                        startActivity(intent8);
                                        break;
                                    case Orientationexamination:
                                        //TODO 定向考试
                                        Intent intent9=new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent9.putExtra("pos",2);
                                        startActivity(intent9);
                                        break;
                                    case Errorcorrectionoftestquestions:
                                        //TODO 试题纠错
                                        Intent intent10=new Intent(TotalscoreActivity.this, ErrorBankActivity.class);
                                        startActivity(intent10);
                                        break;

                                }
                            }
                        });

                    }
                }else {
                    if(Integer.valueOf(item.getLimits())<=Integer.valueOf(item.getDayScore())){
                        //TODO 积分已满
                        ((TextView)helper.getView(R.id.tv_finish)).setText("已完成");
                        ((TextView)helper.getView(R.id.tv_finish)).setTextColor(getResources().getColor(R.color.black2));
                        helper.getView(R.id.tv_finish).setBackground(getResources().getDrawable(R.drawable.shape_huise));

                    }else {
                        //TODO 积分未满
                        ((TextView)helper.getView(R.id.tv_finish)).setText("去看看");
                        ((TextView)helper.getView(R.id.tv_finish)).setTextColor(getResources().getColor(R.color.white));
                        helper.getView(R.id.tv_finish).setBackground(getResources().getDrawable(R.drawable.shape_roid_red));
                        ((TextView)helper.getView(R.id.tv_finish)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (item.getId()){
                                    case LOGINID:
                                        loginScore(NewPreferManager.getId());
                                        //TODO 阅读文章

                                        break;

                                    case ArticlereadingId:
                                        //TODO 阅读文章
                                        Intent intent1 =new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent1.putExtra("pos",0);
                                        startActivity(intent1);
                                        break;
                                    case WatchVideoId:
                                        //TODO  观看视屏
                                        Intent intent2 =new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent2.putExtra("pos",1);
                                        startActivity(intent2);
                                        break;
                                    case DurationarticlesId:
                                        //TODO 文章学习时长
                                        Intent intent3 =new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent3.putExtra("pos",0);
                                        startActivity(intent3);
                                        break;
                                    case DurationWatchVideoId:
                                        //TODO 视屏学习时长
                                        Intent intent4 =new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent4.putExtra("pos",1);
                                        startActivity(intent4);
                                        break;
                                    case PublicIntelligenceAnswers:
                                        //TODO 公共智能答题
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()){

                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map <String,String> map=new HashMap<String, String>();
                                                    map.put("paperTypeId",item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean  bean=   gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent =new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId",bean.getData().getId());
                                                                Examinationintent.putExtra("title",item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle",bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber",bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time",bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode",bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown",bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer",bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType",bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            }catch (final Exception e){
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });

                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent =new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid",item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }



                                        }
//
//                                        Intent intent5 =new Intent(TotalscoreActivity.this, WeekAnswerActivity.class);
//                                        intent5.putExtra("type","0");
//                                        startActivity(intent5);
                                        break;
                                    case Professionalknowledgeanswers:
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()){
                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map <String,String> map=new HashMap<String, String>();
                                                    map.put("paperTypeId",item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean  bean=   gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent =new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId",bean.getData().getId());
                                                                Examinationintent.putExtra("title",item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle",bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber",bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time",bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode",bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown",bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer",bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType",bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            }catch (final Exception e){
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });
                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent =new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid",item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }



                                        }

//

                                        break;
                                    case PublicKnowledgeWeekly:

                                        //TODO 公共知识每周一答
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()){

                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map <String,String> map=new HashMap<String, String>();
                                                    map.put("paperTypeId",item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean bean=   gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent =new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId",bean.getData().getId());
                                                                Examinationintent.putExtra("title",item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle",bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber",bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time",bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode",bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown",bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer",bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType",bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            }catch (final Exception e){
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });
                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent =new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid",item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }



                                        }
//                                        Intent intent7 =new Intent(TotalscoreActivity.this, WeekAnswerActivity.class);
//                                        intent7.putExtra("type","1");
//                                        startActivity(intent7);
                                        break;
                                    case Professionalknowledgeweekly:
                                        //TODO 专业知识每周一答
                                        if(Integer.valueOf(item.getPaperTypeCount())>=1){
                                            // TODO 有子栏目
                                            Intent culmnintent =new Intent(TotalscoreActivity.this, ColumnActivity.class);
                                            culmnintent.putExtra("pid",item.getPaperTypeId());
                                            culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                            startActivity(culmnintent);
                                        }else {
                                            switch (item.getPaperNumber()) {

                                                //TODO 没有子栏目
                                                case "0":
                                                    ToastUtils.show("暂无试卷");
                                                    break;
                                                case "1":
                                                    Map<String, String> map = new HashMap<String, String>();
                                                    map.put("paperTypeId", item.getPaperTypeId());
                                                    OkhttpUtils.getInstance(getBaseContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                                        @Override
                                                        public void onFaild(Exception e) {

                                                        }

                                                        @Override
                                                        public void onResponse(String json) {
                                                            try {
                                                                FindByPaperIdBean bean = gson.fromJson(json, FindByPaperIdBean.class);
                                                                //TODO 一个试卷 直接进试题
                                                                Intent Examinationintent = new Intent(TotalscoreActivity.this, WeekExaminationActivity.class);
                                                                Examinationintent.putExtra("paperId", bean.getData().getId());
                                                                Examinationintent.putExtra("title", item.getPaperTypeName());
                                                                Examinationintent.putExtra("pagetitle", bean.getData().getTitle());
                                                                Examinationintent.putExtra("totalNumber", bean.getData().getTotalNumber());
                                                                Examinationintent.putExtra("time", bean.getData().getDuration());
                                                                Examinationintent.putExtra("mode", bean.getData().getMode());
                                                                Examinationintent.putExtra("countDown", bean.getData().getCountDown());
                                                                Examinationintent.putExtra("repeatAnswer", bean.getData().getRepeatAnswer());
                                                                Examinationintent.putExtra("createType", bean.getData().getCreateType());
                                                                startActivity(Examinationintent);

                                                            } catch (final Exception e) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        ToastUtils.show("暂无试卷");
                                                                    }
                                                                });
                                                            }


                                                        }
                                                    });

                                                    break;
                                                default:
                                                    //有很多试卷
                                                    Intent culmnintent = new Intent(TotalscoreActivity.this, NoCulmnActivity.class);
                                                    culmnintent.putExtra("pid", item.getPaperTypeId());
                                                    culmnintent.putExtra("tv_title",item.getPaperTypeName());
                                                    startActivity(culmnintent);
                                            }
                                        }

//                                            Intent intent8 =new Intent(TotalscoreActivity.this, WeekAnswerActivity.class);
//                                        intent8.putExtra("type","1");
//                                        startActivity(intent8);
                                        break;
                                    case Orientationexamination:
                                        //TODO 定向考试
                                        Intent intent9=new Intent(TotalscoreActivity.this, MainActivity.class);
                                        intent9.putExtra("pos",2);
                                        startActivity(intent9);
                                        break;
                                    case Errorcorrectionoftestquestions:
                                        //TODO 试题纠错
                                        Intent intent10=new Intent(TotalscoreActivity.this, ErrorBankActivity.class);
                                        startActivity(intent10);
                                        break;

                                }
                            }
                        });

                    }
                }

                ((TextView)helper.getView(R.id.tv_integralName)).setText(item.getIntegralName());
                ((TextView)helper.getView(R.id.tv_conditions)).setText(item.getRuleDescribe());

            }
            }
        };
        gradview.setAdapter(Adapter);
        tv_scoredetailed.setOnClickListener(this);
        tv_scoredescription.setOnClickListener(this);
    }

    private void loginScore(String userId) {
        Map<String,String> maps=new HashMap<>();
        maps.put("userId", userId);
        maps.put("ruleId", LOGINID);
        OkhttpUtils.getInstance(this).doPost("academyService/detail/loginScore", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }
            @Override
            public void onResponse(final String json) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            BaseInfo bean=new Gson().fromJson(json,BaseInfo.class);
                            if(bean.getCode()==1){
                                ToastUtils.show(bean.getSuccess());
                            }else {
                               getlsit();
                            }

                        }catch (Exception e){
                            ToastUtils.show(e.getMessage());
                        }




                    }
                });

            }
        });
    }
    @Override
    protected void initComponent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getlsit();
        tv_totle_code.setText(NewPreferManager.getUserTotalScore());
    }

    private void getlsit(){
        Map<String,String> maps=new HashMap<>();
        maps.put("userId", NewPreferManager.getId());
        OkhttpUtils.getInstance(this).getList("academyService/rule/lists", maps, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        ToastUtils.show(e.getMessage());


                    }
                });
            }

            @Override
            public void onResponse(final String json) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson=new Gson();
                            RuleListBean bean=   gson.fromJson(json,RuleListBean.class);
                            tv_today_scoredes.setText(bean.getData().get(1).getDayIntegral());
                            Adapter.setDatas(bean.getData());
                        }catch (Exception e){

                        }




                    }
                });


            }
        });
    }
    private void getList(RuleListBean bean){
        Adapter.setDatas(bean.getData());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_scoredetailed:
                Intent intent =new Intent(this,IntegraldetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_scoredescription:
                Intent intent1 =new Intent(this,DetailsWebActivity.class);
                intent1.putExtra("url","academyService/html/rule.html");
                intent1.putExtra("title","积分说明");
                intent1.putExtra("type","1");
                startActivity(intent1);
                break;
            case R.id.im_black:
                finish();
                break;

        }
    }

}
