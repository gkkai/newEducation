package com.meiliangzi.app.ui.view.DistributionSystem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.PaperOneLevelTypeListBean;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

/**
 * 车辆库
 */

public class CerGarageActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;
    BaseVoteAdapter<String> kaoshiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_cer_garage);
    }

    @Override
    protected void findWidgets() {
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        kaoshiAdapter=new BaseVoteAdapter<String>(getParent(),listView,R.layout.car_grage_list) {
            @Override
            public void convert(BaseViewHolder helper, final String item) {



            }
        };
        listView.setAdapter(kaoshiAdapter);

    }

    @Override
    protected void initComponent() {

    }
}
