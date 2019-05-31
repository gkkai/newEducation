package com.meiliangzi.app.ui.view.Academy.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
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


import butterknife.BindView;

public class ReadFragment extends BaseFragment {
    private String position;
    @BindView(R.id.gradview)
    MyGridView gradview;
    BaseVoteAdapter<PaperBean.Data.QuestionOption> kaoshiAdapter;

    @BindView(R.id.tv_analysis)
    TextView tv_analysis;
    private String type;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private String mode;

    public ReadFragment() {
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            position=(String) getArguments().get("position");
            mode=(String) getArguments().get("mode");

        }
        return createView(inflater.inflate(R.layout.fragment_read, container, false));

    }





    @Override
    protected void findWidgets() {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.analysis, null);
           // ArrayList<JSONObject> objectList=(ArrayList<JSONObject>) JSONObject.parseObject( MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption(),ArrayList.class);
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

        kaoshiAdapter=new BaseVoteAdapter<PaperBean.Data.QuestionOption>(getContext(),gradview,R.layout.choice) {
            @Override
            public void convert(BaseViewHolder helper, PaperBean.Data.QuestionOption item) {

              final int  pos= kaoshiAdapter.getPosition();
                if("3".equals(MyApplication.paperBean.getData().get(Integer.parseInt(position)).getType())){
                    //TODO 填空题
                    helper.showOrGoneView(R.id.ll_chose,false);
                    helper.showOrGoneView(R.id.ll_blanks,true);
                    if(!item.ischos){
                        MyApplication.paperBean.getData().get(Integer.valueOf(position)).setIschos(false);
                        ((EditText)helper.getView(R.id.edit_Answer)).setHint("请输入答案");
                    }else {
                        ((EditText)helper.getView(R.id.edit_Answer)).setText(item.getValue());
                        MyApplication.paperBean.getData().get(Integer.valueOf(position)).setIschos(true);
                    }
                    ((EditText)helper.getView(R.id.edit_Answer)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                                String s=v.getText().toString();
                                if(!"".equals(s)){
                                    MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setIschos(true);
                                    MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setValue(s);
                                }else {
                                    MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setIschos(false);
                                    MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setValue(s);
                                }


                               // kaoshiAdapter.notifyDataSetChanged();
                                return true;
                            }

                            return false;
                        }
                    });
                    ((EditText)helper.getView(R.id.edit_Answer)).addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence sv, int start, int before, int count) {



                            //kaoshiAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void afterTextChanged(Editable sv) {
                            String s=sv.toString();
                            if(!"".equals(s)){
                                MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setIschos(true);
                                MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setValue(s);
                            }else {
                                MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setIschos(false);
                                MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setValue(s);
                            }
                        }
                    });

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
        gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if(MyApplication.paperBean.getData().get(Integer.parseInt(position)).getType().equals("1")){
                    MyApplication.paperBean.getData().get(Integer.parseInt(position)).setIschos(true);
                    //TODO
                    if( MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).ischos){
                        MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setIschos(false);
                        kaoshiAdapter.notifyDataSetChanged();
                    }else {
                        MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(pos).setIschos(true);
                        kaoshiAdapter.notifyDataSetChanged();
                    }
                }else {
                    MyApplication.paperBean.getData().get(Integer.parseInt(position)).setIschos(true);
                    //TODO
                    for(int i=0;i<MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().size();i++){
                        if(pos==i){
                            MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(i).setIschos(true);
                        }else {
                            MyApplication.paperBean.getData().get(Integer.valueOf(position)).getQuestionOption().get(i).setIschos(false);
                        }
                    }
                    kaoshiAdapter.notifyDataSetChanged();
                }



            }
        });
        tv_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 查看解析
                shownavigation( );
            }
        });

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
        TextView tv_content=(TextView) inflate.findViewById(R.id.tv_content);
        tv_content.setText(MyApplication.paperBean.getData().get(Integer.parseInt(position)).getAnswerAnalysis());
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
