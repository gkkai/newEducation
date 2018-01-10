package com.meiliangzi.app.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.CityListBean;
import com.meiliangzi.app.model.bean.CountyListbean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.adapter.ListCityAdapter;
import com.meiliangzi.app.ui.adapter.ListCountyAdapter;
import com.meiliangzi.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddCityListsActivity extends BaseActivity {
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.text_title)
    TextView text_title;
    ListCityAdapter cityadapter;
    ListCountyAdapter countyadapter;
    private List<CountyListbean.DataBean> countyBean=new ArrayList<>();//县目录下的列表
    private List<CityListBean.DataBean> cityBean=new ArrayList<>();//市目录下的县列表
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_city_lists);
        onCreateView(R.layout.activity_add_city_lists);
        String  type=  getIntent().getStringExtra("type");
        if("city".equals(getIntent().getStringExtra("type"))){
            text_title.setText("市名称");
        }else {
            text_title.setText("县名称");
        }

    }

    @Override
    protected void findWidgets() {
        cityadapter=new ListCityAdapter(this,cityBean);
        countyadapter=new ListCountyAdapter(this,countyBean);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                if("city".equals(type)){
                    intent.putExtra("cityid",((CityListBean.DataBean)cityadapter.getItem(i)).getId());
                    intent.putExtra("cityname",((CityListBean.DataBean)cityadapter.getItem(i)).getName());
                    setResult(103,intent);
                    finish();
                }else if("county".equals(type)){
                    intent.putExtra("countyname",((CountyListbean.DataBean)countyadapter.getItem(i)).getName());
                    intent.putExtra("county_id",((CountyListbean.DataBean)countyadapter.getItem(i)).getId());
                    setResult(104,intent);
                    finish();
                }

            }
        });
    }

    @Override
    protected void initComponent() {
        if(getIntent().getStringExtra("type")!=null&&getIntent().getStringExtra("type").equals("city")){
//TODO 拿到市列表
            ProxyUtils.getHttpProxy().citylist(AddCityListsActivity.this);
        }else if(getIntent().getIntExtra("cityid",0)!=0){
            ProxyUtils.getHttpProxy().countylist(AddCityListsActivity.this,getIntent().getIntExtra("cityid",0));
        }
    }
    protected  void  citylist(CityListBean data) {
        type="city";
        cityBean.clear();
        cityBean = data.getData();
        cityadapter.setdata(cityBean);
        listView.setAdapter(cityadapter);
    }
    /**
     * 返回县列表数据类型
     */
    protected  void  countylist(CountyListbean data){
        type="county";
        countyBean=data.getData();
        countyadapter.setdata(countyBean);
        listView.setAdapter(countyadapter);
    }

    @Override
    public void onBackClick(View v) {
        setResult(105);
        finish();
       // super.onBackClick(v);
    }
}
