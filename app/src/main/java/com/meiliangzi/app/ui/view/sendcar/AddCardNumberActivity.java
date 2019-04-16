package com.meiliangzi.app.ui.view.sendcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.QueryuserBean;
import com.meiliangzi.app.model.bean.QueryvehicleListBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

public class AddCardNumberActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.text_sure)
    TextView text_sure;
    private BaseQuickAdapter<QueryuserBean.QueryuserData> adapter;
    private String number="";
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_add_card_number);
    }

    @Override
    protected void findWidgets() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        adapter=new BaseQuickAdapter<QueryuserBean.QueryuserData>(this,R.layout.add_car_list_item) {
            @Override
            public void convert(BaseViewHolder helper, QueryuserBean.QueryuserData item) {
                ((TextView)helper.getView(R.id.tv_diredname)).setText(item.getDriverName());

                ((TextView)helper.getView(R.id.tv_divernumber)).setText(item.getPlateNumber());

            }
        };
        adapter.setDatas(MyApplication.driverList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent mIntent = new Intent();
//                mIntent.putExtra("positino",getIntent().getIntExtra("positino",1));
//                mIntent.putExtra("id",adapter.getItem(position-1).getId());
//                mIntent.putExtra("plateNumber",adapter.getItem(position-1).getPlateNumber());
//                mIntent.putExtra("type",2);
//                // 设置结果，并进行传送
//                setResult(101, mIntent);
//                finish();
                Intent mIntent = new Intent(AddCardNumberActivity.this,DriverListActivity.class);
                mIntent.putExtra("positino",position);
                mIntent.putExtra("type","105");
                startActivityForResult(mIntent,105);
            }
        });
        text_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.putExtra("positino",getIntent().getIntExtra("positino",0));
//                mIntent.putExtra("id",adapter.getItem(position-1).getId());
//                mIntent.putExtra("plateNumber",adapter.getItem(position-1).getPlateNumber());
               // MyApplication.driverList=
               // number=intent.getStringExtra("plateNumber"));
                if("".equals(number)){
                    mIntent.putExtra("type",2);
                    // 设置结果，并进行传送
                    setResult(101, mIntent);
                    finish();
                }else {
                    MyApplication.driverList.get(i-1).setPlateNumber(number);
                    mIntent.putExtra("type",2);
                    // 设置结果，并进行传送
                    setResult(101, mIntent);
                    finish();
                }

//                adapter.setDatas( MyApplication.driverList);
//                adapter.notifyDataSetChanged();

            }
        });
    }


//    @Override
//    public void onBackClick(View v) {
//        //super.onBackClick();
//        finish();
//
//    }

    @Override
    protected void initComponent() {
        //TODO 查询公司下的所有子公司
       // ProxyUtils.getHttpProxy().queryvehiclelist(this);

    }
//    public void getQueryvehicleListBean(QueryvehicleListBean bean){
//
//        adapter.setDatas(bean.getData());
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 105:
                //((TextView)getViewByPosition(intent.getIntExtra("positino",100),listView).findViewById(R.id.text_add_send_driverName)).setText(intent.getStringExtra("driverName"));
                //((TextView)getViewByPosition(intent.getIntExtra("positino",100),listView).findViewById(R.id.text_add_send_driverPhone)).setText(intent.getStringExtra("driverPhone"));
                ((TextView)getViewByPosition(intent.getIntExtra("positino",100),listView).findViewById(R.id.tv_divernumber)).setText(intent.getStringExtra("plateNumber"));

//                String s=intent.getStringExtra("plateNumber");
 i=intent.getIntExtra("positino",1);
//                int d=intent.getIntExtra("id",1);
               number=intent.getStringExtra("plateNumber");
//                //MyApplication.driverList.get(data.getIntExtra("positino",1)-1).setId(Integer.valueOf(data.getStringExtra("id")));
//                adapter.setDatas( MyApplication.driverList);
//                adapter.notifyDataSetChanged();

                break;

        }
    }
    public View getViewByPosition(int pos, XListView mgview ) {
        final int firstListItemPosition = mgview.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + mgview.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return mgview.getAdapter().getView(pos, null, mgview);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return mgview.getChildAt(childIndex);
        }
    }
}
