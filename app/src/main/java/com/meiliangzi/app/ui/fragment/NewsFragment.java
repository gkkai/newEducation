package com.meiliangzi.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.IndexNewsListsBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.NewsDetailActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.WebViewActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseNewsAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.util.List;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 课程列表
 **/

public class NewsFragment extends BaseFragment implements NewsDetailActivity.OnCallBack,XListView.IXListViewListener{


    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    int id;
    private BaseNewsAdapter<IndexNewsListsBean.DataBean> adapter;
    //private MyListViewAdapter lladapter;
    List<IndexNewsListsBean.DataBean> DataBeans;

    private int page = 1;

    public NewsFragment(int position) {
        //getData(position, Constant.PAGESIZE);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //
       /* if(MyApplication.getdatas==null){

        }else {
            getData( MyApplication.type, page, Constant.PAGESIZE);
        }*/
        return createView(inflater.inflate(R.layout.fragment_lession, null, false));
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
       MyApplication.newsactivity.setOnclick(this);
        /*lladapter=new MyListViewAdapter(getActivity(), DataBeans,page);
        listView.setAdapter(lladapter);*/
       adapter = new BaseNewsAdapter<IndexNewsListsBean.DataBean>(getActivity(), listView,R.layout.item_news_lists) {
            @Override
            public void convert(BaseViewHolder helper, IndexNewsListsBean.DataBean item) {

            }
            @Override
            public void convert(BaseViewHolder helper, IndexNewsListsBean.DataBean item, int possition) {

                if (possition==2) {
                    if (!"".equals(item.getImg())) {

                            System.out.println("MyApplication.show=========================");
                            MyApplication.show=true;
                            helper.showOrGoneView(R.id.image_item3, true);
                            helper.showOrGoneView(R.id.llLayout_item_newslists, false);
                           // helper.setImageByUrlTAG(R.id.image_item3,item.getImg(),possition,page);
                            helper.setImageByUrl(R.id.image_item3, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);


                    } else if ("".equals(item.getImg())) {
                        helper.showOrHideView(R.id.ivImg, false);
                        // TODO
                        helper.showOrGoneView(R.id.image_item3, false);
                        helper.setText(R.id.tvTime, item.getCreate_time());
                        helper.setText(R.id.tvTitle, item.getNews_title());
                    }
                } else if ("".equals(item.getImg())) {
                    // TODO
                    helper.showOrGoneView(R.id.image_item3, false);
                    helper.showOrHideView(R.id.ivImg, false);
                    helper.setText(R.id.tvTime, item.getCreate_time());
                    helper.setText(R.id.tvTitle, item.getNews_title());
                } else {
                    // TODO
                    helper.showOrHideView(R.id.ivImg, true);
                    helper.showOrGoneView(R.id.image_item3, false);
                    helper.setImageByUrl(R.id.ivImg, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);
                    helper.setText(R.id.tvTime, item.getCreate_time());
                    helper.setText(R.id.tvTitle, item.getNews_title());
                }


            }
        };
        listView.setAdapter(adapter);
    }
    protected boolean isVisible;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }
    protected void lazyLoad() {
        getData(MyApplication.type, page, Constant.PAGESIZE);
    }
    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* if(adapter.getItem(position-1).isType()){
                    IntentUtils.startAtyWithSingleParam(getActivity(), ArticalDetailActivity.class,"id",adapter.getItem(position-1).getId());
                }else {
                    IntentUtils.startAtyWithSingleParam(getActivity(), VideoDetailActivity.class,"id",adapter.getItem(position-1).getId());
                }*/
                Intent intent = new Intent(MyApplication.newsactivity, WebViewActivity.class);
                intent.putExtra("url", adapter.getItem(position - 1).getUrl());
                getActivity().startActivity(intent);
            }
        });

        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyWithSingleParam(getActivity(), LoginActivity.class, "activity", "WholeFragment");
                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyWithSingleParam(getActivity(), PersonCenterActivity.class, "activity", "WholeFragment");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
       /* if(TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()){
            if(TextUtils.isEmpty(PreferManager.getUserId())){
                tvEmpty.setText("请先登录");
            }else if(!PreferManager.getIsComplete()){
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            tvEmpty.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            //getData( MyApplication.type, page, Constant.PAGESIZE);
        }*/
    }

    public void getData(int type, int currentPage, int pageSize) {
       /* if(TextUtils.isEmpty(PreferManager.getUserId()) && !PreferManager.getIsComplete()){
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }*/

            ProxyUtils.getHttpProxy().querynewslist(this, type, currentPage, pageSize);


    }

    protected void querynewslist(IndexNewsListsBean newslists) {


       /* if(page == 1){
            DataBeans= newslists.getData();
            adapter.pullRefresh(newslists.getData());
        }else {
            adapter.pullLoad(newslists.getData());
        }*/
       if(page == 1){

           adapter.pullRefresh(newslists.getData());
        }else {
            adapter.pullLoad(newslists.getData());
        }

        //adapter.setDatas(newslists.getData());
       // listView.setAdapter(adapter);
    }


    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
       /* if(page == 1){
            adapter.pullRefresh(new ArrayList<IndexNewsListsBean.DataBean>());
        }else {
            adapter.pullLoad(new ArrayList<IndexNewsListsBean.DataBean>());
        }*/
    }

    @Override
    public void onCallBack(int type) {
        page=1;
        MyApplication.type = type;
        getData( MyApplication.type, page, Constant.PAGESIZE);
    }

    @Override
    public void onRefresh() {
        page =1;
       /* if( MyApplication.newsactivity.getViewPager().getCurrentItem() == 0){
            type = 0;
        }else   if(MyApplication.newsactivity.getViewPager().getCurrentItem() == 1){
            type = 1;
        }else {
            type = 2;
        }*/
        getData( MyApplication.type, page, Constant.PAGESIZE);
    }

    @Override
    public void onLoadMore() {
       /* if(MyApplication.newsactivity.getViewPager().getCurrentItem() == 0){
            type = 0;
        }else   if(MyApplication.newsactivity.getViewPager().getCurrentItem() == 1){
            type = 1;
        }else {
            type = 2;
        }*/
        page++;
        getData( MyApplication.type, page, Constant.PAGESIZE);
    }
}
