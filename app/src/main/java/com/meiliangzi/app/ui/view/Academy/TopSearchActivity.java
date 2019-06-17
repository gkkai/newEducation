package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.ArticleListBean;
import com.meiliangzi.app.ui.view.Academy.bean.VideoListBean;
import com.meiliangzi.app.widget.XListView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TopSearchActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    @BindView(R.id.edit_topsearch)
    EditText edit_topsearch;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    String search;
    String searc1;
    private String type;
    @BindView(R.id.listView)
    XListView listView;
    BaseVoteAdapter<ArticleListBean.Data> wenzhangAdapter;
    BaseVoteAdapter<VideoListBean.Data> voidAdapter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type= getIntent().getStringExtra("type");
        onCreateView(R.layout.activity_top_search);
    }

    @Override
    protected void findWidgets() {
        tv_cancel.setOnClickListener(this);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        listView.setXListViewListener(this);
        edit_topsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
     if ((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //search();
         search=v.getText().toString();
         if("视频".equals(type)){
             Map<String,String> map=new HashMap<String, String>();
             map.put("pageNumber","0");
             map.put("pageSize","10");
             map.put("title",search);
            getPageList(map,"academyService/video/getPageList");
             //ProxyUtils.gethttpchanyexutyan().getPageList(TopSearchActivity.this, 0,10,search);
         }else {
             Map<String,String> map=new HashMap<String, String>();
             map.put("pageNumber","0");
             map.put("pageSize","10");
             map.put("title",search);
             getPageList(map,"academyService/essay/getPageList");
             //ProxyUtils.gethttpchanyexutyan().getPageList(TopSearchActivity.this,0,"10",search);
         }

                    return true;
                }
                return false;
            }
        });
        edit_topsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searc1=s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
