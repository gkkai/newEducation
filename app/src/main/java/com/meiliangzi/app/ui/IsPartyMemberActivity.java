package com.meiliangzi.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 是否党员
 **/

public class IsPartyMemberActivity extends BaseActivity {


    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.rbYes)
    RadioButton rbYes;
    @BindView(R.id.rgNo)
    RadioButton rgNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_is_party_member);
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
                    case R.id.rbYes:
                        intent.putExtra("content", "是");
                        break;
                    case R.id.rgNo:
                        intent.putExtra("content", "否");
                        break;
                }
                setResult(RESULT_OK, intent);
                IsPartyMemberActivity.this.finish();
            }
        });
    }
}
