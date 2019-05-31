package com.meiliangzi.app.ui.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.meiliangzi.app.tools.ToastUtils;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    protected abstract void findWidgets();

    protected abstract void initComponent();

    protected View mView;

    protected View createView(View view) {

        this.mView = view;
        ButterKnife.bind(this, view);
        findWidgets();
        initComponent();
        initListener();
        initHandler();
        excuteOther();
        asyncRetrive();
        return mView;
    }

    @SuppressWarnings("unchecked")
    protected <T> T findView(int id) {
        return (T) mView.findViewById(id);
    }

    protected void initListener() {
        return;
    }

    protected void initHandler() {
        return;
    }

    protected void excuteOther() {
        return;
    }


    protected void asyncRetrive() {
        return;
    }


    /**
     * ��ʾʧ����Ϣ��Ĭ����ʾ��˾����������Ҫ��ʾ�����������д
     */
    protected void showErrorMessage(String errorMessage) {
        ToastUtils.custom(errorMessage.toString());
    }

    /**
     * 显示失败信息，默认显示吐司，子类有需要显示界面可自行重写
     */
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        if("今天已记录".equals(errorMessage)){

        }else {
            ToastUtils.custom(errorMessage);
        }
    }

    protected void showErrorMessage(Integer errorCode) {
    }



}
