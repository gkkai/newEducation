package com.meiliangzi.app.ui.view.train;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.TrainQueryDataBean;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

public class QueryClassListActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;
    BaseTrainAdapter<TrainQueryDataBean.Databena> querydata;//信息查询
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_query_class_list);
    }

    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(false);
        intent = getIntent();
        querydata = new BaseTrainAdapter<TrainQueryDataBean.Databena>(this, listView, R.layout.train_querydata) {
            @Override
            public void convert(BaseViewHolder helper, TrainQueryDataBean.Databena item) {
                helper.setText(R.id.tv_query_tiele, item.getName());
                helper.setText(R.id.tv_query_create_at, item.getCreate_at());

            }
        };
        listView.setAdapter(querydata);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QueryClassListActivity.this, QueryDataActivity.class);
                intent.putExtra("studyId", querydata.getItem(position - 1).getId());
                intent.putExtra("type", "querydata");
                startActivity(intent);
                //finish();
                //TODO 返回查询数据

            }
        });
    }

    @Override
    protected void initComponent() {
        int userId = Integer.valueOf(PreferManager.getUserId());
        String name = intent.getStringExtra("name");
        String cardId = intent.getStringExtra("id_card");
        //Todo 开始进行查询
        ProxyUtils.getHttpProxy().queryuserstudylist(this, userId, name, cardId);


    }
    protected void getqueryuserstudylist(TrainQueryDataBean data) {
        querydata.setDatas(data.getData());

    }
}
