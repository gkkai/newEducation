package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 修改性别
 **/

public class UpdateGenderActivity extends BaseActivity {

    @BindView(R.id.rgGender)
    RadioGroup rgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_update_gender);
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initListener() {
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Intent intent = new Intent();
                switch (checkedId){
                    case R.id.rbMale:
                        intent.putExtra("content","男");
                        break;
                    case R.id.rbFeal:
                        intent.putExtra("content","女");
                        break;
                    case R.id.rbOther:
                        intent.putExtra("content","保密");
                        break;
                }
                setResult(RESULT_OK,intent);
                UpdateGenderActivity.this.finish();
            }
        });
    }
}