//    private void getPageList(ArticleListBean bean){
//
//
//        listView.setPullLoadEnable(true);
//        listView.setPullRefreshEnable(true);
//        if (page == 0) {
//            wenzhangAdapter.pullRefresh(bean.getData());
//        } else {
//            wenzhangAdapter.pullLoad(bean.getData());
//        }
//
//
//    }
//    private void getPageList(VideoListBean bean){}
        private void getPageList(Map<String,String> map, String url){



            OkhttpUtils.getInstance(this).getList(url, map, new OkhttpUtils.onCallBack() {
                @Override
                public void onFaild(Exception e) {

                }

                @Override
                public void onResponse(final String json) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Gson gson=new Gson();
                                if("视频".equals(type)){
                                    VideoListBean bean=gson.fromJson(json,VideoListBean.class);

                                    if(bean.getCode()=="1"){
                                        ToastUtils.show(bean.getMessage());
                                    }else {
                                        if(bean.getData()!=null&&bean.getData().size()==0){
                                           ToastUtils.show("暂无数据");
                                        }else {
                                            listView.setPullLoadEnable(true);
                                            listView.setPullRefreshEnable(true);
                                            if (page == 0) {
                                                voidAdapter.pullRefresh(bean.getData());
                                            } else {
                                                voidAdapter.pullLoad(bean.getData());
                                            }
                                        }

                                    }
                                }else {
                                    ArticleListBean bean=gson.fromJson(json,ArticleListBean.class);
                                    if(bean.getCode()=="1"){
                                        ToastUtils.show(bean.getMessage());
                                    }else {
                                        if(bean.getData()!=null&&bean.getData().size()==0){
                                            ToastUtils.show("暂无数据");
                                        }else {
                                            listView.setPullLoadEnable(true);
                                            listView.setPullRefreshEnable(true);
                                            if (page == 0) {
                                                wenzhangAdapter.pullRefresh(bean.getData());
                                            } else {
                                                wenzhangAdapter.pullLoad(bean.getData());
                                            }
                                        }

                                    }
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
    public void onRefresh() {
        page = 0;

        if("视频".equals(type)){
            Map<String,String> map=new HashMap<String, String>();
            map.put("pageNumber","0");
            map.put("pageSize","10");
            map.put("title",search);
            getPageList(map,"academyService/video/getPageList");
        }else {
            Map<String,String> map=new HashMap<String, String>();
            map.put("pageNumber","0");
            map.put("pageSize","10");
            map.put("title",search);
            getPageList(map,"academyService/essay/getPageList");
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        if("视频".equals(type)){
            Map<String,String> map=new HashMap<String, String>();
            map.put("pageNumber","0");
            map.put("pageSize","10");
            map.put("title",search);
            getPageList(map,"academyService/video/getPageList");
        }else {
            Map<String,String> map=new HashMap<String, String>();
            map.put("pageNumber","0");
            map.put("pageSize","10");
            map.put("title",search);
            getPageList(map,"academyService/essay/getPageList");
        }


    }

    @Override
    protected void initComponent() {
        if("视频".equals(type)){
            voidAdapter=new BaseVoteAdapter<VideoListBean.Data>(this,listView,R.layout.tab_video_list) {
                @Override
                public void convert(BaseViewHolder helper, VideoListBean.Data item) {
                    switch (item.getDisplayMode()){

                        case 1:
                            helper.showOrGoneView(R.id.rl_big_image,true);
                            helper.showOrGoneView(R.id.rl_small_image,false);
                            ((TextView)helper.getView(R.id.tv_big_title)).setText(item.getTitle());

                            ((TextView)helper.getView(R.id.tv_big_department)).setText(item.getDepartmentName()
                                    +"   "+item.getCreateTime());
                            helper.setImageByUrl(R.id.image_big_one,item.getCoverImage(),R.mipmap.nocourse,R.mipmap.nocourse);
                            break;
                        case 2:
                            helper.showOrGoneView(R.id.rl_big_image,false);
                            helper.showOrGoneView(R.id.rl_small_image,true);
                            ((TextView)helper.getView(R.id.tv_small_title)).setText(item.getTitle());

                            ((TextView)helper.getView(R.id.tv_small_department)).setText(item.getDepartmentName()
                                    +"   "+item.getCreateTime());
                            helper.setImageByUrl(R.id.image_small,item.getCoverImage(),R.mipmap.nocourse,R.mipmap.nocourse);

                            break;


                    }

                }
            };
            listView.setAdapter(voidAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(TopSearchActivity.this, VideoPlayActivity.class);
//                intent.putExtra("type","1");
//                intent.putExtra("url","html/video.html?id="+Adapter.getItem(position-1).getId());
//                intent.putExtra("title",Adapter.getItem(position-1).getTitle());
                    intent.putExtra("id",voidAdapter.getItem(position-1).getId());
                    startActivity(intent);
                }
            });

        }else {
            wenzhangAdapter=new BaseVoteAdapter<ArticleListBean.Data>(this,listView,R.layout.tab_list) {
                @Override
                public void convert(BaseViewHolder helper, ArticleListBean.Data item) {
                    switch (item.getDisplayMode()){
                        case 0:
                            helper.showOrGoneView(R.id.rl_big_image,false);
                            helper.showOrGoneView(R.id.rl_no_image,true);
                            helper.showOrGoneView(R.id.rl_small_image,false);
                            ((TextView)helper.getView(R.id.tv_no_title)).setText(item.getTitle());

                            ((TextView)helper.getView(R.id.tv_no_department)).setText(item.getDepartmentName()
                                    +"   "+item.getCreateTime());
                            //(ImageView)helper.getView(R.id.tv_no_department).setImageByUrl()

                            break;
                        case 1:
                            helper.showOrGoneView(R.id.rl_big_image,true);
                            helper.showOrGoneView(R.id.rl_no_image,false);
                            helper.showOrGoneView(R.id.rl_small_image,false);
                            ((TextView)helper.getView(R.id.tv_big_title)).setText(item.getTitle());

                            ((TextView)helper.getView(R.id.tv_big_department)).setText(item.getDepartmentName()
                                    +"   "+item.getCreateTime());

                            helper.setImageByUrl(R.id.image_big,item.getCoverImage(),R.mipmap.nocourse,R.mipmap.nocourse);
                            break;
                        case 2:
                            helper.showOrGoneView(R.id.rl_big_image,false);
                            helper.showOrGoneView(R.id.rl_no_image,false);
                            helper.showOrGoneView(R.id.rl_small_image,true);
                            ((TextView)helper.getView(R.id.tv_small_title)).setText(item.getTitle());

                            ((TextView)helper.getView(R.id.tv_small_department)).setText(item.getDepartmentName()
                                    +"   "+item.getCreateTime());
                            List<String> result = Arrays.asList(item.getCoverImage().split(","));
                            if(result.size()>=1){
                                helper.setImageByUrl(R.id.image_small_one,result.get(0),R.mipmap.nocourse,R.mipmap.nocourse);

                            }
                            if(result.size()>=2){
                                helper.setImageByUrl(R.id.image_small_two,result.get(1),R.mipmap.nocourse,R.mipmap.nocourse);

                            }
                            if(result.size()>=3){
                                helper.setImageByUrl(R.id.image_small_three,result.get(2),R.mipmap.nocourse,R.mipmap.nocourse);

                            }


                            break;

                    }

                }
            };

            listView.setAdapter(wenzhangAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(TopSearchActivity.this, DetailsWebActivity.class);
                    intent.putExtra("type","0");
                    intent.putExtra("url","academyService/html/articleInfo.html?id="+wenzhangAdapter.getItem(position-1).getId());
                    intent.putExtra("title",wenzhangAdapter.getItem(position-1).getTitle());
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                finish();
                break;
        }
    }
}
