package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.Company;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * 设置公司
 *
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/23 14:44
 */
public class SetCompanyActivity extends BaseActivity {


    @BindView(R.id.listView)
    ListView listView;

    private BaseQuickAdapter<Company.DataBean> adapter;

    private int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_set_company);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        adapter = new BaseQuickAdapter<Company.DataBean>(SetCompanyActivity.this,R.layout.item_company) {
            @Override
            public void convert(BaseViewHolder helper, Company.DataBean item) {
                CheckBox cbCk = helper.getView(R.id.cbCk);
                cbCk.setText(item.getName());
                if(helper.getPosition() == pos){
                    cbCk.setChecked(true);
                }else {
                    cbCk.setChecked(false);
                }
            }
        };
        listView.setAdapter(adapter);
    }


    @Override
    protected void asyncRetrive() {
        getCompanyList();
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                adapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("content",adapter.getItem(position).getName());
                intent.putExtra("companyId",adapter.getItem(position).getId());
                intent.putExtra("object", adapter.getItem(position));
                setResult(RESULT_OK,intent);
                SetCompanyActivity.this.finish();
            }
        });
    }

    public void getCompanyList() {
        ProxyUtils.getHttpProxy().querycampany(SetCompanyActivity.this);
    }

    public void getData(Company company) {
        adapter.setDatas(company.getData());

//        SetCompanyActivity.this.finish();
    }
}
