package com.meiliangzi.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.NewsDetailActivity;
import com.meiliangzi.app.ui.WebViewActivity;
import com.meiliangzi.app.ui.base.BaseNewsAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;
import com.tencent.mm.opensdk.utils.Log;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements XListView.IXListViewListener ,NewsDetailActivity.OnCallBack, AdapterView.OnItemClickListener {
    private final int FIRST_FRAGMENT = 0;
    private final int SECOND_FRAGMENT = 1;
    private final int THIRD_FRAGMENT = 2;
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private BaseNewsAdapter<IndexNewsListsBean.DataBean> adapter;
    private int page=1;
    private View mView;
    IndexNewsListsBean newslists;
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    public BlankFragment(int position) {
        //getData(position, Constant.PAGESIZE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mView = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    public void init(){
        // MyApplication.newsactivity.setOnclick(this);
        MyApplication.newsactivity.setOnclick(this);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        // MyApplication.newsactivity.setOnclick(this);
        adapter = new BaseNewsAdapter<IndexNewsListsBean.DataBean>(getActivity(), listView,R.layout.item_news_lists) {
            @Override
            public void convert(BaseViewHolder helper, IndexNewsListsBean.DataBean item) {

            }

            @Override
            public void convert(BaseViewHolder helper, IndexNewsListsBean.DataBean item, int possition) {

                if(possition==2&&page==1){

                    if(!"".equals(item.getImg())){
                        helper.showOrGoneView(R.id.image_item3,true);
                        helper.showOrGoneView(R.id.llLayout_item_newslists,false);
                        //helper.setImageByUrlTAG(R.id.image_item3,item.getImg(),possition,page);
                    }else if("".equals(item.getImg())){
                        helper. showOrHideView(R.id.ivImg,false);
                        helper.setText(R.id.tvTime, item.getCreate_time());
                        helper.setText(R.id.tvTitle, item.getNews_title());
                    }
                }else if("".equals(item.getImg())){
                    helper. showOrHideView(R.id.ivImg,false);
                    helper.setText(R.id.tvTime, item.getCreate_time());
                    helper.setText(R.id.tvTitle, item.getNews_title());
                }else {
                    helper.setImageByUrl(R.id.ivImg, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);
                    helper.setText(R.id.tvTime, item.getCreate_time());
                    helper.setText(R.id.tvTitle, item.getNews_title());
                }



            }
        };


        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        page =1;

        getData( MyApplication.type, page, Constant.PAGESIZE);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData( MyApplication.type, page, Constant.PAGESIZE);
    }
    public void getData(int type, int currentPage, int pageSize) {
        if(TextUtils.isEmpty(PreferManager.getUserId()) && !PreferManager.getIsComplete()){
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        ProxyUtils.getHttpProxy().querynewslist(this, type, currentPage, pageSize);


    }
    public void getData(int type,  int pageSize) {
        if(TextUtils.isEmpty(PreferManager.getUserId()) && !PreferManager.getIsComplete()){
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }

        ProxyUtils.getHttpProxy().querynewslist(this, type, page, pageSize);


    }

    protected void lazyLoad() {
        getData( MyApplication.type, page, Constant.PAGESIZE);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
        }
    }
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    protected void querynewslist(IndexNewsListsBean newslists) {
        Log.d("TAG","================"+ MyApplication.type);
        System.out.println("page================"+page+"");
        System.out.println("page================"+ MyApplication.type);
        if(this.newslists==null){
            this.newslists=new IndexNewsListsBean();
        }
        this.newslists.getData().add((IndexNewsListsBean.DataBean) newslists.getData());
        if(adapter!=null){
            adapter.setDatas(this.newslists.getData());
        }
        /*if(adapter!=null){
            this.newslists=newslists;
            adapter.pullRefresh(newslists.getData());
        }else {
            this.newslists=newslists;
        }*/
        //adapter.pullRefresh(newslists.getData());

    }

    @Override
    public void onCallBack(int type) {
        MyApplication.type = type;
        getData( MyApplication.type, page, Constant.PAGESIZE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(MyApplication.newsactivity, WebViewActivity.class);
        intent.putExtra("url",adapter.getItem(i-1).getUrl());
        getActivity().startActivity(intent);
    }

//    @Override
//    public void onClickTab(View tab, int index) {
//
//        getData( index, page, Constant.PAGESIZE);
//    }
}
