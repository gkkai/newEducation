package com.meiliangzi.app.ui.view.Academy.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.bean.PaperBean;
import com.meiliangzi.app.widget.MyGridView;
import com.zipow.videobox.view.mm.sticker.StickerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalysisFragment extends BaseFragment {

    private String position;
    @BindView(R.id.gradview)
    MyGridView gradview;
    BaseVoteAdapter<PaperBean.Data.QuestionOption> kaoshiAdapter;


    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_type)
    TextView tv_type;
    List<String> item1=new ArrayList<>();
    @BindView(R.id.tv_rightkey)
    TextView tv_rightkey;

    @BindView(R.id.tv_stateorr)
    TextView tv_stateorr;
    @BindView(R.id.tv_stateok)
    TextView tv_stateok;
    @BindView(R.id.tv_parsingcontent)
    TextView tv_parsingcontent;

    public AnalysisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        position=(String) getArguments().get("position");
        return createView(inflater.inflate(R.layout.fragment_analysis, container, false));

    }

    @Override
    protected void findWidgets() {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.analysis, null);
//
        tv_title.setText(MyApplication.paperBean.getData().get(Integer.parseInt(position)).getTitle());
        switch (MyApplication.paperBean.getData().get(Integer.parseInt(position)).getType()){
            case "0":
                tv_type.setText("单选题");
                break;
            case "1":
                tv_type.setText("多选题");
                break;
            case "2":
                tv_type.setText("判断题");
                break;
            case "3":
                tv_type.setText("填空题");
                break;
        }


//
        kaoshiAdapter=new BaseVoteAdapter<PaperBean.Data.QuestionOption>(getContext(),gradview,R.layout.choice) {
            @Override
            public void convert(BaseViewHolder helper, PaperBean.Data.QuestionOption item) {
                final int  pos= kaoshiAdapter.getPosition();
                item1.clear();
                if("3".equals(MyApplication.paperBean.getData().get(Integer.parseInt(position)).getType())){
                    //TODO 填空题
                    helper.showOrGoneView(R.id.ll_chose,false);
                    helper.showOrGoneView(R.id.ll_blanks,true);
                    if(MyApplication.paperBean.getData()!=null&&MyApplication.paperBean.getData().get(Integer.valueOf(position)).getUserAnswer()!=null&&MyApplication.paperBean.getData().get(Integer.valueOf(position)).getUserAnswer().size()!=0){
                    }
                    if(pos==0){
                        helper.showOrHideView(R.id.text,true);
                    }else {
                        helper.showOrHideView(R.id.text,false);
                    }
                    if(!item.ischos){
                        MyApplication.paperBean.getData().get(Integer.valueOf(position)).setIschos(false);
                        ((EditText)helper.getView(R.id.edit_Answer)).setHint("请输入答案");
                    }else {
                        ((EditText)helper.getView(R.id.edit_Answer)).setText(item.getValue());
                        MyApplication.paperBean.getData().get(Integer.valueOf(position)).setIschos(true);
                    }
                    ((EditText)helper.getView(R.id.edit_Answer)).setFocusable(false);

                    ((EditText)helper.getView(R.id.edit_Answer)).setFocusableInTouchMode(false);

                }else {
                    helper.showOrGoneView(R.id.ll_chose,true);
                    helper.showOrGoneView(R.id.ll_blanks,false);
                    if(item.ischos){
                        ((CheckBox)helper.getView(R.id.ck)).setChecked(true);


                    }else {
                        ((CheckBox)helper.getView(R.id.ck)).setChecked(false);

                    }
                    ((TextView)helper.getView(R.id.tvAnswer)).setText(item.getValue());
                    ((CheckBox)helper.getView(R.id.ck)).setText(item.getOptinon());


                }
            }

        };
        kaoshiAdapter.setDatas(MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption());
        gradview.setAdapter(kaoshiAdapter);
        if((MyApplication.paperBean.getData().get(Integer.valueOf(position)).getUserAnswerResult()==null)){
            tv_stateok.setVisibility(View.GONE);
            tv_stateorr.setVisibility(View.VISIBLE);
        }else {
            if(MyApplication.paperBean.getData().get(Integer.valueOf(position)).getUserAnswerResult().equals("0")){
                tv_stateok.setVisibility(View.VISIBLE);
                tv_stateorr.setVisibility(View.GONE);
            }else {
                tv_stateok.setVisibility(View.GONE);
                tv_stateorr.setVisibility(View.VISIBLE);
            }
        }

        gradview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });
        for(int i=0;i<MyApplication.paperBean.getData().get(Integer.valueOf(position)).getRightAnswer().size();i++){
            if("3".equals(MyApplication.paperBean.getData().get(Integer.parseInt(position)).getType())){
                item1.add(MyApplication.paperBean.getData().get(Integer.valueOf(position)).getRightAnswer().get(i).getValue());

            }else {
                item1.add(MyApplication.paperBean.getData().get(Integer.valueOf(position)).getRightAnswer().get(i).getOptinon());

            }
                 }

        String result=  item1.toString();
        result=result.replace("[","");
        result=result.replace("]","");
        tv_rightkey.setText("正确答案:"+result);
        tv_parsingcontent.setText(MyApplication.paperBean.getData().get(Integer.valueOf(position)).getAnswerAnalysis());


    }

    @Override
    protected void initComponent() {

    }
    private Dialog dialog;
    private View inflate;
    private void shownavigation( ) {
        dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        if (inflate != null) {
            ViewGroup parentViewGroup = (ViewGroup) inflate.getParent();
            if (parentViewGroup != null ) {
                parentViewGroup.removeView(inflate);
            }
        }

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

}
