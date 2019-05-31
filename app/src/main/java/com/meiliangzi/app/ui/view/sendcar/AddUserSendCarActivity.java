package com.meiliangzi.app.ui.view.sendcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.UserCarSearchuserinfoBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;

import butterknife.BindView;

public class AddUserSendCarActivity extends BaseActivity {
    @BindView(R.id.gradview_group)
    GridView gradview_group;
    @BindView(R.id.edit_search)
    EditText edit_search;
    InputMethodManager imm;
    private BaseTrainAdapter<UserCarSearchuserinfoBean.DepartmentName> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_add_user_send_car);
        imm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    protected void findWidgets() {
        adapter=new BaseTrainAdapter<UserCarSearchuserinfoBean.DepartmentName>(this,gradview_group, R.layout.item_sendcaruser_lists) {
            @Override
            public void convert(BaseViewHolder helper, UserCarSearchuserinfoBean.DepartmentName item) {
                helper.setText(R.id.tv_nickName,item.getNickName());

                helper.setText(R.id.tv_userphone,item.getDepartmentName());

            }
        };
        gradview_group.setAdapter(adapter);
        gradview_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent();
                mIntent.putExtra("departmentName",adapter.getItem(position).getDepartmentName());
                mIntent.putExtra("nickName",adapter.getItem(position).getNickName());
                mIntent.putExtra("userPhone",adapter.getItem(position).getUserPhone());
                // 设置结果，并进行传送
                setResult(103, mIntent);
                finish();
            }
        });
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                if (actionId== EditorInfo.IME_ACTION_SEARCH ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER))
                {
//do something;
                    if("".equals(edit_search.getText().toString())){
                        ToastUtils.show("您输入的内容为空");
                    }else {
                        String result= edit_search.getText().toString();
                        //TODO 跳转到搜索结果界面
                        ProxyUtils.getHttpProxy().searchuserinfo(AddUserSendCarActivity.this,edit_search.getText().toString());

                    }

                    return true;
                }
                return false;
            }

        });
    }

    @Override
    protected void initComponent() {

    }
    private void getsearchuserinfo(UserCarSearchuserinfoBean bean){
        adapter.setDatas(bean.getData());
        adapter.notifyDataSetChanged();
    }
}
