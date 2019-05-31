package com.meiliangzi.app.ui.view.Academy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CAnswerActivity extends BaseActivity {
    @BindView(R.id.listView)
    XListView listView;

    BaseVoteAdapter<String> Adapter;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_canswer);
    }

    @Override
    protected void findWidgets() {
        List<String> list=new ArrayList();
        list.add("考试");
        list.add("考试");
        list.add("考试");

        Adapter=new BaseVoteAdapter<String>(this,listView,R.layout.ansner_item) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                ((TextView)helper.getView(R.id.tv_startquestions)).setText(item);
            }
        };
        listView.setAdapter(Adapter);
        Adapter.setDatas(list);
    }

    @Override
    protected void initComponent() {

    }
}
