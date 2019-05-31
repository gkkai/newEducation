package com.meiliangzi.app.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.ArticalList;
import com.meiliangzi.app.model.bean.BannerBean;
import com.meiliangzi.app.model.bean.HomePageBean;
import com.meiliangzi.app.model.bean.IndexNewsBean;
import com.meiliangzi.app.model.bean.IndexpicBean;
import com.meiliangzi.app.model.bean.StudyInfo;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.ArticalDetailActivity;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.NewsDetailActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.VideoDetailActivity;
import com.meiliangzi.app.ui.WebViewActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.view.AutomaticView;
import com.meiliangzi.app.ui.view.MapActivity;
import com.meiliangzi.app.ui.view.MapNewActivity;
import com.meiliangzi.app.ui.view.ZoomActivity;
import com.meiliangzi.app.ui.view.train.Train_Firest_Activity;
import com.meiliangzi.app.widget.ImageCycleView;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 我的
 **/

public class IndexFragment extends BaseFragment implements XListView.IXListViewListener, View.OnClickListener {


    private BaseQuickAdapter headViewAdapter;
    private BaseQuickAdapter<ArticalList.DataBean> listViewAdapter;
    private String type = "0";
    private int page = 1;
    TextView tvCoopNum;
    TextView tvOssNum;
    TextView tvTradeNum;
    TextView tvEmpty;
    ImageView indexpic;
    //TextView tvMore;
    /*TextView tvZoomMore;
    TextView tvmap;*/
    /*ImageView image_zoom;
    ImageView image_map;*/
    //AutomaticView automaticView;
    RelativeLayout ll_Trani;
    @BindView(R.id.listView)
    XListView listView;
    private ImageCycleView icvView;
    private View footView;
    private ArrayList<View> views;
    private List<IndexNewsBean.DataBean> indexNewBeans;
    private Gson gson;
    private View view;


    public IndexFragment() {
    }

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gson=new Gson();


