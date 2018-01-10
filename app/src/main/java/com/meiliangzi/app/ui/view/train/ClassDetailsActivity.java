package com.meiliangzi.app.ui.view.train;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;

/**
 * 班级详情
 */

public class ClassDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_class_details);
    }

    @Override
    protected void findWidgets() {
        

    }

    @Override
    protected void initComponent() {

    }



    @Override
    protected void asyncRetrive() {
        super.asyncRetrive();
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
    }
}
