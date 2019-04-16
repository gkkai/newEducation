package com.meiliangzi.app.ui.view.sendcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.click.OnChangeListener;
import com.meiliangzi.app.model.bean.QueryuserBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DriverUserLisActivity extends BaseActivity implements OnChangeListener {
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.button)
    Button button;
    private BaseQuickAdapter<QueryuserBean.QueryuserData> adapter;
    private String type;
    /**
     * 全部司机
     */
    private ArrayList<QueryuserBean.QueryuserData> alldriverList = new ArrayList<>();
    /**
     * 选中的司机集合
     */
    private ArrayList<QueryuserBean.QueryuserData> driverList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateView(R.layout.activity_driver_user_lis);

    }

    @Override
    protected void findWidgets() {
        type=getIntent().getStringExtra("type");
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        adapter=new BaseQuickAdapter<QueryuserBean.QueryuserData>(this,R.layout.driveruser_list_item) {
            @Override
            public void convert(final BaseViewHolder helper, QueryuserBean.QueryuserData item) {
                ((TextView)helper.getView(R.id.tv_driver_name)).setText(item.getDriverName());
                if("add".equals(type)){
                    helper.getView(R.id.cbSelect).setVisibility(View.VISIBLE);
//                    holder.canSelect();
                    if(item.isSelect()){
                        ((CheckBox)helper.getView(R.id.cbSelect)).setChecked(true);
//                    holder.canSelect();
                    }else {
                        ((CheckBox)helper.getView(R.id.cbSelect)).setChecked(false);
//                    holder.canSelect();
                    }
                    ((CheckBox)helper.getView(R.id.cbSelect)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            OnChangeListener(helper.getPosition(),isChecked);
                            //isChecked
                        }
                    });
                }



            }
        };
        listView.setAdapter(adapter);
        if("add".equals(type)){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 选择司机返回
//                Intent mIntent = new Intent();
//                mIntent.putExtra("id",adapter.getItem(position-1).getId());
//                mIntent.putExtra("positino",getIntent().getIntExtra("positino",1));
//                mIntent.putExtra("driverName",adapter.getItem(position-1).getDriverName());
//                mIntent.putExtra("plateNumber",adapter.getItem(position-1).getPlateNumber());
//                mIntent.putExtra("driverPhone",adapter.getItem(position-1).getDriverPhone());
//
//                finish();

                    // OnChangeListener(position,isChecked);
                    //TODO 选择司机返回
                    MyApplication.driverList=driverList; // 设置结果，并进行传送
                    Intent mIntent = new Intent();
                    mIntent.putExtra("positino",getIntent().getIntExtra("positino",1));
                    //TODO 添加
                    mIntent.putExtra("type",2);
                    setResult(101, mIntent);

                    finish();
                }
            } );

        }else {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TODO 选择司机返回
                Intent mIntent = new Intent();
                    mIntent.putExtra("id",adapter.getItem(position-1).getId());
                    mIntent.putExtra("positino",getIntent().getIntExtra("positino",1));
                    mIntent.putExtra("driverName",adapter.getItem(position-1).getDriverName());
                    mIntent.putExtra("plateNumber",adapter.getItem(position-1).getPlateNumber());
                    mIntent.putExtra("driverPhone",adapter.getItem(position-1).getDriverPhone());
                    //TODO 修改
                    mIntent.putExtra("type",1);
                    setResult(101, mIntent);
                    finish();
                }
            });
        }

    }

    @Override
    protected void initComponent() {
        //TODO 查询公司下的所有子公司
        ProxyUtils.getHttpProxy().queryuserlist(this);

    }
    public void getqueryuserlist(QueryuserBean bean){
        alldriverList= (ArrayList<QueryuserBean.QueryuserData>) bean.getData();
        adapter.setDatas(bean.getData());
    }

    @Override
    public void OnChangeListener(int position, boolean isChecked) {
        if(alldriverList.size()==0){

        }else {
            if (isChecked) {

                alldriverList.get(position).setSelect(true);
                if (!contains(driverList, alldriverList.get(position))) {
                    driverList.add(alldriverList.get(position));

                    adapter.setDatas(alldriverList);
                    adapter.notifyDataSetChanged();


                }

            } else {
                alldriverList.get(position).setSelect(false);
                if (contains(driverList, alldriverList.get(position))) {
                    driverList.remove(alldriverList.get(position));

                    adapter.setDatas(alldriverList);
                    adapter.notifyDataSetChanged();

                }
            }
        }

    }
    private boolean contains(List<QueryuserBean.QueryuserData> list, QueryuserBean.QueryuserData imageBean) {
        for (QueryuserBean.QueryuserData bean : list) {
            if (bean.getDriverName().equals(imageBean.getDriverName())) {
                return true;
            }
        }
        return false;
    }


//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//
//                mImages.get(position).setSelect(true);
//                if (!contains(mSelectImages, mImages.get(position))) {
//                    mSelectImages.add(mImages.get(position));
//                    if (mSelectImages.size() == maxImageCount) {
//                        mAdapter.notifyData(mSelectImages);
//                    }
//
//            }
//
//        } else {
//            mImages.get(position).setSelect(false);
//            if (contains(mSelectImages, mImages.get(position))) {
//                mSelectImages.remove(mImages.get(position));
//                if (mSelectImages.size() == maxImageCount - 1) {
//                    mAdapter.notifyData(mSelectImages);
//                }
//            }
//        }
//
//    }
}
