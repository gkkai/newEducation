package com.meiliangzi.app.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.MyCollectActivity;
import com.meiliangzi.app.ui.MyScoreActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.SetttingActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.view.train.TrainActivity;
import com.meiliangzi.app.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 我的
 **/

public class MineFragment extends BaseFragment {

    @BindView(R.id.ivImg)
    CircleImageView ivImg;
    @BindView(R.id.tvUserName)
    TextView tvUserName;

    public MineFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.zm_red));
        View view = createView(inflater.inflate(R.layout.fragment_mine, null, false));
        return view;
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
        if(TextUtils.isEmpty(PreferManager.getUserId())){
            ivImg.setImageResource(R.mipmap.ic_default_star);
            tvUserName.setText("登录/注册");
            return;
        }
        if(PreferManager.getUserStar().startsWith("http")){
            ImageLoader.getInstance().displayImage(PreferManager.getUserStar(),ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star,R.mipmap.ic_default_star));
        }else {
            ImageLoader.getInstance().displayImage("file:///"+ PreferManager.getUserStar(),ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star,R.mipmap.ic_default_star));
        }
        tvUserName.setText(PreferManager.getUserName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.llUseInfo, R.id.llMyScore, R.id.llMycollect, R.id.llSetting,R.id.llMyClassinfo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llUseInfo:
                if(TextUtils.isEmpty(PreferManager.getUserId())){
                    IntentUtils.startAty(getActivity(), LoginActivity.class);
                }else {
                    IntentUtils.startAty(getActivity(), PersonCenterActivity.class);
                }

                break;
            case R.id.llMyScore:
                if(TextUtils.isEmpty(PreferManager.getUserId())){
                    IntentUtils.startAty(getActivity(),LoginActivity.class);
                }else {
                    IntentUtils.startAty(getActivity(), MyScoreActivity.class);
                }
                break;
            case R.id.llMycollect:
                if(TextUtils.isEmpty(PreferManager.getUserId())){
                    IntentUtils.startAty(getActivity(),LoginActivity.class);
                }else {
                    IntentUtils.startAty(getActivity(), MyCollectActivity.class);
                }
                break;
            case R.id.llSetting:
                if(TextUtils.isEmpty(PreferManager.getUserId())){
                    IntentUtils.startAty(getActivity(),LoginActivity.class);
                }else {
                    IntentUtils.startAty(getActivity(), SetttingActivity.class);
                }
                break;
            case R.id.llMyClassinfo:
                if(TextUtils.isEmpty(PreferManager.getUserId())){
                    IntentUtils.startAty(getActivity(), LoginActivity.class);
                }else {
                    //IntentUtils.startAty(getActivity(), PersonCenterActivity.class);
                    Intent intent=new Intent(getActivity(), TrainActivity.class);
                    intent.putExtra("type","myclassinfo");
                    startActivity(intent);
                }

                break;
        }
    }
}
