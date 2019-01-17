package com.meiliangzi.app.ui.view.sendcar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.config.Constant;
import com.meiliangzi.app.model.bean.IndexSendacarBean;
import com.meiliangzi.app.model.bean.QuerySendacarinfoBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.SaveToExcelUtil;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class SendFinishActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    public int currentPage=1;
    public int pageSize=10;
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.text_select_time)
    TextView text_select_time;
    private Dialog dialog;
    private View inflate;
    private BaseQuickAdapter<IndexSendacarBean.IndexSendacarData> adapter;
    private BaseQuickAdapter<QuerySendacarinfoBean.Data> adapter1;
    private TextView text_cncule,text_sure;
    private EditText end,start;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.image_exmport)
    ImageView image_exmport;
    SaveToExcelUtil  savetoexcel;
    private QuerySendacarinfoBean querySendacarinfoBean;
    private IndexSendacarBean indexSendcarbean;
    private String excelPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_send_finish);
    }

    @Override
    protected void findWidgets() {
        excelPath = getExcelDir()+ File.separator+"后勤公司外出车辆登记表.xls";
        savetoexcel=new SaveToExcelUtil(this,excelPath);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        adapter = new BaseQuickAdapter<IndexSendacarBean.IndexSendacarData>(this, listView,R.layout.item_ywc_sendcar) {

            @Override
            public void convert(BaseViewHolder helper, final IndexSendacarBean.IndexSendacarData item) {
                helper.setText(R.id.text_sendcar_departmentName, item.getDepartmentName());
                helper.setText(R.id.text_sendcar_plateNumber, item.getPlateNumber());
                helper.setText(R.id.text_sendcar_start, item.getStart());
                helper.setText(R.id.text_sendcar_end, item.getEnd());
                String start = item.getStartAt().substring(item.getStartAt().lastIndexOf(" "));
                String end = item.getEndAt().substring(item.getEndAt().lastIndexOf(" "));
                helper.setText(R.id.text_sendcar_startAt_endAt, start + "~" + end);





            }
        };
        adapter1 = new BaseQuickAdapter<QuerySendacarinfoBean.Data>(this, R.layout.item_ywc_sendcar) {

            @Override
            public void convert(BaseViewHolder helper, final QuerySendacarinfoBean.Data item) {
                helper.setText(R.id.text_sendcar_departmentName, item.getDepartmentName());
                helper.setText(R.id.text_sendcar_plateNumber, item.getPlateNumber());
                helper.setText(R.id.text_sendcar_start, item.getStart());
                helper.setText(R.id.text_sendcar_end, item.getEnd());
                String start = item.getStartAt().substring(item.getStartAt().lastIndexOf(" "));
                String end = item.getEndAt().substring(item.getEndAt().lastIndexOf(" "));
                helper.setText(R.id.text_sendcar_startAt_endAt, start + "~" + end);





            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SendFinishActivity.this,SendaCarinfoActivity.class);
                intent.putExtra("sendACarId",adapter.getItem(position-1).getId());
                intent.putExtra("type",4);
                intent.putExtra("proposerUserid",adapter.getItem(position-1).getProposerUserid());
                startActivity(intent);
            }
        });
        text_select_time.setOnClickListener(this);
        image_exmport.setOnClickListener(this);
    }

    @Override
    protected void initComponent() {
        //ProxyUtils.getHttpProxy().indexsendacar(this,4,currentPage,pageSize);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ProxyUtils.getHttpProxy().indexsendacar(this,4,currentPage,pageSize);
    }

    protected void getindexsendacar(IndexSendacarBean bean){
        querySendacarinfoBean=null;
        listView.setAdapter(adapter);
        //adapter.setDatas(bean.getData());
        if(listView.getVisibility()==View.GONE){
            listView.setVisibility(View.VISIBLE);
        }
        if(tvEmpty.getVisibility()==View.VISIBLE){
            tvEmpty.setVisibility(View.GONE);
        }
        if (currentPage == 1) {
            adapter.pullRefresh(bean.getData());
        } else {
            adapter.pullLoad(bean.getData());
            //listViewAdapter.pullRefresh(articalList.getData());
        }

    }
    private  void  showSelecttie(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
//填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_sendcar_select_time, null);
         text_cncule=(TextView) inflate.findViewById(R.id.text_cncule);
         text_sure=(TextView) inflate.findViewById(R.id.text_sure);
        end=(EditText) inflate.findViewById(R.id.edit_select_end);
        start=(EditText) inflate.findViewById(R.id.edit_select_start);

        text_sure.setOnClickListener(this);
        text_cncule.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_select_time:
                showSelecttie();
                break;
            case R.id.text_cncule:
                //showSelecttie();
                dialog.dismiss();
                break;
            case R.id.text_sure:
                String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                Pattern p = Pattern.compile(eL);
                Matcher s = p.matcher(start.getText().toString().trim());
                Matcher e = p.matcher(end.getText().toString().trim());
                boolean dates = s.matches();
                boolean datee = e.matches();
                if (!dates||!datee) {
                    ToastUtils.show("时间输入格式不对，正确输入格式为2018-09-01");
                }else {
                    text_select_time.setText(start.getText().toString().trim()+"~"+end.getText().toString().trim());
                    ProxyUtils.getHttpProxy().querysendacarlist(this,end.getText().toString().trim(),start.getText().toString().trim());
                    dialog.dismiss();
                }


                break;
            case R.id.image_exmport:
                if(querySendacarinfoBean==null){
                    //ToastUtils.s
                    showSelecttie();
                }else {
                    showexport();
                }



                break;
        }

    }

    public static String getExcelDir() {
        // SD卡指定文件夹
        String sdcardPath = Environment.getExternalStorageDirectory()
                .toString();
        File dir = new File(sdcardPath + File.separator + "Excel"
                + File.separator + "sendCar");

        if (dir.exists()) {
            return dir.toString();

        } else {
            dir.mkdirs();
            return dir.toString();
        }
    }


    private void showexport() {
        File f=new File(excelPath);
        if(f.exists()){
            f.delete();
        }else {
            try {
                f.createNewFile();
                //f=new File(excelPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        savetoexcel.createExcel(f);
        savetoexcel.writeToExcel(querySendacarinfoBean.getData());

    }

    protected void getquerysendacarlist(QuerySendacarinfoBean bean){
        //bean.getData().remove(0);
        querySendacarinfoBean=bean;
        listView.setAdapter(adapter1);
        adapter1.setDatas(bean.getData());

        if(tvEmpty.getVisibility()==View.VISIBLE){
            tvEmpty.setVisibility(View.GONE);
        }

    }


    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        getData(4, currentPage, Constant.PAGESIZE);
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        getData(4, currentPage, Constant.PAGESIZE);
    }
    public void getData(int type,int currentPage,int pageSize ){
        ProxyUtils.getHttpProxy().indexsendacar(this,type,currentPage,pageSize);
    }
}
