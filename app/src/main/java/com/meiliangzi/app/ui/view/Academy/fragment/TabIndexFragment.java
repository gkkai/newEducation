package com.meiliangzi.app.ui.view.Academy.fragment;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.DetailsWebActivity;
import com.meiliangzi.app.ui.view.Academy.bean.ArticleListBean;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.meiliangzi.app.R.mipmap.index;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabIndexFragment extends BaseFragment implements XListView.IXListViewListener {

    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tv_index)
    TextView tv_index;
    BaseVoteAdapter<ArticleListBean.Data> Adapter;
    String position = "";
    private int page = 0;
    private IndexColumnBean.Data data;
    @BindView(R.id.column_children)
    HorizontalScrollView hListView;
    @BindView(R.id.gadiogroup)
    RadioGroup radiogroup;
    int id;

    public TabIndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arg = getArguments();//获取一个Bundle
        position = arg.getString("position");//通过定义好的tag获取数据
        data = (IndexColumnBean.Data) arg.getSerializable("data");
        return createView(inflater.inflate(R.layout.fragment_tab_index, container, false));
    }

    public void settype(String type) {


    }

    private int pos = 0;
    List<ArticleListBean> list = new ArrayList<>();

    private void setRaidBtnAttribute(final RadioButton codeBtn, String btnContent, int id) {
        if (null == codeBtn) {
            return;
        }
        @SuppressWarnings("ResourceType")
        ColorStateList colorStateList=getResources().getColorStateList(R.drawable.color_radiobutton);
        codeBtn.setBackgroundResource(R.drawable.radio_group_selector);
        codeBtn.setTextColor(colorStateList);
        codeBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        codeBtn.setId(id);
        codeBtn.setText(btnContent);
        codeBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        codeBtn.setGravity(Gravity.CENTER);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), codeBtn.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (int)(dp2px(25)));
        codeBtn.setLayoutParams(rlp);
    }

    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        tv_index.setText(data.getColumnName());
        if (data.getChildren() != null && data.getChildren().size() != 0) {
            //TODO 有子菜单
            hListView.setVisibility(View.VISIBLE);
            int index = 0;
            for(IndexColumnBean.Data.Children ss:data.getChildren()){
                RadioButton  button=new RadioButton(getContext());
                button.setPadding(50,5,50,5);
                setRaidBtnAttribute(button,ss.getColumnName(),index);

                radiogroup.addView(button);

                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) button
                        .getLayoutParams();
                layoutParams.setMargins(0, 0,  (int)(dp2px(20)), 0);//4个参数按顺序分别是左上右下
                button.setLayoutParams(layoutParams);
                index++;
            }
            radiogroup.check(pos);
            Map<String,String> map=new HashMap<>();
            map.put("columnId",data.getChildren().get(pos).getId());
            map.put("pageNumber",String.valueOf(page));
            map.put("pageSize","10");
            getPageList(map);
           // ProxyUtils.gethttpchanyexutyan().getPageList(this, data.getChildren().get(pos).getId(),page+"","10");
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    pos=checkedId;
                    Map<String,String> map=new HashMap<>();
                    map.put("columnId",data.getChildren().get(pos).getId());
                    map.put("pageNumber",String.valueOf(page));
                    map.put("pageSize","10");
                    getPageList(map);
                    //ProxyUtils.gethttpchanyexutyan().getPageList(TabIndexFragment.this, data.getChildren().get(pos).getId(),page+"","10");

                }
            });
            }else {
            //TODO 没有子菜单
            hListView.setVisibility(View.GONE);
            pos=-1;
            Map<String,String> map=new HashMap<>();
            map.put("columnId",data.getId());
            map.put("pageNumber",String.valueOf(page));
            map.put("pageSize","10");
            getPageList(map);
            //ProxyUtils.gethttpchanyexutyan().getPageList(this, data.getId(),page+"","10");


        }
        Adapter=new BaseVoteAdapter<ArticleListBean.Data>(getContext(),listView,R.layout.tab_list) {
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

                        break;
                    case 1:
                        helper.showOrGoneView(R.id.rl_big_image,true);
                        helper.showOrGoneView(R.id.rl_no_image,false);
                        helper.showOrGoneView(R.id.rl_small_image,false);
                        ((TextView)helper.getView(R.id.tv_big_title)).setText(item.getTitle());

                        ((TextView)helper.getView(R.id.tv_big_department)).setText(item.getDepartmentName()
                                +"   "+item.getCreateTime());

                        helper.setImageByUrl(R.id.image_big,item.getCoverImage(),R.mipmap.smallphoto,R.mipmap.smallphoto);
                        break;
                    case 2:
                        helper.showOrGoneView(R.id.rl_big_image,false);
                        helper.showOrGoneView(R.id.rl_no_image,false);
                        helper.showOrGoneView(R.id.rl_small_image,true);
                        ((TextView)helper.getView(R.id.tv_small_title)).setText(item.getTitle());

                        ((TextView)helper.getView(R.id.tv_small_department)).setText(item.getDepartmentName()
                                +"   "+item.getCreateTime());
                        List<String> result = Arrays.asList(item.getCoverImage().split(","));
                        if(result.size()>=3){

                        }
                        switch (result.size()){
                            case 1:
                                helper.setImageByUrl(R.id.image_small_one,result.get(0),R.mipmap.smallphoto,R.mipmap.smallphoto);

                                break;
                            case 2:
                                helper.setImageByUrl(R.id.image_small_one,result.get(0),R.mipmap.smallphoto,R.mipmap.smallphoto);
                                helper.setImageByUrl(R.id.image_small_two,result.get(1),R.mipmap.smallphoto,R.mipmap.smallphoto);

                                break;
                            case 3:
                                helper.setImageByUrl(R.id.image_small_one,result.get(0),R.mipmap.smallphoto,R.mipmap.smallphoto);
                                helper.setImageByUrl(R.id.image_small_two,result.get(1),R.mipmap.smallphoto,R.mipmap.smallphoto);
                                helper.setImageByUrl(R.id.image_small_three,result.get(2),R.mipmap.smallphoto,R.mipmap.smallphoto);

                                break;
                        }



                        break;

                }

            }
        };
        listView.setAdapter(Adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), DetailsWebActivity.class);
                intent.putExtra("type","0");
                intent.putExtra("url","academyService/html/articleInfo.html?id="+Adapter.getItem(position-1).getId());
                intent.putExtra("title",Adapter.getItem(position-1).getTitle());
                intent.putExtra("description",Adapter.getItem(position-1).getContent());
                intent.putExtra("id",Adapter.getItem(position-1).getId());
                startActivity(intent);
            }
        });

    }



    private void getPageList(Map<String,String> map){


        OkhttpUtils.getInstance(getContext()).getList("academyService/essay/getPageList", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(final String json) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Gson gson=new Gson();
                            ArticleListBean bean=gson.fromJson(json,ArticleListBean.class);
                            if(bean.getCode()=="1"){
                                ToastUtils.show(bean.getMessage());
                            }else {
                                if (page == 0) {
                                    Adapter.pullRefresh(bean.getData());
                                } else {
                                    Adapter.pullLoad(bean.getData());
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
    protected void initComponent() {


    }
    @Override
    public void onRefresh() {
        page = 0;
        if(pos!=-1){
            Map<String,String> map=new HashMap<>();
            map.put("columnId",data.getChildren().get(pos).getId());
            map.put("pageNumber",String.valueOf(page));
            map.put("pageSize","10");
            getPageList(map);
            //ProxyUtils.gethttpchanyexutyan().getPageList(this, data.getChildren().get(pos).getId(),page+"","10");

        }else {
            Map<String,String> map=new HashMap<>();
            map.put("columnId",data.getId());
            map.put("pageNumber",String.valueOf(page));
            map.put("pageSize","10");
            getPageList(map);
            //ProxyUtils.gethttpchanyexutyan().getPageList(this, data.getId(),page+"","10");

        }

    }

    @Override
    public void onLoadMore() {
        page++;
        if(pos!=-1){
            Map<String,String> map=new HashMap<>();
            map.put("columnId",data.getChildren().get(pos).getId());
            map.put("pageNumber",String.valueOf(page));
            map.put("pageSize","10");
            getPageList(map);
           // ProxyUtils.gethttpchanyexutyan().getPageList(this, data.getChildren().get(pos).getId(),page+"","10");

        }else {
            Map<String,String> map=new HashMap<>();
            map.put("columnId",data.getId());
            map.put("pageNumber",String.valueOf(page));
            map.put("pageSize","10");
            getPageList(map);
           // ProxyUtils.gethttpchanyexutyan().getPageList(this, data.getId(),page+"","10");

        }

    }
    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());
    }
    public float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getContext().getResources().getDisplayMetrics());
    }
    public interface Ifsearch{
        public int getid();
    }

}