        return createView(inflater.inflate(R.layout.fragment_index_, null, false));
    }

    @Override
    protected void findWidgets() {
        ProxyUtils.getHttpProxyNoDialog().indexslideshow(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getActivity().getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
    }

    protected void indexslideshow(BannerBean bean) {
        icvView.setImageResources(bean.getData(), imageCycleListener);

    }

    @Override
    public void onResume() {
        super.onResume();
        page=1;
        ProxyUtils.getHttpProxy().indexnews(this);
        ProxyUtils.getHttpProxy().indexpic(this);

        isLogin();
    }

    private void isLogin() {
        tvEmpty = (TextView) footView.findViewById(R.id.tvEmpty);
        if (TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()) {
            if (TextUtils.isEmpty(PreferManager.getUserId())) {
                tvEmpty.setText("请先登录");
            } else {
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);
            listViewAdapter.pullRefresh(new ArrayList<ArticalList.DataBean>());
            listView.setDivider(getResources().getDrawable(android.R.color.transparent));
            if (listView.getFooterViewsCount() == 1) {
                listView.addFooterView(footView, null, true);
            }
            listView.setPullLoadEnable(false);
            listView.setPullRefreshEnable(false);
        } else {
            listView.setPullLoadEnable(true);
            listView.setPullRefreshEnable(true);
            if (listView.getFooterViewsCount() == 1) {
                listView.removeFooterView(footView);
            }
            getData(type, page, Constant.PAGESIZE);
            ProxyUtils.getHttpProxyNoDialog().studyinfo(this, PreferManager.getUserId());
        }
    }
    protected void getTime(StudyInfo studyInfo) {
    }

    @Override
    protected void asyncRetrive() {

        ProxyUtils.getHttpProxy().querytotalstatistics(this);
    }

    protected void querytotalstatistics(HomePageBean bean) {
        tvCoopNum.setText(bean.getData().getCooperation() + "个");
        tvOssNum.setText(bean.getData().getCloudsstock() + "万");
        tvTradeNum.setText(bean.getData().getTurnover() + "万");
    }
    protected void getindexpic(IndexpicBean bean) {
        /*tvCoopNum.setText(bean.getData().getCooperation() + "个");
        tvOssNum.setText(bean.getData().getCloudsstock() + "万");
        tvTradeNum.setText(bean.getData().getTurnover() + "万");*/
        ImageLoader.getInstance().displayImage(bean.getData(), indexpic, MyApplication.getSimpleOptions(R.mipmap.index_wait, R.mipmap.index_wait));

    }
    public void getData(String type, int currentPage, int pageSize) {
        ProxyUtils.getHttpProxy().querylist(this, type, String.valueOf(currentPage), String.valueOf(pageSize), PreferManager.getUserId());
       // ProxyUtils.getHttpProxy().indexnews(this);
       /* OkhttpUtils.postJson(null,"indexnews",new HttpCallback(){
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.show(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {

                    IndexNewsBean indexNewBean =  gson.fromJson(response.body().string(),IndexNewsBean.class);
                    indexNewBeans= indexNewBean.getData();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init(automaticView);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
*/
    }
    protected void indexnews(IndexNewsBean indexNewBean) {
        indexNewBeans= indexNewBean.getData();
        //init(automaticView);

    }
    protected void getData(ArticalList articalList) {
        listView.removeFooterView(footView);
        if (page == 1) {
            listViewAdapter.pullRefresh(articalList.getData());
        } else {
            listViewAdapter.pullLoad(articalList.getData());
            //listViewAdapter.pullRefresh(articalList.getData());
        }
    }


    @Override
    protected void initComponent() {
        footView = getActivity().getLayoutInflater().inflate(R.layout.item_empty_view, null, false);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        final View headView = getActivity().getLayoutInflater().inflate(R.layout.headview_index_, null, false);
//        myGridView = (MyGridView) headView.findViewById(myGridView);
        icvView = (ImageCycleView) headView.findViewById(R.id.icvView);
        tvCoopNum = (TextView) headView.findViewById(R.id.tv_cooperate_num);
        tvOssNum = (TextView) headView.findViewById(R.id.tv_oss_num);
        tvTradeNum = (TextView) headView.findViewById(R.id.tv_trade_money);
        ll_Trani= (RelativeLayout) headView.findViewById(R.id.ll_train);
        indexpic=(ImageView) headView.findViewById(R.id.image_indexpic);
        ll_Trani.setOnClickListener(this);
        //image_zoom= (ImageView) headView.findViewById(R.id.image_zoom);
       // automaticView= (AutomaticView) headView.findViewById(R.id.AuView_Information);

       // tvMore= (TextView) headView.findViewById(R.id.tview_more);
        //image_map= (ImageView) headView.findViewById(R.id.image_map);
        //tvMore.setOnClickListener(this);
        //image_zoom.setOnClickListener(this);
        //image_map.setOnClickListener(this);

//
//        headViewAdapter = new BaseQuickAdapter(getActivity(), R.layout.item_headview_index) {
//            @Override
//            public void convert(BaseViewHolder helper, Object item) {
//                helper.setImageResource(R.id.ivImg,res[helper.getPosition()]);
//                helper.setText(R.id.tvTitle,title[helper.getPosition()]);
//            }
//        };
//        myGridView.setAdapter(headViewAdapter);
//        headViewAdapter.setDatas(TestData.getData(10));

        listViewAdapter = new BaseQuickAdapter<ArticalList.DataBean>(getActivity(), listView, R.layout.item_index) {
            @Override
            public void convert(BaseViewHolder helper, ArticalList.DataBean item) {
                helper.setText(R.id.tv_new_title, item.getName());
                helper.setText(R.id.tv_new_time, item.getCreate_time());
                helper.setText(R.id.tv_new_collect_num, item.getPraise());
                if (item.getIs_praise().equals("1")) {
                    helper.setImageResource(R.id.iv_new_collect, R.mipmap.ic_support_unselected);
                } else {
                    helper.setImageResource(R.id.iv_new_collect, R.mipmap.ic_support_selected);
                }
                helper.setImageByUrl(R.id.iv_news_img, item.getImg(), R.mipmap.test_artical, R.mipmap.test_artical);

                if (item.isType()) {
                    helper.getView(R.id.icPlay).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.icPlay).setVisibility(View.VISIBLE);
                }
            }
        };
        listView.setAdapter(listViewAdapter);
        listView.addHeaderView(headView, null, false);
    }
    //初始化滚动布局
    private void init(AutomaticView automaticView) {
        views = new ArrayList<>();
        setView();
        //List<String> data = new ArrayList<>();

        automaticView.setViews(views);
    }

    private void setView() {
        for (int i = 0; i < indexNewBeans.size(); i++) {

            //初始化布局的控件

            TextView tv2 = new TextView(getContext());
            tv2.setText(indexNewBeans.get(i).getNews_title());

            final int finalI = i;
            tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MyApplication.activity, WebViewActivity.class);
                    intent.putExtra("url",indexNewBeans.get(finalI).getUrl());
                    getActivity().startActivity(intent);

                }
            });
           /* //进行对控件赋值
            tv1.setText(data.get(i).toString());
            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).toString());
            }else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }*/

            //添加到循环滚动数组里面去
            views.add(tv2);
        }
    }

    ImageCycleView.ImageCycleViewListener imageCycleListener = new ImageCycleView.ImageCycleViewListener() {
        int id = 0;

        @Override
        public void displayImage(final String imageURL, ImageView imageView) {

            ImageLoader.getInstance().displayImage(imageURL, imageView);

//            imageView.setImageResource(R.mipmap.test_index_pic);
        }

        @Override
        public void onImageClick(int position, View imageView) {


        }
    };

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listViewAdapter.getItem(position - 2).isType()) {
                    IntentUtils.startAtyWithSingleParam(getActivity(), ArticalDetailActivity.class, "id", listViewAdapter.getItem(position - 2).getId());
                } else {
                    IntentUtils.startAtyWithSingleParam(getActivity(), VideoDetailActivity.class, "id", listViewAdapter.getItem(position - 2).getId());
                }
            }
        });

        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyForResult(getActivity(), LoginActivity.class, 1003, "activity", "index");

                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyForResult(getActivity(), PersonCenterActivity.class, 1003, "activity", "index");
                }
            }
        });

    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {

        if (page == 1) {
            listViewAdapter.pullRefresh(new ArrayList<ArticalList.DataBean>());
        } else {
            listViewAdapter.pullLoad(new ArrayList<ArticalList.DataBean>());
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData(type, page, Constant.PAGESIZE);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData(type, page, Constant.PAGESIZE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isLogin();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           /* case R.id.tview_more:
                // TODO  查看更多
                MyApplication.type=5;
                Intent intent=new Intent(MyApplication.activity, NewsDetailActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.image_zoom:
                // TODO  查看更多
                Intent intentZoom=new Intent(MyApplication.activity, ZoomActivity.class);
                getActivity().startActivity(intentZoom);
                break;
            case R.id.image_map:
                // TODO  查看更多
                Intent intentmap=new Intent(MyApplication.activity, MapNewActivity.class);
                getActivity().startActivity(intentmap);
                break;*/
            case R.id.ll_train:
                // TODO  查看更多
                Intent intentZoom=new Intent(MyApplication.activity, Train_Firest_Activity.class);
                getActivity().startActivity(intentZoom);
                break;

        }
    }

}
