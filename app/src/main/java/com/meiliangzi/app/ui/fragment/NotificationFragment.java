package com.meiliangzi.app.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.test.TestData;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;

import butterknife.BindView;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 我的
 **/

public class NotificationFragment extends BaseFragment {


    private BaseQuickAdapter adapter;

    @BindView(R.id.listView)
    ListView listView;

    public NotificationFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = createView(inflater.inflate(R.layout.fragment_notification, null, false));
        return view;
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        adapter = new BaseQuickAdapter(getActivity(),R.layout.item_msg) {
            @Override
            public void convert(BaseViewHolder helper, Object item) {
                switch (helper.getPosition()){
                    case 0:
                        helper.setImageResource(R.id.ivIcon,R.mipmap.ic_msg_one);
                        break;
                    case 1:
                        helper.setImageResource(R.id.ivIcon,R.mipmap.ic_msg_two);
                        break;
                }
            }
        };
        listView.setAdapter(adapter);
        adapter.setDatas(TestData.getData(2));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
