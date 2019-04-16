package com.meiliangzi.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseFragment;


public class CheckFragment1 extends BaseFragment {
private int pos;
    public CheckFragment1(int pos) {
        this.pos=pos;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check, container, false);
    }


    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {

    }
    @Override
    public void onResume() {
        super.onResume();
       // getData("0");
    }

}
