package com.meiliangzi.app.ui.view.Academy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.ExaminationQuestionsActivity;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;
import com.meiliangzi.app.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialExaminationFragment extends BaseFragment {

    @BindView(R.id.listView)
    XListView listView;

    BaseVoteAdapter<String> kaoshiAdapter;
    private String content;
    private IndexColumnBean.Data data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = createView(inflater.inflate(R.layout.fragment_special_examination, container, false));
        return view;
    }

    @Override
    protected void findWidgets() {
        kaoshiAdapter=new BaseVoteAdapter<String>(getContext(),listView,R.layout.examination_item) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                ((TextView)helper.getView(R.id.tv_startquestions)).setText(item);

                helper.getView(R.id.tv_startquestions).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.startAty(getActivity(),ExaminationQuestionsActivity.class);
                    }
                });
            }
        };
        listView.setAdapter(kaoshiAdapter);
        initData();

    }

    @Override
    protected void initComponent() {

    }
    private void initData() {
        content = getArguments().getString("position");
        data= (IndexColumnBean.Data) getArguments().getSerializable("data");
        if (TextUtils.isEmpty(content)){
        }else{
            if (!TextUtils.isEmpty(content)){
            }

        }
    }
    public void  settype(String type){
        if("考试".equals(type)){
            List<String> list=new ArrayList();
            list.add("考试");
            list.add("考试");
            list.add("考试");
            kaoshiAdapter.setDatas(list);
        }else {
            List<String> list=new ArrayList();
            list.add("考试记录");
            list.add("考试记录");
            list.add("考试记录");
            kaoshiAdapter.setDatas(list);
        }


    }
}
