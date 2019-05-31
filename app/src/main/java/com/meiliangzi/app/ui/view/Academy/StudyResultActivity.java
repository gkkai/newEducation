package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.bean.MainBean;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.view.Academy.bean.RanKBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class StudyResultActivity extends BaseActivity {
    @BindView(R.id.tv_person_total_score)
    TextView tv_person_total_score;

    @BindView(R.id.tv_company_ranking)
    TextView tv_company_ranking;
    @BindView(R.id.tv_drank)
    TextView tv_drank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_study_result);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        Map<String,String> map =new HashMap<>();
        map.put("userId", NewPreferManager.getId());
        OkhttpUtils.getInstance(this).getList("academyService/userAdmy/finByUserScoreRank", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                final RanKBean ranKBean=gson.fromJson(json,RanKBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_person_total_score.setText(ranKBean.getData().getUserTotalScore());
                        tv_company_ranking.setText(ranKBean.getData().getRank());
                        tv_drank.setText(ranKBean.getData().getdRank());
                    }
                });

            }
        });

    }
}
