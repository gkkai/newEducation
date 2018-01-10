package com.meiliangzi.app.ui.view.train;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.ArticalList;
import com.meiliangzi.app.model.bean.RecentOpenClassBean;
import com.meiliangzi.app.model.bean.StudyCenternBean;
import com.meiliangzi.app.model.bean.TrainBannerBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.TraonImageCycleView;
import com.meiliangzi.app.widget.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Train_Firest_Activity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.imageheader)
    ImageView imageheader;
    private ArrayList<View> views;
    private List<TrainBannerBean.DataBean> indexNewBeans;
    private Gson gson;
    private TraonImageCycleView icvView;
    private BaseQuickAdapter<ArticalList.DataBean> listViewAdapter;
    private Intent intent;
    private LinearLayout llstudycenter,llrecentopenclass,lldataquery,lldatadownload;
    private ImageView image_video_one,image_video_two,image_video_three,image_video_four;
    private TextView text_video_onename,text_video_twoname,text_video_threename,text_video_fourname,openclassmore,videomore;
    private ImageView image_reclass_one,image_reclass_two,image_reclass_three,image_reclass_four;
    private TextView text_reclass_onename,text_reclass_twoname,text_reclass_threename,text_reclass_fourname;
    RecentOpenClassBean recentdatas;//近期开班
    StudyCenternBean studydatas;//学习中心
    int currentPage=1;
    int pageSize=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new Gson();
        onCreateView(R.layout.activity_train__firest_);
    }

    @Override
    protected void findWidgets() {
        imageheader.setOnClickListener(this);
        //轮播图
        ProxyUtils.getHttpProxyNoDialog().querypiclist(this);

    }protected void querypiclist(TrainBannerBean bean) {
        icvView.setImageResources(bean.getData(), imageCycleListener);

    }
    @Override
    public void onResume() {
        super.onResume();

        isLogin();
    }
    private void isLogin() {
        if (TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()) {
            if (TextUtils.isEmpty(PreferManager.getUserId())) {
                tvEmpty.setText("请先登录");
            } else {
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setDivider(getResources().getDrawable(android.R.color.transparent));
            if (listView.getFooterViewsCount() == 1) {
            }
            listView.setPullLoadEnable(false);
            listView.setPullRefreshEnable(false);
        } else {
            tvEmpty.setVisibility(View.GONE);
            listView.setPullLoadEnable(false);
            listView.setPullRefreshEnable(false);
            if (listView.getFooterViewsCount() == 1) {
            }
            getData();
        }
    }
    private void getData() {
        //TODO 近期开班列表
        ProxyUtils.getHttpProxy().recentclasslist(this, 1, 4,Integer.valueOf(PreferManager.getUserId()));
//TODO 精品视屏 和学习中心
        ProxyUtils.getHttpProxy().qualityvideolist(this, 1, 4);

    }
    //TODO 近期开班返回数据
    protected void getrecentclasslist( RecentOpenClassBean data) {
        recentdatas=data;
        setImageByUrl(image_reclass_one,data.getData().get(0).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        setImageByUrl(image_reclass_two,data.getData().get(1).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        setImageByUrl(image_reclass_three,data.getData().get(2).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        setImageByUrl(image_reclass_four,data.getData().get(3).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        text_reclass_onename.setText(data.getData().get(0).getTitle());
        text_reclass_twoname.setText(data.getData().get(1).getTitle());
        text_reclass_threename.setText(data.getData().get(2).getTitle());
        text_reclass_fourname.setText(data.getData().get(3).getTitle());


    }




    protected void getstudylists( StudyCenternBean data) {
        studydatas=data;
        setImageByUrl(image_video_one,data.getData().get(0).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        setImageByUrl(image_video_two,data.getData().get(1).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        setImageByUrl(image_video_three,data.getData().get(2).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        setImageByUrl(image_video_four,data.getData().get(3).getImage(),R.mipmap.defaule,R.mipmap.defaule);
        text_video_onename.setText(data.getData().get(0).getVideo_name());
        text_video_twoname.setText(data.getData().get(1).getVideo_name());
        text_video_threename.setText(data.getData().get(2).getVideo_name());
        text_video_fourname.setText(data.getData().get(3).getVideo_name());



    }

    @Override
    protected void initComponent() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);

        View headView = getLayoutInflater().inflate(R.layout.headview_train_, null, false);
        openclassmore= (TextView) headView.findViewById(R.id.tv_openclass_more);
        openclassmore.setOnClickListener(this);
        videomore=(TextView) headView.findViewById(R.id.tv_videw_more);
        videomore.setOnClickListener(this);
        icvView = (TraonImageCycleView) headView.findViewById(R.id.icvView);
        llstudycenter= (LinearLayout) headView.findViewById(R.id.ll_train_studycenter);
        lldatadownload=(LinearLayout) headView.findViewById(R.id.ll_train_datadownload);
        lldataquery=(LinearLayout) headView.findViewById(R.id.ll_train_dataquery);
        llrecentopenclass=(LinearLayout) headView.findViewById(R.id.ll_train_recentopenclass);
        image_video_one= (ImageView) headView.findViewById(R.id.video_list_oneimage);
        image_video_one.setOnClickListener( this);
        image_video_two= (ImageView) headView.findViewById(R.id.video_list_twoimage);
        image_video_two.setOnClickListener(this);
        image_video_three= (ImageView) headView.findViewById(R.id.video_list_threeimage);
        image_video_three.setOnClickListener(this);
        image_video_four= (ImageView) headView.findViewById(R.id.video_list_fourimage);
        image_video_four.setOnClickListener(this);
        text_video_onename= (TextView) headView.findViewById(R.id.video_list_onename);
        text_video_twoname= (TextView) headView.findViewById(R.id.video_list_twoname);
        text_video_threename= (TextView) headView.findViewById(R.id.video_list_threename);
        text_video_fourname= (TextView) headView.findViewById(R.id.video_list_fourname);
        image_reclass_one= (ImageView) headView.findViewById(R.id.reclass_list_oneimage);
        image_reclass_one.setOnClickListener(this);
        image_reclass_two= (ImageView) headView.findViewById(R.id.reclass_list_twoimage);
        image_reclass_two.setOnClickListener(this);
        image_reclass_three= (ImageView) headView.findViewById(R.id.reclass_list_threeimage);
        image_reclass_three.setOnClickListener(this);
        image_reclass_four= (ImageView) headView.findViewById(R.id.reclass_list_fourimage);
        image_reclass_four.setOnClickListener(this);
        text_reclass_onename= (TextView) headView.findViewById(R.id.reclass_list_onename);
        text_reclass_twoname= (TextView) headView.findViewById(R.id.reclass_list_twoname);
        text_reclass_threename= (TextView) headView.findViewById(R.id.reclass_list_threename);
        text_reclass_fourname= (TextView) headView.findViewById(R.id.reclass_list_fourname);

        lldataquery.setOnClickListener(this);
        llrecentopenclass.setOnClickListener(this);
        lldatadownload.setOnClickListener(this);
        llstudycenter.setOnClickListener(this);
        listViewAdapter = new BaseQuickAdapter<ArticalList.DataBean>(this, listView, R.layout.item_index) {
            @Override
            public void convert(BaseViewHolder helper, ArticalList.DataBean item) {
              /*  helper.setText(R.id.tv_new_title, item.getName());
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
                }*/
            }
        };
        listView.setAdapter(listViewAdapter);
        listView.addHeaderView(headView, null, false);
    }
    //轮播图监听事件
    TraonImageCycleView.ImageCycleViewListener imageCycleListener = new TraonImageCycleView.ImageCycleViewListener() {
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

            }
        });


        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyWithSingleParam(Train_Firest_Activity.this, LoginActivity.class, "activity", "WholeFragment");
                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyWithSingleParam(Train_Firest_Activity.this, PersonCenterActivity.class, "activity", "WholeFragment");
                }
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isLogin();
        }
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

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageheader:
                // TODO  资料下载
                intent = new Intent(this, PersonCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_train_datadownload:
                // TODO  资料下载
                intent = new Intent(this, TrainActivity.class);
                intent.putExtra("type", "datadownload");
                startActivity(intent);
                break;
            case R.id.ll_train_dataquery:
                // TODO  信息查询
                intent = new Intent(this, QueryDataActivity.class);
                intent.putExtra("type", "inputdataquery");
                startActivity(intent);
                break;
            case R.id.ll_train_studycenter:
                // TODO  学习中心
                intent = new Intent(this, TrainActivity.class);
                intent.putExtra("type", "studycenter");
                startActivity(intent);
                break;
            case R.id.ll_train_recentopenclass:
                // TODO  近期开班
                intent = new Intent(this, TrainActivity.class);
                intent.putExtra("type", "recentopenclass");
                startActivity(intent);
                break;
            case R.id.video_list_oneimage:

                // TODO  精品视频1
                Intent studycenterintent1 = new Intent(this, TrainClassStudyActivity.class);
                studycenterintent1.putExtra("videoId", studydatas.getData().get(1).getId());
                startActivity(studycenterintent1);

                break;
            case R.id.tv_openclass_more:

                intent = new Intent(this, TrainActivity.class);
                intent.putExtra("type", "recentopenclass");
                startActivity(intent); break;
            case R.id.tv_videw_more:

                intent = new Intent(this, TrainActivity.class);
                intent.putExtra("type", "studycenter");
                startActivity(intent);
                break;
            case R.id.video_list_twoimage:

                // TODO  精品视频2
                if (studydatas != null && studydatas.getData().size() != 0) {
                    Intent studycenterintent2 = new Intent(this, TrainClassStudyActivity.class);
                    studycenterintent2.putExtra("videoId", studydatas.getData().get(1).getId());
                    startActivity(studycenterintent2);
                }
                break;
            case R.id.video_list_threeimage:
                // TODO  精品视频3
                if (studydatas != null && studydatas.getData().size() != 0) {
                    Intent studycenterintent3 = new Intent(this, TrainClassStudyActivity.class);
                    studycenterintent3.putExtra("videoId", studydatas.getData().get(2).getId());
                    startActivity(studycenterintent3);
                }
                break;
            case R.id.video_list_fourimage:
                // TODO  精品视频4
                if (studydatas != null && studydatas.getData().size() != 0) {
                    Intent studycenterintent4 = new Intent(this, TrainClassStudyActivity.class);
                    studycenterintent4.putExtra("videoId", studydatas.getData().get(3).getId());
                    startActivity(studycenterintent4);
                }
                break;
            case R.id.reclass_list_oneimage:
                // TODO  近期开班1
                if (recentdatas != null && recentdatas.getData().size() != 0) {
                    Intent studycenterintent = new Intent(this, TrainClassDataelsActivity.class);
                    studycenterintent.putExtra("trainingId", recentdatas.getData().get(0).getId());
                    startActivity(studycenterintent);
                }
                break;
            case R.id.reclass_list_twoimage:
                // TODO  近期开班2
                if (recentdatas != null && recentdatas.getData().size() != 0) {
                    Intent studycenterintent = new Intent(this, TrainClassDataelsActivity.class);
                    studycenterintent.putExtra("trainingId", recentdatas.getData().get(1).getId());
                    startActivity(studycenterintent);
                }
                break;
            case R.id.reclass_list_threeimage:
                // TODO  近期开班3
                if (recentdatas != null && recentdatas.getData().size() != 0) {
                    Intent studycenterintent = new Intent(this, TrainClassDataelsActivity.class);
                    studycenterintent.putExtra("trainingId", recentdatas.getData().get(2).getId());
                    startActivity(studycenterintent);
                }
                break;
            case R.id.reclass_list_fourimage:
                // TODO  近期开班4
                if (recentdatas != null && recentdatas.getData().size() != 0) {
                    Intent studycenterintent = new Intent(this, TrainClassDataelsActivity.class);
                    studycenterintent.putExtra("trainingId", recentdatas.getData().get(3).getId());
                    startActivity(studycenterintent);
                }
                break;


        }

    }
}

