package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.Company;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 设置部门
 **/

public class SetPartmentActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    private BaseQuickAdapter<Company.ParentMent> adapter;

    private int pos = -1;
    private Company.DataBean partments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_set_partment);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        partments = (Company.DataBean) getIntent().getSerializableExtra("object");

        adapter = new BaseQuickAdapter<Company.ParentMent>(SetPartmentActivity.this, R.layout.item_company) {
            @Override
            public void convert(BaseViewHolder helper, Company.ParentMent item) {
                CheckBox cbCk = helper.getView(R.id.cbCk);
                cbCk.setText(item.getName());
                if (helper.getPosition() == pos) {
                    cbCk.setChecked(true);
                } else {
                    cbCk.setChecked(false);
                }
            }
        };
        listView.setAdapter(adapter);
        adapter.setDatas(partments.getDepartment_info());
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                adapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("content", adapter.getItem(position).getName());
                intent.putExtra("departmentId", adapter.getItem(position).getId());
                setResult(RESULT_OK, intent);
                SetPartmentActivity.this.finish();
            }
        });
    }

    @Override
    protected void asyncRetrive() {

    }


}
