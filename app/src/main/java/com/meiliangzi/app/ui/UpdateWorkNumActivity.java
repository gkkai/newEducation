package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author xiaobo
 * @date   2017/8/25
 * @version  1.0
 * @description  修改工号
 *
 **/

public class UpdateWorkNumActivity extends BaseActivity {

    @BindView(R.id.etNickName)
    EditText etNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_update_work_num);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }
    @OnClick(R.id.tvDone)
    public void onClick() {
        try {
            RuleCheckUtils.checkEmpty(etNickName.getText().toString(),"请输入真实姓名");
            Intent intent = new Intent();
            intent.putExtra("content",etNickName.getText().toString());
            setResult(RESULT_OK,intent);
            UpdateWorkNumActivity.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
