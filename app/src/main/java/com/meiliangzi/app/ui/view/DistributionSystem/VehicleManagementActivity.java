package com.meiliangzi.app.ui.view.DistributionSystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.widget.MyGridView;

import butterknife.BindView;

public class VehicleManagementActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.gradview)
    MyGridView gradview;
    @BindView(R.id.image_apply_usercar)
    ImageView image_apply_usercar;
    @BindView(R.id.image_apply_hitchhiking)
    ImageView image_apply_hitchhiking;
    @BindView(R.id.image_cer_oning)
    ImageView image_cer_oning;
    @BindView(R.id.image_car_garage)
    ImageView image_car_garage;
    @BindView(R.id.image_mine_oning)
    ImageView image_mine_oning;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_vehicle_management);
    }

    @Override
    protected void findWidgets() {
        image_apply_usercar.setOnClickListener(this);
        image_cer_oning.setOnClickListener(this);
        image_car_garage.setOnClickListener(this);
        image_apply_hitchhiking.setOnClickListener(this);

    }

    @Override
    protected void initComponent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_apply_usercar :
                Intent apply=new Intent(this, ApplyUseActivity.class);
                startActivity(apply);
                break;
            case R.id.image_cer_oning :
                Intent cer_oning=new Intent(this, CarProcessActivity.class);
                startActivity(cer_oning);
                break;
            case R.id.image_car_garage :
                Intent car_garage=new Intent(this, CerGarageActivity.class);
                startActivity(car_garage);
                break;
            case R.id.image_apply_hitchhiking :
                Intent apply_hitchhiking=new Intent(this, Apply_Hitchhiking_Activity.class);
                startActivity(apply_hitchhiking);
                break;
        }
    }
}
