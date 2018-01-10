package com.meiliangzi.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;



public class Main2Activity extends Activity {
    MyGridView gridView;
    private BaseQuickAdapter<String> typeAdapter;
    private List<String> type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        type=new ArrayList<>();
        for (int i=0;i<50;i++){
            type.add("dfjlksjdflkjsdlkfj");
        }
        gridView= (MyGridView) findViewById(R.id.myGridViewType);
        typeAdapter = new BaseQuickAdapter<String>(Main2Activity.this, R.layout.item_filter) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.ckContent, item);
                CheckBox checkBox = helper.getView(R.id.ckContent);

            }
        };
        typeAdapter.setDatas(type);
        gridView.setAdapter(typeAdapter);
    }
}
