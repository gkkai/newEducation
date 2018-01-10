package com.meiliangzi.app.ui.view.train;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.QueryDataBean;
import com.meiliangzi.app.model.bean.RecentOpenClassBean;
import com.meiliangzi.app.model.bean.StudyCenternBean;
import com.meiliangzi.app.model.bean.TrainQueryDataBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.RuleCheckUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseTrainAdapter;
import com.meiliangzi.app.ui.dialog.MyDialog;
import com.meiliangzi.app.ui.view.AddMapLoctionActivity;
import com.meiliangzi.app.ui.view.MapNewActivity;
import com.meiliangzi.app.widget.XListView;

import butterknife.BindView;

public class QueryDataActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_query_data)
    RelativeLayout rl_query_data;
    @BindView(R.id.rel_back)
    RelativeLayout rel_back;
    @BindView(R.id.edit_querydata_name)
    EditText edit_querydata_name;
    @BindView(R.id.edit_querydata_idnumber)
    EditText edit_querydata_idnumber;
    @BindView(R.id.tx_querydata_name)
    TextView tx_querydata_name;
    @BindView(R.id.tx_querydata_id_card)
    TextView tx_querydata_id_card;
    @BindView(R.id.tv_querydata_title)
    TextView tv_querydata_title;
    @BindView(R.id.tx_querydata_start_at)
    TextView tx_querydata_start_at;
    @BindView(R.id.tx_querydata_certificate)
    TextView tx_querydata_certificate;
    @BindView(R.id.tv_querydata_scroe)
    TextView tv_querydata_scroe;
    @BindView(R.id.tx_querydata_repair_scroe)
    TextView tx_querydata_repair_scroe;
    @BindView(R.id.tx_querydata_certificate_status)
    TextView tx_querydata_certificate_status;
    @BindView(R.id.tx_querydata_query)
    TextView tx_querydata_query;




    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getStringExtra("type");
        onCreateView(R.layout.activity_query_data);
    }

    @Override
    protected void findWidgets() {
        type=getIntent().getStringExtra("type");
        if("inputdataquery".equals(type)){
            rl_query_data.setVisibility(View.VISIBLE);
            tx_querydata_query.setOnClickListener(this);
            rel_back.setVisibility(View.GONE);

        }else {
            int studyId=getIntent().getIntExtra("studyId",0);
            //Todo 开始进行查询
            ProxyUtils.getHttpProxy().queryuserstudyinfo(this, studyId);

        }


    }
    protected void getqqueryuserstudyinfo(QueryDataBean data){
        tx_querydata_name.setText(data.getData().getName());
        tx_querydata_id_card.setText(data.getData().getId_card());
        tv_querydata_title.setText(data.getData().getTitle());
        tx_querydata_start_at.setText(data.getData().getStart_at());
        tx_querydata_certificate.setText(data.getData().getCertificate());
        tx_querydata_repair_scroe.setText(data.getData().getRepair_scroe());
        tx_querydata_certificate_status.setText(data.getData().getCertificate_status());
        tv_querydata_scroe.setText(data.getData().getScroe());

    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx_querydata_query:
                try {
                    RuleCheckUtils.checkEmpty(edit_querydata_name.getText().toString(), "请设置输入姓名");
                    RuleCheckUtils.checkEmpty(edit_querydata_idnumber.getText().toString(), "请输入身份证号码");

                    //ProxyUtils.getHttpProxy().queryuserstudylist(this, userId, name,cardId);

                    Intent intent=new Intent(this,QueryClassListActivity.class);
                    intent.putExtra("type","dataquery");
                    intent.putExtra("name",edit_querydata_name.getText().toString().trim());
                    intent.putExtra("id_card",edit_querydata_idnumber.getText().toString().trim());
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    ToastUtils.custom(e.getMessage());
                    e.printStackTrace();
                }
                //Todo 开始进行查询

                break;
        }
    }

}
