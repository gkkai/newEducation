package com.meiliangzi.app.ui.view.Academy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.AnalysisActivity;
import com.meiliangzi.app.ui.view.Academy.ColumnActivity;
import com.meiliangzi.app.ui.view.Academy.NoCulmnActivity;
import com.meiliangzi.app.ui.view.Academy.WeekExaminationActivity;
import com.meiliangzi.app.ui.view.Academy.bean.FindByPaperIdBean;
import com.meiliangzi.app.ui.view.Academy.bean.PageListBean;
import com.meiliangzi.app.ui.view.Academy.bean.PaperOneLevelTypeListBean;
import com.meiliangzi.app.widget.XListView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerFragment extends BaseFragment implements XListView.IXListViewListener {
    @BindView(R.id.listView)
    XListView listView;

    BaseVoteAdapter<PaperOneLevelTypeListBean.Data> kaoshiAdapter;
    Gson gson=new Gson();
    int[] id={R.mipmap.apicture,R.mipmap.pic2,R.mipmap.pic3,R.mipmap.pic4};
    private FindByPaperIdBean fbean;

    public AnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return createView(inflater.inflate(R.layout.fragment_answer, container, false));
    }

    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        kaoshiAdapter=new BaseVoteAdapter<PaperOneLevelTypeListBean.Data>(getContext(),listView,R.layout.answer_item) {
            @Override
            public void convert(BaseViewHolder helper, final PaperOneLevelTypeListBean.Data item) {
                ((TextView)helper.getView(R.id.tv_typeName)).setText(item.getTypeName());
                if(getPosition()>=id.length){
                    helper.setImageByUrl(R.id.image,null,id[0],id[0]);
                }else {
                    helper.setImageByUrl(R.id.image,null,id[getPosition()],id[getPosition()]);
                }


            }
        };
        listView.setAdapter(kaoshiAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PaperOneLevelTypeListBean.Data bean=  kaoshiAdapter.getItem(position-1);
                if(Integer.valueOf(bean.getPaperTypeCount())>=1){
                    // TODO 有子栏目
                    Intent culmnintent =new Intent(getContext(), ColumnActivity.class);
                    culmnintent.putExtra("pid",bean.getId());
                    culmnintent.putExtra("tv_title",bean.getTypeName());
                    startActivity(culmnintent);
                }else {
                    switch (bean.getPaperCount()){

                        //TODO 没有子栏目
                        case "0":
                            ToastUtils.show("暂无试卷");
                            break;
                        case "1":
                            Map <String,String> map=new HashMap<String, String>();
                            map.put("paperTypeId",bean.getId());
                            OkhttpUtils.getInstance(getContext()).getList("academyService/examinationPaper/findByPaperId", map, new OkhttpUtils.onCallBack() {
                                @Override
                                public void onFaild(Exception e) {

                                }
                                @Override
                                public void onResponse(String json) {
                                    try {
                                        fbean=   gson.fromJson(json, FindByPaperIdBean.class);
                                        //TODO 一个试卷 直接进试题
                                        Intent Examinationintent =new Intent(getContext(), WeekExaminationActivity.class);
                                        Examinationintent.putExtra("paperId",fbean.getData().getId());
                                        Examinationintent.putExtra("title",bean.getTypeName());
                                        Examinationintent.putExtra("pagetitle",fbean.getData().getTitle());
                                        Examinationintent.putExtra("totalNumber",fbean.getData().getTotalNumber());
                                        Examinationintent.putExtra("time",fbean.getData().getDuration());
                                        Examinationintent.putExtra("mode",fbean.getData().getMode());
                                        Examinationintent.putExtra("countDown",fbean.getData().getCountDown());
                                        Examinationintent.putExtra("repeatAnswer",fbean.getData().getRepeatAnswer());
                                        Examinationintent.putExtra("createType",fbean.getData().getCreateType());
                                        startActivity(Examinationintent);

                                    }catch (final Exception e){
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtils.show(fbean.getMessage());
                                            }
                                        });


                                    }


                                }
                            });

                            break;
                        default:
                            //有很多试卷
                            Intent culmnintent =new Intent(getContext(), NoCulmnActivity.class);
                            culmnintent.putExtra("pid",bean.getId());
                            culmnintent.putExtra("tv_title",bean.getTypeName());
                            startActivity(culmnintent);
                    }

                }
            }
        });

    }

    @Override
    protected void initComponent() {
        getdata();
    }


    protected void getdata() {
        Map<String,String> map=new HashMap<>();
        map.put("userId",NewPreferManager.getId());
        map.put("hierarchy","2");
        OkhttpUtils.getInstance(getContext()).getList("academyService/examinationPaperType/findExaminationPaperOneLevelTypeList", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                try {
                    final PaperOneLevelTypeListBean bean=gson.fromJson(json,PaperOneLevelTypeListBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            kaoshiAdapter.pullRefresh(bean.getData());

                            //TODO 专业知识答题
                           // kaoshiAdapter.setDatas(bean.getData());
                        }
                    });



                }catch (Exception e){

                }




            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white));
    }

    @Override
    public void onRefresh() {
        getdata();

    }

    @Override
    public void onLoadMore() {
        getdata();

    }
}
